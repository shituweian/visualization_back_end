package scc212.api_server.Entity;

/**
 * Used to save JSON data
 * @ Author Tian Yu
 * @ Date 2020.04.09
 */

public class NationHistory
{
    private String date;
    private int confirmedCount;
    private int confirmedIncr;
    private int curedCount;
    private int curedIncr;
    private int currentConfirmedCount;
    private int currentCoonfirmedIncr;
    private int deadCount;
    private int deadIncr;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getConfirmedCount() {
        return confirmedCount;
    }

    public void setConfirmedCount(int confirmedCount) {
        this.confirmedCount = confirmedCount;
    }

    public int getConfirmedIncr() {
        return confirmedIncr;
    }

    public void setConfirmedIncr(int confirmedIncr) {
        this.confirmedIncr = confirmedIncr;
    }

    public int getCuredCount() {
        return curedCount;
    }

    public void setCuredCount(int curedCount) {
        this.curedCount = curedCount;
    }

    public int getCuredIncr() {
        return curedIncr;
    }

    public void setCuredIncr(int curedIncr) {
        this.curedIncr = curedIncr;
    }

    public int getCurrentConfirmedCount() {
        return currentConfirmedCount;
    }

    public void setCurrentConfirmedCount(int currentConfirmedCount) {
        this.currentConfirmedCount = currentConfirmedCount;
    }

    public int getCurrentCoonfirmedIncr() {
        return currentCoonfirmedIncr;
    }

    public void setCurrentCoonfirmedIncr(int currentCoonfirmedIncr) {
        this.currentCoonfirmedIncr = currentCoonfirmedIncr;
    }

    public int getDeadCount() {
        return deadCount;
    }

    public void setDeadCount(int deadCount) {
        this.deadCount = deadCount;
    }

    public int getDeadIncr() {
        return deadIncr;
    }

    public void setDeadIncr(int deadIncr) {
        this.deadIncr = deadIncr;
    }
}
