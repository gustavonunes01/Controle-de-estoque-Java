package models;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class Venda {
    private int id;
    private Usuario usuario;
    private double valor;
    private List<Produto> produtos = new ArrayList<Produto>();
    private Date data = new Date();

    //GET and SET
    //-------x----------------x--------
    public Usuario getUsuario(){
        return this.usuario;
    }
    public void setUsuario(Usuario usuario){
        this.usuario = usuario;
    }
    //-------x----------------x--------
    public int getId(){
        return this.id;
    }
    public void setId(int id){
        this.id = id;
    }
    //-------x----------------x--------
    public double getValor(){
        return this.valor;
    }
    public void setValor(double valor){
        this.valor = valor;
    }
    //-------x----------------x--------
    public List<Produto> getProdutos(){
        return this.produtos;
    }
    public void setProdutos(List<Produto> produtos){
        this.produtos = produtos;
    }
    //-------x----------------x--------
    public Date getData(){
        return this.data;
    }
    public void setData(Date data){
        this.data = data;
    }
    //-------x----------------x--------
    //Construtores
    //-------x----------------x--------
    public Venda(int id, Usuario usuario, double valor, List<Produto> produtos, Date data)
    {
        this.id = id;
        this.usuario = usuario;
        this.valor = valor;
        this.produtos = produtos;
        this.data = data;
    }
    //-------x----------------x--------
    public Venda(){

    }
    //-------x----------------x--------
    public Venda(int id, Usuario usuario, double valor, Date data) {
        this.id = id;
        this.usuario = usuario;
        this.valor = valor;
        this.data = data;
    }
}
