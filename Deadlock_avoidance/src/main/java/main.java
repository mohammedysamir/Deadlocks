import java.util.*;

public class main {
    /*
        Input:
            1. # of available resources in the system.
            2. # of processes.
            3. for each process we need to know:
                3.1. Allocated resources.
                3.2. Maximum resources.
        */
    // what to do: we need to calculate need matrix and update available after each request or release.
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int noResources, noProcesses;
        System.out.print("Enter number of Resources: ");
        noResources = scan.nextInt();
        System.out.print("Enter number of Processes: ");
        noProcesses = scan.nextInt();
        //a list to carry number of instances for each resource
        Integer[] Available = new Integer[noResources];
        System.out.println("*************************************************");
        System.out.println("Enter number of instances for each resource type");
        for (int i = 0; i < noResources; i++) {
            System.out.print("For resource number " + (i + 1) + " it has # of instances: ");
            int instance = scan.nextInt();
            Available[i] = instance;
        }
        //Creating the matrices
        Integer[][] Allocated = new Integer[noProcesses][noResources];
        System.out.println("*************************************************");
        System.out.println("Fill allocated resources to each process.");
        for (int i = 0; i < noProcesses; i++) {
            System.out.println("For Process number " + (i + 1));
            for (int j = 0; j < noResources; j++) {
                int instance = scan.nextInt();
                Allocated[i][j] = instance;
                Available[j] -= instance;
            }
        }
        System.out.println("*************************************************");
        System.out.println("Fill Maximum resources to each process.");
        Integer[][] Max = new Integer[noProcesses][noResources];
        for (int i = 0; i < noProcesses; i++) {
            System.out.println("For Process number " + (i + 1));
            for (int j = 0; j < noResources; j++) {
                int instance = scan.nextInt();
                Max[i][j] = instance;
            }
        }
        Integer[][] Need = new Integer[noProcesses][noResources];
        for (int i = 0; i < noProcesses; i++) {
            for (int j = 0; j < noResources; j++) {
                Need[i][j] = Max[i][j] - Allocated[i][j];
            }
        }
        /* for testing purposes
        System.out.println("*************************************************");
        System.out.println("Need Matrix:");
        for (int i = 0; i < noProcesses; i++) {
            for (int j = 0; j < noResources; j++) {
                System.out.print(Need[i][j] + " ");
            }
            System.out.print("\n");
        }
        System.out.println("*************************************************");
        System.out.print("Available: ");
        for (int i = 0; i < noResources; i++) {
            System.out.print(Available[i] + " ");
        }
        */
        scan.close();
    }
}
