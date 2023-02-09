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
    void testCreate() {

    }

    @Test
    void testFindByNome() {

    }

    @Test
    void testIndex() throws Exception {
        String expected = "[{\"id\":1,\"nome\":\"Marcos\",\"dataDeNascimento\":\"14/06/1999\",\"enderecos\":[{\"id\":1,\"principal\":true,\"logradouro\":\"Rua Ana Camelo da Silva\",\"cep\":\"51111-040\",\"numero\":\"256\",\"cidade\":\"Recife\"}]}]";

        // setting addresses
        Address add1 = new Address();
        add1.setCep("51111-040");
        add1.setCidade("Recife");
        add1.setLogradouro("Rua Ana Camelo da Silva");
        add1.setNumero("256");
        add1.setPrincipal(true);

        // setting people
        Person marcos = new Person();
        List<Address> marcosEnd = Arrays.asList(new Address[] {add1});

        marcos.setDataDeNascimento("14/06/1999");
        marcos.setEnderecos(marcosEnd);
        marcos.setNome("Marcos");

        // setting json body
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(marcos);

        RequestBuilder postReq = MockMvcRequestBuilders.post("/pessoas")
            .contentType(APPLICATION_JSON_UTF8)
            .content(requestJson);

        mock.perform(postReq).andExpectAll(status().isOk());

        // asserting 
        RequestBuilder request = MockMvcRequestBuilders.get("/pessoas");
        MvcResult result = mock.perform(request).andReturn();
        assertEquals(expected, result.getResponse().getContentAsString());
    }
}
