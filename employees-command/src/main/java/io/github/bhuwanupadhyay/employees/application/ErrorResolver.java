package io.github.bhuwanupadhyay.employees.application;

import io.github.bhuwanupadhyay.employees.application.EmployeeHandler.ErrorResource;


public interface ErrorResolver {

	ErrorResource resolve(String errorId, Object... params);

	class NoTranslationFoundException extends RuntimeException {

		private final String errorId;

		public NoTranslationFoundException(String errorId) {
			super("No translation found for an error id: " + errorId);
			this.errorId = errorId;
		}

		public String getErrorId() {
			return errorId;
		}
	}

}
