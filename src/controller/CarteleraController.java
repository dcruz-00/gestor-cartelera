package controller;

import dao.CarteleraDAO;
import java.util.List;
import model.Cartelera;

/**
 * Controlador de la entidad Cartelera
 *
 * Actúa como intermediaria entre la UI y el DAO Se encarga de coordinar las
 * operaciones CRUD (crear, leer, actualizar, eliminar) Aplica validaciones
 * antes de delegar acciones
 */
public class CarteleraController {

    // Objeto de acceso a datos (DAO)
    private final CarteleraDAO dao = new CarteleraDAO();

    // Lista todas las peliculas
    public List<Cartelera> listar() {
        return dao.listar();
    }

    // Busca peliculas por nombre
    public List<Cartelera> buscarPorNombre(String patron) {
        if (patron == null) {
            patron = "";
        }

        patron = patron.trim().replaceAll("\\s{2,}", " ");

        return patron.isBlank() ? dao.listar() : dao.buscarPorNombre(patron);
    }

    //Busca peliculas por ID
    public Cartelera buscarPorId(Integer id) {
        if (id == null || id <= 0) {
            return null;
        }
        return dao.buscarPorId(id);
    }

    /**
     * Crea una nueva pelicula en la cartelera.
     *
     * @param titulo
     * @param director
     * @param anio
     * @param duracion
     * @param genero
     * @return
     */
    public String crear(String titulo, String director, Integer anio, int duracion, String genero) {

        // Valida el año recibido. 
        // Si la validación falla, el método "validar" devuelve un mensaje de error. 
        String v = validar(titulo, director, anio, duracion, genero);
        if (v != null) {
            return v;
        }

        // Si la validación fue correcta, se intenta crear la película
        return dao.crear(new Cartelera(titulo, director, anio, duracion, genero))
                ? "Pelicula creada."
                : "No se pudo crear la pelicula.";
    }

    /**
     * Actualiza una película existente en la cartelera.
     *
     * @param id
     * @param titulo
     * @param director
     * @param anio
     * @param duracion
     * @param genero
     * @return
     */
    public String actualizar(Integer id, String titulo, String director, Integer anio, int duracion, String genero) {

        // Verifica que se haya seleccionado un registro válido
        if (id == null) {
            return "Debe seleccionar una pelicula.";
        }

        // Valida los datos nuevos
        String v = validar(titulo, director, anio, duracion, genero);
        if (v != null) {
            return v;
        }

        // Intenta actualizar la película en la base de datos usando el DAO
        // Crea un nuevo objeto Cartelera con los datos actualizados
        return dao.actualizar(new Cartelera(id, titulo, director, anio, duracion, genero))
                ? "Pelicula actualizada."
                : "No se pudo actualizar (verifique el ID).";
    }

    // Elimina pelicula a partir de su ID
    public String eliminar(Integer id) {

        // Verifica que se haya seleccionado un registro válido (ID no puede ser NULL)
        if (id == null) {
            return "Debe seleccionar una pelicula.";
        }

        // Intenta eliminar la película en la base de datos usando el DAO. 
        return dao.eliminar(id);
    }

    // Valida que los campos no sean nulos.
    private String validar(String titulo, String director, Integer anio, Integer duracion, String genero) {
        if (titulo == null || titulo.isBlank()) {
            return "El título es obligatorio.";
        }

        if (director == null || director.isBlank()) {
            return "El director es obligatorio.";
        }

        if (anio == null) {
            return "El año es obligatorio.";
        }

        if (anio <= 0) {
            return "El año debe ser positivo.";
        }

        if (duracion == null) {
            return "La duración es obligatoria.";
        }

        if (duracion <= 0) {
            return "La duración debe ser positiva.";
        }

        if (genero == null || genero.isBlank()) {
            return "El género es obligatorio.";
        }

        return null; // todo ok
    }
}
