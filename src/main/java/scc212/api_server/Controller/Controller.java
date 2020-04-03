package scc212.api_server.Controller;

import scc212.api_server.DAO.*;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;

@CrossOrigin(origins = {"*","null"})
@RestController
public class Controller
{
    private String yesterday = null;
	public int kk = 0;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
    private JdbcTemplate jdbcTemplate;
    private CountryDAO country = new CountryDAO();
    private WorldHistoryDAO worldHistory = new WorldHistoryDAO();


    public Controller() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://112.125.95.205:3306/covid_19?useSSL=false&useUnicode=true&characterEncoding=utf-8&autoReconnect=true&serverTimezone=Asia/Shanghai");
        dataSource.setUsername("root");
        dataSource.setPassword("2020");
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

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
}
