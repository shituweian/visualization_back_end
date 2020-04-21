package scc212.api_server.Entity;

import java.util.ArrayList;
import java.util.List;

public class CurrentLocation
{
    private int status;
    private String comments;
    private List<Province> curProvince = new ArrayList<>();


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public List<Province> getCurProvince() {
        return curProvince;
    }

    public void setCurProvince(List<Province> curProvince) {
        this.curProvince = curProvince;
    }
}
