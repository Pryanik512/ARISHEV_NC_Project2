package dao.Entity;


import com.google.gwt.user.client.rpc.IsSerializable;

import java.io.Serializable;

public class Users implements Serializable{

     int id;
     String name;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }





}
