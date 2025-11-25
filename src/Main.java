import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        System.out.println( "--Begin Game? (Y/N)--");
        String beginResponse = s.nextLine();

        if ( beginResponse.equals("Y")) {
            int productRate = 0; // How much it increases every wait
            double waitPerProduct = 0.2; // How long each wait is
            int productBalance = 0; // Balance of money :D

            ProductProduction gameMoney = new ProductProduction(productRate,waitPerProduct);

            gameMoney.showMenu();
        }


    }
}