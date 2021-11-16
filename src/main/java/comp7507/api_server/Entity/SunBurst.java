package comp7507.api_server.Entity;

import java.util.ArrayList;
import java.util.List;

public class SunBurst
{
    private String name;
    private List children = new ArrayList();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List getChildren() {
        return children;
    }

    public void setChildren(List children) {
        this.children = children;
    }
}
