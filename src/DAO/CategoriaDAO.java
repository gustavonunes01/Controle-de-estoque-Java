package DAO;

import connection.ConnectionFactory;
import models.Categoria;
import models.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CategoriaDAO {

    public void add(Categoria c) throws ClassNotFoundException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {

            stmt = con.prepareStatement("INSERT INTO categoria (nome) " +
                    "VALUES (?);");
            stmt.setString(1, c.getNome());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
    public void up(Categoria c) throws ClassNotFoundException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {

            stmt = con.prepareStatement("UPDATE categoria SET nome = ? WHERE id = ?;");
            stmt.setString(1, c.getNome());
            stmt.setInt(2, c.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<Categoria> listAll() throws ClassNotFoundException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Categoria> lista = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM categoria WHERE deleted_at is NULL;");
            rs = stmt.executeQuery();

            while (rs.next()){
                Categoria cat = new Categoria();
                cat.setId(rs.getInt("id"));
                cat.setNome(rs.getString("nome"));
                lista.add(cat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);

        }
        return lista;
    }

    public List<Categoria> listAllById(String id) throws ClassNotFoundException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Categoria> lista = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM categoria WHERE id = ? AND deleted_at is NULL;");
            stmt.setString(1, id);
            rs = stmt.executeQuery();

            while (rs.next()){
                Categoria cat = new Categoria();
                cat.setId(rs.getInt("id"));
                cat.setNome(rs.getString("nome"));
                lista.add(cat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);

        }
        return lista;
    }

    public List<Categoria> listAllByName(String nome) throws ClassNotFoundException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Categoria> lista = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM categoria WHERE nome LIKE ? AND deleted_at is NULL;");
            stmt.setString(1, "%" + nome + "%");
            rs = stmt.executeQuery();

            while (rs.next()){
                Categoria cat = new Categoria();
                cat.setId(rs.getInt("id"));
                cat.setNome(rs.getString("nome"));
                lista.add(cat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);

        }
        return lista;
    }

    public void del(Categoria c) throws ClassNotFoundException {   // ou pelo id, public void del(int id)
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        //Formatando a data
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        Date date = new Date(System.currentTimeMillis());
        String data = dateFormat.format(date);


        try {

            stmt = con.prepareStatement("UPDATE categoria SET deleted_at = ? WHERE id = ?;");
            stmt.setString(1, data);
            stmt.setInt(2, c.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

    }

    public Categoria read(Categoria c) throws ClassNotFoundException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Categoria cat = new Categoria();

        try {
            stmt = con.prepareStatement("SELECT id, nome FROM categoria WHERE id = ? and deleted_at is NULL;");
            stmt.setInt(1, c.getId());
            rs = stmt.executeQuery();

            if (rs.next()) {
                cat.setId(rs.getInt("id"));
                cat.setNome(rs.getString("nome"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
            return cat;
        }
    }

    public Categoria read(String nome) throws ClassNotFoundException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Categoria cat = new Categoria();

        try {
            stmt = con.prepareStatement("SELECT id, nome FROM categoria WHERE nome = ? and deleted_at is NULL;");
            stmt.setString(1, nome);
            rs = stmt.executeQuery();

            if (rs.next()) {
                cat.setId(rs.getInt("id"));
                cat.setNome(rs.getString("nome"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
            return cat;
        }
    }

    public Categoria read(int id) throws ClassNotFoundException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Categoria cat = new Categoria();

        try {
            stmt = con.prepareStatement("SELECT id, nome FROM categoria WHERE id = ? and deleted_at is NULL;");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                cat.setId(rs.getInt("id"));
                cat.setNome(rs.getString("nome"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
            return cat;
        }

    }

    public int idAutoIncrement() throws ClassNotFoundException {
        int id = 0;
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("SELECT AUTO_INCREMENT FROM information_schema.tables WHERE table_name = 'categoria' AND table_schema = 'controlx'");
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
