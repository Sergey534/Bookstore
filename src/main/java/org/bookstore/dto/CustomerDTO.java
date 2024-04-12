package org.bookstore.dto;


import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.bookstore.model.Book;

@Getter
@Setter
public class CustomerDTO {

  private Integer id;
  private String name;
  private List<Book> books;

}