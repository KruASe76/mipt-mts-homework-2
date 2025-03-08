package me.kruase.mipt.unit.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.kruase.mipt.api.controllers.BookController;
import me.kruase.mipt.api.models.request.BookCreateRequest;
import me.kruase.mipt.api.models.request.BookUpdateRequest;
import me.kruase.mipt.db.book.Book;
import me.kruase.mipt.db.book.exceptions.BookNotFoundException;
import me.kruase.mipt.logic.services.BookService;
import me.kruase.mipt.util.SampleDataUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = BookController.class,
        excludeAutoConfiguration = {DataSourceAutoConfiguration.class, SecurityAutoConfiguration.class}
)
class BookControllerMvcTest {
    private static final String basePath = "/api/v1/book";
    private static final MediaType jsonContentType = MediaType.valueOf("application/json");

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private BookService bookService;


    @Test
    public void testGetBookSuccess() throws Exception {
        Book book = SampleDataUtil.DEFAULT_BOOK;
        when(bookService.getById(book.id()))
                .thenReturn(book);

        mockMvc.perform(get(basePath + "/" + book.id()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(jsonContentType))
                .andExpect(jsonPath("$.id").value(book.id()))
                .andExpect(jsonPath("$.title").value(book.title()))
                .andExpect(jsonPath("$.author").value(book.author()))
                .andExpect(jsonPath("$.courseId").value(book.courseId()));
        verify(bookService).getById(book.id());
    }

    @Test
    public void testGetBookNotFound() throws Exception {
        Long nonexistentBookId = SampleDataUtil.NEW_BOOK.id();

        when(bookService.getById(nonexistentBookId))
                .thenThrow(new BookNotFoundException(nonexistentBookId));

        mockMvc.perform(get(basePath + "/" + nonexistentBookId))
                .andExpect(status().isNotFound());
        verify(bookService).getById(nonexistentBookId);
    }

    @Test
    public void testCreateBookCreated() throws Exception {
        BookCreateRequest createRequest = new BookCreateRequest(
                SampleDataUtil.NEW_BOOK.title(),
                SampleDataUtil.NEW_BOOK.author(),
                SampleDataUtil.NEW_BOOK.courseId()
        );
        Book expectedBook = SampleDataUtil.NEW_BOOK.withId(SampleDataUtil.DEFAULT_BOOK.id());

        when(bookService.create(any(BookCreateRequest.class)))
                .thenReturn(expectedBook);

        mockMvc.perform(
                        post(basePath)
                                .contentType(jsonContentType)
                                .content(objectMapper.writeValueAsString(createRequest))
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(jsonContentType))
                .andExpect(jsonPath("$.id").value(expectedBook.id()))
                .andExpect(jsonPath("$.title").value(expectedBook.title()))
                .andExpect(jsonPath("$.author").value(expectedBook.author()))
                .andExpect(jsonPath("$.courseId").value(expectedBook.courseId()));
        verify(bookService).create(createRequest);
    }

    @Test
    public void testUpdateBookNoContent() throws Exception {
        BookUpdateRequest updateRequest = new BookUpdateRequest(
                SampleDataUtil.DEFAULT_BOOK.id(),
                SampleDataUtil.NEW_BOOK.title(),
                SampleDataUtil.NEW_BOOK.author(),
                SampleDataUtil.DEFAULT_BOOK.courseId()
        );

        doNothing().when(bookService).update(updateRequest);

        mockMvc.perform(
                        put(basePath)
                                .contentType(jsonContentType)
                                .content(objectMapper.writeValueAsString(updateRequest))
                )
                .andExpect(status().isNoContent());
        verify(bookService).update(updateRequest);
    }



    @Test
    public void testUpdateBookNotFound() throws Exception {
        BookUpdateRequest updateRequest = new BookUpdateRequest(
                SampleDataUtil.NEW_BOOK.id(),
                SampleDataUtil.NEW_BOOK.title(),
                SampleDataUtil.NEW_BOOK.author(),
                SampleDataUtil.DEFAULT_BOOK.courseId()
        );

        doThrow(BookNotFoundException.class).when(bookService).update(updateRequest);

        mockMvc.perform(
                        put(basePath)
                                .contentType(jsonContentType)
                                .content(objectMapper.writeValueAsString(updateRequest))
                )
                .andExpect(status().isNotFound());
        verify(bookService).update(updateRequest);
    }

    @Test
    public void testDeleteBookNoContent() throws Exception {
        Long bookId = SampleDataUtil.DEFAULT_BOOK.id();

        doNothing().when(bookService).delete(bookId);

        mockMvc.perform(delete(basePath + "/" + bookId))
                .andExpect(status().isNoContent());
        verify(bookService).delete(bookId);
    }

    @Test
    public void testDeleteBookNotFound() throws Exception {
        Long bookId = SampleDataUtil.NEW_BOOK.id();

        doThrow(BookNotFoundException.class).when(bookService).delete(bookId);

        mockMvc.perform(delete(basePath + "/" + bookId))
                .andExpect(status().isNotFound());
        verify(bookService).delete(bookId);
    }
}
