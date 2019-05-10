package com.pic.optimize.http.test;


import com.pic.optimize.http.api.ApiUtil;

import org.json.JSONObject;

import static com.pic.optimize.http.test.Url.IP;

/**
 * Created by gyj on 2018/4/20.
 */

public class TestDeleteBookApi extends ApiUtil {

    public String mData;

    public TestDeleteBookApi(String id) {
        addParam("bookid",id);
    }

    @Override
    protected String getUrl() {
        return IP +"/api/v1/books";

    }

    @Override
    protected  void parseData(JSONObject jsonObject) throws Exception {
        mData = jsonObject.toString();
    }

    @Override
    protected boolean isBackInMainThread() {
        return true;
    }
}
