package com.company.csvfilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.company.csvfilter.mappers.StringToTransactionMapper;
import com.company.csvfilter.mappers.TransactionToStringMapper;
import com.company.csvfilter.model.Transaction;

import static java.nio.charset.StandardCharsets.UTF_8;

public class CsvFilter {

    private final StringToTransactionMapper stringToTransactionMapper;
    private final TransactionToStringMapper transactionToStringMapper;

    public CsvFilter() {
        stringToTransactionMapper = new StringToTransactionMapper();
        transactionToStringMapper = new TransactionToStringMapper();
    }

    public void filter(URI inputFile1, URI inputFile2, URI outputFile) {

        try (Stream<String> firstFileStream = Files.lines(Paths.get(inputFile1), UTF_8);
             Stream<String> secondFileStream = Files.lines(Paths.get(inputFile2), UTF_8)) {

            List<Transaction> firstTransactions = parseTransactions(firstFileStream);
            List<Transaction> secondTransactions = parseTransactions(secondFileStream);

            List<Transaction> distinctSortedTransactions = mergeTransactions(firstTransactions.stream(), secondTransactions.stream());
            writeTransactionsToFile(distinctSortedTransactions, outputFile);
            
        } catch (IOException ex) {
            throw new RuntimeException("Error occurred while reading the input file", ex);
        }

    }

    public List<Transaction> parseTransactions(Stream<String> transactionsStream) {
        return transactionsStream
                        //skip headers
                        .skip(1)
                        .map(stringToTransactionMapper)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .distinct()
                        .sorted()
                        .collect(Collectors.toList());
    }

    public List<Transaction> mergeTransactions(Stream<Transaction> firstFileStream, Stream<Transaction> secondFileStream) {
        return Stream.concat(firstFileStream, secondFileStream)
                        .distinct()
                        .sorted()
                        .collect(Collectors.toList());
    }

    private void writeTransactionsToFile(List<Transaction> transactions, URI outputFile) {
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(Paths.get(outputFile)))) {
            writer.write(getHeaderContent());
            transactions.stream().map(transactionToStringMapper).forEach(writer::write);
        } catch (IOException ex) {
            throw new RuntimeException("Error occurred while reading the input file", ex);
        }
    }

    private String getHeaderContent() {
        return "SOURCE,ID,TIMESTAMP,TYPE,VALUE,COMMISSION\n";
    }
}
