package controls;

import DAO.CompraDAO;
import DAO.ProdutoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import models.Compra;
import models.Usuario;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class Compras implements Initializable {

    @FXML
    private TableView<Compra> tbCPendentes;
    @FXML
    private Button btVisualizar;
    @FXML
    private Button btVoltar;

    CompraDAO cdao = new CompraDAO();
    ProdutoDAO pdao = new ProdutoDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            listViewPendentes(cdao.listAll());
            verificaSelecao();
            verificaData();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void show() throws IOException {
        Stage primaryStage = new Stage();
        FXMLLoader root = new FXMLLoader(getClass().getResource("/views/Compras.fxml"));
        root.setControllerFactory(c -> {
            return new Compras();
        });
        primaryStage.setTitle("ControlX - Compras");
        Main.stage.hide();
        Main.stage = primaryStage;
        primaryStage.setScene(new Scene(root.load(), primaryStage.getWidth(), primaryStage.getHeight()));
        primaryStage.setResizable(false);
        Main.stage.getIcons().add(new Image("images/controlx.png"));
        primaryStage.show();
    }


    public void listViewPendentes(List<Compra> compras) throws ClassNotFoundException {
        //---------- Compras Pendentes ---------//

        tbCPendentes.getItems().clear();
        tbCPendentes.getColumns().clear();

        ObservableList<Compra> lista = FXCollections.observableArrayList();

        for (Compra c : compras) {
            if (c.getStatus() == 0)
                lista.add(new Compra(c.getId(), c.getUsuario(), c.getValor(), c.getProdutos(), c.getStatus(), c.getDataEntrega(), c.getDataEntrega(), c.getDataFinal()));
        }

        TableColumn<Compra, String> idColumn = new TableColumn<>("ID");
        idColumn.setMinWidth(50);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Compra, Usuario> usuarioColumn = new TableColumn<>("Usuario");
        usuarioColumn.setMinWidth(200);
        usuarioColumn.setCellValueFactory(new PropertyValueFactory<>("usuario"));

        TableColumn<Compra, Integer> valorColumn = new TableColumn<>("Valor total");
        valorColumn.setMinWidth(100);
        valorColumn.setCellValueFactory(new PropertyValueFactory<>("valor"));

        TableColumn<Compra, Date> dataColumn = new TableColumn<>("Previsão");
        dataColumn.setMinWidth(120);
        dataColumn.setCellValueFactory(new PropertyValueFactory<>("dataEntrega"));

        tbCPendentes.setItems(lista);
        Date data = new Date(System.currentTimeMillis());
        for (Compra c : compras) {
            if (c.getDataEntrega().before(data)) {
                idColumn.setStyle("-fx-text-background-color: blue; -fx-selection-bar-non-focused: salmon;");
                usuarioColumn.setStyle("-fx-text-background-color: blue; -fx-selection-bar-non-focused: salmon;");
                valorColumn.setStyle("-fx-text-background-color: blue; -fx-selection-bar-non-focused: salmon;");
                dataColumn.setStyle("-fx-text-background-color: blue; -fx-selection-bar-non-focused: salmon;");
            }else{
                idColumn.setStyle("-fx-text-background-color: red; -fx-selection-bar-non-focused: salmon;");
                usuarioColumn.setStyle("-fx-text-background-color: red; -fx-selection-bar-non-focused: salmon;");
                valorColumn.setStyle("-fx-text-background-color: red; -fx-selection-bar-non-focused: salmon;");
                dataColumn.setStyle("-fx-text-background-color: red; -fx-selection-bar-non-focused: salmon;");
            }
        }

        tbCPendentes.getColumns().addAll(idColumn, usuarioColumn, valorColumn, dataColumn);
    }

    public void verificaSelecao(){
        if (!tbCPendentes.getSelectionModel().isEmpty()){
            btVisualizar.setDisable(false);
        } else {
            btVisualizar.setDisable(true);
        }

        if (tbCPendentes.isFocused()){
            btVisualizar.setDisable(false);
        } else {
            btVisualizar.setDisable(true);
        }
    }

    public void verificaData(){
        Date data = new Date(System.currentTimeMillis());
        tbCPendentes.setRowFactory(tv -> {
            return new TableRow<Compra>() {
                @Override
                public void updateItem(Compra c, boolean empty) {
                    super.updateItem(c, empty) ;
                    if (c == null) {
                        setStyle("");
                    } else if (c.getDataEntrega().before(data)) {
                        setStyle("-fx-text-background-color: #AAA;");
                        //setStyle("-fx-background-color: #0080FF;");
                    } else {
                        //setStyle("-fx-background-color: #0080FF;");
                    }
                }
            };
        });
    }

    public void botaoVoltar() throws IOException {
        new MenuPrincipal().show();
    }

    public void botaoAddCompra() throws IOException {
        new NovaCompra().show();
    }

    public void botaoVisualizar() throws IOException, ClassNotFoundException {
        Compra cc =  cdao.read(tbCPendentes.getSelectionModel().getSelectedItem().getId());
        //new NovaCompra().show(true, cc);
        new VisualizarCompra().show(cc, false);
    }

}
