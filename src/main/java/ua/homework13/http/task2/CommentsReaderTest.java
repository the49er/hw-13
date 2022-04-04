package ua.homework13.http.task2;


import java.util.Scanner;

public class CommentsReaderTest {
    public static void main(String[] args) {

        String allPostsByUserId = "src/main/resources/output/all_posts_by_userId.json"; //writes JSON file with all User's posts

        CommentsReader commentsReader = new CommentsReader();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input user's ID: ");


        int userId = scanner.nextInt();
        String posts = commentsReader.getPostsByUserId(userId);
        commentsReader.createPostJsonFile(posts, allPostsByUserId);
        commentsReader.createCommentsJsonFile(userId);

    }
}
