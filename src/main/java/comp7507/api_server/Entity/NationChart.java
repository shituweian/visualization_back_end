package comp7507.api_server.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Used to save JSON data
 * @ Author Tian Yu
 * @ Date 2020.04.10
 */

public class NationChart
{
    private String chartName;
    private String comment;
    private List<String> echartX1 = new ArrayList<String>();
    private List<String> echartX2 = new ArrayList<String>();
    private List<Integer> echartY1 = new ArrayList<Integer>();
    private List<Integer> echartY2 = new ArrayList<Integer>();
    private List<Integer> echartY3 = new ArrayList<Integer>();
    private List<Integer> echartY4 = new ArrayList<Integer>();
    private List<Integer> echartY5 = new ArrayList<Integer>();

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

    public void addEchartY3(int item)
    {
        this.echartY3.add(item);
    }

    public void addEchartY4(int item)
    {
        this.echartY4.add(item);
    }

    public void addEchartY5(int item)
    {
        this.echartY5.add(item);
    }

    public String getChartName()
    {
        return chartName;
    }

    public void setChartName(String chartName)
    {
        this.chartName = chartName;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public List<String> getEchartX1()
    {
        return echartX1;
    }

    public void setEchartX1(List<String> echartX1)
    {
        this.echartX1 = echartX1;
    }

    public List<String> getEchartX2()
    {
        return echartX2;
    }

    public void setEchartX2(List<String> echartX2)
    {
        this.echartX2 = echartX2;
    }

    public List<Integer> getEchartY1()
    {
        return echartY1;
    }

    public void setEchartY1(List<Integer> echartY1)
    {
        this.echartY1 = echartY1;
    }

    public List<Integer> getEchartY2()
    {
        return echartY2;
    }

    public void setEchartY2(List<Integer> echartY2)
    {
        this.echartY2 = echartY2;
    }

    public List<Integer> getEchartY3()
    {
        return echartY3;
    }

    public void setEchartY3(List<Integer> echartY3)
    {
        this.echartY3 = echartY3;
    }
    public List<Integer> getEchartY4()
    {
        return echartY4;
    }

    public void setEchartY4(List<Integer> echartY4)
    {
        this.echartY4 = echartY4;
    }

    public List<Integer> getEchartY5()
    {
        return echartY5;
    }

    public void setEchartY5(List<Integer> echartY5)
    {
        this.echartY5 = echartY5;
    }
}

