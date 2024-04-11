package org.example.domain;

import org.example.domain.local.Local;
import org.example.exceptions.AlreadyExistsException;
import org.example.exceptions.DBComunicationException;
import org.example.exceptions.MultipleOrNoEntityException;
import org.example.exceptions.NoTheSameException;

import java.util.List;

public interface GenericDao<T> {
    //returneaza o lista de obiecte din bd
    List<T> getAll() throws DBComunicationException;
    //returneaza obiectul dupa id
    List<T> findByName(String name) throws DBComunicationException, MultipleOrNoEntityException;
    //salveaza obiectul
    T saveObject(T t) throws DBComunicationException, AlreadyExistsException, MultipleOrNoEntityException;
    //updateaza obiectul
    T updateSingleObject(T t, T n) throws MultipleOrNoEntityException, DBComunicationException;
    // sterge obiectul
    T deleteSingleObjectFromDb(T t) throws DBComunicationException, NoTheSameException;
    //stergeToateObiectele

}
