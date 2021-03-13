package in.omgss.pojo.responses.mydevices;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("productid")
    @Expose
    private String productid;
    @SerializedName("devicename")
    @Expose
    private String devicename;
    @SerializedName("quantity")
    @Expose
    private int quantity;
    @SerializedName("orderid")
    @Expose
    private String orderid;
    @SerializedName("datetime")
    @Expose
    private String datetime;
    @SerializedName("noofdaysleft")
    @Expose
    private String daysLeft;


    public Response() {
    }

    protected Response(Parcel in) {
        id = in.readString();
        productid = in.readString();
        devicename = in.readString();
        quantity = in.readInt();
        orderid = in.readString();
        datetime = in.readString();
        daysLeft = in.readString();
    }

    public static final Creator<Response> CREATOR = new Creator<Response>() {
        @Override
        public Response createFromParcel(Parcel in) {
            return new Response(in);
        }

        @Override
        public Response[] newArray(int size) {
            return new Response[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getDevicename() {
        return devicename;
    }

    public void setDevicename(String devicename) {
        this.devicename = devicename;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(String daysLeft) {
        this.daysLeft = daysLeft;
    }

    @Override
    public String toString() {
        return devicename;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(productid);
        dest.writeString(devicename);
        dest.writeInt(quantity);
        dest.writeString(orderid);
        dest.writeString(datetime);
        dest.writeString(daysLeft);
    }
}
