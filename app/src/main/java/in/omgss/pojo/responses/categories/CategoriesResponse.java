package in.omgss.pojo.responses.categories;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CategoriesResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("response")
    @Expose
    private ArrayList<Response> response = null;
    @SerializedName("products")
    @Expose
    private ArrayList<Product_> products = null;

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

    public ArrayList<Response> getResponse() {
        return response;
    }

    public void setResponse(ArrayList<Response> response) {
        this.response = response;
    }

    public ArrayList<Product_> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product_> products) {
        this.products = products;
    }


}
