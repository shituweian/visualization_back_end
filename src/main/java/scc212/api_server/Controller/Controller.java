package scc212.api_server.Controller;

import scc212.api_server.DAO.*;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.bind.annotation.*;
import scc212.api_server.Entity.NationHistory;
import scc212.api_server.Entity.ObjectWithYear;
import scc212.api_server.Entity.Objects;
import scc212.api_server.Entity.Years;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.util.*;

@CrossOrigin(origins = {"*","null"})
@RestController
public class Controller {
    private JdbcTemplate jdbcTemplate;

    public Controller() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/comp7014adb?useSSL=false&useUnicode=true&characterEncoding=utf-8&autoReconnect=true&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true");
        dataSource.setUsername("root");
        dataSource.setPassword("0611");
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
        System.out.println("Hello World");
        String sqlAllYearCars = "SELECT * FROM allYearsBrand";
        List<Map<String, Object>> listYearCars =  this.jdbcTemplate.queryForList(sqlAllYearCars);
        for (Map<String, Object> map : listYearCars)
        {
            Set<Map.Entry<String, Object>> entries = map.entrySet();
            if (entries != null) {
                Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
                Years one = new Years();
                while (iterator.hasNext())
                {
                    Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
                    String value = entry.getValue().toString();
                    if(entry.getKey().toString().equals("value"))
                        one.setValue(Integer.parseInt(value));
                    else if(entry.getKey().toString().equals("year"))
                        one.setYear(Integer.parseInt(value));
                }
                allYears.add(one);
            }
        }
        result.add(overview);
        result.add(yearly);
        result.add(allYears);
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
