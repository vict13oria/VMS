import VMS.VMS;
import campaign.Campaign;
import users.User;
import users.UserType;
import voucher.VoucherType;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        String testPath = "test/test00/input/";
        new VMS(readCampaigns(testPath + "campaigns.txt"), readUsers(testPath + "users.txt"));
        readEvents(testPath + "events.txt");
    }

    static UserType getType(String type){
        if(type.compareTo("ADMIN") == 0)
            return UserType.ADMIN;
        return UserType.GUEST;
    }
    static VoucherType getVoucherType(String type){
        if(type.compareTo("GiftVoucher") == 0)
            return VoucherType.GIFTVOUCHER;
        return VoucherType.LOYALTYVOUCHER;
    }

    static ArrayList<User> readUsers(String path){
        ArrayList<User> users = new ArrayList<>();
        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            int n = scanner.nextInt();
            for(int i=1; i<=n; i++) {
                String[] u = scanner.next().split(";");
                users.add(new User(Integer.parseInt(u[0]), u[1], u[2], u[3], getType(u[4])));
                //System.out.println("New user added: " );
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return users;
    }

    static LocalDateTime stringToLDT(String s){

        String[] date = s.split(" ")[0].split("-");
        String[] time = s.split(" ")[1].split(":");
        return LocalDateTime.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]),
                Integer.parseInt(time[0]), Integer.parseInt(time[1]));
    }

    static ArrayList<Campaign> readCampaigns(String path){
        ArrayList<Campaign> campaigns = new ArrayList<>();
        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            scanner.useDelimiter("\n");
            int n = Integer.parseInt(scanner.next());
            LocalDateTime curerentLDT = stringToLDT(scanner.next());
            for(int i=1; i<=n; i++) {
                String[] c = scanner.next().split(";");
                //System.out.println(c[3]);
                campaigns.add(new Campaign(Integer.parseInt(c[0]), c[1], c[2], stringToLDT(c[3]), stringToLDT(c[4]),
                        curerentLDT, Integer.parseInt(c[5]))); ///TODO strategy
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return campaigns;
    }

    static void readEvents(String path){
        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            scanner.useDelimiter("\n");
            LocalDateTime curerentLDT = stringToLDT(scanner.next());
            int n = scanner.nextInt();
            for(int i=1; i<=n; i++) {
                Do(scanner.next(), curerentLDT);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static void Do(String event, LocalDateTime curerentLDT){
        String[] e = event.split(";", 3);
        User u = VMS.getInstance().getUserById(Integer.parseInt(e[0]));
        switch (e[1]) {
            case "addCampaign": {
                if (u.getType() == UserType.ADMIN) {
                 //   System.out.println("addCampaign");
                    String[] args = e[2].split(";");
                    //System.out.println(c[3]);
                    VMS.getInstance().addCampaign(new Campaign(Integer.parseInt(args[0]), args[1], args[2], stringToLDT(args[3]),
                            stringToLDT(args[4]), curerentLDT, Integer.parseInt(args[5])));
                }
                break;
            }
            case "editCampaign": {
                if (u.getType() == UserType.ADMIN) {
                  //  System.out.println("editCampaign");
                    String[] args = e[2].split(";");

                    VMS.getInstance().updateCampaign(Integer.parseInt(args[0]), new Campaign(Integer.parseInt(args[0]), args[1], args[2],
                            stringToLDT(args[3]), stringToLDT(args[4]), curerentLDT, Integer.parseInt(args[5])), curerentLDT);
                }
                break;
            }
            case "cancelCampaign": {
                if (u.getType() == UserType.ADMIN) {
                  //  System.out.println("cancelCampaign");
                    String[] args = e[2].split(";");
                    VMS.getInstance().cancelCampaign(Integer.parseInt(args[0]), stringToLDT(args[1]));
                }
                break;
            }
            case "generateVoucher": {
                if (u.getType() == UserType.ADMIN) {
                  //  System.out.println("generateVoucher");
                    String[] args = e[2].split(";");
                    if(Integer.parseInt(args[0]) == 1 && args[1].equals("USER_2_MAIL")){
                        int i = 1;
                        i++;
                    }
                    VMS.getInstance().getCampaign(Integer.parseInt(args[0])).generateVoucher(args[1], getVoucherType(args[2]),
                            Integer.parseInt(args[3]));
                }
                break;
            }
            case "redeemVoucher": {
                if (u.getType() == UserType.ADMIN){
                  //  System.out.println("redeemVouchers");
                    String[] args = e[2].split(";");
                    Campaign aux = VMS.getInstance().getCampaign(Integer.parseInt(args[0]));
                    aux.reedemVoucher(aux.getVoucherbyId(Integer.parseInt(args[1])).getId(),
                            stringToLDT(args[2]));
                }
                break;
            }
            case "getVouchers": {
                if (u.getType() == UserType.GUEST) {
                  //  System.out.println("getVouchers");
                   System.out.println(u.getVouchers());

                }
            break;
           }
            case "getObservers" : {
                if (u.getType() == UserType.ADMIN) {
                   //System.out.println("getObservers");
                  ArrayList<User> obs = VMS.getInstance().getCampaign(Integer.parseInt(e[2])).getUsers();
                    System.out.println(obs);
                }
                break;
            }
            case "getNotifications" : {
                if (u.getType() == UserType.GUEST) {
                    //System.out.println("getNotifications");
                    System.out.println( u.getNotifications());
                }
                break;
            }
            //case "getVoucher" : {
                //System.out.println("getVoucher");

               // break;
           // }
            default: System.out.println("Invalit command");
                break;
        }
    }




}
