package DAO;

import connection.ConnectionFactory;
import models.Fornecedor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FornecedorDAO {

    public void add(Fornecedor f) throws ClassNotFoundException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {

            stmt = con.prepareStatement("INSERT INTO fornecedor(nome, cnpj, tel1, tel2, cep, num, rua, comp, bairro, cidade, estado)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
            stmt.setString(1, f.getNome());
            stmt.setString(2, f.getCnpj());
            stmt.setString(3, f.getTelefone1());
            stmt.setString(4, f.getTelefone2());
            stmt.setString(5, f.getCep());
            stmt.setInt(6, f.getNum());
            stmt.setString(7, f.getRua());
            stmt.setString(8, f.getComp());
            stmt.setString(9, f.getBairro());
            stmt.setString(10, f.getCidade());
            stmt.setString(11, f.getEstado());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void up(Fornecedor f) throws ClassNotFoundException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {

            stmt = con.prepareStatement("UPDATE fornecedor SET nome = ?, cnpj = ?, tel1 = ?, tel2 = ?, " +
                    "cep = ?, num = ?, rua = ?, comp = ?, bairro = ?, cidade = ?, estado = ? WHERE id = ?;");
            stmt.setString(1, f.getNome());
            stmt.setString(2, f.getCnpj());
            stmt.setString(3, f.getTelefone1());
            stmt.setString(4, f.getTelefone2());
            stmt.setString(5, f.getCep());
            stmt.setInt(6, f.getNum());
            stmt.setString(7, f.getRua());
            stmt.setString(8, f.getComp());
            stmt.setString(9, f.getBairro());
            stmt.setString(10, f.getCidade());
            stmt.setString(11, f.getEstado());
            stmt.setInt(12, f.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<Fornecedor> listAll() throws ClassNotFoundException {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Fornecedor> lista = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM fornecedor WHERE deleted_at is NULL;");
            rs = stmt.executeQuery();

            while (rs.next()){
                Fornecedor forn = new Fornecedor();
                forn.setId(rs.getInt("id"));
                forn.setNum(rs.getInt("num"));
                forn.setNome(rs.getString("nome"));
                forn.setRua(rs.getString("rua"));
                forn.setBairro(rs.getString("bairro"));
                forn.setCep(rs.getString("cep"));
                forn.setCidade(rs.getString("cidade"));
                forn.setCnpj(rs.getString("cnpj"));
                forn.setEstado(rs.getString("estado"));
                forn.setTelefone1(rs.getString("tel1"));
                forn.setTelefone2(rs.getString("tel2"));
                lista.add(forn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return lista;
    }

    public List<Fornecedor> listAllById(String id) throws ClassNotFoundException {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Fornecedor> lista = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM fornecedor WHERE id LIKE ? AND deleted_at is NULL;");
            stmt.setString(1, "%" + id + "%");
            rs = stmt.executeQuery();

            while (rs.next()){
                Fornecedor forn = new Fornecedor();
                forn.setId(rs.getInt("id"));
                forn.setNum(rs.getInt("num"));
                forn.setNome(rs.getString("nome"));
                forn.setRua(rs.getString("rua"));
                forn.setBairro(rs.getString("bairro"));
                forn.setCep(rs.getString("cep"));
                forn.setCidade(rs.getString("cidade"));
                forn.setCnpj(rs.getString("cnpj"));
                forn.setEstado(rs.getString("estado"));
                forn.setTelefone1(rs.getString("tel1"));
                forn.setTelefone2(rs.getString("tel2"));
                lista.add(forn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return lista;
    }

    public List<Fornecedor> listAllByName(String nome) throws ClassNotFoundException {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Fornecedor> lista = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM fornecedor WHERE nome LIKE ? AND deleted_at is NULL;");
            stmt.setString(1, "%" + nome + "%");
            rs = stmt.executeQuery();

            while (rs.next()){
                Fornecedor forn = new Fornecedor();
                forn.setId(rs.getInt("id"));
                forn.setNum(rs.getInt("num"));
                forn.setNome(rs.getString("nome"));
                forn.setRua(rs.getString("rua"));
                forn.setBairro(rs.getString("bairro"));
                forn.setCep(rs.getString("cep"));
                forn.setCidade(rs.getString("cidade"));
                forn.setCnpj(rs.getString("cnpj"));
                forn.setEstado(rs.getString("estado"));
                forn.setTelefone1(rs.getString("tel1"));
                forn.setTelefone2(rs.getString("tel2"));
                lista.add(forn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return lista;
    }

    public void del(Fornecedor f) throws ClassNotFoundException {   // ou pelo id, public void del(int id)
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        //Formatando a data
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        Date date = new Date(System.currentTimeMillis());
        String data = dateFormat.format(date);


        try {

            stmt = con.prepareStatement("UPDATE fornecedor SET deleted_at = ? WHERE id = ?;");
            stmt.setString(1, data);
            stmt.setInt(2, f.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public Fornecedor read(int id) throws ClassNotFoundException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Fornecedor forn = new Fornecedor();

        try {
            stmt = con.prepareStatement("SELECT id, nome, cnpj, tel1, tel2, cep, " +
                    "num, rua, comp, bairro, cidade, estado" +
                    " FROM fornecedor WHERE id = ? and deleted_at is NULL;");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if(rs.next()) {
                forn.setId(rs.getInt("id"));
                forn.setNome(rs.getString("nome"));
                forn.setCnpj(rs.getString("cnpj"));
                forn.setTelefone1(rs.getString("tel1"));
                forn.setTelefone2(rs.getString("tel2"));
                forn.setCep(rs.getString("cep"));
                forn.setNum(rs.getInt("num"));
                forn.setRua(rs.getString("rua"));
                forn.setComp(rs.getString("comp"));
                forn.setBairro(rs.getString("bairro"));
                forn.setCidade(rs.getString("cidade"));
                forn.setEstado(rs.getString("estado"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
            return forn;
        }
    }

    public int idAutoIncrement() throws ClassNotFoundException {
        int id = 0;
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("SELECT AUTO_INCREMENT FROM information_schema.tables WHERE table_name = 'fornecedor' AND table_schema = 'controlx'");
            rs = stmt.executeQuery();

            if (rs.next()){
                id = rs.getInt("AUTO_INCREMENT");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
            return id;
        }

    }
}