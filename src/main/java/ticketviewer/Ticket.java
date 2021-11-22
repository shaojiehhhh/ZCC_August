package ticketviewer;

import java.util.ArrayList;
import java.util.List;

public class Ticket {
    private int id;                  //ticket ID
    private String created_at;       //ticket requested time
    private String type;             //ticket type
    private String subject;          //ticket subject
    private String description;      //ticket description
    private String priority;         //ticket priority
    private String status;           //ticket status
    private List<Ticket> tickets = new ArrayList<Ticket>();

    public Ticket() {
    }

    //Getter and Setter
    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String toString() {
        return "id: " + this.id + "\n" + "Subject: " + this.subject + "\n" +  "Requested Time: " + this.created_at + "\n" + "Description: " + this.description
                + "\n"+"=========================================================================================================================================================================================================================================================================";
    }
}

