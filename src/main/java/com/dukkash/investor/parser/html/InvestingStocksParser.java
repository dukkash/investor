package com.dukkash.investor.parser.html;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dukkash.investor.exception.InvestorException;
import com.dukkash.investor.model.Company;
import com.dukkash.investor.model.Country;
import com.dukkash.investor.service.CompanyService;
import com.dukkash.investor.service.CountryService;

@Component
public class InvestingStocksParser {

	@Autowired
	private CompanyService companyService;

	@Autowired
	private CountryService countryService;

	private static final String TURKEY_ALL_COMPANIES = "https://www.investing.com/indices/ise-all-shares-components";
	private static final String CODE_TURKEY = "TR";

	public void updateCompanyPricesOfBIST() throws InvestorException {
		List<Company> companies;
		try {
			companies = companyService.getAllByCountryCode(CODE_TURKEY);
		} catch (InvestorException e) {
			System.out.println("No companies found.");
			throw e;
		}

		if (companies == null) {
			System.out.println("No companies found.");
			return;
		}

		Map<String, Company> companiesByUrl = new HashMap<>(companies.size());

		for (Company c : companies) {
			companiesByUrl.put(c.getStockUrl(), c);
		}

		try {
			updatePrices(companiesByUrl);
		} catch (IOException e) {
			throw new InvestorException(e);
		}
	}

	private void updatePrices(Map<String, Company> companiesByUrl) throws IOException {
		org.jsoup.nodes.Document doc = Jsoup.connect(TURKEY_ALL_COMPANIES).get();
		org.jsoup.nodes.Element stockElements = doc.getElementById("cr1");

		Elements elements = stockElements.getElementsByTag("tbody");
		org.jsoup.nodes.Element element = elements.get(0);

		for (org.jsoup.nodes.Node nodeTR : element.childNodes()) {
			String idStr = nodeTR.attr("id");
			String id = idStr.substring(idStr.indexOf("_") + 1);
			Elements price = doc.getElementsByClass("pid-" + id + "-last");

			for (Node child : nodeTR.childNodes()) {
				if (child.childNodes().size() == 2) {
					String title = child.childNode(0).attr("title");
					String href = child.childNode(0).attr("href");

					if (title == null || title.isEmpty()) {
						TextNode t = (TextNode) child.childNode(1).childNode(0);
						title = t.getWholeText();
						href = child.childNode(1).attr("href");

					} else {
						TextNode t = (TextNode) child.childNode(0).childNode(0);
						href = child.childNode(0).attr("href");
						title = t.getWholeText();
					}
					
					Company com = companiesByUrl.get("https://www.investing.com" + href);

					if (com != null) {
						com.setPrice(new BigDecimal(price.get(0).text().trim()));
						companyService.update(com);
					}
				}
			}
		}
	}

	public List<Company> getStocks(String countryCode, String url) {
		List<Company> stocks = new ArrayList<Company>();

		try {
			org.jsoup.nodes.Document doc = Jsoup.connect(url).get();
			org.jsoup.nodes.Element stockElements = doc.getElementById("cr1");

			Elements elements = stockElements.getElementsByTag("tbody");
			org.jsoup.nodes.Element element = elements.get(0);

			for (org.jsoup.nodes.Node nodeTR : element.childNodes()) {
				String idStr = nodeTR.attr("id");
				String id = idStr.substring(idStr.indexOf("_") + 1);
				Elements price = doc.getElementsByClass("pid-" + id + "-last");

				for (Node child : nodeTR.childNodes()) {
					if (child.childNodes().size() == 2) {
						String title = child.childNode(0).attr("title");
						String href = child.childNode(0).attr("href");

						if (title == null || title.isEmpty()) {
							TextNode t = (TextNode) child.childNode(1).childNode(0);
							title = t.getWholeText();
							href = child.childNode(1).attr("href");

						} else {
							TextNode t = (TextNode) child.childNode(0).childNode(0);
							href = child.childNode(0).attr("href");
							title = t.getWholeText();
						}
						Company stock = new Company();
						stock.setStockUrl("https://www.investing.com" + href);
						stock.setName(title);
						stock.setPrice(new BigDecimal(price.get(0).text()));

						stocks.add(stock);
					}
				}
			}

			int i = 0;
			Country country = countryService.getCountryByCode(countryCode);

			for (Company s : stocks) {
				if (s.getName().equals("Denge Yatirim")) {

					return stocks;
				} else if (i == 0) {
					// continue;
				}

				doc = Jsoup.connect(s.getStockUrl()).get();
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				s.setTickerSymbol(doc.title().substring(0, doc.title().indexOf("|") - 1).trim());
				s.setPrice(new BigDecimal(
						((TextNode) doc.getElementById("last_last").childNode(0)).getWholeText().trim()));

				System.out.println(i + "." + s.getName());
				System.out.println(s.getPrice());

				System.out.println(s.getTickerSymbol());
				System.out.println(s.getStockUrl());
				i++;
				s.setCountry(country);
				Company company = companyService.getCompanyByTickerSymbol(s.getTickerSymbol());

				if (company != null) {
					company.setStockUrl(s.getStockUrl());
					company.setPrice(s.getPrice());
					companyService.update(company);
				} else {
					companyService.save(s);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return stocks;
	}

	public void parseStock() {
		List<Company> stocks = companyService.getAll();
		try {
			for (Company stock : stocks) {

				if (stock.getId().intValue() < 94) {
					continue;
				}

				org.jsoup.nodes.Document doc;
				doc = Jsoup.connect(stock.getStockUrl()).get();
				Elements elements = doc.getElementsByAttributeValueContaining("class", "overviewDataTable");

				Element elem = elements.get(0);

				for (org.jsoup.nodes.Node node : elem.childNodes()) {

					if (node.childNodes().size() == 0) {
						continue;
					}

					Node currentNode = node.childNode(0).childNode(0);
					TextNode t = (TextNode) currentNode;

					if (t.text().startsWith("Dividend")) {
						TextNode secondNode = (TextNode) node.childNode(1).childNode(0);

						if (!secondNode.text().contains("N/A")) {
							String divPerShare = secondNode.text().split(" ")[0];

						}
					} else if (t.text().startsWith("Shares")) {
						TextNode secondNode = (TextNode) node.childNode(1).childNode(0);

						if (!secondNode.text().isEmpty()) {
							BigDecimal shares = new BigDecimal(secondNode.text().replaceAll(",", ""));

						}
					} else if (t.text().startsWith("Average")) {
						TextNode secondNode = (TextNode) node.childNode(1).childNode(0);

						if (!secondNode.text().isEmpty()) {
							BigDecimal averageVolume = new BigDecimal(secondNode.text().replaceAll(",", ""));

						}
					}
				}

				companyService.update(stock);

				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		} catch (

		IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
