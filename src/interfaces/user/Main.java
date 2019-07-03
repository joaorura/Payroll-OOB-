package interfaces.user;

import interfaces.system.Payroll;

import java.lang.reflect.InvocationTargetException;

import static interfaces.user.UtilsMain.readEntries;

public class Main{
    private static boolean processEntries(int input) {
        System.out.println("\nStarting the operation ...\n");
        Payroll pay = Payroll.getDefault();
        int type_id = -1;
        int id = -1;
        String name = null;

        if(input != 9) {
            if(input!= 5)
                pay.backup(true);

            if(input != 0 && input != 1 && input != 7) {
                UtilsMain.printIdentification();
                type_id = readEntries(0,1);
                if(type_id == 0) {
                    System.out.print("Id of employee: ");
                    id = readEntries(0, Integer.MAX_VALUE);
                }
                else {
                    System.out.print("Name of employee: ");
                    name = UtilsMain.takeString();
                }

                if((type_id == 0 && Payroll.getDefault().searchEmployee(id) == null) ||
                        (type_id == 1 && Payroll.getDefault().searchEmployee(Payroll.getDefault().searchEmployee(name)) == null)) {
                    System.out.println("Employee not found, please try again\n");
                    return false;
                }
            }
        }



        FuncionabilitiesInter.att(type_id, id, name);
        Object ret = null;
        try {
            ret = FuncionabilitiesInter.funcionabilities.get(input).invoke(null);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        if(ret != null) {
            if(ret instanceof Boolean) return (boolean) ret;
            else return true;
        }
        else return true;

    }

    public static void main (String[] args) {
        System.out.println("Inicializando o sistema\n" +
                "\tInicializando configurações:\n");
        Payroll.getDefault().configurations(UtilsMain.getDate());

        int input;
        while (true) {
            UtilsMain.printIntro();
            input = readEntries(0,10);
                if(input == 10) return;
                else if (!processEntries(input)) {
                    System.out.println("A sua operação falhou, o sistema não conlcluiu a sua ação. Por favor " +
                            "realize-a novamente!");
                }
        }
    }
}
