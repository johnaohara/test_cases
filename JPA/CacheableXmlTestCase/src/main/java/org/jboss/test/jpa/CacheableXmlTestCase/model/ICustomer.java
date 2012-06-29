package org.jboss.test.jpa.CacheableXmlTestCase.model;

public interface ICustomer {
    public Long getId();

    public void setId(Long id);

    public String getName();

    public void setName(String name);

    public Integer getAvailableCredit();

	public void setAvailableCredit(Integer availableCredit);

	public Class<?> getCustomerClass();
	
	public String getEntityTable();
}
