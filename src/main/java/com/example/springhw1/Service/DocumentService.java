package com.example.springhw1.Service;

import com.example.springhw1.Dao.DocumentDao;
import com.example.springhw1.Dao.DocumentRepository;
import com.example.springhw1.Entity.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.event.DocumentEvent;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentService {
    @Autowired
    DocumentDao documentDao;

    @Autowired
    private DocumentRepository documentRepository;

    public Document saveDocument(Document document){
        Optional<Document> optionalDocument = documentRepository.findById(document.getId());
        if (optionalDocument.isPresent()){
            throw new ResponseStatusException(
                   HttpStatus.CONFLICT, "Already Existed");
        }else{
            return documentRepository.save(document);
        }
    }


    public Optional<Document> getDocumentById(int id){
        Optional<Document> optionalDocument = documentRepository.findById(id);
        if (optionalDocument.isPresent()){
            return documentRepository.findById(id);

        }else{
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Not existed"
            );
        }
    }


    public List<Document> getAllDocuments(){
        return documentRepository.findAll();
    }


    public Document updateDocument(int id, String content){
        Optional<Document> optionalDocument = documentRepository.findById(id);
        if (optionalDocument.isPresent()){
            Document document = optionalDocument.get();
            document.setContent(content);
            documentRepository.save(document);
            return document;
        }else{
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Not existed"
            );
        }
    }


    public void deleteDocument(int id){
        Optional<Document> optionalDocument = documentRepository.findById(id);
        if (optionalDocument.isPresent()){
            documentRepository.deleteById(id);

        }else{
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Not existed"
            );
        }

    }

}
