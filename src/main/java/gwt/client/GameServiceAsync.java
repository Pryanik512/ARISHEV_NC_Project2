package gwt.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import dao.Entity.Users;


public interface GameServiceAsync {
    void addAccount(String name, String pass, AsyncCallback<Void> callback);
    void logIn(String name, String pass,  AsyncCallback<Users> callback);
}
