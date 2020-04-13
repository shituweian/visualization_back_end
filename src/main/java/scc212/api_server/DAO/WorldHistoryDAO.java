package scc212.api_server.DAO;

import scc212.api_server.Entity.WorldHistory;
import org.springframework.jdbc.core.JdbcTemplate;
//import scc212.api_server.Entity.WorldHistoryRate;

import java.util.*;

public class WorldHistoryDAO {
    private JdbcTemplate jdbcTemplate;
    private String input;
    private String inputDate;
    private String startDate;
    private String endDate;
    private String sql = null;
    private WorldHistory queriedCountry;// = new WorldHistory();
    private ArrayList<WorldHistory> allCountry;// = new ArrayList<>();
    private ArrayList<WorldHistory> country = new ArrayList<WorldHistory>();

    public WorldHistoryDAO(){

    }

    public void reset()
    {
        //this.queriedCountry = new WorldHistory();
        this.country = new ArrayList<WorldHistory>();
    }

    public void setInput(String input)
    {
        this.input = input;
    }
    public void setDate(String inputDate) {
        this.inputDate = inputDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
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

            if (inputDate.equals("all") == false) {
                    sql = "SELECT * FROM foreign_history WHERE area_name = '" + chineseName +
                            "' AND date_id='" + inputDate + "'";
                    queryData(sql);
            }
            else if (inputDate.equals("all") == true) {
                if (startDate.equals("none") == false) {
                    sql = "SELECT * FROM foreign_history WHERE area_name = '" + chineseName +
                            "' AND (date_id BETWEEN " + startDate + " AND " + endDate+")";
                }
                else {
                    sql = "SELECT * FROM foreign_history WHERE area_name = '" + chineseName +  "'";
                }
                queryData(sql);
            }
        }
        else if (input.equals("all") == true) {
            if (inputDate.equals("all") == false) {
                sql = "SELECT * FROM foreign_history WHERE date_id='" + inputDate + "'";
                queryData(sql);
            }
            else if (inputDate.equals("all") == true) {
                if (startDate.equals("none") == false) {
                    sql = "SELECT * FROM foreign_history WHERE date_id BETWEEN " + startDate + " AND " + endDate;
                }
                else {
                    sql = "SELECT * FROM foreign_history";
                }
                queryData(sql);
            }
        }
    }

    public void queryData(String sql) {
        List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
        for (Map<String, Object> map : list) {
            Set<Map.Entry<String, Object>> entries = map.entrySet();
            WorldHistory temp = new WorldHistory();
            if (entries != null) {
                Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
                    Object key = entry.getKey();
                    Object value = entry.getValue();
                    if (key.toString().equals("area_name"))
                        temp.setArea_name(entry.getValue().toString());
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
                    else if (key.toString().equals("location_id"))
                        temp.setLocation_id(Integer.parseInt(value.toString()));
                    else if (key.toString().equals("time"))
                        temp.setTime(value.toString());
                }


            }

            String mysql = "select english_name from country_english where chinese_name = '"
                    + temp.getArea_name() + "'";
            String en = jdbcTemplate.queryForObject(mysql, String.class);
            temp.setArea_name_en(en);
            country.add(temp);


        }
    }

    public void computRate(ArrayList<WorldHistory> lists) {
        Iterator<WorldHistory> iterator = lists.iterator();
        while (iterator.hasNext()) {
            WorldHistory temp = iterator.next();

            if (temp.getConfirmed_count() == 0){
                temp.setConfirmed_rate(0);
            }
            else {
                temp.setConfirmed_rate((float)temp.getConfirmed_incr()/(float)temp.getConfirmed_count());
            }

            if (temp.getCured_count() == 0){
                temp.setCured_rate(0);
            }
            else {
                temp.setCured_rate((float)temp.getCured_incr()/(float)temp.getCured_count());
            }

            if (temp.getCurrent_confirmed_count() == 0){
                temp.setCurrent_confirmed_rate(0);
            }
            else {
                temp.setCurrent_confirmed_rate((float)temp.getCurrent_confirmed_incr()/(float)temp.getCurrent_confirmed_count());

            }

            if (temp.getDead_count() == 0){
                temp.setDead_rate(0);
            }
            else {
                temp.setDead_rate((float)temp.getDead_incr()/(float)temp.getDead_count());
            }

        }
    }

    public List getCountry() {
        computRate(country);
        return country;
    }


}
