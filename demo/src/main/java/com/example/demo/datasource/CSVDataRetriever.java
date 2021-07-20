package com.example.demo.datasource;

import com.example.demo.data.RefData;
import com.example.demo.data.Trade;
import com.example.demo.data.Valuation;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Component
public class CSVDataRetriever implements DataRetriever {

    private static final String FILE_ROOT = "src/main/resources/";

    /*
        Quick and dirty option
        better option to stream to object with jackson don't have time to implement
     */
    @Override
    public List<Trade> getTradeData() throws IOException, ParseException {
        var br = new BufferedReader(new FileReader(FILE_ROOT + "trade.csv"));
        List<Trade> trades = new ArrayList<>();
        String line = br.readLine(); //remove headers
        while ((line = br.readLine()) != null) {
            String[] csv = line.split(",");
            Trade trade = new Trade();
            trade.setInventory(csv[0]);
            trade.setBook(csv[1]);
            trade.setSystem(csv[2]);
            trade.setLegalEntity(csv[3]);
            trade.setTradeId(Long.parseLong(csv[4]));
            trade.setVersion(Integer.parseInt(csv[5]));
            trade.setTradeStatus(csv[6]);
            trade.setProductType(csv[7]);
            trade.setResettingLeg(csv[8]);
            trade.setProductSubType(csv[9]);
            trade.setTdsProductType(csv[10]);
            trade.setSecCodeSubType(csv[11]);
            trade.setSwapType(csv[12]);
            trade.setDescription(csv[13]);
            trade.setTradeDate(parseDate(csv[14]));
            trade.setStartDate(parseDate(csv[15]));
            trade.setMaturityDate(parseDate(csv[16]));

            trades.add(trade);
        }
        return trades;
    }

    @Override
    public List<Valuation> getValuationData() throws IOException {
        var br = new BufferedReader(new FileReader(FILE_ROOT + "valuation.csv"));
        List<Valuation> valuations = new ArrayList<>();
        String line = br.readLine(); //remove headers
        while ((line = br.readLine()) != null) {
            String[] csv = line.split(",");
            Valuation valuation = new Valuation();
            valuation.setTradeId(Long.parseLong(csv[0]));
            valuation.setUqlOcMmbMs(Double.parseDouble(csv[1]));
            valuation.setUqlOcMmbMsPc(Double.parseDouble(csv[2]));

            valuations.add(valuation);
        }
        return valuations;
    }

    @Override
    public List<RefData> getRefData() throws IOException {
        var br = new BufferedReader(new FileReader(FILE_ROOT + "refdata.csv"));
        List<RefData> refDataList = new ArrayList<>();
        String line = br.readLine(); //remove headers
        while ((line = br.readLine()) != null) {
            String[] csv = line.split(",");
            RefData refData = new RefData();
            refData.setTopOfHouse(csv[0]);
            refData.setSegment(csv[1]);
            refData.setViceChair(csv[2]);
            refData.setGlobalBusiness(csv[3]);
            refData.setPolicy(csv[4]);
            refData.setDesk(csv[5]);
            refData.setPortfolio(csv[6]);
            refData.setBu(Integer.parseInt(csv[7]));
            refData.setCline(csv[8]);
            refData.setInventory(csv[9]);

            refDataList.add(refData);
        }
        return refDataList;
    }

    private Date parseDate(String dateString) throws ParseException {
        String year = dateString.substring(0, 4);
        String month = dateString.substring(4, 6);
        String day = dateString.substring(6, 8);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.parse(year+"-"+month+"-"+day);
    }
}
