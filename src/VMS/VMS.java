package VMS;
import campaign.Campaign;
import campaign.CampaignStatus;
import notification.Notification;
import notification.NotificationType;
import users.User;

import java.time.LocalDateTime;
import java.util.ArrayList;


public class VMS {
    public static ArrayList<Campaign> campaigns;
    public static  ArrayList<User> users;
    private static VMS instance;

    public static VMS getInstance(){
        if (instance == null){
            instance = new VMS(campaigns, users);
        }
        return instance;
    }

    public VMS(ArrayList<Campaign> campaigns, ArrayList<User> users) {
        this.campaigns = campaigns;
        this.users = users;
        instance = this; ///
    }

    public ArrayList<Campaign> getCampaigns() {
        return campaigns;
    }

    public User getUserByEmail(String email){
        for(User u:users)
            if((u.getEmail()).equals(email))
                return u;
        return null;
    }
    public User getUserById(Integer id){
        for(User u:users)
            if((u.getId()) == id)
                return u;
        return null;
    }

    public Campaign getCampaign(Integer id){
        for(Campaign c : campaigns){
            if(((Integer) c.getId()).equals(id))
                return c;
        }
        return null;
    }

    public void addCampaign(Campaign campaign){
        campaigns.add(campaign);
    }
    public void updateCampaign(Integer id, Campaign campaign, LocalDateTime editTime) {

        Campaign oldCampaign = getCampaign(id);
        int distributedVouchers = oldCampaign.getTotalVocuhers() - oldCampaign.getAvailableVouchers();

        if (oldCampaign.getStatus() == CampaignStatus.NEW) {
            if (distributedVouchers <= campaign.getTotalVocuhers()) {
                oldCampaign.setTotalVocuhers(campaign.getTotalVocuhers());
                oldCampaign.setBeginDate(campaign.getBeginDate());
                oldCampaign.setFinalDate(campaign.getFinalDate());
                oldCampaign.changeStatus(campaign.getStatus());
                oldCampaign.setName(campaign.getName());
                oldCampaign.setDescription(campaign.getDescription());

                oldCampaign.setAvailableVouchers(oldCampaign.getTotalVocuhers() - distributedVouchers);
                oldCampaign.notifyAllObservers(new Notification(NotificationType.EDIT, editTime, oldCampaign.getId()));
            }
        } else if (oldCampaign.getStatus() == CampaignStatus.STARTED) {
            if (distributedVouchers <= campaign.getTotalVocuhers()) {
                oldCampaign.setTotalVocuhers(campaign.getTotalVocuhers());
                oldCampaign.setFinalDate(campaign.getFinalDate());

                oldCampaign.setAvailableVouchers(oldCampaign.getTotalVocuhers() - distributedVouchers);
                oldCampaign.notifyAllObservers(new Notification(NotificationType.EDIT, editTime, oldCampaign.getId()));
            }
        }
    }

    public void cancelCampaign(Integer id, LocalDateTime cancelTime){
            Campaign camp = getCampaign(id);
            if(camp.getStatus() == CampaignStatus.NEW || camp.getStatus() == CampaignStatus.STARTED) {
                camp.changeStatus(CampaignStatus.CANCELLED);
                camp.notifyAllObservers(new Notification(NotificationType.CANCEL,cancelTime, id));
            }
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void addUser(User user){
        users.add(user);
    }
}
