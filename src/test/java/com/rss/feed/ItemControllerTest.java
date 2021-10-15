package com.rss.feed;

import com.rss.feed.controller.ItemController;
import com.rss.feed.service.ItemServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemController.class)
@AutoConfigureMockMvc
@Import({
        ItemServiceImpl.class
})
@AutoConfigureDataJpa
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureTestEntityManager
@Transactional
public class ItemControllerTest {

    // region private fields
    @Autowired
    private MockMvc mockMvc;
    // end region

    // region public methods
    @Test
    public void testGetItemsEndpointAndReturnOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/items")).andExpect(status().isOk());
    }

    @Test
    @Sql(scripts = "/sql/insert_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testGet10NewestItemsAndReturnOk() throws Exception {
        String expected = """
                [{"id":2,"title":"Title2","link":"https://test2.com","publicationDate":"2021-10-14T04:12:55.000+00:00"},{"id":1,"title":"Title1","link":"https://test1.com","publicationDate":"2021-10-13T04:02:52.000+00:00"},{"id":6,"title":"Title6","link":"https://test6.com","publicationDate":"2021-10-12T07:00:00.000+00:00"},{"id":5,"title":"Title5","link":"https://test5.com","publicationDate":"2021-10-11T01:35:45.000+00:00"},{"id":7,"title":"Title7","link":"https://test7.com","publicationDate":"2021-10-10T19:20:38.000+00:00"},{"id":8,"title":"Title8","link":"https://test8.com","publicationDate":"2021-10-10T09:50:52.000+00:00"},{"id":9,"title":"Title9","link":"https://test9.com","publicationDate":"2021-10-10T04:05:44.000+00:00"},{"id":4,"title":"Title4","link":"https://test4.com","publicationDate":"2021-10-09T05:05:52.000+00:00"},{"id":3,"title":"Title3","link":"https://test3.com","publicationDate":"2021-10-05T08:10:03.000+00:00"},{"id":11,"title":"Title11","link":"https://test11.com","publicationDate":"2021-10-01T05:03:00.000+00:00"}]""";
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.get("/items"));
        String actual = perform.andReturn().getResponse().getContentAsString();

        Assertions.assertEquals(expected, actual);
        perform.andExpect(status().isOk());
    }

    @Test
    @Sql(scripts = "/sql/insert_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testGetSecondPagedItemsSortedByPublicationDateInAscOrderAndReturnOk() throws Exception {
        int page = 1;
        int size = 5;
        String direction = "asc";
        String expected = """
                [{"id":8,"title":"Title8","link":"https://test8.com","publicationDate":"2021-10-10T09:50:52.000+00:00"},{"id":7,"title":"Title7","link":"https://test7.com","publicationDate":"2021-10-10T19:20:38.000+00:00"},{"id":5,"title":"Title5","link":"https://test5.com","publicationDate":"2021-10-11T01:35:45.000+00:00"},{"id":6,"title":"Title6","link":"https://test6.com","publicationDate":"2021-10-12T07:00:00.000+00:00"},{"id":1,"title":"Title1","link":"https://test1.com","publicationDate":"2021-10-13T04:02:52.000+00:00"}]""";
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.get("/items")
                .param("page", String.valueOf(page))
                .param("size", String.valueOf(size))
                .param("direction", direction));
        String actual = perform.andReturn().getResponse().getContentAsString();

        Assertions.assertEquals(expected, actual);
        perform.andExpect(status().isOk());
    }
    // end region
}
