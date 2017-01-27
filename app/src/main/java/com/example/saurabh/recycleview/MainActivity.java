package com.example.saurabh.recycleview;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "RecyclerViewExample";

    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter adapter;
    String URL="http://api.androidhive.info/json/movies.json";
    private ProgressDialog pdLoading;
    String[] mResult;

    List<MoviedetailsAdapter> mymovielist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intilizationView();
    }

    private void intilizationView() {

        mRecyclerView= (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        new AsyncMovieDetails().execute();

    }


    //-----------------------------------------service calling---------------------------------

    private class AsyncMovieDetails extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            pdLoading = new android.app.ProgressDialog(MainActivity.this);
            pdLoading.setMessage("please wait...");
            pdLoading.show();
            pdLoading.setCancelable(false);
            pdLoading.setCanceledOnTouchOutside(false);
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {


            return  callService();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            pdLoading.cancel();
            if (pdLoading.isShowing()) {
                pdLoading.dismiss();
            }
            if(result==null){

            } else {

                adapter = new MyRecyclerViewAdapter(MainActivity.this, mymovielist);
                mRecyclerView.setAdapter(adapter);


            }
        }
    }


    public String getObjectvalue(JSONObject listObj, String keyOfObject) {
        try {
            try {
                String mKeyValue = listObj.getString(keyOfObject).toString();
                if (mKeyValue.equals("null")) {
                } else {
                    return mKeyValue;
                }
            } catch (NullPointerException nullExp) {
            }
        } catch (JSONException e) {
        }
        return "";
    }

    private Boolean callService() {
        try {
            Log.e("resultchannel===>", "Service called");
            String resultOutparam = getResponseFromService(URL);
            Log.e("GetConnectorListback", resultOutparam);
            if (resultOutparam == null && resultOutparam.length() == 0) {
                return false;
            } else {
                //isValid=false;
                Log.e("resultchannel===>", resultOutparam);
                mymovielist= parsemovieList(resultOutparam);

                if (mymovielist == null || mymovielist.size() == 0) {
                    return false;
                } else {
                    Log.e("resultchanne2===>", "Service called");
                    return true;
                }
            }
        } catch (Exception e) {
            Log.e("saurabh", e.getMessage());
            return false;
        }
    }

    public List<MoviedetailsAdapter> parsemovieList(String mOutputParam) {
        ArrayList<MoviedetailsAdapter> mUsermovieList = new ArrayList<>();
        try {
            JSONArray moviedetails = new JSONArray(mOutputParam);


            for (int i = 0; i < moviedetails.length(); i++) {
                JSONObject movieobject = moviedetails.getJSONObject(i);
                MoviedetailsAdapter mMovieDetail = new MoviedetailsAdapter();
                mMovieDetail.setMtitle(getObjectvalue(movieobject, "title"));
                mMovieDetail.setMimage(getObjectvalue(movieobject, "image"));
                mMovieDetail.setMrating(getObjectvalue(movieobject, "rating"));


                mMovieDetail.setMreleaseYear(getObjectvalue(movieobject, "releaseYear"));
                JSONArray genreArry = movieobject.getJSONArray("genre");
                ArrayList<String> genre = new ArrayList<String>();
                for (int j = 0; j < genreArry.length(); j++) {
                    genre.add((String) genreArry.get(j));
                }
                mMovieDetail.setGenre(genre);

                mUsermovieList.add(mMovieDetail);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mUsermovieList;
    }


    public String getResponseFromService(String url) {
        String finalResult = null;
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(url);
        if (httpclient ==null) {
        } else {
            HttpResponse response;
            try {
                if (httpget == null) {
                } else {
                    response = httpclient.execute(httpget);
                    int statusCode = response.getStatusLine().getStatusCode();
                    if (statusCode == 404) {
                        return null;
                    }
                    HttpEntity entity = response.getEntity();
                    StringBuffer out = new StringBuffer();
                    byte[] b = EntityUtils.toByteArray(entity);
                    out.append(new String(b, 0, b.length));
                    finalResult = out.toString();
                }

            } catch (ClientProtocolException e) {
                Log.e("REST", "There was a protocol based error", e);
            } catch (IOException e) {
                Log.e("REST", "There was an IO Stream related error", e);
            }
        }

        return finalResult;
    }

}




