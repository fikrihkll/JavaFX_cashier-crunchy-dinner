package ui.employee;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import data.db.AppDatabase;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AddEmployeeController implements Initializable {

    @FXML
    private TextField txtEmployeeID;

    @FXML
    private TextField txtEmployeeName;

    @FXML
    private TextField txtEmployeeAddress;

    @FXML
    private TextField txtEmployeeBirth;

    @FXML
    private TextField txtEmployeePhone;

    @FXML
    private Button btnEmployeeSave;

    @FXML
    private Button btnEmployeeBack;

    @FXML
    private Button btnEmployeeShow;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnEmployeeSave.setOnMouseClicked(event -> {

            Connection connection= AppDatabase.getInstance();

            String sql="INSERT INTO employee VALUES(uuid(),'"+txtEmployeeName.getText()+"','"+txtEmployeeAddress.getText()+"','"+txtEmployeeBirth.getText()+"', '"+txtEmployeePhone.getText()+"')";
            Statement statement= null;
            try {
                statement = connection.createStatement();
                statement.executeUpdate(sql);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        });
}
}