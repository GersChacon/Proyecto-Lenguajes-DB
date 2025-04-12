package VIEW;

import CONTROLLER.DetallePedidoController;
import MODEL.DetallePedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;

public class DetallePedidoForm extends JFrame {

    private JTextField txtId, txtPedido, txtProducto, txtCantidad, txtPrecio;
    private JButton btnGuardar, btnActualizar, btnEliminar;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private DetallePedidoController controller;

    public DetallePedidoForm() {
        controller = new DetallePedidoController();

        setTitle("Gestión de Detalle de Pedido");
        setSize(800, 500);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel lblId = new JLabel("ID Detalle:");
        lblId.setBounds(20, 20, 100, 25);
        add(lblId);

        txtId = new JTextField();
        txtId.setBounds(130, 20, 200, 25);
        txtId.setEnabled(false);
        add(txtId);

        JLabel lblPedido = new JLabel("ID Pedido:");
        lblPedido.setBounds(20, 60, 100, 25);
        add(lblPedido);

        txtPedido = new JTextField();
        txtPedido.setBounds(130, 60, 200, 25);
        add(txtPedido);

        JLabel lblProducto = new JLabel("ID Producto:");
        lblProducto.setBounds(20, 100, 100, 25);
        add(lblProducto);

        txtProducto = new JTextField();
        txtProducto.setBounds(130, 100, 200, 25);
        add(txtProducto);

        JLabel lblCantidad = new JLabel("Cantidad (kg):");
        lblCantidad.setBounds(20, 140, 100, 25);
        add(lblCantidad);

        txtCantidad = new JTextField();
        txtCantidad.setBounds(130, 140, 200, 25);
        add(txtCantidad);

        JLabel lblPrecio = new JLabel("Precio Unitario:");
        lblPrecio.setBounds(20, 180, 100, 25);
        add(lblPrecio);

        txtPrecio = new JTextField();
        txtPrecio.setBounds(130, 180, 200, 25);
        add(txtPrecio);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(350, 60, 100, 30);
        add(btnGuardar);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(350, 100, 100, 30);
        add(btnActualizar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(350, 140, 100, 30);
        add(btnEliminar);

        modeloTabla = new DefaultTableModel(new String[]{"ID", "ID Pedido", "ID Producto", "Cantidad (kg)", "Precio Unitario", "Subtotal"}, 0);
        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 230, 740, 200);
        add(scroll);

        cargarDetalles();

        // Eventos
        btnGuardar.addActionListener(e -> {
            controller.insertarDetalle(
                    Integer.parseInt(txtPedido.getText()),
                    Integer.parseInt(txtProducto.getText()),
                    Double.parseDouble(txtCantidad.getText()),
                    Double.parseDouble(txtPrecio.getText())
            );
            limpiarCampos();
            cargarDetalles();
        });

        btnActualizar.addActionListener(e -> {
            if (!txtId.getText().isEmpty()) {
                int id = Integer.parseInt(txtId.getText());
                controller.actualizarDetalle(id,
                        Integer.parseInt(txtPedido.getText()),
                        Integer.parseInt(txtProducto.getText()),
                        Double.parseDouble(txtCantidad.getText()),
                        Double.parseDouble(txtPrecio.getText())
                );
                limpiarCampos();
                cargarDetalles();
            }
        });

        btnEliminar.addActionListener(e -> {
            if (!txtId.getText().isEmpty()) {
                int id = Integer.parseInt(txtId.getText());
                controller.eliminarDetalle(id);
                limpiarCampos();
                cargarDetalles();
            }
        });

        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = tabla.getSelectedRow();
                txtId.setText(modeloTabla.getValueAt(fila, 0).toString());
                txtPedido.setText(modeloTabla.getValueAt(fila, 1).toString());
                txtProducto.setText(modeloTabla.getValueAt(fila, 2).toString());
                txtCantidad.setText(modeloTabla.getValueAt(fila, 3).toString());
                txtPrecio.setText(modeloTabla.getValueAt(fila, 4).toString());
            }
        });

        setVisible(true);
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtPedido.setText("");
        txtProducto.setText("");
        txtCantidad.setText("");
        txtPrecio.setText("");
    }

    private void cargarDetalles() {
        modeloTabla.setRowCount(0);
        List<DetallePedido> lista = controller.obtenerTodosLosDetalles();
        for (DetallePedido detalle : lista) {
            modeloTabla.addRow(new Object[]{
                detalle.getIdDetalle(),
                detalle.getIdPedido(),
                detalle.getIdProducto(),
                detalle.getCantidadKg(),
                detalle.getPrecioUnitario(),
                detalle.getSubtotal()
            });
        }
    }

    public static void main(String[] args) {
        new DetallePedidoForm();
    }
}
