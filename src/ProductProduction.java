import java.util.Scanner;
import java.time.Instant;

/*
BUG: SALESTIME NEVER STOPS. ELAPSED TIME ADDS ONTO THE PREVIOUS TIME!!!!
 */

public class ProductProduction {
    public int inventoryCount = 0;
    Scanner s = new Scanner(System.in);
    // Attributes
    int productRate = 1;
    int currency = 0;
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
        System.out.println( "Your current value is " + currency);
        System.out.println( "--- |Options| ---");
        System.out.print( "[ Type M to Return To Menu],[ Type P to Make Product]");
        if (startProduction) {
            System.out.print(",[Type S to Manage Sales]");
        }
        if (currency > 0) {
            System.out.print(",[Type U to Manage Upgrades]");
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
        if (menuResponse.equals("U") && currency > 0) {
            upgrades();
        }

    }

    int salesMenuVisits = 0;
    double demand = 1;
    double demandMinimum = 1;
    double rangeDemand = 0.5;
    int logbase = 20;
    int storeInventory = 0;
    int salePotential = 0;
    int inputtedAmt = 0;
    double mcChickenValue = 3.00;
    Instant salestartInstant = salesTime.start();
    public void makeSales() {
        double timeholding = 0;
        System.out.println( "--- |Sales| ---");
        if (salesMenuVisits == 0) { // The MiniTutorial
            System.out.println("Welcome to Stores Menu!");
            System.out.println("Here you can Stock your stores to sell Mcchickens!");
            System.out.println("Come back when you have McChickens in your inventory!");
            salesMenuVisits++;
        } else if (inventoryCount == 0 ) {
            System.out.println("You have no inventory to sell!");
        } else if (salesMenuVisits == 1 ){ // This is beyond the MiniTutorial
            salesInputPercentage();
            salestartInstant = salesTime.start();
            salesMenuVisits++;
        } else if (salesMenuVisits > 1) {
            Instant salendInstant = salesTime.stop();
            timeholding = salesTime.timeInterval().toMillis()/1000.0;
            System.out.println("Elapsed time (seconds): " + timeholding);
            System.out.println("Store Inventory: " + storeInventory);
            System.out.println("Inventory: " + inventoryCount);
            calcDemand();
            salePotential = (int) (timeholding * demand);
            if (salePotential < storeInventory) { // If the amt of McChickens you COULD'VE sold is less than Max
                storeInventory -= salePotential;
                System.out.println("Sold " + salePotential + " McChickens, Store InventoryCount is now: " + storeInventory);
                System.out.println("(+ " + salePotential + "Dollars"); // Amt You Couldve Sold
                currency += (int) (mcChickenValue * salePotential);
            } else { // If the amt of McChickens you COULD'VE sold is equal to or greater than max.
                currency += (int) (mcChickenValue * salePotential);
                storeInventory = 0;
                System.out.println("Sold All " + salePotential + " McChickens, Store InventoryCount: " + storeInventory);
            }
            salestartInstant = salesTime.start();
            salesInputPercentage();
            // create product
            // give option to restock or run an ad, adding a multiplier to the demand for the next salesmenu visit.
            System.out.println( "--- |Sales| ---");
            System.out.println();
        }


        System.out.println("Your Visits is now: " + salesMenuVisits);
        showMenu();
    }

    public void calcDemand() { // Calculate the Demand. Shrink to 2 Var
        demand =(rangeDemand * Math.random()) + demandMinimum;
        System.out.println("Demand: " + demand);
    }

    public void salesInputPercentage() {
        System.out.println( "--- |Stocking| ---");
        System.out.println("What Decimal of your inventory would you like to input? (0-1)");
        double inputPercentage = s.nextDouble(); // Potentially make seperate input that takes integers 0-100. Also Could Make this optional.
        System.out.println("Input percent: " + inputPercentage );
        s.nextLine(); // No Clue what this does but code doesn't work without it
        int calculateInputtedAmt = (int) Math.round(inputPercentage * inventoryCount);
        System.out.println("Inputted Inventory: " + calculateInputtedAmt);
        inventoryCount -= calculateInputtedAmt; // Pick up Here
        inputtedAmt = calculateInputtedAmt; // Calculated stores the change, but keeps it until we no longer need to refrence
        storeInventory += inputtedAmt;
        System.out.println("Store Inventory: " + storeInventory);
        System.out.println("(+ " + inputtedAmt + ")");
        System.out.println( "--- |Stocking| ---");
        System.out.println();
    }

    boolean startUpgrades = false;
    int mcChickenUpgCost = 20;
    public void upgrades() {
        System.out.println( "--- |Upgrades| ---");
        if (!startUpgrades) {
            System.out.println( "This is the upgrades menu!, Speed up various elements to maximize your income!");
            startUpgrades = true;
        } else {
            System.out.println("Input your Selected Upgrade with the corresponding letter");
            System.out.println( "Production Upgrades: [A| Invest in McChicken Factories, increasing production rate by 50% ($ " + mcChickenUpgCost + "),[B| Build a new Store, Increasing Demand by 1 ($ " + mcChickenUpgCost + ")");
            String upgradeResponse = s.nextLine();

            if (upgradeResponse.equals("A")) {
                System.out.println( "A. Adding Factory] ");
                if (currency < mcChickenUpgCost){
                    System.out.println( "You CANNOT Afford This Item");
                } else {
                    System.out.println( "Bought Factory Upgrade");
                    productRate *= 1.5;
                    System.out.println( "Production Rate: " + productRate + " McChickens Per Second" );
                    currency -= mcChickenUpgCost;
                    mcChickenUpgCost = (int) (mcChickenUpgCost* 1.5) + 10;

                }
            } else if (upgradeResponse.equals("B")) {

            }
        }
        showMenu();
    }
}