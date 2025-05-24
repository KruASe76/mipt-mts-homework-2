package me.kruase.mipt.unit.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.kruase.mipt.api.controllers.BookController;
import me.kruase.mipt.api.models.request.BookCreateRequest;
import me.kruase.mipt.api.models.request.BookUpdateRequest;
import me.kruase.mipt.db.book.Book;
import me.kruase.mipt.db.book.exceptions.BookNotFoundException;
import me.kruase.mipt.services.BookService;
import me.kruase.mipt.util.SampleDataUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
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
        Long bookId = 1L;
        when(bookService.getRichById(bookId))
                .thenReturn(book);

        mockMvc.perform(get(basePath + "/" + bookId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(jsonContentType))
                .andExpect(jsonPath("$.title").value(book.getTitle()))
                .andExpect(jsonPath("$.author").value(book.getAuthor()))
                .andExpect(jsonPath("$.course.name").value(book.getCourse().getName()))
                .andExpect(jsonPath("$.course.credits").value(book.getCourse().getCredits()));
        verify(bookService, times(1)).getRichById(bookId);
    }

    @Test
    public void testGetBookNotFound() throws Exception {
        Long nonexistentBookId = 666L;

        when(bookService.getRichById(nonexistentBookId))
                .thenThrow(new BookNotFoundException(nonexistentBookId));

        mockMvc.perform(get(basePath + "/" + nonexistentBookId))
                .andExpect(status().isNotFound());
        verify(bookService, times(1)).getRichById(nonexistentBookId);
    }

    @Test
    public void testCreateBookCreated() throws Exception {
        BookCreateRequest createRequest = new BookCreateRequest(
                SampleDataUtil.NEW_BOOK.getTitle(),
                SampleDataUtil.NEW_BOOK.getAuthor(),
                1L
        );
        Book expectedBook = SampleDataUtil.NEW_BOOK;

        when(bookService.create(createRequest))
                .thenReturn(expectedBook);

        mockMvc.perform(
                        post(basePath)
                                .contentType(jsonContentType)
                                .content(objectMapper.writeValueAsString(createRequest))
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(jsonContentType))
                .andExpect(jsonPath("$.title").value(expectedBook.getTitle()))
                .andExpect(jsonPath("$.author").value(expectedBook.getAuthor()))
                .andExpect(jsonPath("$.course.name").value(expectedBook.getCourse().getName()))
                .andExpect(jsonPath("$.course.credits").value(expectedBook.getCourse().getCredits()));
        verify(bookService, times(1)).create(createRequest);
    }

    @Test
    public void testUpdateBookNoContent() throws Exception {
        BookUpdateRequest updateRequest = new BookUpdateRequest(
                1L,
                SampleDataUtil.NEW_BOOK.getTitle(),
                SampleDataUtil.NEW_BOOK.getAuthor(),
                1L
        );

        doNothing().when(bookService).update(updateRequest);

        mockMvc.perform(
                        put(basePath)
                                .contentType(jsonContentType)
                                .content(objectMapper.writeValueAsString(updateRequest))
                )
                .andExpect(status().isNoContent());
        verify(bookService, times(1)).update(updateRequest);
    }

    @Test
    public void testUpdateBookNotFound() throws Exception {
        BookUpdateRequest updateRequest = new BookUpdateRequest(
                666L,
                SampleDataUtil.NEW_BOOK.getTitle(),
                SampleDataUtil.NEW_BOOK.getAuthor(),
                666L
        );

        doThrow(BookNotFoundException.class).when(bookService).update(updateRequest);

        mockMvc.perform(
                        put(basePath)
                                .contentType(jsonContentType)
                                .content(objectMapper.writeValueAsString(updateRequest))
                )
                .andExpect(status().isNotFound());
        verify(bookService, times(1)).update(updateRequest);
    }

    @Test
    public void testDeleteBookNoContent() throws Exception {
        Long bookId = 1L;

        doNothing().when(bookService).delete(bookId);

        mockMvc.perform(delete(basePath + "/" + bookId))
                .andExpect(status().isNoContent());
        verify(bookService, times(1)).delete(bookId);
    }

    @Test
    public void testDeleteBookNotFound() throws Exception {
        Long bookId = 666L;

        doThrow(BookNotFoundException.class).when(bookService).delete(bookId);

        mockMvc.perform(delete(basePath + "/" + bookId))
                .andExpect(status().isNotFound());
        verify(bookService, times(1)).delete(bookId);
    }
}
