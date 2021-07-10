package controller;

import entity.Order;
import model.OrderModel;
import model.OrderModelImplement;
import model.OrderModelMongoDBImplement;
import model.OrderModelMySQLImplement;
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

// Validate Start
    // check Id Start
    private boolean checkInputId(String id){
        if (id.equals("")){
            System.out.println("Please Enter id Order");
            return false;
        } else if (id.length() > 10){
            System.out.println("id Order <= 10, Please Enter Again");
            return false;
        } else if (orderModel.checkId(id)){
            System.out.println("This id existed, Please Enter Again");
            return false;
        }
        return true;
    }
    // check Id End

    // check User Start
    private boolean checkInputUser(String user){
        if (user.equals("")){
            System.out.println("Please Enter User Order");
            return false;
        }
        return true;
    }
    // check User End

    // check Product Start
    private boolean checkInputProduct(String product){
        if (product.equals("")){
            System.out.println("Please Enter product Order");
            return false;
        }
        return true;
    }
    // check Product End

    // check int Start
    private boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false; // Nhập chữ trả về false
        }
        return true;
    }
    // check int End

    // check Input Price Start
    private boolean checkInputPrice(int totalPrice){
        if (totalPrice <= 0){
            return false;
        }
        return true;
    }

    private boolean checkInputPriceInt(String totalPriceInt){
        if (totalPriceInt.equals("")){
            System.out.println("Please Enter totalPrice");
            return false;
        }
        return true;
    }
    // check Input Price End

    //  check Input Status Start
    private boolean checkInputStatus(int status){
        if (status >2 || status < 0){
            return false;
        }
        return true;
    }

    private boolean checkInputStatusInt(String statusInt){
        if (statusInt.equals("")){
            System.out.println("Please Enter 0. Deleted, 1. Paid, 2. UnPaid");
            return false;
        }
        return true;
    }
    //  check Input Status End
