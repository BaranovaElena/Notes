package com.example.notes;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class NoteEntity implements Parcelable {
    private static final AtomicInteger count = new AtomicInteger(0);

    private final int id;
    private final String title;
    private final String description;
    private final long date;
    private final String text;
    private final String category;
    private boolean isFavorite;

    NoteEntity() {
        id = count.incrementAndGet();
        title = "";
        description = "";
        date = Calendar.getInstance().getTimeInMillis();
        text = "";
        category = "";
        isFavorite = false;
    }

    NoteEntity(int id, String title, String description, long date,
               String text, String category, boolean isFavorite) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.text = text;
        this.category = category;
        this.isFavorite = isFavorite;
    }

    NoteEntity(String title, String description, long date,
               String text, String category, boolean isFavorite) {
        this.id = count.incrementAndGet();
        this.title = title;
        this.description = description;
        this.date = date;
        this.text = text;
        this.category = category;
        this.isFavorite = isFavorite;
    }

    protected NoteEntity(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        date = in.readLong();
        text = in.readString();
        category = in.readString();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            isFavorite = in.readBoolean();
        }
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
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeLong(date);
        dest.writeString(text);
        dest.writeString(category);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            dest.writeBoolean(isFavorite);
        }
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

    public int getIdentifier() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public boolean getIsFavorite() {
        return isFavorite;
    }
}
