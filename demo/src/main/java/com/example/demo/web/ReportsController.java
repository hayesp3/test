package com.example.demo.web;

import com.example.demo.data.RefData;
import com.example.demo.data.Report;
import com.example.demo.data.Trade;
import com.example.demo.data.Valuation;
import com.example.demo.datasource.CSVDataRetriever;
import com.example.demo.datasource.DataRetriever;
import com.example.demo.mapper.FileMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ReportsController {

    private DataRetriever dataRetriever;
    private FileMapper mapper = new FileMapper();

    public ReportsController(CSVDataRetriever dataRetriever) {
        this.dataRetriever = dataRetriever;
    }

    @GetMapping("/reports")
    public ResponseEntity<List<Report>> getReports() {
        try {
            List<Report> reports =  constructReport();
            return new ResponseEntity<>(reports, HttpStatus.OK);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    public List<Report> constructReport() throws IOException, ParseException {
        List<Trade> trades = dataRetriever.getTradeData();
        List<Valuation> valuations = dataRetriever.getValuationData();
        List<RefData> refDataList = dataRetriever.getRefData();
        List<Report> reports = new ArrayList<>();

        for (Trade trade: trades) {
            Report report = mapper.mapTradeToReport(trade);
            Valuation valuation = matchValuationToTrade(report.getTradeId(), valuations);
            if(valuation != null) {
                report = mapper.mapValuationToReport(valuation, report);
            }

            RefData refData = matchRefDataToTrade(report.getInventory(), refDataList);
            if(refData != null) {
                report = mapper.mapRefDataToReport(refData, report);
            }

            reports.add(report);
        }

        return reports;
    }

    private Valuation matchValuationToTrade(long id, List<Valuation> valuations) {
        for (Valuation valuation: valuations) {
            if(valuation.getTradeId() == id) {
                return valuation;
            }
        }
        return null;
    }

    private RefData matchRefDataToTrade(String inventory, List<RefData> refDataList) {
        for (RefData refData: refDataList) {
            if(refData.getInventory().equals(inventory)) {
                return refData;
            }
        }
        return null;
    }
}
