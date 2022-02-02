// Code by: Paden Myjak
// MAS 3105 - Matrix and Linear Algebra
// Extra credit assignments

import java.util.*;
import java.io.*;

class RowOp
{
  public int op;
  public double val;
  public int row1;
  public int row2;

  RowOp(int opNum, double value, int rowOne, int rowTwo)
  {
    this.op = opNum;
    this.val = value;
    this.row1 = rowOne;
    this.row2 = rowTwo;
  }

  public String toString()
  {
    if (this.op == 1) // Add: val * R1 + R2
      return ("Add (" + this.val+ ") * Row" + (this.row1+1) + " to Row" + (this.row2+1));
    if (this.op == 2) // Div: R1 / val
      return ("Divide Row" + (this.row1+1) + " by (" + this.val + ")");
    if (this.op == 3) // Swap: R1 & R2
      return ("Swap Row" + (this.row1+1) + " and Row" + (this.row2+1));

    return null;
  }
}

public class matrixCalcRREF
{
  public static int N = 3;
  public static double[][] matrix;
  public static int numMats = 10;
  public static double[][][] mat = new double[numMats][][];
  public static boolean[] isUpdate = new boolean[numMats];
  public static ArrayList<RowOp> ops = new ArrayList<>();

  public static void main(String [] args) throws Exception
  {
    // Variables
    Scanner in = new Scanner(System.in);

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
        int temp = selectMatrixOption(in);
        clearScreen();
        System.out.println("Matrix " + temp + ": ");
        printMatrix(temp - 1);
        System.out.println("");
      }
      else if (select == 3)
      {
        clearScreen();
        rref(in);
      }
      else if (select == 4)
      {
        clearScreen();
        ref(in);
      }
      else if (select == 5)
      {
        clearScreen();
        alu(in);
      }
      else if (select == 6)
      {
        clearScreen();
        System.out.println("Opperations: ");
        for (RowOp r : matrixCalcRREF.ops)
        System.out.println("  " + r.toString());
        System.out.println("");
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
    int matIndex = -1;
    int numRows = 0, numCols = 0;
    boolean doLoop;
    double[][] newMat;

    // Matrix updated
    doLoop = true;
    while (doLoop)
    {
      System.out.println("Available Matrixes: ");
      printMatrixAvailable();
      System.out.println("");
      System.out.print("Matrix to update: ");

      try
      {
        matIndex = in.nextInt();

        if (matIndex > 0 && matIndex <= numMats)
          doLoop = false;
        else
        {
          clearScreen();
          System.out.println("Please enter a number between 1 and " + numMats + ".");
          System.out.println("");
        }
      }
      catch (Exception e)
      {
        clearScreen();
        String temp = in. nextLine();
        System.out.println("Invalid input format: ''" + temp + "'' is not of type 'int'.");
      }
    }
    System.out.println("");

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
    newMat = new double[numRows][numCols];

    // Input for each element of the matrixCalcRREF
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
            newMat[i][j] = in.nextDouble();
            doLoop = false;
          }
          catch (Exception e)
          {
            String temp = in.next();
            System.out.println("Invalid input format: ''" + temp + "'' is not of type 'double'.");
            newMat[i][j] = 0;
          }
        }
      }
    }

    mat[matIndex - 1] = newMat;
    isUpdate[matIndex - 1] = true;
    clearScreen();
    System.out.println("Matrix successfully updated!");
    System.out.println("");
  }

  public static void ref(double[][] refMatrix)
  {
    int numRows;
    int numCols;
    int lead = 0;
    int matIndex = -1;
    boolean doLoop = true;

    numRows = refMatrix.length;
    numCols = refMatrix[0].length;
    ops.clear();

    for (int r = 0; r < numRows; r++)
    {
      boolean allZero = true;
      for (int c = 0; c < numCols; c++)
      {
        if (refMatrix[r][c] != 0)
        {
          allZero = false;
          break;
        }
      }

      if (allZero)
      {
        // Swap Rows r and numRows
        System.out.println("Swap Row " + (r+1) + " and Row " + (numRows+1) + ":");
        ops.add(new RowOp(3, 0.00, r, numRows-1));
        swapRows(refMatrix[r], refMatrix[numRows-1]);
        printMatrix(refMatrix);
        System.out.println("");
      }
    }

    int p = 0;
    while (p < numRows && p < numCols)
    {
      //  nextPivot
      int r = 1;
      while (refMatrix[p][p] == 0)
      {
        if (p + r <= numRows)
        {
          p++;
          break;
        }

        // Swap Rows p and p+1
        System.out.println("Swap Row " + (p+1) + " and Row " + (p+2) + ":");
        ops.add(new RowOp(3, 0.00, p, p+1));
        swapRows(refMatrix[p], refMatrix[p+1]);
        printMatrix(refMatrix);
        System.out.println("");

        r++;
      }

      for (r = 1; r < (numRows - p); r++)
      {
        if (refMatrix[p+r][p] != 0)
        {
          double coef = (-1) * (refMatrix[p+r][p] / refMatrix[p][p]);
          System.out.println("Add (" + coef + ") * Row " + (p+1) + " from Row " + (p+r+1) + ":");
          matrixCalcRREF.ops.add(new RowOp(1, coef, p, p+r));
          addRows(refMatrix[p+r], refMatrix[p], coef);
          printMatrix(refMatrix);
          System.out.println("");
        }
      }
      p++;

      for (int R = 0; R < numRows; R++)
      {
        lead = 0;
        while (refMatrix[R][lead] == 0)
          lead++;
/*
        if (refMatrix[R][lead] != 1)
        {
          // Divide row R by leading R
          System.out.println("Divide Row " + (R+1) + " by (" + refMatrix[R][lead] + "):");
          ops.add(new RowOp(2, refMatrix[R][lead], R, 0));
          divideRow(refMatrix[R], refMatrix[R][lead]);
          printMatrix(refMatrix);
          System.out.println("");
        }
        */
      }
    }

    // Done!
    clearScreen();
    System.out.println("Row Echelon Form of the given matrix is:");
    printMatrix(refMatrix);
    System.out.println("");
  }

  public static void ref(Scanner in)
  {
    // Get the primat]ry matrix, and check that it has been updated before
    int matIndex = -1;
    boolean doALU = true;
    while (doALU)
    {
      System.out.println("Matrix to perform REF on: ");
      matIndex = selectMatrixOption(in);
      if (isUpdate[matIndex - 1])
      doALU = false;
      else
      {
        clearScreen();
        System.out.println("That matrix has not been initialized.");
      }
    }

    System.out.println("Your selected matrix:");
    printMatrix(matIndex - 1);
    System.out.println("");

    ref(mat[matIndex - 1]);
  }

  public static void rref(Scanner in)
  {
    double[][] rrefMatrix;
    int numRows;
    int numCols;
    int colDone = 0;
    int matIndex = -1;
    boolean doLoop = true;
    matrixCalcRREF.ops.clear();

    System.out.print("Matrix to perform RREF on: ");
    System.out.println("");
    matIndex = selectMatrixOption(in);

    rrefMatrix = mat[matIndex - 1];
    numRows = rrefMatrix.length;
    numCols = rrefMatrix[0].length;
    System.out.println("Your selected matrix:");
    printMatrix(matIndex - 1);
    System.out.println("");

    for (int C = 0; C < numCols; C++)
    {
      for (int R = colDone; R < numRows; R++)
      {
        // Find first non-zero term
        if (rrefMatrix[R][C] != 0)
        {
          // Make first term 1
          if (rrefMatrix[R][C] != 1)
          {
            System.out.println("Divide Row " + (R+1) + " by (" + rrefMatrix[R][C] + "):");
            matrixCalcRREF.ops.add(new RowOp(2, rrefMatrix[R][C], R, 0));
            divideRow(rrefMatrix[R], rrefMatrix[R][C]);
            printMatrix(matIndex - 1);
            System.out.println("");
          }

          // Swaps trows in the wrong place
          if (R != colDone)
          {
            System.out.println("Swap Row " + (R+1) + " and Row " + (colDone+1) + ":");
            matrixCalcRREF.ops.add(new RowOp(3, 0.00, colDone, R));
            swapRows(rrefMatrix[R], rrefMatrix[colDone]);
            printMatrix(matIndex - 1);
            System.out.println("");
          }
          // subRowFromOther
          for (int i = 0; i < numRows; i++)
          {
            if (i != colDone && rrefMatrix[i][C] != 0)
            {
              double coef = -1 * (rrefMatrix[i][C] / rrefMatrix[colDone][C]);
              System.out.println("Add (" + coef + ") * Row " + (colDone+1) + " from Row " + (i+1) + ":");
              matrixCalcRREF.ops.add(new RowOp(1, coef, colDone, i));
              addRows(rrefMatrix[i], rrefMatrix[colDone], coef);
              printMatrix(matIndex - 1);
              System.out.println("");
            }
          }
          colDone++;
          break;
        }
      }
    }

    // Done!
    isUpdate[matIndex - 1] = true;
    System.out.println("RREF of the given matrix is:");
    printMatrix(matIndex - 1);
    System.out.println("");
  }

  public static void alu(Scanner in)
  {
    double[][] aluMatrix;
    double[][] UMatrix, LMatrix;
    int UIndex, LIndex;
    int numRows;
    int numCols;
    int lead = 0;
    int matIndex = -1;
    boolean doLoop = true;
    ops.clear();

    // Get the primat]ry matrix, and check that it has been updated before
    boolean doALU = true;
    while (doALU)
    {
      System.out.println("Matrix to perform A = LU on: ");
      matIndex = selectMatrixOption(in);
      if (isUpdate[matIndex - 1])
        doALU = false;
      else
      {
        clearScreen();
        System.out.println("That matrix has not been initialized.");
      }
    }

    aluMatrix = mat[matIndex - 1];
    numRows = aluMatrix.length;
    numCols = aluMatrix[0].length;
    System.out.println("Your selected matrix:");
    printMatrix(matIndex - 1);
    System.out.println("");

    if (numRows != numCols)
    {
      System.out.println("the given matrix cannot be factored.");
      System.out.println();
      return;
    }

    // Set L as Identity matrix`
    System.out.println("Matrix to set as L: ");
    LIndex = selectMatrixOption(in);
    mat[LIndex - 1] = LMatrix = new double[numRows][numCols];
    for (int R = 0; R < numRows; R++)
      for (int C = 0; C < numCols; C++)
        if (R == C) LMatrix[R][C] = 1;
        else LMatrix[R][C] = 0;
    isUpdate[LIndex - 1] = true;

    // Perform REF on matrix U
    System.out.println("Matrix to set as U: ");
    UIndex = selectMatrixOption(in);
    mat[UIndex - 1] = UMatrix = copyMatrix(mat[matIndex - 1]);
    isUpdate[UIndex - 1] = true;
    ref(UMatrix);

    // Perform REF operations on Identity (as LMatrix)
    for (int i = 0; i < ops.size(); i++)
    {
      System.out.println(ops.get(i));
      if (ops.get(i).op == 1)
      {
        // ("Add (" + ops.get(i).val+ ") * Row" + (ops.get(i).row1+1) + " to Row" + (ops.get(i).row2+1));
        //  ops.add(new RowOp(1, coef, p, p+r));
        addRows(LMatrix[ops.get(i).row2], LMatrix[ops.get(i).row1], ops.get(i).val);
      }
      if (ops.get(i).op == 2)
      {
        // ("Divide Row" + (ops.get(i).row1+1) + " by (" + ops.get(i).val + ")");
        // ops.add(new RowOp(2, refMatrix[R][lead], R, 0));
        divideRow(LMatrix[ops.get(i).row1], ops.get(i).val);
      }
      if (ops.get(i).op == 3)
      {
        // ("Swap Row" + (ops.get(i).row1+1) + " and Row" + (ops.get(i).row2+1));
        swapRows(LMatrix[ops.get(i).row1],  LMatrix[ops.get(i).row2]);
      }
    }

    // Invert (L^-1)
    double[][] temp = copyMatrix(LMatrix);
    N = numRows;
    inverse(LMatrix, temp);
    mat[LIndex-1] = temp;

    clearScreen();
    System.out.println("Initial Matrix A:");
    printMatrix(mat[matIndex-1]);
    System.out.println("");

    System.out.println("Matrix L:");
    printMatrix(mat[LIndex-1]);
    System.out.println("");

    System.out.println("Matrix U:");
    printMatrix(mat[UIndex-1]);
    System.out.println("");
  }

