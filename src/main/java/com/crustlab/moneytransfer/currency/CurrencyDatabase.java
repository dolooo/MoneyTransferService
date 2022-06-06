package com.crustlab.moneytransfer.currency;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CurrencyDatabase {
    private Map<String, Currency> currenciesMap = Collections.emptyMap();
    private static CurrencyDatabase instance = null;

    private CurrencyDatabase() {
        try {
            this.currenciesMap = getCurrencies();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static CurrencyDatabase getInstance(){
        try {
            if (instance == null) {
                instance = new CurrencyDatabase();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return instance;
    }

    Map<String,Currency> getCurrencies() throws IOException, InterruptedException, ParserConfigurationException, SAXException {

        CurrencyDataProvider dataProvider = new CurrencyDataProvider();
        Document doc = dataProvider.GetDocument();

        int length = doc.getElementsByTagName("pozycja").getLength();

        Map<String,Currency> currencyMap = new HashMap<>();
        currencyMap.put("PLN",new Currency("złoty (Polska)","1","PLN","1.000"));

        for (int i = 0; i < length; i++) {
            if (doc.getElementsByTagName("kod_waluty").item(i).getTextContent().equals("USD")) {
                addCurrency(doc, currencyMap, i);
            }
            if (doc.getElementsByTagName("kod_waluty").item(i).getTextContent().equals("EUR")) {
                addCurrency(doc, currencyMap, i);
            }
        }
        return currencyMap;
    }

    private void addCurrency(Document doc, Map<String, Currency> currencyMap, int i) {
        String name = doc.getElementsByTagName("nazwa_waluty").item(i).getTextContent();
        String converter = doc.getElementsByTagName("przelicznik").item(i).getTextContent();
        String code = doc.getElementsByTagName("kod_waluty").item(i).getTextContent();
        String exchange = doc.getElementsByTagName("kurs_sredni").item(i).getTextContent();
        currencyMap.put(code,new Currency(name,converter,code,exchange));
    }

    public void printCurrencies() {
        System.out.println("Kursy walut:");
        for (String s : currenciesMap.keySet()) {
            Currency c = currenciesMap.get(s);
            System.out.format("%s (%s) - %s , przelicznik - %s\n",c.getName(),c.getCode(),c.getExchange(),c.getConverter());
        }
    }

    public Currency getCurrencyByCode(String code) {
        return currenciesMap.get(code);
    }

    public boolean isInvalidCurrencyCode(String name) {
        return !currenciesMap.containsKey(name);
    }
}
