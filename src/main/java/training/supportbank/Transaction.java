package training.supportbank;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Transaction {
    private LocalDate date;
    private String fromAccount;
    private String toAccount;
    private String narrative;
    private BigDecimal amount;

    public Transaction(LocalDate date, String from, String to, String narrative, BigDecimal amount){
        this.date = date;
        this.fromAccount = from;
        this.toAccount = to;
        this.narrative = narrative;
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getFrom() {
        return fromAccount;
    }
    public void setFrom(String from) {
        this.fromAccount = from;
    }

    public String getTo() {
        return toAccount;
    }

    public void setTo(String to) {
        this.toAccount = to;
    }

    public String getNarrative() {
        return narrative;
    }

    public void setNarrative(String narrative)
    {
        this.narrative = narrative;
    }

    public BigDecimal getAmount()
    {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
