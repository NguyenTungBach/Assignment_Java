package controller;

import entity.Order;
import model.OrderModel;
import model.OrderModelImplement;
import util.DateTimeUtil;

import java.text.NumberFormat;
import java.util.*;

public class OrderControllerImplement implements OrderController {

    Locale locale = new Locale("vi", "VN");
    NumberFormat format = NumberFormat.getCurrencyInstance(locale);

    private OrderModel orderModel;
    private Scanner scanner;

    public OrderControllerImplement() {
        this.orderModel = new OrderModelImplement();
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void create() {
        System.out.println("Create Order Shop");
//        Shop shop = new Shop(id,user,product, totalPrice,status );
        System.out.println("Enter id Order, id Order lenght <= 10: ");
        String id;
        while (true){
            id = scanner.nextLine();
            if (id.equals("")){
                System.out.println("Please Enter id Order");
            } else if (id.length() > 10){
                System.out.println("id Order <= 10, Please Enter Again");
            } else {
                break;
            }
        }
//        shop.setId(id);

        System.out.println("Enter user Order: ");
        String user;
        while (true){
            user = scanner.nextLine();
            if (user.equals("")){
                System.out.println("Please Enter User Order");
            } else {
                break;
            }
        }
//        shop.setUser(user);

        System.out.println("Enter product Order: ");
        String product;
        while (true){
            product = scanner.nextLine();
            if (product.equals("")){
                System.out.println("Please Enter product Order");
            } else  {
                break;
            }
        }
//        shop.setProduct(product);

        System.out.println("Enter totalPrice Order, totalPrice is not allow <= 0 : ");
        int totalPrice;
        while (true){
            totalPrice = scanner.nextInt();
            if (totalPrice <= 0){
                System.out.println("totalPrice can not <= 0 , Please Enter Again");
            } else {
                break;
            }
        }
        scanner.nextLine();
//        shop.setTotalPrice(totalPrice);

//        System.out.println("Enter Date Order with format yyyy-mm-DD, for example: 2000-06-27 ");
//        String strDate = scanner.nextLine();
//        Date date = DateTimeUtil.parseDateString(strDate);

//        shop.setCreateAt(Calendar.getInstance().getTime());

        System.out.println("Status Date Order: 0. Deleted, 1. Paid, 2. UnPaid");
        int status ;
        while (true){
            status = scanner.nextInt();
            if (status >2 || status < 0){
                System.out.println("Enter Wrong, Again");
            } else {
                break;
            }
        }
        scanner.nextLine();
//        shop.setStatus(status);

        Order order = new Order(id,user,product,totalPrice,status);

        if (orderModel.save(order)){
            System.out.println("Action success");
        }else {
            System.out.println("Action failse");
        }
    }

    @Override
    public void show() {
        System.out.println("Show list Order Shop");
        System.out.println("\n---------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%5s%8s%5s | %1s%21s%14s | %5s%30s%15s | %8s%10s%7s | %5s%15s%5s | %5s%10s%5s\n",
                "","Id", "",
                "","User", "",
                "","Product", "",
                "","Total Price", "",
                "","Create At", "",
                "","Status", "");
        List<Order> list = orderModel.findAll();
        for (int i = 0; i < list.size(); i++) {
            Order order = list.get(i);
            System.out.println(order.toString());
        }
        System.out.println("\n---------------------------------------------------------------------------------------------------------------------------------");
    }

    @Override
    public void search() {
        System.out.println("Search Order by ID");
        System.out.println("Enter id");
        String id = scanner.nextLine();
        Order order = orderModel.findById(id);
        if (order == null){
            System.out.println("Not found");
        } else {
            System.out.println("Found");
            System.out.println("\n---------------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("%5s%8s%5s | %1s%21s%14s | %5s%30s%15s | %8s%10s%7s | %5s%15s%5s | %5s%10s%5s\n",
                    "","Id", "",
                    "","User", "",
                    "","Product", "",
                    "","Total Price", "",
                    "","Create At", "",
                    "","Status", "");
            System.out.println(order.toString());
            System.out.println("\n---------------------------------------------------------------------------------------------------------------------------------");
        }

    }

    @Override
    public void revenue() {
        System.out.println("Search Order by Time");
        System.out.println("Enter Day Start by format yyyy-MM-dd, for example 2021-06-01");
        String dateStart = scanner.nextLine();
        System.out.println("Enter Day End by format yyyy-MM-dd, for example 2021-06-01");
        String dateEnd = scanner.nextLine();
        int revenueTotalPrice = 0;
        List<Order> list = orderModel.findByTime(DateTimeUtil.parseDateString(dateStart), DateTimeUtil.parseDateString(dateEnd) );
        if (list == null){
            System.out.println("Have no finished order from time " + dateStart + "  to " +dateEnd +"\n" );
        } else {
            System.out.printf("%5s%8s%5s | %1s%21s%14s | %5s%30s%15s | %8s%10s%7s | %5s%15s%5s | %5s%10s%5s\n",
                    "","Id", "",
                    "","User", "",
                    "","Product", "",
                    "","Total Price", "",
                    "","Create At", "",
                    "","Status", "");
            for (int i = 0; i < list.size(); i++) {
                Order orderTime = list.get(i);
                System.out.println(orderTime.toString());
                revenueTotalPrice += list.get(i).getTotalPrice();
            }
            System.out.println("Total money = " + format.format(revenueTotalPrice) +"\n" );
        }

    }
}
