package br.com.urlshortener.data;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PaginatedData<T> {
  private List<T> content;
  private Pagination pagination;
}
