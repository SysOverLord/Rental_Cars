package com.example.RentalCars.Entity;

import android.app.AlertDialog;
import android.content.Context;

public class DialogHelper {
    private static DialogHelper instance = new DialogHelper();

    private DialogHelper() {}

    public static DialogHelper getInstance() {
        return instance;
    }

    public void ShowMessage(String message, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        AlertDialog alert = builder.create();
        if(!message.equals("")){
            alert.setTitle("System Message");
            alert.show();
        }
        else
            alert.dismiss();
    }
}