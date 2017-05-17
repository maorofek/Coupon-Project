package main;

import java.util.Collection;

public class WebCustomer {

	// Member Declaration
	private long id;
	private String custName;
	private String password;
	private Collection<Coupon> coupons;

	// Constructors

	/**
	 * 
	 */
	public WebCustomer() {
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
	 * @return the custName
	 */
	public String getCustName() {
		return this.custName;
	}

	/**
	 * @return the password
	 */

	/**
	 * @return the coupons
	 */
	public Collection<Coupon> getCoupons() {
		return this.coupons;
	}

	// Setters

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @param custName
	 *            the custName to set
	 */
	public void setCustName(String custName) {
		this.custName = custName;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
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
		return String.format("Customer [id=%s, custName=%s, password=%s, coupons=%s]", this.id, this.custName,
				this.password, this.coupons);
	}

}