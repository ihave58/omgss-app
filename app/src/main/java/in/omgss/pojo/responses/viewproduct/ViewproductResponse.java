package in.omgss.pojo.responses.viewproduct;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ViewproductResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("1")
    @Expose
    private _1 _1;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public _1 get1() {
        return _1;
    }

    public void set1(_1 _1) {
        this._1 = _1;
    }

}
