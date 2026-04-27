enum LengthUnit {
    FEET(1.0),
    INCHES(1.0 / 12.0),
    YARDS(3.0),
    CENTIMETERS(1.0 / 30.48);

    private final double factor;

    LengthUnit(double factor) {
        this.factor = factor;
    }

    double toBase(double value) {
        return value * factor;
    }

    double fromBase(double baseValue) {
        return baseValue / factor;
    }
}

class QuantityLength {
    private final double value;
    private final LengthUnit unit;

    QuantityLength(double value, LengthUnit unit) {
        if (unit == null || !Double.isFinite(value)) throw new IllegalArgumentException();
        this.value = value;
        this.unit = unit;
    }

    QuantityLength convertTo(LengthUnit target) {
        if (target == null) throw new IllegalArgumentException();
        double base = unit.toBase(value);
        double converted = target.fromBase(base);
        return new QuantityLength(converted, target);
    }

    QuantityLength add(QuantityLength other, LengthUnit target) {
        if (other == null || target == null) throw new IllegalArgumentException();
        double baseSum = unit.toBase(value) + other.unit.toBase(other.value);
        double result = target.fromBase(baseSum);
        return new QuantityLength(result, target);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        QuantityLength other = (QuantityLength) obj;
        double a = unit.toBase(value);
        double b = other.unit.toBase(other.value);
        return Double.compare(a, b) == 0;
    }

    @Override
    public String toString() {
        return value + " " + unit;
    }
}

public class QuantityMeasurementApp {
    public static void main(String[] args) {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCHES);
        System.out.println(q1.equals(q2));
        System.out.println(q1.convertTo(LengthUnit.INCHES));
        System.out.println(q1.add(q2, LengthUnit.FEET));
    }
}