/**
 * Examples do uso de method reference
 * Referência: https://docs.oracle.com/javase/tutorial/java/javaOO/methodreferences.html
 */
package org.example.method.reference;

import java.util.function.BiFunction;

public class Main {
    public static <T> T merge(T a, T b, BiFunction<T, T, T> merger) {
        return merger.apply(a, b);
    }

    public static String appendStrings(String a, String b) {
        return a + b;
    }

    public String appendStrings2(String a, String b) {
        return a + b;
    }

    public static void main(String[] args) {

        Main myApp = new Main();

        // a) Calling the method mergeThings with a lambda expression

        // b) Reference to a static method

        // c) Reference to an instance method of a particular object

        // d) Reference to an instance method of an arbitrary object of a
        // particular type

    }
}
