// Author: Zeus Polanco Salgado
// CS 203 - Assignment 2.ClosestPairDivideAndConquer
// May/June 2021

import java.util.*;
import static java.lang.Math.pow;
import static java.lang.Math.random;
import static java.lang.Math.ceil;
import static java.lang.Math.floor;
import static java.lang.Math.min;
import static java.lang.Math.abs;

public class ClosestPairDivideAndConquer {

  // Desc. Helper functions calculates distance
  // Input: Two points
  // Output: Distance
  private static double calculateDistance(double[][] points){
    return Math.sqrt(
        Math.pow(points[0][0] - points[1][0], 2)
      + Math.pow(points[0][1] - points[1][1], 2) );
  }

  // Performs HoarePartioning on array of points. First element as key.
  // Input: Set of points A
  // Output: Index of partioning
  private static int HoarePartion(double[][] A){
    int indexOfPivot = 0;
    double pivot;
    double[] temp;
    int i = 0, j = A.length;
    if(A.length < 2) return 0;

    // Prevents infinite loop
    while(indexOfPivot<A.length-1 && A[indexOfPivot][0] == A[A.length-1][0])
      indexOfPivot++;
    pivot = A[indexOfPivot][0];
    do{
      do i++; while( A[i][0] < pivot && i < A.length-1);
      do j--; while( A[j][0] > pivot && j > -1);
      temp = A[i];
      A[i] = A[j];
      A[j] = temp;
    }while(i < j);
    temp = A[i];
    A[i] = A[j];
    A[j] = temp;

    temp = A[indexOfPivot];
    A[indexOfPivot] = A[j];
    A[j] = temp;
    return j;
  }

  // Quicksort with Hoare Partioning
  // Desc. Sorts set of points by the X value, uses Hoare Partioning
  // Input: Set of points A
  // Output: Set of points A
  private static double[][] Quicksort(double[][] A){
    double[][] solution = new double[A.length][2];
    double[][] leftPartion, rightPartion;
    int l, r, indexOfPivot;
    if(A.length < 2) return A;
    if(A.length == 2){
      if(A[0][0] > A[1][0]){
        double[] temp = A[0];
        A[0] = A[1];
        A[1] = temp;
      }
      return A;
    }
    indexOfPivot = HoarePartion(A);
    l = indexOfPivot+1; r = A.length - (indexOfPivot+1);
    leftPartion = new double[l][2];
    rightPartion = new double[r][2];
    for(int i=0; i<A.length; i++){
      if(i < l)
        leftPartion[i] = A[i];
      else
        rightPartion[i-l] = A[i];
    }

    rightPartion = Quicksort(rightPartion);
    leftPartion = Quicksort(leftPartion);

    for(int i=0; i<A.length; i++){
      if(i < l)
        solution[i] = leftPartion[i];
      else
        solution[i] = rightPartion[i-l];
    }
    return solution;
  }

  // Mergesort
  // Desc. Sorts set of points by the Y value
  // Input: Set of points A
  // Output: Set of points A
  private static double[][] Mergesort(double[][] A){
    double[][] B, C, solution;
    if(A.length < 2) return A;
    B = new double[(int)Math.floor(A.length/2.0)][2];
    C = new double[(int)Math.ceil(A.length/2.0)][2];
    solution = new double[A.length][2];
    for (int i=0; i<A.length; i++){
      if(i < B.length)
        B[i] = A[i];
      else
        C[i-B.length] = A[i];
    }
    B = Mergesort(B);
    C = Mergesort(C);

    // Merge
    // i = marker for B
    // j = marker for C
    // k = marker for solution
    int i=0, j=0, k=0;
    while(i < B.length && j < C.length){
      if(B[i][1] <= C[j][1]){ solution[k] = B[i]; i++; }
      else{ solution[k] = C[j]; j++; }
      k++;
    }
    if( i == B.length )
      for(int h=0; h<C.length-j; h++)
        solution[k+h] = C[j+h];
    else
      for(int h=0; h<B.length-i; h++)
        solution[k+h] = B[i+h];

    return solution;
  }

  // Desc. Helper function. Sorts P and initiates recursive function
  // Input: Set of Points P
  // Output: Solution set of Points
  public static double[][] DivideAndConquer(double[][] input){
    double[][] P = new double[input.length][2];
    double[][] Q = new double[input.length][2];

    // Organize points into two arrays
    // P[0] = {x}, P[1] = {y}, Q[0] = {y}, Q[1] = {x}
    for(int i=0; i<input.length; i++){
      P[i][0] = input[i][0]; P[i][1] = input[i][1];
      Q[i][0] = input[i][0]; Q[i][1] = input[i][1];
    }
    P = Quicksort(P);

    return DivideAndConquerRec(P);
  }

