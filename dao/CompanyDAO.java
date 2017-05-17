package dao;

import java.sql.SQLException;
import java.util.Collection;

import main.Company;
import main.Coupon;
import myexception.MyException;

public interface CompanyDAO {

	public void createCompany(Company company) throws SQLException, InterruptedException;

	public void removeCompany(Company company) throws SQLException, InterruptedException, MyException;

	public void updateCompany(Company company) throws SQLException, InterruptedException;

	public Company getCompany(long id) throws SQLException, InterruptedException;

	public Collection<Company> getAllCompanies() throws SQLException, InterruptedException;

	public Collection<Coupon> getCoupons(Company company) throws SQLException, InterruptedException;

	public boolean login(String compName, String password) throws SQLException, InterruptedException;

	public String getCompanyById(long id) throws InterruptedException;

	void removeCompanies(Company company) throws SQLException, InterruptedException;


}
