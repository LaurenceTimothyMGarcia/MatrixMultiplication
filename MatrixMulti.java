//Laurence Timothy M. Garcia
//CS 3310.01
//Professor Huong Luu
//Project 1 - Matrix Multiplication

import java.io.*;
import java.util.Scanner;
import java.util.Random;

public class MatrixMulti
{
    public static void main(String[] args)
    {
        Scanner kb = new Scanner(System.in);

        int matrixSize = keyboardInput(kb);

        int[][] matrix1 = new int[matrixSize][matrixSize];
        int[][] matrix2 = new int[matrixSize][matrixSize];

        int[][] matrixProductBF = new int[matrixSize][matrixSize];
        int[][] matrixProductDQ = new int[matrixSize][matrixSize];
        int[][] matrixProductStrassen = new int[matrixSize][matrixSize];

        //Asks user to build matrix or create a random one
        System.out.print("Would you like to input a matrix or build a random one (1 for build, 2 for random): ");
        int choice = kb.nextInt();

        while (!(choice == 1 || choice == 2))
        {
            System.out.print("Please choose 1 for build or 2 for random: ");
            choice = kb.nextInt();
        }

        if (choice == 1)
        {
            matrix1 = buildMatrix(matrix1, kb);
            matrix2 = buildMatrix(matrix2, kb);
        }
        else if (choice == 2)
        {
            matrix1 = buildRandomMatrix(matrix1);
            matrix2 = buildRandomMatrix(matrix2);
        }

        System.out.println("Matrices being multiplied");

        //prints out the first matrix
        System.out.println("Matrix 1");
        printMatrix(matrix1);
        System.out.println();

        //prints out the second matrix
        System.out.println("Matrix 2");
        printMatrix(matrix2);
        System.out.println();


        /** Brute Force **/
        //Multiplies Matrices by brute force
        matrixProductBF = bruteForce(matrix1, matrix2, matrixProductBF);

        //prints out brute force product
        System.out.println("Brute Force Algorithm");
        System.out.println("Product of Matrix 1 and Matrix 2");
        printMatrix(matrixProductBF);
        System.out.println();


        /** Naive Divide and Conquer Algorithm **/
        matrixProductDQ = divNCon(matrix1, matrix2);

        //prints out Naive Divide and Conquer Algorithm
        System.out.println("Naive Divide and Conquer");
        System.out.println("Product of Matrix 1 and Matrix 2");
        printMatrix(matrixProductDQ);
        System.out.println();


        /** Strassen's Algorithm **/
        //Expanding Matrix to be used by Strassen
        matrix1 = rebuildMatrix(matrix1);
        matrix2 = rebuildMatrix(matrix2);

        //calculation for multiplying first and second matrix
        matrixProductStrassen = strassen(matrix1, matrix2);

        //Cleans up product matrix into its original size
        if (matrixSize != matrix1.length)
        {
            matrixProductStrassen = reduceMatrix(matrixProductStrassen, matrixSize);
        }

        //prints product of matrix
        System.out.println("Strassen's Algorithm");
        System.out.println("Product of Matrix 1 and Matrix 2");
        printMatrix(matrixProductStrassen);
        System.out.println();
    }

    /*** Input + Validation ***/
    public static int keyboardInput(Scanner kb)
    {
        int input = 0;

        System.out.println("Please place size of matrix: ");
        input = kb.nextInt();

        return input;
    }

    /*** Allows user to build their own matrix ***/
    public static int[][] buildMatrix(int[][] matrix, Scanner kb)
    {
        System.out.println();
        System.out.println("Input matrix");
        for (int i = 0; i < matrix.length; i++)
        {
            for (int j = 0; j < matrix.length; j++)
            {
                System.out.printf("Matrix [%d][%d]: ", i, j);
                matrix[i][j] = kb.nextInt();
            }
        }

        return matrix;
    }

    /*** Builds Random Matrix ***/
    public static int[][] buildRandomMatrix(int[][] matrix)
    {
        Random rand = new Random();

        for (int i = 0; i < matrix.length; i++)
        {
            for (int j = 0; j < matrix.length; j++)
            {
                matrix[i][j] = rand.nextInt(10);
            }
        }

        return matrix;
    }

