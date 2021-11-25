package com.example.skinsell.config;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

public class Shared {
    public static void openWhatsApp(Activity acc, String number, String body){
        try {
            String pais = "55";

            Intent sendIntent = new Intent(
                    Intent.ACTION_SENDTO,
                    Uri.parse("smsto:" + pais + number + "?body=" + body)
            );
            sendIntent.setPackage("com.whatsapp");
            acc.startActivity(sendIntent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
