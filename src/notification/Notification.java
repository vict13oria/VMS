package notification;

import voucher.Voucher;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class Notification {
    private LocalDateTime sentDate;
    private NotificationType type;
    private int campaignId;
    private ArrayList<Integer> receivedVouchers;

    public Notification(NotificationType type, LocalDateTime sentDate, int campaignId) {
        this.type = type;
        this.sentDate = sentDate;
        this.campaignId = campaignId;
       // this.receivedVouchers = received;
    }

    public Notification(Notification n){
        this.type = n.getType();
        this.sentDate = n.getSentDate();
        this.campaignId = n.getCampaignId();
    }

    public LocalDateTime getSentDate() {
        return sentDate;
    }


    public NotificationType getType() {
        return type;
    }

    public int getCampaignId() {
        return campaignId;
    }

    public void setReceivedVouchers(ArrayList<Integer> receivedVouchers) {
        this.receivedVouchers = receivedVouchers;
    }

    @Override
    public String toString() {
        return
                "sentDate=" + sentDate +
                ", type=" + type +
                ", campaignId=" + campaignId +
                ", receivedVouchers=" + receivedVouchers ;
    }
}
