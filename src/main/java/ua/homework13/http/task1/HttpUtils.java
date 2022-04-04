package ua.homework13.http.task1;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;

public class HttpUtils {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final Gson GSON_PRETTY_PRINT_CREATED = new GsonBuilder().setPrettyPrinting().create();
    private static final String USERS_URL = "https://jsonplaceholder.typicode.com/users/";



    public String createNewUser(String newUserJsonFilePath){
        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder()
                    .uri(URI.create(USERS_URL))
                    .POST(HttpRequest.BodyPublishers.ofFile(Paths.get(newUserJsonFilePath)))
                    .header("Content-Type", "application/json")
                    .build();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        HttpResponse<String> response = null;
        try {
            response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        response.body();
        return response.body();
    }

    public void createJsonUser(String newUser, String jsonFilePath) {

        User newJsonUser = GSON_PRETTY_PRINT_CREATED.fromJson(newUser, User.class);
        String outputString = GSON_PRETTY_PRINT_CREATED.toJson(newJsonUser);
        try (FileWriter output = new FileWriter(jsonFilePath)) {
            output.write(outputString);
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public String updateUserById(int userId, String jsonUserFilePath){
        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder()
                    .uri(URI.create(USERS_URL + userId))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofFile(Paths.get(jsonUserFilePath)))
                    .build();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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

    public int deleteUserById(int userID){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(USERS_URL+userID))
                .DELETE()
                .build();
        HttpResponse<String> response = null;
        try {
            response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response.statusCode();
    }

    public String getQuery() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(USERS_URL))
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
        System.out.println("Status code: " + response.statusCode());
        return response.body();
    }
    public void getAllUser(String allUserJsonFilePath) {
        String users = getQuery();
        User[] newJsonUser = GSON_PRETTY_PRINT_CREATED.fromJson(users, User[].class);
        String outputString = GSON_PRETTY_PRINT_CREATED.toJson(newJsonUser);
        try (FileWriter output = new FileWriter(allUserJsonFilePath)) {
            output.write(outputString);
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public String getUserById(int userId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(USERS_URL + userId))
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
        System.out.println("Status code: " + response.statusCode());
        return response.body();
    }

    class User {
        private int id;
        private String name;
        private String username;
        private String email;
        private Object address;
        private String geo;
        private String phone;
        private String website;
        private Object company;

        public User(int id, String name, String username, String email, Object address, String geo, String phone, String website, Object company) {
            this.id = id;
            this.name = name;
            this.username = username;
            this.email = email;
            this.address = address;
            this.geo = geo;
            this.phone = phone;
            this.website = website;
            this.company = company;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", username='" + username + '\'' +
                    ", email='" + email + '\'' +
                    ", address='" + address + '\'' +
                    ", geo='" + geo + '\'' +
                    ", phone='" + phone + '\'' +
                    ", website='" + website + '\'' +
                    ", company='" + company + '\'' +
                    '}';
        }
    }

}
