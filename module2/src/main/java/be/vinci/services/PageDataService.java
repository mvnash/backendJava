package be.vinci.services;

import be.vinci.domain.Page;
import be.vinci.domain.User;

import java.util.List;

public interface PageDataService {

    List<Page> getAll();

    List<Page> getAll(User authenticatedUser);

    Page getOne(int id);

    Page getOne(int id, User authenticatedUser);

    Page createOne(Page page, User authenticatedUser);

    Page deleteOne(int id, User authenticatedUser);

    Page updateOne(Page page, int id, User authenticatedUser);

    int nextPageId();
}
