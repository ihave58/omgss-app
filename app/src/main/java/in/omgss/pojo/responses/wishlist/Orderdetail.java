package in.omgss.pojo.responses.wishlist;

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
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("units")
    @Expose
    private String units;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("actualprice")
    @Expose
    private double actualprice;
    @SerializedName("quantity")
    @Expose
    private int quantity;
    @SerializedName("isAddedToCart")
    @Expose
    private int isAddedToCart;

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

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public double getActualprice() {
        return actualprice;
    }

    public void setActualprice(double actualprice) {
        this.actualprice = actualprice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getIsAddedToCart() {
        return isAddedToCart;
    }

    public void setIsAddedToCart(int isAddedToCart) {
        this.isAddedToCart = isAddedToCart;
    }

}
