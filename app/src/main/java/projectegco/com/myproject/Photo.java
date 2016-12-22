package projectegco.com.myproject;

/**
 * Created by dell pc on 20/12/2559.
 */
public class Photo {
    private long id;
    private String imgname;
    private String timestamp;
    private String message;

    public long getId(){return id;}
    public void setId(long id){this.id=id;}

    public Photo(long id,String imgname,String message,String timestamp){
        this.id = id;
        this.message = message;
        this.timestamp = timestamp;
        this.imgname = imgname;
    }

    public String getMessage(){return message;}
    public String getTimestamp(){return timestamp;}
    public String getImgname(){return imgname;}
}
