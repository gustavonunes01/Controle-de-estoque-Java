package controls;

import DAO.UsuarioDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import models.Usuario;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class GerenciarUsuario implements Initializable {

    @FXML
    private TextField txNome;

    @FXML
    private TextField txId;

    @FXML
    private TextField txCpf;

    @FXML
    private RadioButton rdM;

    @FXML
    private RadioButton rdF;

    @FXML
    private ToggleGroup Sexo;

    @FXML
    private DatePicker dtDataNasc;

    @FXML
    private TextField txTel1;

    @FXML
    private TextField txTel2;

    @FXML
    private TextField txCep;

    @FXML
    private TextField txRua;

    @FXML
    private TextField txBairro;

    @FXML
    private TextField txCidade;

    @FXML
    private TextField txNum;

    @FXML
    private TextField txComp;

    @FXML
    private TextField txEstado;

    @FXML
    private TextField txLogin;

    @FXML
    private PasswordField txSenha;

    @FXML
    private Label lbUsuario;

    @FXML
    private Button btSalvar;

    @FXML
    private Button btCancelar;

    @FXML
    private ComboBox<String> cbCargo;

    int id;
    boolean edit = false;
    boolean view = false;
    boolean checklogin = false;
    UsuarioDAO udao = new UsuarioDAO();

    GerenciarUsuario(boolean view, boolean edit, int id){
        this.id = id;
        this.view = view;
        this.edit = edit;
    }

    GerenciarUsuario(){

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            ativarBotaoSalvar();
            iniCombobox();
            if (edit || view){
                preencher();
                txLogin.setEditable(false);
                lbUsuario.setDisable(true);
            }
            if (view){
                visualizar();
            }
            if (!edit && !view){
                txId.setText(Integer.toString(udao.idAutoIncrement()));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void show(boolean view, boolean edit, int id) throws IOException {
        Stage primaryStage = new Stage();
        FXMLLoader root = new FXMLLoader(getClass().getResource("/views/GerenciarUsuario.fxml"));
        root.setControllerFactory(c -> {
            return new GerenciarUsuario(view, edit, id);
        });
        primaryStage.setTitle("ControlX - Gerenciar Usuário");
        Main.stage.hide();
        Main.stage = primaryStage;
        primaryStage.setScene(new Scene(root.load(), primaryStage.getWidth(), primaryStage.getHeight()));
        primaryStage.setResizable(false);
        Main.stage.getIcons().add(new Image("images/controlx.png"));
        primaryStage.show();
    }

    public void show() throws IOException {
        Stage primaryStage = new Stage();
        FXMLLoader root = new FXMLLoader(getClass().getResource("/views/GerenciarUsuario.fxml"));
        root.setControllerFactory(c -> {
            return new GerenciarUsuario();
        });
        primaryStage.setTitle("ControlX - Adicionar Usuário");
        Main.stage.hide();
        Main.stage = primaryStage;
        primaryStage.setScene(new Scene(root.load(), primaryStage.getWidth(), primaryStage.getHeight()));
        primaryStage.setResizable(false);
        Main.stage.getIcons().add(new Image("images/controlx.png"));
        primaryStage.show();
    }

    public void verificaLogin() throws ClassNotFoundException {
        if (udao.verificaLogin(txLogin.getText())){
            lbUsuario.setTextFill(Color.GREEN);
            lbUsuario.setText("Login disponivel!");
            checklogin = true;
            ativarBotaoSalvar();

        } else if (!udao.verificaLogin(txLogin.getText())){
            lbUsuario.setTextFill(Color.RED);
            lbUsuario.setText("Login em uso!");
            checklogin = false;
            ativarBotaoSalvar();
        }
    }

    public void preencher() throws ClassNotFoundException {
        Usuario u = udao.read(id);
        txId.setText(Integer.toString(u.getId()));
        txNome.setText(u.getNome());
        txCpf.setText(u.getCpf());
        if(u.getSexo().equals("M")){
            rdM.setSelected(true);
        } else if(u.getSexo().equals("F")){
            rdF.setSelected(true);
        }
        dtDataNasc.setValue(LocalDate.parse(u.getDataNasc().toString()));
        txTel1.setText(u.getTelefone1());
        txTel2.setText(u.getTelefone2());
        txCep.setText(u.getCep());
        txNum.setText(Integer.toString(u.getNum()));
        txRua.setText(u.getRua());
        txComp.setText(u.getComp());
        txBairro.setText(u.getBairro());
        txCidade.setText(u.getCidade());
        txEstado.setText(u.getEstado());
        if(u.getCargo() == 0){
            cbCargo.setValue("Administrador");
        } else if(u.getCargo() == 1){
            cbCargo.setValue("Supervisor");
        } else if(u.getCargo() == 2){
            cbCargo.setValue("Almoxarife");
        } else if(u.getCargo() == 3){
            cbCargo.setValue("Caixa");
        }
        txLogin.setText(u.getLogin());
        txSenha.setText(u.getSenha());
    }

    public void visualizar(){
        txNome.setEditable(false);
        txCpf.setEditable(false);
        rdM.setDisable(true);
        rdF.setDisable(true);
        dtDataNasc.setEditable(false);
        txTel1.setEditable(false);
        txTel2.setEditable(false);
        txCep.setEditable(false);
        txNum.setEditable(false);
        txRua.setEditable(false);
        txComp.setEditable(false);
        txBairro.setEditable(false);
        txCidade.setEditable(false);
        txEstado.setEditable(false);
        cbCargo.setDisable(false);
        txLogin.setEditable(false);
        txSenha.setEditable(false);
        btSalvar.setVisible(false);
        btCancelar.setText("Voltar");
    }

    public void ativarBotaoSalvar(){
        if(txNome.getText().isEmpty() || txCpf.getText().isEmpty() ||
                txTel1.getText().isEmpty() ||
                txCep.getText().isEmpty() || txNum.getText().isEmpty() || txRua.getText().isEmpty() ||
                txBairro.getText().isEmpty() || txCidade.getText().isEmpty() ||
                txEstado.getText().isEmpty() || cbCargo.getValue().equals("<Selecione>") ||
                txLogin.getText().isEmpty() || txSenha.getText().isEmpty() ||
                dtDataNasc.getValue().equals("") || !checklogin){

            btSalvar.setDisable(true);
        } else {
            btSalvar.setDisable(false);
        }
    }

    public void iniCombobox(){
        if(Login.getUser().getCargo() == 1) {
            ObservableList<String> cargo = FXCollections.observableArrayList(
                    "Almoxarife", "Caixa");
            cbCargo.setItems(cargo);
        } else {

            ObservableList<String> cargo = FXCollections.observableArrayList(
                "Administrador", "Supervisor", "Almoxarife", "Caixa");
            cbCargo.setItems(cargo);
        }

        cbCargo.setValue("<Selecione>");
    }

    public void botaoCancelar() throws IOException {
        new Usuarios().show();
    }

    public void botaoSalvar() throws ClassNotFoundException, IOException {
        Usuario u = new Usuario();
        u.setNome(txNome.getText());
        u.setCpf(txCpf.getText());
        if(rdM.isSelected()){
            u.setSexo("M");
        } else if(rdF.isSelected()){
            u.setSexo("F");
        }
        u.setDataNasc(Date.valueOf(dtDataNasc.getValue()));
        u.setTelefone1(txTel1.getText());
        if(txTel2.getText() != "")
            u.setTelefone2(txTel2.getText());
        else
            u.setTelefone2("0");
        u.setCep(txCep.getText());
        u.setNum(Integer.parseInt(txNum.getText()));
        u.setRua(txRua.getText());
        u.setComp(txComp.getText());
        u.setBairro(txBairro.getText());
        u.setCidade(txCidade.getText());
        u.setEstado(txEstado.getText());
        if(cbCargo.getValue().equals("Administrador")) {
            u.setCargo(0);
        } else if(cbCargo.getValue().equals("Supervisor")) {
            u.setCargo(1);
        } else if(cbCargo.getValue().equals("Almoxarife")) {
            u.setCargo(2);
        } else if(cbCargo.getValue().equals("Caixa")) {
            u.setCargo(3);
        }
        u.setLogin(txLogin.getText());
        u.setSenha(txSenha.getText());

        if(edit){
            udao.up(u);
        } else if(!view && !edit){
            udao.add(u);
        }

        new Usuarios().show();
    }
}
