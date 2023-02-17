package be.vinci.services;

import be.vinci.domain.DomainFactory;
import be.vinci.domain.User;
import be.vinci.services.utils.Json;
import be.vinci.utils.Config;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.inject.Inject;

import java.util.List;

public class UserDataServiceImpl implements UserDataService {
    private final static String SECRET = "ilovefilms";
    private static final String COLLECTION_NAME = "users";
    private static Json<User> jsonDB = new Json<>(User.class);
    @Inject
    private DomainFactory myDomainFactory;
    private final Algorithm jwtAlgorithm = Algorithm.HMAC256(SECRET);
    private final ObjectMapper jsonMapper = new ObjectMapper();

    @Override
    public List<User> getAll() {
        var items = jsonDB.parse(COLLECTION_NAME);
        return jsonDB.filterPublicJsonViewAsList(items);
    }


    @Override
    public User getOne(int id) {
        var items = jsonDB.parse(COLLECTION_NAME);
        return items.stream().filter(item -> item.getId() == id).findAny().orElse(null);
    }

    @Override
    public User getOne(String login) {
        var items = jsonDB.parse(COLLECTION_NAME);
        return items.stream().filter(item -> item.getLogin().equals(login)).findAny().orElse(null);
    }

    @Override
    public User createOne(User item) {
        var items = jsonDB.parse(COLLECTION_NAME);
        item.setId(nextItemId());
        items.add(item);
        jsonDB.serialize(items, COLLECTION_NAME);
        return item;
    }

    @Override
    public int nextItemId() {
        var items = jsonDB.parse(COLLECTION_NAME);
        if (items.size() == 0)
            return 1;
        return items.get(items.size() - 1).getId() + 1;
    }

    @Override
    public ObjectNode login(String login, String password) {
        User user = getOne(login);
        if (user == null || !user.checkPassword(password))
            return null;
        String token;
        try {
            token = JWT.create().withIssuer("auth0")
                    .withClaim("user", user.getId()).sign(this.jwtAlgorithm);
            ObjectNode publicUser = jsonMapper.createObjectNode()
                    .put("token", token)
                    .put("id", user.getId())
                    .put("login", user.getLogin());
            return publicUser;

        } catch (Exception e) {
            System.out.println("Unable to create token");
            return null;
        }
    }

    @Override
    public ObjectNode register(User user) {
        if (getOne(user.getLogin()) != null) // the user already exists !
            return null;

        user.setPassword(user.hashPassword(user.getPassword()));

        user = createOne(user); // add an id to the user and serialize it in db.json
        if (user == null)
            return null;
        String token;
        try {
            token = JWT.create().withIssuer("auth0")
                    .withClaim("user", user.getId()).sign(this.jwtAlgorithm);
            ObjectNode publicUser = jsonMapper.createObjectNode()
                    .put("token", token)
                    .put("id", user.getId())
                    .put("login", user.getLogin());
            return publicUser;

        } catch (Exception e) {
            System.out.println("Unable to create token");
            return null;
        }
    }

}
