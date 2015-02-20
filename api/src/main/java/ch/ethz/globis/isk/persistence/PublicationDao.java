package ch.ethz.globis.isk.persistence;

import ch.ethz.globis.isk.domain.Publication;

import java.util.List;

public interface PublicationDao extends Dao<String, Publication> {

    public Publication findOneByTitle(String title);

    public List<Publication> findByAuthorIdOrderedByYear(String authorId);

    public List<Publication> findByEditorIdOrderedByYear(String editorId);

    public List<Publication> findByPublisherOrderedByYear(String publisherId);

    public List<Publication> findBySchoolOrderedByYear(String schoolId);

    public List<Publication> findBySeriesOrderedByYear(String seriesId);
}