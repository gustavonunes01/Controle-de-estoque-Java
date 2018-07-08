package controls;

import DAO.UsuarioDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import models.Usuario;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Login implements Initializable {

    @FXML
    private TextField txtLogin;

    @FXML
    private PasswordField txtPassword;

    private static Usuario user;
    private UsuarioDAO udao = new UsuarioDAO();

    public static Usuario getUser() {
        return user;
    }

    @Override
        public void initialize(URL location, ResourceBundle resources) {
            user = new Usuario();
            txtLogin.setText("admin");
            txtPassword.setText("admin");
        }

        public void show(Stage primaryStage) throws IOException {
            Main.stage = primaryStage;
            FXMLLoader root = new FXMLLoader(getClass().getResource("/views/Login.fxml"));
            root.setControllerFactory(c -> {
                return new Login();
            });
            Main.stage.setTitle("ControlX - Entrar");
            Main.stage.setScene(new Scene(root.load(), primaryStage.getWidth(), primaryStage.getHeight()));
            Main.stage.setResizable(false);
            Main.stage.getIcons().add(new Image("images/controlx.png"));
            Main.stage.show();
        }

    public void show() throws IOException {
        Stage primaryStage = new Stage();
        FXMLLoader root = new FXMLLoader(getClass().getResource("/views/Login.fxml"));
        root.setControllerFactory(c -> {
            return new Login();
        });
        primaryStage.setTitle("ControlX - Login");
        Main.stage.hide();
        Main.stage = primaryStage;
        primaryStage.setScene(new Scene(root.load(), primaryStage.getWidth(), primaryStage.getHeight()));
        primaryStage.setResizable(false);
        Main.stage.getIcons().add(new Image("images/controlx.png"));
        primaryStage.show();
    }

        @FXML
        public void logar() throws IOException, ClassNotFoundException {
            if (udao.checkLogin(txtLogin.getText(), txtPassword.getText())){
                user = udao.read(txtLogin.getText());
                new MenuPrincipal().show();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ControlX - Aviso");
                alert.setHeaderText("Login");
                alert.setContentText("Login ou senha inválidos!");

                alert.showAndWait();
            }

        }


}
