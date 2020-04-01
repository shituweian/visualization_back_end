package scc212.api_server.DAO;

import scc212.api_server.Entity.Country;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.*;

public class CountryDAO {
    private JdbcTemplate jdbcTemplate;
    private String input;
    private String sql = null;
    private Country queriedCountry = new Country();
    private List<Country> allCountry = new ArrayList<>();

    public CountryDAO(){

    }

    public void reset()
    {
        this.queriedCountry = new Country();
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

            sql = "select * from country where country_name = '" + chineseName + "'";
            List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
            for (Map<String, Object> map : list) {
                Set<Map.Entry<String, Object>> entries = map.entrySet();
                if (entries != null) {
                    Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
                        if (entry.getKey().toString().equals("country_name"))
                            queriedCountry.setCountry_name(entry.getValue().toString());
                        else if (entry.getKey().toString().equals("continent_name"))
                            queriedCountry.setContinent_name(entry.getValue().toString());
                        else if (entry.getKey().toString().equals("current_confirmed_count"))
                            queriedCountry.setCurrent_confirmed_count(Integer.parseInt(entry.getValue().toString()));
                        else if (entry.getKey().toString().equals("confirmed_count"))
                            queriedCountry.setConfirmed_count(Integer.parseInt(entry.getValue().toString()));
                        else if (entry.getKey().toString().equals("suspected_count"))
                            queriedCountry.setSuspected_count(Integer.parseInt(entry.getValue().toString()));
                        else if (entry.getKey().toString().equals("cured_count"))
                            queriedCountry.setCured_count(Integer.parseInt(entry.getValue().toString()));
                        else if (entry.getKey().toString().equals("dead_count"))
                            queriedCountry.setDead_count(Integer.parseInt(entry.getValue().toString()));
                        else if (entry.getKey().toString().equals("location_id"))
                            queriedCountry.setLocation_id(Integer.parseInt(entry.getValue().toString()));
                        else if (entry.getKey().toString().equals("time"))
                            queriedCountry.setTime(entry.getValue().toString());
                    }
                }
                queriedCountry.setCountry_name_en(englishName);
                allCountry.add(queriedCountry);
            }
        }
        else if (input.equals("all") == true) {
            sql = "select * from country";
            List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
            for (Map<String, Object> map : list) {
                Set<Map.Entry<String, Object>> entries = map.entrySet();
                Country temp = new Country();
                if (entries != null) {
                    Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
                        if (entry.getKey().toString().equals("country_name"))
                            temp.setCountry_name(entry.getValue().toString());
                        else if (entry.getKey().toString().equals("continent_name"))
                            temp.setContinent_name(entry.getValue().toString());
                        else if (entry.getKey().toString().equals("current_confirmed_count"))
                            temp.setCurrent_confirmed_count(Integer.parseInt(entry.getValue().toString()));
                        else if (entry.getKey().toString().equals("confirmed_count"))
                            temp.setConfirmed_count(Integer.parseInt(entry.getValue().toString()));
                        else if (entry.getKey().toString().equals("suspected_count"))
                            temp.setSuspected_count(Integer.parseInt(entry.getValue().toString()));
                        else if (entry.getKey().toString().equals("cured_count"))
                            temp.setCured_count(Integer.parseInt(entry.getValue().toString()));
                        else if (entry.getKey().toString().equals("dead_count"))
                            temp.setDead_count(Integer.parseInt(entry.getValue().toString()));
                        else if (entry.getKey().toString().equals("location_id"))
                            temp.setLocation_id(Integer.parseInt(entry.getValue().toString()));
                        else if (entry.getKey().toString().equals("time"))
                            temp.setTime(entry.getValue().toString());
                    }

                }
                String mysql = "select english_name from country_english where chinese_name = '"
                        + temp.getCountry_name() + "'";
                String en = jdbcTemplate.queryForObject(mysql, String.class);
                temp.setCountry_name_en(en);
                allCountry.add(temp);

            }
        }


    }


    public List getCountry() {
        return allCountry;
    }


}
