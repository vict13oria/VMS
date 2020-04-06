package campaign;

import VMS.VMS;
import notification.Notification;
import users.User;
import voucher.Voucher;
import voucher.VoucherFactory;
import voucher.VoucherStatusType;
import voucher.VoucherType;


import java.io.ObjectStreamClass;
import java.time.*;
import java.util.*;

public class Campaign {
    private int id;
    private int counterId = 0;
    private String name;
    private String description;
    private LocalDateTime finalDate;
    private LocalDateTime beginDate;
    private int totalVocuhers;
    private int availableVouchers;
    private CampaignStatus status;
    private CampaignVoucherMap dictionary;
    private ArrayList<User> users;
    private VoucherFactory factory;
    Random rand;

    public Campaign(int id, String name, String description, LocalDateTime beginDate, LocalDateTime finalDate,
                    LocalDateTime currentDate, int availableVouchers){
        //counterId++;
        this.id = id;
        this.dictionary = new CampaignVoucherMap();
        this.factory = VoucherFactory.getInstance();
        rand = new Random();
        users = new ArrayList<>();
        this.name = name;
        this.description = description;
        this.beginDate = beginDate;
        this.finalDate = finalDate;
        this.availableVouchers = availableVouchers;
        this.totalVocuhers = availableVouchers;
        setStatus((currentDate));
    }

    public CampaignStatus getStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFinalDate(LocalDateTime finalDate) {
        this.finalDate = finalDate;
    }

    public void setBeginDate(LocalDateTime beginDate) {
        this.beginDate = beginDate;
    }

    public void setTotalVocuhers(int totalVocuhers) {
        this.totalVocuhers = totalVocuhers;
    }

    public void changeStatus(CampaignStatus status) {
        this.status = status;
    }

    public void setDictionary(CampaignVoucherMap dictionary) {
        this.dictionary = dictionary;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotalVocuhers() {
        return totalVocuhers;
    }

    public int getAvailableVouchers() {
        return availableVouchers;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getFinalDate() {
        return finalDate;
    }

    public LocalDateTime getBeginDate() {
        return beginDate;
    }

    public CampaignVoucherMap getDictionary() {
        return dictionary;
    }

    public VoucherFactory getFactory() {
        return factory;
    }

    public void setAvailableVouchers(int availableVouchers) {
        this.availableVouchers = availableVouchers;
    }

    public void  setStatus(LocalDateTime currentDate){
        if(currentDate.isBefore(beginDate))
            this.status = CampaignStatus.NEW;
        else if(currentDate.isAfter(finalDate))
            this.status = CampaignStatus.EXPIRED;
         if (currentDate.isAfter(beginDate) && currentDate.isBefore(finalDate))
            this.status = CampaignStatus.STARTED;

    }

    public int getId() {

        return id;
    }

    public ArrayList<Voucher> getVouchers(){
        ArrayList<Voucher> vouchers = new ArrayList<>();

        for (Map.Entry<String, ArrayList<Voucher>> entry : dictionary.entrySet()) {
            ArrayList<Voucher>  values = entry.getValue();
            vouchers.addAll(values);
        }
        return vouchers;
    }

    public Voucher getVoucher(String code){
        ArrayList<Voucher> vouchers = new ArrayList<>();

        for (Map.Entry<String, ArrayList<Voucher>> entry : dictionary.entrySet()) {
            ArrayList<Voucher>  values = entry.getValue();
            vouchers.addAll(values);
        }
        for (Voucher v : vouchers) {
            if (v.getCode().equals(code)) {
                return v;
            }
        }
        return null;
    }
    public Voucher getVoucherbyId(int id){
        ArrayList<Voucher> vouchers = new ArrayList<>();

        for (Map.Entry<String, ArrayList<Voucher>> entry : dictionary.entrySet()) {
            ArrayList<Voucher>  values = entry.getValue();
            vouchers.addAll(values);
        }
        for (Voucher v : vouchers) {
            if (v.getId() == id) {
                return v;
            }
        }
        return null;
    }

    public User getUserbyEmail(String email){
        for(User u : users){
            if(( u.getEmail()).equals(email))
                return u;
        }
        return null;
    }
    public void generateVoucher(String email, VoucherType type, float value){
        counterId++;
        VMS vms = VMS.getInstance();
        User user = vms.getUserByEmail(email);
        String code = Integer.toString(rand.nextInt());
        Voucher v = factory.createVoucher(type, code, email, this.id, value);
        v.setId(counterId);
        dictionary.addVoucher(v);
        addObserver(user);
        User u1 = getUserbyEmail(email);
        u1.receiveVoucher(v);

    }


    private boolean isValid(LocalDateTime date){
        if(date.isAfter(beginDate) && date.isBefore(finalDate))
            return true;
        return false;
}

    public void reedemVoucher(int id, LocalDateTime date){
           Voucher v = getVoucherbyId(id);
           boolean ok = false;
           if(isValid(date)  &&  v.getStatusType() != VoucherStatusType.USED  ){
               if(this.status == CampaignStatus.STARTED)
               v.setStatusType(VoucherStatusType.USED);
           }
           else if(isValid(date)  &&  v.getStatusType() != VoucherStatusType.USED){
                if(this.status == CampaignStatus.NEW)
                v.setStatusType(VoucherStatusType.USED);
        }

           User u = VMS.getInstance().getUserByEmail(v.getEmail());

          for ( Voucher it : dictionary.get(u.getEmail())) {
             if( it.getStatusType() == VoucherStatusType.UNUSED)
                 ok = true;
          }
            if(ok)
            this.availableVouchers -= 1;
            else {
                removeObserver(u);
                this.availableVouchers -= 1;
            }
    }

    public ArrayList<User> getUsers() {

        return users;
    }

    public void addObserver(User user){
        if(!users.contains(user))
            users.add(user);
    }

    public void removeObserver(User user){
        if(users.contains(user))
            users.remove(user);
    }

    public void notifyAllObservers(Notification notification){
        ListIterator<User> iter =  users.listIterator();
        while (iter.hasNext()){
            Notification n = new Notification(notification);
            User aux = iter.next();
            n.setReceivedVouchers(aux.getCampaignVouchers(notification.getCampaignId()));
            aux.receiveNotification(n);
           // System.out.println("Userul " + aux.name + " a primit notificare '" + notification + "'");

        }
    }

    @Override
    public String toString() {
        return "Campaign{" +
                "id=" + id +
                ", counterId=" + counterId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", finalDate=" + finalDate +
                ", beginDate=" + beginDate +
                ", totalVocuhers=" + totalVocuhers +
                ", availableVouchers=" + availableVouchers +
                ", status=" + status +
                ", dictionary=" + dictionary +
                ", users=" + users +
                ", factory=" + factory +
                ", rand=" + rand +
                '}';
    }


}
