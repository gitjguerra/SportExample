package repositories;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.sql.*;
import java.util.*;

import play.db.*;

import models.Customer;

@Singleton
public class SportRepository {
    @Inject Database database;

    public List<Customer> findAll() throws SQLException {
        PreparedStatement preparedStatement = database.getConnection().prepareStatement("select * from customers");

        List<Customer> customers = new ArrayList<>();

        preparedStatement.execute();

        ResultSet rs = preparedStatement.getResultSet();

        while (rs.next()) {
            Customer customer = new Customer();

            customer.setId(rs.getInt("id"));
			customer.setDni(rs.getInt("dni"));
			customer.setNombre(rs.getString("nombre"));
			customer.setApellido(rs.getString("apellido"));
			customer.setTelefono(rs.getString("telefono"));
			customer.setEmail(rs.getString("email"));

            customers.add(customer);
        }

        return customers;
    }

    public Optional<Customer> findById(int id) throws SQLException {
        PreparedStatement preparedStatement = database.getConnection().prepareStatement("select * from customers where id = ?");

        preparedStatement.setInt(1, id);

        preparedStatement.execute();

		return chargeDTO(preparedStatement);
    }

    public Optional<Customer> findByDni(int dni) throws SQLException {
        PreparedStatement preparedStatement = database.getConnection().prepareStatement("select * from customers where dni = ?");

        preparedStatement.setInt(1, dni);

        preparedStatement.execute();

		return chargeDTO(preparedStatement);
    }

    public Optional<Customer> findByNombre(String nombre) throws SQLException {
        PreparedStatement preparedStatement = database.getConnection().prepareStatement("select * from customers where nombre = ?");

        preparedStatement.setString(1, nombre);

        preparedStatement.execute();

		return chargeDTO(preparedStatement);
    }

    public Optional<Customer> findByApellido(String apellido) throws SQLException {
        PreparedStatement preparedStatement = database.getConnection().prepareStatement("select * from customers where apellido = ?");

        preparedStatement.setString(1, apellido);

        preparedStatement.execute();

		return chargeDTO(preparedStatement);
    }

    public Optional<Customer> findByTelefono(String telefono) throws SQLException {
        PreparedStatement preparedStatement = database.getConnection().prepareStatement("select * from customers where telefono = ?");

        preparedStatement.setString(1, telefono);

        preparedStatement.execute();

		return chargeDTO(preparedStatement);
    }
	
    public Optional<Customer> findByEmail(String email) throws SQLException {
        PreparedStatement preparedStatement = database.getConnection().prepareStatement("select * from customers where email = ?");

        preparedStatement.setString(1, email);

        preparedStatement.execute();

		return chargeDTO(preparedStatement);
    }
	
    public void delete(int id) throws SQLException {
        PreparedStatement preparedStatement = database.getConnection().prepareStatement("delete from customers where id = ?");

        preparedStatement.setInt(1, id);

        preparedStatement.execute();
    }

    public void add(Customer customer) throws SQLException {
        PreparedStatement preparedStatement = database.getConnection().prepareStatement("insert into customers (id, dni, nombre, apellido, telefono, email) values (null, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, customer.getDni());
        preparedStatement.setString(2, customer.getNombre());
        preparedStatement.setString(3, customer.getApellido());
        preparedStatement.setString(4, customer.getTelefono());
        preparedStatement.setString(5, customer.getEmail());
        preparedStatement.execute();

        ResultSet rs = preparedStatement.getGeneratedKeys();
        if (rs.next()) {
            customer.setId(rs.getInt(1));
        }
        preparedStatement.executeUpdate();
    }

    public void update(Customer customer) throws SQLException {
        PreparedStatement preparedStatement = database.getConnection().prepareStatement("update customers set dni = ?, nombre = ?, apellido = ?, telefono = ?, email = ?  where id = ?");
        preparedStatement.setInt(1, customer.getDni());
        preparedStatement.setString(2, customer.getNombre());
        preparedStatement.setString(3, customer.getApellido());
        preparedStatement.setString(4, customer.getTelefono());
        preparedStatement.setString(5, customer.getEmail());
        preparedStatement.setInt(6, customer.getId());

        preparedStatement.executeUpdate();
    }

    private Optional<Customer> chargeDTO(PreparedStatement preparedStatement) throws SQLException {

	    ResultSet rs = preparedStatement.getResultSet();

        if (rs.next()) {
            Customer customer = new Customer();

            customer.setId(rs.getInt("id"));
			customer.setDni(rs.getInt("dni"));
			customer.setNombre(rs.getString("nombre"));
			customer.setApellido(rs.getString("apellido"));
			customer.setTelefono(rs.getString("telefono"));
			customer.setEmail(rs.getString("email"));

            return Optional.of(customer);
        }
        return Optional.empty();		
    }	
	
}
