package scc212.api_server.DAO;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.jdbc.core.JdbcTemplate;
import scc212.api_server.Entity.CurrentLocation;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/*
@ Intro: This class invoke the Baidu IP-location API, used for return the client's current location.
         Using IP to get location means it may not 100% correctly.
         Because Baidu IP-location API does not support the ip address from oversea, which means can oly get location for users in Cina.
         Therefore, if client's ip is not belongs to China, the locator will fail.
@ Author: Tian Yu 17722024
@ Date: 2020.04.18
 */

public class CurrentLocationDAO
{
    private String ip;
    private String url;
    private String key;
    private String coor;
    private String priData;
    private String jsonData;
    private JsonParser jsonParser = new JsonParser();
    private CurrentLocation curPro = new CurrentLocation();
    private CurrentProDAO queryPro = new CurrentProDAO();
    private JdbcTemplate jdbcTemplate;

    public CurrentLocationDAO(String ip, JdbcTemplate jdbcTemplate)
    {
        this.ip = ip;
        this.url = "http://api.map.baidu.com/location/ip?ak=";
        this.key = "kIfM8oS1DpZfZjsbvXYnbuvBOUdSkViH";
        this.coor = "&coor=bd09ll";
        this.priData = request();
        jsonData = null;
        this.jdbcTemplate = jdbcTemplate;
    }

    public void process()
    {
        JsonObject jsonElements = jsonParser.parse(priData).getAsJsonObject();
        JsonElement code = jsonElements.get("status");
        //If get location success
        if(code.toString().equals("0"))
        {
            jsonData = convert(priData);
            JsonObject data1 = jsonParser.parse(jsonData).getAsJsonObject();
            JsonElement content = data1.get("content");
            JsonObject data2 = jsonParser.parse(content.toString()).getAsJsonObject();
            JsonElement address_detail = data2.get("address_detail");
            System.out.println(address_detail.toString());
            String province = address_detail.getAsJsonObject().get("province").toString().substring(1,
                    address_detail.getAsJsonObject().get("province").toString().length() - 1);
            queryPro.reset();
            queryPro.setJdbc(this.jdbcTemplate);
            queryPro.setInput(province);
            queryPro.access();
            this.curPro.setCurProvince(queryPro.getPor());
            this.curPro.setStatus(0);
            this.curPro.setComments("success");
        }
        //If query failed, return data of Beijing
        //Failed conditions: The ip address is not belongs to China.
        else
        {
            this.curPro.setStatus(1);
            this.curPro.setComments("Sorry, the your location ip address unavailable. May be ip is not belongs to China. Return the data for Beijing.");
            queryPro.reset();
            queryPro.setJdbc(this.jdbcTemplate);
            queryPro.setInput("Beijing");
            queryPro.access();
            this.curPro.setCurProvince(queryPro.getPor());
        }
    }



    public String request()
    {
        String httpUrl = this.url + this.key + "&ip=" + this.ip + this.coor;
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //Transfer the unicode to Chinese.
    public String convert(String utfString)
    {
        StringBuilder sb = new StringBuilder();
        int i = -1;
        int pos = 0;
        int  iint = 0;
        while((i=utfString.indexOf("\\u", pos)) != -1){
            String sd = utfString.substring(pos, i);
            sb.append(sd);
            iint = i+5;

            if(iint < utfString.length()){
                pos = i+6;
                sb.append((char)Integer.parseInt(utfString.substring(i+2, i+6), 16));
            }
        }
        String endStr = utfString.substring(iint+1, utfString.length());
        return sb+""+endStr;
    }

    public CurrentLocation getCurPro()
    {
        return curPro;
    }
}
