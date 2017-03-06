package com.company.csvfilter.mappers;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.company.csvfilter.model.Transaction;

/**
 * Created by cristian.calugaru on 06/03/2017.
 *
 */
public class TransactionToStringMapperTest {

    TransactionToStringMapper mapper;
    DateFormat dateformat;

    @Before
    public void setup() {
        mapper = new TransactionToStringMapper();
        dateformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    }

    @Test
    public void whenTransactionThenMapToString() throws Exception {
        Transaction transaction = new Transaction.TransactionBuilder()
                        .withSource("LMN")
                        .withId("748034958")
                        .withTimestamp(dateformat.parse("2017-01-12T14:02:18").getTime())
                        .withType("TYPEA")
                        .withValue(new BigDecimal("25500000.00"))
                        .withCommission(new BigDecimal("95000.00"))
                        .createTransaction();

        Assert.assertEquals("LMN,748034958,2017-01-12T14:02:18,TYPEA,25500000.00,95000.00", mapper.apply(transaction));
    }


}
