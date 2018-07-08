package controls;

import DAO.CompraDAO;
import DAO.VendaDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.ChoiceBoxSkin;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import models.Compra;
import models.Venda;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class Historico implements Initializable {

    @FXML
    private TableView<Compra> tbCompras;

    @FXML
    private ComboBox cbFiltro;

    @FXML
    private TabPane tpPane;

    @FXML
    private TableView<Venda> tbVendas;

    @FXML
    private Tab tpCompra;

    @FXML
    private ComboBox cbFiltroCompra;

    @FXML
    private Button btDetalhesVenda;

    @FXML
    private Button btVoltarVendas;

    @FXML
    private Button btDetalhesCompra;

    @FXML
    private Tab tpVenda;

    @FXML
    private Button btVoltarCompras;

    VendaDAO vDAO = new VendaDAO();
    CompraDAO cDAO = new CompraDAO();
    boolean venda = false;

    Historico(){

    }

    Historico(boolean venda){
        this.venda = venda;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            verificaSelecao();
            if(Login.getUser().getCargo() == 2){
                tpVenda.setDisable(true);
            } else if(Login.getUser().getCargo() == 3){
                tpCompra.setDisable(true);
                tpPane.getSelectionModel().select(tpVenda);
            }
            ObservableList<String> datas = FXCollections.observableArrayList("Hoje", "Ultimos 7 dias", "Ultimo mês","Todas");
            cbFiltro.setItems(datas);
            cbFiltro.setValue("Todas");
            cbFiltroCompra.setItems(datas);
            cbFiltroCompra.setValue("Todas");
            fillTables();
            if (venda){
                tpPane.getSelectionModel().select(tpVenda);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void show() throws IOException {
        Stage primaryStage = new Stage();
        FXMLLoader root = new FXMLLoader(getClass().getResource("/views/Historico.fxml"));
        root.setControllerFactory(c -> {
            return new Historico();
        });
        primaryStage.setTitle("ControlX - Histórico");
        Main.stage.hide();
        Main.stage = primaryStage;
        primaryStage.setScene(new Scene(root.load(), primaryStage.getWidth(), primaryStage.getHeight()));
        primaryStage.setResizable(false);
        Main.stage.getIcons().add(new Image("images/controlx.png"));
        primaryStage.show();
    }

    public void showVenda(boolean venda) throws IOException {
        Stage primaryStage = new Stage();
        FXMLLoader root = new FXMLLoader(getClass().getResource("/views/Historico.fxml"));
        root.setControllerFactory(c -> {
            return new Historico(venda);
        });
        primaryStage.setTitle("ControlX - Histórico");
        Main.stage.hide();
        Main.stage = primaryStage;
        primaryStage.setScene(new Scene(root.load(), primaryStage.getWidth(), primaryStage.getHeight()));
        primaryStage.setResizable(false);
        Main.stage.getIcons().add(new Image("images/controlx.png"));
        primaryStage.show();
    }

    public void fillTables() throws ClassNotFoundException {
        //Table VENDA

        tbVendas.getItems().clear();
        tbVendas.getColumns().clear();


        ObservableList<Venda> vendas = FXCollections.observableArrayList();

        for (Venda v: vDAO.listAll()) {
            vendas.add(new Venda(v.getId(), v.getUsuario(), v.getValor(), v.getData()));
        }

        TableColumn<Venda, String> idColumn = new TableColumn<>("ID");
        idColumn.setMinWidth(50);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Venda, String> nomeColumn = new TableColumn<>("Usuário");
        nomeColumn.setMinWidth(200);
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("usuario"));

        TableColumn<Venda, String> precoColumn = new TableColumn<>("Total (R$)");
        precoColumn.setMinWidth(50);
        precoColumn.setCellValueFactory(new PropertyValueFactory<>("valor"));

        TableColumn<Venda, String> dataColumn = new TableColumn<>("Data");
        dataColumn.setMinWidth(50);
        dataColumn.setCellValueFactory(new PropertyValueFactory<>("data"));


        tbVendas.setItems(vendas);
        tbVendas.getColumns().addAll(idColumn, nomeColumn, precoColumn, dataColumn);

        //TABLE COMPRA ----------------------------------------- x -----------------------------------------

        tbCompras.getItems().clear();
        tbCompras.getColumns().clear();

        ObservableList<Compra> compras = FXCollections.observableArrayList();

        for (Compra c: cDAO.listAll()) {
            if(c.getStatus() == 1 )
                compras.add(new Compra(c.getId(), c.getUsuario(), c.getValor(), c.getProdutos(), c.getStatus(), c.getData(), c.getDataEntrega(), c.getDataFinal()));
        }

        TableColumn<Compra, String> cidColumn = new TableColumn<>("ID");
        cidColumn.setMinWidth(50);
        cidColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Compra, String> cnomeColumn = new TableColumn<>("Usuário");
        cnomeColumn.setMinWidth(200);
        cnomeColumn.setCellValueFactory(new PropertyValueFactory<>("usuario"));

        TableColumn<Compra, String> cprecoColumn = new TableColumn<>("Total (R$)");
        cprecoColumn.setMinWidth(50);
        cprecoColumn.setCellValueFactory(new PropertyValueFactory<>("valor"));

        TableColumn<Compra, String> cdataColumn = new TableColumn<>("Data Pedido");
        cdataColumn.setMinWidth(50);
        cdataColumn.setCellValueFactory(new PropertyValueFactory<>("data"));

        TableColumn<Compra, String> cdataFinalColumn = new TableColumn<>("Data Pedido Finalizado");
        cdataFinalColumn.setMinWidth(50);
        cdataFinalColumn.setCellValueFactory(new PropertyValueFactory<>("dataFinal"));


        tbCompras.setItems(compras);
        tbCompras.getColumns().addAll(cidColumn, cnomeColumn, cprecoColumn, cdataColumn, cdataFinalColumn);
    }

    public void fillCompraByDate(Date data) throws ClassNotFoundException {
        tbCompras.getItems().clear();
        tbCompras.getColumns().clear();

        ObservableList<Compra> compras = FXCollections.observableArrayList();

        for (Compra c: cDAO.listAll()) {
            if(c.getStatus() == 1 && c.getDataFinal().compareTo(data) >= 0)
                compras.add(new Compra(c.getId(), c.getUsuario(), c.getValor(), c.getProdutos(), c.getStatus(), c.getData(), c.getDataEntrega(), c.getDataFinal()));
        }

        TableColumn<Compra, String> cidColumn = new TableColumn<>("ID");
        cidColumn.setMinWidth(50);
        cidColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Compra, String> cnomeColumn = new TableColumn<>("Usuário");
        cnomeColumn.setMinWidth(200);
        cnomeColumn.setCellValueFactory(new PropertyValueFactory<>("usuario"));

        TableColumn<Compra, String> cprecoColumn = new TableColumn<>("Total (R$)");
        cprecoColumn.setMinWidth(50);
        cprecoColumn.setCellValueFactory(new PropertyValueFactory<>("valor"));

        TableColumn<Compra, String> cdataColumn = new TableColumn<>("Data Pedido");
        cdataColumn.setMinWidth(50);
        cdataColumn.setCellValueFactory(new PropertyValueFactory<>("data"));

        TableColumn<Compra, String> cdataFinalColumn = new TableColumn<>("Data Pedido Finalizado");
        cdataFinalColumn.setMinWidth(50);
        cdataFinalColumn.setCellValueFactory(new PropertyValueFactory<>("dataFinal"));


        tbCompras.setItems(compras);
        tbCompras.getColumns().addAll(cidColumn, cnomeColumn, cprecoColumn, cdataColumn, cdataFinalColumn);
    }

    public void fillVendaByDate(Date data) throws ClassNotFoundException {
        tbVendas.getItems().clear();
        tbVendas.getColumns().clear();


        ObservableList<Venda> vendas = FXCollections.observableArrayList();

        for (Venda v: vDAO.listAll()) {
            if (v.getData().compareTo(data) >= 0)
                vendas.add(new Venda(v.getId(), v.getUsuario(), v.getValor(), v.getData()));
        }

        TableColumn<Venda, String> idColumn = new TableColumn<>("ID");
        idColumn.setMinWidth(50);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Venda, String> nomeColumn = new TableColumn<>("Usuário");
        nomeColumn.setMinWidth(200);
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("usuario"));

        TableColumn<Venda, String> precoColumn = new TableColumn<>("Total (R$)");
        precoColumn.setMinWidth(50);
        precoColumn.setCellValueFactory(new PropertyValueFactory<>("valor"));

        TableColumn<Venda, String> dataColumn = new TableColumn<>("Data");
        dataColumn.setMinWidth(50);
        dataColumn.setCellValueFactory(new PropertyValueFactory<>("data"));


        tbVendas.setItems(vendas);
        tbVendas.getColumns().addAll(idColumn, nomeColumn, precoColumn, dataColumn);
    }


    public void botaoVoltar() throws IOException {
        new MenuPrincipal().show();
    }

    public void visualizarCompra() throws ClassNotFoundException, IOException {
        Compra cc =  cDAO.read(tbCompras.getSelectionModel().getSelectedItem().getId());
        new VisualizarCompra().show(cc, true);
    }

    public void visualizarVenda() throws ClassNotFoundException, IOException {
        Venda vv = vDAO.read(tbVendas.getSelectionModel().getSelectedItem().getId());
        new VisualizarVenda().show(vv);
    }

    public void verificaSelecao(){
        if(!tbCompras.getSelectionModel().isEmpty()){
            btDetalhesCompra.setDisable(false);
        } else {
            btDetalhesCompra.setDisable(true);
        }

        if(!tbVendas.getSelectionModel().isEmpty()){
            btDetalhesVenda.setDisable(false);
        } else {
            btDetalhesVenda.setDisable(true);
        }
    }

    public void checaFiltroCompra() throws ClassNotFoundException {
        Date dataHoje = new Date(System.currentTimeMillis());
        dataHoje.setHours(0);
        dataHoje.setMinutes(0);
        dataHoje.setSeconds(0);
        Calendar c = Calendar.getInstance();
        c.setTime(dataHoje);

        if(cbFiltroCompra.getValue() == "Hoje"){
            fillCompraByDate(c.getTime());
        }
        else if (cbFiltroCompra.getValue() == "Ultimos 7 dias"){
            c.add(Calendar.DATE, -7);
            fillCompraByDate(c.getTime());
        }
        else if (cbFiltroCompra.getValue() == "Ultimo mês"){
            c.add(Calendar.DATE, -30);
            fillCompraByDate(c.getTime());
        }
        else
            fillTables();
    }

    public void checaFiltroVenda() throws ClassNotFoundException {
        Date dataHoje = new Date(System.currentTimeMillis());
        dataHoje.setHours(0);
        dataHoje.setMinutes(0);
        dataHoje.setSeconds(0);
        Calendar c = Calendar.getInstance();
        c.setTime(dataHoje);

        if(cbFiltro.getValue() == "Hoje"){
            fillVendaByDate(c.getTime());
        }
        else if (cbFiltro.getValue() == "Ultimos 7 dias"){
            c.add(Calendar.DATE, -7);
            fillVendaByDate(c.getTime());
        }
        else if (cbFiltro.getValue() == "Ultimo mês"){
            c.add(Calendar.DATE, -30);
            fillVendaByDate(c.getTime());
        }
        else
            fillTables();
    }
}