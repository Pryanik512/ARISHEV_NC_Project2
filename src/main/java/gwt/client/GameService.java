package gwt.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.core.client.GWT;
import dao.Entity.Users;




@RemoteServiceRelativePath("GameService")
public interface GameService extends RemoteService {

    void addAccount(String name, String pass) throws GameNCException;
    Users logIn(String name, String pass) throws GameNCException;

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
