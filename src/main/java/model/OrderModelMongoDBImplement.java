package model;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCommandException;
import com.mongodb.MongoException;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import entity.Order;
import org.bson.Document;
import org.slf4j.LoggerFactory;
import util.ConnectionMongoDB;
import util.DateTimeUtil;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class OrderModelMongoDBImplement implements OrderModel{
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
        // Tạo phiên kết nối tới Mongodb
        ConnectionString ccn = ConnectionMongoDB.getConnection();
        if (ccn == null){
            return false;
        }
        //tắt log mongo java drive
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger rootLogger = loggerContext.getLogger("org.mongodb.driver");
        rootLogger.setLevel(Level.OFF);
        // Tạo phiên kết nối tới Database
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(ccn)
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("Project_Led_Lights");
        // Tạo phiên kết nối tới Collection
        MongoCollection<Document> ordersCollection = database.getCollection("orders");
        /**
         * Insert new
         */
        String id = obj.getId();
        String user = obj.getUser();
        String product = obj.getProduct();
        int totalPrice = obj.getTotalPrice();
        int status = obj.getStatus();
        Order order = new Order(id,user,product,totalPrice,status);
        ordersCollection.insertOne(OrderConvertDocument(order));// chèn vào một
        return true;
    }

    @Override
    public List<Order> findAll() {
        List<Order> listAll = new ArrayList<>();
        // Tạo phiên kết nối tới Mongodb
        ConnectionString ccn = ConnectionMongoDB.getConnection();
        if (ccn == null){
            throw new MongoException("Can't open connection!");
        }
        //tắt log mongo java drive
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger rootLogger = loggerContext.getLogger("org.mongodb.driver");
        rootLogger.setLevel(Level.OFF);
        // Tạo phiên kết nối tới Database
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(ccn)
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("Project_Led_Lights");
        /**
         * Trường hợp muốn tạo dữ liệu mẫu
         * Mở cái này (P1)
         *         try {
         *             database.createCollection("orders");
         *             System.out.println("Collection created successfully");
         *         }catch (MongoCommandException e){
         *             database.getCollection("orders").drop();
         *             database.createCollection("orders");
         *         }
         */
        // Tạo phiên kết nối tới Collection
        MongoCollection<Document> ordersCollection = database.getCollection("orders");
        /**
         * Mở cái này (P2)
         *         ordersCollection.insertMany(ListOrderConvertDocument(list));// chèn vào nhiều
         */
        /**
         * Find All
         */
        MongoCursor <Document> mongoCursorAgain = ordersCollection.find().iterator();
        List <Document> documentListAgain = ListMongoCursorConvertDocument(mongoCursorAgain);
        for (int i = 0; i < documentListAgain.size(); i++) {
            Order order = DocumentConvertOrder(documentListAgain.get(i));
            listAll.add(order);
        }
        return listAll;
    }

    @Override
    public List<Order> findByTime(Date dateStart, Date dateEnd) {
        List<Order> listDistance = new ArrayList<>();
        int checkNull = 0;
        // Tạo phiên kết nối tới Mongodb
        ConnectionString ccn = ConnectionMongoDB.getConnection();
        if (ccn == null){
            throw new MongoException("Can't open connection!");
        }
        //tắt log mongo java drive
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger rootLogger = loggerContext.getLogger("org.mongodb.driver");
        rootLogger.setLevel(Level.OFF);
        // Tạo phiên kết nối tới Database
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(ccn)
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("Project_Led_Lights");
        // Tạo phiên kết nối tới Collection
        MongoCollection<Document> ordersCollection = database.getCollection("orders");
        /**
         * Filters theo khoảng thời gian
         */
        FindIterable<Document> iterDoc = ordersCollection.find(Filters.and(
                Filters.gte("createAt",dateStart),
                Filters.lte("createAt",dateEnd)));

        MongoCursor <Document> mongoCursor = iterDoc.iterator();
        List <Document> documentList = ListMongoCursorConvertDocument(mongoCursor);
        for (int i = 0; i < documentList.size(); i++) {
            Order order = DocumentConvertOrder(documentList.get(i));
            listDistance.add(order);
            checkNull++;
        }
        if (checkNull == 0){
            listDistance = null;
        }
        return listDistance;
    }

    @Override
    public Order findById(String id) {
        Order order = null;
        // Tạo phiên kết nối tới Mongodb
        ConnectionString ccn = ConnectionMongoDB.getConnection();
        if (ccn == null){
            throw new MongoException("Can't open connection!");
        }
        //tắt log mongo java drive
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger rootLogger = loggerContext.getLogger("org.mongodb.driver");
        rootLogger.setLevel(Level.OFF);
        // Tạo phiên kết nối tới Database
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(ccn)
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("Project_Led_Lights");
        // Tạo phiên kết nối tới Collection
        MongoCollection<Document> ordersCollection = database.getCollection("orders");
        /**
         * Filters theo id
         */
        FindIterable<Document> iterDoc = ordersCollection.find(Filters.eq("id", id));
        MongoCursor <Document> mongoCursor = iterDoc.iterator();
        List <Document> documentList = ListMongoCursorConvertDocument(mongoCursor);
        for (int i = 0; i < documentList.size(); i++) {
            order = DocumentConvertOrder(documentList.get(i));
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
        //kiểm tra có tồn tại không bằng cách tìm theo id
        Order existOrder = findById(id);
        if (existOrder == null){
            return false;
        }
        // Tạo phiên kết nối tới Mongodb
        ConnectionString ccn = ConnectionMongoDB.getConnection();
        if (ccn == null){
            return false;
        }
        //tắt log mongo java drive
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger rootLogger = loggerContext.getLogger("org.mongodb.driver");
        rootLogger.setLevel(Level.OFF);
        // Tạo phiên kết nối tới Database
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(ccn)
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("Project_Led_Lights");
        // Tạo phiên kết nối tới Collection
        MongoCollection<Document> ordersCollection = database.getCollection("orders");
        /**
         * Update theo id
         */
        Document updateDocument = new Document("user",updateObj.getUser())
                .append("product", updateObj.getProduct())
                .append("totalPrice",updateObj.getTotalPrice())
                .append("updateAt", getDateNowFormat())
                .append("status",updateObj.getStatus());
        ordersCollection.updateOne(Filters.eq("id", id),
                new Document("$set",updateDocument));
        return true;
    }

    @Override
    public boolean delete(String id) {
        //kiểm tra có tồn tại không bằng cách tìm theo id
        Order existOrder = findById(id);
        if (existOrder == null){
            return false;
        }
        // Tạo phiên kết nối tới Mongodb
        ConnectionString ccn = ConnectionMongoDB.getConnection();
        if (ccn == null){
            return false;
        }
        //tắt log mongo java drive
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger rootLogger = loggerContext.getLogger("org.mongodb.driver");
        rootLogger.setLevel(Level.OFF);
        // Tạo phiên kết nối tới Database
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(ccn)
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("Project_Led_Lights");
        // Tạo phiên kết nối tới Collection
        MongoCollection<Document> ordersCollection = database.getCollection("orders");
        /**
         * Delete theo id
         */
        ordersCollection.deleteOne(Filters.eq("id",id));
        return true;
    }

    private static Document OrderConvertDocument (Order order){
        return new Document("id",order.getId())
                .append("user",order.getUser())
                .append("product",order.getProduct())
                .append("totalPrice",order.getTotalPrice())
                .append("createAt",order.getCreateAt())
                .append("updateAt",order.getUpdateAt())
                .append("status",order.getStatus());
    }

    private static List<Document> ListOrderConvertDocument(List<Order> order){
        List <Document> documentList = new ArrayList<>();
        for (int i = 0; i < order.size(); i++) {
            Document document = OrderConvertDocument(order.get(i));
            documentList.add(document);
        }
        return documentList;
    }

    ////////////////////
    //Chuyển đổi list mongoCursor thành list Document

    private static List<Document> ListMongoCursorConvertDocument (MongoCursor<Document> mongoCursor){
        List <Document> documentList = new ArrayList<>();
        while (mongoCursor.hasNext()){
            Document remakeDocument = mongoCursor.next();
            documentList.add(remakeDocument);
        }
        return documentList;
    }

    ///////////////////
    private static Order DocumentConvertOrder (Document document){
        String id = document.getString("id");
        String user = document.getString("user");
        String product = document.getString("product");
        int totalPrice = document.getInteger("totalPrice");
        Date createAt = document.getDate("createAt");
        Date updateAt = document.getDate("updateAt");
        int status = document.getInteger("status");
        return new Order(id, user, product, totalPrice, createAt, updateAt,status);
    }

//    private static List<Order> ListDocumentConvertOrder (List<Document> documentList){
//        List <Order> orderList = new ArrayList<>();
//        for (int i = 0; i < documentList.size(); i++) {
//            Order order = DocumentConvertOrder(documentList.get(i));
//            orderList.add(order);
//        }
//        return orderList;
//    }
    private  Date getDateNowFormat(){
        Date dateUnFormat = Calendar.getInstance().getTime();
        String dateFormat = DateTimeUtil.formatDateToString(dateUnFormat); // chuyển thành String để lấy format
        return  DateTimeUtil.parseDateString(dateFormat); // Sau đó chuyển lại về Date
    }

}
