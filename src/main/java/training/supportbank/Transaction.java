package training.supportbank;

import java.time.LocalDate;

public class Transaction {
    private LocalDate date;
    private String from;
    private String to;
    private String narrative;
    private float amount;

    public Transaction(LocalDate date, String from, String to, String narrative, float amount){
        this.date = date;
        this.from = from;
        this.to = to;
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
        return from;
    }
    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getNarrative() {
        return narrative;
    }

    public void setNarrative(String narrative)
    {
        this.narrative = narrative;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
