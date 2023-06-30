package com.akruzen.checklance.classes;

import java.util.List;

public class BankDetails {

    int accNo;
    String bank;
    // Sender's names, Credit keywords, Debit keywords
    /* List<String>*/ String sender, credKeys, debKeys;
    double currBal;

    public BankDetails (int accNo, String bank, String sender,
                String credKeys, String debKeys, double currBal) {
        this.accNo = accNo; this.bank = bank;
        this.sender = sender; this.credKeys = credKeys;
        this.debKeys = debKeys; this.currBal = currBal;
    }

    public int getAccNo() {
        return accNo;
    }

    public String getBank() {
        return bank;
    }

    public double getCurrBal() {
        return currBal;
    }

    public void setCurrBal(double currBal) {
        this.currBal = currBal;
    }

    public String getCredKeys() {
        return credKeys;
    }

    public String getDebKeys() {
        return debKeys;
    }

    public String getSender() {
        return sender;
    }
}
