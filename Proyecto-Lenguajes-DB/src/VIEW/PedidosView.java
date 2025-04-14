package VIEW;

import CONTROLLER.PedidosController;
import MODEL.Pedidos;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;

public class PedidosView extends JFrame {

    private JTextField txtId, txtCliente, txtEstado, txtMonto;
    private JButton btnGuardar, btnActualizar, btnEliminar;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private PedidosController controller;

    public PedidosView() {
        controller = new PedidosController();

        setTitle("Gestión de Pedidos");
        setSize(700, 450);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel lblId = new JLabel("ID:");
        lblId.setBounds(20, 20, 100, 25);
        add(lblId);

        txtId = new JTextField();
        txtId.setBounds(130, 20, 200, 25);
        txtId.setEnabled(false);
        add(txtId);

        JLabel lblCliente = new JLabel("ID Cliente:");
        lblCliente.setBounds(20, 60, 100, 25);
        add(lblCliente);

        txtCliente = new JTextField();
        txtCliente.setBounds(130, 60, 200, 25);
        add(txtCliente);

        JLabel lblEstado = new JLabel("ID Estado:");
        lblEstado.setBounds(20, 100, 100, 25);
        add(lblEstado);

        txtEstado = new JTextField();
        txtEstado.setBounds(130, 100, 200, 25);
        add(txtEstado);

        JLabel lblMonto = new JLabel("Monto Pagado:");
        lblMonto.setBounds(20, 140, 100, 25);
        add(lblMonto);

        txtMonto = new JTextField();
        txtMonto.setBounds(130, 140, 200, 25);
        add(txtMonto);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(350, 60, 100, 30);
        add(btnGuardar);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(350, 100, 100, 30);
        add(btnActualizar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(350, 140, 100, 30);
        add(btnEliminar);

        modeloTabla = new DefaultTableModel(new String[]{"ID", "ID Cliente", "ID Estado", "Fecha", "Monto"}, 0);
        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 230, 640, 150);
        add(scroll);

        cargarPedidos();

        btnGuardar.addActionListener(e -> {
            controller.insertarPedido(
                    Integer.parseInt(txtCliente.getText()),
                    Integer.parseInt(txtEstado.getText()),
                    Double.parseDouble(txtMonto.getText())
            );
            limpiarCampos();
            cargarPedidos();
        });

        btnActualizar.addActionListener(e -> {
            if (!txtId.getText().isEmpty()) {
                int id = Integer.parseInt(txtId.getText());
                controller.actualizarPedido(id,
                        Integer.parseInt(txtCliente.getText()),
                        Integer.parseInt(txtEstado.getText()),
                        Double.parseDouble(txtMonto.getText())
                );
                limpiarCampos();
                cargarPedidos();
            }
        });

        btnEliminar.addActionListener(e -> {
            if (!txtId.getText().isEmpty()) {
                int id = Integer.parseInt(txtId.getText());
                controller.eliminarPedido(id);
                limpiarCampos();
                cargarPedidos();
            }
        });

        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = tabla.getSelectedRow();
                txtId.setText(modeloTabla.getValueAt(fila, 0).toString());
                txtCliente.setText(modeloTabla.getValueAt(fila, 1).toString());
                txtEstado.setText(modeloTabla.getValueAt(fila, 2).toString());
                txtMonto.setText(modeloTabla.getValueAt(fila, 4).toString());
            }
        });

        setVisible(true);
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtCliente.setText("");
        txtEstado.setText("");
        txtMonto.setText("");
    }

    private void cargarPedidos() {
        modeloTabla.setRowCount(0);
        List<Pedidos> lista = controller.obtenerTodosLosPedidos();
        for (Pedidos pedido : lista) {
            modeloTabla.addRow(new Object[]{
                pedido.getIdPedido(),
                pedido.getIdCliente(),
                pedido.getIdEstado(),
                pedido.getFechaPedido(),
                pedido.getMontoPagado()
            });
        }
    }

    public static void main(String[] args) {
        new PedidosView();
    }
}
