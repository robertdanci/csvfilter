package com.company.csvfilter.mappers;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.function.Function;

import com.company.csvfilter.model.Transaction;

/**
 * Created by cristian.calugaru on 06/03/2017.
 * Function to map an input csv line to a transaction domain object
 */
public class StringToTransactionMapper implements Function<String, Optional<Transaction>> {

    private final String separator;
    private final DateFormat dateFormatter;

    private static final int NR_OF_FIELDS = 6;

    public StringToTransactionMapper() {
        this(",", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));
    }

    public StringToTransactionMapper(String separator, DateFormat dateFormatter) {
        this.separator = separator;
        this.dateFormatter = dateFormatter;
    }

    @Override
    public Optional<Transaction> apply(String input) {
        String[] parts = input.split(separator);
        if (parts.length != NR_OF_FIELDS) {
            return Optional.empty();
        }
        try {
            return Optional.of(new Transaction.TransactionBuilder()
                            .withSource(parts[0])
                            .withId(parts[1])
                            .withTimestamp(dateFormatter.parse(parts[2]).getTime())
                            .withType(parts[3])
                            .withValue(new BigDecimal(parts[4]))
                            .withCommission(new BigDecimal(parts[5]))
                            .createTransaction());
        } catch (ParseException e) {
            throw new RuntimeException("Error parsing date, ", e);
        }
    }
}
