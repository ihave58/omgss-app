package in.omgss.pojo.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("sliderimage")
    @Expose
    private String sliderimage;
    @SerializedName("tagline1")
    @Expose
    private String tagline1;
    @SerializedName("tagline2")
    @Expose
    private String tagline2;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSliderimage() {
        return sliderimage;
    }

    public void setSliderimage(String sliderimage) {
        this.sliderimage = sliderimage;
    }

    public String getTagline1() {
        return tagline1;
    }

    public void setTagline1(String tagline1) {
        this.tagline1 = tagline1;
    }

    public String getTagline2() {
        return tagline2;
    }

    public void setTagline2(String tagline2) {
        this.tagline2 = tagline2;
    }

}
