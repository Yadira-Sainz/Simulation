package handson3_agentbasedgradientdescent;

public class DiscreteMaths {

    public double sumX(double x[]) {
        double totalX = 0;

        for (int i = 0; i < x.length; i++) {
            totalX = totalX + x[i];
        }
        return totalX;
    }

    public double sumY(double y[]) {
        double totalY = 0;
        for (int i = 0; i < y.length; i++) {
            totalY = totalY + y[i];
        }
        return totalY;
    }

    public double XPow2(double x[]) {
        double valorX = 0;
        for (int i = 0; i < x.length; i++) {
            valorX = (double) (valorX + Math.pow(x[i], 2));
        }
        return valorX;
    }

    public double sumXPow2(double x[]) {
        return (double) Math.pow(sumX(x), 2);
    }

    public double sumXY(double x[], double y[]) {

        double totalXY = 0;
        for (int i = 0; i < x.length; i++) {
            totalXY = totalXY + (x[i] * y[i]);
        }
        return totalXY;
    }

    public double multiplySum(double x[], double y[]) {
        return sumX(x) * sumY(y);
    }

    public double sumYPow2(double y[]) {
        double valorY = 0;
        for (int i = 0; i < y.length; i++) {
            valorY = valorY + (double) Math.pow(y[i], 2);
        }
        return valorY;
    }

    public double correlationCoefficient(double x[], double y[]) {
        int n = x.length;
        double numerator = n * sumXY(x, y) - sumX(x) * sumY(y);
        double denominator = (double) Math.sqrt((n * XPow2(x) - sumXPow2(x)) * (n * sumYPow2(y) - sumXPow2(y)));
        return numerator / denominator;
    }

    public double determinationCoefficient(double x[], double y[]) {
        double r = correlationCoefficient(x, y);
        return (double) Math.pow(r, 2);
    }

    public double b1(double x[], double y[]) {
        int numeroDatos = x.length;
        double operacion1 = numeroDatos * sumXY(x, y);
        double operacion2 = multiplySum(x, y);
        double operacion3 = numeroDatos * XPow2(x);
        double operacion4 = operacion1 - operacion2;
        return operacion4 / (operacion3 - sumXPow2(x));
    }

    public double b0(double x[], double y[]) {
        double promY = sumY(y) / y.length;
        double promX = sumX(x) / x.length;
        return promY - (b1(x, y) * promX);
    }
}