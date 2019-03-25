package com.spencerstudios.simplenote.utilities;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class StringUtils {

    private Context context;
    private String content;

    public StringUtils(Context context, String content){
        this.context = context;
        this.content = content;
    }

    public void share(){

        if (!content.isEmpty()) {
            Intent txtIntent = new Intent(android.content.Intent.ACTION_SEND);
            txtIntent.setType("text/plain");
            txtIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "note");
            txtIntent.putExtra(android.content.Intent.EXTRA_TEXT, content);
            context.startActivity(Intent.createChooser(txtIntent, "share note via..."));
        } else {
            Toast.makeText(context, "nothing to share", Toast.LENGTH_SHORT).show();
        }
    }
}
