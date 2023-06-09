package com.akruzen.checklance.constants;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Variables {

    // TinyDB Keys
    private static final String themeKey = "themeKey"; // 0: Light, 1: System, 2: Dark
    public static int theme = 1; // 0: Light, 1: System, 2: Dark
    private static final List<String> bankList = Arrays.asList(
            "State Bank of India", "Punjab National Bank", "Bank of Baroda", "ICICI Bank", "HDFC Bank",
            "Axis Bank", "Bank of India", "Union Bank of India", "Canara Bank", "Kotak Mahindra Bank",
            "IndusInd Bank", "Yes Bank", "UCO Bank", "Federal Bank", "Bandhan Bank", "Syndicate Bank",
            "IDBI Bank Limited", "IDFC FIRST Bank", "Indian Bank", "Central Bank of India",
            "Indian Overseas Bank", "Bank of Maharashtra", "IDBI Bank", "Punjab & Sind Bank"
    );

    public static List<String> getBankList () {
        Collections.sort(bankList);
        return bankList;
    }

    public static String getThemeKey() {
        return themeKey;
    }
}
