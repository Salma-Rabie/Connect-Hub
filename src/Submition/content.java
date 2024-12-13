/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 *
 * @author alaae
 */
public  abstract class content {
        private final String contentId;
        private final LocalDateTime  date;
        private final String text;
        private final  String img ;
        private final String userId;
       // private final String username;

    public content( LocalDateTime  date, String text, String img, String userId ) {
        this.contentId = UUID.randomUUID().toString().substring(0, 6);
        this.date = date;
        this.text = text;
        this.img = img;
        this.userId = userId;
        //this.username = username;
       // save();
         
    }
    public String getContentId() {
        return contentId;
    }

    public LocalDateTime  getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public String getImg() {
        return img;
    }

    public String getUserId() {
        return userId;
    }

      
    
}
