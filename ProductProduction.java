import java.util.Scanner;
import java.time.Instant;
import java.time.Duration;



public class ProductProduction {
    public int inventoryCount = 0;
    Scanner s = new Scanner(System.in);
    // Attributes
    int productRate = 1;
    int productBalance = 0;
    boolean makingProduct = false;

    public ProductProduction(int productRate, double waitPerProduct) {
        this.productRate = productRate;
    }

    Instant startInstant = Instant.now();
    public void makeProduct() {
        System.out.println( "--- |Product Maker| ---");
        double timeholder = 0;

        Instant endInstant = Instant.now();
        Duration duration = Duration.between(startInstant, endInstant);
        timeholder =  (double) (duration.toMillis()/1000.0);

        System.out.println("Elapsed time (seconds): " + timeholder);
        int productProductionInInterval = (int) (timeholder * (productRate)); // Time in Seconds, * Product Per Second. To get how many were made
        inventoryCount += productProductionInInterval;
        System.out.println("NEW BALANCE: " + inventoryCount);
        System.out.println("(+ " + productProductionInInterval + " McChickens)");

        System.out.println("Timer Begins");
        startInstant = Instant.now();
        System.out.println( "--- |Product Maker| ---");
        System.out.println( "");

        showMenu();
    }

    public void showMenu () {
        System.out.println( "--- |Menu| ---");
        System.out.println( "You can make " + productRate + " McChickens every second");
        System.out.println( "Your current value is " + productBalance);
        System.out.println( "--- |Options| ---");
        System.out.println( "[ Type M to Return To Menu],[ Type P to Make Product]");
        System.out.println( "--- |Menu| ---");
        System.out.println( "");
        String menuResponse = s.nextLine();

        if (menuResponse.equals("M")) {
            showMenu();
        }
        if (menuResponse.equals("P")) {
            makeProduct();
        }

    }
}
