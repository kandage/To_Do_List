package controller;

import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFormController {
    public TextField txtUserName;
    public PasswordField txtPassword;
    public AnchorPane root;
    public static String userID;
    public static String name;


    public void btnLoginOnAction(ActionEvent actionEvent) {
        login();
    }

    public void lblNewAccountOnMouseClicked(MouseEvent mouseEvent) throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("../view/CreateNewAccountForm.fxml"));
        Scene scene = new Scene(parent);
        Stage primaryStage = (Stage) this.root.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Create New Account");
        primaryStage.centerOnScreen();
    }

    public void txtPasswordOnAction(ActionEvent actionEvent) {
        login();
    }


    public void login(){
        String userName = txtUserName.getText();
        String password = txtPassword.getText();

        Connection connection = DBConnection.getInstance().getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from user where name = ? and password = ?");
            preparedStatement.setObject(1,userName);
            preparedStatement.setObject(2,password);
            ResultSet resultSet = preparedStatement.executeQuery();


            if (resultSet.next()){

                userID = resultSet.getString(1);
                name = resultSet.getString(2);

                Parent parent = FXMLLoader.load(this.getClass().getResource("../view/ToDoForm.fxml"));
                Scene scene = new Scene(parent);
                Stage primaryStage = (Stage) this.root.getScene().getWindow();
                primaryStage.setScene(scene);
                primaryStage.setTitle("To-Do Form");
                primaryStage.centerOnScreen();
            }else{
                new Alert(Alert.AlertType.ERROR,"invalid User Name or Password..").showAndWait();
                txtUserName.clear();
                txtPassword.clear();
                txtUserName.requestFocus();
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
