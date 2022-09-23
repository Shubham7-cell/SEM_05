import java.util.*;

public class MissionariesAndCannibals {
    // Method to transfer two missionaries either from left or right bank
    public static boolean MissTwo(int[] list, String boat, boolean[] visited) {
        // These conditions check the bank status and then updates their location to
        // opposite bank
        if (boat.equals(" | Current Position: Left Bank") && visited[0] == false) {
            list[2] += 2;
            list[0] -= 2;
            visited[1] = true;
            return true;
        } else if (boat.equals(" | Current Position: Right Bank") && visited[1] == false) {
            list[2] -= 2;
            list[0] += 2;
            visited[0] = true;
            return true;
        } else {
            return false;
        }
    }

    // Method to transfer two cannibals either from left or right bank
    public static boolean CanTwo(int[] list, String boat, boolean[] visited) {
        // These conditions check the bank status and then updates their location to
        // opposite bank
        if (boat.equals(" | Current Position: Left Bank") && visited[2] == false) {
            list[3] += 2;
            list[1] -= 2;
            visited[3] = true;
            return true;
        } else if (boat.equals(" | Current Position: Right Bank") && visited[3] == false) {
            list[3] -= 2;
            list[1] += 2;
            visited[2] = true;
            return true;
        } else {
            return false;
        }
    }

    // Method to transfer only one missionary either from left or right bank
    public static boolean MissOne(int[] arr, String boat, boolean[] alrdyVisited) {
        // These conditions check the bank status and then updates their location to
        // opposite bank
        if (boat.equals(" | Current Position: Left Bank") && alrdyVisited[4] == false) {
            arr[2] += 1;
            arr[0] -= 1;
            alrdyVisited[5] = true;
            return true;
        } else if (boat.equals(" | Current Position: Right Bank") && alrdyVisited[5] == false) {
            arr[2] -= 1;
            arr[0] += 1;
            alrdyVisited[4] = true;
            return true;
        } else {
            return false;
        }
    }

    // Method to transfer only one cannibal either from left or right bank
    public static boolean CanOne(int[] arr, String boat, boolean[] visited) {
        // These conditions check the bank status and then updates their location to
        // opposite bank
        if (boat.equals(" | Current Position: Left Bank") && visited[6] == false) {
            arr[3] += 1;
            arr[1] -= 1;
            visited[7] = true;
            return true;
        } else if (boat.equals(" | Current Position: Right Bank") && visited[7] == false) {
            arr[3] -= 1;
            arr[1] += 1;
            visited[6] = true;
            return true;
        } else {
            return false;
        }
    }

    // Method to transfer one missionary and one cannibal either from left or right
    // bank
    public static boolean MissOneCanOne(int[] arr, String boat, boolean[] visited) {
        // These conditions check the bank status and then updates their location to
        // opposite bank
        if (boat.equals(" | Current Position: Left Bank") && visited[8] == false) {
            arr[2] += 1;
            arr[0] -= 1;
            arr[3] += 1;
            arr[1] -= 1;
            visited[9] = true;
            return true;
        } else if (boat.equals(" | Current Position: Right Bank") && visited[9] == false) {
            arr[2] -= 1;
            arr[0] += 1;
            arr[3] -= 1;
            arr[1] += 1;
            visited[8] = true;
            return true;
        } else {
            return false;
        }
    }

