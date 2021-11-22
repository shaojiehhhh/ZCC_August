package ticketviewer;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import org.junit.jupiter.api.Test;
import ticketviewer.Ticket;
import ticketviewer.Viewer;

class TicketTest {
    // test API connect
    @org.junit.jupiter.api.Test
    void testApiConnect() throws IOException {
        HttpURLConnection connect = null;
        Ticket ticket = new Ticket();
        URL url = null;
        // create connection to test
        try {
            url = new URL("https://zccaugust.zendesk.com/api/v2/tickets.json/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            connect = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String credentials = ("user" + ":" + "pwd");
        String encodeBytes = Base64.getEncoder().encodeToString((credentials).getBytes());

        connect.setRequestMethod("GET");
        connect.setRequestProperty("Authorization", "Basic " + encodeBytes);
        connect.setRequestMethod("GET");
        connect.setRequestProperty("Basic", "application/json");

        int status = connect.getResponseCode();
        //the response code 200 means OK
        assertEquals(200, status);
    }

    //test user authetication and ticket requesting
    @org.junit.jupiter.api.Test
    void testLoadAllTickets() {
        String user = {username};  //PLEASE REPLACE {username} with user's zendesk username here
        String psw = {password};   //PLEASE REPLACE {password} with user's zendesk password here
        String credientials = user+ ":"+psw;
        Viewer test = new Viewer(user, psw);
        Ticket ticket = test.connectApi(credientials);
        List<Ticket> tickets = test.getTicketDatabase(ticket);
        assertEquals(tickets.size(),100);
    }

}