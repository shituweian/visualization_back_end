package scc212.api_server.DAO;

import scc212.api_server.Entity.WorldHistorySum;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.*;

public class WorldHistorySumDAO {
    private JdbcTemplate jdbcTemplate;
    private String input;
    private String inputDate;
    private String sql = null;
    private WorldHistorySum queriedCountry;// = new WorldHistory();
    private ArrayList<WorldHistorySum> allCountry;// = new ArrayList<>();
    private ArrayList<WorldHistorySum> data = new ArrayList<WorldHistorySum>();

    public WorldHistorySumDAO(){

    }

    public void reset()
    {
        //this.queriedCountry = new WorldHistory();
        this.data = new ArrayList<WorldHistorySum>();
    }

    public void setInput(String input)
    {
        this.input = input;
    }
    public void setDate(String inputDate) {
        this.inputDate = inputDate;
    }

    public void setJdbc(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void access() {
        if (inputDate.equals("all") == false) {
            if (input.equals("world") == false) {
                sql = "select english_name from continent_english where chinese_name = '" + input + "' or english_name = '" + input + "'";
                String englishName = jdbcTemplate.queryForObject(sql, String.class);

                sql = "select chinese_name from continent_english  where chinese_name = '" + input + "' or english_name = '" + input + "'";
                String chineseName = jdbcTemplate.queryForObject(sql, String.class);
                sql = "SELECT * FROM foreign_continent_sum WHERE continent_name = '" + chineseName + "' AND date_id='" + inputDate + "'";
                queryData(sql);
            }
            else if (input.equals("world") == true) {
                sql = "SELECT * FROM world_sum WHERE date_id='" + inputDate + "'";
                queryData(sql);
            }
        }
        else if (inputDate.equals("all") == true) {
            if (input.equals("world") == false) {
                sql = "select english_name from continent_english where chinese_name = '" + input + "' or english_name = '" + input + "'";
                String englishName = jdbcTemplate.queryForObject(sql, String.class);

                sql = "select chinese_name from continent_english  where chinese_name = '" + input + "' or english_name = '" + input + "'";
                String chineseName = jdbcTemplate.queryForObject(sql, String.class);

                sql = "SELECT * FROM foreign_continent_sum WHERE continent_name = '" + chineseName + "'";
                queryData(sql);
            }
            else if (input.equals("world") == true) {
                sql = "SELECT * FROM world_sum";
                queryData(sql);
            }
        }
    }

    public void queryData(String sql) {
        List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
        for (Map<String, Object> map : list) {
            Set<Map.Entry<String, Object>> entries = map.entrySet();
            WorldHistorySum temp = new WorldHistorySum();
            if (entries != null) {
                Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
                    Object key = entry.getKey();
                    Object value = entry.getValue();
                    if (key.toString().equals("continent_name"))
                        temp.setName(entry.getValue().toString());
                    else if (key.toString().equals("confirmed_count"))
                        temp.setConfirmed_count(Integer.parseInt(value.toString()));
                    else if (key.toString().equals("confirmed_incr"))
                        temp.setConfirmed_incr(Integer.parseInt(value.toString()));
                    else if (key.toString().equals("cured_count"))
                        temp.setCured_count(Integer.parseInt(value.toString()));
                    else if (key.toString().equals("cured_incr"))
                        temp.setCured_incr(Integer.parseInt(value.toString()));
                    else if (key.toString().equals("current_confirmed_count"))
                        temp.setCurrent_confirmed_count(Integer.parseInt(value.toString()));
                    else if (key.toString().equals("current_confirmed_incr"))
                        temp.setCurrent_confirmed_incr(Integer.parseInt(value.toString()));
                    else if (key.toString().equals("date_id"))
                        temp.setDate_id(Integer.parseInt(value.toString()));
                    else if (key.toString().equals("dead_count"))
                        temp.setDead_count(Integer.parseInt(value.toString()));
                    else if (key.toString().equals("dead_incr"))
                        temp.setDead_incr(Integer.parseInt(value.toString()));
                }
                data.add(temp);

            }
                /*
                String mysql = "select english_name from country_english where chinese_name = '"
                        + temp.getArea_name() + "'";
                String en = jdbcTemplate.queryForObject(mysql, String.class);
                temp.setArea_name_en(en);

                 */

        }
    }
    public List getData() {
        return data;
    }


}
