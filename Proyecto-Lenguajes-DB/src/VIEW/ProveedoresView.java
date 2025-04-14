package VIEW;

import CONTROLLER.ProveedoresController;
import MODEL.Proveedores;
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

        setTitle("Gestión de Proveedores");
        setSize(800, 500);
        setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel lblId = new JLabel("ID Proveedor:");
        lblId.setBounds(20, 20, 100, 25);
        add(lblId);

        txtId = new JTextField();
        txtId.setBounds(130, 20, 150, 25);
        txtId.setEnabled(false);
        add(txtId);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(20, 60, 100, 25);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(130, 60, 200, 25);
        add(txtNombre);

        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setBounds(20, 100, 100, 25);
        add(lblTelefono);

        txtTelefono = new JTextField();
        txtTelefono.setBounds(130, 100, 200, 25);
        add(txtTelefono);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(20, 140, 100, 25);
        add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(130, 140, 200, 25);
        add(txtEmail);

        JLabel lblDireccion = new JLabel("Dirección:");
        lblDireccion.setBounds(20, 180, 100, 25);
        add(lblDireccion);

        txtDireccion = new JTextField();
        txtDireccion.setBounds(130, 180, 200, 25);
        add(txtDireccion);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(350, 60, 120, 30);
        add(btnGuardar);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(350, 100, 120, 30);
        add(btnActualizar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(350, 140, 120, 30);
        add(btnEliminar);

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(350, 180, 120, 30);
        add(btnLimpiar);

        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Teléfono", "Email", "Dirección"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 220, 740, 200);
        add(scroll);

        cargarProveedores();

        btnGuardar.addActionListener(e -> guardarProveedor());
        btnActualizar.addActionListener(e -> actualizarProveedor());
        btnEliminar.addActionListener(e -> eliminarProveedor());
        btnLimpiar.addActionListener(e -> limpiarCampos());

        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                seleccionarProveedorDeTabla();
            }
        });

        setVisible(true);
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
        SwingUtilities.invokeLater(() -> new ProveedoresView());
    }
}
