package com.spencerstudios.simplenote.activites;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.spencerstudios.simplenote.BtnListener;
import com.spencerstudios.simplenote.DialogFactory;
import com.spencerstudios.simplenote.Note;
import com.spencerstudios.simplenote.R;
import com.spencerstudios.simplenote.adapters.NotesAdapter;
import com.spencerstudios.simplenote.utilities.DbUtils;
import com.spencerstudios.simplenote.utilities.PrefUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import spencerstudios.com.bungeelib.Bungee;

public class NoteListActivity extends AppCompatActivity implements BtnListener {

    private ListView lv;
    private FloatingActionButton fabNewNote;
    private List<Note> notes;
    private NotesAdapter adapter;

    @Override
    protected void onStart() {
        super.onStart();

        DbUtils du = new DbUtils();
        notes = du.getAllNotes();

        Collections.sort(notes, new Comparator<Note>() {
            @Override
            public int compare(Note o1, Note o2) {
                return Long.valueOf(o2.date).compareTo(o1.date);
            }
        });

        adapter = new NotesAdapter(this, notes);
        lv.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initViews();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(NoteListActivity.this, EditorActivity.class);

                PrefUtils pu = new PrefUtils(NoteListActivity.this);
                pu.setIsNewNote(false);
                pu.setId(notes.get(i).id);

                startActivity(intent);
                initTransAnim();
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                foo(i);
                return true;
            }
        });

        fabNewNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), EditorActivity.class);
                PrefUtils pu = new PrefUtils(NoteListActivity.this);
                pu.setIsNewNote(true);
                startActivity(i);
                initTransAnim();
            }
        });
    }

    private void foo(int i) {
        DialogFactory df = new DialogFactory(this);
        df.dispDeleteDialog(this, i);
    }

    private void initViews() {
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        if (Build.VERSION.SDK_INT >= 21) getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.navbar));
        lv = findViewById(R.id.list_view);
        fabNewNote = findViewById(R.id.fab);
        lv.setEmptyView(findViewById(R.id.tv_empty));
    }

    void initTransAnim() {
        Bungee.slideLeft(this);
    }

    @Override
    public void callback(int i) {
        DbUtils du = new DbUtils();
        du.deleteNote(notes.get(i).id);
        notes.remove(i);
        adapter.notifyDataSetChanged();
        Toast.makeText(getApplicationContext(), "note deleted", Toast.LENGTH_SHORT).show();
    }
}
