package comp7507.api_server.Controller;

import comp7507.api_server.DAO.*;
import comp7507.api_server.Entity.*;
import comp7507.api_server.DAO.*;

import comp7507.api_server.Entity.Objects;
import org.json.simple.JSONObject;
import org.springframework.aop.target.LazyInitTargetSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.util.*;

import org.json.simple.JSONArray;

@CrossOrigin(origins = {"*","null"})
@RestController
public class Controller {
    private JdbcTemplate jdbcTemplate;

    public Controller() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://8.218.186.230:3306/visualizationdb?useSSL=false&useUnicode=true&characterEncoding=utf-8&autoReconnect=true&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true");
        dataSource.setUsername("root");
        dataSource.setPassword("gaoshiji111");
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @RequestMapping("/cars/getAllCars")
    public List getAllCars()
    {
        List result = new ArrayList();
        List overview = new ArrayList();
        List yearly = new ArrayList();
        List allYears = new ArrayList();

        String sqlOverview = "SELECT * FROM allcarbrands";
        List<Map<String, Object>> listOverview =  this.jdbcTemplate.queryForList(sqlOverview);
        for (Map<String, Object> map : listOverview)
        {
            Set<Map.Entry<String, Object>> entries = map.entrySet();
            if (entries != null)
            {
                Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
                Objects one = new Objects();
                while (iterator.hasNext())
                {
                    Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
                    String value = entry.getValue().toString();
                    if(entry.getKey().toString().equals("name"))
                        one.setName(value);
                    if(entry.getKey().toString().equals("value"))
                        one.setValue(Integer.parseInt(value));
                }
                overview.add(one);
            }
        }
        String yearlyCars = "SELECT * FROM yearlyCars GROUP BY YEAR, name ORDER BY YEAR ASC";
        List<Map<String, Object>> yeaylyList =  this.jdbcTemplate.queryForList(yearlyCars);
        for (Map<String, Object> map : yeaylyList)
        {
            Set<Map.Entry<String, Object>> entries = map.entrySet();
            if (entries != null)
            {
                Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
                ObjectWithYear one = new ObjectWithYear();
                while (iterator.hasNext())
                {
                    Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
                    String value = entry.getValue().toString();
                    if(entry.getKey().equals("name"))
                        one.setName(value);
                    if(entry.getKey().toString().equals("value"))
                        one.setValue(Integer.parseInt(value));
                    else if(entry.getKey().toString().equals("YEAR"))
                    {
                        one.setYear(Integer.parseInt(value));
                    }
                }
                yearly.add(one);
            }
        }

        result.add(overview);
        result.add(yearly);
        return result;
    }

    @RequestMapping("/cars/getCarsByYear")
    public List getYearlyCars()
    {
        List result = new ArrayList();
        String yearlyCars = "SELECT name, value, year FROM yearlyCars GROUP BY year, name";
        List<Map<String, Object>> yeaylyList =  this.jdbcTemplate.queryForList(yearlyCars);
        for (Map<String, Object> map : yeaylyList)
        {
            Set<Map.Entry<String, Object>> entries = map.entrySet();
            if (entries != null)
            {
                Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
                ObjectWithYear one = new ObjectWithYear();
                while (iterator.hasNext())
                {
                    Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
                    String value = entry.getValue().toString();
                    if(entry.getKey().equals("name"))
                        one.setName(value);
                    if(entry.getKey().toString().equals("value"))
                        one.setValue(Integer.parseInt(value));
                    else if(entry.getKey().toString().equals("year"))
                        one.setYear(Integer.parseInt(value));
                }
                result.add(one);
            }
        }
        return result;
    }

    @RequestMapping("/dayandtime")
    public List getdayofweekandtime(){
        List result=new ArrayList();
        String query="select * from dayofweek_time";
        List<Map<String,Object>> resultList=this.jdbcTemplate.queryForList(query);
        for(Map<String,Object> map:resultList){
            Set<Map.Entry<String, Object>> entries = map.entrySet();
            if (entries != null)
            {
                Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
                DayOfWeekandTime one = new DayOfWeekandTime();
                while (iterator.hasNext())
                {
                    Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
                    String value = entry.getValue().toString();
                    if(entry.getKey().equals("Day_of_week"))
                        one.setDay(value);
                    if(entry.getKey().toString().equals("Time"))
                        one.setTime(value);
                    else if(entry.getKey().toString().equals("Value"))
                        one.setValue(Integer.parseInt(value));
                }
                result.add(one);
            }
        }
        return result;
    }



    @RequestMapping("/enginecapacity")
    public List getEngineCapacity(){
        List result=new ArrayList();
        String query="SELECT `Sex_of_Driver`,`Engine_Capacity_.CC.` FROM `vehicle_information` where (`Sex_of_Driver` =\"female\" or `Sex_of_Driver` =\"male\") and `Engine_Capacity_.CC.`<>\"NA\"";
        List<Map<String,Object>> resultList=this.jdbcTemplate.queryForList(query);
        for(Map<String,Object> map:resultList){
            Set<Map.Entry<String, Object>> entries = map.entrySet();
            if (entries != null)
            {
                Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
                EngineCapacity one = new EngineCapacity();
                while (iterator.hasNext())
                {
                    Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
                    String value = entry.getValue().toString();
                    if(entry.getKey().equals("Engine_Capacity_.CC."))
                        one.setCapacity(value);
                    if(entry.getKey().equals("Sex_of_Driver"))
                        one.setSex(value);
                }
                result.add(one);
            }
        }
        return result;
    }

