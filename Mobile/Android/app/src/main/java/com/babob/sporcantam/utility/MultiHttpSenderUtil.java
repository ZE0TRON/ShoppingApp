package com.babob.sporcantam.utility;

import android.content.Context;

import java.io.File;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;

public class MultiHttpSenderUtil {
    static private void uploadToServer(String filePath, Context context) {
        Retrofit retrofit = NetworkClient.getRetrofitClient( context);

        UploadAPIs uploadAPIs = retrofit.create(UploadAPIs.class);

        //Create a file object using file path
        File file = new File(filePath);

        // Create a request body with file and image media type
        RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), file);

        // Create MultipartBody.Part using file request-body,file name and part name
        MultipartBody.Part part = MultipartBody.Part.createFormData("upload", file.getName(), fileReqBody);

        //Create request body with text description and text media type
        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), "image-type");

        //
        Call call = uploadAPIs.uploadImage(part, description);

        call.enqueue(new Callback() {
            /**
             * Invoked for a received HTTP response.
             * <p>
             * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
             * Call {@link Response#isSuccessful()} to determine if the response indicates success.
             *
             * @param call
             * @param response
             */
            @Override
            public void onResponse(Call call, retrofit2.Response response) {

            }

            /**
             * Invoked when a network exception occurred talking to the server or when an unexpected
             * exception occurred creating the request or processing the response.
             *
             * @param call
             * @param t
             */
            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });

    }
}
