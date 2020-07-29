package calculator;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


// https://github.com/ArturNikitin/hyperskill-Smart-Calculator/blob/master/Smart%20Calculator/task/src/calculator/Main.java - for example
// https://github.com/EightM/HyperskillSmartCalculator/blob/master/Main.java - for example


// https://www.youtube.com/watch?v=juGC9Iaji20 - postfix 

public class Main {
    public static String calculateNumbers(String input, List<String> vars, List<Integer> values) {
        String[] commands = input.replaceAll("\\s+", " ")
                .replaceAll("\\+{2,}", "+").split(" ");
        boolean varOrNot = false;
        if(vars.size() > 0) {
            for (String x : commands) {
                if(x.matches("[a-zA-Z]+")){
                    varOrNot = true;
                    break;
                }
            }
        }
        if(varOrNot) {
            for (int i = 0; i < commands.length; i++) {
                if(commands[i].matches("[a-zA-Z]+")) {
                    for (int j = 0; j < vars.size(); j++) {
                        if(vars.get(j).equals(commands[i])) {
                            commands[i] = String.valueOf(values.get(j));
                            break;
                        }
                    }
                }
            }
        }
        if(vars.size() == 1 && input.toLowerCase().equals(vars.get(0))) {
            return "Unknown variable";
        }
        for (int i = 0; i < commands.length; i++) {
            if(commands[i].matches("[a-zA-z]+") ||
            commands[i].matches("\\d+\\+") ||
            i % 2 != 0 && commands[i].matches("\\d+") ||
                    commands[i].matches("\\d+-")) {
                return "Invalid expression";
            }
        }
        int result = Integer.parseInt(commands[0]);
        if(input.length() == 1 && varOrNot) {
            System.out.println(values.get(vars.indexOf(input)));
        } else if(input.length() == 1) {
            System.out.println(input);
        } else  {
            for (int i = 0; i < commands.length; i++) {
                if(commands[i].matches("-{2,}")) {
                    if(commands[i].length() % 2 == 0) {
                        commands[i] = "+";
                    } else {
                        commands[i] = "-";
                    }
                }
                if(commands[i].matches("\\+\\d+")) {
                    commands[i] = commands[i].substring(1);
                }
            }
            for (int i = 0; i < commands.length-2; i+=2) {
                if(commands[i+1].equals("+")) {
                    result += Integer.parseInt(commands[i+2]);
                } else {
                    result -= Integer.parseInt(commands[i+2]);
                }
            }
            return String.valueOf(result);
        }
        return "";
    }
    public static void addVariables(String input, List<String> vars, List<Integer> values) {
        String[] variables = input.replaceAll("\\s+", "").split("=");
        for (int i = 0; i < variables.length - 1; i+=2) {
            if(!(variables[i].matches("[a-zA-Z]+"))) {
                System.out.println("Invalid identifier");
                break;
            } else if(variables.length > 2 ||
                    variables[i+1].matches("[a-zA-Z]+\\d+[a-zA-Z]+") ||
                    variables[i+1].matches("\\d+[a-zA-Z]+")) {
                System.out.println("Invalid assignment");
                break;
            } else {
                if(variables[i+1].matches("[a-zA-Z]+")) {
                    if(!(vars.contains(variables[i+1]))) {
                        System.out.println("Unknown variable");
                        break;
                    } else {
                        if(vars.contains(variables[i])) {
                            values.set(vars.indexOf(variables[i]), values.get(vars.indexOf(variables[i+1])));
                        } else {
                            vars.add(variables[i]);
                            values.add(values.get(vars.indexOf(variables[i+1])));
                        }
                    }
                } else {
                    if(vars.contains(variables[i])) {
                        values.set(vars.indexOf(variables[i]), Integer.valueOf(variables[i+1]));
                    } else {
                        vars.add(variables[i]);
                        values.add(Integer.valueOf(variables[i+1]));
                    }
                }
            }
        }
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> vars = new ArrayList<>();
        List<Integer> values = new ArrayList<>();
        while (true){
            String input = scanner.nextLine();
            if(input.isEmpty()) continue;
            if(input.equals("/exit")) {
                System.out.println("Bye!");
                break;
            } else if (input.equals("/help")) {
                System.out.println("The program calculates the addition/subtraction of numbers");
            } else if (input.matches("/.*")){
                System.out.println("Unknown command");
            } else {
                if(input.replaceAll("\\s+", "").contains("=")) {
                    addVariables(input, vars, values);
                } else {
                    System.out.println(calculateNumbers(input, vars, values));
                }
            }
        }
    }
}
