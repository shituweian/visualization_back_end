package scc212.api_server.DAO;

import org.springframework.jdbc.core.JdbcTemplate;
import scc212.api_server.Entity.NationHistory;

import java.util.*;

public class NationHistoryDAO
{
    private JdbcTemplate jdbcTemplate;
    private String input = null;
    private String sql = null;
    private List<NationHistory> nationHistory = new ArrayList<NationHistory>();

    public NationHistoryDAO()
    {

    }

    public void access()
    {
        if(input.equals("all") == false)
            sql = "SELECT * FROM history_sum WHERE date_id = '" + this.input + "'";
        else if(input.equals("all"))
            sql = "SELECT * FROM history_sum";
        List<Map<String, Object>> info = jdbcTemplate.queryForList(sql);
        this.readInfo(info);
    }

    public void readInfo(List<Map<String, Object>> list)
    {
        NationHistory oneDay;
        for (Map<String, Object> map : list)
        {
            Set<Map.Entry<String, Object>> entries = map.entrySet();
            if (entries != null)
            {
                Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
                oneDay = new NationHistory();
                while(iterator.hasNext())
                {
                    Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
                    String value = entry.getValue().toString();
                    String key = entry.getKey().toString();
                    if(key.toString().equals("confirmed_count"))
                        oneDay.setConfirmedCount(Integer.parseInt(value.toString()));
                    else if(key.toString().equals("confirmed_incr"))
                        oneDay.setConfirmedIncr(Integer.parseInt(value.toString()));
                    else if(key.toString().equals("cured_count"))
                        oneDay.setCuredCount(Integer.parseInt(value.toString()));
                    else if(key.toString().equals("cured_incr"))
                        oneDay.setCuredIncr(Integer.parseInt(value.toString()));
                    else if(key.toString().equals("current_confirmed_count"))
                        oneDay.setCurrentConfirmedCount(Integer.parseInt(value.toString()));
                    else if(key.toString().equals("current_confirmed_incr"))
                        oneDay.setCurrentCoonfirmedIncr(Integer.parseInt(value.toString()));
                    else if(key.toString().equals("dead_count"))
                        oneDay.setDeadCount(Integer.parseInt(value.toString()));
                    else if(key.toString().equals("dead_incr"))
                        oneDay.setDeadIncr(Integer.parseInt(value.toString()));
                    else if(key.toString().equals("date_id"))
                        oneDay.setDate(value.toString());
                }
                this.nationHistory.add(oneDay);
            }
        }
    }

    public void reset()
    {
        input = null;
        sql = null;
        nationHistory = new ArrayList<>();
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setInput(String input)
    {
        this.input = input;
    }

    public List<NationHistory> getNationalHistory()
    {
        return this.nationHistory;
    }
}
