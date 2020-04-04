package scc212.api_server.DAO;

import org.springframework.jdbc.core.JdbcTemplate;
import scc212.api_server.Entity.Nation;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NationDAO
{
    private JdbcTemplate jdbcTemplate;
    private String input;
    private String sql = null;
    private int confirmedCount = 0; //Confirmed number of the province
    private int currentConfirmedCount = 0; //Currented existed number(现存确诊)
    private int curedCount = 0; // Cured number of the province(治愈人数)
    private int deadCount = 0; //Dead number of the province(死亡人数)
    private int suspectedCount = 0; //Suspected number the province(疑似病例)
    private Nation cn = new Nation();

    public NationDAO()
    {

    }

    public void access()
    {
        sql = "select *from province";
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
        cn.setNameCN("中国");
        cn.setNameEn("China");
        cn.setLocationId("100000");
        cn.setConfirmedCount(String.valueOf(confirmedCount));
        cn.setCurrentConfirmedCount(String.valueOf(currentConfirmedCount));
        cn.setSuspectedCount(String.valueOf(suspectedCount));
        cn.setCuredCount(String.valueOf(curedCount));
        cn.setDeadCount(String.valueOf(deadCount));
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