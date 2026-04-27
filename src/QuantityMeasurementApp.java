enum LengthUnit {
    FEET(1.0),
    INCHES(1.0 / 12.0),
    YARDS(3.0),
    CENTIMETERS(1.0 / 30.48);

    private final double factor;

    LengthUnit(double factor) {
        this.factor = factor;
    }

    public double toBase(double value) {
        return value * factor;
    }

    public double fromBase(double base) {
        return base / factor;
    }
}

enum WeightUnit {
    KILOGRAM(1.0),
    GRAM(0.001),
    POUND(0.453592);

    private final double factor;

    WeightUnit(double factor) {
        this.factor = factor;
    }

    public double toBase(double value) {
        return value * factor;
    }

    public double fromBase(double base) {
        return base / factor;
    }
}

class QuantityLength {
    private final double value;
    private final LengthUnit unit;

    public QuantityLength(double value, LengthUnit unit) {
        if (unit == null || !Double.isFinite(value)) throw new IllegalArgumentException();
        this.value = value;
        this.unit = unit;
    }

    public QuantityLength convertTo(LengthUnit target) {
        double base = unit.toBase(value);
        return new QuantityLength(target.fromBase(base), target);
    }

    public QuantityLength add(QuantityLength other) {
        return add(other, this.unit);
    }

    public QuantityLength add(QuantityLength other, LengthUnit target) {
        double sum = unit.toBase(value) + other.unit.toBase(other.value);
        return new QuantityLength(target.fromBase(sum), target);
    }

    public boolean equals(Object o) {
        if (!(o instanceof QuantityLength q)) return false;
        return Math.abs(unit.toBase(value) - q.unit.toBase(q.value)) < 1e-6;
    }

    public String toString() {
        return value + " " + unit;
    }
}

class QuantityWeight {
    private final double value;
    private final WeightUnit unit;

    public QuantityWeight(double value, WeightUnit unit) {
        if (unit == null || !Double.isFinite(value)) throw new IllegalArgumentException();
        this.value = value;
        this.unit = unit;
    }

    public QuantityWeight convertTo(WeightUnit target) {
        double base = unit.toBase(value);
        return new QuantityWeight(target.fromBase(base), target);
    }

    public QuantityWeight add(QuantityWeight other) {
        return add(other, this.unit);
    }

    public QuantityWeight add(QuantityWeight other, WeightUnit target) {
        double sum = unit.toBase(value) + other.unit.toBase(other.value);
        return new QuantityWeight(target.fromBase(sum), target);
    }

    public boolean equals(Object o) {
        if (!(o instanceof QuantityWeight q)) return false;
        return Math.abs(unit.toBase(value) - q.unit.toBase(q.value)) < 1e-6;
    }

    public String toString() {
        return value + " " + unit;
    }
}

public class QuantityMeasurementApp {
    public static void main(String[] args) {

        System.out.println(new QuantityLength(1, LengthUnit.FEET).convertTo(LengthUnit.INCHES));
        System.out.println(new QuantityLength(1, LengthUnit.FEET).add(new QuantityLength(12, LengthUnit.INCHES)));
        System.out.println(new QuantityLength(1, LengthUnit.FEET).add(new QuantityLength(12, LengthUnit.INCHES), LengthUnit.YARDS));

        System.out.println(new QuantityWeight(1, WeightUnit.KILOGRAM).convertTo(WeightUnit.GRAM));
        System.out.println(new QuantityWeight(1, WeightUnit.KILOGRAM).add(new QuantityWeight(1000, WeightUnit.GRAM)));
        System.out.println(new QuantityWeight(1, WeightUnit.KILOGRAM).add(new QuantityWeight(1000, WeightUnit.GRAM), WeightUnit.POUND));
    }
}