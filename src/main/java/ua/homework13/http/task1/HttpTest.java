package ua.homework13.http.task1;

import java.io.IOException;
import java.util.Scanner;

public class HttpTest {
    public static void main(String[] args) throws IOException, InterruptedException {


        String newUserDataFile = "src/main/resources/input/task1_data/new_user.txt"; //new user information
        String newUserCreatedFile = "src/main/resources/output/task1_data_out/new_user_created.json"; //new user has been added, writes JSON file
        String userDataToUpdateFile = "src/main/resources/input/task1_data/new_data_to_update.txt"; // information which has to be updated
        String userDataUpdatedFile = "src/main/resources/output/task1_data_out/new_data_updated.json"; // updated information, writes JSON file
        String allUsersDataFile = "src/main/resources/output/task1_data_out/all_users_data.json"; //writes JSON file with all Users

        Scanner scanner = new Scanner(System.in);
        HttpUtils httpUtils = new HttpUtils();

        // #1: to create a new User
        String newUserPosted = httpUtils.createNewUser(newUserDataFile);
        System.out.println("New User has been added to server.\nFile \"new_user_created.json\" created\n");
        httpUtils.createJsonUser(newUserPosted, newUserCreatedFile);

        // #2: to update existing User's Data
        int userId = 10;
        String newData = httpUtils.updateUserById(userId, userDataToUpdateFile);
        System.out.println("User's Data with ID: #" + userId + " has been updated. \nFile \"new_data_update.json\" created\n");
        httpUtils.createJsonUser(newData, userDataUpdatedFile);

        // #3: to delete existing User by Id
        int idUserToDelete = 1;
        System.out.println("Status code: " + httpUtils.deleteUserById(idUserToDelete));
        System.out.println("User with ID: #" + idUserToDelete + " has been deleted.\n");

        // #4: to get all Users
        httpUtils.getAllUser(allUsersDataFile);
        System.out.println("File \"all_users_data.json\" created\n");

        // #5: to get User by ID
        System.out.println("Input User's ID to receive information:\r");
        int id = scanner.nextInt();
        System.out.println(httpUtils.getUserById(id));

    }
}
