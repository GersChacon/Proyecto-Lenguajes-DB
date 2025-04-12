package VIEW;

import CONTROLLER.PagoController;
import MODEL.Pago;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;
import java.text.SimpleDateFormat;

public class PagoForm extends JFrame {

    private JTextField txtId, txtPedido, txtMonto;
    private JComboBox<String> cmbEstado;
    private JButton btnGuardar, btnActualizar, btnEliminar;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private PagoController controller;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public PagoForm() {
        controller = new PagoController();

        setTitle("Gestión de Pagos");
        setSize(800, 500);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel lblId = new JLabel("ID Pago:");
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

        JLabel lblMonto = new JLabel("Monto:");
        lblMonto.setBounds(20, 100, 100, 25);
        add(lblMonto);

        txtMonto = new JTextField();
        txtMonto.setBounds(130, 100, 200, 25);
        add(txtMonto);

        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setBounds(20, 140, 100, 25);
        add(lblEstado);

        cmbEstado = new JComboBox<>(new String[]{"pendiente", "pagado", "cancelado"});
        cmbEstado.setBounds(130, 140, 200, 25);
        add(cmbEstado);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(350, 60, 100, 30);
        add(btnGuardar);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(350, 100, 100, 30);
        add(btnActualizar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(350, 140, 100, 30);
        add(btnEliminar);

        modeloTabla = new DefaultTableModel(new String[]{"ID", "ID Pedido", "Monto", "Fecha", "Estado"}, 0);
        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 200, 740, 250);
        add(scroll);

        cargarPagos();

        // Eventos
        btnGuardar.addActionListener(e -> {
            controller.insertarPago(
                    Integer.parseInt(txtPedido.getText()),
                    Double.parseDouble(txtMonto.getText()),
                    (String) cmbEstado.getSelectedItem()
            );
            limpiarCampos();
            cargarPagos();
        });

        btnActualizar.addActionListener(e -> {
            if (!txtId.getText().isEmpty()) {
                controller.actualizarPago(
                        Integer.parseInt(txtId.getText()),
                        Integer.parseInt(txtPedido.getText()),
                        Double.parseDouble(txtMonto.getText()),
                        (String) cmbEstado.getSelectedItem()
                );
                limpiarCampos();
                cargarPagos();
            }
        });

        btnEliminar.addActionListener(e -> {
            if (!txtId.getText().isEmpty()) {
                int id = Integer.parseInt(txtId.getText());
                controller.eliminarPago(id);
                limpiarCampos();
                cargarPagos();
            }
        });

        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = tabla.getSelectedRow();
                txtId.setText(modeloTabla.getValueAt(fila, 0).toString());
                txtPedido.setText(modeloTabla.getValueAt(fila, 1).toString());
                txtMonto.setText(modeloTabla.getValueAt(fila, 2).toString());
                cmbEstado.setSelectedItem(modeloTabla.getValueAt(fila, 4));
            }
        });

        setVisible(true);
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtPedido.setText("");
        txtMonto.setText("");
        cmbEstado.setSelectedIndex(0);
    }

    private void cargarPagos() {
        modeloTabla.setRowCount(0);
        List<Pago> lista = controller.obtenerTodosLosPagos();
        for (Pago pago : lista) {
            modeloTabla.addRow(new Object[]{
                pago.getIdPago(),
                pago.getIdPedido(),
                pago.getMonto(),
                dateFormat.format(pago.getFechaPago()),
                pago.getEstadoPago()
            });
        }
    }

    public static void main(String[] args) {
        new PagoForm();
    }
}
