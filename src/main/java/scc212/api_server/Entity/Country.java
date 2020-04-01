package scc212.api_server.Entity;

public class Country {
    private String country_name;
    private String country_name_en;
    private String continent_name;
    private int current_confirmed_count;
    private int confirmed_count;
    private int suspected_count;
    private int cured_count;
    private int dead_count;
    private int location_id;
    private String time;

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    };
    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name_en(String country_name_en) {
        this.country_name_en = country_name_en;
    };
    public String getCountry_name_en() {
        return country_name_en;
    }

    public void setContinent_name(String continent_name) {
        this.continent_name = continent_name;
    }
    public String getContinent_name() {
        return continent_name;
    }

    public void setCurrent_confirmed_count(int current_confirmed_count) {
        this.current_confirmed_count = current_confirmed_count;
    }
    public int getCurrent_confirmed_count() {
        return current_confirmed_count;
    }

    public void setConfirmed_count(int confirmed_count) {
        this.confirmed_count = confirmed_count;
    }
    public int getConfirmed_count() {
        return confirmed_count;
    }

    public void setSuspected_count(int suspected_count) {
        this.suspected_count = suspected_count;
    }
    public int getSuspected_count() {
        return suspected_count;
    }

    public void setCured_count(int cured_count) {
        this.cured_count = cured_count;
    }
    public int getCured_count() {
        return cured_count;
    }

    public void setDead_count(int dead_count) {
        this.dead_count = dead_count;
    }
    public int getDead_count() {
        return dead_count;
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
