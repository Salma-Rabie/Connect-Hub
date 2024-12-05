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
public class posts extends ContentBuilder {
   
   public posts(ContentBuilder builder)
   {
       super.setDate(builder.build().getDate());
       super.setId(builder.build().getUserId());
       super.setImg(builder.build().getImg());
       super.setText(builder.build().getText());
   }
}
