package model;

import entity.Order;
import util.ConnectionHelper;
import util.DateTimeUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class OrderModelMySQLImplement implements OrderModel{
    private List<Order> list ;
    {
        list = new ArrayList<>();
        list.add(new Order("Order001", "Mục","1 mớ rau, 2kg thịt, 1 táo", 200000,"2021-06-01",1));
        list.add(new Order("Order002", "Xiên","3 Bim, 10kg thóc, 2 bột giặt", 100000,"2021-06-02",2));
        list.add(new Order("Order003", "Tý","2 kẹo, 1 hộp sữa", 300000,"2021-06-03",0));
        list.add(new Order("Order004", "Tèo","1 thùng mì, 1 thùng rượu", 500000,"2021-06-04",1));
        list.add(new Order("Order005", "Tồ","1 rượu vang", 700000,"2021-06-05",2));
    }

    @Override
    public boolean save(Order obj) {
        try {
            Connection cnn = ConnectionHelper.getConnection();
            if (cnn == null){
                return false;
            }
            PreparedStatement preparedStatement = cnn.prepareStatement("insert into orders (id, user, product, totalPrice, createAt, updateAt, status) values (?,?,?,?,?,?,?)");
            preparedStatement.setString(1,obj.getId());
            preparedStatement.setString(2,obj.getUser());
            preparedStatement.setString(3,obj.getProduct());
            preparedStatement.setInt(4,obj.getTotalPrice());
            preparedStatement.setString(5,obj.getCreateAtString());
            preparedStatement.setString(6,obj.getUpdateAtString());
            preparedStatement.setInt(7,obj.getStatus());
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Order> findAll() {
        List<Order> listAll = new ArrayList<>();
        try {
            Connection cnn = ConnectionHelper.getConnection();
            if (cnn == null){
                throw new SQLException("Can't open connection!");
            }
            /**
             * Create dữ liệu mẫu
             * Drop if IF EXISTS orders
             * Statement statement = cnn.createStatement();
             *             String sqlSever;
             *             sqlSever = " DROP TABLE IF EXISTS orders";
             *             statement.execute(sqlSever);
             *             sqlSever = "CREATE TABLE orders ("
             *                     + "id VARCHAR(250),"
             *                     + "user VARCHAR(250),"
             *                     + "product VARCHAR(250),"
             *                     + "totalPrice INT(64),"
             *                     + "createAt Date,"
             *                     + "updateAt Date,"
             *                     + "status INT(64))";
             *             statement.execute(sqlSever);
             *
             *             for (int i = 0; i < list.size(); i++) {
             *                 PreparedStatement preparedStatement = cnn.prepareStatement("insert into orders (id, user, product, totalPrice, createAt, updateAt, status) values (?,?,?,?,?,?,?)");
             *                 preparedStatement.setString(1,list.get(i).getId());
             *                 preparedStatement.setString(2,list.get(i).getUser());
             *                 preparedStatement.setString(3,list.get(i).getProduct());
             *                 preparedStatement.setInt(4,list.get(i).getTotalPrice());
             *                 preparedStatement.setString(5,list.get(i).getCreateAtString());
             *                 preparedStatement.setString(6,list.get(i).getUpdateAtString());
             *                 preparedStatement.setInt(7,list.get(i).getStatus());
             *                 preparedStatement.execute();
             *             }
             */

            PreparedStatement preparedStatement = cnn.prepareStatement("select * from orders");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                String id = rs.getString("id");
                String user = rs.getString("user");
                String product = rs.getString("product");
                int totalPrice = rs.getInt("totalPrice");
                Date createdAt =  DateTimeUtil.parseDateString(rs.getString("createAt"));
                Date updateAt = DateTimeUtil.parseDateString(rs.getString("updateAt"));
                int status = rs.getInt("status");
                Order obj = new Order(id,user,product,totalPrice,createdAt,updateAt,status);
                listAll.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listAll;
    }

    @Override
    public List<Order> findByTime(Date dateStart, Date dateEnd) {
        List<Order> listDistance = new ArrayList<>();
        int checkNull = 0;
        try {
            Connection cnn = ConnectionHelper.getConnection();
            if (cnn == null){
                throw new SQLException("Can't open connection!");
            }
            PreparedStatement preparedStatement = cnn.prepareStatement(String.format("select * from orders where createAt between '%s' and '%s'",DateTimeUtil.formatDateToString(dateStart),DateTimeUtil.formatDateToString(dateEnd)));
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                String id = rs.getString("id");
                String user = rs.getString("user");
                String product = rs.getString("product");
                int totalPrice = rs.getInt("totalPrice");
                Date createdAt =  DateTimeUtil.parseDateString(rs.getString("createAt"));
                Date updateAt = DateTimeUtil.parseDateString(rs.getString("updateAt"));
                int status = rs.getInt("status");
                Order obj = new Order(id,user,product,totalPrice,createdAt,updateAt,status);
                listDistance.add(obj);
                checkNull++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (checkNull == 0){
            listDistance = null;
        }
        return listDistance;
    }

    @Override
    public Order findById(String id) {
        Order order = null;
        try {
            Connection cnn = ConnectionHelper.getConnection();
            if (cnn == null){
                throw new SQLException("Can't open connection!");
            }
            PreparedStatement preparedStatement = cnn.prepareStatement(String.format("select * from orders where id = '%s'",id));
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){
//                System.out.println(rs.getString("id"));
//                System.out.println(rs.getString("user"));
//                System.out.println(rs.getString("product"));
//                System.out.println(rs.getInt("totalPrice"));
//                System.out.println(rs.getString("createAt"));
//                System.out.println(rs.getString("updateAt"));
//                System.out.println(rs.getInt("status"));
                String idFind = rs.getString("id");
                String user = rs.getString("user");
                String product = rs.getString("product");
                int totalPrice = rs.getInt("totalPrice");
                Date createdAt =  DateTimeUtil.parseDateString(rs.getString("createAt"));
                Date updateAt = DateTimeUtil.parseDateString(rs.getString("updateAt"));
                int status = rs.getInt("status");
                order = new Order(idFind,user,product,totalPrice,createdAt,updateAt,status);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    @Override
    public boolean checkId(String id) {
        Order order = findById(id);
        if (order==null){
            return false;
        }
        return true;
    }

    @Override
    public boolean update(String id, Order updateObj) {
        Order existOrder = findById(id);
        if (existOrder == null){
            return false;
        }
        try {
            Connection cnn = ConnectionHelper.getConnection();
            if (cnn == null){
                throw new SQLException("Can't open connection!");
            }
            PreparedStatement preparedStatement = cnn.prepareStatement(String.format("update orders set user = '%s', product = '%s', totalPrice = %d, updateAt = '%s', status = %d where id = '%s'",
                    updateObj.getUser(), updateObj.getProduct(), updateObj.getTotalPrice(), DateTimeUtil.formatDateToString(Calendar.getInstance().getTime()), updateObj.getStatus(), id));
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        Order existOrder = findById(id);
        if (existOrder == null){
            return false;
        }
        try {
            Connection cnn = ConnectionHelper.getConnection();
            if (cnn == null){
                throw new SQLException("Can't open connection!");
            }
            PreparedStatement preparedStatement = cnn.prepareStatement(String.format("delete from orders where id = '%s'",id));
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
