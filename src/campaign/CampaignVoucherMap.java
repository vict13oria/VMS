package campaign;

import voucher.Voucher;

import java.util.ArrayList;

public class CampaignVoucherMap extends ArrayMap<String, ArrayList<Voucher>> {

    public ArrayList<ArrayMapEntry<String, ArrayList<Voucher>>> list = null;

    public CampaignVoucherMap ()
    {

        list = new ArrayList<>();
    }

    boolean addVoucher(Voucher v) {

        String key = v.getEmail();
        ArrayList<Voucher> voucherList;
        if(containsKey(key)){
            voucherList = get(key);
        }
        else{
            voucherList = new ArrayList<>();
            put(key, voucherList);
        }

        if(voucherList.contains(v))
            return false;
        voucherList.add(v);
        return true;

    }
}
