package scc212.api_server.DAO;

import org.springframework.jdbc.core.JdbcTemplate;
import scc212.api_server.Entity.NationChart;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/*
This class aims to return the Apis for drawing the charts in javascript.
 */
public class NationChartDAO
{
    //Jdbc
    private JdbcTemplate jdbcTemplate;
    //Api type
    private String input;
    //Sql query statement
    private String sql;
    private NationChart returnChart = new NationChart();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    private Date date = null;
    private Calendar cld;
    private List<String> dates = new ArrayList<String>();
    private NationHistoryDAO nationHistory = new NationHistoryDAO();
    private HistoryInfoDAO hubei = new HistoryInfoDAO();

    public NationChartDAO()
    {
        try {
            date = dateFormat.parse("20200120");
            Date thisday = new Date();
            String today = dateFormat.format(thisday);
            String oneday = dateFormat.format(date);
            while(Integer.parseInt(oneday) < Integer.parseInt(today))
            {
                cld = Calendar.getInstance();
                cld.setTime(date);
                cld.add(Calendar.DATE, 1);
                date = cld.getTime();
                oneday = dateFormat.format(date);
                if(Integer.parseInt(oneday) > Integer.parseInt(today))
                    break;
                dates.add(oneday);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void access()
    {
        /*
        Over sea input top10 provinces.
         */
        if(this.input.equals("overSeaInputTop10"))
            overSeaInput();
        else if(this.input.equals("provinceCompare"))
            provinceCompare();
        else if(this.input.equals("nationHistoryChart"))
            nationHistory();
        else if(this.input.equals("historyInfoForHubei"))
            historyHubei();
    }

    public void overSeaInput()
    {
        sql = "select map_province_name, confirmed_count, pinyin from city,protoen where city.city_name like '境外%' and protoen.Name=city.map_province_name order by city.confirmed_count DESC ";
        List info = this.jdbcTemplate.queryForList(sql);
        returnChart.setChartName("Oversea Input Top10");
        returnChart.setComment("Oversea Input Top10 of China");
        for(int i = 0; i < 10; i++)
        {
            //Chinese name
            returnChart.addEchartX1(info.get(i).toString().split(",")[0].split("=")[1]);
            //Confirmed count
            returnChart.addEchartY1(Integer.parseInt(info.get(i).toString().split(",")[1].split("=")[1]));
            //English name
            returnChart.addEchartX2(info.get(i).toString().split(",")[2].split("=")[1].split("}")[0]);
        }
    }

    //Compare between provinces of current count.
    public void provinceCompare()
    {
        returnChart.setChartName("Province Compaction Chart");
        returnChart.setComment("x1 is provinces without cases; x2 is provinces have cases; " +
                "y1 is number of province that have and have not cases.");
        sql = "SELECT protoen.pinyin FROM province, protoen WHERE current_confirmed_count=0 and " +
                "province.location_id=protoen.ID";
        List info1 = this.jdbcTemplate.queryForList(sql, String.class);
        sql = "SELECT protoen.pinyin FROM province, protoen WHERE current_confirmed_count!=0 and " +
                "province.location_id=protoen.ID";
        List info2 = this.jdbcTemplate.queryForList(sql, String.class);
        int withoutCase = info1.size();
        int existCase = 34 - withoutCase;
        returnChart.setEchartX1(info1);
        returnChart.setEchartX2(info2);
        returnChart.addEchartY1(withoutCase);
        returnChart.addEchartY1(existCase);
    }

    public void nationHistory()
    {
        returnChart.setChartName("Nation Increase-Information Chart");
        returnChart.setComment("x1 is date; y1 is total confirmed, " +
                "y2 is confirmed increase; y3 is current confirmed count" +
                "y4 is total cured count; y5 is total dead count.");
        //Initialize the date
        nationHistory.setJdbcTemplate(this.jdbcTemplate);
        for(int i = 0; i < dates.size(); i++)
        {
            returnChart.addEchartX1(dates.get(i));
            nationHistory.reset();
            nationHistory.setInput(dates.get(i));
            nationHistory.access();
            //Total confirmed count
            returnChart.addEchartY1(nationHistory.getNationalHistory().getConfirmedCount());
            //Confirmed increase
            returnChart.addEchartY2(nationHistory.getNationalHistory().getConfirmedIncr());
            //Current confirmed
            returnChart.addEchartY3(nationHistory.getNationalHistory().getCurrentConfirmedCount());
            //Total cured count
            returnChart.addEchartY4(nationHistory.getNationalHistory().getCuredCount());
            //Total dead count
            returnChart.addEchartY5(nationHistory.getNationalHistory().getDeadCount());
        }
    }

    //Data for Hubei province.
    public void historyHubei()
    {
        returnChart.setChartName("Hubei covid_19 info");
        returnChart.setComment("x is date; y1 is confirmed count; y2 is cured count; y3 is dead count");
        hubei.setJdbc(this.jdbcTemplate);
        hubei.reset();
        hubei.setParaPro("Hubei");
        hubei.setParaDate("all");
        hubei.access();
        for(int i = 0; i < hubei.getProvinces().size(); i++)
        {
            returnChart.addEchartX1(hubei.getProvinces().get(i).getDate());
            returnChart.addEchartY1(Integer.parseInt(hubei.getProvinces().get(i).getConfirmedCount()));
            returnChart.addEchartY2(Integer.parseInt(hubei.getProvinces().get(i).getCuredCount()));
            returnChart.addEchartY3(Integer.parseInt(hubei.getProvinces().get(i).getDeadCount()));
        }
    }


    public NationChart getReturnChart()
    {
        return returnChart;
    }

    public void setJdbc(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setInput(String input)
    {
        this.input = input;
    }

    public void reset()
    {
        this.sql = null;
        date = null;
        returnChart = new NationChart();
        nationHistory.reset();
    }
}
