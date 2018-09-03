package com.arhiser.difftest.difs;

import java.util.ArrayList;

import de.danielbechler.diff.ObjectDifferBuilder;
import de.danielbechler.diff.node.DiffNode;

public class Test {

    public void test() {
        Vals v1 = new Vals(1, 1f, "1111");
        Vals v2 = new Vals(2, 1f, "1111");

        v1.getVals().add(v2);
        v1.getVals().add(v2);
        v1.getVals().add(v2);

        v2.getVals().add(v2);
        v2.getVals().add(v1);
        v2.getVals().add(v2);

        ObjectDifferBuilder builder = ObjectDifferBuilder.startBuilding();
        builder.introspection().setDefaultIntrospector(new AndroidIntrospector());

        DiffNode root = builder.build().compare(v2, v1);
    }

    public static class Vals {
        int a;
        float b;
        String c;
        ArrayList<Vals> vals = new ArrayList<>();

        public Vals(int a, float b, String c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        public int getA() {
            return a;
        }

        public void setA(int a) {
            this.a = a;
        }

        public float getB() {
            return b;
        }

        public void setB(float b) {
            this.b = b;
        }

        public String getC() {
            return c;
        }

        public void setC(String c) {
            this.c = c;
        }

        public ArrayList<Vals> getVals() {
            return vals;
        }
    }
}
