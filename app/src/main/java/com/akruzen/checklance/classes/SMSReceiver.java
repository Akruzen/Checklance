package com.akruzen.checklance.classes;

import static com.akruzen.checklance.constants.Methods.readJSONFile;
import static com.akruzen.checklance.constants.Methods.saveAsJSONFile;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SMSReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                if (pdus != null) {
                    for (Object pdu : pdus) {
                        String format = bundle.getString("format");
                        SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu, format);
                        String sender = smsMessage.getDisplayOriginatingAddress();
                        String message = smsMessage.getDisplayMessageBody();
                        Log.i("SMS", "Sender: " + sender + ". Msg: " + message);
                        List<BankDetails> detailsList = readJSONFile(context);
                        for (BankDetails details : detailsList) {
                            if (sender.toLowerCase().contains(details.getSender().toLowerCase()) &&
                                    isDebit(message, details) != -1
                                    && getAmount(message) != -1) {
                                double amount = getAmount(message);
                                if (isDebit(message, details) == 1) {
                                    details.setCurrBal(details.getCurrBal() - amount);
                                } else {
                                    details.setCurrBal(details.getCurrBal() + amount);
                                }
                                Log.i("SMS", "Amount: " + amount + ", Remaining Bal: " + details.getCurrBal());
                            } else {
                                Log.i("SMS", "No match found");
                            }
                        }
                        saveAsJSONFile(detailsList, context);
                    }
                }
            }
        }
    }

    private double getAmount(String message) {
        // Define the pattern to search for "Rs" followed by a numeric value
        Pattern pattern = Pattern.compile("Rs\\s*([\\d.]+)");
        // \\s* looks for 1 or more whitespaces, ([\\d.]+) looks for 1 or more digits
        Matcher matcher = pattern.matcher(message);
        // Find the first occurrence of the pattern
        if (matcher.find()) {
            // Extract the matched value (200) from the first group
            String extractedValue = matcher.group(1);
            if (extractedValue != null && !extractedValue.isEmpty()) {
                return Double.parseDouble(extractedValue);
            } else {
                Log.i("SMS", "No match found");
                return -1;
            }
        } else {
            Log.i("SMS", "No amount found");
            return -1;
        }
    }

    private int isDebit (String message, BankDetails details) {
        if (message.toLowerCase().contains(details.credKeys.toLowerCase())) return 0;
        if (message.toLowerCase().contains(details.debKeys.toLowerCase())) return 1;
        return -1;
    }
}
