package com.umesh.expandablelist;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
@Generated("org.jsonschema2pojo")
public class Post {
    @SerializedName("userId")
    @Expose
    public long userId;
    @SerializedName("id")
    @Expose
    public long id;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("body")
    @Expose
    public String body;
    @SerializedName("comments")
    @Expose
    public List<Comment> comments = new ArrayList<Comment>();
}
