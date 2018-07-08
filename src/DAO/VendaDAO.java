package DAO;

import connection.ConnectionFactory;
import models.Produto;
import models.Venda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class VendaDAO {

    private UsuarioDAO uDAO = new UsuarioDAO();
    ProdutoDAO pdao = new ProdutoDAO();
    FornecedorDAO fornDAO = new FornecedorDAO();
    CategoriaDAO catDAO = new CategoriaDAO();

    //public Venda read(int v){
    //    return
    //}

    public boolean vender(Venda v) throws ClassNotFoundException {

        boolean sucess = false;
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        try {

            stmt = con.prepareStatement("INSERT INTO vendas (idUsuario, valor, dataVenda) " +
                    "VALUES (?, ?, ?);");
            stmt.setInt(1, v.getUsuario().getId());
            stmt.setDouble(2, v.getValor());
            String data = dateFormat.format(v.getData());
            stmt.setString(3, data);
            stmt.executeUpdate();

            for (Produto p: v.getProdutos()) {

                PreparedStatement st = null;
                Connection conn = ConnectionFactory.getConnection();

                String preco = String.valueOf(p.getPreco());
                if (preco.contains(","))
                    preco = preco.replace(",", ".");

                String quantidade = String.valueOf(p.getQtd());
                if (quantidade.contains(","))
                    quantidade = quantidade.replace(",", ".");


                st = conn.prepareStatement("INSERT INTO produtos_venda (idVenda, idProduto, qtdProduto, precoUnProduto) " +
                        "VALUES (?, ?, ?, ?);");
                st.setInt(1, getIdVenda());
                st.setInt(2, p.getId());
                st.setString(3, quantidade);
                st.setString(4, preco);

                st.executeUpdate();
                ConnectionFactory.closeConnection(conn, st);
            }
            sucess = true;
        } catch (SQLException e) {
            e.printStackTrace();
            sucess = false;
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
        return sucess;

    }


    public int getIdVenda() throws ClassNotFoundException, SQLException {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        stmt = con.prepareStatement("SELECT `AUTO_INCREMENT` FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'controlx' AND TABLE_NAME = 'vendas'");
        rs = stmt.executeQuery();
        if (rs.next()) {
            int id = (rs.getInt("AUTO_INCREMENT"));
            return (id - 1);
        }
        else
            return 9999;

    }

    public void up(Venda v){

    }
    public List<Venda> listAll() throws ClassNotFoundException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Venda> lista = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM vendas;");
            rs = stmt.executeQuery();

            while (rs.next()){
                Venda vd = new Venda();
                vd.setId(rs.getInt("id"));
                vd.setUsuario(uDAO.read(rs.getInt("idUsuario")));
                vd.setValor(rs.getDouble("valor"));
                vd.setData(rs.getDate("dataVenda"));
                lista.add(vd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);

        }
        return lista;

    }

    public void del(Venda v){   // ou pelo id, public void del(int id)

    }

    public Venda read(int id) throws ClassNotFoundException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Venda venda = new Venda();
        UsuarioDAO uDAO = new UsuarioDAO();
        ProdutoDAO pDAO = new ProdutoDAO();

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        try {
            stmt = con.prepareStatement("SELECT * FROM vendas WHERE id = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                venda.setId(rs.getInt("id"));
                venda.setUsuario(uDAO.read(rs.getInt("idUsuario")));
                venda.setValor(rs.getDouble("valor"));
                venda.setData(rs.getDate("dataVenda"));
            }

            //Produtos da Compra

            stmt = con.prepareStatement("SELECT * FROM produtos_venda WHERE idVenda = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            List <Produto> lista = new ArrayList<>();
            while (rs.next()) {
                Produto p = pdao.readAll(rs.getInt("idProduto"));
                p.setQtd(rs.getDouble("qtdProduto"));
                p.setPreco(rs.getDouble("precoUnProduto"));

                lista.add(p);
            }

            venda.setProdutos(lista);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return venda;
    }
}
