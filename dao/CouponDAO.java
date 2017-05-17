package dao;

import java.sql.SQLException;
import java.util.Collection;

import main.Coupon;

/**
 * @author Dan Aronson
 *
 */
public interface CouponDAO {

	public void createCoupon(Coupon coupon) throws SQLException, InterruptedException;

	public void removeCoupon(Coupon coupon) throws SQLException, InterruptedException;

	public void updateCoupon(Coupon coupon) throws SQLException, InterruptedException;

	public Coupon getCoupon(long id) throws SQLException, InterruptedException;

	public Collection<Coupon> getAllCoupon() throws SQLException, InterruptedException;

	public Collection<Coupon> getCouponByType(CouponType coupontype) throws SQLException, InterruptedException;
}
