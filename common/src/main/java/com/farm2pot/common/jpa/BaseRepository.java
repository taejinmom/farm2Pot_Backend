package com.farm2pot.common.jpa;

import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.collections4.CollectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * QueryDSL용 공통기능
 * @param <E> 엔티티
 * @param <ID> 식별자
 */
@Component
@Slf4j
public abstract class BaseRepository<E, ID> {

    protected EntityManager entityManager;

    @Getter
    protected JPAQueryFactory queryFactory;

    private JpaEntityInformation<E, ID> entityInformation;

    public EntityManager getEntityManager() { return entityManager; }

    /**
     * EntityManager가 주입되면 JPAQueryFactory 초기화
     */
    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    protected JPAQuery<E> selectFrom(EntityPath<E> from) {
        return queryFactory.selectFrom(from);
    }

    protected JPAQuery<E> select(Expression<E> expr) {
        return queryFactory.select(expr);
    }

    protected <T> JPAQuery<T> select(Expression<?>... exprs) {
        return queryFactory.select(Projections.fields((Class<T>) exprs[0].getType(), exprs));
    }
    protected <T> JPAQuery<T> select(Class<T> type, Expression<?>... exprs) {
        return queryFactory.select(Projections.fields(type, exprs));
    }

    protected <T> JPAQuery<T> selectByConstructor(Class<T> type, Expression<?>... exprs) {
        return queryFactory.select(Projections.constructor(type, exprs));
    }

    @Transactional
    public E save(E entity) {
        if (isNewEntity(entity)) {
            entityManager.persist(entity);
            return entity;
        } else {
            return entityManager.merge(entity);
        }
    }

    @Transactional
    public void delete(E entity) {
        if (!isNewEntity(entity)) {
            entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
        }
    }
    protected <T> Page<T> applyPagination(JPAQuery<T> query, Pageable pageable, JPAQuery<Long> countQuery) {
        List<T> content = pagingFetch(query, pageable);
        Page<T> page;

        if (countQuery == null) {
            long totalCount = content.size();
            page = PageableExecutionUtils.getPage(content, pageable, () -> totalCount);
        } else {
            page = PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
        }

        return page;
    }

    private <T>List<T> pagingFetch(JPAQuery<T> query, Pageable pageable) {
        if (pageable.isUnpaged()) {
            return query.fetch();
        } else {
            return query.offset(pageable.getOffset()).limit(pageable.getPageSize()).fetch();
        }
    }

    private Boolean isNewEntity(E entity) {
        JpaEntityInformation<E, ID> entityInformation = getJpaEntityInformation(entity.getClass());
        return entityInformation.isNew(entity);
    }

    @SuppressWarnings("unchecked")
    private JpaEntityInformation<E, ID> getJpaEntityInformation(Class<?> clazz) {
        if (this.entityInformation == null) {
            this.entityInformation =
                    (JpaEntityInformation<E, ID>) JpaEntityInformationSupport.getEntityInformation(clazz, entityManager);
        }
        return this.entityInformation;
    }

    protected <T extends Number & Comparable<?>> JPAQuery<T> select(NumberExpression<T> expr) {
        return queryFactory.select(expr);
    }

    public OrderSpecifier[] toOrderSpecifiers(EntityPathBase<?> entityPathBase, Sort sort) {
        PathBuilder<?> entityPath = new PathBuilder<>(entityPathBase.getType(), entityPathBase.getMetadata());

        return sort.stream()
                .map(order -> new OrderSpecifier(
                        order.isAscending() ? Order.ASC : Order.DESC,
                        entityPath.get(order.getProperty())))
                .toArray(OrderSpecifier[]::new);
    }

    public OrderSpecifier<?>[] toOrderSpecifiers(Sort sort, Map<String, String> clientToEntityFieldMap, EntityPathBase<?>... entities) {
        return toOrderSpecifiers(sort, clientToEntityFieldMap, true, entities);
    }

    public OrderSpecifier<?>[] toOrderSpecifiersNoFieldFound(Sort sort, Map<String, String> clientToEntityFieldMap, EntityPathBase<?>... entities) {
        return toOrderSpecifiers(sort, clientToEntityFieldMap, false, entities);
    }

    private OrderSpecifier<?>[] toOrderSpecifiers(Sort sort, Map<String, String> clientToEntityFieldMap, boolean isFieldFound, EntityPathBase<?>... entities) {
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();

        for (Sort.Order order : sort) {
            String clientFieldName = order.getProperty();
            String entityFieldName = clientToEntityFieldMap.getOrDefault(clientFieldName, clientFieldName);

            boolean fieldFound = false;
            for (EntityPathBase<?> entity : entities) {
                Class<?> entityClass = entity.getType();

                if (hasField(entityClass, entityFieldName)) {
                    PathBuilder<?> entityPath = new PathBuilder<>(entityClass, entity.getMetadata().getName());
                    OrderSpecifier<?> orderSpecifier = new OrderSpecifier(order.isAscending() ? Order.ASC : Order.DESC, entityPath.get(entityFieldName));
                    orderSpecifiers.add(orderSpecifier);
                    fieldFound = true;
                    break;
                }
            }

            if (!fieldFound && isFieldFound) {
                throw new IllegalArgumentException("정렬 필드에 맞는 엔티티가 없습니다: " + clientFieldName);
            }
        }

        return CollectionUtils.isNotEmpty(orderSpecifiers)
                ? orderSpecifiers.toArray(new OrderSpecifier<?>[0])
                : null;
    }

    private boolean hasField(Class<?> clazz, String fieldName) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            return field != null;
        } catch (NoSuchFieldException e) {
            return false;
        }
    }
}
