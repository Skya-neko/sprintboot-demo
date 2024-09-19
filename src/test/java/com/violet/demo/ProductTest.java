package com.violet.demo;

import com.violet.demo.entity.Product;
import com.violet.demo.repository.ProductRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.exceptions.misusing.WrongTypeOfReturnValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.lang.invoke.WrongMethodTypeException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductTest {
    private HttpHeaders httpHeaders;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MockMvc mockMvc;

    @Before
    public void init() {
        httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    }

    @After
    public void clear() {
        productRepository.deleteAll();
    }

    @Test
    public void testCreateProduct() throws Exception {

        JSONObject request = new JSONObject()
                .put("name", "Harry Potter")
                .put("price", 450);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/products").headers(httpHeaders).content(request.toString());
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").hasJsonPath())
                .andExpect(jsonPath("$.name").value(request.getString("name")))
                .andExpect(jsonPath("$.price").value(request.getInt("price")))
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));

    }

    @Test
    public void testGetProduct() throws Exception {
        Product product = createProduct("Economics", 450);
        productRepository.insert(product);

        mockMvc.perform(get("/products/" + product.getId()).headers(httpHeaders))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(product.getId()))
                .andExpect(jsonPath("$.name").value(product.getName()))
                .andExpect(jsonPath("$.price").value(product.getPrice()));
    }

    @Test
    public void testSearchProductsSortByPriceAsc() throws Exception {
        Product p1 = createProduct("Operation Management", 350);
        Product p2 = createProduct("Marketing Management", 200);
        Product p3 = createProduct("Human Resource Management", 420);
        Product p4 = createProduct("Finance Management", 400);
        Product p5 = createProduct("Enterprise Resource Planning", 440);
        productRepository.insert(Arrays.asList(p1, p2, p3, p4, p5));

        MvcResult mvcResult = mockMvc.perform(get("/products")
                        .headers(httpHeaders)
                        .param("keyword", "Manage")
                        .param("orderBy", "price")
                        .param("sortRule", "asc"))
                .andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
        String payload = mockHttpServletResponse.getContentAsString();
        JSONArray productJsonArray = new JSONArray(payload);

        List<String> productIds = new ArrayList<>();
        for (int i = 0; i < productJsonArray.length(); i++) {
            productIds.add(productJsonArray.getJSONObject(i).getString("id"));
        }
        Assert.assertEquals(4, productIds.size());
        Assert.assertTrue(productIds.contains(p1.getId()));
        Assert.assertTrue(productIds.contains(p2.getId()));
        Assert.assertTrue(productIds.contains(p3.getId()));
        Assert.assertTrue(productIds.contains(p4.getId()));

        Assert.assertEquals(HttpStatus.OK.value(), mockHttpServletResponse.getStatus());
        Assert.assertEquals(MediaType.APPLICATION_JSON_VALUE, mockHttpServletResponse.getHeader(HttpHeaders.CONTENT_TYPE));

    }

    @Test
    public void testReplaceProduct() throws Exception {
        Product product = createProduct("Economics", 450);
        productRepository.insert(product);

        JSONObject request = new JSONObject().put("name", "Macroeconomics").put("price", 550);

        mockMvc.perform(put("/products/" + product.getId()).headers(httpHeaders).content(request.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(product.getId()))
                .andExpect(jsonPath("$.name").value(request.getString("name")))
                .andExpect(jsonPath("$.price").value(request.getInt("price")));


    }

    @Test(expected = RuntimeException.class)
    public void testDeleteProduct() throws Exception {
        Product product = createProduct("Economics", 450);
        productRepository.insert(product);
        mockMvc.perform(delete("/products/" + product.getId()).headers(httpHeaders))
                .andExpect(status().isNoContent());
        productRepository.findById(product.getId())
                .orElseThrow(RuntimeException::new);
    }

    private Product createProduct(String name, int price) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        return product;
    }
}
