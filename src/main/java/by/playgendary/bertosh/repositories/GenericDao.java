package by.playgendary.bertosh.repositories;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T, PK extends Serializable> {

    T save(T object);

    T update(T object);

    void delete(T object);

    List<T> findAll();

    T findById(PK id);

}

