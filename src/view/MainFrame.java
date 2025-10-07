package view;

import model.Cartelera;
import controller.CarteleraController;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

/**
 * Interfaz gráfica principal de la aplicación Permite gestionar la cartelera
 */
public class MainFrame extends JFrame {

    // Controlador que maneja la lógica de negocio
    private final CarteleraController controller = new CarteleraController();
    // Modelo de la tabla personalizada para mostrar los datos en JTable 
    private final CarteleraTableModel tableModel = new CarteleraTableModel();
    // Tabla que muestra las películas
    private final JTable table = new JTable(tableModel);

    // Campo de texto
    private final JTextField txtTitulo = new JTextField(10);
    private final JTextField txtDirector = new JTextField(10);
    private final JTextField txtAnio = new JTextField(5);
    private final JTextField txtDuracion = new JTextField(5);
    private final JTextField txtGenero = new JTextField(10);
    private final JTextField txtBuscar = new JTextField(10);
    private final JTextField txtGeneroFiltro = new JTextField(10);
    private final JTextField txtAnioDesde = new JTextField(5);
    private final JTextField txtAnioHasta = new JTextField(5);
    private final JButton btnBuscarGenero = new JButton("Buscar por Género");
    private final JButton btnBuscarRango = new JButton("Buscar por Rango de Años");

