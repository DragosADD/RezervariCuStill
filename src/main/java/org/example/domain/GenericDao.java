package org.example.domain;

import java.sql.SQLException;
import java.util.List;

public interface GenericDao<T> {
    //returneaza o lista de obiecte din bd
    List<T> getAll() throws SQLException;
    //returneaza obiectul dupa id
    T findByName(String name) throws SQLException;
    //salveaza obiectul
    void saveObject(T t) throws SQLException;
    //updateaza obiectul
    void updateSingleObject(T t);
    // sterge obiectul
    void deleteSingleObjectFromDb(T t);
    //stergeToateObiectele

}
