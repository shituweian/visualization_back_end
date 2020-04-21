package scc212.api_server.DAO;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.*;
import scc212.api_server.Entity.CurrentNewsBean;
import scc212.api_server.Tools.TransApi;

/*
This class is the data access object for the current news of COVID-19.
Write the data into buffer, if cannot return data, read data from buffer.
Developer: Tian Yu 17722024
At 2020.04.18
 */


public class CurrentNewsDAO
{
    private String url;
    private String key;
    private String jsonResult;
    private JsonObject jsonElements;
    private JsonParser jsonParser;
    private JsonElement newslist;

    private ArrayList<CurrentNewsBean> curNewsList = new ArrayList<CurrentNewsBean>();

    public  CurrentNewsDAO()
    {
        this.url = "http://api.tianapi.com/txapi/ncov/index";
        this.key = "key=df97d4b0225063b8d5996f3b1ece1b0a";
        this.jsonResult = null;
        this.jsonElements = null;
        this.jsonParser = new JsonParser();
        this.newslist = null;
    }


    public void access()
    {
        //The returned json data queried from API.
        jsonResult = request(url, key);
        writeIntoFile(jsonResult);
        processData(jsonResult);
    }

    public void writeIntoFile(String data)
    {
        jsonElements = jsonParser.parse(data).getAsJsonObject();
        JsonElement code = jsonElements.get("code");
        if(code.toString().equals("200"))
        {
            File file = new File("TextResources/NewsJson.txt");
            FileWriter fileWriter = null;
            try {
                file.createNewFile();
                fileWriter = new FileWriter(file);
                fileWriter.write(data);
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void processData(String json)
    {
        jsonElements = jsonParser.parse(json).getAsJsonObject();
        JsonElement code = jsonElements.get("code");
        if(!code.toString().equals("200"))
            json = readFromLocal();
        else
        {
            newslist = jsonElements.get("newslist");
            JsonElement news = newslist.getAsJsonArray().get(0);
            JsonElement newsInfo = news.getAsJsonObject().get("news");
            int size = newsInfo.getAsJsonArray().size();
            for(int i = 0; i < size; i++)
            {
                CurrentNewsBean one = new CurrentNewsBean();
                JsonObject onePiece = (JsonObject) newsInfo.getAsJsonArray().get(i);
                one.setId(Integer.parseInt(onePiece.get("id").toString()));
                Long get = Long.parseLong(onePiece.get("pubDate").toString());
                Date pubTime = new Date(get);
                one.setPubDate(pubTime.toString());

                TransApi title = new TransApi(onePiece.get("title").toString().substring(1, onePiece.get("title").toString().length() - 1));
                String enTitle = title.request();
                if(!enTitle.equals("error"))
                    one.setTitle(enTitle);
                else
                    one.setTitle(onePiece.get("title").toString().substring(1, onePiece.get("title").toString().length() - 1));

                one.setPubDateStr(onePiece.get("pubDateStr").toString().substring(1, onePiece.get("pubDateStr").toString().length() - 1));

                TransApi summary = new TransApi(onePiece.get("summary").toString().substring(1, onePiece.get("summary").toString().length() - 1));
                String enSummary = summary.request();
                if(!enSummary.equals("error"))
                    one.setSummary(enSummary);
                else
                    one.setSummary(onePiece.get("summary").toString().substring(1, onePiece.get("summary").toString().length() - 1));

                TransApi src = new TransApi(onePiece.get("infoSource").toString().substring(1, onePiece.get("infoSource").toString().length() - 1));
                String enSrc = src.request();
                if(!enSrc.equals("error"))
                    one.setInfoSourse(enSrc);
                else
                    one.setInfoSourse(onePiece.get("infoSource").toString().substring(1, onePiece.get("infoSource").toString().length() - 1));
                one.setSourceUrl(onePiece.get("sourceUrl").toString().substring(1, onePiece.get("sourceUrl").toString().length() - 1));
                this.curNewsList.add(one);
            }
        }
    }

    public String readFromLocal()
    {
        String Path = System.getProperty("user.dir");
        File ctoFile = new File(Path + "/TextResources/NewsJson.txt");
        InputStreamReader reading = null;
        try {
            reading = new InputStreamReader(new FileInputStream(ctoFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader counting = new BufferedReader(reading);
        String txtline = null;
        String returnString = null;
        try {
            while ((txtline = counting.readLine()) != null)
                returnString =  txtline;
        }catch (IOException e) {
            e.printStackTrace();
        }
        return returnString;
    }

    public String request(String httpUrl, String httpArg)
    {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        httpUrl = httpUrl + "?" + httpArg;
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

    public void reset()
    {
        this.url = "http://api.tianapi.com/txapi/ncov/index";
        this.key = "key=df97d4b0225063b8d5996f3b1ece1b0a";
        this.jsonResult = null;
        this.jsonElements = null;
        this.jsonParser = new JsonParser();
        this.newslist = null;
        this.curNewsList = new ArrayList<CurrentNewsBean>();
    }

    public ArrayList<CurrentNewsBean> getCurNewsList()
    {
        return this.curNewsList;
    }
}
