package handson3_agentbasedgradientdescent;

public class DataSet {
    private double[] x;
    private double[] y;

    public DataSet() {
        // Datos predeterminados
        this.x = new double[] { 23, 26, 30, 34, 43, 48, 52, 57, 58 };
        this.y = new double[] { 651, 762, 856, 1063, 1190, 1298, 1421, 1440, 1518 };
    }

    public DataSet(double[] x, double[] y) {
        this.x = x;
        this.y = y;
    }

    public double[] getX() {
        return x;
    }

    public double[] getY() {
        return y;
    }

    public int getSize() {
        return Math.min(x.length, y.length);
    }
}

