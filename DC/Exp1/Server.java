import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

// unicast remote object is a remote object that can only be accessed by one client at a time
public class Server extends UnicastRemoteObject implements BalanceInterface {
    public Server() throws RemoteException {
        super();
    }

    static ArrayList<Account> accounts = new ArrayList<Account>();

    public double checkBalance(String acctNo, String password) throws RemoteException {
        System.out.println("Request received for account number " + acctNo);
        for (Account i : accounts) {
            if (i.acc_no.equals(acctNo) && i.password.equals(password)) {
                return i.balance;
            }
        }

        return -1.0;
    }

    public static void main(String[] args) {
        String serverName = "bankServer";
        try {
            Registry reg = LocateRegistry.createRegistry(8000);
            reg.rebind(serverName, new Server()); // rebind is used to bind the object to the registry
            System.out.println("Server is running...");

            accounts.add(new Account("123456", "shubham", 2000.0));
            accounts.add(new Account("456789", "password2", 3700.50));
        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}

class Account {
    String acc_no;
    String password;
    double balance;

    Account(String acc_no, String password, double balance) {
        this.acc_no = acc_no;
        this.password = password;
        this.balance = balance;
    }

    public double checkBalance(String acc_no, String password) {
        if (this.acc_no.equals(acc_no) && this.password.equals(password))
            return balance;
        return -1.0;
    }

    public String toString() {
        return "Account number: " + acc_no + "\nPassword: " + password + "\nBalance: " + balance;
    }

}