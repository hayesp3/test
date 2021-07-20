package com.example.demo.web;

import com.example.demo.data.Report;
import com.example.demo.datasource.CSVDataRetriever;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ReportConstructionTests {

    @SneakyThrows
    @Test
    public void applicationReturnsListOfResults() {
        CSVDataRetriever dataRetriever = new CSVDataRetriever();
        ReportsController reportsController = new ReportsController(dataRetriever);
        List<Report> reports =  reportsController.constructReport();

    }
}
