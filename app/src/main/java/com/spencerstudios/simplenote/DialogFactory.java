package com.spencerstudios.simplenote;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class DialogFactory {

    private Context context;

    public DialogFactory(Context context){
        this.context = context;
    }

    public void dispDeleteDialog(final BtnListener btnListener, final int i){

        final AlertDialog dialog = new AlertDialog.Builder(context,R.style.PopTheme).create();
        if (dialog.getWindow() != null) dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        @SuppressLint("InflateParams")
        View v = LayoutInflater.from(context).inflate(R.layout.delete_dialog, null);
        Button ok = v.findViewById(R.id.confirm);
        Button cancel = v.findViewById(R.id.cancel);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnListener.callback(i);
                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.setView(v);
        dialog.show();
    }
}
