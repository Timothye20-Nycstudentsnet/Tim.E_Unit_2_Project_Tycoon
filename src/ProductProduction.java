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

    // Code Begins here!
    TimeInterval productTime = new TimeInterval();
    TimeInterval salesTime = new TimeInterval();
    Scanner scan = new Scanner(System.in);




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
            timeholder = productTime.timeInterval().toMillis()/1000.0;
            System.out.println("Elapsed time (seconds): " + timeholder);
            int productProductionInInterval = (int) (timeholder * (productRate)); // Time in Seconds, * Product Per Second. To get how many were made
            inventoryCount += productProductionInInterval;
            System.out.println("NEW BALANCE: " + inventoryCount);
            System.out.println("(+ " + productProductionInInterval + " McChickens)");
            productionStartInstant = productTime.start();
        }


        System.out.println( "--- |Product Maker| ---");
        System.out.println();

        showMenu();
    }

    public void showMenu () {
        System.out.println( "--- |Menu| ---");
        System.out.println( "You can make " + productRate + " McChickens every second");
        System.out.println( "Your inventory is " + inventoryCount + " McChickens");
        System.out.println( "Your current value is " + productBalance);
        System.out.println( "--- |Options| ---");
        System.out.print( "[ Type M to Return To Menu],[ Type P to Make Product]");
        if (startProduction) {
            System.out.print(",[Type S to Manage Sales]");
        }
        System.out.println( "");
        System.out.println( "--- |Menu| ---");
        System.out.println();
        String menuResponse = s.nextLine();

        if (menuResponse.equals("M")) {
            showMenu();
        }
        if (menuResponse.equals("P")) {
            makeProduct();
        }
        if (menuResponse.equals("S") && startProduction) {
            makeSales();
        }

    }

    int salesMenuVisits = 0;
    int storeCount = 1;
    double demand = 1;
    int logbase = 20;
    int storeInventory = 0;
    int SalePotential = 0;
    Instant salestartInstant = salesTime.start();
    public void makeSales() {
        System.out.println( "--- |Sales| ---");
        if (salesMenuVisits == 0) {
            System.out.println("Welcome to Sales!");
            System.out.println("Here you can Automatically restock your stores, and sell McChickens for Cash!");
            System.out.println("Come back when you have McChickens in your inventory!");
            salesMenuVisits++;
        } else if (inventoryCount == 0 ) {
            System.out.println("You have no inventory to sell!");
        } else if (salesMenuVisits == 1){
            System.out.println("What Decimal of your inventory would you like to input? (0-1)");
            double inputPercentage = s.nextDouble();
            System.out.println("Input percent: " + inputPercentage );
            int inputtedAmt = (int) Math.round(inputPercentage * inventoryCount);
            System.out.println("Inputted Inventory: " + inputtedAmt);
            // Pick up Here
            System.out.println("Store Inventory: " + inventoryCount);
            salesMenuVisits++;
        }


        System.out.println("Your Visits is now: " + salesMenuVisits);
        showMenu();
    }

}
