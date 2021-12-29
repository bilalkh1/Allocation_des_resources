package Tools;

import java.io.Serializable;
import java.util.Date;

public class Reservation implements Serializable {

    public static int nbReservation = 0;
    public Date date_reservation;
    public int id_Restaurant;

    public Reservation(int id_Restaurant, Date date_reservation){
        nbReservation ++;
        this.id_Restaurant = id_Restaurant;
        this.date_reservation = date_reservation;
    }

    public int getId_Restaurant() {
        return id_Restaurant;
    }

    public String toString(){
        return "Reservation : " + this.id_Restaurant;
    }
}
