package com.example.usersmanagerapp.response;

import androidx.annotation.NonNull;

import com.example.usersmanagerapp.models.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UsersResponse {

    @SerializedName("page")
    @Expose
    private int page;

    @SerializedName("per_page")
    @Expose
    private int perPage;

    @SerializedName("total")
    @Expose
    private int total;

    @SerializedName("total_pages")
    @Expose
    private int totalPages;

    @SerializedName("data")
    @Expose
    private List<User> users;

    @SerializedName("support")
    @Expose
    private Support support;

    // Getters and setters for all fields

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Support getSupport() {
        return support;
    }

    public void setSupport(Support support) {
        this.support = support;
    }

    @NonNull
    @Override
    public String toString() {
        return "UsersResponse{" +
                "page=" + page +
                ", per_page=" + perPage +
                ", total=" + total +
                ", totalPages=" + totalPages +
                ", users=" + users +
                ", support=" + support +
                '}';
    }

    // Inner class to represent the "support" object
    public static class Support {

        @SerializedName("url")
        @Expose
        private String url;

        @SerializedName("text")
        @Expose
        private String text;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        @NonNull
        @Override
        public String toString() {
            return "Support{" +
                    "url='" + url + '\'' +
                    ", text='" + text + '\'' +
                    '}';
        }
    }
}