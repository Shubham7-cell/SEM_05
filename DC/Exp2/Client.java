import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.time.*;

public class Client {
    public static void main(String args[]) throws RemoteException {
        String server = "bankServer";
        try {
            Scanner sc = new Scanner(System.in);
            Registry reg = LocateRegistry.getRegistry("localhost", 8000);
            // getting the remote object from the registry via the lookup method
            // invoking the remote method using the obtained object of the remote interface
            BalanceInterface obj_bal = (BalanceInterface) reg.lookup(server);
            System.out.print("\nEnter Account Number : ");
            String acc_no = sc.nextLine();
            System.out.print("Enter your password  : ");
            String password = sc.nextLine();

            System.out.println("\nGetting time required to get balance...\n");
            Clock clientTime = Clock.systemUTC();

            // registery is used to get the time of the server
            Registry reg_time = LocateRegistry.getRegistry("localhost", 8080);

            // invoking the remote method using the obtained object of the remote interface
            TimeInterface obj = (TimeInterface) reg_time.lookup("timeServer");

            long start = Instant.now().toEpochMilli(); // to get time in milliseconds
            long serverTime = obj.getSystemTime();
            System.out.println("Server time     :-  " + serverTime);
            long end = Instant.now().toEpochMilli();
            long roundTripTime = (end - start) / 2; // round trip time
            System.out.println("Round Trip Time :-  " + roundTripTime);
            long updatedTime = serverTime + roundTripTime;
            // updated time after request
            clientTime = Clock.offset(clientTime,
                    Duration.ofMillis(updatedTime - clientTime.instant().toEpochMilli()));
            // offset is used to set the time of the client
            System.out.println("New Client time :-  " + clientTime.instant().toEpochMilli());

            // check balance
            double bal = obj_bal.checkBalance(acc_no, password);
            if (bal == -1) {
                System.out.println("\nInvalid credentials, please try again.");
                return;
            } else {
                System.out.println("\nCurrent Balance: Rs." + bal + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
