package in.omgss.pojo.responses.validatecoupon;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("couponname")
    @Expose
    private String couponname;
    @SerializedName("couponcode")
    @Expose
    private String couponcode;
    @SerializedName("coupontype")
    @Expose
    private int coupontype;
    @SerializedName("couponamount")
    @Expose
    private double couponamount;
    @SerializedName("usageperuser")
    @Expose
    private int usageperuser;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCouponname() {
        return couponname;
    }

    public void setCouponname(String couponname) {
        this.couponname = couponname;
    }

    public String getCouponcode() {
        return couponcode;
    }

    public void setCouponcode(String couponcode) {
        this.couponcode = couponcode;
    }

    public int getCoupontype() {
        return coupontype;
    }

    public void setCoupontype(int coupontype) {
        this.coupontype = coupontype;
    }

    public double getCouponamount() {
        return couponamount;
    }

    public void setCouponamount(int couponamount) {
        this.couponamount = couponamount;
    }

    public int getUsageperuser() {
        return usageperuser;
    }

    public void setUsageperuser(int usageperuser) {
        this.usageperuser = usageperuser;
    }

}
