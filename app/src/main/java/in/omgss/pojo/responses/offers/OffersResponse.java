package in.omgss.pojo.responses.offers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OffersResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("response")
    @Expose
    private ArrayList<Response> response = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Response> getResponse() {
        return response;
    }

    public void setResponse(ArrayList<Response> response) {
        this.response = response;
    }

}