  // Recursive implementation of Divide and Conquer algorithm
  // Input: Set of sorted points P
  // Output: Two closest points in set
  private static double[][] DivideAndConquerRec(double[][] P){
    double[][] solution = {{0.0, 0.0}, {0.0, 0.0}};
    // Solve by brute force
    if( P.length == 2){
      return P;
    }
    else if( P.length == 3 ){
      // [0] && [1]
      double d1 =
        (P[0][0] - P[1][0]) * (P[0][0] - P[1][0]) +
        (P[0][1] - P[1][1]) * (P[0][1] - P[1][1]);
      // [1] && [2]
      double d2 =
        (P[1][0] - P[2][0]) * (P[1][0] - P[2][0]) +
        (P[1][1] - P[2][1]) * (P[1][1] - P[2][1]);
      // [0] && [2]
      double d3 =
        (P[0][0] - P[2][0]) * (P[0][0] - P[2][0]) +
        (P[0][1] - P[2][1]) * (P[0][1] - P[2][1]);
      if( d1 < d2 ){
        if( d1 < d3 )
          {solution[0] = P[0]; solution[1] = P[2];}
        // d3 <= 1
        else
          {solution[0] = P[0]; solution[1] = P[2];}
      // d2 <= d1
      }else{
        if( d2 < d3 )
          {solution[0] = P[1]; solution[1] = P[2];}
        // d3 <= 2
        else{
          {solution[0] = P[0]; solution[1] = P[2];}
        }
      }
    }
    else{
      double[][] Q, Pl, Pr, S;
      double[][] left, right;
      int num = 0;
      double dl, dr, d, min, median;

      // Organize points into two arrays
      // P[0] = {x}, P[1] = {y}, Q[0] = {y}, Q[1] = {x}
      Pl = new double[(int)Math.ceil(P.length/2.0)][2];
      Pr = new double[(int)Math.floor(P.length/2.0)][2];
      for(int i=0; i<P.length; i++){
        // First half of points
        if( i<Math.ceil(P.length / 2.0 ) ){
          Pl[i][0] = P[i][0]; Pl[i][1] = P[i][1];
        // Seconds half of points
        }else{
          Pr[i-Pl.length][0] = P[i][0]; Pr[i-Pl.length][1] = P[i][1];
        }
      }

      // Find the closests pairs of left partion and right partion
      left = DivideAndConquerRec( Pl );
      right= DivideAndConquerRec( Pr );
      dl = calculateDistance( left );
      dr = calculateDistance( right );
      if( dl < dr ){
        d = dl;
        solution = left;
      }else{
        d = dr;
        solution = right;
      }
      median = P[(int)Math.ceil(P.length/2.0) -1][0];

      // Prepare Q
      Q = new double[P.length][2];
      for(int i=0; i<P.length; i++)
        Q[i] = P[i];
      Q = Mergesort(Q);

      // copy all the points of Q for which |x - m| < d into array S[0..num -1]
      S = new double[Q.length][2];
      for (int i=0; i<Q.length; i++) {
        if( Math.abs( Q[i][0] - median ) < d ){
          S[num] = Q[i];
          num++;
        }
      }
      // Find if there is two closer pairs near the median for both sides
      double dminsq = d*d;
      for(int i=0; i<num-1; i++){
        int k = i + 1;
        // Stop whenever k is more than d further from i
        while( k < num && Math.pow(S[k][1] - S[i][1], 2) < dminsq){
          d = Math.pow( S[k][1] - S[i][1], 2 )
            + Math.pow( S[k][0] - S[i][0], 2 );
          if( d < dminsq ){
            dminsq = d;
            solution[0] = S[k]; solution[1] = S[i];
          } // Otherwise, solution remains the same
          k++;
      }}
    }
    return solution;
  }

  // MAIN METHOD
  // Desc. Test ClosestPairDivideAndConquer
  public static void main(String[] args){
    double[][] solution, S;
    int n;
    Scanner scan = new Scanner( System.in );
    System.out.println();
    System.out.println("\t\t Closest-Pair Divide & Conquer");
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

    solution = DivideAndConquer(S);

     System.out.println("Results: " + Arrays.deepToString(solution));
  }
}
