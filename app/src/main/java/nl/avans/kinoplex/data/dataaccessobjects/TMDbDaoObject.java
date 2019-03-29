package nl.avans.kinoplex.data.dataaccessobjects;

import java.util.concurrent.ExecutionException;

import nl.avans.kinoplex.domain.DomainObject;

public interface TMDbDaoObject extends DaoObject {

    DomainObject readCollection(String identifier, int page) throws ExecutionException, InterruptedException;

}
