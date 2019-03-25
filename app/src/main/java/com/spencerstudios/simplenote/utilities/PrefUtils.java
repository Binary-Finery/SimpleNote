package com.spencerstudios.simplenote.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PrefUtils {

    private SharedPreferences prefs;

    public PrefUtils(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean getIsNewNote() {
        return prefs.getBoolean("new_note", true);
    }

    public void setIsNewNote(boolean b) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("new_note", b).apply();
    }

    public long getId() {
        return prefs.getLong("id", 0);
    }

    public void setId(long id) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong("id", id).apply();
    }

    public void setOnLoadNoteContent(String content) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("content", content);
        editor.apply();
    }

    private String getPreviousContent() {
        return prefs.getString("content", "");
    }

    public boolean noChangeOnBackPress(String content) {
        String s = getPreviousContent();
        return s.equals(content);
    }
}