package com.oranbyte.acolyte.listener;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class CustomListener {

	@PrePersist
	public void prePersist(Object entity) {

	}

	@PreUpdate
	public void preUpdate(Object entity) {

	}
}