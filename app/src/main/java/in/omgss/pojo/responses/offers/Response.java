package in.omgss.pojo.responses.offers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("couponname")
    @Expose
    private String couponname;
    @SerializedName("couponcode")
    @Expose
    private String couponcode;
    @SerializedName("coupontype")
    @Expose
    private String coupontype;
    @SerializedName("couponamount")
    @Expose
    private Integer couponamount;
    @SerializedName("usageperuser")
    @Expose
    private Integer usageperuser;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getCoupontype() {
        return coupontype;
    }

    public void setCoupontype(String coupontype) {
        this.coupontype = coupontype;
    }

    public Integer getCouponamount() {
        return couponamount;
    }

    public void setCouponamount(Integer couponamount) {
        this.couponamount = couponamount;
    }

    public Integer getUsageperuser() {
        return usageperuser;
    }

    public void setUsageperuser(Integer usageperuser) {
        this.usageperuser = usageperuser;
    }

}
