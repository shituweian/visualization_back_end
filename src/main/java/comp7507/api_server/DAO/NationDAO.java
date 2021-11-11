package comp7507.api_server.DAO;

import comp7507.api_server.Entity.Nation;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
@ Intro: This class returns the current data of China
@ Author: Tian Yu 17722024
@ Date: 2020.03.29
 */

public class NationDAO
{
    private JdbcTemplate jdbcTemplate;
    private String input;
    private String sql = null;
    private int confirmedCount = 0; //Confirmed number of the province
    private int currentConfirmedCount = 0; //Current existed number
    private int curedCount = 0; // Cured number of the province
    private int deadCount = 0; //Dead number of the province
    private int suspectedCount = 0; //Suspected number the province
    private Nation cn = new Nation();

    public NationDAO()
    {

    }

    public void access()
    {
        sql = "SELECT *FROM province";
        List<Map<String, Object>> list =  this.jdbcTemplate.queryForList(sql);
        for (Map<String, Object> map : list)
        {
            Set<Map.Entry<String, Object>> entries = map.entrySet();
            if (entries != null)
            {
                Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
                while (iterator.hasNext())
                {
                    Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
                    String value = entry.getValue().toString();
                    if(entry.getKey().toString().equals("current_confirmed_count"))
                        currentConfirmedCount = currentConfirmedCount + Integer.parseInt(value);
                    else if(entry.getKey().toString().equals("confirmed_count"))
                        confirmedCount = confirmedCount + Integer.parseInt(value);
                    else if(entry.getKey().toString().equals("suspected_count"))
                        suspectedCount = suspectedCount + Integer.parseInt(value);
                    else if(entry.getKey().toString().equals("cured_count"))
                        curedCount = curedCount + Integer.parseInt(value);
                    else if(entry.getKey().toString().equals("dead_count"))
                        deadCount = deadCount + Integer.parseInt(value);
                }
            }
        }
        sql = "SELECT confirmed_count FROM city WHERE city_name like '境外%'";
        List<Integer> info = this.jdbcTemplate.queryForList(sql, Integer.class);
        int count = 0;
        for(int i = 0; i < info.size(); i++)
            count = count + info.get(i);
        cn.setNameCN("中国");
        cn.setNameEn("China");
        cn.setLocationId("100000");
        cn.setConfirmedCount(String.valueOf(confirmedCount));
        cn.setCurrentConfirmedCount(String.valueOf(currentConfirmedCount));
        cn.setSuspectedCount(String.valueOf(suspectedCount));
        cn.setCuredCount(String.valueOf(curedCount));
        cn.setDeadCount(String.valueOf(deadCount));
        cn.setOverseaInput(String.valueOf(count));
    }

    public Object getNation()
    {
        return this.cn;
    }

    public void reset()
    {
        cn = new Nation();
        this.confirmedCount = 0;
        this.curedCount = 0;
        this.currentConfirmedCount = 0;
        this.deadCount = 0;
        this.suspectedCount = 0;
    }

    public void setJdbc(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }
}
