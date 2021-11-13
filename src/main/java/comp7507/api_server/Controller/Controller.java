package comp7507.api_server.Controller;

import comp7507.api_server.DAO.*;
import comp7507.api_server.Entity.*;
import comp7507.api_server.DAO.*;

import comp7507.api_server.Entity.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.util.*;

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
        List<Map<String, Object>> queryLight =  this.jdbcTemplate.queryForList(lightSql);

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
        oneMonth = new ArrayList();
        oneYear = new ArrayList();
        String roadSql = "SELECT Date, Year, Dry, Wet_or_damp as Damp, Frost_or_ice as Frost, " +
                "Snow, Flood_over_3cm_deep as Flood FROM calendar_data";
        List<Map<String, Object>> queryRoad =  this.jdbcTemplate.queryForList(roadSql);
        for (Map<String, Object> map : queryRoad)
        {
            Set<Map.Entry<String, Object>> entries = map.entrySet();
            if (entries != null)
            {
                Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
                List oneDayValue = new ArrayList();
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
                            daylyRoad.add(oneYear);
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
                            daylyLight.add(oneYear);
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
