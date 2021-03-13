package in.omgss.pojo.responses.subcategory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("subcatname")
    @Expose
    private String subcatname;
    @SerializedName("subcatimage")
    @Expose
    private String subcatimage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubcatname() {
        return subcatname;
    }

    public void setSubcatname(String subcatname) {
        this.subcatname = subcatname;
    }

    public String getSubcatimage() {
        return subcatimage;
    }

    public void setSubcatimage(String subcatimage) {
        this.subcatimage = subcatimage;
    }

}
