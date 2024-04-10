package org.example.domain.local;

import org.example.connectionUtil.DbManager;
import org.example.domain.GenericDao;
import org.example.exceptions.MultipleOrNoLocalsException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LocalLoader implements GenericDao<Local> {
    private final DbManager dbManager;

    public LocalLoader(DbManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public List<Local> getAll() throws SQLException {
        List<Local> locale = new ArrayList<>();
        String query = "Select * From local";
        try (Connection conexiune = this.dbManager.getConnection()) {
            PreparedStatement prepared = conexiune.prepareStatement(query);
            ResultSet response = prepared.executeQuery();
            while (response.next()) {
                Local hopaNewLocal = createObjectLocal(response);
                locale.add(hopaNewLocal);
            }
            return locale;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    private Local createObjectLocal(ResultSet response) throws SQLException {
        return new Local(response.getInt("idLocal"),
                response.getString("denumireLocal"), response.getInt("CUI"),
                response.getString("oras"),
                response.getTime("oraInceputProgram"),
                response.getTime("oraSfarsitLocal"),
                response.getString("tipLocal"));

    }

    @Override
    public Local findByName(String NumeLocal) throws SQLException, MultipleOrNoLocalsException {
        final String query = "SELECT * FROM Local WHERE local.denumireLocal = ?;";
        Connection conexiune = null;
        PreparedStatement prepared = null;
        ResultSet resp = null;
        Local toBeReturnedLocal = null;

        try {
            conexiune = this.dbManager.getConnection();
            prepared = conexiune.prepareStatement(query);
            prepared.setString(1, NumeLocal);
            resp = prepared.executeQuery();

            if (resp.next()) {
                toBeReturnedLocal = createObjectLocal(resp);
                if (resp.next()) { // Verify if there is more than one result
                    throw new MultipleOrNoLocalsException("More than one local found.");
                }
            } else {
                throw new MultipleOrNoLocalsException("No local found.");
            }
        } finally {
            dbManager.closeConnection(conexiune, prepared, resp);
        }

        return toBeReturnedLocal;
    }

    @Override
    public void saveObject(Local o) throws SQLException {
        String query = "INSERT INTO local (denumireLocal, CUI, oras,oraInceputProgram, oraSfarsitLocal, tipLocal)VALUES (?,?,?,?,?,?);";
        try (Connection conexiune = this.dbManager.getConnection()) {
            if (this.findByName(o.getDenumireLocal()) != null) {
                System.out.println("Exista deja in baza de date un local cu acest nume");
            }
            PreparedStatement prepared = conexiune.prepareStatement(query);
            prepared.setString(1, o.getDenumireLocal());
            prepared.setInt(2, o.getCui());
            prepared.setString(3, o.getOras());
            prepared.setTime(4, o.getOraInceputLocal());
            prepared.setTime(5, o.getOraSfarsitProgram());
            prepared.setString(6, o.getTipLocal());
            prepared.executeQuery();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void updateSingleObject(Local o) {

    }

    @Override
    public void deleteSingleObjectFromDb(Local o) {

    }
}
