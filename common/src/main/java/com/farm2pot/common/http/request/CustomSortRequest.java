package com.farm2pot.common.http.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

import java.util.Arrays;

/**
 * 클라이언트의 Sort parameter => ArgumentResolver 용도
 */
@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class CustomSortRequest {
    private String[] sort;

    public Sort getSort() {
        try {
            return Sort.by(Arrays.stream(sort).map(sortItem -> {
                String[] arr = sortItem.split(",");
                if (Sort.Direction.ASC.toString().equalsIgnoreCase(arr[1])) {
                    return Sort.Order.asc(arr[0]);
                } else {
                    return Sort.Order.desc(arr[0]);
                }
            }).toList());
        } catch (Exception e) {
            return Sort.unsorted();
        }
    }
}