    /*** Rebuild Matrix for non power of 2 ***/
    public static int[][] rebuildMatrix(int[][] matrix)
    {
        int ogSize = matrix.length;
        int[][] newMatrix;

        if ((Math.log(ogSize) % Math.log(2)) != 0)
        {
            double power = Math.ceil(Math.log(ogSize) / Math.log(2));
            int newSize = (int)Math.pow(2, power);

            newMatrix = new int[newSize][newSize];

            for (int i = 0; i < newSize; i++)
            {
                for (int j = 0; j < newSize; j++)
                {
                    if (j >= ogSize || i >= ogSize)
                    {
                        newMatrix[i][j] = 0;
                    }
                    else
                    {
                        newMatrix[i][j] = matrix[i][j];
                    }
                }
            }
        }
        else
        {
            return matrix;
        }

        return newMatrix;
    }

    /*** Reduces size of the rebuilt matrix down to the original size ***/
    public static int[][] reduceMatrix(int[][] matrix, int ogSize)
    {
        int[][] newMatrix = new int[ogSize][ogSize];

        for (int i = 0; i < ogSize; i++)
        {
            for (int j = 0; j < ogSize; j++)
            {
                newMatrix[i][j] = matrix[i][j];
            }
        }

        return newMatrix;
    }

    /*** Prints out Matrix ***/
    public static void printMatrix(int[][] matrix)
    {
        for (int i = 0; i < matrix.length; i++)
        {
            for (int j = 0; j < matrix[i].length; j++)
            {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }

    }

    /*** Brute Force ***/
    public static int[][] bruteForce(int[][] mat1, int[][] mat2, int[][] mat3)
    {
        int matSize = mat1.length;

        for (int i = 0; i < matSize; i++)
        {
            for (int j = 0; j < matSize; j++)
            {
                mat3[i][j] = 0;
                for (int k = 0; k < matSize; k++)
                {
                    mat3[i][j] += mat1[i][k] * mat2[k][j];
                }
            }
        }

        return mat3;
    }


    /*** Naive Divide and Conquer ***/
    public static int[][] divNCon(int[][] matA, int[][] matB)
    {
        int matSize = matA.length;

        int [][] matC = new int[matSize][matSize];

        if (matSize <= 1)
        {
            matC[0][0] = matA[0][0] * matB[0][0];
            return matC;
        }

        //Splits the matrix into quadrants and returns as 3d array
        int[][][] matASplit = split(matA);
        int[][][] matBSplit = split(matB);

        //Takes the array and places into matrix variables
        int[][] matA1 = matASplit[0];
        int[][] matA2 = matASplit[1];
        int[][] matA3 = matASplit[2];
        int[][] matA4 = matASplit[3];

        int[][] matB1 = matBSplit[0];
        int[][] matB2 = matBSplit[1];
        int[][] matB3 = matBSplit[2];
        int[][] matB4 = matBSplit[3];

        //Recursively calling Strassen to continue breaking down the problem to 1x1 matrix
        int[][] matA1B1 = divNCon(matA1, matB1);
        int[][] matA2B3 = divNCon(matA2, matB3);

        int[][] matA1B2 = divNCon(matA1, matB2);
        int[][] matA2B4 = divNCon(matA2, matB4);

        int[][] matA3B1 = divNCon(matA3, matB1);
        int[][] matA4B3 = divNCon(matA4, matB3);

        int[][] matA3B2 = divNCon(matA3, matB2);
        int[][] matA4B4 = divNCon(matA4, matB4);

        //Adding the products together
        int[][] matC11 = add(matA1B1, matA2B3);
        int[][] matC12 = add(matA1B2, matA2B4);
        int[][] matC21 = add(matA3B1, matA4B3);
        int[][] matC22 = add(matA3B2, matA4B4);

        //Pieces the Matrix back together
        matC = join(matC11, matC12, matC21, matC22);

        return matC;
    }


    /*** Strassen's Algorithm ***/
    public static int[][] strassen(int[][] matA, int[][] matB)
    {
        int matSize = matA.length;

        int[][] matC = new int[matSize][matSize];

        if (matSize <= 1)
        {
            matC[0][0] = matA[0][0] * matB[0][0];
            return matC;
        }

        //Splits the matrix into quadrants and returns as 3d array
        int[][][] matASplit = split(matA);
        int[][][] matBSplit = split(matB);

        //Takes the array and places into matrix variables
        int[][] matA1 = matASplit[0];
        int[][] matA2 = matASplit[1];
        int[][] matA3 = matASplit[2];
        int[][] matA4 = matASplit[3];

        int[][] matB1 = matBSplit[0];
        int[][] matB2 = matBSplit[1];
        int[][] matB3 = matBSplit[2];
        int[][] matB4 = matBSplit[3];

        //Recursively calling Strassen to continue breaking down the problem to 1x1 matrix
        int[][] matA1B1 = strassen(matA1, matB1);
        int[][] matA2B3 = strassen(matA2, matB3);

        int[][] matA1B2 = strassen(matA1, matB2);
        int[][] matA2B4 = strassen(matA2, matB4);

        int[][] matA3B1 = strassen(matA3, matB1);
        int[][] matA4B3 = strassen(matA4, matB3);

        int[][] matA3B2 = strassen(matA3, matB2);
        int[][] matA4B4 = strassen(matA4, matB4);

        //Adding the products together
        int[][] matC11 = add(matA1B1, matA2B3);
        int[][] matC12 = add(matA1B2, matA2B4);
        int[][] matC21 = add(matA3B1, matA4B3);
        int[][] matC22 = add(matA3B2, matA4B4);

        //Pieces the Matrix back together
        matC = join(matC11, matC12, matC21, matC22);

        return matC;
    }

    //Splits the matrix up
    public static int[][][] split(int[][] mat)
    {
        int row = mat.length;
        int col = mat.length;

        int rowDiv = row/2;
        int colDiv = col/2;

        int[][] newMatA = new int[rowDiv][colDiv];
        int[][] newMatB = new int[rowDiv][colDiv];
        int[][] newMatC = new int[rowDiv][colDiv];
        int[][] newMatD = new int[rowDiv][colDiv];

        for (int i = 0; i < rowDiv; i++)
        {
            for (int j = 0; j < colDiv; j++)
            {
                newMatA[i][j] = mat[i][j];                  //Top left quadrant
                newMatB[i][j] = mat[i][j + colDiv];         //Top right quadrant
                newMatC[i][j] = mat[i + rowDiv][j];         //Bottom left quadrant
                newMatD[i][j] = mat[i + rowDiv][j + colDiv];//Bottom right quadrant
            }
        }

        int[][][] matArray = {newMatA, newMatB, newMatC, newMatD};

        return matArray;
    }

    //Joins the matrix back together
    public static int[][] join(int[][] matA, int[][] matB, int[][] matC, int[][] matD)
    {
        int matHalf = matA.length;
        int matFullSize = matHalf * 2;

        int[][] matFull = new int[matFullSize][matFullSize];

        for (int row = 0; row < matFullSize; row++)
        {
            if (row < matHalf)
            {
                for (int col = 0; col < matFullSize; col++)
                {
                    if (col < matHalf)
                    {
                        matFull[row][col] = matA[row][col];
                    }
                    else
                    {
                        matFull[row][col] = matB[row][col - matHalf];
                    }
                }
            }
            else
            {
                for (int col = 0; col < matFullSize; col++)
                {
                    if (col < matHalf)
                    {
                        matFull[row][col] = matC[row - matHalf][col];
                    }
                    else
                    {
                        matFull[row][col] = matD[row - matHalf][col - matHalf];
                    }
                }
            }
        }

        return matFull;
    }

    //Adding the product of 2 matrices together
    public static int[][] add(int[][] matA, int[][] matB)
    {
        int matSize = matA.length;
        int[][] matSum = new int[matSize][matSize];

        for (int i = 0; i < matSize; i++)
        {
            for (int j = 0; j < matSize; j++)
            {
                matSum[i][j] = matA[i][j] + matB[i][j];
            }
        }

        return matSum;
    }
}