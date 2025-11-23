package com.farm2pot.product.entity;

import com.farm2pot.common.exception.DomainErrorCode;
import com.farm2pot.common.exception.DomainException;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.concurrent.atomic.AtomicInteger;

@Embeddable
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Stock {
    @Convert(converter = AtomicIntegerConverter.class)
    private AtomicInteger quantity;

    public void increase(AtomicInteger amount) {
        this.quantity.addAndGet(amount.get());
    }

    public void decrease(AtomicInteger amount) {
        int currentQty = this.quantity.get();
        if (currentQty < amount.get()) {
            throw new DomainException(DomainErrorCode.OUT_OF_STOCK);
        }
        this.quantity.addAndGet(-amount.get());
    }
}
