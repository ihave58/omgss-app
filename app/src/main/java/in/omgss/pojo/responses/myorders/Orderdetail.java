package in.omgss.pojo.responses.myorders;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Orderdetail implements Parcelable {

    @SerializedName("productid")
    @Expose
    private String productid;
    @SerializedName("productname")
    @Expose
    private String productname;
    @SerializedName("productimg")
    @Expose
    private String productimg;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("saleprice")
    @Expose
    private double saleprice;
    @SerializedName("actualprice")
    @Expose
    private double actualprice;
    @SerializedName("quantity")
    @Expose
    private int quantity;


    public Orderdetail() {
    }

    protected Orderdetail(Parcel in) {
        productid = in.readString();
        productname = in.readString();
        productimg = in.readString();
        thumbnail = in.readString();
        saleprice = in.readDouble();
        actualprice = in.readDouble();
        quantity = in.readInt();
    }

    public static final Creator<Orderdetail> CREATOR = new Creator<Orderdetail>() {
        @Override
        public Orderdetail createFromParcel(Parcel in) {
            return new Orderdetail(in);
        }

        @Override
        public Orderdetail[] newArray(int size) {
            return new Orderdetail[size];
        }
    };

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductimg() {
        return productimg;
    }

    public void setProductimg(String productimg) {
        this.productimg = productimg;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public double getSaleprice() {
        return saleprice;
    }

    public void setSaleprice(double saleprice) {
        this.saleprice = saleprice;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(productid);
        dest.writeString(productname);
        dest.writeString(productimg);
        dest.writeString(thumbnail);
        dest.writeDouble(saleprice);
        dest.writeDouble(actualprice);
        dest.writeInt(quantity);
    }
}
