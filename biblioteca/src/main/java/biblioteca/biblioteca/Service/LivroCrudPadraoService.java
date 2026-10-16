package biblioteca.biblioteca.Service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import biblioteca.biblioteca.Dto.LivroPostPutDto;
import biblioteca.biblioteca.Dto.LivroResponseDto;
import biblioteca.biblioteca.Model.Autor;
import biblioteca.biblioteca.Model.Livro;
import biblioteca.biblioteca.Repository.AutorRepository;
import biblioteca.biblioteca.Repository.LivroRepository;

@Service
public class LivroCrudPadraoService implements LivroCrudService{

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    LivroRepository livroRepository;

    @Autowired
    AutorRepository autorRepository;


    @Override
    public LivroResponseDto criarLivro(LivroPostPutDto livroPostPutDto) {
        Livro livro = modelMapper.map(livroPostPutDto, Livro.class);
        livro.setAutor(obterAutorExistente(livroPostPutDto.getAutorId()));
        return paraResponseDto(livroRepository.save(livro));
    }

    @Override
    public List<LivroResponseDto> buscarTodosLivros() {
        return livroRepository.findAll()
                .stream()
                .map(this::paraResponseDto)
                .toList();
    }
    
    @Override
    public List<LivroResponseDto> buscarLivro(String isbn, String titulo) {
        if (isbn != null && isbn.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe um trecho do ISBN válido");
        }
        if (titulo != null && titulo.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe um trecho do título válido");
        }
        if (isbn == null && titulo == null) {
            return buscarTodosLivros();
        }

        return livroRepository.buscarPorIsbnOuTitulo(
                        isbn == null ? null : isbn.trim(),
                        titulo == null ? null : titulo.trim())
                .stream()
                .map(this::paraResponseDto)
                .toList();
    }

    @Override
    public LivroResponseDto atualizarLivro(Long isbn, LivroPostPutDto livroPostPutDto) {
        Livro livro = obterLivroExistente(isbn);
        livro.setTitulo(livroPostPutDto.getTitulo());
        livro.setAno(livroPostPutDto.getAno());
        livro.setEdicao(livroPostPutDto.getEdicao());
        livro.setAutor(obterAutorExistente(livroPostPutDto.getAutorId()));
        return paraResponseDto(livroRepository.save(livro));
    }

    @Override
    public void removerLivro(Long isbn) {
        livroRepository.delete(obterLivroExistente(isbn));
    }

    private Livro obterLivroExistente(Long isbn) {
        return livroRepository.findById(isbn)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado"));
    }

    private Autor obterAutorExistente(Long autorId) {
        return autorRepository.findById(autorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Autor não encontrado"));
    }

    private LivroResponseDto paraResponseDto(Livro livro) {
        LivroResponseDto responseDto = modelMapper.map(livro, LivroResponseDto.class);
        responseDto.setAutorId(livro.getAutor().getId());
        return responseDto;
    }
}
