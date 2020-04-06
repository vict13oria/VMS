package voucher;

import java.util.Date;

public abstract class Voucher {
    private int id;
   // private static int counterId = 0;
    private String code;
    private VoucherStatusType type;
    private Date data;
    private String email;
    private int campaignId;

    public Voucher(String code, String email, int campaignId) {
        //counterId++;
        //this.id = id;
        this.code = code;
        this.type = VoucherStatusType.UNUSED;
        this.email = email;
        this.campaignId = campaignId;;
    }

    public int getCampaignId() {

        return this.campaignId;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    public String getCode() {

        return code;
    }

    @Override
    public String toString() {
        return "Voucher{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", type=" + type +
                ", email='" + email + '\'' +
                '}';
    }

    public void setStatusType(VoucherStatusType type) {

        this.type = type;
    }

    public VoucherStatusType getStatusType() {

        return type;
    }

    public void setId(int id) {
        this.id = id;
    }
}
