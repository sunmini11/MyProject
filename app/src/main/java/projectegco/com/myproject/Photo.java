package projectegco.com.myproject;

/**
 * Created by dell pc on 20/12/2559.
 */
public class Photo {
    private long id;
    private String imgpath;
    private String timestamp;
    private String subject;

    public long getId(){return id;}
    public void setId(long id){this.id=id;}

    public Photo(long id,String imgpath,String subject,String timestamp){
        this.id = id;
        this.imgpath = imgpath;
        this.timestamp = timestamp;
        this.subject = subject;
    }
    public String getImgpath(){return imgpath;}
    public String getTimestamp(){return timestamp;}
    public String getSubject(){return subject;}
}