    /**
     * Constructor que configura la ventana principal y sus componentes
     */
    public MainFrame() {
        setTitle("Cine DB - Gestión de Películas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Layout principal con espacios para componentes
        setLayout(new BorderLayout(10, 10));
        ((JComponent) getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));

        // Panel de formulario
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Fila 1: Título, Director y Año
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Titulo:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(txtTitulo, gbc);

        gbc.gridx = 2;
        inputPanel.add(new JLabel("Director:"), gbc);
        gbc.gridx = 3;
        inputPanel.add(txtDirector, gbc);

        gbc.gridx = 4;
        inputPanel.add(new JLabel("Año:"), gbc);
        gbc.gridx = 5;
        inputPanel.add(txtAnio, gbc);

        // Fila 2: Duración y Género
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Duracion:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(txtDuracion, gbc);

        gbc.gridx = 2;
        inputPanel.add(new JLabel("Genero:"), gbc);
        gbc.gridx = 3;
        inputPanel.add(txtGenero, gbc);

        add(inputPanel, BorderLayout.NORTH);

        // Tabla
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setPreferredScrollableViewportSize(new Dimension(700, 300));
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Panel de botones
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.X_AXIS));
        btnPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

        // Botones CRUD y búsqueda 
        JButton btnCrear = new JButton("Crear");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnBuscar = new JButton("Buscar");
        JButton btnLimpiar = new JButton("Limpiar");

        // Los botones con espacios 
        btnPanel.add(btnCrear);
        btnPanel.add(Box.createHorizontalStrut(5));
        btnPanel.add(btnActualizar);
        btnPanel.add(Box.createHorizontalStrut(5));
        btnPanel.add(btnEliminar);
        btnPanel.add(Box.createHorizontalGlue()); // separa búsqueda de botones
        btnPanel.add(new JLabel("Buscar:"));
        btnPanel.add(Box.createHorizontalStrut(5));
        btnPanel.add(txtBuscar);
        btnPanel.add(Box.createHorizontalStrut(5));
        btnPanel.add(btnBuscar);
        btnPanel.add(btnLimpiar);
        btnPanel.add(Box.createHorizontalStrut(5));
        add(btnPanel, BorderLayout.SOUTH);
        btnPanel.add(Box.createHorizontalStrut(10));
        btnPanel.add(new JLabel("Género:"));
        btnPanel.add(txtGeneroFiltro);
        btnPanel.add(btnBuscarGenero);

        btnPanel.add(Box.createHorizontalStrut(10));
        btnPanel.add(new JLabel("Años:"));
        btnPanel.add(txtAnioDesde);
        btnPanel.add(new JLabel(" - "));
        btnPanel.add(txtAnioHasta);
        btnPanel.add(btnBuscarRango);

        // Acciones de botones
        btnCrear.addActionListener(e -> crearPelicula());
        btnActualizar.addActionListener(e -> actualizarPelicula());
        btnEliminar.addActionListener(e -> eliminarPelicula());
        btnBuscar.addActionListener(e -> buscar());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnBuscarGenero.addActionListener(e -> buscarPorGenero());
        btnBuscarRango.addActionListener(e -> buscarPorRango());

        // Ajusta tamaño, centra y muestra la ventana
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        // Carga inicial de la tabla
        refrescarTabla();

        //Cambia idioma de JOptionPane
        UIManager.put("OptionPane.yesButtonText", "Sí");
        UIManager.put("OptionPane.noButtonText", "No");
        UIManager.put("OptionPane.cancelButtonText", "Cancelar");
    }

    /**
     * Refresca la tabla cargando la lista actual de las películas desde el
     * controlador
     */
    private void refrescarTabla() {
        tableModel.setData(controller.listar());
    }

    /**
     * Crea una nueva película usando los datos del formulario Muestra un
     * mensaje
     */
    private void crearPelicula() {

        if (txtTitulo.getText().isBlank() || txtDirector.getText().isBlank()
                || txtAnio.getText().isBlank() || txtDuracion.getText().isBlank()
                || txtGenero.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
            return;
        }

        String msg = controller.crear(txtTitulo.getText(), txtDirector.getText(),
                parseInt(txtAnio.getText()), parseInt(txtDuracion.getText()), txtGenero.getText());
        JOptionPane.showMessageDialog(this, msg);
        refrescarTabla();
    }

    /**
     * Actualiza la película seleccionada en la tabla con los datos del
     * formulario Muestra mensaje
     */
    private void actualizarPelicula() {
        int row = table.getSelectedRow();
        Cartelera c = tableModel.getAt(row);
        if (c == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una película.");
            return;
        }

        // Validación rápida
        if (txtTitulo.getText().isBlank() || txtDirector.getText().isBlank()
                || txtAnio.getText().isBlank() || txtDuracion.getText().isBlank()
                || txtGenero.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
            return;
        }

        Integer anio = parseInt(txtAnio.getText());
        Integer dur = parseInt(txtDuracion.getText());

        if (anio == null || dur == null || anio <= 0 || dur <= 0) {
            JOptionPane.showMessageDialog(this, "Año y duración deben ser números positivos.");
            return;
        }

        String msg = controller.actualizar(c.getId(), txtTitulo.getText(), txtDirector.getText(),
                anio, dur, txtGenero.getText());
        JOptionPane.showMessageDialog(this, msg);
        refrescarTabla();
    }

    /**
     * Elimina la película seleccionada en la tabla Muestra un mensaje
     */
    private void eliminarPelicula() {
        int row = table.getSelectedRow();
        Cartelera c = tableModel.getAt(row);

        if (c == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una pelicula.");
            return;
        }

        int opcion = JOptionPane.showConfirmDialog(
                this,
                "Está seguro de eliminar la película " + c.getTitulo() + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {

            Cartelera existe = controller.buscarPorId(c.getId());
            if (existe == null) {
                JOptionPane.showMessageDialog(this, "La pelicula ya no existe en la base de datos.");
                refrescarTabla();
                limpiarCampos();
                return;
            }

            String msg = controller.eliminar(c.getId());
            JOptionPane.showMessageDialog(this, msg);
            refrescarTabla();
            limpiarCampos();
        }
    }

    private void buscar() {
        String texto = txtBuscar.getText().trim();

        if (texto.isEmpty()) {
            refrescarTabla();
            limpiarCampos();
            return;
        }

        try {
            // Si busca por id
            int id = Integer.parseInt(texto);
            Cartelera c = controller.buscarPorId(id);
            if (c != null) {
                tableModel.setData(List.of(c));
                // llena con los campos encontrados.
                txtTitulo.setText(c.getTitulo());
                txtDirector.setText(c.getDirector());
                txtAnio.setText(String.valueOf(c.getAnio()));
                txtDuracion.setText(String.valueOf(c.getDuracion()));
                txtGenero.setText(c.getGenero());
            } else {
                tableModel.setData(new ArrayList<>());
                limpiarCampos();
                JOptionPane.showMessageDialog(this, "No se encontró la película con ID " + id);
            }
        } catch (NumberFormatException e) {
            // Si busca por nombre (título)
            List<Cartelera> lista = controller.buscarPorNombre(texto);
            if (lista.isEmpty()) {
                tableModel.setData(new ArrayList<>());
                limpiarCampos();
                JOptionPane.showMessageDialog(this, "No se encontró ninguna película con ese nombre.");
            } else {
                tableModel.setData(lista);
                // rellena campos si solo hay 1 resultado
                if (lista.size() == 1) {
                    Cartelera c = lista.get(0);
                    txtTitulo.setText(c.getTitulo());
                    txtDirector.setText(c.getDirector());
                    txtAnio.setText(String.valueOf(c.getAnio()));
                    txtDuracion.setText(String.valueOf(c.getDuracion()));
                    txtGenero.setText(c.getGenero());
                } else {
                    limpiarCampos(); // si son varias, no rellenamos para evitar confusión
                }
            }
        }
    }

    private void buscarPorGenero() {
        String genero = txtGeneroFiltro.getText().trim();
        List<Cartelera> lista = controller.buscarPorGenero(genero);

        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontraron películas con género: " + genero);
        }
        tableModel.setData(lista);
    }

    private void buscarPorRango() {
        try {
            int desde = Integer.parseInt(txtAnioDesde.getText());
            int hasta = Integer.parseInt(txtAnioHasta.getText());

            List<Cartelera> lista = controller.buscarPorRangoAnios(desde, hasta);

            if (lista.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No se encontraron películas entre " + desde + " y " + hasta);
            }
            tableModel.setData(lista);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese años válidos (números).");
        }
    }

    /**
     * Limpia todos los campos
     *
     */
    private void limpiarCampos() {
        txtTitulo.setText("");
        txtDirector.setText("");
        txtAnio.setText("");
        txtDuracion.setText("");
        txtGenero.setText("");
        txtBuscar.setText("");
    }

    /**
     * Convierte una cadena a Integer
     *
     * @param s
     * @return
     */
    private Integer parseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Método principal. Inicia la aplicación Ejecuta la creación de la ventana
     * en hilo de eventos de Swing
     *
     * @param args
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}
