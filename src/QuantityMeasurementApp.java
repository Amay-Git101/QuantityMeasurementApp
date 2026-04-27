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

        public QuantityLength add(QuantityLength other) {
            if (other == null) throw new IllegalArgumentException();
            double sumFeet = this.toFeet() + other.toFeet();
            double result = this.unit.fromFeet(sumFeet);
            return new QuantityLength(result, this.unit);
        }

        public static QuantityLength add(QuantityLength q1, QuantityLength q2, LengthUnit target) {
            if (q1 == null || q2 == null || target == null)
                throw new IllegalArgumentException();
            double sumFeet = q1.toFeet() + q2.toFeet();
            double result = target.fromFeet(sumFeet);
            return new QuantityLength(result, target);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof QuantityLength)) return false;
            QuantityLength q = (QuantityLength) obj;
            return Double.compare(this.toFeet(), q.toFeet()) == 0;
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    public static void main(String[] args) {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCHES);

        System.out.println(q1.add(q2));
        System.out.println(QuantityLength.add(q1, q2, LengthUnit.INCHES));
    }
}