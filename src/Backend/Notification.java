/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

/**
 *
 * @author sarar
 */
// Step 1: Define the Notification class using the Builder Pattern (Creational Design Pattern)
public class Notification {
    
    private final String id;
    private final String userId;
    private final String type;
    private final String content;
    private final String timestamp;
    private boolean read;

    private Notification(Builder builder) {
        this.id = builder.id;
        this.userId = builder.userId;
        this.type = builder.type;
        this.content = builder.content;
        this.timestamp = builder.timestamp;
        this.read = builder.read;
    }

    public static class Builder {
        private String id;
        private String userId;
        private String type;
        private String content;
        private String timestamp;
        private boolean read = false;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder timestamp(String timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder read(boolean read) {
            this.read = read;
            return this;
        }

        public Notification build() {
            return new Notification(this);
        }
    }

    // Getters and setters
    public String getId() { return id; }
    public String getUserId() { return userId; }
    public String getType() { return type; }
    public String getContent() { return content; }
    public String getTimestamp() { return timestamp; }
    public boolean isRead() { return read; }
    public void markAsRead() { this.read = true; }
}










