package dao;

import java.sql.SQLException;
import java.util.Collection;

import main.Company;
import main.Coupon;
import main.Customer;

public interface CustomerDAO {

	public void createCustomer(Customer customer) throws SQLException, InterruptedException;

	public void removeCustomer(Customer customer) throws SQLException, InterruptedException;

	public void updateCustomer(Customer customer) throws SQLException, InterruptedException;

	public Customer getCustomer(long id) throws SQLException, InterruptedException;

	public Collection<Customer> getAllCustomer() throws SQLException, InterruptedException;

	public Collection<Coupon> getCoupons(Customer customer) throws SQLException, InterruptedException;

	public boolean login(String compName, String password) throws SQLException, InterruptedException;
}
