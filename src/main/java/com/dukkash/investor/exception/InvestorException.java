package com.dukkash.investor.exception;

public class InvestorException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public InvestorException(String message) {
		super(message);
	}
	
	public InvestorException(Throwable e) {
		super(e);
	}

}
