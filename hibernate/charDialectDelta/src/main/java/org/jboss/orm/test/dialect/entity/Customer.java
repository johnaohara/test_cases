package org.jboss.orm.test.dialect.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "customer")
public class Customer implements java.io.Serializable {

	@Id()
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "c_id", nullable = false)
	private int id;

	@Column(name = "c_phone")
	private String CPhone;

	public Customer() {
	}

	public Customer(int CId) {
		this.id = CId;
	}

	public Customer(int CId, String CPhone) {
		this.id = CId;
		this.CPhone = CPhone;
	}

	public Customer(String CPhone) {
		this.CPhone = CPhone;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int CId) {
		this.id = CId;
	}

	public String getPhone() {
		return this.CPhone;
	}

	public void setPhone(String CPhone) {
		this.CPhone = CPhone;
	}

}
