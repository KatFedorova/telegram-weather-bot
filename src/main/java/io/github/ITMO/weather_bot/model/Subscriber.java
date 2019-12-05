package io.github.ITMO.weather_bot.model;

public class Subscriber {
    private long Id;

    private long chatId;
    private double latitude;
    private double longitude;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
