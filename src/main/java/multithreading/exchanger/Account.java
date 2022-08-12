package multithreading.exchanger;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.Exchanger;

@Data
@AllArgsConstructor
public class Account implements Runnable{
    private String userAccountName;
    Map<Currency, BigDecimal> balance;
    Exchanger<BigDecimal> ex;

    @Override
    public void run() {
        try {
            process();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void process () throws InterruptedException {
        BigDecimal exchange = ex.exchange(balance.get(Currency.EURO).subtract(Currency.EURO.getRate()));
        setBalance(Map.of(Currency.EURO, exchange));
    }
}
