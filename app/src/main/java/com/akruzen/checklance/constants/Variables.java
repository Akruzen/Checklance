package com.akruzen.checklance.constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Variables {

    // TinyDB Keys
    private static final String themeKey = "themeKey"; // 0: Light, 1: System, 2: Dark
    private static final String biometricKey = "biometricKey"; // 0: No, 1: Yes, -1: No biometric
    private static final String isBiometricTemporarilyOffKey = "isBiometricTemporarilyOff";
    // true: biometric is temporarily off, turn on immediately and set variable false
    private static final String launchedBefore = "launchedBefore"; // Has the app been launched first time?
    private static final String jsonFileName = "output.json";
    // public static int theme = 1; // 0: Light, 1: System, 2: Dark
    private static final String otherBankStr = "Other...";
    private static final List<String> bankList = Arrays.asList(
            "State Bank of India", "Punjab National Bank", "Bank of Baroda", "ICICI Bank", "HDFC Bank",
            "Axis Bank", "Bank of India", "Union Bank of India", "Canara Bank", "Kotak Mahindra Bank",
            "IndusInd Bank", "Yes Bank", "UCO Bank", "Federal Bank", "Bandhan Bank", "Syndicate Bank",
            "IDBI Bank Limited", "IDFC FIRST Bank", "Indian Bank", "Central Bank of India",
            "Indian Overseas Bank", "Bank of Maharashtra", "IDBI Bank", "Punjab & Sind Bank"
    );

    public static List<String> getBankList () {
        Collections.sort(bankList);
        List<String> list = new ArrayList<>(bankList);
        list.add(getOtherBankStr());
        return list;
    }

    public static String getThemeKey() {
        return themeKey;
    }

    public static String getJsonFileName() {
        return jsonFileName;
    }

    public static String getLaunchedBefore() {
        return launchedBefore;
    }

    public static String getOtherBankStr() {
        return otherBankStr;
    }

    public static String getBiometricKey() {
        return biometricKey;
    }

    public static String getIsBiometricTemporarilyOffKey() {
        return isBiometricTemporarilyOffKey;
    }
}
