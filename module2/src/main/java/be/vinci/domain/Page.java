package be.vinci.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = PageImpl.class)
public interface Page {
    int getId();

    String getTitre();

    String getUri();

    String getContenu();

    String getStatutPub();

    void setId(int id);

    void setTitre(String titre);

    void setUri(String uri);

    void setContenu(String contenu);

    void setAuteurId(int auteurId);

    int getAuteurId();

    void setStatutPub(String statutPub);
}
