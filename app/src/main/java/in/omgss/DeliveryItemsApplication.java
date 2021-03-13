package in.omgss;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import in.omgss.data.DataManager;


public class DeliveryItemsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DataManager.init(this);
        Fresco.initialize(this);
    }

}