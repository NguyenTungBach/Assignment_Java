package model;

import entity.Order;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class OrderModelImplement implements OrderModel {
    private List<Order> list;

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
        list.add(obj);
        return true;
    }

    @Override
    public List<Order> findAll() {
        return list;
    }

    @Override
    public List<Order> findByTime(Date dateStart, Date dateEnd) {
        List<Order> listDistance = new ArrayList<>();
        int checkNull = 0;
        for (int i = 0; i < list.size(); i++) {
            int checkStart = list.get(i).getCreateAt().compareTo(dateStart);
            int checkEnd = list.get(i).getCreateAt().compareTo(dateEnd);
//            System.out.println("CheckStart = "+ checkStart);
//            System.out.println("CheckEnd = " + checkEnd);
            int checkStatus = list.get(i).getStatus();
            if (checkStart >= 0 && checkEnd <= 0 && checkStatus == 1){
                Order order = list.get(i);
                listDistance.add(order);
                checkNull ++;
            }
        }
        if (checkNull == 0){
            listDistance = null;
        }
        return listDistance;
    }

    @Override
    public Order findById(String id) {
        Order order = null;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(id)){
                order = list.get(i);
            }
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
        existOrder.setUser(updateObj.getUser());
        existOrder.setProduct(updateObj.getProduct());
        existOrder.setTotalPrice(updateObj.getTotalPrice());
        existOrder.setUpdateAt(Calendar.getInstance().getTime());
        existOrder.setStatus(updateObj.getStatus());
        return true;
    }

    @Override
    public boolean delete(String id) {
        Order existOrder = findById(id);
        if (existOrder == null){
            return false;
        }
        list.remove(existOrder);
        return false;
    }
}