    @RequestMapping("/gdpAccident")
    public JSONObject getgdp(){
        JSONObject j1=new JSONObject();
        JSONArray a1=new JSONArray();
        String[] s1=new String[20];
        a1.add("Manchester");
        a1.add("Glasgow City");
        a1.add("Bradford");
        a1.add("Liverpool");
        a1.add("Sheffield");
        a1.add("City of Edinburgh");
        a1.add("County Durham");
        a1.add("Bristol, City of");
        a1.add("Lambeth");
        a1.add("Kirklees");
        a1.add("Barnet");
        a1.add("Doncaster");
        a1.add("Ealing");
        a1.add("Brent");
        a1.add("Coventry");
        a1.add("Barnsley");
        a1.add("Oldham");
        a1.add("Bexley");
        a1.add("Bath and North East Somerset");
        a1.add("Angus");
        j1.put("counties",a1);
        for(int i=0;i<s1.length;i++){
            s1[i]=(String)a1.get(i);
        }
        List<String> l1=Arrays.asList(s1);
        JSONArray a2=new JSONArray();
        for(int year=2005;year<2016;year++){
            a2.add(year);
        }
        j1.put("timeline",a2);

        JSONArray a3=new JSONArray();
        JSONArray a4=new JSONArray();
        for(int year=2005;year<2016;year++){
            JSONArray a5=new JSONArray();
            String query="select `GDP`,`Accident`,`Population`,`City` from `gdp_population2` where Year=\""+year+"\"";
            System.out.println(query);
            List<Map<String,Object>> resultList=jdbcTemplate.queryForList(query);
            for(Map<String,Object> map:resultList){
                Set<Map.Entry<String, Object>> entries = map.entrySet();
                if (entries != null)
                {
                    Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
                    EngineCapacity one = new EngineCapacity();
                    while (iterator.hasNext())
                    {
                        Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
                        String value = entry.getValue().toString();
                        if (entry.getKey().equals("GDP"))
                            a5.add(Integer.parseInt(value));
                        if (entry.getKey().equals("Accident"))
                            a5.add(Integer.parseInt(value));
                        if (entry.getKey().equals("Population"))
                            a5.add(Integer.parseInt(value));

                        if (entry.getKey().equals("City")){
                            if(l1.contains(value)) {
                                a5.add(value);
                                a5.add(year);
                                a4.add(a5);
                                a5 = new JSONArray();
                            }else{
                                a5=new JSONArray();
                            }
                        }

                    }
                }
            }
            a3.add(a4);
            a4=new JSONArray();
        }

        //a3.add(a6);
        j1.put("series",a3);
        System.out.println(j1);

        return j1;
    }


    @RequestMapping("/GDP")
    public JSONArray getGDP(){
        JSONObject j1=new JSONObject();
        JSONArray a1=new JSONArray();

        String[] s1=new String[20];
        a1.add("Manchester");

        a1.add("Glasgow City");

        a1.add("Bradford");

        a1.add("Liverpool");

        a1.add("Sheffield");

        a1.add("City of Edinburgh");

        a1.add("County Durham");
        a1.add("Bristol, City of");

        a1.add("Lambeth");

        a1.add("Kirklees");

        a1.add("Barnet");

        a1.add("Doncaster");

        a1.add("Ealing");

        a1.add("Brent");

        a1.add("Coventry");
        a1.add("Barnsley");

        a1.add("Oldham");

        a1.add("Bexley");

        a1.add("Bath and North East Somerset");

        a1.add("Angus");

        for(int i=0;i<s1.length;i++){
            s1[i]=(String)a1.get(i);
        }
        JSONArray a2=new JSONArray();
        a2.add("Income");
        a2.add("Life Expectancy");
        a2.add("Population");
        a2.add("Country");
        a2.add("Year");
        List<String> l1=Arrays.asList(s1);
        JSONArray a3=new JSONArray();
        a3.add(a2);
        for(int year=2005;year<2016;year++){
            JSONArray a5=new JSONArray();
            String query="select `GDP`,`Accident`,`Population`,`City` from `gdp_population2` where Year=\""+year+"\"";
            System.out.println(query);
            List<Map<String,Object>> resultList=jdbcTemplate.queryForList(query);
            for(Map<String,Object> map:resultList){
                Set<Map.Entry<String, Object>> entries = map.entrySet();
                if (entries != null)
                {
                    Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
                    EngineCapacity one = new EngineCapacity();
                    while (iterator.hasNext())
                    {
                        Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
                        String value = entry.getValue().toString();
                        if (entry.getKey().equals("GDP"))
                            a5.add(Integer.parseInt(value));
                        if (entry.getKey().equals("Accident"))
                            a5.add(Integer.parseInt(value));
                        if (entry.getKey().equals("Population"))
                            a5.add(Integer.parseInt(value));

                        if (entry.getKey().equals("City")){
                            if(l1.contains(value)) {
                                a5.add(value);
                                a5.add(year);
                                a3.add(a5);
                                a5 = new JSONArray();
                            }else{
                                a5=new JSONArray();
                            }
                        }

                    }
                }
            }
        }

        //a3.add(a6);
        j1.put("",a3);
        System.out.println(j1);

        return a3;

    }


