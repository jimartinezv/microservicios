package co.edu.uniquindio.biblioteca.servicios;

import co.edu.uniquindio.biblioteca.dto.ClienteGet;
import co.edu.uniquindio.biblioteca.dto.PrestamoDTO;
import co.edu.uniquindio.biblioteca.dto.PrestamoGet;
import co.edu.uniquindio.biblioteca.entity.Cliente;
import co.edu.uniquindio.biblioteca.entity.Libro;
import co.edu.uniquindio.biblioteca.entity.Prestamo;
import co.edu.uniquindio.biblioteca.repo.ClienteRepo;
import co.edu.uniquindio.biblioteca.repo.PrestamoRepo;
import co.edu.uniquindio.biblioteca.servicios.excepciones.ClienteNoEncontradoException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PrestamoServicio {

    private final PrestamoRepo prestamoRepo;
    private final ClienteRepo clienteRepo;

    public Prestamo save(PrestamoDTO prestamoDTO){

        long codigoCliente = prestamoDTO.clienteID();
        Optional<Cliente> consulta = clienteRepo.findById(codigoCliente);

        if(consulta.isEmpty()){
            throw new ClienteNoEncontradoException("No existe");
        }

        Prestamo prestamo = new Prestamo();
        prestamo.setCliente(consulta.get());
        prestamo.setFechaPrestamo(LocalDateTime.now());

        List<String> codigosLibros = prestamoDTO.isbnLibros();
        List<Libro> libros = new ArrayList<>();

        /*Optional<Libro> buscado libroRepo.findById(codigosLibros[0]);

        if(buscado.isEmpty()){
            throw new LibroNoExiste("El libro no existe");
        }

        libros.add( buscado );*/

        //TODO Completar la parte de los libros
        prestamo.setLibros(libros);
        prestamo.setFechaDevolucion(prestamoDTO.fechaDevolucion());

        return prestamoRepo.save(prestamo);
    }

    //Retorne la lista de préstamos realizados en una fecha dada.
    public List<PrestamoGet> findByDate(LocalDate fecha){
        return prestamoRepo.listarPrestamosByDate(fecha).stream()
                .map(p-> convertir(p))
                .collect(Collectors.toList());

    }


    //Retorne la lista de préstamos de un cliente dado su código.
    public List<PrestamoGet> findByCodigoCliente(long codigoCliente){

        return prestamoRepo.listarPrestamosByCliente(codigoCliente).stream()
                .map(p -> convertir(p))
                .collect(Collectors.toList());

    }

    private PrestamoGet convertir(Prestamo prestamo){
        //long clienteID, List<String> isbnLibros, LocalDateTime fechaDevolucion) {
        //return new PrestamoDTO(prestamo.getCliente().getCodigo(), prestamo.getLibros(),prestamo.getFechaDevolucion());
        return new PrestamoGet(prestamo.getCodigo(), prestamo.getFechaPrestamo(), prestamo.getFechaDevolucion(), prestamo.getLibros());
        //return new ClienteGet(cliente.getCodigo(), cliente.getNombre(), cliente.getEmail(), cliente.getTelefono());
    }

    //TODO usar DTO y la exepción propia de préstamo
    public Prestamo findById(long codigoPrestamo){
        return prestamoRepo.findById(codigoPrestamo).orElseThrow(()-> new RuntimeException("No existe"));
    }


}
