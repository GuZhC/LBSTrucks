package com.rongyuan.mingyida;

import com.google.gson.Gson;
import com.rongyuan.mingyida.common.http.API;
import com.rongyuan.mingyida.common.http.IHttpClient;
import com.rongyuan.mingyida.common.http.IRequest;
import com.rongyuan.mingyida.common.http.IResponse;
import com.rongyuan.mingyida.common.http.impl.BaseRequest;
import com.rongyuan.mingyida.common.http.impl.OkHttpClientImpl;
import com.rongyuan.mingyida.model.RegisterModel;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by GuZhongCai on 2018/4/12.
 * describe:
 */


public class test {
    @Test
    public  void http() {
        // TODO Auto-generated method stub
        URL url;
        HttpURLConnection con = null;
        try {
            url = new URL("http://www.liuchuan95.cn:3000");
            con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setDoInput(true);//允许读入
            con.setDoOutput(true);//允许输出
            con.setRequestMethod("POST");

            con.setUseCaches(false);//不用缓存
            con.setInstanceFollowRedirects(true);//允许重定向
            con.setRequestProperty("Content-Type", "application/json;charset=utf-8");//设置请求头信息
            con.setRequestProperty("Charset", "UTF-8");
            //设置连接超时和读取超时
            con.setConnectTimeout(10000);
            con.setReadTimeout(10000);
            con.connect();
            BufferedReader reader = null;
//            String message = "{\"class\":\"driver\",\"mark\":\"select_truck\",\"driver_id\":\"d1d1a14e-3c8b-11e8-b371-00ac4c8e9c0f\"}";
            String message = "{\"class\":\"user\",\"mark\":\"register\", \"name\":\"李四\",\"phone_number\":\"18244290923\",\"account\":\"lisi\",\"password\":\"123456\"}";
            DataOutputStream outputSteam = new DataOutputStream(con.getOutputStream());
            byte[] b = message.getBytes("utf-8");
            outputSteam.write(b);
            // System.out.println(message);
            outputSteam.flush();
            outputSteam.close();
            reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            String line;
            String result = "";
            while ((line = reader.readLine()) != null) {
                result += line;
            }
            reader.close();
            con.disconnect();
            System.out.println(result);
            con.getInputStream();    //在这里才正式向服务器端发送请求，否则服务器端将无法打印hello world
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testOKhttp(){
        RegisterModel registerModel = new RegisterModel();
        IRequest request = new BaseRequest(API.TEST_DOMAIN);
        IHttpClient httpClient = new OkHttpClientImpl();
        request.setBody("class", "user");
        request.setBody("mark", "register");
        request.setBody("name", "register");
        request.setBody("phone_number", "12345678");
        request.setBody("account", "12456");
        request.setBody("password", "123456");

        IResponse response = httpClient.post(request,false);
       System.out.print(response.getCode()+"|||"+ response.getData());

    }


}
