package com.spencerstudios.simplenote.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.spencerstudios.simplenote.Note;
import com.spencerstudios.simplenote.R;

import java.text.DateFormat;
import java.util.List;

public class NotesAdapter extends BaseAdapter {

    private List<Note> notes;
    private LayoutInflater layoutInflater;

    public  NotesAdapter(Context context, List<Note> notes){
        this.notes = notes;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int i, View v, ViewGroup viewGroup) {
        v = layoutInflater.inflate(R.layout.note_list_item, null);
        TextView title = v.findViewById(R.id.lv_item_tv_title);
        TextView date = v.findViewById(R.id.lv_item_tv_date);
        title.setText(notes.get(i).content.trim().split("\n")[0]);
        date.setText(DateFormat.getDateTimeInstance().format(notes.get(i).date));

        return v;
    }
}