// =============================================================================

  // Function to get cofactor of A[p][q] in temp[][]. n is current
  // dimension of A[][]
  static void getCofactor(double A[][], double temp[][], int p, int q, int n)
  {
      int i = 0, j = 0;

      // Looping for each element of the matrix
      for (int row = 0; row < n; row++)
      {
          for (int col = 0; col < n; col++)
          {
              // Copying into temporary matrix only those element
              // which are not in given row and column
              if (row != p && col != q)
              {
                  temp[i][j++] = A[row][col];

                  // Row is filled, so increase row index and
                  // reset col index
                  if (j == n - 1)
                  {
                      j = 0;
                      i++;
                  }
              }
          }
      }
  }

  /* Recursive function for finding determinant of matrix.
  n is current dimension of A[][]. */
  static double determinant(double A[][], int n)
  {
      double D = 0; // Initialize result

      // Base case : if matrix contains single element
      if (n == 1)
          return A[0][0];

      double [][]temp = new double[N][N]; // To store cofactors

      int sign = 1; // To store sign multiplier

      // Iterate for each element of first row
      for (int f = 0; f < n; f++)
      {
          // Getting Cofactor of A[0][f]
          getCofactor(A, temp, 0, f, n);
          D += sign * A[0][f] * determinant(temp, n - 1);

          // terms are to be added with alternate sign
          sign = -sign;
      }

      return D;
  }

  // Function to get adjoint of A[N][N] in adj[N][N].
  static void adjoint(double A[][], double [][]adj)
  {
      if (N == 1)
      {
          adj[0][0] = 1;
          return;
      }

      // temp is used to store cofactors of A[][]
      int sign = 1;
      double [][]temp = new double[N][N];

      for (int i = 0; i < N; i++)
      {
          for (int j = 0; j < N; j++)
          {
              // Get cofactor of A[i][j]
              getCofactor(A, temp, i, j, N);

              // sign of adj[j][i] positive if sum of row
              // and column indexes is even.
              sign = ((i + j) % 2 == 0)? 1: -1;

              // Interchanging rows and columns to get the
              // transpose of the cofactor matrix
              adj[j][i] = (sign)*(determinant(temp, N-1));
          }
      }
  }

  // Function to calculate and store inverse, returns false if
  // matrix is singular
  static boolean inverse(double[][] A, double[][] inverse)
  {
      // Find determinant of A[][]
      double det = determinant(A, N);
      if (det == 0)
      {
          System.out.print("Singular matrix, can't find its inverse");
          return false;
      }

      // Find adjoint
      double [][]adj = new double[N][N];
      adjoint(A, adj);

      // Find Inverse using formula "inverse(A) = adj(A)/det(A)"
      for (int i = 0; i < N; i++)
          for (int j = 0; j < N; j++)
              inverse[i][j] = adj[i][j]/(double)det;

      return true;
  }

