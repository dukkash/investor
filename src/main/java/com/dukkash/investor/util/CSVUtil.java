package com.dukkash.investor.util;

import com.dukkash.investor.model.BalanceSheet;
import com.dukkash.investor.model.CashFlow;
import com.dukkash.investor.model.IncomeStatement;
import com.dukkash.investor.model.QuarterlyData;

import java.io.*;
import java.math.BigDecimal;

public class CSVUtil {

    public void parse10K(String symbol, String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));
        String line = null;
        String[] tokens;

        QuarterlyData q1 = initQuarter();
        QuarterlyData q2 = initQuarter();
        QuarterlyData q3 = initQuarter();
        QuarterlyData q4 = initQuarter();

        line = reader.readLine();
        tokens = line.split(",");

        String[] nameParts = tokens[1].split("/");
        q1.setName(nameParts[0]+ "-Q" +getQuarter(nameParts[1]));

        nameParts = tokens[2].split("/");
        q2.setName(nameParts[0]+ "-Q" +getQuarter(nameParts[1]));

        nameParts = tokens[3].split("/");
        q3.setName(nameParts[0]+ "-Q" +getQuarter(nameParts[1]));

        nameParts = tokens[4].split("/");
        q4.setName(nameParts[0]+ "-Q" +getQuarter(nameParts[1]));

        while((line = reader.readLine()) != null) {
            System.out.println(line);
            tokens = line.split(",");

            if(line.startsWith("D�nen Varl�klar")) { // current assets
                q4.getBalanceSheet().setTotalCurrentAssets(new BigDecimal(tokens[tokens.length-1]));
                q3.getBalanceSheet().setTotalCurrentAssets(new BigDecimal(tokens[tokens.length-2]));
                q2.getBalanceSheet().setTotalCurrentAssets(new BigDecimal(tokens[tokens.length-3]));
                q1.getBalanceSheet().setTotalCurrentAssets(new BigDecimal(tokens[tokens.length-4]));
            } else if(line.startsWith("Nakit ve Nakit Benzerleri")) { // cash and equv.
                q4.getBalanceSheet().setCash(new BigDecimal(tokens[tokens.length-1]));
                q3.getBalanceSheet().setCash(new BigDecimal(tokens[tokens.length-2]));
                q2.getBalanceSheet().setCash(new BigDecimal(tokens[tokens.length-3]));
                q1.getBalanceSheet().setCash(new BigDecimal(tokens[tokens.length-4]));
            } else if(line.startsWith("Stoklar")) {
                q4.getBalanceSheet().setInventories(new BigDecimal(tokens[tokens.length-1]));
                q3.getBalanceSheet().setInventories(new BigDecimal(tokens[tokens.length-2]));
                q2.getBalanceSheet().setInventories(new BigDecimal(tokens[tokens.length-3]));
                q1.getBalanceSheet().setInventories(new BigDecimal(tokens[tokens.length-4]));
            } else if(line.startsWith("TOPLAM VARLIKLAR")) {
                q4.getBalanceSheet().setTotalAssets(new BigDecimal(tokens[tokens.length-1]));
                q3.getBalanceSheet().setTotalAssets(new BigDecimal(tokens[tokens.length-2]));
                q2.getBalanceSheet().setTotalAssets(new BigDecimal(tokens[tokens.length-3]));
                q1.getBalanceSheet().setTotalAssets(new BigDecimal(tokens[tokens.length-4]));
            } else if(line.startsWith("K�sa Vadeli Y�k�ml�l�kler")) {
                q4.getBalanceSheet().setTotalCurrentLiabilities(new BigDecimal(tokens[tokens.length-1]));
                q3.getBalanceSheet().setTotalCurrentLiabilities(new BigDecimal(tokens[tokens.length-2]));
                q2.getBalanceSheet().setTotalCurrentLiabilities(new BigDecimal(tokens[tokens.length-3]));
                q1.getBalanceSheet().setTotalCurrentLiabilities(new BigDecimal(tokens[tokens.length-4]));
            } else if(line.startsWith("Finansal Borçlar")) {
                q4.getBalanceSheet().setShortTermDebt(new BigDecimal(tokens[tokens.length-1]));
                q3.getBalanceSheet().setShortTermDebt(new BigDecimal(tokens[tokens.length-2]));
                q2.getBalanceSheet().setShortTermDebt(new BigDecimal(tokens[tokens.length-3]));
                q1.getBalanceSheet().setShortTermDebt(new BigDecimal(tokens[tokens.length-4]));
            } else if(line.startsWith("Stoklar")) {
                q4.getBalanceSheet().setInventories(new BigDecimal(tokens[tokens.length-1]));
                q3.getBalanceSheet().setInventories(new BigDecimal(tokens[tokens.length-2]));
                q2.getBalanceSheet().setInventories(new BigDecimal(tokens[tokens.length-3]));
                q1.getBalanceSheet().setInventories(new BigDecimal(tokens[tokens.length-4]));
            }
        }
    }

    private String getQuarter(String namePart) {
        if(namePart.equals("12")) {
            return "4";
        } else if(namePart.equals("9")) {
            return "3";
        } else if(namePart.equals("6")) {
            return "2";
        }
        return "1";
    }

    public QuarterlyData initQuarter() {
        QuarterlyData q1 = new QuarterlyData();
        BalanceSheet bs = new BalanceSheet();
        IncomeStatement is = new IncomeStatement();
        CashFlow cf = new CashFlow();

        q1.setBalanceSheet(bs);
        q1.setIncomeStatement(is);
        q1.setCashFlow(cf);

        return q1;
    }
}
