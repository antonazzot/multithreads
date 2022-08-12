package multithreading.exchanger;

import lombok.Getter;

import java.math.BigDecimal;
@Getter
public enum Currency {
    EURO(BigDecimal.valueOf(100)), DOLLAR(BigDecimal.valueOf(101));

    private BigDecimal rate;

    Currency(BigDecimal rate) {
        this.rate = rate;
    }

}
