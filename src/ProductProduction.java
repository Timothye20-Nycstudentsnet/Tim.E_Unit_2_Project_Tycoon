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


    TimeInterval productTime = new TimeInterval();





    Instant productionStartInstant = productTime.start();
    boolean startProduction = false;
    public void makeProduct() {
        System.out.println( "--- |Product Maker| ---");
        double timeholder = 0;
        if (!startProduction) {
            System.out.println("Production has begun!");
            productRate = 8; // CHANGE PRODUCTION RATE HERE!!!!!
            System.out.println("As of now you're making " + productRate + " McChickens per Second!");
            System.out.println("Return to Production Menu when you want to collect your McChickens");
            startProduction = true;
            productionStartInstant = productTime.start();
        } else {
            Instant productionEndInstant = productTime.stop();
            Duration duration = Duration.between(productionStartInstant, productionEndInstant);
            timeholder =  (double) (duration.toMillis()/1000.0);

            System.out.println("Elapsed time (seconds): " + timeholder);
            int productProductionInInterval = (int) (timeholder * (productRate)); // Time in Seconds, * Product Per Second. To get how many were made
            inventoryCount += productProductionInInterval;
            System.out.println("NEW BALANCE: " + inventoryCount);
            System.out.println("(+ " + productProductionInInterval + " McChickens)");

            System.out.println("Timer Begins");
            productionStartInstant = productTime.start();
        }


        System.out.println( "--- |Product Maker| ---");
        System.out.println( "");

        showMenu();
    }

    public void showMenu () {
        System.out.println( "--- |Menu| ---");
        System.out.println( "You can make " + productRate + " McChickens every second");
        System.out.println( "Your inventory is " + inventoryCount + " McChickens");
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
