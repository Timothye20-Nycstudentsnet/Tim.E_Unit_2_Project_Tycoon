import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        System.out.println( "--Begin Game? (Y/N)--");
        String beginResponse = s.nextLine();

        if ( beginResponse.equals("Y")) {
            int productRate = 1; // How much it increases every wait
            int waitPerProduct = 2; // How long each wait is
            int productBalance = 0; // Balance of money :D

            ProductProduction gameMoney = new ProductProduction(productRate,waitPerProduct);

            gameMoney.showMenu();
        }

        /*
        import java.time.Instant;
import java.time.Duration;

Instant startInstant = Instant.now();

// Code segment to be measured

Instant endInstant = Instant.now();
Duration duration = Duration.between(startInstant, endInstant);
System.out.println("Elapsed time (milliseconds): " + duration.toMillis());
System.out.println("Elapsed time (seconds): " + duration.getSeconds());
         */


    }
}