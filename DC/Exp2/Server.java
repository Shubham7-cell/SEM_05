import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Server extends UnicastRemoteObject implements BalanceInterface {
    public Server() throws RemoteException {
        super(); // call the constructor of the parent class
    }

    // to store the account details
    static ArrayList<Account> accounts = new ArrayList<Account>();

    public double checkBalance(String accountNo, String password) throws RemoteException {
        System.out.println("Request received for account number : " + accountNo);
        for (Account i : accounts) {
            if (i.accountNo.equals(accountNo) && i.password.equals(password)) {
                return i.balance;
            }
        }
        return -1.0;
    }

    public static void main(String[] args) {
        String server = "bankServer";
        try {
            Registry reg = LocateRegistry.createRegistry(8000);
            // java.rmi.ConnectException: Connection refused to host
            System.setProperty("java.rmi.server.hostname", "192.168.164.1");
            // binding the remote object to the registry
            reg.rebind(server, new Server());
            accounts.add(new Account("777777", "Shubham", 9999.0));
            accounts.add(new Account("888888", "Aakanksha", 4321.50));
            accounts.add(new Account("999999", "Sakshi", 4440));
            accounts.add(new Account("111111", "Chirag", 0000));
            System.out.println("Server is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Account {
    String accountNo;
    String password;
    double balance;

    Account(String accountNo, String password, double balance) {
        this.accountNo = accountNo;
        this.password = password;
        this.balance = balance;
    }

    public double checkBalance(String accountNo, String password) {
        if (this.accountNo.equals(accountNo) && this.password.equals(password))
            return balance;
        else
            return -1.0;
    }

}