    @RequestMapping("/calendarData")
    public List getCalendarData()
    {
        List result = new ArrayList();
        List daylyTotalAccident = new ArrayList();
        List daylyLight = new ArrayList();
        List daylyRoad = new ArrayList();
        List dalyWeather = new ArrayList();


        int curMonth = 0;
        int curYear = 2005;
        List oneMonth = new ArrayList();
        List oneYear = new ArrayList();

        // Get the per day's total accidents
        String sql = "SELECT Date, total, Year FROM calendar_data";
        List<Map<String, Object>> queryTotal =  this.jdbcTemplate.queryForList(sql);
        for (Map<String, Object> map : queryTotal)
        {
            Set<Map.Entry<String, Object>> entries = map.entrySet();
            if (entries != null)
            {
                Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
                CalendarPieElement pieElement = new CalendarPieElement();
                List oneDayValue = new ArrayList();
                int count = 0;
                while (iterator.hasNext())
                {
                    Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();

                    String value = entry.getValue().toString();
                    if(entry.getKey().toString().equals("Date"))
                    {
                        count++;
                        String curDate = value;
                        int entryMon = Integer.parseInt(curDate.split("-")[1]);
                        if(curMonth != entryMon)
                        {
                            if(curMonth != 0)
                                oneYear.add(oneMonth);
                            oneMonth = new ArrayList();
                            curMonth = entryMon;
                        }
                        oneDayValue.add(value);
                        if(curMonth == 12 && curYear == 2015 && Integer.parseInt(curDate.split("-")[2]) == 31)
                        {
                            oneYear.add(oneMonth);
                            daylyTotalAccident.add(oneYear);
                        }
                    }
                    if(entry.getKey().toString().equals("total"))
                    {
                        count++;
                        oneDayValue.add(Integer.parseInt(value));
                    }
                    if(entry.getKey().toString().equals("Year"))
                    {
                        count++;
                        int enrtyYear = Integer.parseInt(value);
                        // System.out.println(enrtyYear);
                        if(curYear != enrtyYear)
                        {
                            daylyTotalAccident.add(oneYear);
                            oneYear = new ArrayList();
                            curYear = enrtyYear;
                        }
                    }
                    if(count == 3)
                    {
                        oneMonth.add(oneDayValue);
                        count = 0;
                    }
                }

            }
            // daylyTotalAccident.add(oneYearAccident);
        }

        curMonth = 0;
        curYear = 2005;
        oneMonth = new ArrayList();
        oneYear = new ArrayList();
        String lightSql = "SELECT Date, Year, Daylight, Darkness_lights_lit, Darkness_lights_unlit, " +
                "Darkness_no_lighting, Darkness_lighting_unknown, Missing_light FROM calendar_data";
        List<Map<String, Object>> queryLight = this.jdbcTemplate.queryForList(lightSql);

        for (Map<String, Object> map : queryLight)
        {
            Set<Map.Entry<String, Object>> entries = map.entrySet();
            if (entries != null)
            {
                Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
                List oneDayValue = new ArrayList();
                List oneDayPie = new ArrayList();
                CalendarOverall calendars = new CalendarOverall();
                int count = 0;
                int weakLightValue = 0;
                int weakLightNumber = 0;
                int noLightValue = 0;
                int noLightNumber = 0;
                while (iterator.hasNext())
                {
                    Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
                    String value = entry.getValue().toString();
                    if(entry.getKey().toString().equals("Date"))
                    {
                        String curDate = value;
                        int entryMon = Integer.parseInt(curDate.split("-")[1]);
                        if(curMonth != entryMon)
                        {
                            if(curMonth != 0)
                                oneYear.add(oneMonth);
                            oneMonth = new ArrayList();
                            curMonth = entryMon;
                        }
                        // oneDayValue.add(value);
                        if(curMonth == 12 && curYear == 2015 && Integer.parseInt(curDate.split("-")[2]) == 31)
                        {
                            oneYear.add(oneMonth);
                            daylyLight.add(oneYear);
                        }
                    }
                    if(entry.getKey().toString().equals("Daylight"))
                    {
                        CalendarPieElement pieElement = new CalendarPieElement();
                        pieElement.setName("Daylight");
                        pieElement.setValue(Integer.parseInt(value));
                        oneDayPie.add(pieElement);
                    }
                    if(entry.getKey().toString().equals("Darkness_lights_lit") ||
                            entry.getKey().toString().equals("Darkness_lights_unlit"))
                    {
                        weakLightNumber++;
                        weakLightValue += Integer.parseInt(value);
                        if(weakLightNumber == 2)
                        {
                            CalendarPieElement pieElement = new CalendarPieElement();
                            pieElement.setName("Weak light");
                            pieElement.setValue(weakLightValue);
                            oneDayPie.add(pieElement);
                        }
                    }
                    if(entry.getKey().toString().equals("Darkness_no_lighting") || entry.getKey().toString().equals("Darkness_lighting_unknown")
                            || entry.getKey().toString().equals("Missing_light"))
                    {
                        noLightNumber++;
                        noLightValue += Integer.parseInt(value);
                        if(noLightNumber == 3)
                        {
                            CalendarPieElement pieElement = new CalendarPieElement();
                            pieElement.setName("No light");
                            pieElement.setValue(noLightValue);
                            oneDayPie.add(pieElement);
                            noLightNumber = 0;
                            noLightValue = 0;
                        }
                    }

                    if(entry.getKey().toString().equals("Year"))
                    {
                        count++;
                        int enrtyYear = Integer.parseInt(value);
                        if(curYear != enrtyYear)
                        {
                            daylyLight.add(oneYear);
                            oneYear = new ArrayList();
                            curYear = enrtyYear;
                        }
                    }
                }
                calendars.setData(oneDayPie);
                // oneDayValue.add(calendars);
                oneMonth.add(calendars);

            }
            // daylyTotalAccident.add(oneYearAccident);
        }


        curMonth = 0;
        curYear = 2005;
        List oneMonth1 = new ArrayList();
        List oneYear1 = new ArrayList();
        String roadSql = "SELECT Date, Year, Dry, Wet_or_damp as Damp, Frost_or_ice as Frost, " +
                "Snow, Flood_over_3cm_deep as Flood FROM calendar_data";
        List<Map<String, Object>> queryRoad =  this.jdbcTemplate.queryForList(roadSql);
        for (Map<String, Object> map : queryRoad)
        {
            Set<Map.Entry<String, Object>> entries = map.entrySet();
            if (entries != null)
            {
                Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
                List oneDayPie = new ArrayList();
                CalendarOverall calendars = new CalendarOverall();
                int count = 0;
                while (iterator.hasNext())
                {
                    Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
                    String value = entry.getValue().toString();
                    if(entry.getKey().toString().equals("Date"))
                    {
                        count++;
                        String curDate = value;
                        int entryMon = Integer.parseInt(curDate.split("-")[1]);
                        if(curMonth != entryMon)
                        {
                            if(curMonth != 0)
                                oneYear1.add(oneMonth1);
                            oneMonth1 = new ArrayList();
                            curMonth = entryMon;
                        }
                        // oneDayValue.add(value);
                        if(curMonth == 12 && curYear == 2015 && Integer.parseInt(curDate.split("-")[2]) == 31)
                        {
                            oneYear1.add(oneMonth1);
                            daylyRoad.add(oneYear1);
                        }
                    }
                    if(entry.getKey().toString().equals("Damp"))
                    {
                        count++;
                        CalendarPieElement pieElement = new CalendarPieElement();
                        pieElement.setName("Damp");
                        pieElement.setValue(Integer.parseInt(value));
                        oneDayPie.add(pieElement);
                    }
                    if(entry.getKey().toString().equals("Dry"))
                    {
                        count++;
                        CalendarPieElement pieElement = new CalendarPieElement();
                        pieElement.setName("Dry");
                        pieElement.setValue(Integer.parseInt(value));
                        oneDayPie.add(pieElement);
                    }
                    if(entry.getKey().toString().equals("Frost"))
                    {
                        count++;
                        CalendarPieElement pieElement = new CalendarPieElement();
                        pieElement.setName("Frost");
                        pieElement.setValue(Integer.parseInt(value));
                        oneDayPie.add(pieElement);
                    }
                    if(entry.getKey().toString().equals("Snow"))
                    {
                        count++;
                        CalendarPieElement pieElement = new CalendarPieElement();
                        pieElement.setName("Snow");
                        pieElement.setValue(Integer.parseInt(value));
                        oneDayPie.add(pieElement);
                    }
                    if(entry.getKey().toString().equals("Flood"))
                    {
                        count++;
                        CalendarPieElement pieElement = new CalendarPieElement();
                        pieElement.setName("Flood");
                        pieElement.setValue(Integer.parseInt(value));
                        oneDayPie.add(pieElement);
                    }
                    if(entry.getKey().toString().equals("Year"))
                    {
                        count++;
                        int enrtyYear = Integer.parseInt(value);
                        if(curYear != enrtyYear)
                        {
                            daylyRoad.add(oneYear1);
                            oneYear1 = new ArrayList();
                            curYear = enrtyYear;
                        }
                    }
                }
                calendars.setData(oneDayPie);
                // oneDayValue.add(calendars);
                oneMonth1.add(calendars);

            }
            // daylyTotalAccident.add(oneYearAccident);
        }


        curMonth = 0;
        curYear = 2005;
        oneMonth = new ArrayList();
        oneYear = new ArrayList();
        String weatherSql = "SELECT Date, Year, Fine_no_high_winds, Raining_no_high_winds, Snowing_no_high_winds, " +
                "Other_weather, Weather_unknow, Missing_weather, Fine_high_winds, Raining_high_winds, Fog_or_mist, Snowing_high_winds FROM calendar_data";
        List<Map<String, Object>> queryWeather =  this.jdbcTemplate.queryForList(weatherSql);
        for (Map<String, Object> map : queryWeather)
        {
            Set<Map.Entry<String, Object>> entries = map.entrySet();
            if (entries != null)
            {
                Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
                List oneDayValue = new ArrayList();
                List oneDayPie = new ArrayList();
                CalendarOverall calendars = new CalendarOverall();
                int count = 0;
                int fog = 0;
                while (iterator.hasNext())
                {
                    Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
                    String value = entry.getValue().toString();
                    if(entry.getKey().toString().equals("Date"))
                    {
                        String curDate = value;
                        int entryMon = Integer.parseInt(curDate.split("-")[1]);
                        if(curMonth != entryMon)
                        {
                            if(curMonth != 0)
                                oneYear.add(oneMonth);
                            oneMonth = new ArrayList();
                            curMonth = entryMon;
                        }
                        // oneDayValue.add(value);
                        if(curMonth == 12 && curYear == 2015 && Integer.parseInt(curDate.split("-")[2]) == 31)
                        {
                            oneYear.add(oneMonth);
                            dalyWeather.add(oneYear);
                        }
                    }
                    if(entry.getKey().toString().equals("Raining_no_high_winds"))
                    {
                        CalendarPieElement pieElement = new CalendarPieElement();
                        pieElement.setName("Rain");
                        pieElement.setValue(Integer.parseInt(value));
                        oneDayPie.add(pieElement);
                    }
                    if(entry.getKey().toString().equals("Fine_no_high_winds"))
                    {
                        CalendarPieElement pieElement = new CalendarPieElement();
                        pieElement.setName("Fine");
                        pieElement.setValue(Integer.parseInt(value));
                        oneDayPie.add(pieElement);
                    }
                    if(entry.getKey().toString().equals("Snowing_no_high_winds"))
                    {
                        CalendarPieElement pieElement = new CalendarPieElement();
                        pieElement.setName("Snow");
                        pieElement.setValue(Integer.parseInt(value));
                        oneDayPie.add(pieElement);
                    }
                    if(entry.getKey().toString().equals("Fine_high_winds"))
                    {
                        CalendarPieElement pieElement = new CalendarPieElement();
                        pieElement.setName("High winds");
                        pieElement.setValue(Integer.parseInt(value));
                        oneDayPie.add(pieElement);
                    }
                    if(entry.getKey().toString().equals("Raining_high_winds"))
                    {
                        CalendarPieElement pieElement = new CalendarPieElement();
                        pieElement.setName("Rain & high winds");
                        pieElement.setValue(Integer.parseInt(value));
                        oneDayPie.add(pieElement);
                    }
                    if(entry.getKey().toString().equals("Fog_or_mist") || entry.getKey().toString().equals("Other_weather")
                            || entry.getKey().toString().equals("Missing_weather") || entry.getKey().toString().equals("Weather_unknow"))
                    {
                        count++;
                        fog += Integer.parseInt(value);
                        if(count == 4)
                        {
                            CalendarPieElement pieElement = new CalendarPieElement();
                            pieElement.setName("Fog");
                            pieElement.setValue(fog);
                            oneDayPie.add(pieElement);
                            count = 0;
                            fog = 0;

                        }
                    }
                    if(entry.getKey().toString().equals("Snowing_high_winds"))
                    {
                        CalendarPieElement pieElement = new CalendarPieElement();
                        pieElement.setName("Snow & high winds");
                        pieElement.setValue(Integer.parseInt(value));
                        oneDayPie.add(pieElement);
                    }
                    if(entry.getKey().toString().equals("Year"))
                    {
                        int enrtyYear = Integer.parseInt(value);
                        if(curYear != enrtyYear)
                        {
                            dalyWeather.add(oneYear);
                            oneYear = new ArrayList();
                            curYear = enrtyYear;
                        }
                    }
                }
                calendars.setData(oneDayPie);
                // oneDayValue.add(calendars);
                oneMonth.add(calendars);

            }
            // daylyTotalAccident.add(oneYearAccident);
        }

        result.add(daylyTotalAccident);
        result.add(daylyLight);
        result.add(daylyRoad);
        result.add(dalyWeather);
        return result;
    }

