package multithreading.exchanger;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
   private String name;
   private Account account;
}
