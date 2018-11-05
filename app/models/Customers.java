package models;

import io.ebean.Finder;
import io.ebean.Model;
import javax.persistence.*;

@Entity
public class Customers extends Model {
    @Id
    public int id;
	private int dni;
	private String nombre;
    private String apellido;
	private String telefono;

	// ********* With the correct import is posible validate with annotations **************
	//@MaxSize(value=255, message = “email.maxsize”)
    //@play.data.validation.Email
    // ********* With the correct import is posible validate with annotations **************
	private String email;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

	public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static final Finder<Integer, Customers> find = new Finder<>(Customers.class);
}
