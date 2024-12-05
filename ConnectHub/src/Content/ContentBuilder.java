/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Content;

import java.awt.image.BufferedImage;
import java.util.Date;

/**
 *
 * @author alaae
 */
public class ContentBuilder {
    protected content content;
        private String contentId;
        private Date date;
        private String text;
        private BufferedImage img;
        private String userId;
  
   public void setId(String Id)
   {
       contentId= Id;
      
   }
    public void setText(String text)
   {
       this.text =text;
       
   }
    public void setImg(BufferedImage img)
    {
        this. img=  img;
        
    }
    public void setDate(Date date)
    {
        this.date = date;
       
    }
    public content build()
    {
        content = new content();
        content.setContentId(contentId);
        content.setDate(date);
        content.setImg(img);
        content.setUserId(userId);
        return content;
    }
    
    
}
