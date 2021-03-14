import Graphs.*;
public class Profile {
    private String name;
    private String status;
    private String username;

    public Profile(String name, String status,String username) {
        this.name = name;
        this.status = status;
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String toString(){
        String profileStr = "USERNAME: "+ username +"\nNAME: " + name + "\nSTATUS: " + status;
        return profileStr;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
