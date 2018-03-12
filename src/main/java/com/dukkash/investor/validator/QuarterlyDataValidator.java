package com.dukkash.investor.validator;

import javax.validation.ValidationException;

import com.dukkash.investor.ui.model.PeriodModel;

public class QuarterlyDataValidator {

	public void validateShorQuarterlyDataModel(PeriodModel model) throws ValidationException {

		if (model.getName() == null || model.getName().equals("")) {
			throw new ValidationException("Quarterly Data name is required field.");
		} else if (model.getTickerSymbol() == null || model.getTickerSymbol().equals("")) {
			throw new ValidationException("Quarterly Data Ticker Symbol is required field.");
		} else if (model.getRevenue() == null || model.getRevenue().equals("")) {
			throw new ValidationException("Quarterly Data revenue is required field.");
		} else if (model.getTotalAssets() == null || model.getTotalAssets().equals("")) {
			throw new ValidationException("Quarterly Data Total Assets is required field.");
		} else if ((model.getTotalLiabilities() == null || model.getTotalLiabilities().equals(""))
				&& (model.getEquity() == null || model.getEquity().equals(""))) {
			throw new ValidationException("Quarterly Data must have one of Total Liabilities or equity fields.");
		} else if (model.getCurrentAssets() == null || model.getCurrentAssets().equals("")) {
			throw new ValidationException("Quarterly Data Current Assets is required field.");
		} else if (model.getCurrentLiabilities() == null || model.getCurrentLiabilities().equals("")) {
			throw new ValidationException("Quarterly Data Current Liabilities is required field.");
		} else if (model.getCash() == null || model.getCash().equals("")) {
			throw new ValidationException("Quarterly Data cash is required field.");
		} else if (model.getTotalDebt() == null || model.getTotalDebt().equals("")) {
			throw new ValidationException("Quarterly Data Total debt is required field.");
		}
	}

}