    @RequestMapping("/mapData")
    public List getMapData()
    {
        List result = new ArrayList();
        List total = new ArrayList();
        List y_05 = new ArrayList();
        List y_06 = new ArrayList();
        List y_07 = new ArrayList();
        List y_08 = new ArrayList();
        List y_09 = new ArrayList();
        List y_10 = new ArrayList();
        List y_11 = new ArrayList();
        List y_12 = new ArrayList();
        List y_13 = new ArrayList();
        List y_14 = new ArrayList();
        List y_15 = new ArrayList();
        String mapSqlTotal = "SELECT Location as name, num as value FROM mapdata";
        List<Map<String, Object>> queryTotal =  this.jdbcTemplate.queryForList(mapSqlTotal);
        for (Map<String, Object> map : queryTotal)
        {
            Set<Map.Entry<String, Object>> entries = map.entrySet();
            if (entries != null)
            {
                Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
                Objects oneCity = new Objects();
                while (iterator.hasNext())
                {
                    Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
                    String value = entry.getValue().toString();
                    if(entry.getKey().toString().equals("name"))
                        oneCity.setName(value);
                    if(entry.getKey().toString().equals("value"))
                        oneCity.setValue(Integer.parseInt(value));
                }
                total.add(oneCity);
            }
        }

        String mapSql05 = "SELECT Location as name, y_05 as value FROM mapdata";
        List<Map<String, Object>> query05 =  this.jdbcTemplate.queryForList(mapSql05);
        for (Map<String, Object> map : query05)
        {
            Set<Map.Entry<String, Object>> entries = map.entrySet();
            if (entries != null)
            {
                Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
                Objects oneCity = new Objects();
                while (iterator.hasNext())
                {
                    Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
                    String value = entry.getValue().toString();
                    if(entry.getKey().toString().equals("name"))
                        oneCity.setName(value);
                    if(entry.getKey().toString().equals("value"))
                        oneCity.setValue(Integer.parseInt(value));
                }
                y_05.add(oneCity);
            }
        }

        String mapSql06 = "SELECT Location as name, y_06 as value FROM mapdata";
        List<Map<String, Object>> query06 =  this.jdbcTemplate.queryForList(mapSql06);
        for (Map<String, Object> map : query06)
        {
            Set<Map.Entry<String, Object>> entries = map.entrySet();
            if (entries != null)
            {
                Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
                Objects oneCity = new Objects();
                while (iterator.hasNext())
                {
                    Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
                    String value = entry.getValue().toString();
                    if(entry.getKey().toString().equals("name"))
                        oneCity.setName(value);
                    if(entry.getKey().toString().equals("value"))
                        oneCity.setValue(Integer.parseInt(value));
                }
                y_06.add(oneCity);
            }
        }

        String mapSql07 = "SELECT Location as name, y_07 as value FROM mapdata";
        List<Map<String, Object>> query07 =  this.jdbcTemplate.queryForList(mapSql07);
        for (Map<String, Object> map : query07)
        {
            Set<Map.Entry<String, Object>> entries = map.entrySet();
            if (entries != null)
            {
                Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
                Objects oneCity = new Objects();
                while (iterator.hasNext())
                {
                    Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
                    String value = entry.getValue().toString();
                    if(entry.getKey().toString().equals("name"))
                        oneCity.setName(value);
                    if(entry.getKey().toString().equals("value"))
                        oneCity.setValue(Integer.parseInt(value));
                }
                y_07.add(oneCity);
            }
        }

        String mapSql08 = "SELECT Location as name, y_08 as value FROM mapdata";
        List<Map<String, Object>> query08 =  this.jdbcTemplate.queryForList(mapSql08);
        for (Map<String, Object> map : query08)
        {
            Set<Map.Entry<String, Object>> entries = map.entrySet();
            if (entries != null)
            {
                Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
                Objects oneCity = new Objects();
                while (iterator.hasNext())
                {
                    Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
                    String value = entry.getValue().toString();
                    if(entry.getKey().toString().equals("name"))
                        oneCity.setName(value);
                    if(entry.getKey().toString().equals("value"))
                        oneCity.setValue(Integer.parseInt(value));
                }
                y_08.add(oneCity);
            }
        }

        String mapSql09 = "SELECT Location as name, y_09 as value FROM mapdata";
        List<Map<String, Object>> query09 =  this.jdbcTemplate.queryForList(mapSql09);
        for (Map<String, Object> map : query09)
        {
            Set<Map.Entry<String, Object>> entries = map.entrySet();
            if (entries != null)
            {
                Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
                Objects oneCity = new Objects();
                while (iterator.hasNext())
                {
                    Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
                    String value = entry.getValue().toString();
                    if(entry.getKey().toString().equals("name"))
                        oneCity.setName(value);
                    if(entry.getKey().toString().equals("value"))
                        oneCity.setValue(Integer.parseInt(value));
                }
                y_09.add(oneCity);
            }
        }

        String mapSql10 = "SELECT Location as name, y_10 as value FROM mapdata";
        List<Map<String, Object>> query10 =  this.jdbcTemplate.queryForList(mapSql10);
        for (Map<String, Object> map : query10)
        {
            Set<Map.Entry<String, Object>> entries = map.entrySet();
            if (entries != null)
            {
                Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
                Objects oneCity = new Objects();
                while (iterator.hasNext())
                {
                    Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
                    String value = entry.getValue().toString();
                    if(entry.getKey().toString().equals("name"))
                        oneCity.setName(value);
                    if(entry.getKey().toString().equals("value"))
                        oneCity.setValue(Integer.parseInt(value));
                }
                y_10.add(oneCity);
            }
        }

        String mapSql11 = "SELECT Location as name, y_11 as value FROM mapdata";
        List<Map<String, Object>> query11 =  this.jdbcTemplate.queryForList(mapSql11);
        for (Map<String, Object> map : query11)
        {
            Set<Map.Entry<String, Object>> entries = map.entrySet();
            if (entries != null)
            {
                Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
                Objects oneCity = new Objects();
                while (iterator.hasNext())
                {
                    Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
                    String value = entry.getValue().toString();
                    if(entry.getKey().toString().equals("name"))
                        oneCity.setName(value);
                    if(entry.getKey().toString().equals("value"))
                        oneCity.setValue(Integer.parseInt(value));
                }
                y_11.add(oneCity);
            }
        }

        String mapSql12 = "SELECT Location as name, y_12 as value FROM mapdata";
        List<Map<String, Object>> query12 =  this.jdbcTemplate.queryForList(mapSql12);
        for (Map<String, Object> map : query12)
        {
            Set<Map.Entry<String, Object>> entries = map.entrySet();
            if (entries != null)
            {
                Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
                Objects oneCity = new Objects();
                while (iterator.hasNext())
                {
                    Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
                    String value = entry.getValue().toString();
                    if(entry.getKey().toString().equals("name"))
                        oneCity.setName(value);
                    if(entry.getKey().toString().equals("value"))
                        oneCity.setValue(Integer.parseInt(value));
                }
                y_12.add(oneCity);
            }
        }

        String mapSql13 = "SELECT Location as name, y_13 as value FROM mapdata";
        List<Map<String, Object>> query13 =  this.jdbcTemplate.queryForList(mapSql13);
        for (Map<String, Object> map : query13)
        {
            Set<Map.Entry<String, Object>> entries = map.entrySet();
            if (entries != null)
            {
                Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
                Objects oneCity = new Objects();
                while (iterator.hasNext())
                {
                    Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
                    String value = entry.getValue().toString();
                    if(entry.getKey().toString().equals("name"))
                        oneCity.setName(value);
                    if(entry.getKey().toString().equals("value"))
                        oneCity.setValue(Integer.parseInt(value));
                }
                y_13.add(oneCity);
            }
        }

        String mapSql14 = "SELECT Location as name, y_14 as value FROM mapdata";
        List<Map<String, Object>> query14 =  this.jdbcTemplate.queryForList(mapSql14);
        for (Map<String, Object> map : query14)
        {
            Set<Map.Entry<String, Object>> entries = map.entrySet();
            if (entries != null)
            {
                Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
                Objects oneCity = new Objects();
                while (iterator.hasNext())
                {
                    Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
                    String value = entry.getValue().toString();
                    if(entry.getKey().toString().equals("name"))
                        oneCity.setName(value);
                    if(entry.getKey().toString().equals("value"))
                        oneCity.setValue(Integer.parseInt(value));
                }
                y_14.add(oneCity);
            }
        }

        String mapSql15 = "SELECT Location as name, y_15 as value FROM mapdata";
        List<Map<String, Object>> query15 =  this.jdbcTemplate.queryForList(mapSql15);
        for (Map<String, Object> map : query07)
        {
            Set<Map.Entry<String, Object>> entries = map.entrySet();
            if (entries != null)
            {
                Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
                Objects oneCity = new Objects();
                while (iterator.hasNext())
                {
                    Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
                    String value = entry.getValue().toString();
                    if(entry.getKey().toString().equals("name"))
                        oneCity.setName(value);
                    if(entry.getKey().toString().equals("value"))
                        oneCity.setValue(Integer.parseInt(value));
                }
                y_15.add(oneCity);
            }
        }
        result.add(total);
        result.add(y_05);
        result.add(y_06);
        result.add(y_07);
        result.add(y_08);
        result.add(y_09);
        result.add(y_10);
        result.add(y_11);
        result.add(y_12);
        result.add(y_13);
        result.add(y_14);
        result.add(y_15);
        return result;
    }


