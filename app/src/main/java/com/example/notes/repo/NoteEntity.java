package com.example.notes.repo;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NoteEntity implements Parcelable {
    private String id;
    private final String title;
    private final String description;
    private final long date;
    private final String text;
    private final String category;
    private final boolean isFavorite;

    public NoteEntity() {
        id = "";
        title = "";
        description = "";
        date = Calendar.getInstance().getTimeInMillis();
        text = "";
        category = "";
        isFavorite = false;
    }

    public NoteEntity(String id, String title, String description, long date,
                      String text, String category, boolean isFavorite) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.text = text;
        this.category = category;
        this.isFavorite = isFavorite;
    }

    protected NoteEntity(Parcel in) {
        id = in.readString();
        title = in.readString();
        description = in.readString();
        date = in.readLong();
        text = in.readString();
        category = in.readString();
        isFavorite = in.readInt() > 0;
    }

    public static final Creator<NoteEntity> CREATOR = new Creator<NoteEntity>() {
        @Override
        public NoteEntity createFromParcel(Parcel in) {
            return new NoteEntity(in);
        }

        @Override
        public NoteEntity[] newArray(int size) {
            return new NoteEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeLong(date);
        dest.writeString(text);
        dest.writeString(category);
        dest.writeInt(isFavorite ? 1 : 0);
    }

    public String getTitle() {
        return title;
    }

    public String getStringDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        return formatter.format(new Date(date));
    }

    public String getDescription() {
        return description;
    }

    public String getText() {
        return text;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public boolean getIsFavorite() {
        return isFavorite;
    }
}
