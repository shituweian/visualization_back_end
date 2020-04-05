package scc212.api_server.DAO;

import org.springframework.jdbc.core.JdbcTemplate;
import scc212.api_server.Entity.CityBean;
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
                cld.add(Calendar.DATE, 3);
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
        if(this.input.equals("overSeaInputTop10"))
            overSeaInput();
        else if(this.input.equals("cityEpidemicCompare"))
            cityEpidemicCompare();
        else if(this.input.equals("nationHistoryChart"))
            nationHistory();
    }

    public void overSeaInput()
    {
        sql = "select map_province_name, confirmed_count, pinyin from city,protoen where city.city_name like '境外%' and protoen.Name=city.map_province_name order by city.confirmed_count DESC ";
        List info = this.jdbcTemplate.queryForList(sql);
        returnChart.setChartName("Oversea Input Top10");
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

    public void cityEpidemicCompare()
    {
        sql = "";
    }

    public void nationHistory()
    {
        returnChart.setChartName("Nation Increase-Information Chart");
            //Initialize the date
        nationHistory.setJdbcTemplate(this.jdbcTemplate);
        for(int i = 0; i < dates.size(); i++)
        {
            returnChart.addEchartX1(dates.get(i));
            nationHistory.reset();
            nationHistory.setInput(dates.get(i));
            nationHistory.access();
            returnChart.addEchartY1(nationHistory.getNationalHistory().getConfirmedIncr());
            returnChart.addEchartY2(nationHistory.getNationalHistory().getDeadIncr());
        }
    }

    public void sortList(List info)
    {

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
