package ua.homework13.http.task3;

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

public class ToDoChecker {

    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final String USERS_URL = "https://jsonplaceholder.typicode.com/users/";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();


    public void createJsonFileWithOpenToDoTaskByUserId(int userId){
        String allTodoTask = getAllToDoTasksByUserId(userId);
        List<ToDoTask> allTodoTaskList = getOpenToDoTasks(allTodoTask);
        String jsonFilePath = "src/main/resources/output/task3_data_out/user-" + userId + "-Open-To-Do-Tasks.json";

        createJsonFileWithToDoTask(allTodoTaskList, jsonFilePath);
    }


    private String getAllToDoTasksByUserId(int userId) {
        String uri = USERS_URL + userId + "/todos";
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

    private List<ToDoTask> getOpenToDoTasks(String str){
        ToDoTask[] toDoTasksArr = GSON.fromJson(str, ToDoTask[].class);
        List<ToDoTask> toDoTasksList = new ArrayList<>(Arrays.asList(toDoTasksArr));
        List<ToDoTask> openToDoTaskList = new ArrayList<>();

        for ( ToDoTask openTodoTask: toDoTasksList) {
            if (!openTodoTask.completed)
            openToDoTaskList.add(openTodoTask);
        }
        return openToDoTaskList;
    }

    private void createJsonFileWithToDoTask(List<ToDoTask> toDoTaskList, String jsonFilePath) {
        String outputData = GSON.toJson(toDoTaskList.toArray());
        try (FileWriter output  = new FileWriter(jsonFilePath)){
            output.write(outputData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    class ToDoTask {
        int userId;
        int id;
        String title;
        boolean completed;

        public ToDoTask(int userId, int id, String title, boolean completed) {
            this.userId = userId;
            this.id = id;
            this.title = title;
            this.completed = completed;
        }

        @Override
        public String toString() {
            return "ToDoTask{" +
                    "userId=" + userId +
                    ", id=" + id +
                    ", title='" + title + '\'' +
                    ", completed=" + completed +
                    '}';
        }
    }

}
