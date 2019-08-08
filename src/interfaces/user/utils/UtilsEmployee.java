package interfaces.user.utils;

import funcionabilities.Employee;
import funcionabilities.auxiliary_entities.Syndicate;
import funcionabilities.functional_aids.calendar.PointCalendar;
import interfaces.system.controlers.EmployeeController;
import interfaces.user.problematics.UtilsProblematicCreate;

import javax.naming.directory.InvalidAttributesException;
import java.util.ArrayList;

import static interfaces.user.utils.UtilsSystem.readEntries;

public class UtilsEmployee {
    public static int identifier(EmployeeController payroll) {
        UtilsSystem.printIdentification();

        int type_id = readEntries(0, 1);
        int id;

        if (type_id == 0) {
            System.out.print("Identifier: ");
            id = readEntries(0, Integer.MAX_VALUE);
        } else {
            System.out.print("Name: ");
            id = payroll.searchEmployee(UtilsSystem.takeString());
        }

        if (payroll.searchEmployee(id) == null) {
            System.out.println("Employee not founded");
            return -1;
        }
        else {
            return id;
        }
    }

    public static ArrayList<ArrayList<Object>> getDatas(EmployeeController empControll, Employee emp)
            throws InvalidAttributesException {
        ArrayList<ArrayList<Object>> param = new ArrayList<>();
        ArrayList<Object> auxOb;

        for (int i = 0; i < 5; i++) {
            param.add(new ArrayList<>());
        }

        boolean check = emp != null;
        UtilsCreate.identifierProcess(empControll, emp, param.get(0));

        if (UtilsProblematicCreate.canChange(check, "Syndicate")) {
            UtilsProblematicCreate.syndicateProcess(param.get(1));
        } else {
            Syndicate synd = emp.getSyndicate();
            auxOb = param.get(1);

            if (synd == null) {
                auxOb.add(Class.class);
            } else {
                auxOb.add(synd.getIndetification());
                auxOb.add(synd.getMonthlyFee());
            }
        }

        UtilsProblematicCreate.methodProcess(empControll, (String) param.get(0).get(1), (String) param.get(0).get(2), param.get(2));

        UtilsProblematicCreate.typeProcess(empControll.getPayroll(), false, param.get(3));

        param.get(4).add(PointCalendar.class);

        return param;
    }
}
