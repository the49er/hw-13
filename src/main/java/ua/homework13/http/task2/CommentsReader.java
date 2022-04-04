package ua.homework13.http.task2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommentsReader {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final String USERS_URL = "https://jsonplaceholder.typicode.com/users/";
    private static final String USER_POSTS = "https://jsonplaceholder.typicode.com/posts/";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public String getPostsByUserId(int userId) {
        String uri = USERS_URL + userId + "/posts";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .GET()
                .build();
        HttpResponse<String> response = null;
        try {
            response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response.body();
    }

    public String getCommentsByPostId(int postId) {
        String uri = USER_POSTS + postId + "/comments";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .GET()
                .build();
        HttpResponse<String> response = null;
        try {
            response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response.body();
    }

    private UserPost[] createAllPostsFromString(String str) {
        return GSON.fromJson(str, UserPost[].class);
    }

    private CommentsToPost[] createAllCommentsFromString(String str) {
        return GSON.fromJson(str, CommentsToPost[].class);
    }


    public void createPostJsonFile(String str, String jsonFilePath) {
        String outputData = GSON.toJson(createAllPostsFromString(str));
        try (FileWriter fileWriter = new FileWriter(jsonFilePath)) {
            fileWriter.write(outputData);
        } catch (IOException ex) {
            ex.getStackTrace();
        }
    }

    public void createCommentsJsonFile(int userId) {
        String allPosts = getPostsByUserId(userId);
        List<String> postIds = getAllPostIds(allPosts);
        int lastPost = getLastPostId(postIds);
        String allComments = getCommentsByPostId(lastPost);
        String jsonFilePath = "src/main/resources/output/user-" + userId + "-post-" + lastPost + "-comments.json";
        String outputData = GSON.toJson(createAllCommentsFromString(allComments));
        try (FileWriter fileWriter = new FileWriter(jsonFilePath)) {
            fileWriter.write(outputData);
        } catch (IOException ex) {
            ex.getStackTrace();
        }
        System.out.println("\n" + jsonFilePath + "\nhas been created");
    }

    private List<String> getAllPostIds(String str) {
        List<String> result = new ArrayList<>();

        Pattern pattern = Pattern.compile("\"id\": \\d+");
        Matcher matcher = pattern.matcher(str);

        while (matcher.find()) {
            result.add(matcher.group());
        }
        //System.out.println(result);
        return result;
    }

    private int getLastPostId(List<String> list) {
        String[] temArr = list.get(list.size() - 1).split(" ");
        //System.out.println(Arrays.toString(temArr));
        int lastPost = Integer.parseInt(temArr[1]);

        return lastPost;
    }

    class CommentsToPost {
        String postId;
        int id;
        String name;
        String email;
        String body;

        public CommentsToPost(String postId, int id, String name, String email, String body) {
            this.postId = postId;
            this.id = id;
            this.name = name;
            this.email = email;
            this.body = body;
        }

        @Override
        public String toString() {
            return "CommentsPosts{" +
                    "postId='" + postId + '\'' +
                    ", id=" + id +
                    ", name='" + name + '\'' +
                    ", email='" + email + '\'' +
                    ", body='" + body + '\'' +
                    '}';
        }
    }

    class UserPost {
        String userId;
        int id;
        String title;
        String body;

        public UserPost(String userId, int id, String title, String body) {
            this.userId = userId;
            this.id = id;
            this.title = title;
            this.body = body;
        }

        @Override
        public String toString() {
            return "Comments{" +
                    "userId='" + userId + '\'' +
                    ", id=" + id +
                    ", title='" + title + '\'' +
                    ", body='" + body + '\'' +
                    '}';
        }
    }

}
