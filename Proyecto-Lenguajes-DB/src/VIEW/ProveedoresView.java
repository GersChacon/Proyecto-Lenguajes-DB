package VIEW;

import CONTROLLER.ProveedoresController;
import MODEL.Proveedores;
import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class ProveedoresView extends JFrame {

    private JTextField txtId, txtNombre, txtTelefono, txtEmail, txtDireccion;
    private JButton btnGuardar, btnActualizar, btnEliminar, btnLimpiar;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private ProveedoresController controller;

    public ProveedoresView() {
        controller = new ProveedoresController();
        initComponents();
        setupListeners();
        cargarProveedores();
    }

    private void initComponents() {
        setTitle("Gestión de Proveedores");
        setSize(850, 600); // Ajustado para mejor visualización
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal con márgenes
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Panel de formulario (GridBagLayout para alineación precisa)
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Datos del Proveedor"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        // Campos del formulario
        addFormField(formPanel, gbc, "ID Proveedor:", txtId = createTextField(false), 0);
        addFormField(formPanel, gbc, "Nombre:", txtNombre = createTextField(true), 1);
        addFormField(formPanel, gbc, "Teléfono:", txtTelefono = createTextField(true), 2);
        addFormField(formPanel, gbc, "Email:", txtEmail = createTextField(true), 3);
        addFormField(formPanel, gbc, "Dirección:", txtDireccion = createTextField(true), 4);

        // Panel de botones (alineados a la derecha)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        btnGuardar = new JButton("Guardar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");
        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnActualizar);
        buttonPanel.add(btnEliminar);
        buttonPanel.add(btnLimpiar);

        // Panel superior (formulario + botones)
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Configuración de la tabla
        modeloTabla = new DefaultTableModel(
            new String[]{"ID", "Nombre", "Teléfono", "Email", "Dirección"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tabla = new JTable(modeloTabla);
        styleTable(tabla); // Aplicar estilos consistentes
        
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Proveedores Registrados"));

        // Ensamblar la interfaz
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scroll, BorderLayout.CENTER);
        add(mainPanel);
    }

    private void setupListeners() {
        btnGuardar.addActionListener(e -> guardarProveedor());
        btnActualizar.addActionListener(e -> actualizarProveedor());
        btnEliminar.addActionListener(e -> eliminarProveedor());
        btnLimpiar.addActionListener(e -> limpiarCampos());

        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                seleccionarProveedorDeTabla();
            }
        });
    }

    // Métodos auxiliares (consistentes con MetodoPagoView)
    private JTextField createTextField(boolean enabled) {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(250, 25));
        field.setEnabled(enabled);
        return field;
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc, String label, JTextField field, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(label), gbc);
        
        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private void styleTable(JTable table) {
        table.setRowHeight(25);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void guardarProveedor() {
        try {
            String nombre = txtNombre.getText().trim();
            String telefono = txtTelefono.getText().trim();
            String email = txtEmail.getText().trim();
            String direccion = txtDireccion.getText().trim();

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre es obligatorio", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            controller.insertarProveedor(nombre, telefono, email, direccion);
            JOptionPane.showMessageDialog(this, "Proveedor guardado correctamente");
            limpiarCampos();
            cargarProveedores();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar el proveedor: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void actualizarProveedor() {
        if (!txtId.getText().isEmpty()) {
            try {
                int idProveedor = Integer.parseInt(txtId.getText());
                String nombre = txtNombre.getText().trim();
                String telefono = txtTelefono.getText().trim();
                String email = txtEmail.getText().trim();
                String direccion = txtDireccion.getText().trim();

                if (nombre.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El nombre es obligatorio", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                controller.actualizarProveedor(idProveedor, nombre, telefono, email, direccion);
                JOptionPane.showMessageDialog(this, "Proveedor actualizado correctamente");
                limpiarCampos();
                cargarProveedores();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al actualizar el proveedor: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un proveedor para actualizar", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void eliminarProveedor() {
        if (!txtId.getText().isEmpty()) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de eliminar este proveedor?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    int idProveedor = Integer.parseInt(txtId.getText());
                    controller.eliminarProveedor(idProveedor);
                    JOptionPane.showMessageDialog(this, "Proveedor eliminado correctamente");
                    limpiarCampos();
                    cargarProveedores();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error al eliminar el proveedor: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un proveedor para eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void seleccionarProveedorDeTabla() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            txtId.setText(modeloTabla.getValueAt(fila, 0).toString());
            txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
            txtTelefono.setText(modeloTabla.getValueAt(fila, 2).toString());
            txtEmail.setText(modeloTabla.getValueAt(fila, 3).toString());
            txtDireccion.setText(modeloTabla.getValueAt(fila, 4).toString());
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        txtDireccion.setText("");
    }

    private void cargarProveedores() {
        try {
            modeloTabla.setRowCount(0);

            List<Proveedores> listaProveedores = controller.obtenerTodosLosProveedores();

            if (listaProveedores.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "No hay proveedores registrados",
                        "Información", JOptionPane.INFORMATION_MESSAGE);
            } else {
                for (Proveedores proveedor : listaProveedores) {
                    modeloTabla.addRow(new Object[]{
                        proveedor.getIdProveedor(),
                        proveedor.getNombre(),
                        proveedor.getTelefono(),
                        proveedor.getEmail(),
                        proveedor.getDireccion()
                    });
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar proveedores: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception e) {
            System.err.println("Error al cargar FlatLaf");
        }
        SwingUtilities.invokeLater(() -> new ProveedoresView().setVisible(true));
    }
}