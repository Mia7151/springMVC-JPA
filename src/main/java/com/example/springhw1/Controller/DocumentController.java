package com.example.springhw1.Controller;

import com.example.springhw1.Entity.Document;
import com.example.springhw1.Service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class DocumentController {
    @Autowired
    DocumentService service;

    @RequestMapping(value = "/documents", method = RequestMethod.POST, consumes = "application/json")
    public Document CreateDocument(@RequestBody Document document) {
        return service.saveDocument(document);
    }

    @GetMapping("/document/{id}")
    public Optional<Document> GetDocument(@PathVariable Integer id) {
        return service.getDocumentById(id);
    }


    @GetMapping("/documents")
    public List<Document> ListDocuments() {
        return service.getAllDocuments();
    }


    @PostMapping("/document/{id}")
    public Document UpdateDocument(@PathVariable Integer id, @RequestBody Document document) {
        return service.updateDocument(id, document.getContent());
    }

//    @DeleteMapping("/document/{id}")
//    public void DeleteDocument(@PathVariable Integer id) {
//        service.DeleteDocument(id);
//    }

    @DeleteMapping("/document/{id}")
    public void DeleteDocument(@PathVariable Integer id) {
        service.deleteDocument(id);
    }
}
