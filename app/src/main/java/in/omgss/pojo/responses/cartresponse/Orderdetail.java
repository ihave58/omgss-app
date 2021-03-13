package in.omgss.pojo.responses.cartresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Orderdetail {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("prdid")
    @Expose
    private String prdid;
    @SerializedName("productname")
    @Expose
    private String productname;
    @SerializedName("saleprice")
    @Expose
    private double saleprice;
    @SerializedName("actualprice")
    @Expose
    private double actualprice;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("units")
    @Expose
    private String units;
    @SerializedName("quantity")
    @Expose
    private int quantity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrdid() {
        return prdid;
    }

    public void setPrdid(String prdid) {
        this.prdid = prdid;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public double getSaleprice() {
        return saleprice;
    }

    public void setSaleprice(double saleprice) {
        this.saleprice = saleprice;
    }

    public String getImage() {
        return image;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getActualprice() {
        return actualprice;
    }

    public void setActualprice(double actualprice) {
        this.actualprice = actualprice;
    }
}
