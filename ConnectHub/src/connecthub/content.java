/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package connecthub;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.awt.image.BufferedImage;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
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

    public content( LocalDateTime  date, String text, String img, String userId) {
        this.contentId = UUID.randomUUID().toString().substring(0, 6);
        this.date = date;
        this.text = text;
        if(img!=null){
        this.img = img;}
        else
          this.img = null;
        this.userId = userId;
        save();
         
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
    public void save()
    {
          Gson gson = new Gson(); 
         JsonObject jsonObject = new JsonObject();
         jsonObject.addProperty("contentId", contentId);
          jsonObject.addProperty("creation time ", date.toString());
         jsonObject.addProperty("Text", text);
         if(img!=null)
         {
              jsonObject.addProperty("image path :", img);
         }
         try{
              String filepath = "output.json";
             FileWriter file = new FileWriter(filepath, true);
             file.write(gson.toJson(jsonObject) + System.lineSeparator());
       
                  file.close();
         }catch(IOException e)
         {
             e.printStackTrace();
         }
         
         
    }
        
        
    
}
