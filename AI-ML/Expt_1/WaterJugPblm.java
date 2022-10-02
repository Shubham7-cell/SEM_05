public class WaterJugPblm {
    public static void main(String[] args) {
        int maxCap_j1 = 4;
        int maxCap_j2 = 3;
        int goal = 2;
        int j1 = 0, j2 = 0;
        System.out.println("Jug 1 has a capacity of 4 gallons\nJug 2 has a capacity of 3 gallons");
        System.out.println("\nLet J1 and J2 denote the amount of water filled in Jug 1 and J2 respectively");
        System.out.println("\n\n----------Solution Path----------\n");
        System.out.println("\nInitial state : J1= " + j1 + "  J2= " + j2 + "\n\n");

        while (true) {
            if (j1 == goal) {
                System.out.println("Goal achieved : Jug 1 is filled with exactly 2 gallons of water\n");
                // Jug 1 is filled with exactly 2 gallons of water
                break;

            } else if (j1 == maxCap_j1) {
                j1 = 0; // empty it
                System.out.println("Amount of water in Jug 1 and Jug 2 :\nJ1= " + j1 + "\nJ2= " + j2 + "\n\n");
                // Jug 1 is full (4 gallons)
                continue;

            } else if (j1 == 0 && j2 == 0) // Both jugs are empty (first step)
            {
                j1 = maxCap_j1;
                j2 = 0; // So fill Jug 2 completely
                System.out.println("Amount of water in Jug 1 and Jug 2 :\nJ1= " + j1 + "\nJ2= " + j2 + "\n\n");
                continue;

            } else if (j1 == 0 && j2 > 0) // Jug 1 is empty and Jug 2 is non-empty
            {
                j1 = j2; // Fill all the water from Jug 2 to Jug 1
                j2 = 0; // So Jug 2 becomes empty
                System.out.println("Amount of water in Jug 1 and Jug 2 :\nJ1= " + j1 + "\nJ2= " + j2 + "\n\n");
                continue;
            }

            else if (j1 < maxCap_j1 && j2 == 0) // Jug 1 is non-empty and Jug 2 is empty
            {
                j2 = maxCap_j2; // J1=3 and J2=3
                System.out.println("Amount of water in Jug 1 and Jug 2 :\nJ1= " + j1 + "\nJ2= " + j2 + "\n\n");
                continue;
            } else if (j1 < maxCap_j1 && j2 == maxCap_j2) // Jug 1 is non-empty and Jug 2 is full
            {
                j2 = maxCap_j2 - (maxCap_j1 - j1);
                j1 = maxCap_j1; // J1=4 and J2=2
                System.out.println("Amount of water in Jug 1 and Jug 2 :\nJ1= " + j1 + "\nJ2= " + j2 + "\n\n");
                continue;
            }
        }
    }
}