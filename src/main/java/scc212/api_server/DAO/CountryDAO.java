/**
 * Country API
 * Please see the details in wiki
 *
 * @author Chongyang Zhoao
 *
 * @date 2020/04/29
 */

package scc212.api_server.DAO;

import scc212.api_server.Entity.Country;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.*;

public class CountryDAO {
    private JdbcTemplate jdbcTemplate;
    private String input;
    private String sql = null;
    private String country_name;
    private String country_name_en;
    //private Country queriedCountry = new Country();
    private List<Country> allCountry = new ArrayList<Country>();

    public CountryDAO(){

    }

    public void reset()
    {
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
/*
            sql = "select english_name from country_english where short_name = '" + input
                    + "' or chinese_name = '" + input + "' or english_name = '" + input + "'";
            country_name_en = jdbcTemplate.queryForObject(sql, String.class);
*/
            sql = "select chinese_name from country_english  where short_name = '" + input
                    + "' or chinese_name = '" + input + "' or english_name = '" + input + "'";
            country_name = jdbcTemplate.queryForObject(sql, String.class);
            sql = "select * from country where country_name = '" + country_name + "'";
            queryData(sql);
        }
        else if (input.equals("all") == true) {
            sql = "select * from country";
            queryData(sql);
        }
    }

    public void queryData(String sql) {
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
            String mysql = "select english_name from continent_english where chinese_name = '"
                    + temp.getContinent_name() + "'";
            String en = jdbcTemplate.queryForObject(mysql, String.class);
            temp.setContinent_name_en(en);


            String mysql2 = "select english_name from country_english where chinese_name = '"
                    + temp.getCountry_name() + "'";
            String en2 = jdbcTemplate.queryForObject(mysql2, String.class);

            temp.setCountry_name_en(en2);

            allCountry.add(temp);
        }
    }

    public List getCountry() {
        return allCountry;
    }


}
