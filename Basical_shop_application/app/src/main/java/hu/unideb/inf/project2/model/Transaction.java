package hu.unideb.inf.project2.model;

public class Transaction {

    private int id;
    private String name;
    private int sum;

    public Transaction(int id, String name, int sum) {
        this.id = id;
        this.name = name;
        this.sum = sum;
    }

    public Transaction(String name, int sum) {
        this.name = name;
        this.sum = sum;
    }

    public Transaction() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    @Override
    public String toString() {
        return id + " " + name + " " + sum;
    }
}
