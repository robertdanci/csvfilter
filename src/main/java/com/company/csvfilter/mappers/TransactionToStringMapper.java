package com.company.csvfilter.mappers;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.function.Function;

import com.company.csvfilter.model.Transaction;

/**
 * Created by cristian.calugaru on 06/03/2017.
 * Function to map a transaction to an output String, used to write to a file
 */
public class TransactionToStringMapper implements Function<Transaction, String> {

    private final String separator;
    private final DateFormat dateFormatter;
    private final DecimalFormat decimalFormatter;

    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    private static final String DEFAULT_DECIMAL_FORMAT = "#.00";


    public TransactionToStringMapper(String separator) {
        this(separator, new SimpleDateFormat(), new DecimalFormat());
    }

    public TransactionToStringMapper() {
        this(",", new SimpleDateFormat(DEFAULT_DATE_FORMAT), new DecimalFormat(DEFAULT_DECIMAL_FORMAT));
    }

    public TransactionToStringMapper(String separator, DateFormat dateFormatter, DecimalFormat decimalFormatter) {
        this.separator = separator;
        this.dateFormatter = dateFormatter;
        this.decimalFormatter = decimalFormatter;
    }


    @Override
    public String apply(Transaction transaction) {
        StringBuilder result = new StringBuilder();
        result.append(transaction.getSource()).append(separator);
        result.append(transaction.getId()).append(separator);

        result.append(dateFormatter.format(transaction.getTimestamp())).append(separator);
        result.append(transaction.getType()).append(separator);
        result.append(decimalFormatter.format(transaction.getValue())).append(separator);
        result.append(decimalFormatter.format(transaction.getCommission())).append("\n");
        return result.toString();
    }
}
