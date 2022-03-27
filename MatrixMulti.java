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

        int[][] matrixProduct;

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

        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 4; j++)
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


}