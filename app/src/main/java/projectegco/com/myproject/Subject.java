package projectegco.com.myproject;

/**
 * Created by dell pc on 23/12/2559.
 */
public class Subject {
    private long id;
    private String subject;

    public long getId(){return id;}
    public void setId(long id){this.id=id;}

    public Subject(long id,String subject){
        this.id = id;
        this.subject = subject;
    }
    public String getSubject(){return subject;}
}