    @RequestMapping("/Acc/getDailyAcc")
    public List getDailyAcc()
    {
        List result = new ArrayList();
        List daylyTotalAccident = new ArrayList();



        int curMonth = 0;
        int curYear = 0;
        List oneMonth = new ArrayList();
        List oneYear = new ArrayList();

        // Get the per day's total accidents
        String sql = "SELECT a_year, a_month, zero_to_two, two_to_four,four_to_six,six_to_eight,eight_to_ten," +
                "ten_to_twelve,twelve_to_fourteen,fourteen_to_sixteen, sixteen_to_eighteen," +
                "eighteen_to_twenty,twenty_to_twentytwo, twentytwo_to_twentyfour FROM timeline";
        List<Map<String, Object>> queryTotal =  this.jdbcTemplate.queryForList(sql);
        for (Map<String, Object> map : queryTotal)
        {
            Set<Map.Entry<String, Object>> entries = map.entrySet();
            if (entries != null)
            {
                Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
                List oneDayValue = new ArrayList();
                // Objectwithday oneDay = new Objectwithday();
                int count = 0;
                int year=0;
                while (iterator.hasNext())
                {
                    Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
                    String value = entry.getValue().toString();

                    if(entry.getKey().toString().equals("a_month"))
                    {
                        // count++;
                        String curMon = value;
                        int entryMon = Integer.parseInt(curMon);
                        if(curMonth != entryMon)
                        {
                            if(curMonth != 0)
                                oneYear.add(oneMonth);
                            oneMonth = new ArrayList();
                            curMonth = entryMon;
                        }
                        // oneDayValue.add(value);
                        if(curMonth == 12 && curYear == 2015 && count == 12)
                        {
                            count = 0;
                            oneYear.add(oneMonth);
                            daylyTotalAccident.add(oneYear);
                        }
                    }
                    if(entry.getKey().toString().equals("zero_to_two"))
                    {
                        count++;
                        // System.out.println(value);
                        Objects one = new Objects();
                        one.setName(entry.getKey().toString());
                        one.setValue(Integer.parseInt(value));
                        oneMonth.add(one);
                    }
                    if(entry.getKey().toString().equals("two_to_four"))
                    {
                        count++;
                        Objects one = new Objects();
                        one.setName(entry.getKey().toString());
                        one.setValue(Integer.parseInt(value));

                        oneMonth.add(one);
                    }
                    if(entry.getKey().toString().equals("four_to_six"))
                    {
                        count++;
                        Objects one = new Objects();
                        one.setName(entry.getKey().toString());
                        one.setValue(Integer.parseInt(value));

                        oneMonth.add(one);
                    }
                    if(entry.getKey().toString().equals("six_to_eight"))
                    {
                        count++;
                        Objects one = new Objects();
                        one.setName(entry.getKey().toString());
                        one.setValue(Integer.parseInt(value));

                        oneMonth.add(one);
                    }
                    if(entry.getKey().toString().equals("eight_to_ten"))
                    {
                        count++;
                        Objects one = new Objects();
                        one.setName(entry.getKey().toString());
                        one.setValue(Integer.parseInt(value));

                        oneMonth.add(one);
                    }
                    if(entry.getKey().toString().equals("ten_to_twelve"))
                    {
                        count++;
                        Objects one = new Objects();
                        one.setName(entry.getKey().toString());
                        one.setValue(Integer.parseInt(value));

                        oneMonth.add(one);
                    }
                    if(entry.getKey().toString().equals("twelve_to_fourteen"))
                    {
                        count++;
                        Objects one = new Objects();
                        one.setName(entry.getKey().toString());
                        one.setValue(Integer.parseInt(value));

                        oneMonth.add(one);
                    }
                    if(entry.getKey().toString().equals("fourteen_to_sixteen"))
                    {
                        count++;
                        Objects one = new Objects();
                        one.setName(entry.getKey().toString());
                        one.setValue(Integer.parseInt(value));

                        oneMonth.add(one);
                    }

                    if(entry.getKey().toString().equals("sixteen_to_eighteen"))
                    {
                        count++;
                        Objects one = new Objects();
                        one.setName(entry.getKey().toString());
                        one.setValue(Integer.parseInt(value));

                        oneMonth.add(one);
                    }
                    if(entry.getKey().toString().equals("eighteen_to_twenty"))
                    {
                        count++;
                        Objects one = new Objects();
                        one.setName(entry.getKey().toString());
                        one.setValue(Integer.parseInt(value));

                        oneMonth.add(one);
                    }
                    if(entry.getKey().toString().equals("twenty_to_twentytwo"))
                    {
                        count++;
                        Objects one = new Objects();
                        one.setName(entry.getKey().toString());
                        one.setValue(Integer.parseInt(value));

                        oneMonth.add(one);
                    }
                    if(entry.getKey().toString().equals("twentytwo_to_twentyfour"))
                    {
                        count++;
                        Objects one = new Objects();
                        one.setName(entry.getKey().toString());
                        one.setValue(Integer.parseInt(value));

                        oneMonth.add(one);
                    }
                    if(entry.getKey().toString().equals("a_year"))
                    {
                        // count++;
                        int enrtyYear = Integer.parseInt(value);
                        // accident_dailyaccident_daily;
                        if(curYear != enrtyYear)
                        {
                            if(curYear != 0)
                            {
                                System.out.println(curYear + " " + curMonth);
                                daylyTotalAccident.add(oneYear);
                            }
                            oneYear = new ArrayList();
                            curYear = enrtyYear;
                        }
                    }
                }
                //oneDayValue.add(one);
            }
            // daylyTotalAccident.add(oneYearAccident);
        }


        // result.add(daylyTotalAccident);
        return daylyTotalAccident;
    }

