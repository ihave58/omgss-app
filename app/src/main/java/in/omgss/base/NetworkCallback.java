package in.omgss.base;

import com.google.gson.Gson;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import in.omgss.pojo.FailureResponse;
import in.omgss.pojo.commonresponse.CommonResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class NetworkCallback<T> implements Callback<T> {

    public static final int AUTH_FAILED = 99;
    public static final int NO_INTERNET = 9;


    public abstract void onSuccess(T t);

    public abstract void onFailure(FailureResponse failureResponse);

    public abstract void onError(Throwable t);

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            onSuccess(response.body());
        } else {
            FailureResponse failureErrorBody = getFailureErrorBody(call.request().url().url().getFile(), response);
            onFailure(failureErrorBody);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (t instanceof SocketTimeoutException || t instanceof UnknownHostException) {
            FailureResponse failureResponseForNoNetwork = getFailureResponseForNoNetwork();
            onFailure(failureResponseForNoNetwork);
        } else {
            onError(t);
        }
    }


    private FailureResponse getFailureResponseForNoNetwork() {
        FailureResponse failureResponse = new FailureResponse();
        failureResponse.setErrorMessage("Couldn't connect to server! Please try again.");
        failureResponse.setErrorCode(NO_INTERNET);
        return failureResponse;
    }


    /**
     * Create your custom failure response out of server response
     * Also save Url for any further use
     */
    private FailureResponse getFailureErrorBody(String url, Response<T> errorBody) {
        FailureResponse failureResponse = new FailureResponse();
        if (errorBody.code() == 500) {
            failureResponse.setErrorCode(errorBody.code());
            failureResponse.setErrorMessage(errorBody.message());
            return failureResponse;
        } else {
            CommonResponse commonResponse = null;
            try {
                commonResponse = new Gson().fromJson(errorBody.errorBody().string(), CommonResponse.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (commonResponse != null) {
                failureResponse.setErrorMessage(commonResponse.getMESSAGE());
                failureResponse.setErrorCode(commonResponse.getCODE());
            }
            return failureResponse;
        }
    }

}
