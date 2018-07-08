package models;

import java.util.Date;

public class Usuario {
    private int id;
    private String nome;
    private String cpf;
    private String sexo;
    private Date dataNasc;
    private String telefone1;
    private String telefone2;
    private String cep;
    private int num;
    private String rua;
    private String comp;
    private String bairro;
    private String cidade;
    private String estado;
    private int cargo;
    private String login;
    private String senha;
    //private String picPath;

    //GET and SET
    //-------x----------------x--------
    public String getNome(){
        return this.nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    //-------x----------------x--------
    public int getId(){
        return this.id;
    }
    public void setId(int id){
        this.id = id;
    }
    //-------x----------------x--------
    public String getCpf(){
        return this.cpf;
    }
    public void setCpf(String cpf){
        this.cpf = cpf;
    }
    //-------x----------------x--------
    public String getSexo(){
        return this.sexo;
    }
    public void setSexo(String sexo){
        this.sexo = sexo;
    }
    //-------x----------------x--------
    public Date getDataNasc(){
        return this.dataNasc;
    }
    public void setDataNasc(Date dataNasc){
        this.dataNasc = dataNasc;
    }
    //-------x----------------x--------
    public String getTelefone1(){
        return this.telefone1;
    }
    public void setTelefone1(String telefone1){
        this.telefone1 = telefone1;
    }
    //-------x----------------x--------
    public String getTelefone2(){
        return this.telefone2;
    }
    public void setTelefone2(String telefone2){
        this.telefone2 = telefone2;
    }
    //-------x----------------x--------
    public String getCep(){
        return this.cep;
    }
    public void setCep(String cep){
        this.cep = cep;
    }
    //-------x----------------x--------
    public int getNum(){
        return this.num;
    }
    public void setNum(int num){
        this.num = num;
    }
    //-------x----------------x--------
    public String getRua(){
        return this.rua;
    }
    public void setRua(String rua){
        this.rua = rua;
    }
    //-------x----------------x--------
    public String getComp(){
        return this.comp;
    }
    public void setComp(String comp){
        this.comp = comp;
    }
    //-------x----------------x--------
    public String getBairro(){
        return this.bairro;
    }
    public void setBairro(String bairro){
        this.bairro = bairro;
    }
    //-------x----------------x--------
    public String getCidade(){
        return this.cidade;
    }
    public void setCidade(String cidade){
        this.cidade = cidade;
    }
    //-------x----------------x--------
    public String getEstado(){
        return this.estado;
    }
    public void setEstado(String estado){
        this.estado = estado;
    }
    //-------x----------------x--------
    public int getCargo() {
        return cargo;
    }

    public void setCargo(int cargo) {
        this.cargo = cargo;
    }
    //-------x----------------x--------
    public String getLogin(){
        return this.login;
    }
    public void setLogin(String login){
        this.login = login;
    }
    //-------x----------------x--------
    public String getSenha(){
        return this.senha;
    }
    public void setSenha(String senha){
        this.senha = senha;
    }
    //-------x----------------x--------
    /*
    public String getPicPath(){
        return this.picPath;
    }
    public void setPicPath(String picPath){
        this.picPath = picPath;
    }
    */
    //-------x----------------x--------
    //Construtores
    //-------x----------------x--------
    public Usuario(int id, String nome, String cpf, String sexo, Date dataNasc, String telefone1, String telefone2, String cep, int num, String rua, String comp, String bairro, String cidade, String estado, int cargo, String login, String senha)
    {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.sexo = sexo;
        this.dataNasc = dataNasc;
        this.telefone1 = telefone1;
        this.telefone2 = telefone2;
        this.cep = cep;
        this.num = num;
        this.rua = rua;
        this.comp = comp;
        this.bairro = bairro;
        this.cidade = cidade;
        this.cargo = cargo;
        this.estado = estado;
        this.login = login;
        this.senha = senha;
    }

    public Usuario()
    {

    }
    //-------x----------------x--------


    @Override
    public String toString(){
        return id + " - " + nome;
    }
}
