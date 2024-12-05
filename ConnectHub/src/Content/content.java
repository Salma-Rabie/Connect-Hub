/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Content;
import com.google.gson.Gson;
import java.awt.image.BufferedImage;
import java.util.Date;

/**
 *
 * @author alaae
 */
public class content {
        private String contentId;
        private Date date;
        private String text;
        private BufferedImage img;
        private String userId;

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getContentId() {
        return contentId;
    }

    public Date getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public BufferedImage getImg() {
        return img;
    }

    public String getUserId() {
        return userId;
    }
        
        
        
    
}
