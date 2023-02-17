package be.vinci.domain;

import java.util.Arrays;

class PageImpl implements Page {
    private int id;
    private String titre;
    private String uri;
    private String contenu;
    private int auteurId;
    private final static String[] POSSIBLE_PUBLICATION_STATUSES = {"hidden", "published"};
    private String statutPub;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getTitre() {
        return titre;
    }

    @Override
    public String getUri() {
        return uri;
    }

    @Override
    public String getContenu() {
        return contenu;
    }

    @Override
    public String getStatutPub() {
        return statutPub;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setTitre(String titre) {
        this.titre = titre;
    }

    @Override
    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    @Override
    public void setAuteurId(int auteurId) {
        this.auteurId = auteurId;
    }

    @Override
    public int getAuteurId() {
        return auteurId;
    }

    @Override
    public void setStatutPub(String statutPub) {
        this.statutPub = Arrays.stream(POSSIBLE_PUBLICATION_STATUSES).filter(possibleStatus -> possibleStatus.equals(statutPub)).findFirst()
                .orElse(null);
    }
}
