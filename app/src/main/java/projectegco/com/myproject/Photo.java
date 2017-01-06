package projectegco.com.myproject;

/**
 * Created by dell pc on 20/12/2559.
 */
public class Photo {
    private long id;
    private String imgpath;
    private String timestamp;
    private String subject_id;
    private String phId;
    private boolean selected;

    public long getId(){return id;}
    public void setId(long id){this.id=id;}

    public Photo(long id,String imgpath,String subject_id,String timestamp){
        this.id = id;
        this.imgpath = imgpath;
        this.timestamp = timestamp;
        this.subject_id = subject_id;
    }

    public String getImgpath(){return imgpath;}
    public String getTimestamp(){return timestamp;}
    public String getSubject(){return subject_id;}



    public Photo(String phId) {
        this.phId = phId;
    }

    public String getName() {
        return phId;
    }


    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
