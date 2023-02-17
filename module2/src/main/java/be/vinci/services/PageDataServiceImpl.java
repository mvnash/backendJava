package be.vinci.services;

import be.vinci.domain.Page;
import be.vinci.domain.User;
import org.apache.commons.text.StringEscapeUtils;

import java.util.List;

public class PageDataServiceImpl implements PageDataService {

  private static final String COLLECTION_NAME = "pages";
  private static Json<Page> jsonDB = new Json<>(Page.class);

  @Override
  public List<Page> getAll() {
    var pages = jsonDB.parse(COLLECTION_NAME);
    return pages.stream().filter(item ->
            item.getStatutPub().contentEquals("published"))
        .toList();

  }

  @Override
  public List<Page> getAll(User authenticatedUser) {
    var pages = jsonDB.parse(COLLECTION_NAME);
    return pages.stream().filter(item -> item.getStatutPub().contentEquals("published")
        || item.getAuteurId() == authenticatedUser.getId()).toList();

  }

  @Override
  public Page getOne(int id) {
    var pages = jsonDB.parse(COLLECTION_NAME);
    return pages.stream().filter(item -> (item.getId() == id)
            && (item.getStatutPub().contentEquals("published")))
        .findAny().orElse(null);
  }

  @Override
  public Page getOne(int id, User authenticatedUser) {
    var pages = jsonDB.parse(COLLECTION_NAME);
    return pages.stream().filter(item -> (item.getId() == id)
            && (item.getStatutPub().contentEquals("published")
            || item.getAuteurId() == authenticatedUser.getId()))
        .findAny().orElse(null);
  }

  @Override
  public Page createOne(Page page, User authenticatedUser) {
    var pages = jsonDB.parse(COLLECTION_NAME);
    page.setId(nextPageId());
    // escape dangerous chars to protect against XSS attacks
    page.setTitre(StringEscapeUtils.escapeHtml4(page.getTitre()));
    page.setUri(StringEscapeUtils.escapeHtml4(page.getUri()));
    page.setContenu(StringEscapeUtils.escapeHtml4(page.getContenu()));
    page.setAuteurId(authenticatedUser.getId());
    pages.add(page);

    jsonDB.serialize(pages, COLLECTION_NAME);
    return page;
  }


  @Override
  public Page deleteOne(int id, User authenticatedUser) {
    Page pageToDelete = getOne(id, authenticatedUser);
    var pages = jsonDB.parse(COLLECTION_NAME);
      if (pageToDelete == null) {
          return null;
      }
      if (pageToDelete.getAuteurId() != authenticatedUser.getId()) {
          throw new IllegalStateException("Forbidden");
      }
    pages.remove(pageToDelete);
    jsonDB.serialize(pages, COLLECTION_NAME);
    return pageToDelete;
  }

  @Override
  public Page updateOne(Page page, int id, User authenticatedUser) {
    Page pageToUpdate = getOne(id, authenticatedUser);
    var pages = jsonDB.parse(COLLECTION_NAME);

      if (pageToUpdate == null) {
          return null;
      }
      if (pageToUpdate.getAuteurId() != authenticatedUser.getId()) {
          throw new IllegalStateException("Forbidden");
      }

    pageToUpdate.setId(id);
      if (pageToUpdate == null) {
          return null;
      }
    // escape dangerous chars to protect against XSS attacks
      if (page.getTitre() != null) {
          pageToUpdate.setTitre(StringEscapeUtils.escapeHtml4(page.getTitre()));
      }
      if (page.getUri() != null) {
          pageToUpdate.setUri(StringEscapeUtils.escapeHtml4(page.getUri()));
      }
      if (page.getContenu() != null) {
          pageToUpdate.setContenu(StringEscapeUtils.escapeHtml4(page.getContenu()));
      }
      if (page.getAuteurId() != 0) {
          pageToUpdate.setAuteurId(page.getAuteurId());
      }
      if (page.getStatutPub() != null) {
          pageToUpdate.setStatutPub(page.getStatutPub());
      }
    pages.remove(page); // thanks to equals(), pages is found via its id
    pages.add(pageToUpdate);
    jsonDB.serialize(pages, COLLECTION_NAME);
    return pageToUpdate;
  }

  @Override
  public int nextPageId() {
    var pages = jsonDB.parse(COLLECTION_NAME);
      if (pages.size() == 0) {
          return 1;
      }
    return pages.get(pages.size() - 1).getId() + 1;
  }
}
