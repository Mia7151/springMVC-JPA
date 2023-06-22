package com.example.springhw1.Dao;


import com.example.springhw1.Entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DocumentDao  {
    private Map<Integer, Document> documents = new HashMap<>();

    public Document CreateDocument(Document document) {
        if (documents.containsKey(document.getId())) {
            throw new IllegalArgumentException("The document is already existed!");
        }
        documents.put(document.getId(), document);
        return documents.get(document.getId());
    }

    public Document GetDocument(Integer id) {
        if (!documents.containsKey(id)) {
            throw new IllegalArgumentException("The document is not existed!");
        }
        return documents.get(id);
    }

    public List<Document> ListDocuments() {
        return documents.values().stream().toList();
    }

    public Document UpdateDocument(Integer id, String content) {
        if (!documents.containsKey(id)) {
            throw new IllegalArgumentException("The document is not existed!");
        }
        documents.get(id).setContent(content);
        return documents.get(id);
    }

    public void DeleteDocument(Integer id) {
        if (!documents.containsKey(id)) {
            throw new IllegalArgumentException("The document is not existed!");
        }
       documents.remove(id);
    }
}
