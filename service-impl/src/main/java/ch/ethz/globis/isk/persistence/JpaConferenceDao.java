package ch.ethz.globis.isk.persistence;

import ch.ethz.globis.isk.domain.Conference;
import ch.ethz.globis.isk.domain.jpa.JpaConference;
import ch.ethz.globis.isk.util.Filter;
import ch.ethz.globis.isk.util.Operator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class JpaConferenceDao extends JpaDao<String, Conference> implements ConferenceDao {

    @Autowired
    ProceedingsDao proceedingsDao;

    @Override
    protected Class<JpaConference> getStoredClass() {
        return JpaConference.class;
    }

    @Override
    public Conference createEntity() {
        return new JpaConference();
    }

    @Override
    public Conference findOneByName(String name) {
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("name", new Filter(Operator.EQUAL, name));
        return findOneByFilter(filterMap);
    }
}