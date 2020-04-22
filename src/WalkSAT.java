import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.System.exit;

public class WalkSAT {
    private int numVars;
    private int numClauses;
    private int k;
    private List<ArrayList<Integer>> literals = new ArrayList<>();
    private ArrayList<Integer> sol = new ArrayList<>();
    final static int MAX_TRIALS = 5000;
    static int counter = 0;
    public void getSol() {
        System.out.print("\nSolution:\n\t");
        for (int i=0; i< numVars-1; i++) {
            System.out.print(sol.get(i) + ", ");
        }
        System.out.println(sol.get(numVars-1) + "\n");
    }

    /**
     * Helper function to constructor
     * Random solution generator
     */
    private void generateSol() {
        for(int i=0; i < numVars; i++) {
            Random rd = new Random();   // create a random object
            boolean posNeg = rd.nextBoolean();  // random boolean value

            if(posNeg) {
                this.sol.add(i + 1);
            } else {
                this.sol.add(-1*(i+1));
            }
        }
    }

    public WalkSAT(String fileName) {
        // Read file
        List<String> clauses = new ArrayList<>();
        try(Stream<String> stream = Files.lines(Paths.get("src/" + fileName))) {
            clauses = stream.collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * Process individual clauses and store them
         */
        int i = 0;
        for (String line: clauses) {
            if(i==0) {
                String[] splitted = line.split(" ");
                this.numClauses = Integer.parseInt(splitted[2]);
                this.numVars = Integer.parseInt(splitted[3]);
                generateSol(); // create a random solution
            } else {
                String[] splitted = line.split(" ");
                ArrayList<Integer> temp = new ArrayList<>();
                for(int j=0; j<splitted.length; j++) {
                    temp.add(Integer.parseInt(splitted[j]));
                }
                temp.remove(temp.size()-1);
                this.literals.add(temp);
            }
            i++;
        }
        k = this.literals.get(1).size();

        System.out.println("Read k = " + this.k +
                "-CNF with m = " + this.numClauses + " clauses, n = " +
                this.numVars + " variables.");

        printClauses();
    }

    public void printClauses () {
        // Print Clauses
        System.out.println("Printing Clauses:");
        for (ArrayList<Integer> line: literals) {
            System.out.print("\t");
            for(int elt: line) {
                System.out.print(elt + "\t");
            }
            System.out.println("");
        }
        return;
    }

    public boolean check_all() {
        counter += 1;
        if(counter > MAX_TRIALS) {
            return false;
        }
        int count = 0;
        for(int i = 0; i < numClauses; i++) {
            if(check_clause(i)) {
                count++;
            } else {
                break;
            }
        }

        if(count != numClauses) {
            generateSol();
            return check_all();
        } else {
            return true;
        }
    }

    private boolean check_clause(int index) {
        ArrayList<Integer> clause = literals.get(index);
        for(int i=0; i < numVars; i++) {
            for(int j = 0; j < k; j++) {
                if(sol.get(i) == clause.get(j)) {
                    return true;
                }
            }
        }
        return false;
    }
}
