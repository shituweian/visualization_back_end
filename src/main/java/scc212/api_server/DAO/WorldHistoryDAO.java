package scc212.api_server.DAO;

import scc212.api_server.Entity.WorldHistory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.*;

public class WorldHistoryDAO {
    private JdbcTemplate jdbcTemplate;
    private String input;
    private String sql = null;
    private WorldHistory queriedCountry = new WorldHistory();
    private List<WorldHistory> allCountry = new ArrayList<>();

    public WorldHistoryDAO(){

    }

    public void reset()
    {
        this.queriedCountry = new WorldHistory();
        this.allCountry = new ArrayList<>();
    }

    public void setInput(String input)
    {
        this.input = input;
    }

    public void setJdbc(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void access() {
        if (input.equals("all") == false) {
            sql = "select english_name from country_english where short_name = '" + input
                    + "' or chinese_name = '" + input + "' or english_name = '" + input + "'";
            String englishName = jdbcTemplate.queryForObject(sql, String.class);

            sql = "select chinese_name from country_english  where short_name = '" + input
                    + "' or chinese_name = '" + input + "' or english_name = '" + input + "'";
            String chineseName = jdbcTemplate.queryForObject(sql, String.class);

            sql = "select * from foreign_history where area_name = '" + chineseName + "'";
            List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
            for (Map<String, Object> map : list) {
                Set<Map.Entry<String, Object>> entries = map.entrySet();
                WorldHistory temp = new WorldHistory();
                if (entries != null) {
                    Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
                        if (entry.getKey().toString().equals("area_name"))
                            temp.setArea_name(entry.getValue().toString());
                        else if (entry.getKey().toString().equals("confirmed_count"))
                            temp.setConfirmed_count(Integer.parseInt(entry.getValue().toString()));
                        else if (entry.getKey().toString().equals("confirmed_incr"))
                            temp.setConfirmed_incr(Integer.parseInt(entry.getValue().toString()));
                        else if (entry.getKey().toString().equals("cured_count"))
                            temp.setCured_count(Integer.parseInt(entry.getValue().toString()));
                        else if (entry.getKey().toString().equals("cured_incr"))
                            temp.setCured_incr(Integer.parseInt(entry.getValue().toString()));
                        else if (entry.getKey().toString().equals("current_confirmed_count"))
                            temp.setCurrent_confirmed_count(Integer.parseInt(entry.getValue().toString()));
                        else if (entry.getKey().toString().equals("current_confirmed_incr"))
                            temp.setCurrent_confirmed_incr(Integer.parseInt(entry.getValue().toString()));
                        else if (entry.getKey().toString().equals("date_id"))
                            temp.setDate_id(Integer.parseInt(entry.getValue().toString()));
                        else if (entry.getKey().toString().equals("dead_count"))
                            temp.setDead_count(Integer.parseInt(entry.getValue().toString()));
                        else if (entry.getKey().toString().equals("dead_incr"))
                            temp.setDead_incr(Integer.parseInt(entry.getValue().toString()));
                        else if (entry.getKey().toString().equals("location_id"))
                            temp.setLocation_id(Integer.parseInt(entry.getValue().toString()));
                        else if (entry.getKey().toString().equals("time"))
                            temp.setTime(entry.getValue().toString());
                    }

                }
                /*
                String mysql = "select english_name from country_english where chinese_name = '"
                        + temp.getArea_name() + "'";
                String en = jdbcTemplate.queryForObject(mysql, String.class);
                temp.setArea_name_en(en);

                 */
                allCountry.add(temp);

            }
        }
        else if (input.equals("all") == true) {
            sql = "select * from foreign_history";
            List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
            for (Map<String, Object> map : list) {
                Set<Map.Entry<String, Object>> entries = map.entrySet();
                WorldHistory temp = new WorldHistory();
                if (entries != null) {
                    Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
                        if (entry.getKey().toString().equals("area_name"))
                            temp.setArea_name(entry.getValue().toString());
                        else if (entry.getKey().toString().equals("confirmed_count"))
                            temp.setConfirmed_count(Integer.parseInt(entry.getValue().toString()));
                        else if (entry.getKey().toString().equals("confirmed_incr"))
                            temp.setConfirmed_incr(Integer.parseInt(entry.getValue().toString()));
                        else if (entry.getKey().toString().equals("cured_count"))
                            temp.setCured_count(Integer.parseInt(entry.getValue().toString()));
                        else if (entry.getKey().toString().equals("cured_incr"))
                            temp.setCured_incr(Integer.parseInt(entry.getValue().toString()));
                        else if (entry.getKey().toString().equals("current_confirmed_count"))
                            temp.setCurrent_confirmed_count(Integer.parseInt(entry.getValue().toString()));
                        else if (entry.getKey().toString().equals("current_confirmed_incr"))
                            temp.setCurrent_confirmed_incr(Integer.parseInt(entry.getValue().toString()));
                        else if (entry.getKey().toString().equals("date_id"))
                            temp.setDate_id(Integer.parseInt(entry.getValue().toString()));
                        else if (entry.getKey().toString().equals("dead_count"))
                            temp.setDead_count(Integer.parseInt(entry.getValue().toString()));
                        else if (entry.getKey().toString().equals("dead_incr"))
                            temp.setDead_incr(Integer.parseInt(entry.getValue().toString()));
                        else if (entry.getKey().toString().equals("location_id"))
                            temp.setLocation_id(Integer.parseInt(entry.getValue().toString()));
                        else if (entry.getKey().toString().equals("time"))
                            temp.setTime(entry.getValue().toString());
                    }

                }
                /*
                String mysql = "select english_name from country_english where chinese_name = '"
                        + temp.getArea_name() + "'";
                String en = jdbcTemplate.queryForObject(mysql, String.class);
                temp.setArea_name_en(en);

                 */
                allCountry.add(temp);

            }
        }


    }


    public List getCountry() {
        return allCountry;
    }


}
