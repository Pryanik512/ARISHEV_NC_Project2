package gwt.client;

import com.google.gwt.user.client.rpc.AsyncCallback;


public interface GameServiceAsync {
    void addAccount(String name, String pass, AsyncCallback<Void> callback);

}
