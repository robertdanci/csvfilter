package com.company.csvfilter.model;

import java.math.BigDecimal;
import java.util.Objects;

public final class Transaction implements Comparable<Transaction> {

    private final String source;
    private final String id;
    private final long timestamp;
    private final String type;
    private final BigDecimal value;
    private final BigDecimal commission;

    private Transaction(String source, String id, long timestamp, String type, BigDecimal value, BigDecimal commission) {
        this.source = source;
        this.id = id;
        this.timestamp = timestamp;
        this.type = type;
        this.value = value;
        this.commission = commission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id) &&
               Objects.equals(source, that.source) &&
               Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, id, type);
    }

    @Override
    public int compareTo(Transaction that) {
        return Long.compare(this.timestamp, that.getTimestamp());
    }

    public String getSource() {
        return source;
    }

    public String getId() {
        return id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getValue() {
        return value;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public static class TransactionBuilder {
        private String source;
        private String id;
        private long timestamp;
        private String type;
        private BigDecimal value;
        private BigDecimal commission;

        public TransactionBuilder withSource(String source) {
            this.source = source;
            return this;
        }

        public TransactionBuilder withId(String id) {
            this.id = id;
            return this;
        }

        public TransactionBuilder withTimestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public TransactionBuilder withType(String type) {
            this.type = type;
            return this;
        }

        public TransactionBuilder withValue(BigDecimal value) {
            this.value = value;
            return this;
        }

        public TransactionBuilder withCommission(BigDecimal commission) {
            this.commission = commission;
            return this;
        }

        public Transaction createTransaction() {
            return new Transaction(source, id, timestamp, type, value, commission);
        }
    }
}
