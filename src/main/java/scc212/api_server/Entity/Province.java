package scc212.api_server.Entity;

public class Province
{
    private String nameCN; //Chinese name
    private String shortName; //Short name of the province
    private String nameEn; //English name
    private int confirmedCount; //Confirmed number of the province
    private int currentConfirmedCount; //Currented existed number(现存确诊)
    private int curedCount; // Cured number of the province(治愈人数)
    private int deadCount; //Dead number of the province(死亡人数)
    private int suspectedCount; //Suspected number the province(疑似病例)
    private int locationId; //The location ID of the province

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
