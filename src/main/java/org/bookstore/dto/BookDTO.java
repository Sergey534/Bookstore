package org.bookstore.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDTO {

  private Integer id;
  private String name;
  private Double price;
  private Integer authorId;

}