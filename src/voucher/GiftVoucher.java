package voucher;

import java.util.Date;

public class GiftVoucher extends Voucher {
    private float availabeSum;

    public GiftVoucher(String code, String email, int campaignId, float value){
        super(code, email, campaignId);
        this.availabeSum = value;
    }

    public double getAvailabeUniqueSum() {
        return availabeSum;
    }

    public void setAvailabeUniqueSum(double availabeSum) {
        availabeSum = availabeSum;
    }
}
