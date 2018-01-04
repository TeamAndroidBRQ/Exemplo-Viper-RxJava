package com.rods.projeto.gitdesafio.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Repository implements Parcelable {
    private String name;
    private String description;
    @JsonProperty("forks")
    private int forksCount;
    @JsonProperty("stargazers_count")
    private int starsCount;
    @JsonProperty("full_name")
    private String fullName;

    private Owner owner;

    Repository(){}

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getStarsCount() {
        return starsCount;
    }

    public int getForksCount() {
        return forksCount;
    }

    public Owner getOwner() {
        return owner;
    }

    public String getFullName() {
        return fullName;
    }

    //**
    // *PARCEL CONTENT
    // *//

    public Repository(Parcel in){
        this.name = in.readString();
        this.description = in.readString();
        this.forksCount = in.readInt();
        this.starsCount = in.readInt();
        this.fullName = in.readString();
        this.owner = in.readParcelable(Owner.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeInt(this.forksCount);
        dest.writeInt(this.starsCount);
        dest.writeString(this.fullName);
        dest.writeParcelable(this.owner, flags);
    }

    public static final Parcelable.Creator<Repository> CREATOR = new Parcelable.Creator<Repository>() {
        @Override
        public Repository createFromParcel(Parcel in) {
            return new Repository(in);
        }
        @Override
        public Repository[] newArray(int size) {
            return new Repository[size];
        }
    };
}
