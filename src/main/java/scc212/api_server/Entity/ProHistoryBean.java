package scc212.api_server.Entity;


/**
 * Used to save JSON data
 * @ Author Tian Yu
 * @ Date 2020.04.10
 */
public class ProHistoryBean
{
    private String date;
    private String confirmedCount;
    private String confirmedIncr;
    private String curedCount;
    private String curedIncr;
    private String currentConfirmedCount;
    private String currentCoonfirmedIncr;
    private String deadCount;
    private String deadIncr;

    public ProHistoryBean()
    {

    }

    //Initialize the paras of the class.

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getConfirmedCount()
    {
        return confirmedCount;
    }

    public void setConfirmedCount(String confirmedCount)
    {
        this.confirmedCount = confirmedCount;
    }

    public String getConfirmedIncr()
    {
        return confirmedIncr;
    }

    public void setConfirmedIncr(String confirmedIncr)
    {
        this.confirmedIncr = confirmedIncr;
    }

    public String getCuredCount()
    {
        return curedCount;
    }

    public void setCuredCount(String curedCount)
    {
        this.curedCount = curedCount;
    }

    public String getCuredIncr()
    {
        return curedIncr;
    }

    public void setCuredIncr(String curedIncr)
    {
        this.curedIncr = curedIncr;
    }

    public String getCurrentConfirmedCount()
    {
        return currentConfirmedCount;
    }

    public void setCurrentConfirmedCount(String currentConfirmedCount)
    {
        this.currentConfirmedCount = currentConfirmedCount;
    }

    public String getCurrentCoonfirmedIncr()
    {
        return currentCoonfirmedIncr;
    }

    public void setCurrentCoonfirmedIncr(String currentCoonfirmedIncr)
    {
        this.currentCoonfirmedIncr = currentCoonfirmedIncr;
    }

    public String getDeadCount()
    {
        return deadCount;
    }

    public void setDeadCount(String deadCount)
    {
        this.deadCount = deadCount;
    }

    public String getDeadIncr()
    {
        return deadIncr;
    }

    public void setDeadIncr(String deadIncr)
    {
        this.deadIncr = deadIncr;
    }
}
