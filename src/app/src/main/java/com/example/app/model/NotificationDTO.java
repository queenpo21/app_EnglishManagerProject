package com.example.app.model;

public class NotificationDTO {
    private String idNotification;
    private String title;
    private String poster;
    private String description;

    public NotificationDTO(String idNotification, String poster, String title, String description) {
        this.idNotification = idNotification;
        this.title = title;
        this.poster = poster;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }
    public String getPoster() {
        return poster;
    }
    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIdNotification() {
        return idNotification;
    }

    public void setIdNotification(String idNotification) {
        this.idNotification = idNotification;
    }

    @Override
    public String toString() {
        return "NotificationDTO{" +
                "idNotification='" + idNotification + '\'' +
                ", title='" + title + '\'' +
                ", poster='" + poster + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
