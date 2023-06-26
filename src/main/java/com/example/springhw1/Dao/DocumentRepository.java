package com.example.springhw1.Dao;

import com.example.springhw1.Entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface DocumentRepository extends JpaRepository<Document, Integer> {





}
