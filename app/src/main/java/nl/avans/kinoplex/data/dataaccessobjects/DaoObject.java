package nl.avans.kinoplex.data.dataaccessobjects;

public interface DaoObject<T> {

    boolean create(T t);
    T read();
    boolean update(T t);
    boolean delete(T t);
}
