
package connect.hub.Backend;

public class UserDataBase {
 private static UserDataBase instance=null;
  private final String filePath;
    private UserDataBase(String filePath) {
        this.filePath = filePath;
    }
    public static UserDataBase getInstance(String filePath){
        if(instance==null) 
            instance=new UserDataBase(filePath);
        return instance;   
    }
    
    
}
