/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import Backend.content;
import Backend.UserDataBase;
import com.google.gson.JsonArray;
import com.google.gson.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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


