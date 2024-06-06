package handson5_multiagent;

public class Predictions {
    double b0, b1;
    
    public Predictions(double b0, double b1) {
        this.b0 = b0;
        this.b1 = b1;
    }

    public void printRegEquation(){
        System.out.println("Regression Equation = " + b0 +" + " + b1 + "x");
    }

    public void predict(double x){
        double e = b0 + b1 * x;
        System.out.println("Predict of Y: " + e);
    }
}