// Validate End

    @Override
    public void create() {
        System.out.println("Create Order Shop");
        System.out.println("Enter id Order, id Order lenght <= 10: ");
        String id;
        while (true){
            id = scanner.nextLine();
            if (checkInputId(id)){
                break;
            }
        }

        System.out.println("Enter user Order: ");
        String user;
        while (true){
            user = scanner.nextLine();
            if (checkInputUser(user)){
                break;
            }
        }

        System.out.println("Enter product Order: ");
        String product;
        while (true){
            product = scanner.nextLine();
            if (checkInputProduct(product)){
                break;
            }
        }

        System.out.println("Enter totalPrice Order");
//        int totalPrice;
//        do {
            System.out.println("TotalPrice is not allow <= 0 : ");
//            while (!scanner.hasNextInt()){
//                System.out.println("You are not Enter Price Order or this is not a number");
//                scanner.next();
//            }
//            totalPrice = scanner.nextInt();
//        } while (checkInputPrice(totalPrice));

        int totalPriceInt;
        while (true){
            String totalPrice = scanner.nextLine();
            if (checkInputPriceInt(totalPrice)){
                if (isInteger(totalPrice) && checkInputPrice(Integer.valueOf(totalPrice))){
                    totalPriceInt = Integer.valueOf(totalPrice);
                    System.out.println("OK\n");
                    break;
                }
                else if (isInteger(totalPrice) && !checkInputPrice(Integer.valueOf(totalPrice))) {
                    System.out.println("TotalPrice is not allow <= 0, Please Enter Again");
                } else {
                    System.out.println("This is not a number");
                }
            }
        }

        System.out.println("Status Date Order: ");
        int statusInt ;
//        do {
            System.out.println("Enter 0. Deleted, 1. Paid, 2. UnPaid ");
//            while (!scanner.hasNextInt()){
//                System.out.println("Enter Wrong, Again");
//                scanner.next();
//            }
//            status = scanner.nextInt();
//        } while (checkInputStatus(status));
//        scanner.nextLine();
        while (true){
            String status = scanner.nextLine();
            if (checkInputStatusInt(status)){
                if (isInteger(status) && checkInputStatus(Integer.valueOf(status))){
                    statusInt = Integer.valueOf(status);
                    System.out.println("OK\n");
                    break;
                }
                else if (isInteger(status) && !checkInputStatus(Integer.valueOf(status)) ) {
                    System.out.println("Enter Wrong, 0. Deleted, 1. Paid, 2. UnPaid");
                } else {
                    System.out.println("This is not a number");
                }
            }
        }

        Order order = new Order(id,user,product,totalPriceInt,statusInt);

        if (orderModel.save(order)){
            System.out.println("Action success");
            show();
        }else {
            System.out.println("Action failse");
        }
    }

    @Override
    public void show() {
        System.out.println("Show list Order Shop");
        System.out.println("\n---------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%5s%8s%5s | %1s%21s%14s | %5s%30s%15s | %8s%10s%7s | %5s%15s%5s | %5s%15s%5s | %5s%10s%5s\n",
                "","Id", "",
                "","User", "",
                "","Product", "",
                "","Total Price", "",
                "","Create At", "",
                "","Update At", "",
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
            System.out.printf("%5s%8s%5s | %1s%21s%14s | %5s%30s%15s | %8s%10s%7s | %5s%15s%5s | %5s%15s%5s | %5s%10s%5s\n",
                    "","Id", "",
                    "","User", "",
                    "","Product", "",
                    "","Total Price", "",
                    "","Create At", "",
                    "","Update At", "",
                    "","Status", "");
            System.out.println(order.toString());
            System.out.println("\n---------------------------------------------------------------------------------------------------------------------------------");
        }
    }

    @Override
    public void update() {
        System.out.println("Update Order by ID");
        System.out.println("Enter id");
        String id = scanner.nextLine();
        Order order = orderModel.findById(id);
        if (order == null){
            System.out.println("Not found");
        } else {
            System.out.println("Found");
            System.out.println("\n---------------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("%5s%8s%5s | %1s%21s%14s | %5s%30s%15s | %8s%10s%7s | %5s%15s%5s | %5s%15s%5s | %5s%10s%5s\n",
                    "","Id", "",
                    "","User", "",
                    "","Product", "",
                    "","Total Price", "",
                    "","Create At", "",
                    "","Update At", "",
                    "","Status", "");
            System.out.println(order.toString());
            System.out.println("\n---------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("Enter user Order: ");
            String user;
            while (true){
                user = scanner.nextLine();
                if (checkInputUser(user)){
                    break;
                }
            }

            System.out.println("Enter product Order: ");
            String product;
            while (true){
                product = scanner.nextLine();
                if (checkInputProduct(product)){
                    break;
                }
            }

            System.out.println("Enter totalPrice Order, TotalPrice > 0 ");
            int totalPriceInt;
            while (true){
                String totalPrice = scanner.nextLine();
                if (checkInputPriceInt(totalPrice)){
                    if (isInteger(totalPrice) && Integer.valueOf(totalPrice) >0){
                        totalPriceInt = Integer.valueOf(totalPrice);
                        System.out.println("OK\n");
                        break;
                    }
                    else if (isInteger(totalPrice) && Integer.valueOf(totalPrice) <=0) {
                        System.out.println("TotalPrice is not allow <= 0, Please Enter Again");
                    } else {
                        System.out.println("This is not a number");
                    }
                }
            }

            System.out.println("Status Date Order: 0. Deleted, 1. Paid, 2. UnPaid ");
            int statusInt ;
            while (true){
                String status = scanner.nextLine();
                if (checkInputStatusInt(status)){
                    if (isInteger(status) && checkInputStatus(Integer.valueOf(status))){
                        statusInt = Integer.valueOf(status);
                        System.out.println("OK\n");
                        break;
                    }
                    else if (isInteger(status) && !checkInputStatus(Integer.valueOf(status)) ) {
                        System.out.println("Enter Wrong, 0. Deleted, 1. Paid, 2. UnPaid");
                    } else {
                        System.out.println("This is not a number");
                    }
                }
            }

            order.setUser(user);
            order.setProduct(product);
            order.setTotalPrice(totalPriceInt);
            order.setStatus(statusInt);

            if (orderModel.update(id, order)){
                System.out.println("Update Order id = " + order.getId() + " success\n" );
            } else {
                System.out.println("Update Order id = " + order.getId()  + " failse\n" );
            }
        }
    }

    @Override
    public void delete() {
        System.out.println("Delete Order by ID");
        System.out.println("Enter id");
        String id = scanner.nextLine();
        Order order = orderModel.findById(id);
        if (order == null){
            System.out.println("Not found");
        } else {
            System.out.println("Found");
            System.out.println("\n---------------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("%5s%8s%5s | %1s%21s%14s | %5s%30s%15s | %8s%10s%7s | %5s%15s%5s | %5s%15s%5s | %5s%10s%5s\n",
                    "","Id", "",
                    "","User", "",
                    "","Product", "",
                    "","Total Price", "",
                    "","Create At", "",
                    "","Update At", "",
                    "","Status", "");
            System.out.println(order.toString());
            System.out.println("\n---------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("Are you sure (y/n)");
            while (true){
                String choice = scanner.nextLine();
                if (choice.equalsIgnoreCase("y")){
                    if (orderModel.delete(id)){
                        System.out.println("Delete Order id " + order.getId() + " success\n");
                    } else {
                        System.out.println("Delete Order id " + order.getId() + " failse\n");
                    }
                    break;
                } if (choice.equalsIgnoreCase("n")){
                    System.out.println("Delete Order id " + order.getId() + " failse\n");
                    break;
                } else {
                    System.out.println("Enter Wrong, Please, Enter y/n");
                }

            }
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
