public class QuantityMeasurementApp {

    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0),
        YARD(3.0),
        CM(0.393701 / 12.0);

        private final double toFeet;

        LengthUnit(double toFeet) {
            this.toFeet = toFeet;
        }

        public double toFeet(double value) {
            return value * toFeet;
        }
    }

    static class Quantity {
        private final double value;
        private final LengthUnit unit;

        Quantity(double value, LengthUnit unit) {
            if (unit == null) throw new IllegalArgumentException("Unit cannot be null");
            this.value = value;
            this.unit = unit;
        }

        private double toBase() {
            return unit.toFeet(value);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Quantity other = (Quantity) obj;
            return Double.compare(this.toBase(), other.toBase()) == 0;
        }
    }

    public static void main(String[] args) {
        System.out.println(new Quantity(1.0, LengthUnit.YARD).equals(new Quantity(3.0, LengthUnit.FEET)));
        System.out.println(new Quantity(1.0, LengthUnit.YARD).equals(new Quantity(36.0, LengthUnit.INCH)));
        System.out.println(new Quantity(2.0, LengthUnit.CM).equals(new Quantity(2.0, LengthUnit.CM)));
        System.out.println(new Quantity(1.0, LengthUnit.CM).equals(new Quantity(0.393701, LengthUnit.INCH)));
    }
}