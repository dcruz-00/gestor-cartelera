package view;

import javax.swing.table.AbstractTableModel; 
import java.util.ArrayList;
import java.util.List;
import model.Cartelera;

/**
 * Modelo de tabla para mostrar objetos Cartelera en una JTable
 * Extiende AbstractTableModel e implementa metodos necesarios para
 * que la tabla pueda mostrar, actualizar y acceder a datos
 */
public class CarteleraTableModel extends AbstractTableModel {
    
    // Nombres de las columnas que use mostrarán en la tabla
    private final String[] cols = {"ID","Titulo","Director","Año","Duracion","Genero"};

    // Lista que contiene los datos (peliculas) que se mostrarán en la tabla
    private List<Cartelera> data = new ArrayList<>();

    /**
     * Actualiza la lista de datos que se mostrarán en la tabla
     * @param nueva 
     */
    public void setData(List<Cartelera> nueva) {
        this.data = nueva != null ? nueva : new ArrayList<>();

        // Notifica a la tabla que los datos han cambiado y debe refrescarse
        fireTableDataChanged();
    }

    /**
     * Obtiene la película que está en una file especifica
     * @param row
     * @return 
     */
    public Cartelera getAt(int row) {
        return (row >= 0 && row < data.size()) ? data.get(row) : null;
    }

    /**
     * Devuelve el número total de filas en la tabla
     * @return 
     */
    @Override
    public int getRowCount() {
        return data.size();
    }

    /**
     * Devuelve el número total de columnas en la tabla 
     * @return 
     */
    @Override
    public int getColumnCount() {
        return cols.length;
    }

    /**
     * Devuelve el nombre de una columna específica
     * @param c
     * @return 
     */
    @Override
    public String getColumnName(int c) {
        return cols[c];
    }
        
    /**
     * Devuelve el valor que se mostrará en una celda específica
     * @param r
     * @param c
     * @return 
     */
    @Override
    public Object getValueAt(int r, int c) {
        // Obtiene la película de la fila especificada
        Cartelera x = data.get(r);

        // Devuelve el valor correspondiente a la columna
        return switch (c) {
            case 0 -> x.getId();        
            case 1 -> x.getTitulo();    
            case 2 -> x.getDirector();  
            case 3 -> x.getAnio();    
            case 4 -> x.getDuracion();
            case 5 -> x.getGenero();
            default -> "";              
        };
    }
}
