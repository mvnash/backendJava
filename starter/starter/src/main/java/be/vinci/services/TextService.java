package be.vinci.services;

import be.vinci.domain.TexteADactylographier;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.commons.text.StringEscapeUtils;

import java.util.List;
import java.util.stream.Collectors;

public class TextService {


    private static final String COLLECTION_NAME = "texts";
    private static Json<TexteADactylographier> jsonDB = new Json<>(TexteADactylographier.class);
    public List<TexteADactylographier> getAllTexts( String level) {
        var texts = jsonDB.parse(COLLECTION_NAME);
        if (level.isBlank()) {
            return texts;
        }
        return texts.stream().filter(item -> item.getLevel().contentEquals(level))
                .collect(Collectors.toList());
    }


    public TexteADactylographier getOne(int id) {
        var texts = jsonDB.parse(COLLECTION_NAME);
        TexteADactylographier textFound = texts.stream().filter(text -> text.getId() == id).findAny().orElse(null);
        return textFound;
    }

    public TexteADactylographier createOne(TexteADactylographier texteADactylographier) {
        var texts = jsonDB.parse(COLLECTION_NAME);
        texteADactylographier.setId(nextTextId());
        texteADactylographier.setContent(texteADactylographier.getContent());
        texteADactylographier.setLevel(texteADactylographier.getLevel());
        texts.add(texteADactylographier);
        jsonDB.serialize(texts,COLLECTION_NAME);
        return texteADactylographier;
    }


    public TexteADactylographier deleteOne(int id) {
        var texts = jsonDB.parse(COLLECTION_NAME);
        TexteADactylographier textToDelete = texts.stream().filter(text -> text.getId() == id).findAny().orElse(null);
        texts.remove(textToDelete);
        jsonDB.serialize(texts,COLLECTION_NAME);
        return textToDelete;
    }

    public TexteADactylographier updateOne(TexteADactylographier text, int id) {

        var texts = jsonDB.parse(COLLECTION_NAME);
        TexteADactylographier textToUpdate = texts.stream().filter(t -> t.getId() == id).findAny().orElse(null);

        text.setId(id);
        text.setContent(text.getContent());
        text.setLevel(text.getLevel());
        texts.remove(text); // thanks to equals(), films is found via its id
        texts.add(text);
        jsonDB.serialize(texts,COLLECTION_NAME);
        return text;
    }

    public int nextTextId() {
        var texts = jsonDB.parse(COLLECTION_NAME);
        if (texts.size() == 0)
            return 1;
        return texts.get(texts.size() - 1).getId() + 1;
    }
}

