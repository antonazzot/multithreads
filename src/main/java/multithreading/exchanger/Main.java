package multithreading.exchanger;

import static java.lang.Thread.sleep;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Exchanger<BigDecimal> ex = new Exchanger<BigDecimal>();
        Account oneACC = new Account("OneACC", Map.of(Currency.EURO, BigDecimal.valueOf(500)), ex);
        Account twoacc = new Account("TWOACC", Map.of(Currency.EURO, BigDecimal.valueOf(700)), ex);
        User user = new User("One", oneACC);
        User user2 = new User("TWO",twoacc);

        System.out.println("Before exchange: " + user.getAccount().balance.get(Currency.EURO));
        makeExchange(user, user2);
        sleep(500);
        System.out.println("After exchange: " + user.getAccount().balance.get(Currency.EURO));
    }

    private static void makeExchange(User user, User user2) {
        ExecutorService service = Executors.newFixedThreadPool(2);
        service.execute(user.getAccount());
        service.execute(user2.getAccount());
    }
}
