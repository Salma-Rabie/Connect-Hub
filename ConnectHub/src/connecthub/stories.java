/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package connecthub;

import com.google.gson.JsonArray;
import com.google.gson.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author alaae
 */
public class stories extends content{
    
    public stories(LocalDateTime date, String text, String img, String userId) {
        super(date, text, img, userId);
    }
     static {
         ScheduledExecutorService SCHEDULER = Executors.newScheduledThreadPool(1);
        SCHEDULER.scheduleAtFixedRate(() -> {
            try {
                delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.MINUTES);
    }
    public static void delete() throws IOException
    {
        JsonArray stories = read();
          JsonArray updatedStories = new JsonArray();
        LocalDateTime now = LocalDateTime.now();
       for (JsonElement element : stories) {
        JsonObject story = element.getAsJsonObject();
        LocalDateTime creationTime = LocalDateTime.parse(story.get("creation time").getAsString());

        System.out.println("Story creation time: " + creationTime);
        if (creationTime.plusHours(24).isAfter(now)) {
            updatedStories.add(story);
        } else {
            System.out.println("Deleted story with ID: " + story.get("contentId").getAsString());
        }
        }
    }
    public static JsonArray read() throws IOException
    {
        File file = new File("output.json");
        Gson GSON = new Gson();
        if (!file.exists() || file.length() == 0) {
            return new JsonArray();
        }

        try (Reader reader = new FileReader(file)) {
            return GSON.fromJson(reader, JsonArray.class);
        }
       
    }
}
