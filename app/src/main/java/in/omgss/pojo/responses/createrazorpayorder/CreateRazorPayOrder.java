package in.omgss.pojo.responses.createrazorpayorder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateRazorPayOrder {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("razorpayorderid")
    @Expose
    private String razorpayorderid;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRazorpayorderid() {
        return razorpayorderid;
    }

    public void setRazorpayorderid(String razorpayorderid) {
        this.razorpayorderid = razorpayorderid;
    }
}
