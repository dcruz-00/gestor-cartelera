package model;

/**
 * Modelo que representa una película en la cartelera. 
 * Contiene los atributos básicos
 */
public class Cartelera {

    private int id; 
    private String titulo;
    private String director;
    private Integer anio;
    private int duracion;
    private String genero;

    /**
     * Constructor vacío
     * Permite crear un objeto Cartelera sin inicializar atributos. 
     */
    public Cartelera() {
    }

    /**
     * Constructor completo con su ID
     * Representa pelicula ya existentes en la base de datos
     * @param id
     * @param titulo
     * @param director
     * @param anio
     * @param duracion
     * @param genero 
     */
    public Cartelera(int id, String titulo, String director, Integer anio, int duracion, String genero) {
        this.id = id;
        this.titulo = titulo;
        this.director = director;
        this.anio = anio;
        this.duracion = duracion;
        this.genero = genero;
    }
    
    /**
     * Constructor sin ID
     * Se utiliza para crear nuevas películas antes se insertarlas en la base de datos
     * El ID será generado automaticamente 
     * @param titulo
     * @param director
     * @param anio
     * @param duracion
     * @param genero 
     */
    public Cartelera(String titulo, String director, Integer anio, int duracion, String genero) {
        this.titulo = titulo;
        this.director = director;
        this.anio = anio;
        this.duracion = duracion;
        this.genero = genero;
    }

    // Getters 
    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDirector() {
        return director;
    }

    public int getAnio() {
        return anio;
    }

    public int getDuracion() {
        return duracion;
    }

    public String getGenero() {
        return genero;
    }

    // Setters 
    public void setId(int id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
    
    /**
     * Devuelve la película en formato String
     * @return 
     */
    @Override 
    public String toString() {
        return id + " | " + titulo + " | " + director + " | " + anio + " | " + duracion + " | " + genero; 
    }
}
