package interfaces.user;

import funcionabilities.Employee;
import funcionabilities.functional_aids.payments.ITypePayments;
import interfaces.SystemSettings;
import interfaces.system.Payroll;
import interfaces.system.UtilsPayroll;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static interfaces.user.UtilsMain.readEntries;

class FuncionabilitiesInter {
    public static final Map<Integer, Method> funcionabilities = new HashMap<>();
    static {
        Method[] methods = FuncionabilitiesInter.class.getDeclaredMethods();
        Arrays.sort(methods, new Comparator<Method>() {
            @Override
            public int compare(Method o1, Method o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        int j = 0;
        for(int i = 0; i < methods.length; i++) {
            if(methods[i].getName().equals("att")) continue;
            funcionabilities.put(j, methods[i]);
            j++;
        }
    }

    private static Payroll pay = null;
    private static int type_id = -1;
    private static int id = -1;
    private static String name = null;

    static void att(int tp_id, int identifi, String nam) {
        pay = Payroll.getDefault();
        type_id = tp_id;
        id = identifi;
        name = nam;
    }

    static Employee addEmployee() {
        System.out.println("Add employee!\n");
        ArrayList<ArrayList<Object>> param = new ArrayList<>();

        for(int i = 0; i < 5; i ++) {param.add(new ArrayList<>());}

        CreateElements.identificatonProcess(id, param.get(0));
        CreateElements.syndicateProcess(param.get(1));
        CreateElements.methodProcess((String) param.get(0).get(1), (String) param.get(0).get(2), param.get(2));
        CreateElements.typeProcess(param.get(3));
        CreateElements.pointsProcess(param.get(4));

        return pay.addEmployee(param);
    }

    static Employee removeEmployee() {
        System.out.println("\nRemove Employee!\n");
        Employee emp_aux;
        if(type_id == 0) emp_aux = pay.removeEmployee(id);
        else emp_aux = pay.removeEmployee(name);

        if(emp_aux == null) return null;
        else {
            System.out.println("Removed: \n\t" + emp_aux.toString());
            return emp_aux;
        }

    }

    static void processPointCard() {
        if(pay.searchEmployee(id) == null) {
            System.out.println("Employee not found, try again\n");
        }
        System.out.println("\nProcess Point Card!\n");

        System.out.print("Start of turn: ");
        GregorianCalendar start = UtilsMain.getDate();

        System.out.println("End of turn: ");
        GregorianCalendar end = UtilsMain.getDate();
        if(type_id == 0)  pay.processPointCard(id, start, start);
        else  pay.processPointCard(name, start, start);
    }

    static void processSale() {
        System.out.println("\nProcess Sale Result\n");
        System.out.println("\n\tName of product: ");
        String name_product = UtilsMain.takeString();

        Double value_product;
        System.out.println("\n\tValue of product: ");
        do value_product = (Double) UtilsMain.readEntries(Double.class); while (value_product == null);

        if(type_id == 0) {
            try {
                pay.processSaleResult(id, name_product, value_product);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                pay.processSaleResult(name, name_product, value_product);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static void processServiceChange() {
        System.out.println("You desire retire or add services: \n" +
                "\t0: Add\n" +
                "\t1: Remove");

        int aux = UtilsMain.readEntries(0,1);
        boolean type = (aux == 0);

        String name_product = UtilsMain.takeString();
        Double value_product = (Double) UtilsMain.readEntries(Double.class);
        assert(value_product != null);
        if(type_id == 0) pay.processServiceChange(type, id, name_product, value_product);
        else pay.processServiceChange(type, name, name_product, value_product);
    }

    static void processEmployeeDetail(){
        System.out.println("Changes of employee: ");
        Employee emp_aux = addEmployee();
        if(type_id == 0) pay.changeEmployee(id, emp_aux);
        else pay.changeEmployee(name, emp_aux);

        att(0,Payroll.getDefault().nextId() - 1, name);
        removeEmployee();
    }

    static boolean undoRedo() {
        System.out.println("Você deseja:");
        System.out.println("\t0: Desfazer");
        System.out.println("\t1: Refazer");
        if (readEntries(0, 1) == 0) return pay.undo();
        else return pay.redo();
    }

    static void setPersonalPayment() {
        System.out.println("Create Employee Payment Schedule / Set Employee Payment Schedule\n" +
                "\t0: Create\n" +
                "\t1: Set\n");

        int aux = UtilsMain.readEntries(0,1);

        if(aux == 0) {
            ArrayList<Object> param = new ArrayList<>();
            CreateElements.typeProcess(param);
            UtilsPayroll.createPaymentSchedule(param);
        }
        else {
            aux = CreateElements.showTypeElements();

            if(type_id == 0) UtilsPayroll.setPaymentSchedule(id, aux);
            else UtilsPayroll.setPaymentSchedule(name, aux);
        }
    }

    static void printState() {
        System.out.println("State: \n\t" + pay.toString());
    }

    static void runPayroll() {
        System.out.println("Number of days that will be passed: ");
        int aux = readEntries(0, Integer.MAX_VALUE);
        for(int i = 0; i < aux; i++) {
            pay.runPayrollToday();
        }
    }
}
