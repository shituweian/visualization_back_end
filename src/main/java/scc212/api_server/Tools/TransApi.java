package scc212.api_server.Tools;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;



public class TransApi
{
    private static final String TRANS_API_HOST = "http://fanyi.sogou.com:80/reventondc/api/sogouTranslate";
    private String appid = "68a93c2a5a5f6ce7ba981e2c50dec61f";
    private String key = "c55ab8d63792ee0844b2be9d5f61bab7";
    private String query;
    private JsonObject jsonElements = null;
    private JsonParser jsonParser = new JsonParser();


    public TransApi(String query)
    {
        this.query = query;
        this.query = this.query.replaceAll("“", " ");
        this.query = this.query.replaceAll("”", " ");
        this.query = this.query.replaceAll("#", "");
        this.query = this.query.replaceAll("&", "");
        this.query = this.query.replaceAll("\\\\", "");
    }


    public String request()
    {
        String result = null;
        PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager();
        manager.setMaxTotal(400);
        manager.setDefaultMaxPerRoute(30);
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(manager).build();
        HttpPost httpPost = new HttpPost(TRANS_API_HOST);
        String salt = String.valueOf(System.currentTimeMillis());
        String sign = DigestUtils.md5Hex(appid + query + salt + key);

        List nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("from", "zh-CHS"));
        nvps.add(new BasicNameValuePair("to", "en"));
        nvps.add(new BasicNameValuePair("pid", appid));
        nvps.add(new BasicNameValuePair("q", query));
        nvps.add(new BasicNameValuePair("salt", salt));
        nvps.add(new BasicNameValuePair("sign", sign));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, StandardCharsets.UTF_8));
        HttpRequestBase requestBase = httpPost;
        requestBase.addHeader("content-type", "application/x-www-form-urlencoded");
        requestBase.addHeader("accept", "application/json");
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(requestBase);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
        } catch (IOException e)
        {
            e.printStackTrace();
            return "error";
        }
        jsonElements = jsonParser.parse(result).getAsJsonObject();
        JsonElement errorCode = jsonElements.get("errorCode");
        if(!errorCode.toString().substring(1, errorCode.toString().length() - 1).equals("0"))
            return "error";
        JsonElement translation = jsonElements.get("translation");
        String trans = translation.toString().substring(1, translation.toString().length() - 1);
        return trans;
    }
}
