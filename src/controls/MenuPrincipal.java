package controls;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class MenuPrincipal implements Initializable
{
    @FXML
    private Label lbUser;
    @FXML
    private Label lbCargo;
    @FXML
    private Label lbDate;
    @FXML
    private Button btProdutos;
    @FXML
    private Button btFornecedores;
    @FXML
    private Button btCompras;
    @FXML
    private Button btUsuarios;
    @FXML
    private Button btVender;
    @FXML
    private Button btHistoricos;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        verifCargo();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date data = new Date(System.currentTimeMillis());
        lbDate.setText(String.valueOf(dateFormat.format(data)));
        usuario();
    }

    public void verifCargo(){
        if(Login.getUser().getCargo() == 2){ //ALMOXARIFE
            btUsuarios.setDisable(true);
            btVender.setDisable(true);

        } else if(Login.getUser().getCargo() == 3){ //CAIXA
            btUsuarios.setDisable(true);
            btCompras.setDisable(true);
            btFornecedores.setDisable(true);
        }
    }

    public void usuario(){
        lbUser.setText(Login.getUser().getNome());
        if(Login.getUser().getCargo() == 0) {
            lbCargo.setText("Administrador");
        } else if(Login.getUser().getCargo() == 1) {
            lbCargo.setText("Supervisor");
        } else if(Login.getUser().getCargo() == 2) {
            lbCargo.setText("Almoxarife");
        } else if(Login.getUser().getCargo() == 3) {
            lbCargo.setText("Caixa");
        }
    }

    public void show() throws IOException {
        Stage primaryStage = new Stage();
        FXMLLoader root = new FXMLLoader(getClass().getResource("/views/MenuPrincipal.fxml"));
        root.setControllerFactory(c -> {
            return new MenuPrincipal();
        });
        primaryStage.setTitle("ControlX - Menu");
        Main.stage.hide();
        Main.stage = primaryStage;
        primaryStage.setScene(new Scene(root.load(), primaryStage.getWidth(), primaryStage.getHeight()));
        primaryStage.setResizable(false);
        Main.stage.getIcons().add(new Image("images/controlx.png"));
        primaryStage.show();
    }

    public void btProdutos() throws IOException {
        new Estoque().show();
    }

    public void btCompras() throws IOException {
        new Compras().show();
    }
    public void btVendas() throws IOException{
        new Vendas().show();
    }
    public void botaoVoltar() throws IOException{
        new Login().show();
    }
    public void botaoFornecedores() throws IOException {
        new Fornecedores().show();
    }
    public void botaoFuncionarios() throws IOException {
        new Usuarios().show();
    }
    public void botaoHistorico() throws IOException{
        new Historico().show();
    }
}
