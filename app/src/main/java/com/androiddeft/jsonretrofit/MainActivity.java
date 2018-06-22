package com.androiddeft.jsonretrofit;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.androiddeft.jsonretrofit.api.ApiService;
import com.androiddeft.jsonretrofit.helper.RetroClient;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class MainActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    private RecyclerView recyclerView;
    private EmployeesAdapter eAdapter;
    ApiService apiInterface;
    int tempCount=1;
    UserList userList;
    ArrayList mUserList;
    String json1 ="{\"page\":1,\"per_page\":3,\"total\":12,\"total_pages\":4,\"data\":[{\"id\":1,\"first_name\":\"George\",\"last_name\":\"Bluth\",\"avatar\":\"https://s3.amazonaws.com/uifaces/faces/twitter/calebogden/128.jpg\"},{\"id\":2,\"first_name\":\"Janet\",\"last_name\":\"Weaver\",\"avatar\":\"https://s3.amazonaws.com/uifaces/faces/twitter/josephstein/128.jpg\"},{\"id\":3,\"first_name\":\"Emma\",\"last_name\":\"Wong\",\"avatar\":\"https://s3.amazonaws.com/uifaces/faces/twitter/olegpogodaev/128.jpg\"}]}";
    String json2 ="{\"page\":2,\"per_page\":3,\"total\":12,\"total_pages\":4,\"data\":[{\"id\":4,\"first_name\":\"Eve\",\"last_name\":\"Holt\",\"avatar\":\"https://s3.amazonaws.com/uifaces/faces/twitter/marcoramires/128.jpg\"},{\"id\":5,\"first_name\":\"Charles\",\"last_name\":\"Morris\",\"avatar\":\"https://s3.amazonaws.com/uifaces/faces/twitter/stephenmoon/128.jpg\"},{\"id\":6,\"first_name\":\"Tracey\",\"last_name\":\"Ramos\",\"avatar\":\"https://s3.amazonaws.com/uifaces/faces/twitter/bigmancho/128.jpg\"}]}";
    String json3="{\"page\":3,\"per_page\":3,\"total\":12,\"total_pages\":4,\"data\":[{\"id\":7,\"first_name\":\"Michael\",\"last_name\":\"Lawson\",\"avatar\":\"https://s3.amazonaws.com/uifaces/faces/twitter/follettkyle/128.jpg\"},{\"id\":8,\"first_name\":\"Lindsay\",\"last_name\":\"Ferguson\",\"avatar\":\"https://s3.amazonaws.com/uifaces/faces/twitter/araa3185/128.jpg\"},{\"id\":9,\"first_name\":\"Tobias\",\"last_name\":\"Funke\",\"avatar\":\"https://s3.amazonaws.com/uifaces/faces/twitter/vivekprvr/128.jpg\"}]}";
    String json4= "{\"page\":4,\"per_page\":3,\"total\":12,\"total_pages\":4,\"data\":[{\"id\":10,\"first_name\":\"Byron\",\"last_name\":\"Fields\",\"avatar\":\"https://s3.amazonaws.com/uifaces/faces/twitter/russoedu/128.jpg\"},{\"id\":11,\"first_name\":\"George\",\"last_name\":\"Edwards\",\"avatar\":\"https://s3.amazonaws.com/uifaces/faces/twitter/mrmoiree/128.jpg\"},{\"id\":12,\"first_name\":\"Rachel\",\"last_name\":\"Howell\",\"avatar\":\"https://s3.amazonaws.com/uifaces/faces/twitter/hebertialmeida/128.jpg\"}]}";
    boolean mIsOnline=true;
    ArrayList mtempUserList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIsOnline=PublicDictionary.isOnline(getApplicationContext());
        mUserList =new ArrayList();
        mtempUserList =new ArrayList();
        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setMessage("Loading Data.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
        apiInterface = RetroClient.getClient().create(ApiService.class);
        GetUserList(1,mIsOnline);
       }

    private void GetUserList(int count,final boolean isOnline){
        pDialog.dismiss();
        if(tempCount>4){
            mtempUserList.clear();
            if(!isOnline) {
                mtempUserList = getArrayList("UserList");
            }
            else{
                mtempUserList= mUserList;
            }
            Log.d("TotalUSerList " ,"Userlist  "+ mUserList.size());
//            saveArrayList(mUserList,"UserList");
            recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            eAdapter = new EmployeesAdapter(/*mUserList*/mtempUserList,getApplicationContext(),isOnline);
            RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(eLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(eAdapter);
            Log.d("SharedUSerList " ,"Userlist  "+ mtempUserList .size());
            tempCount=1;
            return ;
        }
        if(isOnline) {
            String pageno = String.valueOf(count);
            Call call2 = apiInterface.doGetUserList(pageno);
            call2.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    try {
                        System.out.println("Response " + response.body().toString().getBytes());
                        userList = (UserList) response.body();
                        Integer text = userList.page;
                        Integer total = userList.total;
                        Integer totalPages = userList.totalPages;
                        ArrayList datumList = userList.data;

                        for ( int i = 0; i < datumList.size(); i++ ) {
                            UserList.UserDatum u = (UserList.UserDatum) datumList.get(i);
                            mUserList.add(u);
                        }
                        tempCount++;
                        GetUserList(tempCount,isOnline);
                    } catch (Exception e) {
                        Log.e("Next request  ", "Exception   " + e);
                    }

                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    call.cancel();
                }
            });
        }
        else{
            String json="";
            if(count==1){
                json=json1;
            }else if(count==2){
                json=json2;
            }
            else if(count==3){
                json=json3;
            }
            else if(count==4){
                json=json4;
            }
            UserList jsonParseResult = new Gson().fromJson(json, UserList.class);
            if (jsonParseResult != null && jsonParseResult.getResult() != null) {
                for (UserList.UserDatum result : jsonParseResult.getResult()) {
                    mUserList.add(result);
                    Log.d("SharedUSerList ", "Result: " + result.id +" === "+result.last_name+" ----- "+result.first_name+" ----- "+result.avatar);
                }
            }
            tempCount++;
            if(tempCount>4&&!isOnline){
                saveArrayList(mUserList,"UserList");
            }
            GetUserList(tempCount,isOnline);
        }
    }

    public void saveArrayList(ArrayList<UserList.UserDatum> list, String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!
    }

    public ArrayList<UserList.UserDatum> getArrayList(String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<UserList.UserDatum>>() {}.getType();
        return gson.fromJson(json, type);
    }
    public void DisplayUserDataOffline(){
        UserList jsonParseResult = new Gson().fromJson(json1, UserList.class);
        if (jsonParseResult != null && jsonParseResult.getResult() != null) {
            for (UserList.UserDatum result : jsonParseResult.getResult()) {
                mUserList.add(result);
                Log.d("SharedUSerList ", "Result: " + result.id +" === "+result.last_name+" ----- "+result.first_name+" ----- "+result.avatar);
            }
        }
    }
}
