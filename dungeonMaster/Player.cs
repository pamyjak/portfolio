using System;

public class Player { 
    private String name;
    private Boolean isActive;

    public void setName(String arg) {
        this.name = arg;
    }

    public String getName() {
        return this.name;
    }

    public void setActive(Boolean arg) {
        this.isActive = arg;
    }

    public Boolean getActive() {
        return this.isActive;
    }
}
