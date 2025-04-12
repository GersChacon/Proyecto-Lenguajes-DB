package VIEW;

import CONTROLLER.DetallePagoController;
import MODEL.DetallePago;
import SERVICE.BancosService;
import SERVICE.MetodoPagoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

public class DetallePagoForm extends JFrame {

    private JTextField txtId, txtPago, txtBanco, txtNumeroTarjeta, txtNombreTitular, txtNumeroTransferencia;
    private JComboBox<String> cmbMetodoPago;
    private JFormattedTextField txtFechaExpiracion;
    private JButton btnGuardar, btnActualizar, btnEliminar;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private DetallePagoController controller;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public DetallePagoForm() {
        controller = new DetallePagoController();

        setTitle("Gestión de Detalles de Pago");
        setSize(900, 600);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Cargar combos
        MetodoPagoService metodoPagoService = new MetodoPagoService();
        BancosService bancosService = new BancosService();

        JLabel lblId = new JLabel("ID Detalle:");
        lblId.setBounds(20, 20, 120, 25);
        add(lblId);

        txtId = new JTextField();
        txtId.setBounds(150, 20, 200, 25);
        txtId.setEnabled(false);
        add(txtId);

        JLabel lblPago = new JLabel("ID Pago:");
        lblPago.setBounds(20, 60, 120, 25);
        add(lblPago);

        txtPago = new JTextField();
        txtPago.setBounds(150, 60, 200, 25);
        add(txtPago);

        JLabel lblMetodoPago = new JLabel("Método Pago:");
        lblMetodoPago.setBounds(20, 100, 120, 25);
        add(lblMetodoPago);

        cmbMetodoPago = new JComboBox<>();
        cmbMetodoPago.setBounds(150, 100, 200, 25);
        add(cmbMetodoPago);

        JLabel lblBanco = new JLabel("ID Banco:");
        lblBanco.setBounds(20, 140, 120, 25);
        add(lblBanco);

        txtBanco = new JTextField();
        txtBanco.setBounds(150, 140, 200, 25);
        add(txtBanco);

        JLabel lblNumeroTarjeta = new JLabel("Número Tarjeta:");
        lblNumeroTarjeta.setBounds(20, 180, 120, 25);
        add(lblNumeroTarjeta);

        txtNumeroTarjeta = new JTextField();
        txtNumeroTarjeta.setBounds(150, 180, 200, 25);
        add(txtNumeroTarjeta);

        JLabel lblNombreTitular = new JLabel("Nombre Titular:");
        lblNombreTitular.setBounds(20, 220, 120, 25);
        add(lblNombreTitular);

        txtNombreTitular = new JTextField();
        txtNombreTitular.setBounds(150, 220, 200, 25);
        add(txtNombreTitular);

        JLabel lblFechaExpiracion = new JLabel("Fecha Expiración:");
        lblFechaExpiracion.setBounds(20, 260, 120, 25);
        add(lblFechaExpiracion);

        txtFechaExpiracion = new JFormattedTextField(dateFormat);
        txtFechaExpiracion.setBounds(150, 260, 200, 25);
        add(txtFechaExpiracion);

        JLabel lblNumeroTransferencia = new JLabel("Número Transferencia:");
        lblNumeroTransferencia.setBounds(20, 300, 120, 25);
        add(lblNumeroTransferencia);

        txtNumeroTransferencia = new JTextField();
        txtNumeroTransferencia.setBounds(150, 300, 200, 25);
        add(txtNumeroTransferencia);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(370, 60, 120, 30);
        add(btnGuardar);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(370, 100, 120, 30);
        add(btnActualizar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(370, 140, 120, 30);
        add(btnEliminar);

        modeloTabla = new DefaultTableModel(new String[]{"ID", "ID Pago", "Método Pago", "Banco",
            "Núm. Tarjeta", "Titular", "Expiración",
            "Transferencia"}, 0);
        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 350, 840, 200);
        add(scroll);

        cargarMetodosPago();
        cargarDetallesPago();

        // Eventos
        btnGuardar.addActionListener(e -> {
            try {
                controller.insertarDetallePago(
                        Integer.parseInt(txtPago.getText()),
                        cmbMetodoPago.getSelectedIndex() + 1, // Asumiendo que los IDs empiezan en 1
                        txtBanco.getText().isEmpty() ? null : Integer.parseInt(txtBanco.getText()),
                        txtNumeroTarjeta.getText(),
                        txtNombreTitular.getText(),
                        txtFechaExpiracion.getText().isEmpty() ? null : dateFormat.parse(txtFechaExpiracion.getText()),
                        txtNumeroTransferencia.getText()
                );
                limpiarCampos();
                cargarDetallesPago();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnActualizar.addActionListener(e -> {
            if (!txtId.getText().isEmpty()) {
                try {
                    controller.actualizarDetallePago(
                            Integer.parseInt(txtId.getText()),
                            Integer.parseInt(txtPago.getText()),
                            cmbMetodoPago.getSelectedIndex() + 1,
                            txtBanco.getText().isEmpty() ? null : Integer.parseInt(txtBanco.getText()),
                            txtNumeroTarjeta.getText(),
                            txtNombreTitular.getText(),
                            txtFechaExpiracion.getText().isEmpty() ? null : dateFormat.parse(txtFechaExpiracion.getText()),
                            txtNumeroTransferencia.getText()
                    );
                    limpiarCampos();
                    cargarDetallesPago();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnEliminar.addActionListener(e -> {
            if (!txtId.getText().isEmpty()) {
                int id = Integer.parseInt(txtId.getText());
                controller.eliminarDetallePago(id);
                limpiarCampos();
                cargarDetallesPago();
            }
        });

        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = tabla.getSelectedRow();
                txtId.setText(modeloTabla.getValueAt(fila, 0).toString());
                txtPago.setText(modeloTabla.getValueAt(fila, 1).toString());
                cmbMetodoPago.setSelectedItem(modeloTabla.getValueAt(fila, 2));
                Object banco = modeloTabla.getValueAt(fila, 3);
                txtBanco.setText(banco != null ? banco.toString() : "");
                txtNumeroTarjeta.setText(modeloTabla.getValueAt(fila, 4).toString());
                txtNombreTitular.setText(modeloTabla.getValueAt(fila, 5).toString());
                Object fecha = modeloTabla.getValueAt(fila, 6);
                txtFechaExpiracion.setText(fecha != null ? fecha.toString() : "");
                Object transferencia = modeloTabla.getValueAt(fila, 7);
                txtNumeroTransferencia.setText(transferencia != null ? transferencia.toString() : "");
            }
        });

        setVisible(true);
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtPago.setText("");
        cmbMetodoPago.setSelectedIndex(0);
        txtBanco.setText("");
        txtNumeroTarjeta.setText("");
        txtNombreTitular.setText("");
        txtFechaExpiracion.setText("");
        txtNumeroTransferencia.setText("");
    }

    private void cargarMetodosPago() {
        // Implementar carga de métodos de pago en el combo
        // Esto es un ejemplo, deberías usar tu servicio de métodos de pago
        cmbMetodoPago.addItem("Efectivo");
        cmbMetodoPago.addItem("Tarjeta");
        cmbMetodoPago.addItem("Transferencia");
    }

    private void cargarDetallesPago() {
        modeloTabla.setRowCount(0);
        List<DetallePago> lista = controller.obtenerTodosLosDetallesPago();
        for (DetallePago detalle : lista) {
            modeloTabla.addRow(new Object[]{
                detalle.getIdDetallePago(),
                detalle.getIdPago(),
                detalle.getIdMetodoPago(),
                detalle.getIdBanco(),
                detalle.getNumeroTarjeta(),
                detalle.getNombreTitular(),
                detalle.getFechaExpiracion() != null ? dateFormat.format(detalle.getFechaExpiracion()) : "",
                detalle.getNumeroTransferencia()
            });
        }
    }

    public static void main(String[] args) {
        new DetallePagoForm();
    }
}
