package principal;
public class Productos {
	private int Id;
	private String Nombre;
	private String Descripcion;
	private double Precio;
	public Productos(int Id, String Nombre, String Descripcion, double Precio) {	
		super();
        this.Id = Id;
        this.Nombre = Nombre;
        this.Descripcion = Descripcion;
        this.Precio = Precio;
	}
	public int getId() {
        return Id;
    }
	public void setId(int id) {
        Id = id;
    }
	public String getNombre() {
        return Nombre;
    }
	public void setNombre(String nombre) {
        Nombre = nombre;
    }
	public String getDescripcion() {
        return Descripcion;
    }
	public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }
	public double getPrecio() {
        return Precio;
    }
	public void setPrecio(double precio) {
        Precio = precio;
    }
	@Override
    public String toString() {
        return "\nProductos:----------------------- \nId=" + Id + "\nNombre=" + Nombre + "\nDescripcion=" + Descripcion + "\nPrecio=" + Precio ;
    }
}
