package com.example.demo.mapper;

import com.example.demo.data.RefData;
import com.example.demo.data.Report;
import com.example.demo.data.Trade;
import com.example.demo.data.Valuation;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class FileMapper {

    public Report mapTradeToReport(Trade trade) throws ParseException {
        Report report =  new Report();
        report.setInventory(trade.getInventory());
        report.setBook(trade.getBook());
        report.setSystem(trade.getSystem());
        report.setLegalEntity(trade.getLegalEntity());
        report.setTradeId(trade.getTradeId());
        report.setVersion(trade.getVersion());
        report.setTradeStatus(trade.getTradeStatus());
        report.setProductType(trade.getProductType());
        report.setResettingLeg(trade.getResettingLeg());
        report.setProductSubType(trade.getProductSubType());
        report.setTdsProductType(trade.getTdsProductType());
        report.setSecCodeSubType(trade.getSecCodeSubType());
        report.setSwapType(trade.getSwapType());
        report.setDescription(trade.getDescription());
        report.setTradeDate(trade.getTradeDate());
        report.setStartDate(trade.getStartDate());
        report.setMaturityDate(trade.getMaturityDate());
        if(report.getMaturityDate() != null && report.getMaturityDate().before(new Date())) {
            LocalDateTime maturityDate = convertToLocalDateTime(report.getMaturityDate());
            long diffInMonths = ChronoUnit.MONTHS.between(LocalDateTime.now(), maturityDate);
            report.setTerm(mapTermRange(diffInMonths));
        }

        return report;
    }

    public Report mapRefDataToReport(RefData refData, Report report) {
        report.setTopOfHouse(refData.getTopOfHouse());
        report.setSegment(refData.getSegment());
        report.setViceChair(refData.getViceChair());
        report.setGlobalBusiness(report.getGlobalBusiness());
        report.setPolicy(refData.getPolicy());
        report.setDesk(refData.getDesk());
        report.setPortfolio(refData.getPortfolio());
        report.setBu(refData.getBu());
        report.setCline(refData.getCline());

        return report;
    }

    public Report mapValuationToReport(Valuation valuation, Report report) {
        report.setUqlOcMmbMs(valuation.getUqlOcMmbMs());
        report.setUqlOcMmbMsPc(valuation.getUqlOcMmbMsPc());
        double dif = Math.abs(valuation.getUqlOcMmbMs() - valuation.getUqlOcMmbMsPc());
        report.setMsMinusPc(dif);
        report.setBreakStatus(mapBreakStatusRange(dif));

        return report;
    }

    private String mapBreakStatusRange(double value) {
        if(value <= 99) {
            return "0-99";
        } else if(value <= 999) {
            return "100-999";
        } else if(value <= 9999) {
            return "1000-9999";
        } else if(value <= 99999) {
            return "10000-99999";
        } else {
            return "100000+";
        }
    }

    // positive number guaranteed by caller
    private String mapTermRange(long months) {
        if(months <=1) {
            return "0m-1m";
        } else if(months <= 6) {
            return "1m-6m";
        } else if(months <= 12) {
            return "6m-1y";
        } else if(months <= 120) {
            return "1y-10y";
        } else if(months <= 360) {
            return "10y-30y";
        } else if(months <= 600){
            return "30y-50y";
        } else {
            return "50y+";
        }
    }

    private LocalDateTime convertToLocalDateTime(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
