package org.jboss.test.jpa.CacheableXmlTestCase.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@SuppressWarnings("serial")
@Table(name = "CUSTOMER_HIBER")
public class CustomerHibernateCached implements Serializable, ICustomer {

	@Id
	@GeneratedValue
	@Column(name = "CUST_ID")
	protected Long id;

	@NotNull
	@Size(min = 1, max = 25)
	@Pattern(regexp = "[A-Za-z ]*", message = "must contain only letters and spaces")
	@Column(name = "CUST_NAME")
	protected String cust_name;

	@Column(name = "CUST_CREDIT")
	protected Integer availableCredit;

	public CustomerHibernateCached() {

	}

	public CustomerHibernateCached(String name, Integer credit) {
		this.cust_name = name;
		this.availableCredit = credit;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return cust_name;
	}

	public void setName(String name) {
		this.cust_name = name;
	}

	public Integer getAvailableCredit() {
		return availableCredit;
	}

	public void setAvailableCredit(Integer availableCredit) {
		this.availableCredit = availableCredit;
	}	
	@Override
	public Class<?> getCustomerClass() {
		return this.getClass();
	}

	@Override
	public String getEntityTable() {
		return "CUSTOMER_HIBER";
	}

}