    @RequestMapping("/vehicle/types")
    public List getVehicleTypes()
    {
        List result = new ArrayList();
        String sql = "SELECT * FROM vehicletypes ORDER BY year";

        List<Map<String, Object>> query =  this.jdbcTemplate.queryForList(sql);
        for (Map<String, Object> map : query)
        {
            Set<Map.Entry<String, Object>> entries = map.entrySet();
            if (entries != null)
            {
                Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
                SunBurst motor = new SunBurst();
                SunBurst cars = new SunBurst();
                SunBurst bus = new SunBurst();
                SunBurst trucks = new SunBurst();
                SunBurst scooter = new SunBurst();

                List childrenMotor = new ArrayList();
                List childrenCars = new ArrayList();
                List childrenBus = new ArrayList();
                List childrenTrucks = new ArrayList();
                List childrenScooter = new ArrayList();
                List oneYear = new ArrayList();
                List oneYearTotal = new ArrayList();

                while (iterator.hasNext())
                {
                    Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
                    String value = entry.getValue().toString();
                    if(entry.getKey().toString().equals("MOTORCYCLE"))
                    {
                        motor.setName("Motorcycle");
                        Objects one = new Objects();
                        one.setName("Motorcycle");
                        one.setValue(Integer.parseInt(value));
                        oneYearTotal.add(one);
                    }
                    if(entry.getKey().toString().equals("MOTORCYCLE_50"))
                    {
                        Objects oneType = new Objects();
                        oneType.setName("Motor<50cc");
                        oneType.setValue(Integer.parseInt(value));
                        childrenMotor.add(oneType);
                    }
                    if(entry.getKey().toString().equals("MOTORCYCLE_125_50"))
                    {
                        Objects oneType = new Objects();
                        oneType.setName("Motor(50~125cc)");
                        oneType.setValue(Integer.parseInt(value));
                        childrenMotor.add(oneType);
                    }
                    if(entry.getKey().toString().equals("MOTORCYCLE_125_500"))
                    {
                        Objects oneType = new Objects();
                        oneType.setName("Motor(125~500cc)");
                        oneType.setValue(Integer.parseInt(value));
                        childrenMotor.add(oneType);
                    }
                    if(entry.getKey().toString().equals("MOTORCYCLE_500"))
                    {
                        Objects oneType = new Objects();
                        oneType.setName("Motor>500cc");
                        oneType.setValue(Integer.parseInt(value));
                        childrenMotor.add(oneType);
                    }
                    if(entry.getKey().toString().equals("CARS"))
                    {
                        cars.setName("Car");
                        Objects one = new Objects();
                        one.setName("Car");
                        one.setValue(Integer.parseInt(value));
                        oneYearTotal.add(one);
                    }
                    if(entry.getKey().toString().equals("TAXI"))
                    {
                        Objects oneType = new Objects();
                        oneType.setName("Taxis");
                        oneType.setValue(Integer.parseInt(value));
                        childrenCars.add(oneType);
                    }
                    if(entry.getKey().toString().equals("PRIVATE_CARS"))
                    {
                        Objects oneType = new Objects();
                        oneType.setName("Private Cars");
                        oneType.setValue(Integer.parseInt(value));
                        childrenCars.add(oneType);
                    }
                    if(entry.getKey().toString().equals("BUS"))
                    {
                        bus.setName("Bus");
                        Objects one = new Objects();
                        one.setName("Bus");
                        one.setValue(Integer.parseInt(value));
                        oneYearTotal.add(one);
                    }
                    if(entry.getKey().toString().equals("MINIBUS"))
                    {
                        Objects oneType = new Objects();
                        oneType.setName("Load<17");
                        oneType.setValue(Integer.parseInt(value));
                        childrenBus.add(oneType);
                    }
                    if(entry.getKey().toString().equals("COACH"))
                    {
                        Objects oneType = new Objects();
                        oneType.setName("Load>17");
                        oneType.setValue(Integer.parseInt(value));
                        childrenBus.add(oneType);
                    }

                    if(entry.getKey().toString().equals("TRUCK"))
                    {
                        trucks.setName("Truck");
                        Objects one = new Objects();
                        one.setName("Truck");
                        one.setValue(Integer.parseInt(value));
                        oneYearTotal.add(one);
                    }
                    if(entry.getKey().toString().equals("TRUCKS_35TONNES"))
                    {
                        Objects oneType = new Objects();
                        oneType.setName("Goods<3.5t");
                        oneType.setValue(Integer.parseInt(value));
                        childrenTrucks.add(oneType);
                    }
                    if(entry.getKey().toString().equals("TRUCKS_35_75TONNES"))
                    {
                        Objects oneType = new Objects();
                        oneType.setName("Goods 3.5~7.5t");
                        oneType.setValue(Integer.parseInt(value));
                        childrenTrucks.add(oneType);
                    }
                    if(entry.getKey().toString().equals("TRUCKS_75TONNES"))
                    {
                        Objects oneType = new Objects();
                        oneType.setName("Goods>7.5t");
                        oneType.setValue(Integer.parseInt(value));
                        childrenTrucks.add(oneType);
                    }
                    if(entry.getKey().toString().equals("SCOOTER"))
                    {
                        scooter.setName("Scooter");
                        Objects one = new Objects();
                        one.setName("Scooter");
                        one.setValue(Integer.parseInt(value));
                        oneYearTotal.add(one);
                    }
                    if(entry.getKey().toString().equals("BIKE"))
                    {
                        Objects oneType = new Objects();
                        oneType.setName("Bike");
                        oneType.setValue(Integer.parseInt(value));
                        childrenScooter.add(oneType);
                    }
                    if(entry.getKey().toString().equals("MOBILITY_SCOOTER"))
                    {
                        Objects oneType = new Objects();
                        oneType.setName("Mobility scooter");
                        oneType.setValue(Integer.parseInt(value));
                        childrenScooter.add(oneType);
                    }

                }
                motor.setChildren(childrenMotor);
                cars.setChildren(childrenCars);
                bus.setChildren(childrenBus);
                trucks.setChildren(childrenTrucks);
                scooter.setChildren(childrenScooter);
                oneYear.add(motor);
                // oneYear.add(cars);
                oneYear.add(bus);
                oneYear.add(trucks);
                oneYear.add(scooter);
                result.add(oneYear);
                result.add(oneYearTotal);
            }
        }

        return result;
    }










