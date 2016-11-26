package gwt.client;

import java.io.Serializable;


public class GameNCException extends Exception implements Serializable {

    String msg;

    public GameNCException(){
    }

    public GameNCException(String msg){
        this.msg = msg;
    }

    public String getMsg(){
        return this.msg;
    }
}
