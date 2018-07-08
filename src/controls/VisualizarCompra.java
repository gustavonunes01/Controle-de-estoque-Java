package controls;

import DAO.CompraDAO;
import DAO.ProdutoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Compra;
import models.Produto;
import models.Usuario;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class VisualizarCompra implements Initializable{

    @FXML
    private Button btVoltar;

    @FXML
    private TextField txUsuario;

    @FXML
    private TableView tbProdutos;

    @FXML
    private TextField txId;

    @FXML
    private TextField txDataPedido;

    @FXML
    private TextField txTotal;

    @FXML
    private Button btVisualizar;

    @FXML
    private TextField txDataPrevista;

    @FXML
    private AnchorPane apPane;

    @FXML

    Compra compra = new Compra();
    boolean view;

    CompraDAO cdao = new CompraDAO();
    ProdutoDAO pdao = new ProdutoDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            fillFields();
            if (view)
                btVisualizar.setVisible(false);
            else
                btVisualizar.setVisible(true);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void show(Compra compra, boolean view) throws IOException {
        Stage primaryStage = new Stage();
        FXMLLoader root = new FXMLLoader(getClass().getResource("/views/VisualizarCompra.fxml"));
        root.setControllerFactory(c -> {
            return new VisualizarCompra(compra, view);
        });
        primaryStage.setTitle("ControlX - Visualizar Compra");
        Main.stage.hide();
        Main.stage = primaryStage;
        primaryStage.setScene(new Scene(root.load(), primaryStage.getWidth(), primaryStage.getHeight()));
        //primaryStage.initModality(Modality.WINDOW_MODAL);
        //primaryStage.initOwner(apPane.getScene().getWindow());
        primaryStage.setResizable(false);
        Main.stage.getIcons().add(new Image("images/controlx.png"));
        primaryStage.show();
    }

    public VisualizarCompra(){

    }
    public VisualizarCompra(Compra compra, boolean view){
        this.compra = compra;
        this.view = view;
    }


    public void botaoVoltar() throws IOException {
        if (view)
            new Historico().show();
        else
            new Compras().show();
    }


    public void botaoFinalizar() throws ClassNotFoundException, IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("ControlX - Finalizar Pedido");
        alert.setResizable(false);
        alert.setHeaderText("Deseja Finalizar o Pedido de Compra?");
        alert.setContentText("Após finalizada, a compra aparecerá no Histórico e será acrescentado os \n produtos no estoque.\n Deseja Finalizar");
        alert.getButtonTypes();

        Optional<ButtonType> result = alert.showAndWait();
        if(!result.isPresent())
            return;
        else if(result.get() == ButtonType.OK) {
            compra.setStatus(1);
            compra.setDataFinal(new Date(System.currentTimeMillis()));
            cdao.up(compra);

            //Atualizando Produtos
            for(Produto p: compra.getProdutos()){
                Produto pEstoque = pdao.read(p.getId());
                pEstoque.setQtd(pEstoque.getQtd() + p.getQtd());
                pdao.up(pEstoque);
            }

            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
            alert1.setTitle("ControlX - Pedido Finalizado");
            alert1.setContentText("Pedido Finalizado");
            alert1.showAndWait();
            new Compras().show();
        }
        else if(result.get() == ButtonType.CANCEL)
            return;


    }
    public void fillFields(){
        txDataPedido.setText(compra.getData().toString());
        txDataPrevista.setText(compra.getDataEntrega().toString());
        txId.setText(String.valueOf(compra.getId()));
        txTotal.setText(String.valueOf(compra.getValor()));
        txUsuario.setText(compra.getUsuario().getNome());


        tbProdutos.getItems().clear();
        tbProdutos.getColumns().clear();

        ObservableList<Produto> lista = FXCollections.observableArrayList();

        for (Produto p : compra.getProdutos()) {
            lista.add(new Produto(p.getId(), p.getNome(), p.getPreco(), p.getQtd(), p.getTipoUn(), p.getCat()));
        }

        TableColumn<Produto, String> idColumn = new TableColumn<>("ID");
        idColumn.setMinWidth(50);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Produto, String> nomeColumn = new TableColumn<>("Nome");
        nomeColumn.setMinWidth(220);
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<Produto, String> precoColumn = new TableColumn<>("Preço (R$)");
        precoColumn.setMinWidth(60);
        precoColumn.setCellValueFactory(new PropertyValueFactory<>("preco"));

        TableColumn<Produto, String> qtdColumn = new TableColumn<>("Qtd");
        qtdColumn.setMinWidth(60);
        qtdColumn.setCellValueFactory(new PropertyValueFactory<>("qtd"));

        TableColumn<Produto, String> unColumn = new TableColumn<>("Un");
        unColumn.setMinWidth(60);
        unColumn.setCellValueFactory(new PropertyValueFactory<>("tipoUn"));

        tbProdutos.setItems(lista);
        tbProdutos.getColumns().addAll(idColumn, nomeColumn, precoColumn, qtdColumn, unColumn);
    }

}
