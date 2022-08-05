package lk.barclays.hostelManagementSystem.dao;

import java.util.ArrayList;

public interface CrudDAO<T> extends SuperDAO{

    ArrayList<T> getAll();
    boolean save(T entity);
    boolean delete(String id);
    boolean update(T entity);
    T getSpecificEntity(String id);
}
