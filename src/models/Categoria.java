package models;

import java.util.List;
import java.util.ArrayList;

public class Categoria {
    private String nome;
    private int id;
    private List<Produto> produtos = new ArrayList<Produto>();

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
    public List<Produto> getProdutos(){
        return this.produtos;
    }
    public void setProdutos(List<Produto> produtos){
        this.produtos = produtos;
    }
    //-------x----------------x--------
    //Construtores
    //-------x----------------x--------
    public Categoria(String nome, int id){
        this.nome = nome;
        this.id = id;
    }
    //-------x----------------x--------
    public Categoria(){
    }
    //-------x----------------x--------

    @Override
    public String toString(){
        return this.id + " - " + this.nome;
    }

}
