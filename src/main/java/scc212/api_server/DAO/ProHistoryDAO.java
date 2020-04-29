package scc212.api_server.DAO;

import org.springframework.jdbc.core.JdbcTemplate;
import scc212.api_server.Entity.ProHistory;
import scc212.api_server.Entity.ProHistoryBean;

import java.util.*;

/*
@ Intro: This class aims to return history data for all provinces (one day or all days)
@ Author: Tian Yu 17722024
@ Data: 2020.03.30
 */

public class ProHistoryDAO
{
    private JdbcTemplate jdbcTemplate;
    private String paraPro;
    private String paraDate;
    private String paraLan;
    private String sql = null;
    private String mysql = null;
    private String proNumber = null;
    private String enName = null;
    private String cnName = null;
    private List<ProHistoryBean> provinces = new ArrayList<ProHistoryBean>();
    private ProHistory return_list = new ProHistory();

    public ProHistoryDAO()
    {

    }

    //Access database.
    public void access()
    {
        proNumber = null;
        enName = null;
        cnName = null;
        sql = "SELECT pinyin, ID, shortName FROM protoen WHERE shortName = '" + paraPro
                + "' OR Name = '" + paraPro + "' OR pinyin = '" + paraPro + "' OR ID = '" + paraPro + "'";
        List header = jdbcTemplate.queryForList(sql);
        enName = header.get(0).toString().split(",")[0].split("=")[1].split("}")[0];
        proNumber = header.get(0).toString().split(",")[1].split("=")[1].split("}")[0];
        cnName = header.get(0).toString().split(",")[2].split("=")[1].split("}")[0];
        return_list.setEnName(enName);
        return_list.setProId(proNumber);
        return_list.setCnName(cnName);
        if(this.paraDate.equals("all") == false)
            mysql = "SELECT *  FROM history" +" WHERE date_id = '" + paraDate + "' AND location_id = '" + proNumber + "'";
        else if(this.paraDate.equals("all") == true)
            mysql = "SELECT * FROM history WHERE location_id = '" + proNumber + "'";
        List<Map<String, Object>> list =  this.jdbcTemplate.queryForList(mysql);
        queryData(list);
        return_list.setPros(provinces);
    }

    //Process json data
    public void queryData(List<Map<String, Object>> list)
    {
        for (Map<String, Object> map : list)
        {
            Set<Map.Entry<String, Object>> entries = map.entrySet();
            ProHistoryBean oneDay = new ProHistoryBean();
            if(entries != null)
            {
                Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
                while(iterator.hasNext())
                {
                    Map.Entry<String, Object> entry =(Map.Entry<String, Object>) iterator.next( );
                    Object key = entry.getKey();
                    Object value = entry.getValue();
                    if(key.toString().equals("confirmed_count"))
                        oneDay.setConfirmedCount(value.toString());
                    else if(key.toString().equals("confirmed_incr"))
                        oneDay.setConfirmedIncr(value.toString());
                    else if(key.toString().equals("cured_count"))
                        oneDay.setCuredCount(value.toString());
                    else if(key.toString().equals("cured_incr"))
                        oneDay.setCuredIncr(value.toString());
                    else if(key.toString().equals("current_confirmed_count"))
                        oneDay.setCurrentConfirmedCount(value.toString());
                    else if(key.toString().equals("current_confirmed_incr"))
                        oneDay.setCurrentCoonfirmedIncr(value.toString());
                    else if(key.toString().equals("dead_count"))
                        oneDay.setDeadCount(value.toString());
                    else if(key.toString().equals("dead_incr"))
                        oneDay.setDeadIncr(value.toString());
                    else if(key.toString().equals("date_id"))
                        oneDay.setDate(value.toString());
                }
                provinces.add(oneDay);
            }
        }
    }

    public Object returnList()
    {
        return this.return_list;
    }

    public List<ProHistoryBean> getProvinces()
    {
        return this.provinces;
    }

    public void reset()
    {
        provinces = new ArrayList<ProHistoryBean>();
        this.paraDate = null;
        this.paraPro = null;
    }

    public void setJdbc(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String getParaPro()
    {
        return paraPro;
    }

    public void setParaPro(String paraPro)
    {
        this.paraPro = paraPro;
    }

    public String getParaDate()
    {
        return paraDate;
    }

    public void setParaDate(String paraDate)
    {
        this.paraDate = paraDate;
    }

    public String getParaLan()
    {
        return paraLan;
    }

    public void setParaLan(String paraLan)
    {
        this.paraLan = paraLan;
    }
}