    //Get the information for provinces in China, can choose one or all provinces
    @RequestMapping("/get/CurrentProInfo")
    public List getCityInfo(@RequestParam(value = "proName" , required = false, defaultValue = "all") String name)
    {
        CurrentProDAO currentPro = new CurrentProDAO();
        currentPro.reset();
        currentPro.setInput(name);
        currentPro.setJdbc(this.jdbcTemplate);
        currentPro.access();
        return currentPro.getPor();
    }

    @RequestMapping("/hello")
    public String getString()
    {
        return "123";
    }


    @RequestMapping(value = "/get/getProHistoryData")
    public Object getHistoryData(@RequestParam(value = "proName") String name,
                               @RequestParam(value = "date", required = false, defaultValue = "all") String date)
    {
        ProHistoryDAO proHisData = new ProHistoryDAO();
        proHisData.reset();
        proHisData.setParaPro(name);
        proHisData.setParaDate(date);
        proHisData.setJdbc(this.jdbcTemplate);
        proHisData.access();
        return proHisData.returnList();
    }

    @RequestMapping("/get/CurrentChina")
    public Object getNation()
    {
        NationDAO nation = new NationDAO();
        nation.reset();
        nation.setJdbc(this.jdbcTemplate);
        nation.access();
        return nation.getNation();
    }

