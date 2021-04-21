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
    static void Request(int Process_no, int[] Requested_amt, int[] Available, int[][] Allocated, int[][] Need) {
        boolean Error = false;
        for (int i = 0; i < Requested_amt.length; i++) {
            //handling request > need
            if (Requested_amt[i] > Need[Process_no][i]) {
                System.out.println("Error: Request resource #" + (i + 1) + " out of defined Needs");
                Error = true;
                break;
            }
            //handling request > available
            if (Requested_amt[i] > Available[i]) {
                System.out.println("Error: Request resource #" + (i + 1) + " out of Available resources #" + (i + 1));
                Error = true;
                break;
            }
        }
        if (Error) return;
        //pretend to allocate
        for (int i = 0; i < Requested_amt.length; i++) {
            Available[i] -= Requested_amt[i];
            Need[Process_no][i] -= Requested_amt[i];
            Allocated[Process_no][i] += Requested_amt[i];
        }
        System.out.println("Request granted successfully.");
    }

    //Release just handling amount released to not go out of bounds
    static void Release(int Process_no, int[] Released_amt, int[] Available, int[][] Allocated) {
        for (int i = 0; i < Released_amt.length; i++) {
            if (Allocated[Process_no][i] < Released_amt[i]) {
                System.out.println("Error: Can't release resource's instance #" + (i + 1) + " released amount greater than allocated");
                return;
            } else {
                //release resources from the process allocation
                Allocated[Process_no][i] -= Released_amt[i];
                //re-add resources to Available
                Available[i] += Released_amt[i];
            }
        }
        System.out.println("Process #" + Process_no + " has released the desired resources.");
    }

    static boolean isSafe(int no_processes, int[] Available, int[][] Need, int[][] Allocated) {
        int[] Work = Available;
        boolean[] Finish = new boolean[no_processes];
        for (int i = 0; i < no_processes; i++) {
            //init finish to false
            Finish[i] = false;
        }
        boolean allFinished = true;
        for (int i = 0; i < no_processes; i++) {
            for (int j = 0; j < Available.length; j++) {
                if (!Finish[i] && Need[i][j] <= Work[j]) {
                    allFinished = false;
                    Work[j] += Allocated[i][j];
                    Finish[i] = true;
                } else {
                    allFinished = true;
                }
            }
        }
        if (allFinished) {
            System.out.println("System is safe.");
        } else
            System.out.println("System is unsafe");
        return allFinished;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int noResources, noProcesses;
        System.out.print("Enter number of Resources: ");
        noResources = scan.nextInt();
        System.out.print("Enter number of Processes: ");
        noProcesses = scan.nextInt();
        //a list to carry number of instances for each resource
        int[] Available = new int[noResources];
        System.out.println("*************************************************");
        System.out.println("Enter number of instances for each resource type");
        for (int i = 0; i < noResources; i++) {
            System.out.print("For resource number " + (i + 1) + " it has # of instances: ");
            int instance = scan.nextInt();
            Available[i] = instance;
        }
        //Creating the matrices
        int[][] Allocated = new int[noProcesses][noResources];
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
        int[][] Max = new int[noProcesses][noResources];
        for (int i = 0; i < noProcesses; i++) {
            System.out.println("For Process number " + (i + 1));
            for (int j = 0; j < noResources; j++) {
                int instance = scan.nextInt();
                Max[i][j] = instance;
            }
        }
        int[][] Need = new int[noProcesses][noResources];
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
        boolean Continue;
        do {
            Continue = true;
            int choice;
            System.out.println("1. Request additional resources.\n2. Release resources.\n3. Print all data");
            choice = scan.nextInt();
            System.out.println("Enter number of process");
            int Process_no;
            switch (choice) {
                case 1:
                    System.out.println("and set of amount of requested resources");
                    Process_no = scan.nextInt();
                    int[] ResourceRequested = new int[noResources];
                    for (int i = 0; i < noResources; i++) {
                        int amount = scan.nextInt();
                        ResourceRequested[i] = amount;
                    }
                    Request(Process_no, ResourceRequested, Available, Allocated, Need);
                    break;
                case 2:
                    System.out.println("and set of amount of released resources");
                    Process_no = scan.nextInt();
                    int[] ResourcesReleased = new int[noResources];
                    for (int i = 0; i < noResources; i++) {
                        int amount = scan.nextInt();
                        ResourcesReleased[i] = amount;
                    }
                    Release(Process_no, ResourcesReleased, Available, Allocated);
                    break;
                case 3:
                    System.out.println("*************************************************");
                    System.out.print("Available: ");
                    for (int i = 0; i < noResources; i++) {
                        System.out.print(Available[i] + " ");
                    }
                    System.out.println("*************************************************");
                    System.out.println("Allocated Matrix:");
                    for (int i = 0; i < noProcesses; i++) {
                        for (int j = 0; j < noResources; j++) {
                            System.out.print(Allocated[i][j] + " ");
                        }
                        System.out.print("\n");
                    }
                    System.out.println("*************************************************");
                    System.out.println("Maximum Matrix:");
                    for (int i = 0; i < noProcesses; i++) {
                        for (int j = 0; j < noResources; j++) {
                            System.out.print(Max[i][j] + " ");
                        }
                        System.out.print("\n");
                    }
                    System.out.println("*************************************************");
                    System.out.println("Need Matrix:");
                    for (int i = 0; i < noProcesses; i++) {
                        for (int j = 0; j < noResources; j++) {
                            System.out.print(Need[i][j] + " ");
                        }
                        System.out.print("\n");
                    }
                    break;
            }
            System.out.println("Do you want to request or release another resources ?");
            Continue = scan.nextBoolean();
        } while (Continue);
        scan.close();
    }
}
/*
 Slide's testcase
 3
 5
 10 5 7

 0 1 0
 2 0 0
 3 0 2
 2 1 1
 0 0 2

 7 5 3
 3 2 2
 9 0 2
 2 2 2
 4 3 3
 */