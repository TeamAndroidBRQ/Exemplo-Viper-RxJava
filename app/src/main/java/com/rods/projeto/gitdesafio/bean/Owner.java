package com.rods.projeto.gitdesafio.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Owner implements Parcelable {

    private String login;
    @JsonProperty("avatar_url")
    private String avatarUrl;

    Owner(){}

    public String getLogin() {
        Assert.
        return login;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    //**
    // *PARCEL CONTENT
    // *//

    Owner(Parcel in){
        this.login = in.readString();
        this.avatarUrl = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.login);
        dest.writeString(this.avatarUrl);
    }

    public static final Parcelable.Creator<Owner> CREATOR = new Parcelable.Creator<Owner>() {
        @Override
        public Owner createFromParcel(Parcel in) {
            return new Owner(in);
        }
        @Override
        public Owner[] newArray(int size) {
            return new Owner[size];
        }
    };
}
