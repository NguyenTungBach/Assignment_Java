import view.OrderView;
import view.OrderViewImplement;

public class MainThread {
    public static void main(String[] args) {
        OrderView orderView = new OrderViewImplement();
        orderView.generateMenu();
    }
}
