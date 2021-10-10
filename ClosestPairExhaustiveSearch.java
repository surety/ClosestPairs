// Author: Zeus Polanco Salgado
// CS 203 - Assignment 2.ClosestPairExhaustiveSearch
// May 2021

import java.util.*;
import static java.lang.Math.pow;
import static java.lang.Math.random;

// ClosestPairExhaustiveSearch class contains solution and tests.
public class ClosestPairExhaustiveSearch {

  // Find the closest pair of a set P by brute force
  // Input: Set S (int[][], n > 1), contains [P[], and Q[]]
  // Output: int[] - the pair with the least distance
  public static double[][] ExhaustiveSearch(double[][] P){
    double[][] solution = {{0.0, 0.0}, {0.0, 0.0}};
    double minDistance = Double.MAX_VALUE;
    double thisDistance;
    for(int i = 0; i<P.length-1; i++)
      for(int j = i+1; j<P.length; j++){
        // (P[i].x - P[j].x)^2 + (Q[i].y - Q[j].y)^2
        thisDistance = (P[i][0] - P[j][0]) * (P[i][0] - P[j][0])
                     + (P[i][1] - P[j][1]) * (P[i][1] - P[j][1]);
        if( thisDistance < minDistance ){
          minDistance = thisDistance;
          solution[0] = P[i]; solution[1] = P[j];
        }
      }

    return solution;
  }

  // MAIN METHOD
  // Desc. Test ClosestPairExhaustiveSearch
  public static void main(String[] args){
    double[][] solution, S;
    int n;
    Scanner scan = new Scanner( System.in );
    System.out.println();
    System.out.println("\t\t Closest-Pair Exhaustive Search");
    System.out.println("Enter number of points: ");
    n = scan.nextInt();
    scan.close();

    // Generate S
    // S = {P, Q}, P = {x | 0, 100}, Q = {y | 0, 100}
    S = new double[n][2];
    for(int i=0; i<n; i++){
      double sign = 1.0;
      if( (int) (Math.random() * 10) % 2 == 1 ) sign = -1.0;
      S[i][0] = (Math.random() * 50000) * sign;
      sign = 1.0;
      if( (int) (Math.random() * 10) % 2 == 1 ) sign = -1.0;
      S[i][1] = (Math.random() * 50000) * sign;
    }

    System.out.println();
    System.out.println("Input generated: " + Arrays.deepToString(S));
    System.out.println("Points are randomly generated in range of (-50k, 50k)");

    solution = ExhaustiveSearch(S);

     System.out.println("Results: " + Arrays.deepToString(solution));
  }
}
