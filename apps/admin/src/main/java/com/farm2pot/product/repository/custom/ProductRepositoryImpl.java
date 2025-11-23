package com.farm2pot.product.repository.custom;

import com.farm2pot.common.jpa.BaseRepository;
import com.farm2pot.product.entity.Product;
import com.farm2pot.product.entity.QProduct;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ProductRepository의 QueryDSL 구현체
 * BaseRepository를 상속받아 queryFactory 사용 가능
 */
@Repository
public class ProductRepositoryImpl extends BaseRepository implements ProductRepositoryCustom {

    @Override
    public Page<Product> getProducts(String keyword, String category, String origin, Pageable pageable) {
        QProduct qProduct = QProduct.product;

        // 데이터 조회 쿼리
        List<Product> content = queryFactory
                .selectFrom(qProduct)
                .where(
                        keywordContains(keyword),
                        categoryEq(category),
                        originEq(origin)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qProduct.createAt.desc())
                .fetch();

        // 카운트 쿼리 (최적화)
        JPAQuery<Long> countQuery = queryFactory
                .select(qProduct.count())
                .from(qProduct)
                .where(
                        keywordContains(keyword),
                        categoryEq(category),
                        originEq(origin)
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    /**
     * 키워드 검색 조건 (name 또는 code에 포함)
     */
    private BooleanExpression keywordContains(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return null;
        }
        QProduct product = QProduct.product;
        return product.name.containsIgnoreCase(keyword)
                .or(product.code.containsIgnoreCase(keyword));
    }

    /**
     * 카테고리 동등 조건
     */
    private BooleanExpression categoryEq(String category) {
        return category != null && !category.trim().isEmpty() ? QProduct.product.category.eq(category) : null;
    }

    /**
     * 원산지 동등 조건
     */
    private BooleanExpression originEq(String origin) {
        return origin != null && !origin.trim().isEmpty() ? QProduct.product.origin.eq(origin) : null;
    }
}
