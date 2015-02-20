package ch.ethz.globis.isk.persistence;

import ch.ethz.globis.isk.domain.Person;

public interface PersonDao extends Dao<String, Person> {

    public Person findOneByName(String name);
}