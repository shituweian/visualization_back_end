package scc212.api_server.Entity;

import java.util.ArrayList;
import java.util.List;

public class NationChart
{
    private String chartName;
    private List<String> echartX1 = new ArrayList<String>();
    private List<String> echartX2 = new ArrayList<String>();
    private List<String> echartY = new ArrayList<String>();

    public void addEchartX1(String item)
    {
        this.echartX1.add(item);
    }

    public void addEchartX2(String item)
    {
        this.echartX2.add(item);
    }

    public void addEchartY(String item)
    {
        this.echartY.add(item);
    }

    public String getChartName()
    {
        return chartName;
    }

    public void setChartName(String chartName)
    {
        this.chartName = chartName;
    }


    public List getEchartX1()
    {
        return echartX1;
    }

    public void setEchartX1(List echartX1)
    {
        this.echartX1 = echartX1;
    }

    public List getEchartX2()
    {
        return echartX2;
    }

    public void setEchartX2(List echartX2)
    {
        this.echartX2 = echartX2;
    }


    public List getEchartY()
    {
        return echartY;
    }

    public void setEchartY(List echartY)
    {
        this.echartY = echartY;
    }

}
