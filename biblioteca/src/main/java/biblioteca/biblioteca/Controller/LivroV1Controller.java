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
    public ResponseEntity<List<LivroResponseDto>> buscarTodosLivros() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(livroCrudService.buscarTodosLivros());
    }

    @GetMapping(value = "", params = "titulo")
    public ResponseEntity<LivroResponseDto> buscarLivro(@RequestParam String titulo) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(livroCrudService.buscarLivro(titulo));
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<LivroResponseDto> buscarLivro(@PathVariable Long isbn) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(livroCrudService.buscarLivro(isbn));
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
