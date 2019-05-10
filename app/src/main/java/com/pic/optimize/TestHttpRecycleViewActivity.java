package com.pic.optimize.http;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.pic.optimize.BookDetailActivity;
import com.pic.optimize.HttpUtil;
import com.pic.optimize.R;
import com.pic.optimize.adapter.RecyclerBookAdapter;
import com.pic.optimize.http.api.ApiListener;
import com.pic.optimize.http.api.ApiUtil;
import com.pic.optimize.http.test.TestGetBookApi;

import java.util.ArrayList;


public class TestHttpRecycleViewActivity extends Activity {


    private static final String TAG = TestHttpRecycleViewActivity.class.getSimpleName();

    private RecyclerView mListRecyclerView;

    private RecyclerBookAdapter mBookMainAdapter;
    private ArrayList<Book> mBookList = new ArrayList<>();


    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context,TestHttpRecycleViewActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_list_acitivity);

        mListRecyclerView = (RecyclerView) findViewById(R.id.book_recycler_view);
        mBookMainAdapter = new RecyclerBookAdapter(this, mBookList);

        //你想控制横向或者纵向滑动列表效果可以通过LinearLayoutManager这个类来进行控制
        mListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mListRecyclerView.setAdapter(mBookMainAdapter);

        Button button = (Button)findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookDetailActivity.startActivity(TestHttpRecycleViewActivity.this,null);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        new SendBookListTask().execute();
        //如果使用okhttp框架去请求请使用proceedBookApi();
        //proceedBookApi();
    }

    private class SendBookListTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... params) {
            String response = HttpUtil.get(UrlConstant.URL);
            Log.d(TAG,"response="+response);
            parseResponse(response);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d(TAG,"<<<<<mBookArrayList="+ mBookList);
            mBookMainAdapter.setList(mBookList);
        }
    }

    private void parseResponse(String response) {
        try{
            Gson gson = new Gson();
            BookResponse bookResponse = new Gson().fromJson(response, BookResponse.class);
            mBookList = bookResponse.data;
        }catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    private void proceedBookApi() {
        new TestGetBookApi().get(this, new ApiListener(){

            @Override
            public void success(ApiUtil api) {
                Log.d(TAG,"<<<<<<success");
                TestGetBookApi api1 = (TestGetBookApi)api;
                if(api1.mData != null) {
                    BookResponse response = new Gson().fromJson(api1.mData, BookResponse.class);
                    mBookList = response.data;
                    mListRecyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            mBookMainAdapter.setList(mBookList);
                        }
                    });
                }
            }

            @Override
            public void failure(ApiUtil api) {
                Log.d(TAG,"<<<<<<failure");

            }
        });
    }


}
