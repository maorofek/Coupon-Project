package main;

import java.util.Collection;

public class WebCompany {

	// Members Declarations

	private long id;
	private String compName;
	private String password;
	private String email;
	private Collection<Coupon> coupons;

	// Constructors

	public WebCompany() {
		super();
		// TODO Auto-generated constructor stub
	}

	// Getters

	/**
	 * @return the id
	 */
	public long getId() {
		return this.id;
	}

	/**
	 * @return the compName
	 */
	public String getCompName() {
		return this.compName;
	}

	/**
	 * @return the password
	 */

	/**
	 * @return the email
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * @return the coupons
	 */
	public Collection<Coupon> getCoupons() {
		return this.coupons;
	}

	// Setters

	/**
	 * @param id
	 *            id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @param compName
	 *            the compName to set
	 */
	public void setCompName(String compName) {
		this.compName = compName;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @param coupons
	 *            the coupons to set
	 */
	public void setCoupons(Collection<Coupon> coupons) {
		this.coupons = coupons;
	}

	// toString

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("Company [id=%s, compName=%s, password=%s, email=%s, coupons=%s]", this.id, this.compName,
				this.password, this.email, this.coupons);
	}

}
