package org.example.domain.local;

import org.checkerframework.checker.units.qual.A;
import org.example.connectionUtil.DbManager;
import org.example.domain.GenericDao;
import org.example.exceptions.AlreadyExistsException;
import org.example.exceptions.DBComunicationException;
import org.example.exceptions.MultipleOrNoEntityException;
import org.example.exceptions.NoTheSameException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LocalLoader implements GenericDao<Local> {
    private final DbManager dbManager;

    public LocalLoader(DbManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public List<Local> getAll() throws DBComunicationException {
        List<Local> locale = new ArrayList<>();
        String query = "Select idLocal, denumireLocal, CUI, oras, oraInceputProgram, oraSfarsitLocal, tipLocal From local";
        Connection conexiune = null;
        PreparedStatement prepared = null;
        ResultSet response = null;
        try {
            conexiune = this.dbManager.getConnection();
            prepared = conexiune.prepareStatement(query);
            response = prepared.executeQuery();
            while (response.next()) {
                Local hopaNewLocal = createObjectLocal(response);
                locale.add(hopaNewLocal);
            }
            return locale;
        } catch (SQLException e) {
            throw new DBComunicationException(e);
        } finally {
            this.dbManager.closeConnection(conexiune, prepared, response);
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
    public List<Local> findByName(String NumeLocal) throws DBComunicationException {
        final String query = "SELECT idLocal, denumireLocal, CUI, oras, oraInceputProgram, oraSfarsitLocal, tipLocal FROM Local WHERE local.denumireLocal = ?;";
        Connection conexiune = null;
        PreparedStatement prepared = null;
        ResultSet resp = null;
        List<Local> localeCuAcelasiNume = new ArrayList<>();


        try {
            conexiune = this.dbManager.getConnection();
            prepared = conexiune.prepareStatement(query);
            prepared.setString(1, NumeLocal);
            resp = prepared.executeQuery();

           while(resp.next()){
               localeCuAcelasiNume.add(this.createObjectLocal(resp));
           }
            return localeCuAcelasiNume;
        } catch (SQLException e) {
            throw new DBComunicationException(e);
        } finally {
            dbManager.closeConnection(conexiune, prepared, resp);
        }
    }

    @Override
    public Local saveObject(Local o) throws DBComunicationException, MultipleOrNoEntityException {
        String insertQuery = "INSERT INTO local (denumireLocal, CUI, oras, oraInceputProgram, oraSfarsitLocal, tipLocal) VALUES (?, ?, ?, ?, ?, ?)";
        Connection conexiune = null;
        PreparedStatement insertStmt = null;
        ResultSet generatedKeys = null;
        try {
            conexiune = this.dbManager.getConnection();

            insertStmt = conexiune.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            insertStmt.setString(1, o.getDenumireLocal());
            insertStmt.setInt(2, o.getCui());
            insertStmt.setString(3, o.getOras());
            insertStmt.setTime(4, o.getOraInceputLocal());
            insertStmt.setTime(5, o.getOraSfarsitProgram());
            insertStmt.setString(6, o.getTipLocal());

            int affectedRows = insertStmt.executeUpdate();
            if (affectedRows == 0) {
                return null;
            }
            if (affectedRows > 1) {
                throw new DBComunicationException("Cumva au fost trimise mai multe atunci s-a incercat salvarea unui obiect, acesta fiind: "+o);
            }

            generatedKeys = insertStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int newId = generatedKeys.getInt(1);
                return this.finById(newId);
            }
            return null;
        } catch (SQLException e) {
            throw new DBComunicationException("Database communication error " + e.getMessage(), e);
        } finally {
            this.dbManager.closeConnection(conexiune, insertStmt, generatedKeys);
        }
    }

    private Local finById(int id) throws DBComunicationException {
        String selectQuery = "SELECT idLocal, denumireLocal, CUI, oras, oraInceputProgram, oraSfarsitLocal, tipLocal FROM local WHERE idLocal = ?";
        Connection con = null;
        PreparedStatement selectStm = null;
        ResultSet set = null;
        Local finalLocal = null;
        try {
            con = this.dbManager.getConnection();
            selectStm = con.prepareStatement(selectQuery);
            selectStm.setInt(1, id);
            set = selectStm.executeQuery();
            if (set.next()) {
                finalLocal = this.createObjectLocal(set);
                if (set.next()) {
                    finalLocal = null;
                }
            }
            return finalLocal;
        } catch (SQLException e) {
            throw new DBComunicationException("Unable to find the requested local with the id " + e.getMessage(), e);
        } finally {
            this.dbManager.closeConnection(con, selectStm, set);
        }
    }

    @Override
    public Local updateSingleObject(Local old, Local newLocal) throws DBComunicationException {
        String updateQuery = "UPDATE local SET denumireLocal = ?, CUI = ?, oras = ?, oraInceputProgram = ?, oraSfarsitLocal = ?, tipLocal = ? WHERE idLocal = ?;";
        Connection con;
        PreparedStatement updateStm;
        try {
            con = this.dbManager.getConnection();
            updateStm = con.prepareStatement(updateQuery);
            updateStm.setString(1, newLocal.getDenumireLocal());
            updateStm.setInt(2, newLocal.getCui());
            updateStm.setString(3, newLocal.getOras());
            updateStm.setTime(4, newLocal.getOraInceputLocal());
            updateStm.setTime(5, newLocal.getOraSfarsitProgram());
            updateStm.setString(6, newLocal.getTipLocal());
            updateStm.setInt(7, old.getIdLocal());
            int affectedRows = updateStm.executeUpdate();
            if (affectedRows == 0) {
                return null;
            }
            return finById(old.getIdLocal());
        } catch (SQLException e) {
            throw new DBComunicationException("Failed in the process of updating a local", e);
        }
    }

    @Override
    public Local deleteSingleObjectFromDb(Local o) throws DBComunicationException, NoTheSameException{
        String deleteQuery = "DELETE FROM local WHERE idLocal = ?";
        Connection con = null;
        PreparedStatement prep = null;
        try{
            Local existingLocalInDb = this.finById(o.getIdLocal());
            Comparator <Local> comparator = Comparator.comparing(Local::getIdLocal).thenComparing(Local::getDenumireLocal)
                    .thenComparing(Local::getTipLocal).thenComparing(Local::getCui).thenComparing(Local::getOraInceputLocal).thenComparing(Local::getOras).thenComparing(Local::getOraSfarsitProgram);

            if(comparator.compare(existingLocalInDb, o)!=0){
                throw new NoTheSameException("unable to delete the object because there is no row which matches the object in question");
            }

            con = this.dbManager.getConnection();
            prep = con.prepareStatement(deleteQuery);
            prep.setInt(1, o.getIdLocal());
            int affecteRows = prep.executeUpdate();
            if(affecteRows !=1 ){
                throw new DBComunicationException("Something went wrong in the process of deleting a local");
            }
            return o;
        } catch (SQLException e) {
            throw new DBComunicationException("Error in the process of deleting a local "+e.getMessage(), e );
        }finally {
            this.dbManager.closeConnection(con, prep);
        }
    }
}
