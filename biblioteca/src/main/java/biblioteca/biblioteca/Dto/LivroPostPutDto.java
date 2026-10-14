package biblioteca.biblioteca.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LivroPostPutDto {
    @JsonProperty("isbn")
    @NotBlank(message = "ISBN do livro não pode ser vazio")
    private Long isbn;

    @JsonProperty("titulo")
    @NotBlank(message = "Título do livro não pode ser vazia")
    private String titulo;

    @JsonProperty("ano")
    @NotBlank(message = "Ano do livro não pode ser vazio")
    private int ano;

    @JsonProperty("edicao")
    @NotBlank(message = "Edição do livro não pode ser vazia")
    private int edicao;
}