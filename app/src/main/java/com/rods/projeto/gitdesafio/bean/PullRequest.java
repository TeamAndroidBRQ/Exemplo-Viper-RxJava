package com.rods.projeto.gitdesafio.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PullRequest implements Parcelable {
    //Nome / Foto do autor do PR, Título do PR, Data do PR e Body do PR
    private String title;

    @JsonProperty("body")
    private String description;

    @JsonProperty("updated_at")
    private String updatedAtStr;

    @JsonProperty("html_url")
    private String contentUrl;

    @JsonProperty("user")
    private Owner owner;

    PullRequest(){}

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUpdatedAtStr() {
        return updatedAtStr;
    }

    public Owner getOwner() {
        return owner;
    }

    public String getContentUrl(){
        return contentUrl;
    }
    //**
    // *PARCEL CONTENT
    // *//

    PullRequest(Parcel in){
        this.title = in.readString();
        this.description = in.readString();
        this.updatedAtStr = in.readString();
        this.contentUrl = in.readString();
        this.owner = in.readParcelable(Owner.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.updatedAtStr);
        dest.writeString(this.contentUrl);
        dest.writeParcelable(this.owner, flags);
    }

    public static final Creator<PullRequest> CREATOR = new Creator<PullRequest>() {
        @Override
        public PullRequest createFromParcel(Parcel in) {
            return new PullRequest(in);
        }
        @Override
        public PullRequest[] newArray(int size) {
            return new PullRequest[size];
        }
    };
}
