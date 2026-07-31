
import api.SupermarketAPI;
import gui.LoginPage;

public class Main {
    public static void main(String[] args) {
        SupermarketAPI supermarketAPI = new SupermarketAPI();
        new LoginPage(supermarketAPI);
    }
}
