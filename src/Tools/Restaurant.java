package Tools;

public class Restaurant {

    public int id;
    public String Name;
    public int capacity;
    public int nbrPlaceEmpty;


    public Restaurant(int id, int c, int n){
        this.id = id;
        this.capacity = c;
        this.nbrPlaceEmpty = n;
    }

    public int getId() {
        return id;
    }
    public int getCapacity() {
        return capacity;
    }

    public int getNbrPlaceEmpty() {
        return nbrPlaceEmpty;
    }
}
