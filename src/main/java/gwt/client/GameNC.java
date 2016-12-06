package gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import gwt.client.widgets.LogInWindow;


public class GameNC implements EntryPoint {



    public void onModuleLoad() {


        RootPanel.get("NewAccPage").add(new LogInWindow());


    }
}
