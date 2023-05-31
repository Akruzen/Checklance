package com.akruzen.checklance.classes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;

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
                    }
                }
            }
        }
    }
}
