package com.androiddeft.jsonretrofit;

import android.content.Context;
import android.content.res.AssetManager;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vverbytskyy on 1/28/16.
 */
public class Utils {

    private static final String UTF_8_CODING = "UTF-8";

    private Context context;

    public Utils(Context context) {
        this.context = context;
    }

    public String loadJsonFromAsset(String assetName) {

        String outputJson = null;

        try {
            InputStream inputStream = context.getResources().getAssets().open(assetName, AssetManager.ACCESS_STREAMING);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            outputJson = new String(buffer, UTF_8_CODING);

        } catch (IOException ignore) {
            ignore.printStackTrace();
        }

        return outputJson;
    }

//    public ArrayList<UserList.UserDatum> getResuUserData() {
//        return
//    }


}
