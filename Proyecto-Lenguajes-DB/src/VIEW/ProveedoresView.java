package VIEW;

import CONTROLLER.ProveedoresController;
import MODEL.Proveedores;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class ProveedoresView extends JPanel {

    private JTextField txtId, txtNombre, txtTelefono, txtEmail, txtDireccion;
    private JButton btnGuardar, btnActualizar, btnEliminar, btnLimpiar;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private final ProveedoresController controller;

    public ProveedoresView(ProveedoresController controller) {
        this.controller = controller;
        initComponents();
        setupListeners();
        cargarProveedores();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Datos del Proveedor"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        addFormField(formPanel, gbc, "ID:", txtId = createTextField(false), 0);
        addFormField(formPanel, gbc, "Nombre:", txtNombre = createTextField(true), 1);
        addFormField(formPanel, gbc, "Teléfono:", txtTelefono = createTextField(true), 2);
        addFormField(formPanel, gbc, "Email:", txtEmail = createTextField(true), 3);
        addFormField(formPanel, gbc, "Dirección:", txtDireccion = createTextField(true), 4);

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

        modeloTabla = new DefaultTableModel(
                new String[]{"ID", "Nombre", "Teléfono", "Email", "Dirección"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(modeloTabla);
        styleTable(tabla);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Proveedores Registrados"));

        add(topPanel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    private void setupListeners() {
        btnGuardar.addActionListener(e -> {
            if (validarCampos()) {
                try {
                    controller.insertarProveedor(
                            txtNombre.getText().trim(),
                            txtTelefono.getText().trim(),
                            txtEmail.getText().trim(),
                            txtDireccion.getText().trim()
                    );
                    mostrarMensaje("Proveedor guardado correctamente");
                    limpiarCampos();
                    cargarProveedores();
                } catch (Exception ex) {
                    mostrarError("Error al guardar el proveedor: " + ex.getMessage());
                }
            }
        });

        btnActualizar.addActionListener(e -> {
            if (!txtId.getText().isEmpty() && validarCampos()) {
                try {
                    controller.actualizarProveedor(
                            Integer.parseInt(txtId.getText()),
                            txtNombre.getText().trim(),
                            txtTelefono.getText().trim(),
                            txtEmail.getText().trim(),
                            txtDireccion.getText().trim()
                    );
                    mostrarMensaje("Proveedor actualizado correctamente");
                    limpiarCampos();
                    cargarProveedores();
                } catch (Exception ex) {
                    mostrarError("Error al actualizar el proveedor: " + ex.getMessage());
                }
            }
        });

        btnEliminar.addActionListener(e -> {
            if (!txtId.getText().isEmpty()) {
                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "¿Confirmas que deseas eliminar este proveedor?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        controller.eliminarProveedor(Integer.parseInt(txtId.getText()));
                        mostrarMensaje("Proveedor eliminado correctamente");
                        limpiarCampos();
                        cargarProveedores();
                    } catch (Exception ex) {
                        mostrarError("Error al eliminar el proveedor: " + ex.getMessage());
                    }
                }
            }
        });

        btnLimpiar.addActionListener(e -> limpiarCampos());

        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tabla.getSelectedRow();
                if (fila >= 0) {
                    txtId.setText(modeloTabla.getValueAt(fila, 0).toString());
                    txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
                    txtTelefono.setText(modeloTabla.getValueAt(fila, 2).toString());
                    txtEmail.setText(modeloTabla.getValueAt(fila, 3).toString());
                    txtDireccion.setText(modeloTabla.getValueAt(fila, 4).toString());
                }
            }
        });
    }

    private boolean validarCampos() {
        if (txtNombre.getText().trim().isEmpty()) {
            mostrarError("El campo 'Nombre' es obligatorio");
            return false;
        }
        if (!txtEmail.getText().trim().isEmpty() && !txtEmail.getText().contains("@")) {
            mostrarError("El email no tiene un formato válido");
            return false;
        }
        return true;
    }

    private void cargarProveedores() {
        modeloTabla.setRowCount(0);
        List<Proveedores> lista = controller.obtenerTodosLosProveedores();
        for (Proveedores proveedor : lista) {
            modeloTabla.addRow(new Object[]{
                proveedor.getIdProveedor(),
                proveedor.getNombre(),
                proveedor.getTelefono(),
                proveedor.getEmail(),
                proveedor.getDireccion()
            });
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        txtDireccion.setText("");
    }

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

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }
}