// =============================================================================

  public static double[][] copyMatrix(double[][] initial)
  {
    int numRows = initial.length;
    int numCols = initial[0].length;

    double[][] newMat = new double[numRows][numCols];
    for (int R = 0; R < numRows; R++)
      for (int C = 0; C < numCols; C++)
        newMat[R][C] = initial[R][C];

    return newMat;
  }

  // RREF Helper Methods
  public static void divideRow(double[] row, double d)
  {
    for (int i = 0; i < row.length; i++)
      if (row[i] != 0)
        row[i] /= d;
  }
  public static void addRows(double[] row1, double[] row2, double m)
  {
    for (int i = 0; i < row1.length; i++)
      row1[i] += (row2[i] * m);
  }
  public static void swapRows(double[] row1, double[] row2)
  {
    double[] temp = new double[row1.length];
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
    System.out.println("\t2) Print matrix");
    System.out.println("\t3) Perform Row Reduced Echelon Form");
    System.out.println("\t4) Perform Row Echelon Form");
    System.out.println("\t5) Perform A = LU Factorization");
    System.out.println("\t6) Print last opperations list");
    System.out.println("\t0) Quit Program");
  }


  // Clear Terminal method
  public static void clearScreen()
  {
    System.out.println("--------------------------------------------------");
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }


  // Print Matrix Methods
  public static void printMatrixAvailable()
  {
    System.out.print("| ");
    for (int i = 0; i < numMats; i++)
    {
      System.out.print((i + 1) + ":");
      if (isUpdate[i]) System.out.print("X | ");
      else System.out.print("O | ");
    }
    System.out.println("");
  }
  public static int selectMatrixOption(Scanner in)
  {
    printMatrixAvailable();
    System.out.println("");

    int matIndex = -1;
    boolean doLoop = true;
    while (doLoop)
    {
      System.out.print("Select Matrix: ");
      try
      {
        matIndex = in.nextInt();
        doLoop = false;
      }
      catch (Exception e)
      {
        clearScreen();
        String temp = in. nextLine();
        System.out.println("Invalid input format: " + temp + " is not of type int.");
      }
    }

    return matIndex;
  }

  public static void printMatrix(double[][] matrix)
  {
    for (int i = 0; i < matrix.length; i++)
    {
      for (int j = 0; j < matrix[0].length; j++)
      {
        System.out.printf("%.02f\t", matrix[i][j]);
      }
      System.out.println("");
    }
  }

  public static void printMatrix(int matIndex)
  {
    if (!isUpdate[matIndex] || mat[matIndex] == null)
    {
      clearScreen();
      System.out.println("Cannot print this matrix");
      return;
    }

    for (int i = 0; i < mat[matIndex].length; i++)
    {
      for (int j = 0; j < mat[matIndex][0].length; j++)
      {
        System.out.printf("%.02f\t", mat[matIndex][i][j]);
      }
      System.out.println("");
    }
  }
}
