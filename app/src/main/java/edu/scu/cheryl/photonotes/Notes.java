package edu.scu.cheryl.photonotes;

/**
 * Created by XiangM on 16-2-18.
 */
public class Notes {
    //private String name;
    //private String filename;
    public static  final String TABLE="Notes";
    public static final String KEY_ID="id";
    public static final String KEY_caption="caption";
    public  static final String KEY_filename="filename";

    public int notes_id;
    public String caption;
    public String filename;

    public Notes(int id, String caption, String filename) {
        this.caption = caption;
        this.filename = filename;
        this.notes_id=id;
    }
    public int getNotes_id(){
        return notes_id;
    }
    public void setNotes_id(int notes_id){
        this.notes_id=notes_id;
    }
    public String getCaption(){
        return caption;
    }
    public void setCaption(String caption){
        this.caption=caption;
    }
    public String getFilename(){
        return filename;
    }
    public void setFilename(String filename){
        this.filename=filename;
    }

}
