package scc212.api_server.Entity;

import java.util.ArrayList;
import java.util.List;

public class NationCity
{
    private String proNameEN;
    private String proNameCN;
    private String proID;
    private List<CityBean> cities = new ArrayList<CityBean>();

    public String getProNameEN()
    {
        return proNameEN;
    }

    public void setProNameEN(String proNameEN)
    {
        this.proNameEN = proNameEN;
    }

    public String getProNameCN()
    {
        return proNameCN;
    }

    public void setProNameCN(String proNameCN)
    {
        this.proNameCN = proNameCN;
    }

    public String getProID()
    {
        return proID;
    }

    public void setProID(String proID)
    {
        this.proID = proID;
    }


    public List<CityBean> getCities()
    {
        return cities;
    }

    public void setCities(List<CityBean> cities)
    {
        this.cities = cities;
    }
}
