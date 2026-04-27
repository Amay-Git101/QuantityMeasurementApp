public class QuantityMeasurementApp {

    enum LengthUnit {
        FEET(1.0),
        INCHES(1.0 / 12.0),
        YARDS(3.0),
        CENTIMETERS(0.0328084);

        private final double factor;

        LengthUnit(double factor) {
            this.factor = factor;
        }

        public double toFeet(double value) {
            return value * factor;
        }

        public double fromFeet(double value) {
            return value / factor;
        }
    }

    static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            if (!Double.isFinite(value) || unit == null)
                throw new IllegalArgumentException();
            this.value = value;
            this.unit = unit;
        }

        public double toFeet() {
            return unit.toFeet(value);
        }

        public static double convert(double value, LengthUnit from, LengthUnit to) {
            double feet = from.toFeet(value);
            return to.fromFeet(feet);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof QuantityLength)) return false;
            QuantityLength q = (QuantityLength) obj;
            return Double.compare(this.toFeet(), q.toFeet()) == 0;
        }
    }

    public static void main(String[] args) {
        System.out.println(QuantityLength.convert(1.0, LengthUnit.FEET, LengthUnit.INCHES));

        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCHES);

        System.out.println(q1.equals(q2));
    }
}