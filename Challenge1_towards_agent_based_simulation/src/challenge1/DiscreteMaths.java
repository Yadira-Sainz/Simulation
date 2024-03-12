package challenge1;

public class DiscreteMaths {

    public float sumX(float x[]) {
        float totalX = 0;

        for (int i = 0; i < x.length; i++)
            totalX = totalX + x[i];
        return totalX;
    }

    public float sumY(float y[]) {
        float totalY = 0;
        for (int i = 0; i < y.length; i++)
            totalY = totalY + y[i];
        return totalY;
    }

    public float XPow2(float x[]){
        float valorX = 0;
        for(int i = 0; i < x.length; i++)
            valorX = (float) (valorX + Math.pow(x[i], 2));
        return valorX;
    }

    public float sumXPow2(float x[]){
        return (float) Math.pow(sumX(x), 2);
    }

    public float sumXY(float x[], float y[]){

        float totalXY = 0;
        for (int i = 0; i < x.length; i++)
            totalXY = totalXY + (x[i] * y[i]);
        return totalXY;
    }

    public float multiplySum(float x[], float y[]){
        return sumX(x) * sumY(y);
    }

    public float b1(float x[], float y[]){
        int numeroDatos = x.length;
        float operacion1 = numeroDatos * sumXY(x, y);
        float operacion2 = multiplySum(x,y);
        float operacion3 = numeroDatos * XPow2(x);
        float operacion4 = operacion1 - operacion2;
        return operacion4 / (operacion3 - sumXPow2(x));
    }

    public float b0(float x[], float y[]){
        float promY = sumY(y) / y.length;
        float promX = sumX(x) / x.length;
        return promY - (b1(x, y) * promX);
    }
}


