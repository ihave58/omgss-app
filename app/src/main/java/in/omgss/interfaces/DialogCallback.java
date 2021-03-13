package in.omgss.interfaces;

import android.view.View;

import java.io.Serializable;

/**
 * this interface is used to get the callback from the dialog
 */
public interface DialogCallback extends Serializable {
    void onSubmit(View view, Object result);

    void onCancel();
}
