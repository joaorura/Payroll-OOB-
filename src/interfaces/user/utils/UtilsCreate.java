package interfaces.user.utils;

import model.Commissioned;
import model.Employee;
import model.Hourly;
import interfaces.system.controlers.EmployeeController;
import interfaces.user.utils.create.CommissionedCreate;
import interfaces.user.utils.create.ExecuteCreate;
import interfaces.user.utils.create.HourlyCreate;
import interfaces.user.utils.create.SalariedCreate;

import java.util.ArrayList;

import static interfaces.user.problematics.UtilsProblematicCreate.canChange;

class UtilsCreate {

    private static int getDafaultEntries(boolean check, EmployeeController pay, Employee emp, ArrayList<Object> param) {
        param.add(null);

        if (canChange(check, "name")) {
            System.out.print("\tName: ");
            param.add(UtilsSystem.takeString());
        } else {
            param.add(emp.getName());
        }

        if (canChange(check, "address")) {
            System.out.print("\tAddress:");
            String str = UtilsSystem.takeString();

            while (str.equals("\n")) {
                System.out.print("Enter again: ");
                str = UtilsSystem.takeString();
            }

            param.add(str);
        }

        int aux;
        if (!check) {
            param.add(pay.nextId());

            System.out.println("\nType:\n" +
                    "\t0: Hourly\n" +
                    "\t1: Salaried\n" +
                    "\t2 : Commissioned\n");

            aux = UtilsSystem.readEntries(0, 2);
        } else {
            param.add(emp.getId());

            if (emp instanceof Hourly) aux = 0;
            else if (emp instanceof Commissioned) aux = 2;
            else aux = 1;
        }

        return aux;
    }

    private static void getMutableEntries(int aux, boolean check, Employee emp, ArrayList<Object> param) {
        ExecuteCreate exec = null;
        switch (aux) {
            case 0:
                exec = new HourlyCreate();
                break;

            case 1:
                exec = new SalariedCreate();
                break;

            case 2:
                exec = new CommissionedCreate();
                break;
        }

        if(exec == null) throw new RuntimeException("The System founded a critical error please contact the developers.");
        exec.execute(check, emp, param);
    }

    static void identifierProcess(EmployeeController pay, Employee emp, ArrayList<Object> param) {
        boolean check = emp != null;
        int aux = getDafaultEntries(check, pay, emp, param);
        try {
            getMutableEntries(aux, check, emp, param);
        }
        catch(RuntimeException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
