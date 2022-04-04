package ua.homework13.http.task3;

import ua.homework13.http.task2.CommentsReader;

import java.util.Scanner;

public class ToDoCheckerTest {
    public static void main(String[] args) {

        ToDoChecker toDoChecker = new ToDoChecker();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input user's ID: ");

        int userId = scanner.nextInt();

        toDoChecker.createJsonFileWithOpenToDoTaskByUserId(userId);
    }
}
