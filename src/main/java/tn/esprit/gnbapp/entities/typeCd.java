package tn.esprit.gnbapp.entities;

public enum typeCd {

    CAR(0.05),
    HOME(0.06),
    PERSONAL(0.08);

    private final double interestRate;

    typeCd(double interestRate) {
        this.interestRate = interestRate;
    }

    public double getInterestRate() {
        return interestRate;
    }}
