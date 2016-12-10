package gwt.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.core.client.GWT;

import gwt.client.GWTEntity.EntityForBattle;
import gwt.client.GWTEntity.UsersGWT;


@RemoteServiceRelativePath("GameService")
public interface GameService extends RemoteService {

    UsersGWT addAccount(String name, String pass) throws GameNCException;
    UsersGWT logIn(String name, String pass) throws GameNCException;
    EntityForBattle getHero(UsersGWT usersGWT, int hero_type) throws GameNCException;

    /**
     * Utility/Convenience class.
     * Use GameService.App.getInstance() to access static instance of GameServiceAsync
     */
    public static class App {
        private static final GameServiceAsync ourInstance = (GameServiceAsync) GWT.create(GameService.class);

        public static GameServiceAsync getInstance() {
            return ourInstance;
        }
    }
}
