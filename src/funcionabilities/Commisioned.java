package funcionabilities;

import funcionabilities.auxiliary_entities.Syndicate;
import funcionabilities.functional_aids.PaymentBills;
import funcionabilities.functional_aids.SaleList;
import funcionabilities.functional_aids.calendar.PointCalendar;
import funcionabilities.functional_aids.transactions.IMethodsPayments;

public class Commisioned extends Salaried {
    private final SaleList sales;
    private final double ratioSales;
    private String identifier;

    public Commisioned(String adress, String name, int personal_id, Syndicate personalSyndicate,
                       IMethodsPayments typePayment, PaymentBills personalIPayment, PointCalendar worker,
                       Double salary, Double ratioSales) {
        super(adress, name, personal_id, personalSyndicate, typePayment, personalIPayment, worker, salary);
        this.sales = new SaleList();
        this.ratioSales = ratioSales;
        this.identifier = super.toString() + "Ratio Sales: " + ratioSales + "\nValues of Sales: " + sales.getAllValues() + "\n";
    }

    public SaleList getSales() {
        return sales;
    }

    public double getRatioSales() {
        return ratioSales;
    }

    @Override
    public double attMoney() {
        double tempSalary = super.attMoney();
        tempSalary += sales.getAllValues();
        sales.clearList();
        return tempSalary;
    }

    @Override
    public String toString() {
        return identifier;
    }

    @Override
    public Commisioned clone() throws CloneNotSupportedException {
        return (Commisioned) super.clone();
    }
}
