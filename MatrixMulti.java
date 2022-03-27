import java.lang.reflect.Array;

//Laurence Timothy M. Garcia
//CS 3310.01
//Professor Huong Luu
//Project 1 - Matrix Multiplication

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
    }

    public int[][] buildMatrix(int[][] matrix)
    {
        Random rand = new Random();

        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                matrix[i][j] = rand.nextInt(10);
            }
        }

        System.out.print(matrix);

        return matrix;
    }
}