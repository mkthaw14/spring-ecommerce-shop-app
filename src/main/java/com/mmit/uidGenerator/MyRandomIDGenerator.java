package com.mmit.uidGenerator;

import java.io.Serializable;
import java.rmi.server.UID;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class MyRandomIDGenerator implements IdentifierGenerator {

	public static final String generatorName = "myGenerator";
	
	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		// TODO Auto-generated method stub
		return UUID.randomUUID().toString().replace("-", "");
	}

}
