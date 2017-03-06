package com.company.csvfilter.mappers;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.company.csvfilter.model.Transaction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by cristian.calugaru on 06/03/2017.
 *
 */
public class StringToTransactionMapperTest {


    StringToTransactionMapper stringToTransactionMapper;
    DateFormat dateFormat;

    @Before
    public void setup() {
        stringToTransactionMapper = new StringToTransactionMapper();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    }

    @Test
    public void whenCsvLineThenMapToTransaction() throws Exception {
        final Optional<Transaction> transactionOptional = stringToTransactionMapper.apply("LMN,748034958,2017-01-12T14:02:18,TYPEA,25500000.00,95000.00");
        assertTrue(transactionOptional.isPresent());

        final Transaction transaction = transactionOptional.get();
        assertEquals(transaction.getSource(), "LMN");
        assertEquals(transaction.getId(), "748034958");
        assertEquals(transaction.getTimestamp(), dateFormat.parse("2017-01-12T14:02:18").getTime());
        assertEquals(transaction.getType(), "TYPEA");
        assertEquals(transaction.getValue(), new BigDecimal("25500000.00"));
        assertEquals(transaction.getCommission(), new BigDecimal("95000.00"));
    }

    @Test
    public void whenCsvLineHasInvalidNumberOfFieldsReturnEmptyOptional() {
        final Optional<Transaction> transactionOptional = stringToTransactionMapper.apply("a, b, c");
        assertFalse(transactionOptional.isPresent());
    }

    @Test(expected = RuntimeException.class)
    public void whenCsvLineContainsInvalidDateThenThrowException() {
        stringToTransactionMapper.apply("LMN,748034958,123456,TYPEA,25500000.00,95000.00");
    }

    @Test(expected = RuntimeException.class)
    public void whenCsvLineContainsInvalidDecimalNumberThenThrowException() {
        stringToTransactionMapper.apply("LMN,748034958,123456,TYPEA,ab,95000.00");
    }
}
