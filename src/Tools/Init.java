package Tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Init {
    // restaurant number
    public static int M;
    // number of persons
    public static int N;
    // sum of all restaurant capacity
    public static int C = 0;
    // list of restaurants
    public static List<Restaurant> restaurants = new ArrayList<Restaurant>();

    Init(){
        int i;
        // generate restaurant number between 10 and 5
        M = (int)(Math.random()*(10-5+1)+5);
        // create instances of restaurants
        for (i=0;i<M;i++){
            // generate capacity in every restaurant
            int c = (int)(Math.random()*(15-8+1)+8);
            C += c;
            restaurants.add(new Restaurant(i+1,c,(int)(Math.random()*(c+1))));
        }
        // generate number of persons with 2*N < C and N > Ci(max)
        N = (int)(Math.random()*(((int)C/2)-10+1)+10);
    }

    public static void main(String[] args) {
        int j;
        Init i = new Init();
        System.out.println(i.M);
        System.out.println(i.N);
        System.out.println(i.C);
//        for (j=0;j<i.restaurants.size();j++){
//            System.out.println(i.restaurants.get(j).getId() + "  " + i.restaurants.get(j).getCapacity()+ "  " + i.restaurants.get(j).getNbrPlaceEmpty());
//            System.out.println();
//        }

    }
}
