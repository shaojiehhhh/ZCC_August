package ticketviewer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;

public class Viewer {
    public static Ticket ticket;
    public static String username;          //user's email address
    public static String password;          //user's password
    public static List<Ticket> allTickets;
    public static String credentials;       //user's authentication
    private static HttpURLConnection connection;

    public Viewer(String username, String password){
        this.username = username;
        this.password = password;
    }

    /**
     * Method for connecting with Zendesk API
     * and handling basic errors
     * @param credentials
     * @return
     */
    public static Ticket connectApi(String credentials){
        Ticket ticket = null;
        try {
            // url connection to grab tickets
            URL url = new URL("https://zccaugust.zendesk.com/api/v2/tickets.json/");
            connection = (HttpURLConnection) url.openConnection();
            // encoding into base64 credentials for header to accept
            String encodeBytes = Base64.getEncoder().encodeToString((credentials).getBytes());

            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Basic " + encodeBytes);

            InputStreamReader inputReader = new InputStreamReader(connection.getInputStream());
            BufferedReader bReader = new BufferedReader(inputReader);
            int status = connection.getResponseCode();
            if (status == connection.HTTP_OK) {
                Gson gson = new Gson();
                BufferedReader br = null;
                br = new BufferedReader(inputReader);
                // here ticket is one single ticket object
                ticket = gson.fromJson(br, Ticket.class);
            }

            // handle the API being unavailable and response being invalid
            else if (status == connection.HTTP_BAD_REQUEST) {
                System.out.println("The URL does not exist");
                throw new RuntimeException("HttpResponseCode: " + status);
            } else if (status == connection.HTTP_SERVER_ERROR) {
                System.out.println("error!");
                throw new RuntimeException("HttpResponseCode: " + status);
            } else if (status == connection.HTTP_FORBIDDEN) {
                System.out.println("forbidden URL!");
                throw new RuntimeException("HttpResponseCode: " + status);
            } else if (status == connection.HTTP_UNAVAILABLE) {
                System.out.println("unavailable URL!");
                throw new RuntimeException("HttpResponseCode: " + status);
            }

            bReader.close();
        }

        catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ticket;
    }

    /**
     * Method for displaying menu
     * @param ticket
     */
    public static void route(Ticket ticket){
        if (ticket == null) {
            // display a friendly error message
            System.out.println("Cannot connect to the API");
            return;
        }

        String userChoice = "";

        // main menu
        while (!userChoice.equalsIgnoreCase("quit")) {
            // calls for user input in Interface method
            userChoice = Menu();
            switch (userChoice) {
                // option1: view all tickets
                case "1":
                    requestAllTickets(ticket);
                    break;
                // option2; view a single ticket based on the ticket id
                case "2":
                    requestOneTicket(ticket);
                    break;
                // quit: quit the menu
                case "quit":
                    break;
                default:
                    System.out.println("Please input either: '1' , '2' , or 'q'  ");
                    System.out.println("\n" + "\n");
                    break;
            }
        }

        System.out.println("Thanks for using the viewer. Goodbye.");
    }

    /**
     * Method for getting ticket database
     * @param ticket
     * @return ticket databse
     */
    public static List<Ticket> getTicketDatabase(Ticket ticket){
        List<Ticket> tickets=null;
        // display a friendly error message
        if (ticket == null){
            System.out.println("error, something wrong with the api connect!");
        }
        else{
            tickets = allTickets = ticket.getTickets();
        }

        return tickets;
    }

    /**
     * Method for requesting and displaying all tickets
     * and paging through tickets when more than 25 are returned
     * @param ticket
     */
    public static void requestAllTickets(Ticket ticket) {
        allTickets = getTicketDatabase(ticket);
        if (allTickets == null) return;
        Scanner scan = new Scanner(System.in);
        int input = 0;
        try {
            for (Ticket t : allTickets) {
                // display tickets in a list
                System.out.println(t.toString());
                if (t.getId() % 25 == 0) {
                    System.out.println("--------------------------------------");
                    System.out.println("\n" + "\n");
                    System.out.println("If you want to view next 25 tickets, please type '1' ");
                    System.out.println("If you want to return to the main menu, please type '2'");
                    System.out.println(" ");
                    input = scan.nextInt();
                    if (input == 2) {
                        break;
                    }

                    if (input == 1) {
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method for authenticating based on user's username and password
     * @return credentials
     */
    public static String authenticate(){
        Scanner au = new Scanner(System.in);
        String input1 = "";
        String input2 = "";
        System.out.println("please type your username: ");
        input1 = au.next();
        System.out.println("please type your password: ");
        input2 = au.next();
        credentials = input1 + ":" + input2;
        return credentials;
    }

    /**
     * Method for displaying menu page
     * @return the printed menu
     */
    public static String Menu() {
        Scanner kb = new Scanner(System.in);
        String input = "";
        System.out.println("Select view options:");
        System.out.println("* Press 1 to view all tickets");
        System.out.println("* Press 2 to view a ticket");
        System.out.println("* Type 'quit' to quit");
        System.out.print("\n" + "\n");
        input = kb.next();
        return input;
    }

    /**
     * Method for requesting and displaying one single ticket detail
     * based on ticket id
     * @param ticket
     */
    public static void requestOneTicket(Ticket ticket) {
        Scanner scanId = new Scanner(System.in);
        System.out.println("Enter ticket id: ");
        String id = scanId.nextLine();
        try {
            if (ticket != null) {
                for (Ticket t : ticket.getTickets()) {
                    if (t.getId() == Integer.parseInt(id)) {
                        System.out.println(t.toString());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        credentials = authenticate();
        route(connectApi(credentials));
    }

}
