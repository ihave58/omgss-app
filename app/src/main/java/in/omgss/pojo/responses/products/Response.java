package in.omgss.pojo.responses.products;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("id")
    @Expose
    private String id;
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
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("saleprice")
    @Expose
    private int saleprice;
    @SerializedName("actualprice")
    @Expose
    private int actualprice;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("maintenancetype")
    @Expose
    private int maintenancetype;
    @SerializedName("isAddedToWishlist")
    @Expose
    private int isAddedToWishlist;
    @SerializedName("isAddedToCart")
    @Expose
    private int isAddedToCart;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public int getSaleprice() {
        return saleprice;
    }

    public void setSaleprice(int saleprice) {
        this.saleprice = saleprice;
    }

    public int getActualprice() {
        return actualprice;
    }

    public void setActualprice(int actualprice) {
        this.actualprice = actualprice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMaintenancetype() {
        return maintenancetype;
    }

    public void setMaintenancetype(int maintenancetype) {
        this.maintenancetype = maintenancetype;
    }

    public int getIsAddedToWishlist() {
        return isAddedToWishlist;
    }

    public void setIsAddedToWishlist(int isAddedToWishlist) {
        this.isAddedToWishlist = isAddedToWishlist;
    }

    public int getIsAddedToCart() {
        return isAddedToCart;
    }

    public void setIsAddedToCart(int isAddedToCart) {
        this.isAddedToCart = isAddedToCart;
    }
}
