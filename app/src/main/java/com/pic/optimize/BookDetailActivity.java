package com.pic.optimize;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pic.optimize.http.Book;
import com.pic.optimize.http.UrlConstant;
import com.pic.optimize.http.api.ApiListener;
import com.pic.optimize.http.api.ApiUtil;
import com.pic.optimize.http.test.TestBookApi;
import com.pic.optimize.http.test.TestDeleteBookApi;
import com.pic.optimize.http.test.TestPutBookApi;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BookDetailActivity extends Activity {

    public static final String TAG = BookDetailActivity.class.getSimpleName();
    private TextView mBookName;
    private EditText mBookNameEdit;
    private boolean isAdd;
    private LinearLayout description_layout;
    private EditText mDescriptionEdit;
    private Book mBook;
    private Button mBtnDelete;

    public static void startActivity(Context context, Book book) {
        Intent intent = new Intent();
        intent.putExtra("book",book);
        intent.setClass(context,BookDetailActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_detail);

        Intent intent = getIntent();
        mBook = (Book)intent.getSerializableExtra("book");
        mBookName = (TextView)findViewById(R.id.bookName);
        mBookNameEdit = (EditText)findViewById(R.id.bookname_edit);
        mDescriptionEdit = (EditText) findViewById(R.id.bookdescription_edit);
        mBtnDelete = (Button)findViewById(R.id.btn_delete);

        TextView mSaveBtn = (TextView)findViewById(R.id.save_tv);
        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new InitDataAsyncTask(mBook).execute();
                //如果使用okhttp框架去请求请使用saveBook()
                //saveBook();
            }
        });

        mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DeleteAsyncTask().execute();
                //如果使用okhttp框架去请求请使用deleteBookApi
                //deleteBookApi();
            }
        });

        description_layout = (LinearLayout)findViewById(R.id.description_layout);

        //如果book == null,说明是新增的情况
        if(mBook == null) {
            isAdd = true;
            mBtnDelete.setVisibility(View.GONE);
        }else{
            mBookNameEdit.setText(mBook.bookName);
            mDescriptionEdit.setText(mBook.book_description);
            mBtnDelete.setVisibility(View.VISIBLE);
            isAdd = false;
        }

    }

    private class InitDataAsyncTask extends AsyncTask<Void,Void,Void> {

        private Book mBook;
        public InitDataAsyncTask(Book book) {
            mBook = book;
        }
        @Override
        protected Void doInBackground(Void... params) {

            if(!isAdd) {
                String para = new String("bookid="+mBook.bookid+"&bookName="+mBookNameEdit.getText().toString());
                String response = HttpUtil.put(UrlConstant.URL,para);
                Log.d(TAG,"<<<<<response="+response);
            }else{
                String para = new String("bookDescription="+mDescriptionEdit.getText().toString()+"&bookName="+mBookNameEdit.getText().toString());
                String response = HttpUtil.post(UrlConstant.URL,para);
                Log.d(TAG,"<<<<<response="+response);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            finish();
        }
    }


    private class DeleteAsyncTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... params) {
            String para = new String("bookid="+mBook.bookid);
            HttpUtil.doDelete(UrlConstant.URL,para);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            finish();
        }
    }


    /**********************使用封装okhttp框架去请求数据*******************************/


    private void saveBook() {
        if(isAdd) {
            proceedBookApi();
        }else{
            putBookApi();
        }

    }

    private void deleteBookApi() {
        new TestDeleteBookApi(String.valueOf(mBook.bookid)).delete(new ApiListener() {
            @Override
            public void success(ApiUtil api) {
                Log.d(TAG,"<<<<thread delete="+Thread.currentThread().getName());
                finish();
            }

            @Override
            public void failure(ApiUtil api) {

            }
        });
    }


    private void putBookApi() {
        new TestPutBookApi(String.valueOf(mBook.bookid),
                mBookNameEdit.getText().toString()).put(new ApiListener() {
            @Override
            public void success(ApiUtil api) {
                Log.d(TAG,"<<<<thread="+Thread.currentThread().getName());
                finish();
            }

            @Override
            public void failure(ApiUtil api) {

            }
        });
    }

    private void proceedBookApi() {
        new TestBookApi(mBookNameEdit.getText().toString(),mDescriptionEdit.getText().toString())
                .get(this, new ApiListener(){

                    @Override
                    public void success(ApiUtil api) {
                        Log.d(TAG,"<<<<<<success");
                         finish();
                    }

                    @Override
                    public void failure(ApiUtil api) {
                        Log.d(TAG,"<<<<<<failure");

                    }
                });
    }


    private void useOkHttpSendRequest() {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("bookDescription",mDescriptionEdit.getText().toString())
                .add("bookName",mBookNameEdit.getText().toString()).build();

        final Request request = new Request.Builder()
                .url(UrlConstant.URL)
                .post(body).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG,"<<<<e="+e);

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()) {
                    String d = response.body().string();
                    mBookName.post(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    });
                    Log.d(TAG,"<<<<d="+d);
                }
            }
        });
    }

}
