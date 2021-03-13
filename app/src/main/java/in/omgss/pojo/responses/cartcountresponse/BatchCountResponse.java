package in.omgss.pojo.responses.cartcountresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class BatchCountResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("countofcart")
    @Expose
    private int countofcart;
    @SerializedName("countofnotification")
    @Expose
    private int countofnotification;

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

    public int getCountofcart() {
        return countofcart;
    }

    public void setCountofcart(int countofcart) {
        this.countofcart = countofcart;
    }

    public int getCountofnotification() {
        return countofnotification;
    }

    public void setCountofnotification(int countofnotification) {
        this.countofnotification = countofnotification;
    }
}
