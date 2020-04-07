package scc212.api_server.Controller;

import scc212.api_server.DAO.*;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.bind.annotation.*;
import scc212.api_server.Entity.NationHistory;

import java.text.SimpleDateFormat;
import java.util.List;

@CrossOrigin(origins = {"*","null"})
@RestController
public class Controller
{
    private JdbcTemplate jdbcTemplate;
    private CountryDAO country = new CountryDAO();
    private WorldHistoryDAO worldHistory = new WorldHistoryDAO();
    //Tian Yu Added
    private HistoryInfoDAO proHisData = new HistoryInfoDAO();
    private CurrentProDAO currentPro = new CurrentProDAO();
    private NationDAO nation = new NationDAO();
    private ProvinceWithCitiesDAO cities = new ProvinceWithCitiesDAO();
    private NationHistoryDAO nationalHistory = new NationHistoryDAO();
    private NationChartDAO nationChart = new NationChartDAO();


    public Controller()
    {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/covid_19?useSSL=false&useUnicode=true&characterEncoding=utf-8&autoReconnect=true&serverTimezone=Asia/Shanghai");
        dataSource.setUsername("root");
        dataSource.setPassword("2020");
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /*
    For the APIs of the world expect China.
     */
    @RequestMapping("/get/Country")
    public List getCountry(@RequestParam(value = "country" , required = false, defaultValue = "all") String name) {
        country.reset();
        country.setInput(name);
        country.setJdbc(this.jdbcTemplate);
        country.access();
        return country.getCountry();
    }

    @RequestMapping("/get/WorldHistory")
    public List getWorldHistory(@RequestParam(value = "country" , required = false, defaultValue = "all") String name) {
        worldHistory.reset();
        worldHistory.setInput(name);
        worldHistory.setJdbc(this.jdbcTemplate);
        worldHistory.access();
        return worldHistory.getCountry();
    }

    /*
    For the APIs of China
     */

    @RequestMapping("/get/CurrentProInfo")
    public List getCityInfo(@RequestParam(value = "proName" , required = false, defaultValue = "all") String name)
    {
        currentPro.reset();
        currentPro.setInput(name);
        currentPro.setJdbc(this.jdbcTemplate);
        currentPro.access();
        return currentPro.getPor();
    }

    @RequestMapping(value = "/get/getProHistoryData")
    public Object getHistoryData(@RequestParam(value = "proName") String name,
                               @RequestParam(value = "date", required = false, defaultValue = "all") String date)
    {
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
        nation.reset();
        nation.setJdbc(this.jdbcTemplate);
        nation.access();
        return nation.getNation();
    }

    @RequestMapping("/get/CurrentCities")
    public Object getProWithCities(@RequestParam(value = "proName") String name)
    {
        cities.reset();
        cities.setJdbc(this.jdbcTemplate);
        cities.setInput(name);
        cities.access();
        return cities.getCityObjects();
    }
    
    @RequestMapping("/get/NationHistory")
    public List<NationHistory> getChinaHistory(@RequestParam(value = "date" , required = false, defaultValue = "all") String date)
    {
        nationalHistory.reset();
        nationalHistory.setJdbcTemplate(this.jdbcTemplate);
        nationalHistory.setInput(date);
        nationalHistory.access();
        return nationalHistory.getNationalHistory();
    }

    @RequestMapping("/NationChart")
    public Object getNationChart(@RequestParam(value = "type") String type)
    {
        nationChart.reset();
        nationChart.setInput(type);
        nationChart.setJdbc(this.jdbcTemplate);
        nationChart.access();
        return nationChart.getReturnChart();
    }
}
