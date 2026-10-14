package biblioteca.biblioteca.Service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import biblioteca.biblioteca.Dto.LivroPostPutDto;
import biblioteca.biblioteca.Dto.LivroResponseDto;
import biblioteca.biblioteca.Model.Livro;
import biblioteca.biblioteca.Repository.LivroRepository;

@Service
public class LivroCrudPadraoService implements LivroCrudService{

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    LivroRepository livroRepository;


    @Override
    public LivroResponseDto criarLivro(LivroPostPutDto livroPostPutDto) {
        Livro livro = modelMapper.map(livroPostPutDto, Livro.class);
        return modelMapper.map(livroRepository.save(livro), LivroResponseDto.class);
    }

    @Override
    public List<LivroResponseDto> buscarTodosLivros() {
        return livroRepository.findAll()
                .stream()
                .map(livro -> modelMapper.map(livro, LivroResponseDto.class))
                .toList();
    }
    
    @Override
    public LivroResponseDto buscarLivro(Long isbn) {
        return modelMapper.map(obterLivroExistente(isbn), LivroResponseDto.class);
    }

    @Override
    public LivroResponseDto buscarLivro(String titulo) {
        return modelMapper.map(obterLivroExistente(titulo), LivroResponseDto.class);
    }

    @Override
    public LivroResponseDto atualizarLivro(Long isbn, LivroPostPutDto livroPostPutDto) {
        Livro livro = obterLivroExistente(isbn);
        livro.setTitulo(livroPostPutDto.getTitulo());
        livro.setAno(livroPostPutDto.getAno());
        livro.setEdicao(livroPostPutDto.getEdicao());
        return modelMapper.map(livroRepository.save(livro), LivroResponseDto.class);
    }

    @Override
    public void removerLivro(Long isbn) {
        livroRepository.delete(obterLivroExistente(isbn));
    }

    private Livro obterLivroExistente(Long isbn) {
        return livroRepository.findById(isbn)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado"));
    }

    private Livro obterLivroExistente(String titulo) {
        //Preciso implementar
        return null;
    }
}
