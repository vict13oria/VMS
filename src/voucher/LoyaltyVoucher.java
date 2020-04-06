package voucher;

import java.util.Date;

public class LoyaltyVoucher extends Voucher {
    private float availableDiscount;

    public LoyaltyVoucher(String code, String email, int campaignId, float value){
        super(code, email, campaignId);
        this.availableDiscount = value;
    }

    public double getAvailableDiscount() {

        return availableDiscount;
    }

    public void setAvailableDiscount(float availableDiscount) {

        this.availableDiscount = availableDiscount;
    }
}

