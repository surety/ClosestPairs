// Author: Zeus Polanco Salgado
// Assignment 2 - CS 203
// June 2021

import java.util.Arrays;
import java.time.Instant;
import java.time.Duration;
import java.io.FileWriter;
import java.io.IOException;

public class Timer{
  private static ClosestPairExhaustiveSearch closestPairES;
  private static ClosestPairDivideAndConquer closestPairDC;

  public static void main(String[] args) {
    final String csvTitle = "Algorithm,InputSize,Time(nano)\n";
    String fileTitle = args[0];

    double[][] P;
    int n = 5, tests = 5, count;
    double[][] solutionDC, solutionES;
    double solutionDifference;
    closestPairES = new ClosestPairExhaustiveSearch();
    closestPairDC = new ClosestPairDivideAndConquer();

    int[][] test1 = {{62, 0}, {12, 1}, {27, 2}, {27, 3}, {52, 4}};
    int[][] test2 = {{62, 0}, {62, 0}, {62, 0}, {27, 3}, {52, 4}};
    int[][] test3 = {{62, 0}, {62, 1}, {62, 2}, {62, 3}, {62, 4}};
    int[][] test4 = {{19, 7}, {77, 8}, {48, 44}, {65, 94}, {41, 90}};

    try{
      FileWriter results = new FileWriter(fileTitle);
      results.write(csvTitle);
      long divideConquerTime = 0, exhaustiveTime = 0;

      for(n = 157000; n<500000; n+=500) {
        System.out.println("Current test size: " + n);
        count = 0;
        while(count < tests){
          // Generate P
          // P = {P.x, Q.y}, P.x = {x | 0, 100}, P.y = {y | 0, 100}
          P = new double[n][2];
          for(int i=0; i<n; i++){
            double sign = 1.0;
            if( (int) (Math.random() * 10) % 2 == 1 ) sign = -1.0;
            P[i][0] = (Math.random() * 50000) * sign;
            sign = 1.0;
            if( (int) (Math.random() * 10) % 2 == 1 ) sign = -1.0;
            P[i][1] = (Math.random() * 50000) * sign;
          }
          //if( validTest(P) ){
            // System.out.println(Arrays.deepToString(test4));
            System.out.println("\tTest sample: " + (count+1));
            System.out.println("\tTest size: " + n);
            divideConquerTime = testDivideAndConquer(P);
            exhaustiveTime = testDivideAndConquer(P);
            results.write(String.format("DivideAndConquer,%d,%d\n",n,divideConquerTime));
            results.write(String.format("ExhaustiveSearch,%d,%d\n",n,exhaustiveTime));
            System.out.println("\tSamples complete.");
            System.out.format("\tDivideAndConquer Time %d : %d 10e-9 seconds\n"
              , n, divideConquerTime);
            System.out.format("\tExhaustiveSearch Time %d : %d 10e-9 seconds\n"
              , n, exhaustiveTime);

            // Testing accuracy
            // solutionDC = closestPairDC.DivideAndConquer(P);
            //  System.out.println("\tResults from DC: " + Arrays.deepToString(solutionDC));
            // solutionES = closestPairES.ExhaustiveSearch(P);
            //  System.out.println("\tResults from ES: " + Arrays.deepToString(solutionES));
            // solutionDifference = calculateDistance(solutionDC)
            //                    - calculateDistance(solutionES);
            // System.out.println("Displacement: " + solutionDifference);
            // System.out.println("\tSame solution: " + (solutionDifference == 0));

            count++;
            System.out.println("Test complete!");
          //}
        } // End of while
      } // End of for
      results.close();
      System.out.println("Done!");
    }catch(IOException e){System.out.println("Error writting results!");}
  } // End of main()

  private static boolean validTest(double[][] test){
    for (int i=0; i<test.length-1; i++)
      for (int j=i+1; j<test.length; j++)
        if(test[i][0] == test[j][0] && test[i][1] == test[j][1]) return false;
    return true;
  }

  private static long testExhaustiveSearch(double[][] input){
    Instant start, stop;
    long ns;
    start = Instant.now();
    closestPairES.ExhaustiveSearch(input);
    stop = Instant.now();
    ns = Duration.between(start, stop).toNanos();
    return ns;
  }

  private static long testDivideAndConquer(double[][] input){
    Instant start, stop;
    long ns;
    start = Instant.now();
    closestPairDC.DivideAndConquer(input);
    stop = Instant.now();
    ns = Duration.between(start, stop).toNanos();
    return ns;
  }

  private static double calculateDistance(double[][] points){
    return
        (points[0][0] - points[1][0]) * (points[0][0] - points[1][0]) +
        (points[0][1] - points[1][1]) * (points[0][1] - points[1][1]);
  }
}
