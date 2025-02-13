import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Expressoes {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);   
        System.out.println("Digite uma expresao matematica (ou 'sair' para encerrar):");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().replaceAll("\s", ""); 

            if (input.equalsIgnoreCase("sair")) {
                break;
            }

            try {
                solveExpression(input);
            } catch (Exception e) {
                System.out.println("Erro ao processar a expressao: " + e.getMessage());
            }
        }

        scanner.close();

    }

    public static void solveExpression(String expr) {
        while (!expr.equals(evaluateStep(expr))) {
            System.out.println(expr);
            expr = evaluateStep(expr);
        }
        System.out.println(expr); 
    }

    public static String evaluateStep(String expr) {
        if (expr.contains("(")) {
            int lastOpen = expr.lastIndexOf("(");
            int firstClose = expr.indexOf(")", lastOpen);
            String subExpr = expr.substring(lastOpen + 1, firstClose);
            String result = evaluateStep(subExpr);
            return expr.substring(0, lastOpen) + result + expr.substring(firstClose + 1);
        }

        if (expr.contains("^")) {
            return evaluateBinaryOperation(expr, "\\d+\\^\\d+", "^", (a, b) -> (int) Math.pow(a, b));
        }

        if (expr.contains("*") || expr.contains("/")) {
            return evaluateBinaryOperation(expr, "\\d+[*/]\\d+", "*", (a, b) -> a * b);
        } 

        if (expr.contains("+") || expr.contains("-")) {
            return evaluateBinaryOperation(expr, "\\d+[+-]\\d+", "+-", (a, b) -> a + b);
        }

        return expr;
    }

    private static String evaluateBinaryOperation(String expr, String pattern, String operators, Operation operation) {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(expr);
            if (m.find()) {
                String match = m.group();
                char operator = match.replaceAll("\\d", "").charAt(0);
                String[] parts = match.split("\\" + operator);
                int left = Integer.parseInt(parts[0]);
                int rigth = Integer.parseInt(parts[1]);
                int result = operation.apply(left, rigth);
                return expr.replaceFirst(Pattern.quote(match), String.valueOf(result));
            }
        return expr;
    }

    interface Operation{
        int apply(int a, int b);
    }
}