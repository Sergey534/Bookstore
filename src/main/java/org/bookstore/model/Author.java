package org.bookstore.model;

import java.util.List;
import lombok.Data;

@Data
public class Author {

  private Integer id;
  private String name;
  private List<Book> books;

}