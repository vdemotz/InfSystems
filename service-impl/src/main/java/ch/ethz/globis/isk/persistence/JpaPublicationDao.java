package ch.ethz.globis.isk.persistence;

import ch.ethz.globis.isk.domain.Publication;
import ch.ethz.globis.isk.domain.jpa.JpaPublication;
import ch.ethz.globis.isk.util.Filter;
import ch.ethz.globis.isk.util.Operator;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JpaPublicationDao extends JpaDao<String, Publication> implements PublicationDao {

    @Override
    protected Class<JpaPublication> getStoredClass() {
        return JpaPublication.class;
    }

    @Override
    public Publication createEntity() {
        return new JpaPublication();
    }

    @Override
    public Publication findOneByTitle(String title) {
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("title", new Filter(Operator.EQUAL, title));
        return findOneByFilter(filterMap);
    }

    @Override
    public List<Publication> findByAuthorIdOrderedByYear(String authorId) {
        return queryByReferenceIdOrderByYear("Publication", "authors", authorId);
    }

    @Override
    public List<Publication> findByEditorIdOrderedByYear(String editorId) {
        return queryByReferenceIdOrderByYear("Publication", "editors", editorId);
    }

    @Override
    public List<Publication> findByPublisherOrderedByYear(String publisherId) {
        return queryByReferenceIdOrderByYear("Publication", "publisher", publisherId);
    }

    @Override
    public List<Publication> findBySchoolOrderedByYear(String schoolId) {
        return queryByReferenceIdOrderByYear("Publication", "school", schoolId);
    }

    @Override
    public List<Publication> findBySeriesOrderedByYear(String seriesId) {
        return queryByReferenceIdOrderByYear("Publication", "series", seriesId);
    }
}