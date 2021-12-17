package Tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Init {
    public static int M;
    public static int N;
    public static int C = 0;
    public static List<Restaurant> restaurants = new ArrayList<Restaurant>();

    Init(){
        int i;
        M = (int)(Math.random()*(10-5+1)+5);
        for (i=0;i<M;i++){
            int c = (int)(Math.random()*(15-8+1)+8);
            C += c;
            restaurants.add(new Restaurant(i+1,c,(int)(Math.random()*(c+1))));
        }
        N = (int)(Math.random()*(((int)C/2)-10+1)+10);
    }
    public int getM(){
        return M;
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
