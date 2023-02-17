package be.vinci.domain;

import be.vinci.views.Views;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import org.mindrot.jbcrypt.BCrypt;

@JsonInclude(JsonInclude.Include.NON_DEFAULT) // ignore all default fields in order to avoid sending props not linked to a JSON view
class UserImpl implements User {
    @JsonView(Views.Public.class)
    private int id;
    @JsonView(Views.Public.class)
    private String login;
    @JsonView(Views.Internal.class)
    private String password;
    @JsonView(Views.Internal.class)
    private Integer age;
    @JsonView(Views.Public.class)
    private Boolean married ;

    @Override
    public Integer getAge() {
        return age;
    }

    @Override
    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public Boolean isMarried() {
        return married;
    }

    @Override
    public void setMarried(Boolean married) {
        this.married = married;
    }

    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)



    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean checkPassword(String password) {
        return BCrypt.checkpw(password, this.password);
    }

    @Override
    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    @Override
    public String toString() {
        return "{id:" + id + ", login:" + login + ", password:" + password + "}";
    }

}
