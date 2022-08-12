package multithreading.exchanger;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.concurrent.Exchanger;
@Getter
@Setter
@Data
public class Exchang implements Runnable {
    private Exchanger<BigDecimal> acc1;
    private Exchanger<BigDecimal> acc2;

    @Override
    public void run() {
        process();
    }

    private void process () {
    }
}
