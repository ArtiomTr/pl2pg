package org.example.plsql.ast;

public class SubtypeDefinition extends Declaration {

    public static abstract class Constraint extends Node {

    }

    public static class CharacterSetConstraint extends Constraint {

        private StringLiteral characterSet;

        public CharacterSetConstraint(StringLiteral characterSet) {
            this.characterSet = characterSet;
        }

        @Override
        public <T> T accept(Visitor<T> visitor) {
            return visitor.visit(this);
        }
    }

    public static class PrecisionConstraint extends Constraint {

        private NumberLiteral precision;

        private NumberLiteral scale;

        public PrecisionConstraint(NumberLiteral precision, NumberLiteral scale) {
            this.precision = precision;
            this.scale = scale;
        }

        @Override
        public <T> T accept(Visitor<T> visitor) {
            return visitor.visit(this);
        }
    }

    public static class RangeConstraint extends Constraint {

        private NumberLiteral lowValue;

        private NumberLiteral highValue;

        public RangeConstraint(NumberLiteral lowValue, NumberLiteral highValue) {
            this.lowValue = lowValue;
            this.highValue = highValue;
        }

        @Override
        public <T> T accept(Visitor<T> visitor) {
            return visitor.visit(this);
        }
    }

    private Identifier name;

    private TypeToken baseType;

    private Constraint constraint;

    public SubtypeDefinition(Identifier name, TypeToken baseType, Constraint constraint) {
        this.name = name;
        this.baseType = baseType;
        this.constraint = constraint;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
