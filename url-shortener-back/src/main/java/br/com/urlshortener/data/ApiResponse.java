package br.com.urlshortener.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse {
  private String message;
  private Object data;

  /**
   *
   * @param data Dados para retornar
   * @return Objeto ApiResponse com status 200 e sem messagem com o paramametro data no corpo da resposta
   */
  public static ApiResponse of(Object data) {
    return new ApiResponse("", data);
  }

  /**
   *
   * @param status Status HTTP
   * @param message Mensagem resposta
   * @return Objeto ApiResponse com status 200 e sem messagem com o paramametro data no corpo da resposta
   */
  public static ApiResponse of(String message) {
    return new ApiResponse(message, null);
  }

  public static ApiResponse of(String message, Object data) {
    return new ApiResponse(message, data);
  }

}
