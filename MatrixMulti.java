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

        int[][] matrixProduct = new int[4][4];

        matrix1 = buildRandomMatrix(matrix1);
        matrix2 = buildRandomMatrix(matrix2);

        System.out.println("Matrix 1");
        printMatrix(matrix1);
        System.out.println();

        System.out.println("Matrix 2");
        printMatrix(matrix2);
        System.out.println();

        matrixProduct = strassen(matrix1, matrix2);

        System.out.println("Product of Matrix 1 and Matrix 2");
        printMatrix(matrixProduct);
        System.out.println();
    }

    /*** Input + Validation ***/
    public static int keyboardInput(Scanner kb)
    {
        int input = 0;

        System.out.println("Please place size of matrix that is multiple of 4, ie: 4, 8, 16: ");
        input = kb.nextInt();

        while (input%4 != 0)
        {
            System.out.println("Input is not a multiple of 4, please try again: ");
            input = kb.nextInt();
        }

        return input;
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

    /*** Prints out Matrix ***/
    public static void printMatrix(int[][] matrix)
    {
        System.out.println("Test Matrix");

        for (int i = 0; i < matrix.length; i++)
        {
            for (int j = 0; j < matrix[i].length; j++)
            {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }

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
        int[][][] matASplit = strassenSplit(matA);
        int[][][] matBSplit = strassenSplit(matB);

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
        int[][] matC11 = strassenAdd(matA1B1, matA2B3);
        int[][] matC12 = strassenAdd(matA1B2, matA2B4);
        int[][] matC21 = strassenAdd(matA3B1, matA4B3);
        int[][] matC22 = strassenAdd(matA3B2, matA4B4);

        //Pieces the Matrix back together
        matC = strassenJoin(matC11, matC12, matC21, matC22);

        return matC;
    }

    //Splits the matrix up
    public static int[][][] strassenSplit(int[][] mat)
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
    public static int[][] strassenJoin(int[][] matA, int[][] matB, int[][] matC, int[][] matD)
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

    //Adding the product of 2 matricies together
    public static int[][] strassenAdd(int[][] matA, int[][] matB)
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