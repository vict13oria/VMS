package users;

import campaign.ArrayMap;
import voucher.Voucher;

import java.util.ArrayList;

public class UserVoucherMap extends ArrayMap<Integer,  ArrayList<Voucher>> {

    boolean addVoucher(Voucher v) {
        int key = v.getCampaignId();
        ArrayList<Voucher> list;
        if(containsKey(key)){
            list = get(key);
        }
        else{
            list = new ArrayList<>();
            put(key, list);
        }
        if(list.contains(v))
            return false;
        list.add(v);
        return true;

    }
}
