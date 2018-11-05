package repositories;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.sql.*;
import java.util.*;
import models.Customers;
import play.api.db.Database;
import scala.Option;

@Singleton
public class SportRepository {
    @Inject
    Database database;

    public List<Customers> findAll(Option<String> nombre, Option<String> apellido, Option<Integer> dni, Option<String> telefono, Option<String> email) throws SQLException {

        List<Customers> customers = null;
        if(nombre.isEmpty() && apellido.isEmpty() && dni.isEmpty() && telefono.isEmpty() && email.isEmpty()){
            customers = Customers.db().find(Customers.class).findList();
        }else{
            // With a little more time it is possible to discriminate the parameters that exist and improve the code
            // and make more complex task query
            customers = Customers.find.query().where()
                    .ilike("nombre", nombre.get())
                    .or()
                    .ilike("apellido", apellido.get())
                    .or()
                    .ilike("dni", dni.toString())
                    .or()
                    .ilike("telefono", telefono.get())
                    .or()
                    .ilike("email", email.get())
                    .orderBy("id asc")
                    .setFirstRow(0)
                    .setMaxRows(25)
                    .findPagedList()
                    .getList();
        }

        return customers;
    }

    public void delete(int id) throws SQLException {
        Customers.db().delete(Customers.class, id);
    }

    public void update(Customers customers, Customers customersToBe) throws SQLException {
        customers.setDni(customersToBe.getDni());
        customers.setNombre(customersToBe.getNombre());
        customers.setApellido(customersToBe.getApellido());
        customers.setTelefono(customersToBe.getTelefono());
        customers.setEmail(customersToBe.getEmail());
        customers.update();
    }


    // ******************** Initials examples ***********************
    public List<Customers> findById(int id) throws SQLException {
        // More complex task query
        List<Customers> ids = Customers.find.query().where()
                .ilike("id", "%"+ id +"%")
                .orderBy("id asc")
                .setFirstRow(0)
                .setMaxRows(25)
                .findPagedList()
                .getList();
        return ids;
    }
    public List<Customers> findByDni(int dni) throws SQLException {
        List<Customers> dnis = Customers.find.query().where()
                .ilike("dni", "%"+ dni +"%")
                .orderBy("id asc")
                .setFirstRow(0)
                .setMaxRows(25)
                .findPagedList()
                .getList();
        return dnis;
    }
    public List<Customers> findByNombre(String nombre) throws SQLException {
        List<Customers> names = Customers.find.query().where()
                .ilike("nombre", "%"+ nombre +"%")
                .orderBy("id asc")
                .setFirstRow(0)
                .setMaxRows(25)
                .findPagedList()
                .getList();
        return names;
    }
    public List<Customers> findByApellido(String apellido) throws SQLException {
        List<Customers> apellidos = Customers.find.query().where()
                .ilike("apellido", "%"+ apellido +"%")
                .orderBy("id asc")
                .setFirstRow(0)
                .setMaxRows(25)
                .findPagedList()
                .getList();
        return apellidos;
    }
    public List<Customers> findByTelefono(String telefono) throws SQLException {
        List<Customers> telefonos = Customers.find.query().where()
                .ilike("telefono", "%"+ telefono +"%")
                .orderBy("id asc")
                .setFirstRow(0)
                .setMaxRows(25)
                .findPagedList()
                .getList();
        return telefonos;
    }
    public List<Customers> findByEmail(String email) throws SQLException {
        List<Customers> emails = Customers.find.query().where()
                .ilike("email", "%"+ email +"%")
                .orderBy("id asc")
                .setFirstRow(0)
                .setMaxRows(25)
                .findPagedList()
                .getList();

        return emails;
    }
    // ******************** Initials examples ***********************



    // *************** this code changed by ORM and now this methods are deprecate ********************
    public void add(Customers customers) throws SQLException {
        PreparedStatement preparedStatement = database.getConnection().prepareStatement("insert into customers (id, dni, nombre, apellido, telefono, email) values (null, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, customers.getDni());
        preparedStatement.setString(2, customers.getNombre());
        preparedStatement.setString(3, customers.getApellido());
        preparedStatement.setString(4, customers.getTelefono());
        preparedStatement.setString(5, customers.getEmail());
        preparedStatement.execute();

        ResultSet rs = preparedStatement.getGeneratedKeys();
        if (rs.next()) {
            customers.setId(rs.getInt(1));
        }
        preparedStatement.executeUpdate();
    }
    private Optional<Customers> chargeDTO(PreparedStatement preparedStatement) throws SQLException {
	    ResultSet rs = preparedStatement.getResultSet();
        if (rs.next()) {
            Customers customers = new Customers();
            customers.setId(rs.getInt("id"));
			customers.setDni(rs.getInt("dni"));
			customers.setNombre(rs.getString("nombre"));
			customers.setApellido(rs.getString("apellido"));
			customers.setTelefono(rs.getString("telefono"));
			customers.setEmail(rs.getString("email"));
            return Optional.of(customers);
        }
        return Optional.empty();		
    }
    // *************** this code changed by ORM and now this methods are deprecate ********************

}
