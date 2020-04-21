package scc212.api_server.Controller;

import scc212.api_server.DAO.*;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.bind.annotation.*;
import scc212.api_server.Entity.Medical_CommentsBean;
import scc212.api_server.Entity.NationHistory;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.List;

@CrossOrigin(origins = {"*","null"})
@RestController
public class Controller
{
    private JdbcTemplate jdbcTemplate;
    private CountryDAO country = new CountryDAO();
    private WorldHistoryDAO worldHistory = new WorldHistoryDAO();
    //private WorldHistorySumDAO worldHistorySum = new WorldHistorySumDAO();
    private KnowledgeDAO knowledge = new KnowledgeDAO();
    //Tian Yu Added
    private ProHistoryDAO proHisData = new ProHistoryDAO();
    //private CurrentProDAO currentPro = new CurrentProDAO();
    private NationDAO nation = new NationDAO();
    //private ProvinceWithCitiesDAO cities = new ProvinceWithCitiesDAO();
    private NationHistoryDAO nationalHistory = new NationHistoryDAO();
    //private NationChartDAO nationChart = new NationChartDAO();
    private MedicalCommentsDAO comments = new MedicalCommentsDAO();
    private CurrentNewsDAO newsDAO = new CurrentNewsDAO();

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
    APIs of the World.
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
    public List getWorldHistory(@RequestParam(value = "country" , required = false, defaultValue = "all") String name,
                                @RequestParam(value = "date", required = false, defaultValue = "all") String inputDate,
                                @RequestParam(value = "startDate", required = false, defaultValue = "none") String startDate,
                                @RequestParam(value = "endDate", required = false, defaultValue = "none") String endDate) {
        worldHistory.reset();
        worldHistory.setInput(name);
        worldHistory.setDate(inputDate);
        worldHistory.setStartDate(startDate);
        worldHistory.setEndDate(endDate);
        worldHistory.setJdbc(this.jdbcTemplate);
        worldHistory.access();
        return worldHistory.getCountry();
    }
    @RequestMapping("/get/WorldHistorySum")
    public List getWorldHistorySum(@RequestParam(value = "name" , required = false, defaultValue = "all") String name,
                                   @RequestParam(value = "date", required = false, defaultValue = "all") String inputDate,
                                   @RequestParam(value = "startDate", required = false, defaultValue = "none") String startDate,
                                   @RequestParam(value = "endDate", required = false, defaultValue = "none") String endDate) {

        WorldHistorySumDAO worldHistorySum = new WorldHistorySumDAO();
        worldHistorySum.reset();
        worldHistorySum.setInput(name);
        worldHistorySum.setDate(inputDate);
        worldHistorySum.setStartDate(startDate);
        worldHistorySum.setEndDate(endDate);
        worldHistorySum.setJdbc(this.jdbcTemplate);
        worldHistorySum.access();
        return worldHistorySum.getData();
    }

    /*
    APIs of China
     */

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
        
        nationalHistory.reset();
        nationalHistory.setJdbcTemplate(this.jdbcTemplate);
        nationalHistory.setInput(date);
        nationalHistory.access();
        return nationalHistory.getNationalHistory();
    }
    
    @RequestMapping("/get/CurrentLocation")
    private Object getIpAddress(HttpServletRequest request)
    {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknow".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length () == 0 || "unknown".equalsIgnoreCase (ip))
            ip = request.getHeader ("WL-Proxy-Client-IP");
        if (ip == null || ip.length () == 0 || "unknown".equalsIgnoreCase (ip)) {
            ip = request.getRemoteAddr ();
            //Get ip address according to network card.
            if (ip.equals ("127.0.0.1"))
            {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost ();
                } catch (Exception e) {
                    e.printStackTrace ();
                }
                ip = inet.getHostAddress ();
            }
        }
        //Multi-proxy, the first one is the real ip of client.
        if (ip != null && ip.length () > 15) {
            if (ip.indexOf (",") > 0)
            {
                ip = ip.substring (0, ip.indexOf (","));
            }
        }
        CurrentLocationDAO curPro = new CurrentLocationDAO(ip, this.jdbcTemplate);
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
    APIs for some news and comments.
     */

    @RequestMapping("/MedicalComments")
    public List getMedicalComments()
    {
        comments.reset();
        comments.access();
        return comments.getReturn_list();
    }

    @RequestMapping("/CurrentNews")
    public List getCurrentNews()
    {
        newsDAO.reset();
        newsDAO.access();
        return newsDAO.getCurNewsList();
    }


    @RequestMapping("/Knowledge")
    public List getKnowledge()
    {
        knowledge.reset();
        knowledge.access();
        return knowledge.getReturn_list();
    }
}
