package com.rizafaizal.aplikasidaftarfilmlocalstorage.recyclerview.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Film implements Parcelable {

    private int id;
    private String title;
    private String release;
    private String overview;
    private String score;
    private String poster;

    public Film () {
    }

    public Film(int id, String title, String release, String overview, String score, String poster) {
        this.id = id;
        this.title = title;
        this.release = release;
        this.overview = overview;
        this.score = score;
        this.poster = poster;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeString(this.title);
        parcel.writeString(this.release);
        parcel.writeString(this.overview);
        parcel.writeString(this.score);
        parcel.writeString(this.poster);
    }

    protected Film(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.release = in.readString();
        this.overview = in.readString();
        this.score = in.readString();
        this.poster = in.readString();
    }

    public static final Creator<Film> CREATOR = new Creator<Film>() {
        @Override
        public Film createFromParcel(Parcel source) {
            return new Film(source);
        }

        @Override
        public Film[] newArray(int size) {
            return new Film[size];
        }
    };
}
