package beans;

import java.util.Date;

public class Post implements Comparable<Post>{
    private String text, userid; //obbligatorio
    private Date date; //obbligatorio
    private String image; //opzionale
    private int likes; //0 di default
    private Integer id; //viene generato dal db. null se non ancora inserito nel db

    private Post(Integer id, String text, Date date, String userid, String image, int likes) {
        //controlli input

        this.id = id;
        this.text = text;
        this.date = date;
        this.userid = userid;
        this.image = image;
        this.likes = likes;
    }

    public Post(int id, String text, Date date, String userid, String image, int likes) {
        this((Integer)id, text, date, userid, image, likes);
    }
    public Post(int id, String text, Date date, String userid, int likes) {
        this((Integer)id, text, date, userid, null, likes);
    }

    public Post(String text, Date date, String userid, String image) {
        this(null, text, date, userid, image, 0);
    }

    public Post(String text, Date date, String userid) {
        this(null, text, date, userid, null, 0);
    }

    public int getId() {
        return id;
    }
    public String getText() {
        return text;
    }
    public Date getDate() {
        return date;
    }
    public String getUserid() {
        return userid;
    }
    public String getImage() {
        return image;
    }
    public int getLikes() {
        return likes;
    }

    @Override
    public int compareTo(Post o) {
        return this.date.compareTo(o.date);
    }
}
