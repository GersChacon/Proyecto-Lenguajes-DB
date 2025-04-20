package VIEW;

import CONTROLLER.TiposProductoController;
import MODEL.TiposProducto;
import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class TiposProductoView extends JFrame {

    private JTextField txtId, txtIdCategoria, txtNombre;
    private JButton btnGuardar, btnActualizar, btnEliminar, btnLimpiar;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private TiposProductoController controller;

    public TiposProductoView() {
        controller = new TiposProductoController();
        initComponents();
        setupListeners();
        cargarTiposProducto();
    }

    private void initComponents() {
        setTitle("Gestión de Tipos de Producto");
        setSize(700, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Panel formulario
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Datos del Tipo de Producto"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        addFormField(formPanel, gbc, "ID:", txtId = createTextField(false), 0);
        addFormField(formPanel, gbc, "ID Categoría:", txtIdCategoria = createTextField(true), 1);
        addFormField(formPanel, gbc, "Nombre:", txtNombre = createTextField(true), 2);

        // Botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        btnGuardar = new JButton("Guardar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");
        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnActualizar);
        buttonPanel.add(btnEliminar);
        buttonPanel.add(btnLimpiar);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Tabla
        modeloTabla = new DefaultTableModel(new String[]{"ID", "ID Categoría", "Nombre"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Tipos de Producto Registrados"));

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scroll, BorderLayout.CENTER);
        add(mainPanel);
    }

    private void setupListeners() {
        btnGuardar.addActionListener(e -> guardarTipoProducto());
        btnActualizar.addActionListener(e -> actualizarTipoProducto());
        btnEliminar.addActionListener(e -> eliminarTipoProducto());
        btnLimpiar.addActionListener(e -> limpiarCampos());

        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                seleccionarTipoDeTabla();
            }
        });
    }

    private void guardarTipoProducto() {
        try {
            int idCategoria = Integer.parseInt(txtIdCategoria.getText());
            String nombre = txtNombre.getText().trim();

            if (nombre.isEmpty()) {
                mostrarError("El nombre es obligatorio");
                return;
            }

            controller.insertarTipoProducto(idCategoria, nombre);
            mostrarMensaje("Tipo de producto guardado correctamente");
            limpiarCampos();
            cargarTiposProducto();
        } catch (NumberFormatException ex) {
            mostrarError("ID Categoría debe ser un número válido");
        } catch (Exception ex) {
            mostrarError("Error al guardar: " + ex.getMessage());
        }
    }

    private void actualizarTipoProducto() {
        if (!txtId.getText().isEmpty()) {
            try {
                int idTipo = Integer.parseInt(txtId.getText());
                int idCategoria = Integer.parseInt(txtIdCategoria.getText());
                String nombre = txtNombre.getText().trim();

                if (nombre.isEmpty()) {
                    mostrarError("El nombre es obligatorio");
                    return;
                }

                controller.actualizarTipoProducto(idTipo, idCategoria, nombre);
                mostrarMensaje("Tipo de producto actualizado correctamente");
                limpiarCampos();
                cargarTiposProducto();
            } catch (NumberFormatException ex) {
                mostrarError("IDs deben ser números válidos");
            } catch (Exception ex) {
                mostrarError("Error al actualizar: " + ex.getMessage());
            }
        } else {
            mostrarAdvertencia("Seleccione un tipo para actualizar");
        }
    }

    private void eliminarTipoProducto() {
        if (!txtId.getText().isEmpty()) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de eliminar este tipo de producto?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    int idTipo = Integer.parseInt(txtId.getText());
                    controller.eliminarTipoProducto(idTipo);
                    mostrarMensaje("Tipo de producto eliminado correctamente");
                    limpiarCampos();
                    cargarTiposProducto();
                } catch (Exception ex) {
                    mostrarError("Error al eliminar: " + ex.getMessage());
                }
            }
        } else {
            mostrarAdvertencia("Seleccione un tipo para eliminar");
        }
    }

    private void seleccionarTipoDeTabla() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            txtId.setText(modeloTabla.getValueAt(fila, 0).toString());
            txtIdCategoria.setText(modeloTabla.getValueAt(fila, 1).toString());
            txtNombre.setText(modeloTabla.getValueAt(fila, 2).toString());
        }
    }

    private void cargarTiposProducto() {
        try {
            modeloTabla.setRowCount(0);
            List<TiposProducto> listaTipos = controller.obtenerTodosLosTiposProducto();

            if (listaTipos.isEmpty()) {
                mostrarInformacion("No hay tipos de producto registrados");
            } else {
                for (TiposProducto tipo : listaTipos) {
                    modeloTabla.addRow(new Object[]{
                        tipo.getIdTipo(),
                        tipo.getIdCategoria(),
                        tipo.getNombre()
                    });
                }
            }
        } catch (Exception e) {
            mostrarError("Error al cargar tipos: " + e.getMessage());
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtIdCategoria.setText("");
        txtNombre.setText("");
    }

    private JTextField createTextField(boolean enabled) {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(200, 25));
        field.setEnabled(enabled);
        return field;
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc, String label, JTextField field, int row) {
        gbc.gridy = row;
        gbc.gridx = 0;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarAdvertencia(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE);
    }

    private void mostrarInformacion(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception e) {
            System.err.println("No se pudo aplicar FlatLaf.");
        }
        SwingUtilities.invokeLater(() -> new TiposProductoView().setVisible(true));
    }
}
