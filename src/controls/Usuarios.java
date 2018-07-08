package controls;

import DAO.UsuarioDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import models.Usuario;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class Usuarios implements Initializable {

    @FXML
    private TableView<Usuario> tbView;
    @FXML
    private RadioButton rdId;
    @FXML
    private RadioButton rdNome;
    @FXML
    private TextField txPesquisar;
    @FXML
    private Button btRemove;
    @FXML
    private Button btEdit;
    @FXML
    private Button btView;

    private UsuarioDAO udao = new UsuarioDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            listView(udao.listAll());
            verificaSelecao();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void show() throws IOException {
        Stage primaryStage = new Stage();
        FXMLLoader root = new FXMLLoader(getClass().getResource("/views/Usuarios.fxml"));
        root.setControllerFactory(c -> {
            return new Usuarios();
        });
        primaryStage.setTitle("ControlX - Usuarios");
        Main.stage.hide();
        Main.stage = primaryStage;
        primaryStage.setScene(new Scene(root.load(), primaryStage.getWidth(), primaryStage.getHeight()));
        primaryStage.setResizable(true);
        Main.stage.getIcons().add(new Image("images/controlx.png"));
        primaryStage.show();
    }

    public void listView(List<Usuario> users) throws ClassNotFoundException {
        tbView.getItems().clear();
        tbView.getColumns().clear();

        ObservableList<Usuario> lista = FXCollections.observableArrayList();

        for (Usuario u : users) {
            lista.add(new Usuario(u.getId(), u.getNome(), u.getCpf(), u.getSexo(), u.getDataNasc(),
                    u.getTelefone1(), u.getTelefone2(), u.getCep(), u.getNum(), u.getRua(), u.getComp(),
                    u.getBairro(), u.getCidade(), u.getEstado(), u.getCargo(), u.getLogin(), u.getSenha()));
        }

        TableColumn<Usuario, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setMinWidth(50);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Usuario, String> nomeColumn = new TableColumn<>("Nome");
        nomeColumn.setMinWidth(250);
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<Usuario, String> cpfColumn = new TableColumn<>("Cpf");
        cpfColumn.setMinWidth(100);
        cpfColumn.setCellValueFactory(new PropertyValueFactory<>("cpf"));

        TableColumn<Usuario, String> loginColumn = new TableColumn<>("Login");
        loginColumn.setMinWidth(100);
        loginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));

        tbView.setItems(lista);

        tbView.getColumns().addAll(idColumn, nomeColumn, cpfColumn, loginColumn);
        verificaSelecao();
    }

    public void botaoAddUsuario() throws IOException {
        new GerenciarUsuario().show();
    }

    public void botaoRemoveUsuario() throws ClassNotFoundException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("ControlX - Remover usuario");
        alert.setHeaderText("Este usuario será removido permanentemente.");
        alert.setContentText("Deseja continuar?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            udao.del(udao.read(tbView.getSelectionModel().getSelectedItem().getId()));
            listView(udao.listAll());
        } else {
            // ... user chose CANCEL or closed the dialog
        }
    }

    public void botaoEditUsuario() throws IOException, ClassNotFoundException {
        if(Login.getUser().getCargo() == 1){
            if (tbView.getSelectionModel().getSelectedItem().getCargo() == 0 ||
                    tbView.getSelectionModel().getSelectedItem().getCargo() == 1){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ControlX - Aviso");
                alert.setHeaderText("Acesso negado");
                alert.setContentText("Você não tem permissão para editar\n um Admin/Supervisor.");

                alert.showAndWait();
            } else {
                new GerenciarUsuario().show(false, true, tbView.getSelectionModel().getSelectedItem().getId());
            }
        } else {
            new GerenciarUsuario().show(false, true, tbView.getSelectionModel().getSelectedItem().getId());
        }
    }

    public void botaoViewUsuario() throws IOException, ClassNotFoundException {
        new GerenciarUsuario().show(true, false, tbView.getSelectionModel().getSelectedItem().getId());
    }

    public void botaoVoltar() throws IOException {
        new MenuPrincipal().show();
    }

    public void pesquisarUsuario() throws ClassNotFoundException {
        if (txPesquisar.getText().equals("")) {
            listView(udao.listAll());
        }else {

            if (rdId.isSelected()) {
                listView(udao.listAllById(txPesquisar.getText()));
            } else if (rdNome.isSelected()) {
                listView(udao.listAllByName(txPesquisar.getText()));
            }
        }

    }

    public void verificaSelecao(){
        if (!tbView.getSelectionModel().isEmpty()){
            btEdit.setDisable(false);
            btView.setDisable(false);
            if (Login.getUser().getCargo() == 1){
                btRemove.setDisable(true);
            }else{
                btRemove.setDisable(false);
            }

        } else {
            btRemove.setDisable(true);
            btEdit.setDisable(true);
            btView.setDisable(true);
        }
    }
}
