package view;

import controller.OrderController;
import controller.OrderControllerImplement;

import java.util.Scanner;

public class OrderViewImplement implements OrderView {
    private OrderController orderController;
    private Scanner scanner;

    public OrderViewImplement(){
        this.orderController = new OrderControllerImplement();
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void generateMenu() {
        while (true) {
            System.out.println("Order Manager");
            System.out.println("==========================");
            System.out.println("1. Add new order");
            System.out.println("2. Search order by id");
            System.out.println("3. Revenue by time");
//            System.out.println("4. Show List Order");
            System.out.println("4. Exit");
            System.out.println("==========================");
            System.out.println("Please enter your choice (0-3)");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice){
                case 1:
                    orderController.create();
                    break;
                case 2:
                    orderController.search();
                    break;
                case 3:
                    orderController.revenue();
                    break;
//                case 4:
//                    orderController.show();
//                    break;
            }
            if (choice == 4){
                System.out.println("Program Manage Shop Finnish\n");
                break;
            }
        }
    }
}
