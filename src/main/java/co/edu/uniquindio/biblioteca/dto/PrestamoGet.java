package co.edu.uniquindio.biblioteca.dto;

import co.edu.uniquindio.biblioteca.entity.Libro;

import java.time.LocalDateTime;
import java.util.List;

public record PrestamoGet(long codigo, LocalDateTime fechaPrestamo, LocalDateTime fechaDevolucion, List<Libro> libros) {
}
