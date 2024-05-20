package Programa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
public class main {
	static String url = "jdbc:mysql://localhost:3306/proyectoprogramacion";
	static String root = "root";
	static String pass = "Dummy013Ig";
	static Scanner scanner = new Scanner(System.in);
	public static void main(String[] args) {
		Menu();
	}
	private static void Menu() {
		do {
			Texto();
			int opcion = new Scanner(System.in).nextInt();
            switch (opcion) {
            case 1:
            	System.out.println("Informacion Usuarios");
            	InfoUser(url, root, pass);
                break;
            case 2: 
            	System.out.println("Informacion Productos");
            	InfoProductos(url, root, pass);
                break;
            case 3: 
            	System.out.println("Informacion Ventas");
            	InfoVentas(url, root, pass);
                break;
            case 4: 
            	NewUser(url, root, pass);
                break;
            case 5:
            	NewProductos(url, root, pass);
                break;		  
            case 6:
            	System.out.println("Nueva Venta");
            	NewVentas(url, root, pass); 
                break;
            case 7:
            	System.out.println("Añadir Saldo a usuario");
                AddSaldo(url, root, pass);
                break;
            case 8:
            	EliminarUsuario(url, root, pass);
            	break;
            case 9:
            	EliminarProductos(url, root, pass);
                break;
            case 10:
            	EliminarVentas(url, root, pass);
                break;
            case 11:
            	System.out.println("Salir");
            	System.exit(0);
                break;
                default:
                	System.out.println("Opcion Invalida");
                break;
            }
		}while(true);
    }
	private static void EliminarVentas(String url, String root, String pass) {
		// TODO Auto-generated method stub
		try {
			Connection c = DriverManager.getConnection(url, root, pass);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void EliminarProductos(String url, String root, String pass) {
		// TODO Auto-generated method stub
		try {
			Connection c = DriverManager.getConnection(url, root, pass);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void EliminarUsuario(String url, String root, String pass) {
		// TODO Auto-generated method stub
		try {
			Connection c = DriverManager.getConnection(url, root, pass);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void AddSaldo(String url, String root, String pass) {
		// TODO Auto-generated method stub
		try {
			Connection c = DriverManager.getConnection(url, root, pass);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void NewVentas(String url, String root, String pass) {
		// TODO Auto-generated method stub
		try {
			Connection c = DriverManager.getConnection(url, root, pass);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void NewProductos(String url, String root, String pass) {
		// TODO Auto-generated method stub
		try {
			Connection c = DriverManager.getConnection(url, root, pass);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void NewUser(String url, String root, String pass) {
		// TODO Auto-generated method stub
		try (Connection c = DriverManager.getConnection(url, root, pass)){
			System.out.println("Crear Comprador o Vendedor?\n1) Comprador 2) Vendedor");
			 System.out.println("\n================================\n");
			 int opcion1 = new Scanner(System.in).nextInt();
			 switch (opcion1) {
			 case 1:
				 compradro(c);
				 break;
			 case 2:
				 vendedor(c);
				 break;
			default:
				System.out.println("Opcion no valida");
			 }
		} catch (SQLException e) {
		    // Imprimir el mensaje de la excepción
		    System.out.println("Error en la creación de usuario: " + e.getMessage());
		    // Imprimir la traza de la pila completa
		    e.printStackTrace();
		}

	}
	private static void vendedor(Connection c) {
		// TODO Auto-generated method stub
	            // Recoger datos del nuevo Vendedor
		
			System.out.println("Nuevo Usuario");
			System.out.println("Dime el nombre"); 
			String nombre = scanner.nextLine();
			System.out.println("Dime el id");
			String id = scanner.nextLine();
			System.out.println("Dime la Contraseña");
			String Password = scanner.nextLine();
			System.out.println("Dime el Email");
			String Email = scanner.nextLine();
			System.out.println("Dime el Saldo");
			double Saldo = scanner.nextDouble();
			 System.out.println("Dime el Producto");
			 int idP = new Scanner(System.in).nextInt(); //Ahoramos codigo
			ComprovarProducto(c,idP);
			scanner.nextLine();
			Vendedor v0 = new Vendedor(id,nombre,Email,Password,Saldo,idP);
	            // Preparar y ejecutar la inserción
	            String insertQuery = "INSERT INTO Vendedor (IDvendedor, nombre, Email, Contraseña, Saldo, Producto) VALUES (?, ?, ?, ?, ?, ?)";
	            try (PreparedStatement ps = c.prepareStatement(insertQuery)) {
	                ps.setString(1, id);
	                ps.setString(2, nombre);
	                ps.setString(3, Email);
	                ps.setString(4, Password);
	                ps.setDouble(5, Saldo);
	                ps.setInt(6, idP);
	                ps.executeUpdate();
	            } catch (SQLException e) {
					// TODO Auto-generated catch block
					System.out.println("Error a la hora de crear vendedor");
				}
	}
    private static boolean ComprovarProducto(Connection c, int idP) {
        // TODO Auto-generated method stub
       do { try {
    Statement s = c.createStatement(); //Creamos Statement
    ResultSet resultSet = s.executeQuery("SELECT MAX(id) FROM Producto"); //Creamos Statement
    if(idP == resultSet.getInt(1)) {
        return true;
    }else {
    System.out.println("Producto no encontrado");     
    }
    } catch (SQLException e) {
        System.out.println("Error al comprovar producto");
    }
		return false;

    }while (!false);
       }
	private static void compradro(Connection c) {
		// TODO Auto-generated method stub
		System.out.println("Nuevo Usuario");
		System.out.println("Dime el nombre"); 
		String nombre = scanner.nextLine();
		System.out.println("Dime el id");
		String id = scanner.nextLine();
		System.out.println("Dime la Contraseña");
		String Password = scanner.nextLine();
		System.out.println("Dime el Email");
		String Email = scanner.nextLine();
		System.out.println("Dime el Saldo");
		double Saldo = scanner.nextDouble();
		scanner.nextLine();
		Comprador C0 = new Comprador(id,nombre,Email,Password,Saldo);
            // Preparar y ejecutar la inserción
            String insertQuery = "INSERT INTO Comprador (IDvendedor, nombre, Email, Contraseña, Saldo) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement ps = c.prepareStatement(insertQuery)) {
                ps.setString(1, id);
                ps.setString(2, nombre);
                ps.setString(3, Email);
                ps.setString(4, Password);
                ps.setDouble(5, Saldo);
                ps.executeUpdate();
            } catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Error a la hora de crear vendedor");
			}
		
	}
	private static void InfoVentas(String url, String root, String pass) {
		// TODO Auto-generated method stub
		try {
			Connection c = DriverManager.getConnection(url, root, pass);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void InfoProductos(String url, String root, String pass) {
		// TODO Auto-generated method stub
		try {
			Connection c = DriverManager.getConnection(url, root, pass);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void InfoUser(String url, String root, String pass) {
		// TODO Auto-generated method stub
		try {
			Connection c = DriverManager.getConnection(url, root, pass);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void Texto() {
		System.out.println("\n================================");
		System.out.println("1)  Informacion Usuarios");
        System.out.println("2)  Informacion Productos");
        System.out.println("3)  Informacion Ventas");
        System.out.println("4)  Nuevo Usuario");
        System.out.println("5)  Nuevo Producto");
        System.out.println("6)  Nueva Venta");
        System.out.println("7)  Añadir Saldo a usuario");
        System.out.println("8)  Eliminar Usuario");
        System.out.println("9)  Eliminar Producto");
        System.out.println("10) Elimunar Venta");
        System.out.println("11) Salir");
        System.out.print("Ingrese opcion: ");
        System.out.println("\n================================\n");
    }
}
