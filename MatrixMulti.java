//Laurence Timothy M. Garcia
//CS 3310.01
//Professor Huong Luu
//Project 1 - Matrix Multiplication

import java.io.*;
import java.util.Random;

public class MatrixMulti
{
    public static void main(String[] args)
    {
        int[][] matrix1 = new int[4][4];
        int[][] matrix2 = new int[4][4];

        int[][] matrixProduct = new int[4][4];

        matrix1 = buildMatrix(matrix1);
        matrix2 = buildMatrix(matrix2);

        System.out.println("Matrix 1");
        printMatrix(matrix1);

        System.out.println("Matrix 2");
        printMatrix(matrix2);
    }

    /*** Builds Random Matrix ***/
    public static int[][] buildMatrix(int[][] matrix)
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
    public int[][] strassenSolve(int[][] matA, int[][] matB)
    {
        
    }

    public int[][] strassenDiv(int[][] matA, int[][] matB)
    {
        if (matA.length <= 2)
        {
            return 
        }
    }
}