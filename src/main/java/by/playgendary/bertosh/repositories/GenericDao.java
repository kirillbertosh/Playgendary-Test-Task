package by.playgendary.bertosh.repositories;

import by.playgendary.bertosh.exceptions.DatabaseException;
import by.playgendary.bertosh.exceptions.EntityNotFoundException;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T, PK extends Serializable> {

    T save(T object) throws DatabaseException;

    T update(T object) throws DatabaseException;

    void delete(T object) throws DatabaseException;

    List<T> findAll() throws EntityNotFoundException, DatabaseException;

    T findById(PK id) throws EntityNotFoundException, DatabaseException;

}

