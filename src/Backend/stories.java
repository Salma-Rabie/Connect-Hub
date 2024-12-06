/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;
import java.time.LocalDateTime;


/**
 *
 * @author alaae
 */
public class stories extends content{
    

    public stories(LocalDateTime date, String text, String img, String userId ) {
        super(date, text, img, userId );
    }
   
  public boolean isExpired() {
        return this.getDate().isBefore(LocalDateTime.now().minusHours(24));
    }
   

}


