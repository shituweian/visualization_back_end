package comp7507.api_server.Entity;

public class EngineCapacity {
    String sex;
    String capacity;
    public void setSex(String sex){
        this.sex=sex;
    }
    public void setCapacity(String capacity){
        this.capacity=capacity;
    }

    public String getCapacity(){
        return capacity;
    }
    public String getSex(){
        return sex;
    }

}
