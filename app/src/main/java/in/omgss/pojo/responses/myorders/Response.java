package in.omgss.pojo.responses.myorders;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("orderdetails")
    @Expose
    private List<Orderdetail> orderdetails = null;
    @SerializedName("fullname")
    @Expose
    private String fullname;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("zip")
    @Expose
    private String zip;
    @SerializedName("paymenttype")
    @Expose
    private String paymenttype;
    @SerializedName("totalordervalue")
    @Expose
    private double totalordervalue;
    @SerializedName("deliverycharges")
    @Expose
    private double deliverycharges;
    @SerializedName("taxvalue")
    @Expose
    private double taxvalue;
    @SerializedName("discountvalue")
    @Expose
    private double discountvalue;
    @SerializedName("orderstate")
    @Expose
    private String orderstate;
    @SerializedName("razorpayid")
    @Expose
    private String razorpayid;
    @SerializedName("couponcode")
    @Expose
    private String couponcode;
    @SerializedName("datetime")
    @Expose
    private String datetime;

    public Response() {
    }


    protected Response(Parcel in) {
        id = in.readString();
        orderdetails = in.createTypedArrayList(Orderdetail.CREATOR);
        fullname = in.readString();
        email = in.readString();
        address = in.readString();
        city = in.readString();
        state = in.readString();
        zip = in.readString();
        paymenttype = in.readString();
        totalordervalue = in.readDouble();
        deliverycharges = in.readDouble();
        taxvalue = in.readDouble();
        discountvalue = in.readDouble();
        orderstate = in.readString();
        razorpayid = in.readString();
        couponcode = in.readString();
        datetime = in.readString();
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

    public List<Orderdetail> getOrderdetails() {
        return orderdetails;
    }

    public void setOrderdetails(List<Orderdetail> orderdetails) {
        this.orderdetails = orderdetails;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPaymenttype() {
        return paymenttype;
    }

    public void setPaymenttype(String paymenttype) {
        this.paymenttype = paymenttype;
    }

    public double getTotalordervalue() {
        return totalordervalue;
    }

    public void setTotalordervalue(double totalordervalue) {
        this.totalordervalue = totalordervalue;
    }

    public double getDeliverycharges() {
        return deliverycharges;
    }

    public void setDeliverycharges(double deliverycharges) {
        this.deliverycharges = deliverycharges;
    }

    public double getTaxvalue() {
        return taxvalue;
    }

    public void setTaxvalue(double taxvalue) {
        this.taxvalue = taxvalue;
    }

    public double getDiscountvalue() {
        return discountvalue;
    }

    public void setDiscountvalue(double discountvalue) {
        this.discountvalue = discountvalue;
    }

    public String getOrderstate() {
        return orderstate;
    }

    public void setOrderstate(String orderstate) {
        this.orderstate = orderstate;
    }

    public String getRazorpayid() {
        return razorpayid;
    }

    public void setRazorpayid(String razorpayid) {
        this.razorpayid = razorpayid;
    }

    public String getCouponcode() {
        return couponcode;
    }

    public void setCouponcode(String couponcode) {
        this.couponcode = couponcode;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeTypedList(orderdetails);
        dest.writeString(fullname);
        dest.writeString(email);
        dest.writeString(address);
        dest.writeString(city);
        dest.writeString(state);
        dest.writeString(zip);
        dest.writeString(paymenttype);
        dest.writeDouble(totalordervalue);
        dest.writeDouble(deliverycharges);
        dest.writeDouble(taxvalue);
        dest.writeDouble(discountvalue);
        dest.writeString(orderstate);
        dest.writeString(razorpayid);
        dest.writeString(couponcode);
        dest.writeString(datetime);
    }
}
