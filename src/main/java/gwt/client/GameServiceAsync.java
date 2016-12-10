package gwt.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import gwt.client.GWTEntity.EntityForBattle;
import gwt.client.GWTEntity.UsersGWT;


public interface GameServiceAsync {
    void addAccount(String name, String pass, AsyncCallback<UsersGWT> callback);
    void logIn(String name, String pass,  AsyncCallback<UsersGWT> callback);
    void getHero(UsersGWT usersGWT, int hero_type, AsyncCallback<EntityForBattle> callback);
}
