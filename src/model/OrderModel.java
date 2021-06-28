package model;

import entity.Order;

import java.util.Date;
import java.util.List;


public interface OrderModel {
    boolean save(Order obj);
    List<Order> findAll();
    List<Order> findByTime(Date dateStart, Date dateEnd);
    Order findById(String id);
//    phát triển sau
//    boolean update(String id, Shop updateObj);
//    boolean delete(String id);
}
