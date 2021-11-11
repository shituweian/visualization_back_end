package comp7507.api_server.DAO;

import comp7507.api_server.Entity.NationChart;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;

/*
@ Intro: This class aims to return the Apis for drawing the charts in javascript.
         Process data in backends.
@ Author: Tian Yu 17722024
@ Date: 2020.04.03
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
    private Date date = null;
    private NationHistoryDAO nationHistory = new NationHistoryDAO();
    private ProHistoryDAO hubei = new ProHistoryDAO();
    private NationHistoryDAO compareNation = new NationHistoryDAO();
    private ProHistoryDAO compareHubei = new ProHistoryDAO();

    public NationChartDAO()
    {

    }

    public void access()
    {
        /*
        Over sea input top10 provinces.
         */
        if(this.input.equals("overSeaInputTop10"))
            overSeaInput();
        else if(this.input.equals("provinceCompareFull"))
            provinceCompareFull();
        else if(this.input.equals("nationHistoryChart"))
            nationHistory();
        else if(this.input.equals("historyInfoForHubei"))
            historyHubei();
        else if(this.input.equals("HubeiCompareOthers"))
            HubeiCompareOthers();
    }

    public void overSeaInput()
    {
        sql = "SELECT map_province_name, confirmed_count, pinyin FROM city,protoen WHERE city.city_name LIKE '境外%' " +
                "AND protoen.Name=city.map_province_name ORDER BY city.confirmed_count DESC ";
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
    public void provinceCompareFull()
    {
        returnChart.setChartName("Province Compaction Chart-Details");
        returnChart.setComment("x1 is provinces without cases; x2 is provinces have cases; " +
                "y1 is number of province that have and have not cases.");
        sql = "SELECT protoen.pinyin FROM province, protoen WHERE current_confirmed_count=0 AND " +
                "province.location_id=protoen.ID";
        List info1 = this.jdbcTemplate.queryForList(sql, String.class);
        sql = "SELECT protoen.pinyin FROM province, protoen WHERE current_confirmed_count!=0 AND " +
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
        nationHistory.reset();
        nationHistory.setInput("all");
        nationHistory.access();

        for(int i = 0; i < nationHistory.getNationalHistory().size(); i++)
        {
            returnChart.addEchartX1(nationHistory.getNationalHistory().get(i).getDate());
            returnChart.addEchartY1(nationHistory.getNationalHistory().get(i).getConfirmedCount());
            returnChart.addEchartY2(nationHistory.getNationalHistory().get(i).getConfirmedIncr());
            returnChart.addEchartY3(nationHistory.getNationalHistory().get(i).getCurrentConfirmedCount());
            returnChart.addEchartY4(nationHistory.getNationalHistory().get(i).getCuredCount());
            returnChart.addEchartY5(nationHistory.getNationalHistory().get(i).getDeadCount());
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

    public void HubeiCompareOthers()
    {
        returnChart.setChartName("Compare Hubei and other provinces");
        returnChart.setComment("x1 is date; y1 is Hubei confirmed count; y2 is Hubei dead count; " +
                "y3 is other total confirmed count; y4 is other total dead count");
        compareNation.setJdbcTemplate(this.jdbcTemplate);
        compareNation.reset();
        compareNation.setInput("all");
        compareNation.access();

        compareHubei.setJdbc(this.jdbcTemplate);
        compareHubei.reset();
        compareHubei.setParaPro("Hubei");
        compareHubei.setParaDate("all");
        compareHubei.access();

        for(int i = 0; i < compareHubei.getProvinces().size(); i++)
        {
            returnChart.addEchartX1(compareHubei.getProvinces().get(i).getDate());
            returnChart.addEchartY1(Integer.parseInt(compareHubei.getProvinces().get(i).getConfirmedCount()));
            returnChart.addEchartY2(Integer.parseInt(compareHubei.getProvinces().get(i).getDeadCount()));
            returnChart.addEchartY3(compareNation.getNationalHistory().get(i + 1).getConfirmedCount() -
                    Integer.parseInt(compareHubei.getProvinces().get(i).getConfirmedCount()));
            returnChart.addEchartY4(compareNation.getNationalHistory().get(i + 1).getDeadCount() -
                    Integer.parseInt(compareHubei.getProvinces().get(i).getDeadCount()));
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
    }
}


