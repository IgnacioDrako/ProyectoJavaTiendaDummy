package Programa;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
public class main {
    static String url = "jdbc:mysql://localhost:3306/proyectoprogramacion";
    static String root = "root";
    static String pass = "Dummy013Ig";
    public static void main(String[] args) {
        SwingUtilities.invokeLater(main::createAndShowGUI);
    }
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Sistema de Gestión de Base de Datos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);
        frame.setVisible(true);
    }
///////////////////////////////////////////////////////////////////
    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("Sistema de Gestión de Base de Datos");
        titleLabel.setBounds(50, 20, 300, 25);
        panel.add(titleLabel);

        JButton productInfoButton = new JButton("Productos");
        productInfoButton.setBounds(50, 90, 300, 25);
        panel.add(productInfoButton);
        productInfoButton.addActionListener(e -> showProduc());

        JButton salesInfoButton = new JButton("Ventas");
        salesInfoButton.setBounds(50, 120, 300, 25);
        panel.add(salesInfoButton);
        salesInfoButton.addActionListener(e -> showSalesInfo());

        JButton newUserButton = new JButton("Usuario");
        newUserButton.setBounds(50, 150, 300, 25);
        panel.add(newUserButton);
        newUserButton.addActionListener(e -> showUserDialog(panel));
    }
///////////////////////////////////////////////////////////////////
    private static void showProduc() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Gestión de Producto");
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(3, 1));

        JButton ProductoInfoButton = new JButton("Informacion");
        ProductoInfoButton.setBounds(50, 90, 300, 25);
        ProductoInfoButton.addActionListener(e -> showProductInfo());
        dialog.add(ProductoInfoButton);

        JButton newComButton = new JButton("Crear");
        newComButton.addActionListener(e -> showCreateProductoDialog());
        dialog.add(newComButton);

        JButton newVenButton = new JButton("Borrar");
        newVenButton.addActionListener(e -> showDeleteUserDialog());
        dialog.add(newVenButton);

        dialog.setVisible(true);
    }
