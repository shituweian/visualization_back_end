package comp7507.api_server.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Used to save JSON data
 * @ Author Tian Yu
 * @ Date 2020.04.11
 */

public class ProHistory
{
    private String enName;
    private String cnName;
    private String proId;
    private List<ProHistoryBean> pros = new ArrayList<ProHistoryBean>();


    public String getProId()
    {
        return proId;
    }

    public void setProId(String proId)
    {
        this.proId = proId;
    }

    public String getEnName()
    {
        return enName;
    }

    public void setEnName(String enName)
    {
        this.enName = enName;
    }

    public String getCnName()
    {
        return cnName;
    }

    public void setCnName(String cnName)
    {
        this.cnName = cnName;
    }
    public List<ProHistoryBean> getPros()
    {
        return pros;
    }

    public void setPros(List<ProHistoryBean> pros)
    {
        this.pros = pros;
    }
}
