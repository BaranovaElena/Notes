package com.example.notes;

import android.os.Parcel;
import android.os.Parcelable;

public class NoteEntity implements Parcelable {
    private final String title;
    private final String description;
    private final String date;
    private final String text;

    NoteEntity(String title, String description, String date, String text) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.text = text;
    }

    protected NoteEntity(Parcel in) {
        title = in.readString();
        description = in.readString();
        date = in.readString();
        text = in.readString();
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
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(date);
        dest.writeString(text);
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getText() {
        return text;
    }
}
