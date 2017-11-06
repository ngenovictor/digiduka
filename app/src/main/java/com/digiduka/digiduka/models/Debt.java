package com.digiduka.digiduka.models;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by victor on 11/6/17.
 */

public class Debt {
    private String debtorName;
    private int debtValue;
    private String pushId;
    private String dateCreated;
    private boolean paidUp;

    public Debt(){}
    public Debt(String debtorsName, int debtAmount){
        this.debtorName = debtorsName;
        this.debtValue = debtAmount;
        //for the date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        this.dateCreated = dateFormat.format(new Date());

        this.paidUp = false;

    }

    public String getDebtorName() {
        return debtorName;
    }

    public int getDebtValue() {
        return debtValue;
    }

    public String getPushId() {
        return pushId;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public boolean isPaidUp() {
        return paidUp;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public void setPaidUp(boolean paidUp) {
        this.paidUp = paidUp;
    }
}
