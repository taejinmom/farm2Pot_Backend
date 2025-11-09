package com.farm2pot.common.http.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Arrays;

/**
 * 클라이언트의 Page parameter => ArgumentResolver 용도
 */
@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class CustomPageRequest {
    private int page;
    private int pageSize;
    private String[] sort;

    // jpa 페이징에 사용되는 페이징객체 반환 (pageNumber 는 0부터 시작해야 함)
    public PageRequest getPageable() {
        if (ArrayUtils.isEmpty(sort)) {
            return PageRequest.of(page - 1, pageSize);
        } else {
            Sort sortObj = Sort.unsorted();

            try {
                sortObj = Sort.by(Arrays.stream(sort).map(sortItem -> {
                    String[] arr = sortItem.split(",");
                    if (Sort.Direction.ASC.toString().equalsIgnoreCase(arr[1])) {
                        return Sort.Order.asc(arr[0]);
                    } else {
                        return Sort.Order.desc(arr[0]);
                    }
                }).toList());
            } catch (Exception e) {
            }

            return PageRequest.of(page - 1, pageSize, sortObj);
        }
    }
}