///////////////////////////////////////////////////////////////////
    private static void showCreateProductoDialog() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Nuevo Producto");
        dialog.setSize(400, 300);

        JPanel mainPanel = new JPanel(new BorderLayout());
        dialog.add(mainPanel);

        JPanel inputPanel = new JPanel(new GridLayout(6, 2));
        JTextField descpField = new JTextField();
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField precioField = new JTextField();

        inputPanel.add(new JLabel("Nombre:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("ID:"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Descripcion:"));
        inputPanel.add(descpField);
        inputPanel.add(new JLabel("Precio:"));
        inputPanel.add(precioField);

        mainPanel.add(inputPanel, BorderLayout.CENTER);

        JButton saveButton = new JButton("Guardar");
        saveButton.addActionListener(e -> {
            try {
                String nombre = nameField.getText().trim();
                String idText = idField.getText().trim();
                String descripcion = descpField.getText().trim();
                String precioText = precioField.getText().trim();
                if (nombre.isEmpty() || idText.isEmpty() || descripcion.isEmpty() || precioText.isEmpty()) {
                    throw new IllegalArgumentException("No se permiten campos vacíos.");
                }

                int id = Integer.parseInt(idText);
                double precio = Double.parseDouble(precioText);

                if (precio < 0) {
                    throw new NumberFormatException("El precio no puede ser negativo");
                }
                Producto P0 = new Producto(id, nombre ,descripcion, precio);
                try (Connection c = DriverManager.getConnection(url, root, pass)) {
                    String insertQuery = "INSERT INTO Podructo (idpodructo, nombre, descripcion, precio) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement ps = c.prepareStatement(insertQuery)) {
                        ps.setInt(1, P0.getId());
                        ps.setString(2, P0.getNombre());
                        ps.setString(3, P0.getDescripcion());
                        ps.setDouble(4, P0.getPrecio());
                        ps.executeUpdate();
                        JOptionPane.showMessageDialog(dialog, "Producto creado exitosamente");
                        dialog.dispose();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(dialog, "Error a la hora de crear el producto: " + ex.getMessage());
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(dialog, "Error de conexión a la base de datos: " + ex.getMessage());
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Por favor, introduce un precio válido y positivo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        mainPanel.add(saveButton, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
///////////////////////////////////////////////////////////////////
    private static void showProductInfo() {
    	JFrame frame = new JFrame("Datos Producto");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);
          try (Connection conn = DriverManager.getConnection(url, root, pass);
        		  Statement stmt = conn.createStatement();
                  ResultSet rs = stmt.executeQuery("SELECT * FROM podructo")) { 
                 ResultSetMetaData rsmd = rs.getMetaData();
                 int columnCount = rsmd.getColumnCount();
                 // Agregar nombres de columnas al modelo
                 for (int i = 1; i <= columnCount; i++) {
                     model.addColumn(rsmd.getColumnName(i));
                 }
                 // Agregar filas al modelo
                 while (rs.next()) {
                     Object[] row = new Object[columnCount];
                     for (int i = 1; i <= columnCount; i++) {
                         row[i - 1] = rs.getObject(i);
                     }
                     model.addRow(row);
                 }
             } catch (SQLException e) {
                 JOptionPane.showMessageDialog(null, "Error al obtener datos de la base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                 e.printStackTrace(); // Agrega esta línea para imprimir la traza del error y obtener más detalles
             }
             frame.setVisible(true);
         }
///////////////////////////////////////////////////////////////////
    private static void showSalesInfo() {
    	JFrame frame = new JFrame("Datos Vetas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);
          try (Connection conn = DriverManager.getConnection(url, root, pass);
        		  Statement stmt = conn.createStatement();
                  ResultSet rs = stmt.executeQuery("SELECT * FROM Venta")) { 
                 ResultSetMetaData rsmd = rs.getMetaData();
                 int columnCount = rsmd.getColumnCount();
                 // Agregar nombres de columnas al modelo
                 for (int i = 1; i <= columnCount; i++) {
                     model.addColumn(rsmd.getColumnName(i));
                 }
                 // Agregar filas al modelo
                 while (rs.next()) {
                     Object[] row = new Object[columnCount];
                     for (int i = 1; i <= columnCount; i++) {
                         row[i - 1] = rs.getObject(i);
                     }
                     model.addRow(row);
                 }
             } catch (SQLException e) {
                 JOptionPane.showMessageDialog(null, "Error al obtener datos de la base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                 e.printStackTrace(); // Agrega esta línea para imprimir la traza del error y obtener más detalles
             }
             frame.setVisible(true);
    }
///////////////////////////////////////////////////////////////////
    private static void showUserInfo() {
    	    JFrame frame = new JFrame("Datos Usuarios");
    	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	    frame.setSize(800, 600);
    	    JPanel mainPanel = new JPanel(new GridLayout(2, 1)); // Dos filas para cada tabla
    	    frame.add(mainPanel);
    	    // Panel para la tabla de Comprador
    	    JPanel panel1 = new JPanel(new BorderLayout());
    	    panel1.setBorder(BorderFactory.createTitledBorder("Datos Comprador"));
    	    mainPanel.add(panel1);
    	    DefaultTableModel model1 = new DefaultTableModel();
    	    JTable table1 = new JTable(model1);
    	    JScrollPane scrollPane1 = new JScrollPane(table1);
    	    panel1.add(scrollPane1, BorderLayout.CENTER);
    	    // Panel para la tabla de Vendedor
    	    JPanel panel2 = new JPanel(new BorderLayout());
    	    panel2.setBorder(BorderFactory.createTitledBorder("Datos Vendedor"));
    	    mainPanel.add(panel2);
    	    DefaultTableModel model2 = new DefaultTableModel();
    	    JTable table2 = new JTable(model2);
    	    JScrollPane scrollPane2 = new JScrollPane(table2);
    	    panel2.add(scrollPane2, BorderLayout.CENTER);
    	    try (Connection conn = DriverManager.getConnection(url, root, pass)) {
    	        // Consulta y llenado de datos para Comprador
    	        try (Statement stmt1 = conn.createStatement();
    	             ResultSet rs1 = stmt1.executeQuery("SELECT * FROM Comprador")) {
    	            ResultSetMetaData rsmd1 = rs1.getMetaData();
    	            int columnCount1 = rsmd1.getColumnCount();
    	            for (int i = 1; i <= columnCount1; i++) {
    	                model1.addColumn(rsmd1.getColumnName(i));
    	            }
    	            while (rs1.next()) {
    	                Object[] row = new Object[columnCount1];
    	                for (int i = 1; i <= columnCount1; i++) {
    	                    row[i - 1] = rs1.getObject(i);
    	                }
    	                model1.addRow(row);
    	            }
    	        }
    	        // Consulta y llenado de datos para Vendedor
    	        try (Statement stmt2 = conn.createStatement();
    	             ResultSet rs2 = stmt2.executeQuery("SELECT * FROM Vendedor")) {
    	            ResultSetMetaData rsmd2 = rs2.getMetaData();
    	            int columnCount2 = rsmd2.getColumnCount();
    	            for (int i = 1; i <= columnCount2; i++) {
    	                model2.addColumn(rsmd2.getColumnName(i));
    	            }
    	            while (rs2.next()) {
    	                Object[] row = new Object[columnCount2];
    	                for (int i = 1; i <= columnCount2; i++) {
    	                    row[i - 1] = rs2.getObject(i);
    	                }
    	                model2.addRow(row);
    	            }
    	        }
    	    } catch (SQLException e) {
    	        JOptionPane.showMessageDialog(null, "Error al obtener datos de la base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    	    }

    	    frame.setVisible(true);
    	}
///////////////////////////////////////////////////////////////////
    private static void showUserDialog(JPanel panel) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Gestión de Usuarios");
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(3, 1));

        JButton userInfoButton = new JButton("Informacion");
        userInfoButton.setBounds(50, 90, 300, 25);
        userInfoButton.addActionListener(e -> showUserInfo());
        dialog.add(userInfoButton);

        JButton newComButton = new JButton("Crear");
        newComButton.addActionListener(e -> showCreateUserDialog(panel));
        dialog.add(newComButton);

        JButton newVenButton = new JButton("Borrar");
        newVenButton.addActionListener(e -> showDeleteUserDialog());
        dialog.add(newVenButton);

        dialog.setVisible(true);
    }
///////////////////////////////////////////////////////////////////
    private static void showDeleteUserDialog() {
        // Implementar lógica para borrar usuarios
    	JDialog dialog = new JDialog();
    	dialog.setTitle("Borrar Usuario");
    	dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(3, 1));

        JButton newComButton = new JButton("Comprador");
        newComButton.addActionListener(e -> showDeleteUserDialogComprador());
        dialog.add(newComButton);

        JButton newVenButton = new JButton("Vendedor");
        newVenButton.addActionListener(e -> showDeleteUserDialogVendedor());
        dialog.add(newVenButton);

        dialog.setVisible(true);

    }
/////////////////////////////////////////////////////////////////// 
    private static void showDeleteUserDialogVendedor() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Borrar Vendedor");
        dialog.setSize(400, 300);
        JPanel mainPanel = new JPanel(new BorderLayout());
        dialog.add(mainPanel);
        JPanel inputPanel = new JPanel(new GridLayout(6, 2));
        JTextField idField = new JTextField();
        inputPanel.add(new JLabel("ID:"));
        inputPanel.add(idField);
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        JButton deleteButton = new JButton("Borrar");
        deleteButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                // Verificar la existencia del usuario 
                if (!VendedorExiste(id)) {
                    JOptionPane.showMessageDialog(dialog, "El Vendedor con el ID proporcionado no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try (Connection c = DriverManager.getConnection(url, root, pass)) {
                    String deleteQuery = "DELETE FROM Vendedor WHERE idVendedor = ?";
                    try (PreparedStatement ps = c.prepareStatement(deleteQuery)) {
                        ps.setInt(1, id);
                        int affectedRows = ps.executeUpdate();
                        if (affectedRows > 0) {
                            JOptionPane.showMessageDialog(dialog, "Vendedor borrado exitosamente");
                            dialog.dispose();
                        } else {
                            JOptionPane.showMessageDialog(dialog, "No se encontró un vendedor con el ID proporcionado", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(dialog, "Error a la hora de borrar el usuario: " + ex.getMessage());
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(dialog, "Error de conexión a la base de datos: " + ex.getMessage());
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Por favor, introduce un ID válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        mainPanel.add(deleteButton, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
///////////////////////////////////////////////////////////////////
    private static boolean VendedorExiste(int idVendedor) {
        String query = "SELECT COUNT(*) FROM Vendedor WHERE idVendedor = ?";
        try (Connection c = DriverManager.getConnection(url, root, pass);
             PreparedStatement ps = c.prepareStatement(query)) {
            ps.setInt(1, idVendedor);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al verificar la existencia del Vendedor: " + ex.getMessage());
        }
        return false;
    }
///////////////////////////////////////////////////////////////////
    private static void showDeleteUserDialogComprador() {
        // TODO Auto-generated method stub
        JDialog dialog = new JDialog();
        dialog.setTitle("Borrar Comprador");
        dialog.setSize(400, 300);
        JPanel mainPanel = new JPanel(new BorderLayout());
        dialog.add(mainPanel);
        JPanel inputPanel = new JPanel(new GridLayout(6, 2));
        JTextField idField = new JTextField();
        inputPanel.add(new JLabel("ID:"));
        inputPanel.add(idField);
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        JButton saveButton = new JButton("Borrar");
        saveButton.addActionListener(e -> {
                String id = idField.getText();
                // Verificar la existencia del usuario
                if (!CompradorExiste(id)) {
                    JOptionPane.showMessageDialog(dialog, "El Comprador con el ID proporcionado no existe.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try (Connection c = DriverManager.getConnection(url, root, pass)) {
                    String insertQuery = "Delete from Comprador when idcomprador = " + id;
                    try (PreparedStatement ps = c.prepareStatement(insertQuery)) {
                        ps.executeUpdate();
                        JOptionPane.showMessageDialog(dialog, "Comprador eliminado exitosamente");
                        dialog.dispose();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(dialog,
                                "Error a la hora de eliminar el Comprador: " + ex.getMessage());
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(dialog, "Error de conexión a la base de datos: " + ex.getMessage());
                }
        });

        mainPanel.add(saveButton, BorderLayout.SOUTH);
        dialog.setVisible(true);


    }
///////////////////////////////////////////////////////////////////
    private static void showCreateUserDialog(JPanel panel) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Gestión de Usuarios");
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(3, 1));

        JButton newComButton = new JButton("Crear Comprador");
        newComButton.addActionListener(e -> showNewUserDialogComprador());
        dialog.add(newComButton);

        JButton newVenButton = new JButton("Crear Vendedor");
        newVenButton.addActionListener(e -> showNewUserDialogVendedor());
        dialog.add(newVenButton);

        dialog.setVisible(true);
    }
///////////////////////////////////////////////////////////////////
    private static void showNewUserDialogVendedor() {
		// TODO Auto-generated method stub
		 JDialog dialog = new JDialog();
		    dialog.setTitle("Nuevo Vendedor");
		    dialog.setSize(400, 300);
		    // Crear el panel principal con BorderLayout
		    JPanel mainPanel = new JPanel(new BorderLayout());
		    dialog.add(mainPanel);
		    // Panel para los campos de entrada
		    JPanel inputPanel = new JPanel(new GridLayout(6, 2));
		    // Campos de entrada
		    JTextField nameField = new JTextField();
		    JTextField idField = new JTextField();
		    JTextField passwordField = new JTextField();
		    JTextField emailField = new JTextField();
		    JTextField saldoField = new JTextField();
		    JTextField ProductoField = new JTextField();
		    // Etiquetas
		    inputPanel.add(new JLabel("Nombre:"));
		    inputPanel.add(nameField);
		    inputPanel.add(new JLabel("ID:"));
		    inputPanel.add(idField);
		    inputPanel.add(new JLabel("Contraseña:"));
		    inputPanel.add(passwordField);
		    inputPanel.add(new JLabel("Email:"));
		    inputPanel.add(emailField);
		    inputPanel.add(new JLabel("Saldo:"));
		    inputPanel.add(saldoField);
		    inputPanel.add(new JLabel("Producto:"));
		    inputPanel.add(ProductoField);
		    // Añadir el panel de entrada al panel principal
		    mainPanel.add(inputPanel, BorderLayout.CENTER);
		    // Botón de guardar
		    JButton saveButton = new JButton("Guardar");

		 // Lógica para guardar el usuario
		    saveButton.addActionListener(e -> {
		        try {
		            String nombre = nameField.getText().trim();
		            String id = idField.getText().trim();
		            String password = passwordField.getText().trim();
		            String email = emailField.getText().trim();
		            String saldoText = saldoField.getText().trim();
		            String productoText = ProductoField.getText().trim();

		            if (saldoText.isEmpty() || productoText.isEmpty() || email.isEmpty()|| id.isEmpty() ||nombre.isEmpty() || email.isEmpty()) {
		                throw new NumberFormatException("El campo no se perminten campors vacíos");
		            }

		            double saldo = Double.parseDouble(saldoText);
		            int productoId = Integer.parseInt(productoText);

		            if (saldo < 0) {
		                throw new NumberFormatException("El saldo no puede ser negativo");
		            }

		            // Verificar la existencia del producto antes de crear el vendedor
		            if (!productoExiste(productoId)) {
		                return;
		            }

		            Vendedor V0 = new Vendedor(nombre, id, email, password, saldo, productoId);

		            try (Connection c = DriverManager.getConnection(url, root, pass)) {
		                String insertQuery = "INSERT INTO Vendedor (IDVendedor, nombre, Email, Contraseña, Saldo, Producto) VALUES (?, ?, ?, ?, ?, ?)";
		                try (PreparedStatement ps = c.prepareStatement(insertQuery)) {
		                    ps.setString(1, V0.getIDUsuario());
		                    ps.setString(2, V0.getNombre());
		                    ps.setString(3, V0.getEmail());
		                    ps.setString(4, V0.getPassword());
		                    ps.setDouble(5, V0.getSaldo());
		                    ps.setInt(6, V0.getProducto());
		                    ps.executeUpdate();
		                    JOptionPane.showMessageDialog(dialog, "Usuario creado exitosamente");
		                    dialog.dispose();
		                } catch (SQLException ex) {
		                    JOptionPane.showMessageDialog(dialog, "Error a la hora de crear el usuario: " + ex.getMessage());
		                }
		            } catch (SQLException ex) {
		                JOptionPane.showMessageDialog(dialog, "Error de conexión a la base de datos: " + ex.getMessage());
		            }
		        } catch (NumberFormatException ex) {
		            JOptionPane.showMessageDialog(dialog, "Por favor, introduce un saldo y un ID de producto válidos y positivos.", "Error", JOptionPane.ERROR_MESSAGE);
		        }
		    });
	}
///////////////////////////////////////////////////////////////////
    private static void showNewUserDialogComprador() {
    JDialog dialog = new JDialog();
    dialog.setTitle("Nuevo Comprador");
    dialog.setSize(400, 300);
    JPanel mainPanel = new JPanel(new BorderLayout());
    dialog.add(mainPanel);
    JPanel inputPanel = new JPanel(new GridLayout(6, 2));
    JTextField nameField = new JTextField();
    JTextField idField = new JTextField();
    JTextField passwordField = new JTextField();
    JTextField emailField = new JTextField();
    JTextField saldoField = new JTextField();
    inputPanel.add(new JLabel("Nombre:"));
    inputPanel.add(nameField);
    inputPanel.add(new JLabel("ID:"));
    inputPanel.add(idField);
    inputPanel.add(new JLabel("Contraseña:"));
    inputPanel.add(passwordField);
    inputPanel.add(new JLabel("Email:"));
    inputPanel.add(emailField);
    inputPanel.add(new JLabel("Saldo:"));
    inputPanel.add(saldoField);
    mainPanel.add(inputPanel, BorderLayout.CENTER);
    JButton saveButton = new JButton("Guardar");
    saveButton.addActionListener(e -> {
        try {
		            String nombre = nameField.getText().trim();
		            String id = idField.getText().trim();
		            String password = passwordField.getText().trim();
		            String email = emailField.getText().trim();
		            String saldoText = saldoField.getText().trim();  
		            if (saldoText.isEmpty()|| email.isEmpty()|| id.isEmpty() ||nombre.isEmpty() || email.isEmpty()) {
		                throw new NumberFormatException("El campo no se perminten campors vacíos");
		            }
		            double saldo = Double.parseDouble(saldoText);
            if (saldoText.isEmpty()) {
                throw new NumberFormatException("El campo de saldo está vacío");
            }
            // Verificar que el saldo no sea negativo
            if (saldo < 0) {
                throw new NumberFormatException("El saldo no puede ser negativo");
            }
            // Crear el objeto Comprador
            Comprador C0 = new Comprador(nombre,id, email, password, saldo);
            // Insertar el comprador en la base de datos
            try (Connection c = DriverManager.getConnection(url, root, pass)) {
                String insertQuery = "INSERT INTO Comprador (IDComprador, nombre, Email, Contraseña, Saldo) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement ps = c.prepareStatement(insertQuery)) {
                    ps.setString(1, C0.getIDUsuario());
                    ps.setString(2, C0.getNombre());
                    ps.setString(3, C0.getEmail());
                    ps.setString(4, C0.getPassword());
                    ps.setDouble(5, C0.getSaldo());
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(dialog, "Usuario creado exitosamente");
                    dialog.dispose();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(dialog, "Error a la hora de crear el usuario: " + ex.getMessage());
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(dialog, "Error de conexión a la base de datos: " + ex.getMessage());
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(dialog, "Por favor, introduce un saldo válido y positivo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    });

    mainPanel.add(saveButton, BorderLayout.SOUTH);
    dialog.setVisible(true);
}
///////////////////////////////////////////////////////////////////
    private static boolean productoExiste(Integer productoId) {
    if (productoId == null) {
        JOptionPane.showMessageDialog(null, "Por favor, introduce un ID de producto válido.", "Error", JOptionPane.ERROR_MESSAGE);
        return false;
    }

    String query = "SELECT COUNT(*) FROM Producto WHERE idProducto = ?";
    try (Connection c = DriverManager.getConnection(url, root, pass);
         PreparedStatement ps = c.prepareStatement(query)) {
        ps.setInt(1, productoId);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error al verificar la existencia del producto: " + ex.getMessage());
    }
    return false;
    } 
///////////////////////////////////////////////////////////////////
    private static boolean CompradorExiste(String idComprador) {
        String query = "SELECT COUNT(*) FROM Vendedor WHERE idComprador = ?";
        try (Connection c = DriverManager.getConnection(url, root, pass);
             PreparedStatement ps = c.prepareStatement(query)) {
            ps.setString(1, idComprador);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1)>0 ;
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al verificar la existencia del Comprador: " + ex.getMessage());
        }
        return false;
    }
///////////////////////////////////////////////////////////////////
}
