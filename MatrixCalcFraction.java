// Code by: Paden Myjak
// MAS 3105 - Matrix and Linear Algebra
// Extra credit assignments

import java.util.*;
import java.io.*;

public class MatrixCalc
{
  public static Fraction[][] matrix;

  public static void main(String [] args) throws Exception
  {
    // Variables
    Scanner in = new Scanner(System.in);

    // Fraction Tests
    String[] strFracts = {"1/2", "1/3", "0/1", "1/0", "2/3", "15/30", "1234c", "c234", "0/0", null, ""};
    for (String s : strFracts)
    {
      try
      {
        System.out.println(s + " isFract: " + Fraction.isFraction(s));
        System.out.println("Fract: " + Fraction.parseFraction(s));
        int res = Fraction.parseFraction(strFracts[0]).compareTo(Fraction.parseFraction(s));
        System.out.println("Undef: " + Fraction.parseFraction(s).isUndefined());
        if (res == 0)
          System.out.println("Compare: Equal");
        else if (res < 0)
          System.out.println("Compare: Less Than");
        else if (res > 0)
          System.out.println("Compare: Greater Than");

      }
      catch (FractionFormatException ex)
      {
        System.out.println(ex.getMessage());
      }
      catch (FractionUndefinedException undef)
      {
        System.out.println("UNDEF: " + undef.getMessage());
      }
      catch (NullPointerException en)
      {
        System.out.println("NPE: " + en.getMessage());
      }
      catch (Exception e)
      {
        System.out.println(e.getMessage());
      }
      System.out.println("");
    }

    // Main menu
    clearScreen();
    boolean doLoop = true;
    while(doLoop)
    {
      int select = 0;

      System.out.println("Please select from the following options.");
      printMenu();
      System.out.print("Your selection: ");
      try
      {
        select = in.nextInt();
      }
      catch (Exception e)
      {
        String temp = in.nextLine();
        clearScreen();
        System.out.println("Invalid input format: please give an integer.");
        System.out.println();
        continue;
      }

      // Switch
      if(select == 0)
      {
        clearScreen();
        System.out.println("End of program.");
        doLoop = false;
      }
      else if (select == 1)
      {
        clearScreen();
        inputMatrix(in);
      }
      else if (select == 2)
      {
        clearScreen();
        printMatrixOption();
      }
      else if (select == 3)
      {
        clearScreen();
        rref();
      }
      else {
        clearScreen();
        System.out.println("Invalid selection.");
        System.out.println();
      }
    }
    // Cleaning phase
    in.close();
  }


