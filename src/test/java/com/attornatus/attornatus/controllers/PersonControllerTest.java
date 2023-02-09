package com.attornatus.attornatus.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.attornatus.attornatus.models.Person;
import com.attornatus.attornatus.models.Address;

import org.springframework.http.MediaType;
import java.nio.charset.Charset;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTest {

    @Autowired
    private MockMvc mock;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8")); 

    @Test
    void testCreate() throws Exception {
        List<Person> people = this.setPeople();

        // setting json body
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(people.get(0));

        RequestBuilder postReq = MockMvcRequestBuilders.post("/pessoas")
            .contentType(APPLICATION_JSON_UTF8)
            .content(requestJson);

        mock.perform(postReq).andExpect(status().isOk());
    }

    @Test
    void testFindByNome() throws Exception {
        String expected = "{\"id\":2,\"nome\":\"Victor\",\"dataDeNascimento\":\"22/09/1995\",\"enderecos\":[{\"id\":2,\"principal\":true,\"logradouro\":\"Rua Professor Augusto Lins e Silva\",\"cep\":\"51030-030\",\"numero\":\"80\",\"cidade\":\"Recife\"},{\"id\":3,\"principal\":false,\"logradouro\":\"Rua Pedro Paes Mendonca\",\"cep\":\"51111-202\",\"numero\":\"200\",\"cidade\":\"Recife\"}]}";

        List<Person> people = this.setPeople();

        // setting json body
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        // posting all people to my H2 databse
        String requestJson = ow.writeValueAsString(people.get(0));
        RequestBuilder postReq = MockMvcRequestBuilders.post("/pessoas")
            .contentType(APPLICATION_JSON_UTF8)
            .content(requestJson);

        mock.perform(postReq).andExpectAll(status().isOk());

        requestJson = ow.writeValueAsString(people.get(1));
        postReq = MockMvcRequestBuilders.post("/pessoas")
            .contentType(APPLICATION_JSON_UTF8)
            .content(requestJson);

        mock.perform(postReq).andExpectAll(status().isOk());

        // asserting
        RequestBuilder request = MockMvcRequestBuilders.get("/pessoas/Victor");
        MvcResult result = mock.perform(request).andReturn();
        assertEquals(expected, result.getResponse().getContentAsString());

    }

    @Test
    void testIndex() throws Exception {
        String expected = "[{\"id\":1,\"nome\":\"Marcos\",\"dataDeNascimento\":\"14/06/1999\",\"enderecos\":[{\"id\":1,\"principal\":true,\"logradouro\":\"Rua Ana Camelo da Silva\",\"cep\":\"51111-040\",\"numero\":\"256\",\"cidade\":\"Recife\"}]}]";

        // people[0] == marcos and people[1] == victor
        List<Person> people = this.setPeople();

        // setting json body
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(people.get(0));

        RequestBuilder postReq = MockMvcRequestBuilders.post("/pessoas")
            .contentType(APPLICATION_JSON_UTF8)
            .content(requestJson);

        mock.perform(postReq).andExpectAll(status().isOk());

        // asserting 
        RequestBuilder request = MockMvcRequestBuilders.get("/pessoas");
        MvcResult result = mock.perform(request).andReturn();
        assertEquals(expected, result.getResponse().getContentAsString());
    }

    public List<Person> setPeople() {
        // setting addresses
        Address add1 = new Address();
        add1.setCep("51111-040");
        add1.setCidade("Recife");
        add1.setLogradouro("Rua Ana Camelo da Silva");
        add1.setNumero("256");
        add1.setPrincipal(true);

        Address add2 = new Address();
        add2.setCep("51111-202");
        add2.setCidade("Recife");
        add2.setLogradouro("Rua Pedro Paes Mendonca");
        add2.setNumero("200");
        add2.setPrincipal(false);

        Address add3 = new Address();
        add3.setCep("51030-030");
        add3.setCidade("Recife");
        add3.setLogradouro("Rua Professor Augusto Lins e Silva");
        add3.setNumero("80");
        add3.setPrincipal(true);

        // setting people
        Person marcos = new Person();
        List<Address> marcosEnd = Arrays.asList(new Address[] {add1});

        marcos.setDataDeNascimento("14/06/1999");
        marcos.setEnderecos(marcosEnd);
        marcos.setNome("Marcos");

        Person victor = new Person();
        List<Address> victorAdd = Arrays.asList(new Address[] {add3, add2});

        victor.setDataDeNascimento("22/09/1995");
        victor.setEnderecos(victorAdd);
        victor.setNome("Victor");

        List<Person> returnList = Arrays.asList(new Person[] {marcos, victor});
        return returnList;
    }
}