    @RequestMapping("/get/CurrentCities")
    public Object getProWithCities(@RequestParam(value = "proName", required = false, defaultValue = "all") String name)
    {
        ProvinceWithCitiesDAO cities = new ProvinceWithCitiesDAO();
        cities.reset();
        cities.setJdbc(this.jdbcTemplate);
        cities.setInput(name);
        cities.access();
        return cities.getCityObjects();
    }
    
    @RequestMapping("/get/NationHistory")
    public List<NationHistory> getChinaHistory(@RequestParam(value = "date" , required = false, defaultValue = "all") String date)
    {
        NationHistoryDAO nationalHistory = new NationHistoryDAO();
        nationalHistory.reset();
        nationalHistory.setJdbcTemplate(this.jdbcTemplate);
        nationalHistory.setInput(date);
        nationalHistory.access();
        return nationalHistory.getNationalHistory();
    }
    
    @RequestMapping("/get/CurrentLocation")
    private Object getIpAddress(HttpServletRequest request)
    {
        String clientIp = null;
        try {
            String ip = request.getHeader("x-forwarded-for");
            if (ip == null || ip.length() == 0 || "unknow".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
                ip = request.getHeader("WL-Proxy-Client-IP");
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
                //Get ip address according to network card.
                if (ip.equals("127.0.0.1")) {
                    //Get local ip use network card.
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (Exception e) {
                        clientIp = "112.125.95.205";
                        e.printStackTrace();
                    }
                    ip = inet.getHostAddress();
                }
            }
            //Multi-proxy, the first one is the real ip of client.
            if (ip != null && ip.length() > 15) {
                if (ip.indexOf(",") > 0) {
                    ip = ip.substring(0, ip.indexOf(","));
                }
            }
            clientIp = ip;
        }catch(Exception e)
        {
            clientIp = "112.125.95.205";
        }
        //Note that can not use localhost as an client, or can not return the local data.
        CurrentLocationDAO curPro = new CurrentLocationDAO(clientIp, this.jdbcTemplate);
        curPro.process();
        return curPro.getCurPro();
    }

    @RequestMapping("/NationChart")
    public Object getNationChart(@RequestParam(value = "type") String type)
    {
        NationChartDAO nationChart = new NationChartDAO();
        nationChart.reset();
        nationChart.setInput(type);
        nationChart.setJdbc(this.jdbcTemplate);
        nationChart.access();
        return nationChart.getReturnChart();
    }

    /*
    APIs for others
     */

    @RequestMapping("/MedicalComments")
    public List getMedicalComments()
    {
        MedicalCommentsDAO comments = new MedicalCommentsDAO();
        comments.reset();
        comments.access();
        return comments.getReturn_list();
    }

    @RequestMapping("/CurrentNews")
    public List getCurrentNews()
    {
        CurrentNewsDAO newsDAO = new CurrentNewsDAO();
        newsDAO.reset();
        newsDAO.access();
        return newsDAO.getCurNewsList();
    }

}
