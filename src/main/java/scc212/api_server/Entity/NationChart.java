package scc212.api_server.Entity;

import java.util.ArrayList;
import java.util.List;

public class NationChart
{
    private String chartName;
    private List<String> echartX1 = new ArrayList<String>();
    private List<String> echartX2 = new ArrayList<String>();
    private List<Integer> echartY1 = new ArrayList<Integer>();

    private List<Integer> echartY2 = new ArrayList<Integer>();

    public void addEchartX1(String item)
    {
        this.echartX1.add(item);
    }

    public void addEchartX2(String item)
    {
        this.echartX2.add(item);
    }

    public void addEchartY1(int item)
    {
        this.echartY1.add(item);
    }

    public void addEchartY2(int item)
    {
        this.echartY2.add(item);
    }


    public List<Integer> getEchartY2() {
        return echartY2;
    }

    public void setEchartY2(List<Integer> echartY2) {
        this.echartY2 = echartY2;
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


    public List getEchartY1()
    {
        return echartY1;
    }

    public void setEchartY1(List echartY)
    {
        this.echartY1 = echartY;
    }


}
