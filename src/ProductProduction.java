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
            System.out.println("Warehouse Inventory: " + inventoryCount);
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
        System.out.println( "Your Warehouse inventory is " + inventoryCount + " McChickens");
        System.out.println( "Your current Balance is $" + currency);
        if (salesMenuVisits >= 2) {
            System.out.println("The price of a McChicken is $" + mcChickenValue + "!");
        }
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
        } else if (menuResponse.equals("P")) {
            makeProduct();
        } else if (menuResponse.equals("S") && startProduction) {
            makeSales();
        } else if (menuResponse.equals("U") && currency > 0) {
            upgrades();
        } else {
            invalidcommand();
        }

    }

    public void invalidcommand() {
        System.out.println("Invalid Command. Returning to Menu");
        showMenu();
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
    int adCounter = 0;
    public void makeSales() {
        double timeholding = 0;
        System.out.println( "--- |Sales| ---");
        if (salesMenuVisits == 0) { // The MiniTutorial
            System.out.println("Welcome to Stores Menu!");
            System.out.println("Here you can Stock your stores to sell Mcchickens!");
            System.out.println("You'll sell 1 McChicken per Second, but consumer Demand acts as a multiplier!");
            System.out.println("Sell your first batch of McChickens to unlock the Upgrade Menu");
            System.out.println("Come back when you have McChickens in your inventory!");
            salesMenuVisits++;
            System.out.println( "--- |Sales| ---");
            System.out.println();
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
            System.out.println("Warehouse Inventory: " + inventoryCount);
            calcDemand();
            salePotential = (int) (timeholding * demand);
            if (salePotential < storeInventory) { // If the amt of McChickens you COULD'VE sold is less than Max
                storeInventory -= salePotential;
                System.out.println("Sold " + salePotential + " McChickens, Store InventoryCount is now: " + storeInventory);
                System.out.println("(+ $" + (salePotential * mcChickenValue) + " Dollars)"); // Amt You Couldve Sold
                currency += (int) (mcChickenValue * salePotential);
            } else { // If the amt of McChickens you COULD'VE sold is equal to or greater than max.
                currency += (int) (mcChickenValue * salePotential);
                storeInventory = 0;
                System.out.println("Sold All " + salePotential + " McChickens, Store InventoryCount: " + storeInventory);
                System.out.println("(+ $" + (salePotential * mcChickenValue) + " Dollars)");
            }
            salestartInstant = salesTime.start();
            salesInputPercentage();
            // create product
            // give option to restock or run an ad, adding a multiplier to the demand for the next salesmenu visit.
            System.out.println( "--- |Sales| ---");
            System.out.println();
        }
        showMenu();
    }

    public void calcDemand() { // Calculate the Demand. Shrink to 2 Var
        demand = (rangeDemand * Math.random()) + demandMinimum;
        if (adCounter != 0) {
            demand *= 2;
            adCounter -= 1;
            System.out.println("Ad Campaign Running! [" + adCounter + "] Uses Remaining");
        }
        String formattedDemand = String.format("%.3f", demand);
        System.out.println("Demand: " + formattedDemand);
    }

    public void salesInputPercentage() {
        System.out.println( "--- |Stocking| ---");
        System.out.println("What Decimal of your inventory would you like to input? (0-100)");
        double inputPercentage = s.nextDouble(); // Potentially make seperate input that takes integers 0-100. Also Could Make this optional.
        System.out.println("Input percent: " + inputPercentage + "%");
        if (inputPercentage > 100 || inputPercentage < 0) {
            invalidcommand();
        } else {
            inputPercentage *= 0.01;
            s.nextLine(); // No Clue what this does but code doesn't work without it
            int calculateInputtedAmt = (int) Math.round(inputPercentage * inventoryCount);
            System.out.println("Inputted Inventory: " + calculateInputtedAmt);
            inventoryCount -= calculateInputtedAmt; // Pick up Here
            inputtedAmt = calculateInputtedAmt; // Calculated stores the change, but keeps it until we no longer need to refrence
            storeInventory += inputtedAmt;
            System.out.println("Store Inventory: " + storeInventory);
            System.out.println("(+ " + inputtedAmt + ")");
            System.out.println("--- |Stocking| ---");
            System.out.println();
        }
    }

    boolean startUpgrades = false;
    int mcChickenUpgCost = 50;
    int storeUpgCost = 35;
    int demandUpgCost = 200;
    int adUpgCost = 500;
    int adMax = 3;
    public void upgrades() {
        System.out.println( "--- |Upgrades| ---");
        if (!startUpgrades) {
            System.out.println( "This is the upgrades menu!, Speed up various elements to maximize your income!");
            startUpgrades = true;
        } else {

            boolean demandUpgCondition = demandMinimum> 3 && mcChickenValue > 2; // Variable that stores conditions for the Reduce Price Purchase. Based on Sales of B (Inc Demand)
            boolean adUpgCondition = mcChickenValue <= 2.25 && adCounter == 0; // Variables storing conditions for Ad Upg. Based on if C ever sold & No current Ads.

            System.out.println("Input your Selected Upgrade with the corresponding letter");
            System.out.println( "Production Upgrades: [A| Invest in McChicken Factories, increasing production rate by 50% ($ " + mcChickenUpgCost + ")");
            System.out.println("[B| Build a new Store, Increasing Demand by 30% ($"  + storeUpgCost + ")");
            if (demandUpgCondition) {
                System.out.println("[C| Reduce the McChicken price by 25c. Increasing demand's potential range by 3 ($"  + demandUpgCost + ")");
            }
            if (adUpgCondition) {
                System.out.println("[D| Run a Small Ad Campaign. Doubling demand for next [" + adMax + "] Cash-Ins ($"  + adUpgCost + ")");
            }
            String upgradeResponse = s.nextLine();

            if (upgradeResponse.equals("A")) {
                System.out.println( "A. [Factory Investment] ");
                if (currency < mcChickenUpgCost){
                    System.out.println( "You CANNOT Afford This Item");
                } else {
                    System.out.println( "Bought Factory Upgrade");
                    productRate *= 1.5;
                    System.out.println( "Production Rate: " + productRate + " McChickens Per Second" );
                    currency -= mcChickenUpgCost;
                    mcChickenUpgCost = (int) (mcChickenUpgCost* 1.4) + 10;
                }
            } else if (upgradeResponse.equals("B")) {
                System.out.println( "B. [New Store] ");
                if (currency < storeUpgCost){
                    System.out.println( "You CANNOT Afford This Item");
                } else {
                    System.out.println( "Bought Store Upgrade");
                    demandMinimum *= 1.3;
                    String formattedDemandEquation = String.format("%.3f", (demandMinimum +( 0.5*rangeDemand)));
                    System.out.println( "Demand: " + formattedDemandEquation + " ± " + rangeDemand );
                    currency -= storeUpgCost;
                    storeUpgCost = (int) (storeUpgCost* 1.3) + 5;

                }
            } else if (upgradeResponse.equals("C") && demandUpgCondition) {
                System.out.println( "C. [Reduce prices] ");
                if (currency < demandUpgCost){
                    System.out.println( "You CANNOT Afford This Item");
                } else if (mcChickenValue <= 2.00) {
                    System.out.println( "MAXED OUT UPGRADE!");
                } else if (rangeDemand + 3 > demandMinimum) {
                    System.out.println( "MINIMUM DEMAND TOO LOW");
                } else {
                    System.out.println( "Bought Demand Upgrade");
                    mcChickenValue -= 0.25;
                    rangeDemand += 3;
                    String formattedDemandEquation = String.format("%.3f", (demandMinimum +( 0.5*rangeDemand)));
                    System.out.println( "Demand: " + formattedDemandEquation + " ± " + rangeDemand );
                    currency -= demandUpgCost;
                    demandUpgCost = (int) (demandUpgCost* 1.75) + 25;

                }
            } else if (upgradeResponse.equalsIgnoreCase("D") && adUpgCondition) {
                System.out.println( "D. [Ad Campaign] ");
                if (currency < adUpgCost){
                    System.out.println( "You CANNOT Afford This Item");
                } else {
                    adCounter = adMax;
                    adMax += 1; // Max For Ads. This way each time u buy the length increases
                    System.out.println( "Ad Campaign Begun! It'll apply at the Sales Menu when you collect your money.");
                    currency -= adUpgCost;
                    adUpgCost = (int) (adUpgCost* 2.75) + 45;
                }

            } else {
                invalidcommand();
            }
        }
        System.out.println( "--- |Upgrades| ---");
        System.out.println( "");
        showMenu();
    }
}