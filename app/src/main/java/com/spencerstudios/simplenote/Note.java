package com.spencerstudios.simplenote;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class Note {

    @Id public long id;

    public String title;
    public String content;
    public long date;
}