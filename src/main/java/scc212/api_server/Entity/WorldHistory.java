package scc212.api_server.Entity;

public class WorldHistory {
    private String area_name;
    private String area_name_en;
    private int confirmed_count;
    private int confirmed_incr;
    private float confirmed_rate;
    private int cured_count;
    private int cured_incr;
    private float cured_rate;
    private int current_confirmed_count;
    private int current_confirmed_incr;
    private float current_confirmed_rate;
    private int date_id;
    private int dead_count;
    private int dead_incr;
    private float dead_rate;
    private int location_id;
    private String time;

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    };
    public String getArea_name() {
        return area_name;
    }

    public void setArea_name_en(String country_name_en) {
        this.area_name_en = area_name_en;
    };
    public String getArea_name_en() {
        return area_name_en;
    }

    public void setConfirmed_count(int confirmed_count) {
        this.confirmed_count = confirmed_count;
    }
    public int getConfirmed_count() {
        return confirmed_count;
    }

    public void setConfirmed_incr(int confirmed_incr) {
        this.confirmed_incr = confirmed_incr;
    }
    public int getConfirmed_incr() {
        return confirmed_incr;
    }

    public void setConfirmed_rate(float confirmed_rate) {
        this.confirmed_rate = confirmed_rate;
    }
    public float getConfirmed_rate() {
        return confirmed_rate;
    }

    public void setCured_count(int cured_count) {
        this.cured_count = cured_count;
    }
    public int getCured_count() {
        return cured_count;
    }

    public void setCured_incr(int cured_incr) {
        this.cured_incr = cured_incr;
    }
    public int getCured_incr() {
        return cured_incr;
    }

    public void setCured_rate(float cured_rate) {
        this.cured_rate = cured_rate;
    }
    public float getCured_rate() {
        return cured_rate;
    }

    public void setCurrent_confirmed_count(int current_confirmed_count) {
        this.current_confirmed_count = current_confirmed_count;
    }
    public int getCurrent_confirmed_count() {
        return current_confirmed_count;
    }

    public void setCurrent_confirmed_incr(int current_confirmed_incr) {
        this.current_confirmed_incr = current_confirmed_incr;
    }
    public int getCurrent_confirmed_incr() {
        return current_confirmed_incr;
    }

    public void setCurrent_confirmed_rate(float current_confirmed_rate) {
        this.current_confirmed_rate = current_confirmed_rate;
    }
    public float getCurrent_confirmed_rate() {
        return current_confirmed_rate;
    }

    public void setDate_id(int date_id) {
        this.date_id = date_id;
    }
    public int getDate_id() {
        return date_id;
    }

    public void setDead_count(int dead_count) {
        this.dead_count = dead_count;
    }
    public int getDead_count() {
        return dead_count;
    }

    public void setDead_incr(int dead_incr) {
        this.dead_incr = dead_incr;
    }
    public int getDead_incr() {
        return dead_incr;
    }

    public void setDead_rate(float dead_rate) {
        this.dead_rate = dead_rate;
    }
    public float getDead_rate() {
        return dead_rate;
    }

    public void setLocation_id(int location_id) {
        this.location_id = location_id;
    }
    public int getLocation_id() {
        return location_id;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public String getTime() {
        return time;
    }

}
