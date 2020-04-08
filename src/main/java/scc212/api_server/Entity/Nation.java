package scc212.api_server.Entity;

public class Nation
{
    private String nameCN; //Chinese name
    private String nameEn; //English name
    private String confirmedCount; //Confirmed number of the province
    private String currentConfirmedCount; //Current existed number
    private String curedCount; // Cured number of the province
    private String deadCount; //Dead number
    private String suspectedCount; //Suspected number
    private String overseaInput;
    private String locationId; //The location ID of the province

    public String getNameCN() {
        return nameCN;
    }

    public void setNameCN(String nameCN) {
        this.nameCN = nameCN;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getConfirmedCount() {
        return confirmedCount;
    }

    public void setConfirmedCount(String confirmedCount) {
        this.confirmedCount = confirmedCount;
    }

    public String getCurrentConfirmedCount() {
        return currentConfirmedCount;
    }

    public void setCurrentConfirmedCount(String currentConfirmedCount) {
        this.currentConfirmedCount = currentConfirmedCount;
    }

    public String getCuredCount() {
        return curedCount;
    }

    public void setCuredCount(String curedCount) {
        this.curedCount = curedCount;
    }

    public String getDeadCount() {
        return deadCount;
    }

    public void setDeadCount(String deadCount) {
        this.deadCount = deadCount;
    }

    public String getSuspectedCount() {
        return suspectedCount;
    }

    public void setSuspectedCount(String suspectedCount) {
        this.suspectedCount = suspectedCount;
    }

    public String getOverseaInput()
    {
        return overseaInput;
    }

    public void setOverseaInput(String overseaInput)
    {
        this.overseaInput = overseaInput;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

}
