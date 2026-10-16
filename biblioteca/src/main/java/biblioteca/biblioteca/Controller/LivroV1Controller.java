package biblioteca.biblioteca.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import biblioteca.biblioteca.Dto.LivroPostPutDto;
import biblioteca.biblioteca.Dto.LivroResponseDto;
import biblioteca.biblioteca.Service.LivroCrudService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/v1/livros", produces = MediaType.APPLICATION_JSON_VALUE)
public class LivroV1Controller {
    
    @Autowired
    LivroCrudService livroCrudService;

    @PostMapping("")
    public ResponseEntity<LivroResponseDto> criarLivro(
            @RequestBody @Valid LivroPostPutDto livroPostPutDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(livroCrudService.criarLivro(livroPostPutDto));
    }

    @GetMapping("")
    public ResponseEntity<List<LivroResponseDto>> buscarLivro(
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) String titulo) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(livroCrudService.buscarLivro(isbn, titulo));
    }

    @PutMapping("/{isbn}")
    public ResponseEntity<LivroResponseDto> atualizarLivro(
            @PathVariable Long isbn,
            @RequestBody @Valid LivroPostPutDto livroPostPutDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(livroCrudService.atualizarLivro(isbn, livroPostPutDto));
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<Void> removerLivro(@PathVariable Long isbn) {
        livroCrudService.removerLivro(isbn);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
