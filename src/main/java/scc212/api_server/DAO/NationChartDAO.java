package scc212.api_server.DAO;

import org.springframework.jdbc.core.JdbcTemplate;
import scc212.api_server.Entity.CityBean;
import scc212.api_server.Entity.NationChart;

import java.util.ArrayList;
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
    private String sql;
    private NationChart returnChart = new NationChart();
    private List<CityBean> topTen = new ArrayList<CityBean>();




    public void access()
    {
        if(this.input.equals("overSeaInputTop10"))
            overSeaInput();
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
            returnChart.addEchartY(info.get(i).toString().split(",")[1].split("=")[1]);
            //English name
            returnChart.addEchartX2(info.get(i).toString().split(",")[2].split("=")[1].split("}")[0]);
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
        returnChart = new NationChart();
    }
}
