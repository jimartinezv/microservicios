package co.edu.uniquindio.biblioteca.repo;

import co.edu.uniquindio.biblioteca.entity.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PrestamoRepo extends JpaRepository<Prestamo, Long> {

    @Query("select p from Prestamo p join Cliente c where c.codigo=:codigo and p.activo=true")
    List<Prestamo> listarPrestamosByCliente(long codigo);

    @Query("select p from Prestamo p where p.fechaPrestamo=:fecha and p.activo=true")
    List<Prestamo> listarPrestamosByDate(LocalDate fecha);

    @Query("select count(p) from Prestamo p join Libro l where p.activo=true and l.isbn=:isbn")
    Integer contarPrestamoByLibro(String isbn);
}
