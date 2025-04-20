package proyecto.lenguajes.db;

import VIEW.BancosView;
import VIEW.CategoriasView;
import VIEW.ClientesView;
import VIEW.DetallePagoView;
import VIEW.DetallePedidoView;
import VIEW.EstadoPedidoView;
import VIEW.InventarioView;
import VIEW.MetodoPagoView;
import VIEW.PagoView;
import VIEW.PedidosView;
import VIEW.ProductosView;
import VIEW.ProveedoresView;
import VIEW.TiposProductoView;
import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(new FlatDarkLaf()); 
        } catch (Exception ex) {
            System.err.println("Error");
        }

        SwingUtilities.invokeLater(() -> {
            new BancosView().setVisible(true);
//            new CategoriasView().setVisible(true);
//            new ClientesView().setVisible(true);
//            new DetallePagoView().setVisible(true);
//            new DetallePedidoView().setVisible(true);
//            new EstadoPedidoView().setVisible(true);
//            new ProductosView().setVisible(true);
//            new InventarioView().setVisible(true);
//            new MetodoPagoView().setVisible(true);
//            new PagoView().setVisible(true);
//            new PedidosView().setVisible(true);
//            new ProveedoresView().setVisible(true);
//            new TiposProductoView().setVisible(true);                  
        });
    }

//        new BancosView();
//        new CategoriasView();
//        new ClientesView();
//        new DetallePagoView();
//        new DetallePedidoView();
//        new EstadoPedidoView();
//        new ProductosView();
//        new InventarioView();
//        new MetodoPagoView();
//        new PagoView();
//        new PedidosView();
//        new ProveedoresView();
//        new TiposProductoView();
}
