package entity;

import util.DateTimeUtil;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Order {

    Locale locale = new Locale("vi", "VN");
    NumberFormat format = NumberFormat.getCurrencyInstance(locale);

    private String id;
    private String user;
    private String product;
    private int totalPrice;
    private Date createAt;
    private Date updateAt;
    private int status;

    public Order() {
    }

    public Order(String id, String user, String product, int totalPrice, int status) {
        this.id = id;
        this.user = user;
        this.product = product;
        this.totalPrice = totalPrice;
        this.createAt = getDateNowFormat();
        this.updateAt = getDateNowFormat();
        this.status = status;
    }

    public Order(String id, String user, String product, int totalPrice, String strCreateAt, int status) {
        this.id = id;
        this.user = user;
        this.product = product;
        this.totalPrice = totalPrice;
        this.createAt = DateTimeUtil.parseDateString(strCreateAt);
        this.updateAt = this.createAt;
        this.status = status;
    }

    public Order(String id, String user, String product, int totalPrice, Date createAt, Date updateAt, int status) {
        this.id = id;
        this.user = user;
        this.product = product;
        this.totalPrice = totalPrice;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.status = status;
    }

    private String getStatusName(){
        if (this.status == 0){
            return "Deleted";
        }else if (this.status == 1){
            return "Paid";
        }
        return this.status == 2 ? "Unpaid" : "";
    }

    // Ép Date now về Format ngày
    private  Date getDateNowFormat(){
        Date dateUnFormat = Calendar.getInstance().getTime();
        String dateFormat = DateTimeUtil.formatDateToString(dateUnFormat); // chuyển thành String để lấy format
        return  DateTimeUtil.parseDateString(dateFormat); // Sau đó chuyển lại về Date
    }

    @Override
    public String toString() {
        return String.format("%5s%6s%5s | %1s%21s%14s | %5s%30s%15s | %8s%10s%8s | %5s%15s%5s | %5s%15s%5s | %5s%10s%5s",
                "",id, "",
                "",user, "",
                "",product, "",
                "",format.format(totalPrice), "",
                "",getCreateAtString(), "",
                "",getUpdateAtString(), "",
                "",getStatusName(), "");
    }

    public String getCreateAtString(){
        return DateTimeUtil.formatDateToString(this.createAt);
    }
    public String getUpdateAtString(){
        return DateTimeUtil.formatDateToString(this.updateAt);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }



    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getUpdateAt() {
        return updateAt;
    }



    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
}
