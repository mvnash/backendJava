package be.vinci.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = UserImpl.class)
public interface User {
    String getLogin();

    void setLogin(String login);

    int getId();

    void setId(int id);

    String getPassword();

    void setPassword(String password);

    Integer getAge();

    void setAge(Integer age);

    Boolean isMarried();

    void setMarried(Boolean married);

    boolean checkPassword(String password);

    String hashPassword(String password);
}
