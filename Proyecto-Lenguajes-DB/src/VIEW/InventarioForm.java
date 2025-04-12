package VIEW;

import CONTROLLER.InventarioController;
import MODEL.Inventario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;
import java.text.SimpleDateFormat;

public class InventarioForm extends JFrame {

    private JTextField txtId, txtProducto, txtCantidad, txtDetallePedido;
    private JComboBox<String> cmbTipoMovimiento;
    private JButton btnGuardar, btnActualizar, btnEliminar;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private InventarioController controller;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public InventarioForm() {
        controller = new InventarioController();

        setTitle("Gestión de Inventario");
        setSize(850, 500);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel lblId = new JLabel("ID Movimiento:");
        lblId.setBounds(20, 20, 120, 25);
        add(lblId);

        txtId = new JTextField();
        txtId.setBounds(150, 20, 200, 25);
        txtId.setEnabled(false);
        add(txtId);

        JLabel lblProducto = new JLabel("ID Producto:");
        lblProducto.setBounds(20, 60, 120, 25);
        add(lblProducto);

        txtProducto = new JTextField();
        txtProducto.setBounds(150, 60, 200, 25);
        add(txtProducto);

        JLabel lblTipo = new JLabel("Tipo Movimiento:");
        lblTipo.setBounds(20, 100, 120, 25);
        add(lblTipo);

        cmbTipoMovimiento = new JComboBox<>(new String[]{"entrada", "salida"});
        cmbTipoMovimiento.setBounds(150, 100, 200, 25);
        add(cmbTipoMovimiento);

        JLabel lblCantidad = new JLabel("Cantidad (kg):");
        lblCantidad.setBounds(20, 140, 120, 25);
        add(lblCantidad);

        txtCantidad = new JTextField();
        txtCantidad.setBounds(150, 140, 200, 25);
        add(txtCantidad);

        JLabel lblDetalle = new JLabel("ID Detalle Pedido:");
        lblDetalle.setBounds(20, 180, 120, 25);
        add(lblDetalle);

        txtDetallePedido = new JTextField();
        txtDetallePedido.setBounds(150, 180, 200, 25);
        add(txtDetallePedido);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(370, 60, 120, 30);
        add(btnGuardar);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(370, 100, 120, 30);
        add(btnActualizar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(370, 140, 120, 30);
        add(btnEliminar);

        modeloTabla = new DefaultTableModel(new String[]{"ID", "Producto", "Tipo", "Cantidad", "Fecha", "Detalle Pedido"}, 0);
        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 230, 800, 200);
        add(scroll);

        cargarMovimientos();

        // Eventos
        btnGuardar.addActionListener(e -> {
            Integer detallePedido = txtDetallePedido.getText().isEmpty() ? null : Integer.parseInt(txtDetallePedido.getText());
            controller.insertarMovimiento(
                    Integer.parseInt(txtProducto.getText()),
                    (String) cmbTipoMovimiento.getSelectedItem(),
                    Double.parseDouble(txtCantidad.getText()),
                    detallePedido
            );
            limpiarCampos();
            cargarMovimientos();
        });

        btnActualizar.addActionListener(e -> {
            if (!txtId.getText().isEmpty()) {
                Integer detallePedido = txtDetallePedido.getText().isEmpty() ? null : Integer.parseInt(txtDetallePedido.getText());
                controller.actualizarMovimiento(
                        Integer.parseInt(txtId.getText()),
                        Integer.parseInt(txtProducto.getText()),
                        (String) cmbTipoMovimiento.getSelectedItem(),
                        Double.parseDouble(txtCantidad.getText()),
                        detallePedido
                );
                limpiarCampos();
                cargarMovimientos();
            }
        });

        btnEliminar.addActionListener(e -> {
            if (!txtId.getText().isEmpty()) {
                int id = Integer.parseInt(txtId.getText());
                controller.eliminarMovimiento(id);
                limpiarCampos();
                cargarMovimientos();
            }
        });

        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = tabla.getSelectedRow();
                txtId.setText(modeloTabla.getValueAt(fila, 0).toString());
                txtProducto.setText(modeloTabla.getValueAt(fila, 1).toString());
                cmbTipoMovimiento.setSelectedItem(modeloTabla.getValueAt(fila, 2));
                txtCantidad.setText(modeloTabla.getValueAt(fila, 3).toString());
                Object detalle = modeloTabla.getValueAt(fila, 5);
                txtDetallePedido.setText(detalle != null ? detalle.toString() : "");
            }
        });

        setVisible(true);
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtProducto.setText("");
        cmbTipoMovimiento.setSelectedIndex(0);
        txtCantidad.setText("");
        txtDetallePedido.setText("");
    }

    private void cargarMovimientos() {
        modeloTabla.setRowCount(0);
        List<Inventario> lista = controller.obtenerTodosLosMovimientos();
        for (Inventario movimiento : lista) {
            modeloTabla.addRow(new Object[]{
                movimiento.getIdMovimiento(),
                movimiento.getIdProducto(),
                movimiento.getTipoMovimiento(),
                movimiento.getCantidadKg(),
                dateFormat.format(movimiento.getFechaMovimiento()),
                movimiento.getIdDetallePedido() != null ? movimiento.getIdDetallePedido() : ""
            });
        }
    }

    public static void main(String[] args) {
        new InventarioForm();
    }
}
