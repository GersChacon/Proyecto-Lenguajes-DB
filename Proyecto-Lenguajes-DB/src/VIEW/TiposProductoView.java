package VIEW;

import CONTROLLER.TiposProductoController;
import MODEL.TiposProducto;
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

        setTitle("Gestión de Tipos de Producto");
        setSize(700, 500);
        setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel lblId = new JLabel("ID Tipo:");
        lblId.setBounds(20, 20, 100, 25);
        add(lblId);

        txtId = new JTextField();
        txtId.setBounds(130, 20, 150, 25);
        txtId.setEnabled(false);
        add(txtId);

        JLabel lblIdCategoria = new JLabel("ID Categoría:");
        lblIdCategoria.setBounds(20, 60, 100, 25);
        add(lblIdCategoria);

        txtIdCategoria = new JTextField();
        txtIdCategoria.setBounds(130, 60, 150, 25);
        add(txtIdCategoria);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(20, 100, 100, 25);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(130, 100, 200, 25);
        add(txtNombre);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(350, 20, 120, 30);
        add(btnGuardar);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(350, 60, 120, 30);
        add(btnActualizar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(350, 100, 120, 30);
        add(btnEliminar);

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(350, 140, 120, 30);
        add(btnLimpiar);

        modeloTabla = new DefaultTableModel(new String[]{"ID", "ID Categoría", "Nombre"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 180, 640, 250);
        add(scroll);

        cargarTiposProducto();

        btnGuardar.addActionListener(e -> guardarTipoProducto());
        btnActualizar.addActionListener(e -> actualizarTipoProducto());
        btnEliminar.addActionListener(e -> eliminarTipoProducto());
        btnLimpiar.addActionListener(e -> limpiarCampos());

        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                seleccionarTipoDeTabla();
            }
        });

        setVisible(true);
    }

    private void guardarTipoProducto() {
        try {
            int idCategoria = Integer.parseInt(txtIdCategoria.getText());
            String nombre = txtNombre.getText().trim();

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre es obligatorio", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            controller.insertarTipoProducto(idCategoria, nombre);
            JOptionPane.showMessageDialog(this, "Tipo de producto guardado correctamente");
            limpiarCampos();
            cargarTiposProducto();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID Categoría debe ser un número válido", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void actualizarTipoProducto() {
        if (!txtId.getText().isEmpty()) {
            try {
                int idTipo = Integer.parseInt(txtId.getText());
                int idCategoria = Integer.parseInt(txtIdCategoria.getText());
                String nombre = txtNombre.getText().trim();

                if (nombre.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El nombre es obligatorio", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                controller.actualizarTipoProducto(idTipo, idCategoria, nombre);
                JOptionPane.showMessageDialog(this, "Tipo de producto actualizado correctamente");
                limpiarCampos();
                cargarTiposProducto();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "IDs deben ser números válidos", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al actualizar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un tipo para actualizar", "Advertencia", JOptionPane.WARNING_MESSAGE);
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
                    JOptionPane.showMessageDialog(this, "Tipo de producto eliminado correctamente");
                    limpiarCampos();
                    cargarTiposProducto();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error al eliminar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un tipo para eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
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

    private void limpiarCampos() {
        txtId.setText("");
        txtIdCategoria.setText("");
        txtNombre.setText("");
    }

    private void cargarTiposProducto() {
        try {
            modeloTabla.setRowCount(0);

            List<TiposProducto> listaTipos = controller.obtenerTodosLosTiposProducto();

            if (listaTipos.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "No hay tipos de producto registrados",
                        "Información", JOptionPane.INFORMATION_MESSAGE);
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
            JOptionPane.showMessageDialog(this,
                    "Error al cargar tipos: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TiposProductoView());
    }
}
