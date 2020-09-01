package com.suek.ex77jsontest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv= findViewById(R.id.tv);
    }

    public void clickBtn(View view) {

        //assets 폴더의 파일을 가져오기 위해 창고관리자(AssetManager) 소환
        AssetManager assetManager= getAssets();

        //assets/test.json 파일을 얻기위한 InputStream
        try {
            InputStream is= assetManager.open("test.json");
            InputStreamReader isr= new InputStreamReader(is);
            BufferedReader reader= new BufferedReader(isr);  //여러줄 받을때
            StringBuffer buffer= new StringBuffer();  //여러줄 받을때
            while (true){
                String line= reader.readLine();
                if(line==null) break;

                buffer.append(line+"\n");
            }

            String jsonData= buffer.toString();

            //tv.setText(jsonData);   //잘읽어지는지 테스트
            //읽어온 jsonData 문자열을 파싱(분석)하기
            /*JSONObject jsonObject= new JSONObject(jsonData);  //파싱이끝남  //JSONObject 안에 name 과 msg 가 있음
            String name= jsonObject.getString("name");
            String message= jsonObject.getString("msg");

            tv.setText("이름 :"+name+"\n메세지 :"+message);*/

            //읽어온 json 문자열이 json [배열]일때는..
            JSONArray jsonArray= new JSONArray(jsonData);

            StringBuffer buffer1= new StringBuffer();
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jo= jsonArray.getJSONObject(i);    //jsonArray 배열안에->String 2개: name, msg

                String name= jo.getString("name");
                String message= jo.getString("msg");
                JSONObject obj= jo.getJSONObject("address");  //jsonArray 배열안에->String 2개: name, msg  --> 또 그안에 jsonOject 객체하나
               String city= obj.getString("city");
                int post= obj.getInt("post");

                buffer1.append(name+", "+message+"==>"+city+","+post+"\n");

            }

            tv.setText(buffer1.toString());


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
