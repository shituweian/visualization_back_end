package scc212.api_server.DAO;

import org.springframework.jdbc.core.JdbcTemplate;
import scc212.api_server.Entity.CityBean;
import scc212.api_server.Entity.NationCity;

import java.util.*;

public class ProvinceWithCitiesDAO
{
    private JdbcTemplate jdbcTemplate;
    private String input = null;
    private String sql = null;
    private NationCity provinces = new NationCity();
    private List<CityBean> city = new ArrayList<CityBean>();
    private CityBean oneCity = new CityBean();
    private List<Map<String, Object>> list;

    public ProvinceWithCitiesDAO()
    {

    }

    public void access()
    {
        sql = "select pinyin, Name, ID from protoen where shortName = '" + input
                + "' or Name = '" + input + "' or pinyin = '" + input + "'";
        List info = this.jdbcTemplate.queryForList(sql);
        String pinyin = info.get(0).toString().split(",")[0].split("=")[1].split("}")[0];
        String Name = info.get(0).toString().split(",")[1].split("=")[1].split("}")[0];
        String proID = info.get(0).toString().split(",")[2].split("=")[1].split("}")[0];
        provinces.setProID(proID);
        provinces.setProNameCN(Name);
        provinces.setProNameEN(pinyin);
        sql = "select * from city where map_province_name = '" + Name + "'";
        List<Map<String, Object>> list =  this.jdbcTemplate.queryForList(sql);
        readInfo(list);
        sortList(city);
    }

    public void readInfo(List<Map<String, Object>> list)
    {
        for (Map<String, Object> map : list)
        {
            Set<Map.Entry<String, Object>> entries = map.entrySet();
            if (entries != null)
            {
                Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
                oneCity = new CityBean();
                while (iterator.hasNext())
                {
                    Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
                    String value = entry.getValue().toString();
                    if(entry.getKey().toString().equals("current_confirmed_count"))
                        oneCity.setCurrentConfirmedCount(Integer.parseInt(value));
                    else if(entry.getKey().toString().equals("confirmed_count"))
                        oneCity.setConfirmedCount(Integer.parseInt(value));
                    else if(entry.getKey().toString().equals("suspected_count"))
                        oneCity.setSusCount(Integer.parseInt(value));
                    else if(entry.getKey().toString().equals("cured_count"))
                        oneCity.setCuredCount(Integer.parseInt(value));
                    else if(entry.getKey().toString().equals("dead_count"))
                        oneCity.setDeadCount(Integer.parseInt(value));
                    else if(entry.getKey().toString().equals("location_id"))
                        oneCity.setID(value);
                    else if(entry.getKey().toString().equals("time"))
                        oneCity.setTime(value);
                    else if(entry.getKey().toString().equals("city_name"))
                        oneCity.setCityName(value);
                }
                city.add(oneCity);
            }
        }
    }

    public void sortList(List<CityBean> city)
    {
        Comparator sorting = new Comparator<CityBean>()
        {
            @Override
            public int compare(CityBean c1, CityBean c2)
            {
                if(c1.getConfirmedCount() < c2.getConfirmedCount())
                    return 1;
                else
                    return -1;
            }
        };
        city.sort(sorting);
    }

    public void setInput(String input)
    {
        this.input = input;
    }

    public void reset()
    {
        input = null;
        sql = null;
        list = null;
        city =  new ArrayList<CityBean>();
        oneCity = new CityBean();
        provinces = new NationCity();
    }

    public void setJdbc(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    public NationCity getCityObjects()
    {
        provinces.setCities(this.city);
        return provinces;
    }
}