    public static void MC(int[] list, String boat, boolean[] visited) {
        System.out.print(Arrays.toString(list) + " ");
        System.out.print(boat + " \n");

        // exit condition which breaks the recursive loop
        if (Arrays.equals(list, new int[] { 0, 0, 3, 3 })) {
            System.out.println("Solution found!");
            System.exit(0);
        }

        // Condition for two missionaries on the boat via left bank
        // M should outnumber C / 0 M should be present at this bank
        if ((list[0] - 2 > list[1] || list[0] - 2 == 0) && boat.equals(" | Current Position: Left Bank")) {
            if (MissTwo(list, boat, visited)) {
                System.out.println("Boat Status: (M M)\n");
                boat = " | Current Position: Right Bank";
                MC(list, boat, visited);
            }
        }

        // Condition for two cannibals on the boat via left bank
        // C should be either 0 or greater than zero at this bank
        if (list[1] - 2 >= 0 && boat.equals(" | Current Position: Left Bank")) {
            if (CanTwo(list, boat, visited)) {
                System.out.println("Boat Status: (C C)\n");
                boat = " | Current Position: Right Bank";
                MC(list, boat, visited);
            }
        }

        // Condition for one missionary on the boat via left bank
        // M should be either 0 or greater than number of cannibals at this bank
        if ((list[0] - 1 > list[1] || list[0] - 1 == 0) && boat.equals(" | Current Position: Left Bank")) {
            if (MissOne(list, boat, visited)) {
                System.out.println("Boat Status: (M _)\n");
                boat = " | Current Position: Right Bank";
                MC(list, boat, visited);
            }
        }

        // Condition for one cannibal on the boat via left bank
        // C should be either 0 or greater than zero at this bank
        if (list[1] - 1 >= 0 && boat.equals(" | Current Position: Left Bank")) {
            if (CanOne(list, boat, visited)) {
                System.out.println("Boat Status: (C _)\n");
                boat = " | Current Position: Right Bank";
                MC(list, boat, visited);
            }
        }

        // Condition for one missionary and one cannibal on the boat via left bank
        // C and M should be equal in number at this bank
        if (list[0] == list[1] && boat.equals(" | Current Position: Left Bank")) {
            if (MissOneCanOne(list, boat, visited)) {
                System.out.println("Boat Status: (M C)\n");
                boat = " | Current Position: Right Bank";
                MC(list, boat, visited);
            }
        }

        // Condition for two missionaries on the boat via right bank
        // M should be greater than C or 0 at this bank
        if ((list[2] - 2 > list[3] || list[2] - 2 == 0) && boat.equals(" | Current Position: Right Bank")) {
            if (MissTwo(list, boat, visited)) {
                System.out.println("Boat Status: (M M)\n");
                boat = " | Current Position: Left Bank";
                MC(list, boat, visited);
            }
        }

        // Condition for two cannibals on the boat via right bank
        // C should be either 0 or greater than zero at this bank
        if (list[3] - 2 >= 0 && boat.equals(" | Current Position: Right Bank")) {
            if (CanTwo(list, boat, visited)) {
                System.out.println("Boat Status: (C C)\n");
                boat = " | Current Position: Left Bank";
                MC(list, boat, visited);
            }
        }

        // Condition for one missionary on the boat via right bank
        // M should be either 0 or greater than number of cannibals at this bank
        if ((list[2] - 1 > list[3] || list[2] - 1 == 0) && boat.equals(" | Current Position: Right Bank")) {
            if (MissOne(list, boat, visited)) {
                System.out.println("Boat Status: (M _)\n");
                boat = " | Current Position: Left Bank";
                MC(list, boat, visited);
            }
        }

        // Condition for one cannibal on the boat via right bank
        // C should be either 0 or greater than zero at this bank
        if (list[3] - 1 >= 0 && boat.equals(" | Current Position: Right Bank")) {
            if (CanOne(list, boat, visited)) {
                System.out.println("Boat Status: (C _)\n");
                boat = " | Current Position: Left Bank";
                MC(list, boat, visited);
            }
        }

        // Condition for one missionary and one cannibal on the boat via right bank
        // C and M should be equal in number at this bank
        if (list[2] == list[3] && boat.equals(" | Current Position: Right Bank")) {
            if (MissOneCanOne(list, boat, visited)) {
                System.out.println(" (M C)\n");
                boat = " | Current Position: Left Bank";
                MC(list, boat, visited);
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // System.out.println("Enter the number Missionaries on the Left Bank: ");
        // int missionaries = sc.nextInt();
        // System.out.println("Enter the number of Cannibals on the Left Bank: ");
        // int cannibals = sc.nextInt();
        // System.out.println();

        int missionaries = 3;
        int cannibals = 3;
        System.out.println("------------------Solution------------------\n");
        // Missionaries and Cannibals on left bank
        int[] arr = { missionaries, cannibals, 0, 0 };
        String boat = " | Current Position: Left Bank";
        boolean[] visited = new boolean[10];
        MC(arr, boat, visited);
        sc.close();
    }
}
