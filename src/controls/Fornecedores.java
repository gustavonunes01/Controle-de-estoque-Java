package controls;

import DAO.FornecedorDAO;
import DAO.ProdutoDAO;
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
import models.Fornecedor;
import models.Produto;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class Fornecedores implements Initializable {

    @FXML
    private TableView<Fornecedor> tbView;
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

    private FornecedorDAO fdao = new FornecedorDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            listView(fdao.listAll());
            verificaSelecao();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void show() throws IOException {
        Stage primaryStage = new Stage();
        FXMLLoader root = new FXMLLoader(getClass().getResource("/views/Fornecedores.fxml"));
        root.setControllerFactory(c -> {
            return new Fornecedores();
        });
        primaryStage.setTitle("ControlX - Fornecedores");
        Main.stage.hide();
        Main.stage = primaryStage;
        primaryStage.setScene(new Scene(root.load(), primaryStage.getWidth(), primaryStage.getHeight()));
        primaryStage.setResizable(true);
        Main.stage.getIcons().add(new Image("images/controlx.png"));
        primaryStage.show();
    }

    public void listView(List<Fornecedor> forns) throws ClassNotFoundException {
        tbView.getItems().clear();
        tbView.getColumns().clear();

        ObservableList<Fornecedor> lista = FXCollections.observableArrayList();

        for (Fornecedor f : forns) {
            lista.add(new Fornecedor(f.getId(), f.getNome(), f.getCnpj(),
                    f.getTelefone1(), f.getTelefone2(), f.getCep(), f.getNum(),
                    f.getRua(), f.getComp(), f.getBairro(), f.getCidade(), f.getEstado()));
        }

        TableColumn<Fornecedor, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setMinWidth(50);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Fornecedor, String> nomeColumn = new TableColumn<>("Nome");
        nomeColumn.setMinWidth(250);
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<Fornecedor, String> telColumn = new TableColumn<>("Telefone 1");
        telColumn.setMinWidth(100);
        telColumn.setCellValueFactory(new PropertyValueFactory<>("telefone1"));

        TableColumn<Fornecedor, String> cidadeColumn = new TableColumn<>("Cidade");
        cidadeColumn.setMinWidth(100);
        cidadeColumn.setCellValueFactory(new PropertyValueFactory<>("cidade"));

        tbView.setItems(lista);

        tbView.getColumns().addAll(idColumn, nomeColumn, telColumn, cidadeColumn);
        verificaSelecao();
    }

    public void botaoAddFornecedor() throws IOException {
        new GerenciarFornecedor().show();
    }

    public void botaoRemoveFornecedor() throws ClassNotFoundException {
        ProdutoDAO pdao = new ProdutoDAO();
        Fornecedor f = fdao.read(tbView.getSelectionModel().getSelectedItem().getId());
        List<Produto> lista = pdao.listAllByForn(f);

        if (lista.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("ControlX - Remover fornecedor");
            alert.setHeaderText("Este fornecedor será removida permanentemente.");
            alert.setContentText("Deseja continuar?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                fdao.del(fdao.read(tbView.getSelectionModel().getSelectedItem().getId()));
                listView(fdao.listAll());
            } else {
                // ... user chose CANCEL or closed the dialog
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ControlX - Aviso");
            alert.setHeaderText("Não é possível remover este fornecedor");
            alert.setContentText("Existem um ou mais produtos cadastrados\n com este fornecedor.");

            alert.showAndWait();
        }
    }

    public void botaoEditFornecedor() throws IOException, ClassNotFoundException {
        new GerenciarFornecedor().show(false, true, tbView.getSelectionModel().getSelectedItem().getId());
    }

    public void botaoViewFornecedor() throws IOException, ClassNotFoundException {
        new GerenciarFornecedor().show(true, false, tbView.getSelectionModel().getSelectedItem().getId());
    }

    public void botaoVoltar() throws IOException {
        new MenuPrincipal().show();
    }

    public void pesquisarFornecedor() throws ClassNotFoundException {
        if (txPesquisar.getText().equals("")) {
            listView(fdao.listAll());
        }else {

            if (rdId.isSelected()) {
                listView(fdao.listAllById(txPesquisar.getText()));
            } else if (rdNome.isSelected()) {
                listView(fdao.listAllByName(txPesquisar.getText()));
            }
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