  // Selection Methods
  public static void inputMatrix(Scanner in)
  {
    int numRows = 0, numCols = 0;
    boolean doLoop;

    // Input numRows
    doLoop = true;
    while (doLoop)
    {
      System.out.print("Number of rows: ");
      try
      {
        numRows = in.nextInt();
        doLoop = false;
      }
      catch (Exception e)
      {
        clearScreen();
        String temp = in. nextLine();
        System.out.println("Invalid input format: ''" + temp + "'' is not of type 'int'.");
      }
    }

    // Input numCols
    doLoop = true;
    while (doLoop)
    {
      System.out.print("Number of columns: ");
      try
      {
        numCols = in.nextInt();
        doLoop = false;
      }
      catch (Exception e)
      {
        clearScreen();
        String temp = in. nextLine();
        System.out.println("Invalid input format: ''" + temp + "'' is not of type 'int'.");
      }
    }

    // Make array
    MatrixCalc.matrix = new Fraction[numRows][numCols];

    // Input for each element of the MatrixCalc
    System.out.println("Please insert elements into the " + numRows +"x" + numCols + " matrix:");
    for (int i = 0; i < numRows; i++)
    {
      for (int j = 0; j < numCols; j++)
      {
        doLoop = true;
        while (doLoop)
        {
          try
          {
            matrix[i][j] = Fraction.parseFraction(in.next());
            doLoop = false;
          }
          catch (Exception e)
          {
            String temp = in.next();
            System.out.println("Invalid input format: '" + temp + "' is not of type 'Fraction'.");
            matrix[i][j].set(0, 1);
          }
        }
      }
    }
    clearScreen();
    System.out.println("Matrix successfully updated!");
  }
  public static void printMatrixOption()
  {
    System.out.println("Your current matrix:");
    printMatrix(MatrixCalc.matrix);
    System.out.println("");
  }
  public static void rref()
  {
    Fraction[][] rrefMatrix = MatrixCalc.matrix;
    int numRows = MatrixCalc.matrix.length;
    int numCols = MatrixCalc.matrix[0].length;
    int colDone = 0;

    for (int C = 0; C < numCols; C++)
    {
      for (int R = colDone; R < numRows; R++)
      {
        // Find first non-zero term
        if (rrefMatrix[R][C].getNumer() != 0)
        {
          // Make first term 1
          if (rrefMatrix[R][C].getNumer() != 1 && rrefMatrix[R][C].getDenom() != 1)
          {
            System.out.println("Divide Row " + (R+1) + " by (" + rrefMatrix[R][C] + "):");
            divideRow(rrefMatrix[R], rrefMatrix[R][C]);
            printMatrix(rrefMatrix);
            System.out.println("");
          }

          // Swaps trows in the wrong place
          if (R != colDone)
          {
            System.out.println("Swap Row " + (R+1) + " and Row " + (colDone+1) + ":");
            swapRows(rrefMatrix[R], rrefMatrix[colDone]);
            printMatrix(rrefMatrix);
            System.out.println("");
          }
          // subRowFromOther
          for (int i = 0; i < numRows; i++)
          {
            if (i != colDone && rrefMatrix[i][C].getNumer() != 0)
            {
              Fraction temp = rrefMatrix[colDone][C].getCopy();
              temp.mult(-1);
              Fraction coef = rrefMatrix[i][C].getCopy();
              coef.divide(temp);
              System.out.println("Subtract (" + coef + ") * Row " + (colDone+1) + " from Row " + (i+1) + ":");
              addRows(rrefMatrix[i], rrefMatrix[colDone], coef);
              printMatrix(rrefMatrix);
              System.out.println("");
            }
          }
          colDone++;
          break;
        }
      }
    }

    // Done!
    System.out.println("RREF of the given matrix is:");
    printMatrix(rrefMatrix);
    System.out.println("");
    //  System.out.println("Would you like to update the matrix?");
    //  System.out.println("Selection [y/n]: n");
    //  System.out.println("RREF of the given matrix was not saved.");
    //  System.out.println("");
  }

  // RREF Helpetempr Methods
  public static void divideRow(Fraction[] row, Fraction d)
  {
    for (int i = 0; i < row.length; i++)
      if (row[i].getNumer() != 0)
        row[i].divide(d);
  }
  public static void addRows(Fraction[] row1, Fraction[] row2, Fraction m)
  {
    for (int i = 0; i < row1.length; i++)
    {
      Fraction temp = row2[i].getCopy();
      temp.mult(m);
      row1[i].add(temp);
    }
  }
  public static void swapRows(Fraction[] row1, Fraction[] row2)
  {
    Fraction[] temp = new Fraction[row1.length];
    for (int i = 0; i < row1.length; i++)
    {
      temp[i] = row1[i];
      row1[i] = row2[i];
      row2[i] = temp[i];
    }
  }

  // Print Menu method
  public static void printMenu()
  {
    System.out.println("\t1) Update the matrix");
    System.out.println("\t2) Print the matrix");
    System.out.println("\t3) RREF of the matrix");
    System.out.println("\t0) Quit Program");
  }


  // Clear Terminal method
  public static void clearScreen()
  {
    System.out.println("--------------------------------------------------");
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }


  // Print Matrix helper
  public static void printMatrix(Fraction[][] mat)
  {
    for (int i = 0; i < mat.length; i++)
    {
      for (int j = 0; j < mat[0].length; j++)
      {
        System.out.printf("%.02f\t", mat[i][j]);
      }
      System.out.println("");
    }
  }
}
