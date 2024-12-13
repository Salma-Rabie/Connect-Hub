
package Backend;
import java.time.LocalDateTime;


public class stories extends content{
    

    public stories(LocalDateTime date, String text, String img, String userId ) {
        super(date, text, img, userId );
    }
   
  public boolean isExpired() {
        return this.getDate().isBefore(LocalDateTime.now().minusHours(24));
    }
   

}


