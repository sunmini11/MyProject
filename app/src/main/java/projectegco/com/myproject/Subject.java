package projectegco.com.myproject;

/**
 * Created by dell pc on 23/12/2559.
 */
public class Subject {
    private long id;
    private String subject_name;
    private String subject_id;

    public long getId(){return id;}
    public void setId(long id){this.id=id;}

    public Subject(long id,String subject_name,String subject_id){
        this.id = id;
        this.subject_name = subject_name;
        this.subject_id = subject_id;
    }
    public String getSubjectName(){return subject_name;}
    public String getSubjectId(){return subject_id;}
}
