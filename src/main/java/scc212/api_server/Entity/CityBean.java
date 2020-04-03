package scc212.api_server.Entity;

public class CityBean
{
    private String cityNameCn;
    private String cityNameEn;
    private int currentConfirmedCount;
    private int confirmedCount;
    private int susCount;
    private int curedCount;
    private int deadCount;
    private String ID;
    private String time;

    public String getCityName()
    {
        return cityNameCn;
    }

    public void setCityName(String cityName)
    {
        this.cityNameCn = cityName;
    }

    public int getCurrentConfirmedCount()
    {
        return currentConfirmedCount;
    }

    public void setCurrentConfirmedCount(int currentConfirmedCount)
    {
        this.currentConfirmedCount = currentConfirmedCount;
    }
    public String getCityNameEn() {
        return cityNameEn;
    }

    public void setCityNameEn(String cityNameEn) {
        this.cityNameEn = cityNameEn;
    }
    public int getConfirmedCount()
    {
        return confirmedCount;
    }

    public void setConfirmedCount(int confirmedCount)
    {
        this.confirmedCount = confirmedCount;
    }

    public int getSusCount()
    {
        return susCount;
    }

    public void setSusCount(int susCount)
    {
        this.susCount = susCount;
    }

    public int getCuredCount()
    {
        return curedCount;
    }

    public void setCuredCount(int curedCount)
    {
        this.curedCount = curedCount;
    }

    public int getDeadCount()
    {
        return deadCount;
    }

    public void setDeadCount(int deadCount)
    {
        this.deadCount = deadCount;
    }

    public String getID()
    {
        return ID;
    }

    public void setID(String ID)
    {
        this.ID = ID;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }
}
