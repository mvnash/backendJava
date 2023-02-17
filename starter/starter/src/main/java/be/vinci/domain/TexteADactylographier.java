package be.vinci.domain;

import java.util.Objects;

public class TexteADactylographier {

    private int id;

    private String content;

    private String level;

    public TexteADactylographier(){

    }

    public TexteADactylographier(int id, String content, String level) {
        this.id = id;
        this.content = content;

        if (level.equals("easy") || level.equals("medium") || level.equals("hard")) {
            this.level = level;
        } else {
            this.level = "";
        }

    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getLevel() {
        return level;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setLevel(String level) {
        if (level != null && (level.equals("easy") || level.equals("medium") || level.equals("hard"))) {
            this.level = level;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TexteADactylographier that = (TexteADactylographier) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "TexteADactylographier{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", level='" + level + '\'' +
                '}';
    }
}