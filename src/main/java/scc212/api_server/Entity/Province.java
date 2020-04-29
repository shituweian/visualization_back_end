package scc212.api_server.Entity;

/**
 * Used to save JSON data
 * @ Author Tian Yu
 * @ Date 2020.04.10
 */

public class Province
{
    private String nameCN;
    private String shortName;
    private String nameEn;
    private int confirmedCount;
    private int currentConfirmedCount;
    private int curedCount;
    private int deadCount;
    private int suspectedCount;
    private int locationId;

    public String getNameCN()
    {
        return nameCN;
    }

    public void setNameCN(String nameCN)
    {
        this.nameCN = nameCN;
    }

    public String getShortName()
    {
        return shortName;
    }

    public void setShortName(String shortName)
    {
        this.shortName = shortName;
    }

    public String getNameEn()
    {
        return nameEn;
    }

    public void setNameEn(String nameEn)
    {
        this.nameEn = nameEn;
    }

    public int getConfirmedCount()
    {
        return confirmedCount;
    }

    public void setConfirmedCount(int confirmedCount)
    {
        this.confirmedCount = confirmedCount;
    }

    public int getCurrentConfirmedCount()
    {
        return currentConfirmedCount;
    }

    public void setCurrentConfirmedCount(int currentConfirmedCount)
    {
        this.currentConfirmedCount = currentConfirmedCount;
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

    public int getSuspectedCount()
    {
        return suspectedCount;
    }

    public void setSuspectedCount(int suspectedCount)
    {
        this.suspectedCount = suspectedCount;
    }

    public int getLocationId()
    {
        return locationId;
    }

    public void setLocationId(int locationId)
    {
        this.locationId = locationId;
    }
}
