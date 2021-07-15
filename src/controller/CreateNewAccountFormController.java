package controller;

import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class CreateNewAccountFormController {
    public TextField txtUserName;
    public TextField txtEmail;
    public PasswordField txtNewPassword;
    public PasswordField txtConfirmPassword;
    public Label lblNotMatch;
    public Button btnAddNewUser;
    public Label lblID;
    public Button btnRegister;
    public AnchorPane root;


    public void initialize(){
        lblNotMatch.setVisible(false);

        txtUserName.setDisable(true);
        txtEmail.setDisable(true);
        txtNewPassword.setDisable(true);
        txtConfirmPassword.setDisable(true);
        btnRegister.setDisable(true);
    }

    public void btnRegisterOnAction(ActionEvent actionEvent) {
        registerUser();
    }

    public void btnAddNewUserOnAction(ActionEvent actionEvent) {
        autoGenerateID();

        txtUserName.setDisable(false);
        txtEmail.setDisable(false);
        txtNewPassword.setDisable(false);
        txtConfirmPassword.setDisable(false);
        btnRegister.setDisable(false);

        txtUserName.requestFocus();
    }

    public void autoGenerateID(){

        Connection connection = DBConnection.getInstance().getConnection();

        try {

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select id from user order by id desc limit 1");

            boolean isExist = resultSet.next();

            if(isExist){

                String oldId = resultSet.getString(1);


                String id = oldId.substring(1,4);

                int intId = Integer.parseInt(id);

                intId = intId + 1;

                if(intId < 10){
                    lblID.setText("U00"+intId);
                }else if(intId < 100){
                    lblID.setText("U0"+intId);
                }else{
                    lblID.setText("U"+intId);
                }
            }else{
                lblID.setText("U001");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void txtConfirmPasswordOnAction(ActionEvent actionEvent) {
        registerUser();
    }

    public void registerUser(){
        String newPassword = txtNewPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();

        if(newPassword.equals(confirmPassword)){
            txtNewPassword.setStyle("-fx-border-color: transparent");
            txtConfirmPassword.setStyle("-fx-border-color: transparent");
            lblNotMatch.setVisible(false);

            String id = lblID.getText();
            String userName = txtUserName.getText();
            String email = txtEmail.getText();

            Connection connection = DBConnection.getInstance().getConnection();

            try {

                PreparedStatement preparedStatement = connection.prepareStatement("insert into user values(?,?,?,?)");

                preparedStatement.setObject(1,id);
                preparedStatement.setObject(2,userName);
                preparedStatement.setObject(3,confirmPassword);
                preparedStatement.setObject(4,email);

                int i = preparedStatement.executeUpdate();

                if(i != 0){
                    new Alert(Alert.AlertType.CONFIRMATION,"Successfully Added....").showAndWait();

                    Parent parent = FXMLLoader.load(this.getClass().getResource("../view/LoginForm.fxml"));
                    Scene scene = new Scene(parent);
                    Stage primaryStage = (Stage) this.root.getScene().getWindow();
                    primaryStage.setScene(scene);
                    primaryStage.centerOnScreen();
                    primaryStage.setTitle("Login Form");
                }else{
                    new Alert(Alert.AlertType.ERROR,"Something went wrong...").showAndWait();
                }

            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }else{
            txtNewPassword.setStyle("-fx-border-color: red");
            txtConfirmPassword.setStyle("-fx-border-color: red");
            txtNewPassword.requestFocus();
            lblNotMatch.setVisible(true);
        }
    }
}
