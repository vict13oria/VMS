package voucher;

import java.util.Date;

public class VoucherFactory {
    private static VoucherFactory instance;
    private VoucherFactory(){

    }
    public static VoucherFactory getInstance() {
        if (instance == null){
            instance = new VoucherFactory();
        }
        return instance;
    }
    public Voucher createVoucher(VoucherType type, String code, String email, int campaignId, float value) {
        switch (type) {
            case GIFTVOUCHER:
                return new GiftVoucher(code, email, campaignId, value);
            case LOYALTYVOUCHER:
                return new LoyaltyVoucher(code, email, campaignId, value);
            default:
                return null;
        }
    }

}
