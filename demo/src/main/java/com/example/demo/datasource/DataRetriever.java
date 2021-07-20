package com.example.demo.datasource;

import com.example.demo.data.RefData;
import com.example.demo.data.Trade;
import com.example.demo.data.Valuation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/*
    generic interface for retrieving trade data
 */
public interface DataRetriever {

    List<Trade> getTradeData() throws IOException, ParseException;

    List<Valuation> getValuationData() throws IOException;

    List<RefData> getRefData() throws IOException;

}
