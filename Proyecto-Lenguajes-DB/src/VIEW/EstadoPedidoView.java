package VIEW;

import CONTROLLER.EstadoPedidoController;
import MODEL.EstadoPedido;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;

public class EstadoPedidoView extends JFrame {

    private JTextField txtId, txtNombre;
    private JButton btnGuardar, btnActualizar, btnEliminar;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private EstadoPedidoController controller;

    public EstadoPedidoView() {
        controller = new EstadoPedidoController();

        setTitle("Gestión de Estados de Pedido");
        setSize(600, 400);
        setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel lblId = new JLabel("ID:");
        lblId.setBounds(20, 20, 100, 25);
        add(lblId);

        txtId = new JTextField();
        txtId.setBounds(130, 20, 200, 25);
        txtId.setEnabled(false);
        add(txtId);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(20, 60, 100, 25);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(130, 60, 200, 25);
        add(txtNombre);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(350, 20, 100, 30);
        add(btnGuardar);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(350, 60, 100, 30);
        add(btnActualizar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(350, 100, 100, 30);
        add(btnEliminar);

        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre"}, 0);
        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 150, 540, 180);
        add(scroll);

        cargarEstadosPedido();

        btnGuardar.addActionListener(e -> {
            if (!txtNombre.getText().isEmpty()) {
                controller.insertarEstadoPedido(txtNombre.getText());
                limpiarCampos();
                cargarEstadosPedido();
            } else {
                JOptionPane.showMessageDialog(this, "El nombre del estado es requerido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnActualizar.addActionListener(e -> {
            if (!txtId.getText().isEmpty() && !txtNombre.getText().isEmpty()) {
                int id = Integer.parseInt(txtId.getText());
                controller.actualizarEstadoPedido(id, txtNombre.getText());
                limpiarCampos();
                cargarEstadosPedido();
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un estado y complete el nombre", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnEliminar.addActionListener(e -> {
            if (!txtId.getText().isEmpty()) {
                int id = Integer.parseInt(txtId.getText());
                int confirm = JOptionPane.showConfirmDialog(this,
                        "¿Está seguro de eliminar este estado de pedido?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    controller.eliminarEstadoPedido(id);
                    limpiarCampos();
                    cargarEstadosPedido();
                }
            }
        });

        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = tabla.getSelectedRow();
                txtId.setText(modeloTabla.getValueAt(fila, 0).toString());
                txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
            }
        });

        setVisible(true);
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
    }

    private void cargarEstadosPedido() {
        try {
            modeloTabla.setRowCount(0);
            List<EstadoPedido> lista = controller.obtenerTodosLosEstadosPedido();

            if (lista.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay estados de pedido registrados",
                        "Información", JOptionPane.INFORMATION_MESSAGE);
            }

            for (EstadoPedido estado : lista) {
                modeloTabla.addRow(new Object[]{
                    estado.getIdEstado(),
                    estado.getNombre()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar estados de pedido: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EstadoPedidoView());
    }
}
