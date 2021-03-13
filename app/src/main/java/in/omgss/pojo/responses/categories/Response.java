package in.omgss.pojo.responses.categories;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Response {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("products")
    @Expose
    private ArrayList<in.omgss.pojo.responses.products.Response> products = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<in.omgss.pojo.responses.products.Response> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<in.omgss.pojo.responses.products.Response> products) {
        this.products = products;
    }

    @NotNull
    @Override
    public String toString() {
        return name != null ? name : "";
    }

}
