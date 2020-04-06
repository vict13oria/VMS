package users;

import campaign.Campaign;
import notification.Notification;
import voucher.Voucher;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

public class User {
    private int id;
    private int counterId = 0;
    public  String name;
    private String email;
    private String password;
    private UserType type;
    private ArrayList<Notification> notifications;
    private UserVoucherMap vouchers;

    public User(int id, String name, String password, String email, UserType type){
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.type = type;
        this.notifications = new ArrayList<>();
        this.vouchers = new UserVoucherMap();
    }

    public void receiveVoucher(Voucher v){
        this.vouchers.addVoucher(v);

    }
    public ArrayList<Integer> getCampaignVouchers(int id){
         ArrayList<Voucher> aux = vouchers.get(id);
         ArrayList<Integer> codes = new ArrayList<>();
         for(Voucher v : aux)
             codes.add(v.getId());
         return codes;

    }
    public void receiveNotification(Notification notification){
            notifications.add(notification);
       // System.out.println("Userul " + name + " a primit notificare '" + notification + "'");
    }

    public String getEmail() {

        return email;
    }

    public int getId() {
        return id;
    }

    public UserType getType() {
        return type;
    }
    public ArrayList<Voucher> getVouchers(){
        ArrayList<Voucher> aux = new ArrayList<>();

        for (Map.Entry<Integer, ArrayList<Voucher>> entry : vouchers.entrySet()) {
            ArrayList<Voucher>  values = entry.getValue();
            aux.addAll(values);
        }
        return aux;
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public String toString() {
        return "[" + id + ";" + name + ";" + ";" + password + ";" + type +  "]";
    }
}
