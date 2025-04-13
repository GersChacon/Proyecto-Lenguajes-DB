package VIEW;

import CONTROLLER.ProductosController;
import MODEL.Productos;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;

public class ProductosForm extends JFrame {

    private JTextField txtId, txtNombre, txtPrecioKg, txtStockKg, txtIdTipo, txtIdProveedor;
    private JButton btnGuardar, btnActualizar, btnEliminar, btnLimpiar;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private ProductosController controller;

    public ProductosForm() {
        controller = new ProductosController();

        setTitle("Gestión de Productos");
        setSize(800, 500);
        setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Campos del formulario
        JLabel lblId = new JLabel("ID Producto:");
        lblId.setBounds(20, 20, 100, 25);
        add(lblId);

        txtId = new JTextField();
        txtId.setBounds(130, 20, 150, 25);
        txtId.setEnabled(false);
        add(txtId);

        JLabel lblIdTipo = new JLabel("ID Tipo:");
        lblIdTipo.setBounds(20, 60, 100, 25);
        add(lblIdTipo);

        txtIdTipo = new JTextField();
        txtIdTipo.setBounds(130, 60, 150, 25);
        add(txtIdTipo);

        JLabel lblIdProveedor = new JLabel("ID Proveedor:");
        lblIdProveedor.setBounds(20, 100, 100, 25);
        add(lblIdProveedor);

        txtIdProveedor = new JTextField();
        txtIdProveedor.setBounds(130, 100, 150, 25);
        add(txtIdProveedor);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(20, 140, 100, 25);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(130, 140, 150, 25);
        add(txtNombre);

        JLabel lblPrecioKg = new JLabel("Precio por Kg:");
        lblPrecioKg.setBounds(20, 180, 100, 25);
        add(lblPrecioKg);

        txtPrecioKg = new JTextField();
        txtPrecioKg.setBounds(130, 180, 150, 25);
        add(txtPrecioKg);

        JLabel lblStockKg = new JLabel("Stock en Kg:");
        lblStockKg.setBounds(20, 220, 100, 25);
        add(lblStockKg);

        txtStockKg = new JTextField();
        txtStockKg.setBounds(130, 220, 150, 25);
        add(txtStockKg);

        // Botones
        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(300, 60, 120, 30);
        add(btnGuardar);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(300, 100, 120, 30);
        add(btnActualizar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(300, 140, 120, 30);
        add(btnEliminar);

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(300, 180, 120, 30);
        add(btnLimpiar);

        // Tabla de productos
        modeloTabla = new DefaultTableModel(new String[]{"ID", "ID Tipo", "ID Proveedor", "Nombre", "Precio/Kg", "Stock/Kg"}, 0);
        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 260, 740, 180);
        add(scroll);

        cargarProductos();

        // Eventos
        btnGuardar.addActionListener(e -> guardarProducto());
        btnActualizar.addActionListener(e -> actualizarProducto());
        btnEliminar.addActionListener(e -> eliminarProducto());
        btnLimpiar.addActionListener(e -> limpiarCampos());

        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                seleccionarProductoDeTabla();
            }
        });

        setVisible(true);
    }

    private void guardarProducto() {
        try {
            int idTipo = Integer.parseInt(txtIdTipo.getText());
            int idProveedor = Integer.parseInt(txtIdProveedor.getText());
            String nombre = txtNombre.getText();
            double precioKg = Double.parseDouble(txtPrecioKg.getText());
            double stockKg = Double.parseDouble(txtStockKg.getText());

            controller.insertarProducto(idTipo, idProveedor, nombre, precioKg, stockKg);
            JOptionPane.showMessageDialog(this, "Producto guardado correctamente");
            limpiarCampos();
            cargarProductos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese valores numéricos válidos", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar el producto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarProducto() {
        if (!txtId.getText().isEmpty()) {
            try {
                int idProducto = Integer.parseInt(txtId.getText());
                int idTipo = Integer.parseInt(txtIdTipo.getText());
                int idProveedor = Integer.parseInt(txtIdProveedor.getText());
                String nombre = txtNombre.getText();
                double precioKg = Double.parseDouble(txtPrecioKg.getText());
                double stockKg = Double.parseDouble(txtStockKg.getText());

                controller.actualizarProducto(idProducto, idTipo, idProveedor, nombre, precioKg, stockKg);
                JOptionPane.showMessageDialog(this, "Producto actualizado correctamente");
                limpiarCampos();
                cargarProductos();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor ingrese valores numéricos válidos", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al actualizar el producto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para actualizar", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void eliminarProducto() {
        if (!txtId.getText().isEmpty()) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de eliminar este producto?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    int idProducto = Integer.parseInt(txtId.getText());
                    controller.eliminarProducto(idProducto);
                    JOptionPane.showMessageDialog(this, "Producto eliminado correctamente");
                    limpiarCampos();
                    cargarProductos();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error al eliminar el producto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void seleccionarProductoDeTabla() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            txtId.setText(modeloTabla.getValueAt(fila, 0).toString());
            txtIdTipo.setText(modeloTabla.getValueAt(fila, 1).toString());
            txtIdProveedor.setText(modeloTabla.getValueAt(fila, 2).toString());
            txtNombre.setText(modeloTabla.getValueAt(fila, 3).toString());
            txtPrecioKg.setText(modeloTabla.getValueAt(fila, 4).toString());
            txtStockKg.setText(modeloTabla.getValueAt(fila, 5).toString());
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtIdTipo.setText("");
        txtIdProveedor.setText("");
        txtNombre.setText("");
        txtPrecioKg.setText("");
        txtStockKg.setText("");
    }

    private void cargarProductos() {
        try {
            modeloTabla.setRowCount(0);

            List<Productos> listaProductos = controller.obtenerTodosLosProductos();

            if (listaProductos.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "No hay productos registrados en el sistema",
                        "Información", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            for (Productos producto : listaProductos) {
                modeloTabla.addRow(new Object[]{
                    producto.getIdProducto(),
                    producto.getIdTipo(),
                    producto.getIdProveedor(),
                    producto.getNombre(),
                    String.format("%.2f", producto.getPrecioKg()),
                    String.format("%.2f", producto.getStockKg())
                });
            }

            for (int i = 0; i < tabla.getColumnCount(); i++) {
                tabla.getColumnModel().getColumn(i).setPreferredWidth(100);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar los productos: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ProductosForm());
    }
}
