package scc212.api_server.DAO;

import org.springframework.jdbc.core.JdbcTemplate;
import scc212.api_server.Entity.NationHistory;
import scc212.api_server.Entity.ProHistoryBean;

import java.util.ArrayList;
import java.util.List;

public class NationHistoryDAO
{
    private JdbcTemplate jdbcTemplate;
    private String input = null;
    private List<ProHistoryBean> provinces = new ArrayList<ProHistoryBean>();
    private String sql = null;
    private NationHistory nationHistory = new NationHistory();
    private HistoryInfoDAO history = new HistoryInfoDAO();
    private int confirmedCount = 0;
    private int confirmedIncr = 0;
    private int curedCount = 0;
    private int curedIncr = 0;
    private int currentConfirmedCount = 0;
    private int currentCoonfirmedIncr = 0;
    private int deadCount = 0;
    private int deadIncr = 0;

    public NationHistoryDAO()
    {

    }

    public void access()
    {
        String id = null;
        sql = "select ID from protoen";
        List<String> allPro = jdbcTemplate.queryForList(sql, String.class);
        history.setJdbc(this.jdbcTemplate);
        for(int i = 0; i < allPro.size(); i++)
        {
            id = allPro.get(i);
            history.reset();
            history.setParaDate(input);
            history.setParaPro(id);
            history.access();
            provinces = history.getProvinces();
            running(history.getProvinces());
        }
        nationHistory.setConfirmedCount(this.confirmedCount);
        nationHistory.setConfirmedIncr(this.confirmedIncr);
        nationHistory.setCurrentConfirmedCount(this.currentConfirmedCount);
        nationHistory.setCurrentCoonfirmedIncr(this.currentCoonfirmedIncr);
        nationHistory.setCuredCount(this.curedCount);
        nationHistory.setCuredIncr(this.curedIncr);
        nationHistory.setDeadCount(this.deadCount);
        nationHistory.setDeadIncr(this.deadIncr);
        nationHistory.setDate(Integer.parseInt(this.input));
    }

    public void running(List<ProHistoryBean> provinces)
    {
        if(provinces.size() != 0)
        {
            confirmedCount = confirmedCount + Integer.parseInt(provinces.get(0).getConfirmedCount());
            confirmedIncr = confirmedIncr + Integer.parseInt(provinces.get(0).getConfirmedIncr());
            curedCount = curedCount + Integer.parseInt(provinces.get(0).getCuredCount());
            curedIncr = curedIncr + Integer.parseInt(provinces.get(0).getCuredIncr());
            currentConfirmedCount = currentConfirmedCount + Integer.parseInt(provinces.get(0).getCurrentConfirmedCount());
            currentCoonfirmedIncr = currentCoonfirmedIncr + Integer.parseInt(provinces.get(0).getCurrentCoonfirmedIncr());
            deadCount = deadCount + Integer.parseInt(provinces.get(0).getDeadCount());
            deadIncr = deadIncr + Integer.parseInt(provinces.get(0).getDeadIncr());
        }
    }

    public void reset()
    {
        provinces = new ArrayList<ProHistoryBean>();
        input = null;
        sql = null;
        nationHistory = new NationHistory();
        confirmedCount = 0;
        confirmedIncr = 0;
        curedCount = 0;
        curedIncr = 0;
        currentConfirmedCount = 0;
        currentCoonfirmedIncr = 0;
        deadCount = 0;
        deadIncr = 0;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setInput(String input)
    {
        this.input = input;
    }

    public NationHistory getNationalHistory()
    {
        return this.nationHistory;
    }
}
