/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package connecthub;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 *
 * @author alaae
 */
public class factory {
    public content factory(String type ,LocalDateTime date, String text, String img, String userId)
    {
        if(type == "post")
        {
            return new posts(date, text, img,userId);
        }
        else 
            return new stories(date, text, img,userId);
        
    }
    
}
