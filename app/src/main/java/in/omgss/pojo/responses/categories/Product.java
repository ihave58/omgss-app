package in.omgss.pojo.responses.categories;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("units")
    @Expose
    private String units;
    @SerializedName("saleprice")
    @Expose
    private Integer saleprice;
    @SerializedName("actualprice")
    @Expose
    private Integer actualprice;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("maintenancetype")
    @Expose
    private Integer maintenancetype;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public Integer getSaleprice() {
        return saleprice;
    }

    public void setSaleprice(Integer saleprice) {
        this.saleprice = saleprice;
    }

    public Integer getActualprice() {
        return actualprice;
    }

    public void setActualprice(Integer actualprice) {
        this.actualprice = actualprice;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMaintenancetype() {
        return maintenancetype;
    }

    public void setMaintenancetype(Integer maintenancetype) {
        this.maintenancetype = maintenancetype;
    }

}
