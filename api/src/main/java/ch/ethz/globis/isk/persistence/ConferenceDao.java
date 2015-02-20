package ch.ethz.globis.isk.persistence;

import ch.ethz.globis.isk.domain.Conference;

public interface ConferenceDao extends Dao<String, Conference> {

    public Conference findOneByName(String name);
}