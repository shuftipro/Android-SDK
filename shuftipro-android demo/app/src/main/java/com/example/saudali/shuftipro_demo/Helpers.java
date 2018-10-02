package com.example.saudali.shuftipro_demo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class Helpers {
    public static void displayAlertMessage(Context context, String message){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(message);
        builder1.setCancelable(false);

        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = builder1.create();
        alertDialog.show();
    }

}
