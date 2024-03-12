package challenge1;

public class SLR {
    private  DataSet dataSet;
    private  DiscreteMaths discreteMaths;

    public SLR(DataSet dataSet, DiscreteMaths discreteMaths) {
        this.dataSet = dataSet;
        this.discreteMaths = discreteMaths;
    }

    public void calculateIntersection() {
        System.out.println("x: " + discreteMaths.sumX(dataSet.getX()));
        System.out.println("y: " + discreteMaths.sumY(dataSet.getY()));
    } //beta0 23

    public void printRegEquation(){
        System.out.println("Regression Equation= " + discreteMaths.b0(dataSet.getX(), dataSet.getY())+" + "
                + discreteMaths.b1(dataSet.getX(), dataSet.getY())+ "x");
    }

    public void predict(float x){
        float e = discreteMaths.b0(dataSet.getX(), dataSet.getY()) + (discreteMaths.b1(dataSet.getX(), dataSet.getY()) * x);
        System.out.println("Predict of Y: " + e);
    }

}
