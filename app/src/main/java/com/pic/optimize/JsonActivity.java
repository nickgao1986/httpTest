package com.pic.optimize;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.google.gson.Gson;
import com.pic.optimize.json.Course;
import com.pic.optimize.json.CourseTime;
import com.pic.optimize.json.TimeUnit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonActivity extends Activity {

    private TextView mTextView;
    //GSON是Google提供的用来在Java对象和JSON数据之间进行映射的Java类库。可以将一个Json字符转成一个Java对象，或者将一个Java转化为Json字符串。
    //代码量少、简洁,数据传递和解析方便
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.json_create_layout);
        mTextView = (TextView)findViewById(R.id.textview);
        createJson();
    }

    public void createJson() {
        Course course = new Course();
        course.name = "http在安卓中的应用";
        course.author = "nick";
        String[] array = new String[]{"http基础", "json数据解析", "封装okhttp", "经验分享", "总结"};
        List<String> list = new ArrayList<>();
        for (int i=0;i<array.length;i++) {
            list.add(array[i]);
        }
        course.content = list;


        TimeUnit timeUnit1 = new TimeUnit();
        timeUnit1.name = "All";
        timeUnit1.value = 200;
        timeUnit1.unit = "minute";

        TimeUnit timeUnit2 = new TimeUnit();
        timeUnit2.name = "http基础";
        timeUnit2.value = 60;
        timeUnit2.unit = "minute";

        TimeUnit timeUnit3 = new TimeUnit();
        timeUnit3.name = "json数据解析";
        timeUnit3.value = 50;
        timeUnit3.unit = "minute";

        CourseTime courseTime = new CourseTime();
        courseTime.total = timeUnit1;

        List<TimeUnit> list1 = new ArrayList<>();
        list1.add(timeUnit2);
        list1.add(timeUnit3);
        courseTime.data = list1;

        course.time = courseTime;

        String str = "";
        Gson gson = new Gson();
        str = gson.toJson(course);
        mTextView.setText(str);
    }

    //在java语言里，如果字符串里面有引号，那么必须将引号前面加上转义字符\
    private String mJsonStr = "{" +
            "\"name\":\"http在安卓中的应用\"," +
            "\"author\":\"nick\"," +
            "\"content\":[\"http基础\",\"json数据解析\",\"封装okhttp\",\"经验分享\"]" +
            "}";


    private void testJsonObject() {
        try {
            JSONObject jsonObject = new JSONObject(mJsonStr);
            String name = jsonObject.optString("name");
            JSONArray jsonArray = jsonObject.optJSONArray("content");
            mTextView.setText(jsonArray.get(1).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void testGson() {
        Gson gson = new Gson();
        Course course = gson.fromJson(mJsonStr,Course.class);
        mTextView.setText(course.name);
    }




}
