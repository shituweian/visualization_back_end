package scc212.api_server.DAO;

import org.springframework.jdbc.core.JdbcTemplate;
import scc212.api_server.Entity.Province;
import java.util.*;

public class CurrentProDAO
{
    private JdbcTemplate jdbcTemplate;
    private String input;
    private String sql = null;
    private Province queriedPro = new Province();
    private List<Province> allPro = new ArrayList<>();

    public CurrentProDAO()
    {

    }

    public void access()
    {
        if (input.equals("all") == false)
        {
            sql = "select pinyin, shortName from protoen where shortName = '" + input
                    + "' or Name = '" + input + "' or pinyin = '" + input + "'";
            List header = jdbcTemplate.queryForList(sql);
            String pinyin = header.get(0).toString().split(",")[0].split("=")[1].split("}")[0];
            String shortName = header.get(0).toString().split(",")[1].split("=")[1].split("}")[0];
            sql = "select *from province where province_short_name = '" + shortName + "'";
        }
        else if (input.equals("all") == true)
            sql = "select *from province";
        List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
        for (Map<String, Object> map : list)
        {
            Set<Map.Entry<String, Object>> entries = map.entrySet();
            Province one = new Province();
            if (entries != null)
            {
                Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
                    if (entry.getKey().toString().equals("province_name"))
                        one.setNameCN(entry.getValue().toString());
                    else if (entry.getKey().toString().equals("province_short_name"))
                        one.setShortName(entry.getValue().toString());
                    else if (entry.getKey().toString().equals("current_confirmed_count"))
                        one.setCurrentConfirmedCount(Integer.parseInt(entry.getValue().toString()));
                    else if (entry.getKey().toString().equals("confirmed_count"))
                        one.setConfirmedCount(Integer.parseInt(entry.getValue().toString()));
                    else if (entry.getKey().toString().equals("suspected_count"))
                        one.setSuspectedCount(Integer.parseInt(entry.getValue().toString()));
                    else if (entry.getKey().toString().equals("cured_count"))
                        one.setCuredCount(Integer.parseInt(entry.getValue().toString()));
                    else if (entry.getKey().toString().equals("dead_count"))
                        one.setDeadCount(Integer.parseInt(entry.getValue().toString()));
                    else if (entry.getKey().toString().equals("location_id"))
                        one.setLocationId(Integer.parseInt(entry.getValue().toString()));
                }
                String mysql = "select pinyin from protoen where shortName = '" + one.getShortName() + "'";
                String en = jdbcTemplate.queryForObject(mysql, String.class);
                one.setNameEn(en);
                allPro.add(one);
            }
        }
        if(input.equals("all"))
            sortList(allPro);
    }

    public void sortList(List<Province> pros)
    {
        Comparator sorting = new Comparator<Province>()
        {

            @Override
            public int compare(Province p1, Province p2)
            {
                if(p1.getConfirmedCount() < p2.getConfirmedCount())
                    return 1;
                else
                    return -1;
            }
        };
        pros.sort(sorting);
    }

    public String getInput()
    {
        return input;
    }

    public void setInput(String input)
    {
        this.input = input;
    }

    public void reset()
    {
        this.queriedPro = new Province();
        this.allPro = new ArrayList<>();
    }

    public void setJdbc(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Province getQueriedPro()
    {
        return queriedPro;
    }

    public List getPor()
    {
        return allPro;
    }

    public void setQueriedPro(Province queriedPro)
    {
        this.queriedPro = queriedPro;
    }
}