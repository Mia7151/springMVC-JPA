package com.example.springhw1;

import com.example.springhw1.Dao.DocumentRepository;
import com.example.springhw1.Entity.Document;
import com.example.springhw1.Service.DocumentService;
import org.hibernate.annotations.NotFound;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
class SpringHw1ApplicationTests {

	@MockBean
	DocumentRepository dao;

	@MockBean
	DocumentService documentService;

	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	@Autowired
	private MockMvc mockMvc;

	private Document document;



	@Test
	void contextLoads() {


	}

	//for get all
	@Test
	public void testContextLoads1() throws Exception{
		List<Document> documentList = new ArrayList<>();
		document = new Document();
		document.setId(10);
		document.setContent("test10");

		documentList.add(document);
		when(dao.findAll()).thenReturn(documentList);

		mockMvc.perform(MockMvcRequestBuilders.get("/documents"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json("[{\"id\": 10,\"content\": \"test10\"}]"));

		System.out.println("test passed!");
	}

	// find by id
	@Test
	public void testContextLoads2() throws Exception{
		document = new Document();
		document.setId(10);
		document.setContent("test10");

		when(dao.findById(10)).thenReturn(Optional.ofNullable(document));
		mockMvc.perform(MockMvcRequestBuilders.get("/document/{id}",10))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json("{\"id\": 10,\"content\": \"test10\"}"));

		Mockito.when(dao.findById(111)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Not existed"));
		mockMvc.perform(MockMvcRequestBuilders.get("/document/{id}",111))
						.andExpect(MockMvcResultMatchers.status().isNotFound());

		System.out.println("test passed!");
	}


	// save
	@Test
	public void testContextLoads3() throws Exception {
		document = new Document();
		document.setId(10);
		document.setContent("test10");

		when(dao.save(document)).thenReturn(document);

		String json = "{\"id\": 10,\"content\": \"test10\"}";
		mockMvc.perform(MockMvcRequestBuilders.post("/documents")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json("{\"id\": 10,\"content\": \"test10\"}"));


		Mockito.when(dao.save(document)).thenThrow(new ResponseStatusException(HttpStatus.CONFLICT, "Not existed"));
		mockMvc.perform(MockMvcRequestBuilders.post("/documents")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(MockMvcResultMatchers.status().isConflict());

		System.out.println("test passed!");

	}

	// save
	@Test
	public void testContextLoads4() throws Exception {
		document = new Document();
		document.setId(10);
		document.setContent("test10");

		when(documentService.updateDocument(10,"test10")).thenReturn(document);

		String json = "{\"id\": 10,\"content\": \"test10\"}";
		mockMvc.perform(MockMvcRequestBuilders.post("/document/{id}",10)
						.contentType(MediaType.APPLICATION_JSON)
						.content(json))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json("{\"id\": 10,\"content\": \"test10\"}"));


		Mockito.when(documentService.updateDocument(10,"test10")).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Not exist"));
		mockMvc.perform(MockMvcRequestBuilders.post("/document/{id}",10)
						.contentType(MediaType.APPLICATION_JSON)
						.content(json))
				.andExpect(MockMvcResultMatchers.status().isNotFound());

		System.out.println("test passed!!");


	}


	// delete
	@Test
	public void testContextLoads5() throws Exception {


		mockMvc.perform(MockMvcRequestBuilders.delete("/document/{id}",10))
				.andExpect(MockMvcResultMatchers.status().isOk());

		
		doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Not exist"))
				.when(documentService)
				.deleteDocument(10);

		mockMvc.perform(MockMvcRequestBuilders.delete("/document/{id}",10))
				.andExpect(MockMvcResultMatchers.status().isNotFound());

		System.out.println("delete test passed");


	}



}
