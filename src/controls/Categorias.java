package controls;

import DAO.CategoriaDAO;
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
import models.Categoria;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class Categorias implements Initializable {

    @FXML
    private TableView<Categoria> tbView;
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

    private CategoriaDAO cdao = new CategoriaDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            listView(cdao.listAll());
            verificaSelecao();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void show() throws IOException {
        Stage primaryStage = new Stage();
        FXMLLoader root = new FXMLLoader(getClass().getResource("/views/Categorias.fxml"));
        root.setControllerFactory(c -> {
            return new Categorias();
        });
        primaryStage.setTitle("ControlX - Categorias");
        Main.stage.hide();
        Main.stage = primaryStage;
        primaryStage.setScene(new Scene(root.load(), primaryStage.getWidth(), primaryStage.getHeight()));
        primaryStage.setResizable(true);
        Main.stage.getIcons().add(new Image("images/controlx.png"));
        primaryStage.show();
    }

    public void listView(List<Categoria> cats) throws ClassNotFoundException {
        tbView.getItems().clear();
        tbView.getColumns().clear();

        ObservableList<Categoria> lista = FXCollections.observableArrayList();

        for (Categoria c : cats) {
            lista.add(new Categoria(c.getNome(), c.getId()));
        }

        TableColumn<Categoria, String> idColumn = new TableColumn<>("ID");
        idColumn.setMinWidth(50);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Categoria, String> nomeColumn = new TableColumn<>("Nome");
        nomeColumn.setMinWidth(250);
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));

        tbView.setItems(lista);

        tbView.getColumns().addAll(idColumn, nomeColumn);
        verificaSelecao();
    }

    public void botaoAddCategoria() throws IOException {
        new GerenciarCategoria().show();
    }

    public void botaoRemoveCategoria() throws ClassNotFoundException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("ControlX - Remover categoria");
        alert.setHeaderText("Esta categoria será removida permanentemente.");
        alert.setContentText("Deseja continuar?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            cdao.del(cdao.read(tbView.getSelectionModel().getSelectedItem().getId()));
            listView(cdao.listAll());
        } else {
            // ... user chose CANCEL or closed the dialog
        }
    }

    public void botaoEditCategoria() throws IOException, ClassNotFoundException {
        new GerenciarCategoria().show(false, true, tbView.getSelectionModel().getSelectedItem().getId());
    }

    public void botaoViewCategoria() throws IOException, ClassNotFoundException {
        new GerenciarCategoria().show(true, false, tbView.getSelectionModel().getSelectedItem().getId());
    }

    public void botaoVoltar() throws IOException {
        new Estoque().show();
    }

    public void pesquisarCategoria() throws ClassNotFoundException {
        if (rdId.isSelected()) {
            listView(cdao.listAllById(txPesquisar.getText()));
        } else if (rdNome.isSelected()) {
            listView(cdao.listAllByName(txPesquisar.getText()));
        }

        if (txPesquisar.getText().equals("")) {
            listView(cdao.listAll());
        }
    }

    public void verificaSelecao(){
        if (!tbView.getSelectionModel().isEmpty()){
            btRemove.setDisable(false);
            btEdit.setDisable(false);
            btView.setDisable(false);
        } else {
            btRemove.setDisable(true);
            btEdit.setDisable(true);
            btView.setDisable(true);
        }
    }
}
