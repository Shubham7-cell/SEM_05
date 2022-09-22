import java.rmi.*;

// Remote interface for our getting time in application
public interface TimeInterface extends Remote {
    long getSystemTime() throws RemoteException;
}
