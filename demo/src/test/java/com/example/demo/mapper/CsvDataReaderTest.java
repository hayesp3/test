package com.example.demo.mapper;

import com.example.demo.data.RefData;
import com.example.demo.data.Trade;
import com.example.demo.data.Valuation;
import com.example.demo.datasource.CSVDataRetriever;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CsvDataReaderTest {

    @Test
    public void testTradeRead() throws IOException, ParseException {
        CSVDataRetriever retriever = new CSVDataRetriever();
        List<Trade> trades = retriever.getTradeData();
        assertEquals(16, trades.size());
    }

    @Test
    public void testValuationRead() throws IOException {
        CSVDataRetriever retriever = new CSVDataRetriever();
        List<Valuation> valuations = retriever.getValuationData();
        assertEquals(18, valuations.size());
    }

    @Test
    public void testRefDataRead() throws IOException {
        CSVDataRetriever retriever = new CSVDataRetriever();
        List<RefData> refDataList = retriever.getRefData();
        assertEquals(23, refDataList.size());
    }
}
