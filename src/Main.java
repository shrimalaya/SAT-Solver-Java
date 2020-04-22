import java.sql.SQLOutput;

import static java.lang.System.exit;

public class Main {
    final static long MAX_RUNS = 1999999999;

    public static void main(String args[]) {
        long startTime = System.nanoTime();
        int counter = 0;
        // Create a WalkSAT object
        WalkSAT mySAT = new WalkSAT("example2.txt");

        // Solve the walkSAT problem
        boolean result = mySAT.check_all();
        while(result == false) {
            result = mySAT.check_all();
            if (counter > MAX_RUNS) {
                System.out.println("\nMAX TRIALS EXCEEDED. No Solution found!");
                break;
            }
            counter++;
        }

        // Print the solution
        if(result)
            mySAT.getSol();

        long endTime = System.nanoTime();
        long timeElapsed = endTime - startTime;

        System.out.println("\nExecution time: " + timeElapsed + " ns.");
        System.out.println("In Secs = " + timeElapsed/1000000000.0 + " s.");
    }
}
