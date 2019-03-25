package com.spencerstudios.simplenote.activites;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.spencerstudios.simplenote.BtnListener;
import com.spencerstudios.simplenote.DialogFactory;
import com.spencerstudios.simplenote.R;
import com.spencerstudios.simplenote.utilities.DbUtils;
import com.spencerstudios.simplenote.utilities.PrefUtils;
import com.spencerstudios.simplenote.utilities.StringUtils;

import spencerstudios.com.bungeelib.Bungee;

public class EditorActivity extends AppCompatActivity implements BtnListener{

    private EditText etContent;
    private PrefUtils pu;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initViews();

        pu = new PrefUtils(this);

        if (!pu.getIsNewNote()) {

            long id = pu.getId();
            DbUtils du = new DbUtils();
            etContent.setText(du.getNote(id));
        }
        pu.setOnLoadNoteContent(etContent.getText().toString());
    }

    private void initViews() {

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        etContent = findViewById(R.id.et_content);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.navbar));
        }
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.iv_save:
                saveNote();
                break;
            case R.id.iv_share:
                StringUtils su = new StringUtils(this, etContent.getText().toString());
                su.share();
                break;
            case R.id.iv_delete:
                if(!pu.getIsNewNote()){
                    DialogFactory df = new DialogFactory(this);
                    df.dispDeleteDialog(this, 0);
                }else Toast.makeText(getApplicationContext(), "you cannot delete an unsaved note", Toast.LENGTH_SHORT).show();

                break;
        }
    }

    private void saveNote() {

        DbUtils du = new DbUtils();
        String content = etContent.getText().toString();

        String type;

        if (pu.getIsNewNote()) {
            du.addNote(content);
            type = "note saved";
        } else {
            du.updateNote(pu.getId(), content);
            type = "note updated";
        }

        Toast.makeText(getApplicationContext(), type, Toast.LENGTH_SHORT).show();

        finish();
        initTransAnim();
    }

    void initTransAnim() {
        Bungee.slideRight(this);
    }

    public void onBackPressed() {
        if (pu.noChangeOnBackPress(etContent.getText().toString())) {
            finish();
            initTransAnim();
        } else {
            showContentChangedDialog();
        }
    }

    private void showContentChangedDialog() {

        final AlertDialog ad = new AlertDialog.Builder(this).create();
        ad.setTitle("Content Has Changed");
        ad.setMessage("save changes?");
        ad.setButton(DialogInterface.BUTTON_POSITIVE, "save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                saveNote();
                ad.dismiss();
            }
        });
        ad.setButton(DialogInterface.BUTTON_NEGATIVE, "cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ad.dismiss();
            }
        });

        ad.show();
    }

    @Override
    public void callback(int i) {
        PrefUtils pu = new PrefUtils(this);
        long id = pu.getId();
        DbUtils du = new DbUtils();
        du.deleteNote(id);
        finish();
        initTransAnim();
    }
}
