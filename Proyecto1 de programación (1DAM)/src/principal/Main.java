package principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
public class Main {
	private static ArrayList<Vendedor> Vendedor = new ArrayList<>();
	private static ArrayList<Productos> Productos = new ArrayList<>();
	private static ArrayList<Ventas> Venta = new ArrayList<>();
	private static ArrayList<Comprador> Comprador = new ArrayList<>();
	static Scanner scanner = new Scanner(System.in);
	public static void main(String[] args) {
		DemoDummy();
		menu();
    }
	private static void DemoDummy() {//Creacion de Plantilla para tener de prueva y ejemplo
		Comprador compradorDummy = new Comprador("DummyComprador","IDDummyComprador","ContraseñaDummyComprador","Dummy@Comprador.es",500);
		Productos productoDummy = new Productos(0,"DummyProduct","Es un produto Dummy",10);
		Vendedor vendedorDummy = new Vendedor("DummyVendedor","IDDummyVendedor","ContraseñaDummyVendedor","Dummy@Vendedor.es",500,productoDummy);
	    Ventas VentaDummy = new Ventas(vendedorDummy, productoDummy, compradorDummy);
	    Comprador.add(compradorDummy);
	    Productos.add(productoDummy);
	    Vendedor.add(vendedorDummy);
	    Venta.add(VentaDummy);
		
	}
	public static void menu() {//menu Pricial 
		do {
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
			int opcion = new Scanner(System.in).nextInt();
            switch (opcion) {
            case 1:
            	System.out.println("Informacion Usuarios");
            	InfoUser();
                break;
            case 2: 
            	System.out.println("Informacion Productos");
            	InfoProductos();
                break;
            case 3: 
            	System.out.println("Informacion Ventas");
            	InfoVentas();
                break;
            case 4: 
            	NewUser();
                break;
            case 5:
            	NewProductos();//Podiamos hacer que solo se pueda acceder al produto por comprador
                break;		  // Es decir que solo se crea un produco al crear un vendedor
            case 6:
            	System.out.println("Nueva Venta");
            	NewVentas(); //Podiamos hacer que verifica si el producto es del vendedor correto
                break;
            case 7:
            	System.out.println("Añadir Saldo a usuario");
                AddSaldo();
                break;
            case 8:
            	EliminarUsuario();
            	break;
            case 9:
            	EliminarProductos();
                break;
            case 10:
            	EliminarVentas();
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
	//Funcion la cual elimina usuario, pide que tipo de usaurio quieres aliminar y luego verifica que este exsite
	private static void EliminarUsuario() {
		Iterator<Vendedor> iteratorV = Vendedor.iterator();//Usamos el interator
		Iterator<Comprador> iteratorC = Comprador.iterator();
		System.out.println("Dime: \n1)Ordinario \n2)Vendedor");//menu para las eleguir tipo de usuario
		int opcion1 = new Scanner(System.in).nextInt();
		switch (opcion1) {
			case 1://Eliminacion de comprador
				System.out.println("Dame un nombre");
				String nombre = scanner.nextLine();
				while(iteratorC.hasNext()) {
					Comprador c = (Comprador)iteratorC.next();
		            if(c.getNombre().equals(nombre)) {
		            	iteratorC.remove();
		                System.out.println("Se ha borrado corectamete");}
				}
				break;
			case 2://Eliminacion de Vendedor
				System.out.println("Dame un nombre");
				String nombre1 = scanner.nextLine();
				while(iteratorV.hasNext()) {
					Vendedor v = (Vendedor)iteratorV.next();
		            if(v.getNombre().equals(nombre1)) {
		            	iteratorV.remove();
		                System.out.println("Se ha borrado corectamete");}
					}
			default:
				System.out.println("Optional no valida");
				}
		
	}
	//////////////////////////////////////////////////////////////////////
	//Eliminar Los productos, solo pide nombre del produto y lo borra
	private static void EliminarProductos() {
		Iterator<Productos> iteratorP = Productos.iterator();
        System.out.println("Dame el nombre del Producto");
        String nombre = scanner.nextLine();
        while(iteratorP.hasNext()) {
        Productos p = (Productos)iteratorP.next();
            if(p.getNombre().equals(nombre)) {
                iteratorP.remove();
                System.out.println("Se ha borrado corectamete");}
        }
	}
	/////////////////////////////////////////////////////////////////////
	//Elimina un reguistro de la vetas, pide que de uno de los doasto y borrar todas las vnetas asociadas a ese producto o vededor
	private static void EliminarVentas() {	
		Iterator<Ventas> iteratorV = Venta.iterator();
        System.out.println("Dame el nombre del Vendedor");
        String nombre = scanner.nextLine();
        while(iteratorV.hasNext()) {
        Ventas v = (Ventas)iteratorV.next();
            if(v.getVendedores().getNombre().equals(nombre)) {
                iteratorV.remove();
                System.out.println("Se ha borrado corectamete");}
        }
	}
	/////////////////////////////////////////////////////////////////
	//Aumenta el salario de un compradro
	private static void AddSaldo() {
		System.out.println("Dame el usuario comprador que quieres añadir saldo");
		Scanner scanner = new Scanner(System.in);
		String usuario = scanner.nextLine();
		boolean encontrado = false;
		boolean salir = false;//Sistema de bucles por si el usuaria comete errores
        for (Comprador comprador : Comprador) {
            if (comprador.getNombre().equals(usuario)) {
                System.out.println("El nombre pertenece a un comprador.");
                encontrado = true;
                do {
                	System.out.println("Dame una catidad Positiva para añadir saldo");
            		int cantidad = new Scanner(System.in).nextInt();
            		if(cantidad <=0) {
            			System.out.println("Cantida positiva o superior a 0");//Verificamos si da un salo inferior a 1
            		}else {
            			comprador.setSaldo(comprador.getSaldo()+cantidad);
            		salir = true;
            		break;
            		}
            		}while(!salir);
            }
        }
        if (!encontrado) {
            System.out.println("El nombre ingresado no pertenece a ningún comprador:");
            
        }
	}
	//////////////////////////////////////////////////////////////
	//Creamos un nuevo reguistro de ventas, aqui devemo dar comprador vendedor y producto
	private static void NewVentas() {
	    System.out.println("Creando nueva venta:");
	    System.out.println("Ingrese el nombre del vendedor:");
	    boolean encontradoC = false;
	    Vendedor vendedorVentas = null;
	    Scanner scanner = new Scanner(System.in);
	    /////////////////////////////////////
	    //Vendedor
	    do {
	        String Vendedoractual = scanner.nextLine();
	        for (Vendedor vendedor : Vendedor) {
	            if (vendedor.getNombre().equals(Vendedoractual)) {
	                System.out.println("El nombre pertenece a un vendedor.");
	                encontradoC = true;
	                vendedorVentas = vendedor;
	                break; 
	            }
	        }
	        if (!encontradoC) {
	            System.out.println("El nombre ingresado no pertenece a ningún vendedor. Inténtelo nuevamente:");
	        }
	    } while (!encontradoC);
	    ////////////////////////////////////////////////////////////////7
	    //Producto
	    System.out.println("Dame Un producto, Nombre del producto");
	    boolean encontradoV = false;
	    Productos productoVentas = null;
	    do {
	        String productoActual = scanner.nextLine();
	        for (Productos producto : Productos) {
	            if (producto.getNombre().equals(productoActual)) {
	                System.out.println("El nombre pertenece a un producto.");
	                encontradoV = true;
	                productoVentas = producto;
	                break; 
	            }
	        }
	        if (!encontradoV) {
	            System.out.println("El nombre ingresado no pertenece a ningún producto. Inténtelo nuevamente:");
	        }
	    } while(!encontradoV);
	    //////////////////////////////////////////////////////////////
	    //Comprador
	    System.out.println("Dame Un comprador, Nombre del comprador");
	    boolean encontrado = false;
	    Comprador CompradorVentas = null;
	    do {
	        String compradorActual = scanner.nextLine();
	        for (Comprador comprador : Comprador) {
	            if (comprador.getNombre().equals(compradorActual)) {
	                System.out.println("El nombre pertenece a un comprador.");
	                encontrado = true;
	                CompradorVentas = comprador;
	                break; 
	            }
	           
	        }
	        if (!encontrado) {	
	            System.out.println("El nombre ingresado no pertenece a ningún comprador o no tiene saldo para pagar. Inténtelo nuevamente:");
	        }
	    } while(!encontrado);
	    if ((CompradorVentas.getSaldo() - productoVentas.getPrecio()) <= -1) {
	    	System.out.println("El comprador no puede permitirse el producto");
	    }else {
	    //////////////////////////////////////////////////////////////
	    // En este apartado verificamo si el comprador se puede permitir la compra, y aumentamos el saldo del vendedor
	    CompradorVentas.setSaldo(CompradorVentas.getSaldo() - productoVentas.getPrecio());
	    vendedorVentas.setSaldo(vendedorVentas.getSaldo()+productoVentas.getPrecio());
        System.out.println("El comprador ha sido pagado el producto");
	    Ventas VentaNueva = new Ventas(vendedorVentas, productoVentas, CompradorVentas);
        Venta.add(VentaNueva);
	    }
	}
	//////////////////////////////////////////////////////////////////
	//Creamos un nuevo produto
	private static void NewProductos() {
		System.out.println("Nuevo Producto");
		System.out.println("Dime la id");
		int id = new Scanner(System.in).nextInt();
		System.out.println("Dime el nombre");
		String nombre = scanner.nextLine();
		System.out.println("Dime la descripcion");
		String descripcion =scanner.nextLine();
		System.out.println("Dime el precio");
		double precio = new Scanner(System.in).nextInt();
		Productos productoNuevo = new Productos(id, nombre, descripcion, precio);
		Productos.add(productoNuevo);
	}
	////////////////////////////////////////////////////////////////////
	//Creamos un nuevo usauria, puede ser o comprador "ordinario" o vendedor
	private static void NewUser() {
		//Datos que comparte ambas clases
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
	    
	    if(Saldo <= -1) {
	        System.out.println("El saldo debe ser superior a 0");
	        return; 
	    }
	    
	    System.out.println("¿Es un vendedor? (si/no)");
	    String input = scanner.nextLine();
	    ////////////////////////////////////////////////////////
	    //Verificamos si es comprador o vendedor
	    if (input.equalsIgnoreCase("si")) {
	        System.out.println("¿El producto está registrado? (si/no)");
	        input = scanner.nextLine();
	        //////////////////////////////////////////////
	        //hay que asociar un produto, si exsite se añade, si no, se crear
	        if (input.equalsIgnoreCase("si")) {
	            System.out.println("Dame el nombre del producto");
	            String productoNombre = scanner.nextLine();
	            Productos productoComprador = null;
	            for (Productos producto : Productos) {
	                if (producto.getNombre().equals(productoNombre)) {
	                    productoComprador = producto;
	                    break;
	                }
	            }
	            if (productoComprador == null) {
	                System.out.println("Producto no encontrado");
	                return; 
	            }
	            Vendedor vendedornuevo = new Vendedor(nombre, id, Password, Email, Saldo, productoComprador);
	            Vendedor.add(vendedornuevo);
	            //////////////////////////////////////
	            //Creamos producto
	        } else if (input.equalsIgnoreCase("no")) {
	            NewProductos();
	            System.out.println("Dame el nombre del producto creado");
	            String productoNombre = scanner.nextLine();
	            Productos productoComprador = null;
	            for (Productos producto : Productos) {
	                if (producto.getNombre().equals(productoNombre)) {
	                    productoComprador = producto;
	                    break;
	                }
	            }
	            if (productoComprador == null) {
	                System.out.println("Producto no encontrado");
	                return; 
	            }
	            Vendedor vendedornuevo = new Vendedor(nombre, id, Password, Email, Saldo, productoComprador);
	            Vendedor.add(vendedornuevo);
	        } else {
	            System.out.println("Entrada inválida");
	        }
            /////////////////////////////////////
            //Comprador:
	    } else if (input.equalsIgnoreCase("no")) {
	        Comprador compradornuevo = new Comprador(nombre, id, Password, Email, Saldo);
	        Comprador.add(compradornuevo);
	    } else {
	        System.out.println("Entrada inválida");
	    }
	}
	/////////////////////////////////////////
	//Nos da la inofmacion de las ventas que se han llevado a cavo y quin compra vede y el que
	private static void InfoVentas() {
		System.out.println("------------------------------\nInformacion de las Ventas\n");
		////////////////////////////////////////////////////////////////
		//Damos al usuario la posivilidad de bucar por producto, vendedor ,comprador o que nos de todas
		System.out.println("////////////////////////////////////////");
		System.out.println("1) Todas\n2)Por comprador \n3)Por vendedor \n4)Por producto");
		System.out.println("////////////////////////////////////////");
		int opcion = new Scanner(System.in).nextInt();
		switch (opcion) {
		case 1://Todas
			for (Ventas ventas : Venta) {
				System.out.println("====================================");
                System.out.println(ventas);
                System.out.println("====================================");
            }
            break;
            case 2://Por comprador
        	    System.out.println("Dame Un comprador, Nombre del comprador");
        	    String nomComprador =scanner.nextLine();
        	    for(Ventas ventas : Venta) {
        	    	if(ventas.getCompradores().getNombre().equals(nomComprador)) {
        	    		System.out.println("====================================");
        	    		System.out.println(ventas);
        	    		System.out.println("====================================");
        	    		break;
        	    	}else {
        	    		System.out.println("El nombre no pertenece a un comprador.");
        	    		break;
        	    	}
        	    }
            case 3://Por vendedor
        	    System.out.println("Dame Un Vendedor, Nombre del Vendedor");
        	    String nomVendedor =scanner.nextLine();
        	    for(Ventas ventas : Venta) {
        	    	if(ventas.getVendedores().getNombre().equals(nomVendedor)) {
        	    		System.out.println("====================================");
        	    		System.out.println(ventas);
        	    		System.out.println("====================================");
        	    		break;
        	    	}else {
        	    		System.out.println("El nombre no pertenece a un Vendedor.");
        	    		break;
        	    	}
        	    }
            case 4://Por producto
        	    System.out.println("Dame Un Vendedor, Nombre del Vendedor");
        	    String nomProducto =scanner.nextLine();
        	    for(Ventas ventas : Venta) {
        	    	if(ventas.getProducto().getNombre().equals(nomProducto)) {
        	    		System.out.println("====================================");
        	    		System.out.println(ventas);
        	    		System.out.println("====================================");
        	    		break;
        	    	}else {
        	    		System.out.println("El nombre no pertenece a un Producto.");
        	    		break;
        	    	}
        	    }
        	    default: {
        	    	System.out.println("Opcion no valida");
        	    }
		}
	}
	//////////////////////////////////////////////
	//Al igual que con las ventas, da informacion de los produtos, o todos o por propietario
	private static void InfoProductos() {
		System.out.println("////////////////////////////////////////");
		System.out.println("Productos");
		System.out.println("1)Todos\n2)Por propietario");
		System.out.println("////////////////////////////////////////");
		int opcion = new Scanner(System.in).nextInt();
		switch (opcion) {
		case 1://todos
			for(Productos Producto : Productos) {
				System.out.println("====================================");
				System.out.println(Producto.getId());
				System.out.println(Producto.getNombre());
				System.out.println(Producto.getDescripcion());
				System.out.println(Producto.getPrecio());
				System.out.println("====================================");
			}
			break;
		case 2://Por propietario
			System.out.println("Dame un Vendedor");
			String nomVendedor =scanner.nextLine();
	        for (Vendedor vendedor : Vendedor) {
	            if (vendedor.getNombre().equals(nomVendedor)) {
	            	System.out.println("====================================");
	            	System.out.println(vendedor.getProducto());
	            	System.out.println("====================================");
	            }
	        }
	        break;
	        default:
	        	System.out.println("Option no Valida");
		}
	}
	////////////////////////////////////////////////////////
	//Informacion de los suario, e puede filtar por categoria
	private static void InfoUser() {
		System.out.println("////////////////////////////////////////");
		System.out.println("Usuarios");
		System.out.println("1)Todos\n2)Por nombre");
		System.out.println("////////////////////////////////////////");
		int opcion = new Scanner(System.in).nextInt();
		switch (opcion) {
		case 1://Todos 
			System.out.println("====================================");
			System.out.println("Ordinarios://///////////////////////////////");
			for (Comprador usuario : Comprador) {
				System.out.println("====================================");
			    System.out.println(usuario.getIDUsuario());
			    System.out.println(usuario.getNombre());
			    System.out.println(usuario.getPassword());
			    System.out.println(usuario.getEmail());
			    System.out.println(usuario.getSaldo());
			    System.out.println("====================================");
			}
			System.out.println("Vendedor://///////////////////////////////");
			for(Vendedor usuario : Vendedor) {
				 System.out.println("====================================");
                System.out.println(usuario.getIDUsuario());
			    System.out.println(usuario.getNombre());
			    System.out.println(usuario.getPassword());
			    System.out.println(usuario.getEmail());
			    System.out.println(usuario.getSaldo());
			    System.out.println("====================================");
            }
			break;
		case 2://soliciamos que diga la categoria
			Scanner scanner = new Scanner(System.in);
			System.out.println("////////////////////////////////////////");
			System.out.println("Dime: \n1)Ordinario \n2)Vendedor");
			System.out.println("////////////////////////////////////////");
			int opcion1 = new Scanner(System.in).nextInt();
			switch (opcion1) {
				case 1://Comprador por nombre
					System.out.println("Dame un nombre");
					String nombre = scanner.nextLine();
					for(Comprador usuario : Comprador) {
						if(usuario.getNombre().equals(nombre)) {
							 System.out.println("====================================");
						    System.out.println(usuario.getIDUsuario());
						    System.out.println(usuario.getNombre());
						    System.out.println(usuario.getPassword());
						    System.out.println(usuario.getEmail());
						    System.out.println(usuario.getSaldo());
						    System.out.println("====================================");
						    break;
						}else{
							System.out.println("No encontrado");
						}
					}
					break;
				case 2://Vendedr por nombre
					System.out.println("Dame un nombre");
					String nombre1 = scanner.nextLine();
					for(Vendedor usuario : Vendedor) {
						if(usuario.getNombre().equals(nombre1)) {
							 System.out.println("====================================");
						    System.out.println(usuario.getIDUsuario());
						    System.out.println(usuario.getNombre());
						    System.out.println(usuario.getPassword());
						    System.out.println(usuario.getEmail());
						    System.out.println(usuario.getSaldo());
						    System.out.println("====================================");
						    break;
						}else{
							System.out.println("No encontrado");
						}
					}
				default:
					System.out.println("Option no Valida");
			}
			break;
			default:
				System.out.println("Option no Valida");
		}
	}
}