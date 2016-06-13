package com.example.digicorp.testretrofit;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by digicorp on 10/06/16.
 */
public class GreatAndroidAppActivity extends ListActivity implements Callback<StackOverflowQuestions> {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.signup);
       // Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        requestWindowFeature(Window.FEATURE_PROGRESS);
        ArrayAdapter<Question> arrayAdapter =
                new ArrayAdapter<Question>(this,
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        new ArrayList<Question>());
        setListAdapter(arrayAdapter);
        setProgressBarIndeterminateVisibility(true);
        setProgressBarVisibility(true);
        //setSupportActionBar(myToolbar);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.stackexchange.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofit.client().setConnectTimeout(10, TimeUnit.SECONDS);
        System.setProperty("http.proxyHost", "192.168.0.206");
        System.setProperty("http.proxyPort", "8080");
        System.setProperty("https.proxyHost", "192.168.0.206");
        System.setProperty("https.proxyPort", "8080");
        Log.d("retrofit1 ", "hi");
        // prepare call in Retrofit 2.0
        StackOverflowAPI stackOverflowAPI = retrofit.create(StackOverflowAPI.class);
        Log.d("retrofit2 ", "hi");
        Call<StackOverflowQuestions> call = stackOverflowAPI.loadQuestions("android");
        Log.d("retrofit3 ", "hi");
        //asynchronous call
        call.enqueue(this);
        Log.d("hi ", "hi");
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
////            case R.id.about:
////                startActivity(new Intent(this, About.class));
////                return true;
////            case R.id.help:
////                startActivity(new Intent(this, Help.class));
////                return true;
////            default:
                return super.onOptionsItemSelected(item);
//        }
    }

    @Override
    public void onResponse(Response<StackOverflowQuestions> response, Retrofit retrofit) {
        Log.d("done ", response.toString());
        setProgressBarIndeterminateVisibility(false);
        ArrayAdapter<Question> adapter = (ArrayAdapter<Question>) getListAdapter();
        adapter.clear();
        adapter.addAll(response.body().items);
    }

    @Override
    public void onFailure(Throwable t) {
        Log.d("fail ",  t.getLocalizedMessage());
    }


}
