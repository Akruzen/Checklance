package com.akruzen.checklance.classes;

import java.util.List;

public class BankDetails {

    int accNo;
    String bank;
    // Sender's names, Credit keywords, Debit keywords
    /*List<String> sender, credKeys, debKeys;
    double currBal;*/

    public BankDetails (int accNo, String bank/*, List<String> sender,
                List<String> credKeys, List<String> debKeys, double currBal*/) {
        this.accNo = accNo; this.bank = bank;
        /*this.sender = sender; this.credKeys = credKeys;
        this.debKeys = debKeys; this.currBal = currBal;*/
    }

}
