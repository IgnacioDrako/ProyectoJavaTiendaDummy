package Programa;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class main {
    static String url = "jdbc:mysql://localhost:3306/proyectoprogramacion";
    static String root = "root";
    static String pass = "Dummy013Ig";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
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
    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);
        JLabel titleLabel = new JLabel("Sistema de Gestión de Base de Datos");
        titleLabel.setBounds(50, 20, 300, 25);
        panel.add(titleLabel);
        JButton productInfoButton = new JButton("Productos");
        productInfoButton.setBounds(50, 90, 300, 25);
        panel.add(productInfoButton);
        productInfoButton.addActionListener(e -> showProductInfo());
        JButton salesInfoButton = new JButton("Ventas");
        salesInfoButton.setBounds(50, 120, 300, 25);
        panel.add(salesInfoButton);
        salesInfoButton.addActionListener(e -> showSalesInfo());
        JButton newUserButton = new JButton("Usuario");
        newUserButton.setBounds(50, 150, 300, 25);
        panel.add(newUserButton);
        newUserButton.addActionListener(e -> showUserDialog(panel));
        // Añadir más botones para otras funcionalidades
    }
    private static void showProductInfo() {
        // Implementar lógica para mostrar información de productos
        JOptionPane.showMessageDialog(null, "Mostrar información de productos");
    }
    private static void showSalesInfo() {
        // Implementar lógica para mostrar información de ventas
        JOptionPane.showMessageDialog(null, "Mostrar información de ventas");
    }
    private static void showUserInfo() {
        // Implementar lógica para mostrar información de usuarios
        JOptionPane.showMessageDialog(null, "Mostrar información de usuarios");
    }
    private static void showUserDialog(JPanel panel) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Gestión de Usuarios");
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(3, 1));
        JButton userInfoButton = new JButton("Informacion");
        userInfoButton.addActionListener(e -> showUserInfo());
        dialog.add(userInfoButton);
        JButton newComButton = new JButton("Crear Comprador");
        newComButton.addActionListener(e -> showNewUserDialogComprador());
        dialog.add(newComButton);
        JButton newVenButton = new JButton("Crear Vendedor");
        newVenButton.addActionListener(e -> showNewUserDialogVendedor());
        dialog.add(newVenButton);
        dialog.setVisible(true);
    }
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
		    saveButton.addActionListener(e -> {
		        // Lógica para guardar el usuario
		    });
	        saveButton.addActionListener(e -> {
	            String nombre = nameField.getText();
	            String id = idField.getText();
	            String password = passwordField.getText();
	            String email = emailField.getText();
	            double saldo = Double.parseDouble(saldoField.getText());
	            int IdProducto = Integer.parseInt(ProductoField.getText());
	            Vendedor V0 = new Vendedor(id, nombre, email, password, saldo,IdProducto);
	            // Llamar a la función para crear un nuevo usuario en la base de datos
	            try (Connection c = DriverManager.getConnection(url, root, pass)) {
	                String insertQuery = "INSERT INTO Vendedro (IDVendedor, nombre, Email, Contraseña, Saldo,Producto) VALUES (?, ?, ?, ?, ?, ?)";
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
	        });
	        mainPanel.add(saveButton, BorderLayout.SOUTH);
	        // Hacer visible el diálogo
	        dialog.setVisible(true);
	}
	private static void showNewUserDialogComprador() {
		 JDialog dialog = new JDialog();
		    dialog.setTitle("Nuevo Comprador");
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
		    mainPanel.add(inputPanel, BorderLayout.CENTER);
		    // Botón de guardar
		    JButton saveButton = new JButton("Guardar");
		    saveButton.addActionListener(e -> {
		    });
        saveButton.addActionListener(e -> {
            String nombre = nameField.getText();
            String id = idField.getText();
            String password = passwordField.getText();
            String email = emailField.getText();
            double saldo = Double.parseDouble(saldoField.getText());
            Comprador C0 = new Comprador(id, nombre, email, password, saldo);
            // Llamar a la función para crear un nuevo usuario en la base de datos
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
        });
        mainPanel.add(saveButton, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
}
