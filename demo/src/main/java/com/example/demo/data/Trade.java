package com.example.demo.data;

import lombok.Data;

import java.util.Date;

@Data

public class Trade {
    private String  inventory;
    private String  book;
    private String  system;
    private String  legalEntity;
    private long    tradeId;
    private int     version;
    private String  tradeStatus;
    private String  productType;
    private String  resettingLeg;
    private String  productSubType;
    private String  tdsProductType;
    private String  secCodeSubType;
    private String  swapType;
    private String  description;
    private Date    tradeDate;
    private Date    startDate;
    private Date    maturityDate;
}
