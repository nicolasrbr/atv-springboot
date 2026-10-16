package biblioteca.biblioteca.Repository;

import biblioteca.biblioteca.Model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

    @Query("""
            SELECT livro FROM Livro livro
            WHERE (:isbn IS NULL OR CAST(livro.isbn AS string) LIKE CONCAT('%', :isbn, '%'))
              AND (:titulo IS NULL OR LOWER(livro.titulo) LIKE LOWER(CONCAT('%', :titulo, '%')))
            """)
    List<Livro> buscarPorIsbnOuTitulo(
            @Param("isbn") String isbn,
            @Param("titulo") String titulo);
}