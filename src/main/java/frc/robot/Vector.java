// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/** Add your docs here. */
public class Vector {
    public double x;
    public double y;

    public Vector() {
        x = 0;
        y = 0;
    }

    public Vector(double X, double Y) {
        x = X;
        y = Y;
    }

    public static double dot(Vector a, Vector b) {
        double result = a.x * b.x + a.y * b.y;
        return result;
    }

    // Method to project vector A onto vector B
    public static Vector proj(Vector a, Vector b) {
        Vector result = new Vector(b.x, b.y);
        double a_dot_b = dot(a, b);
        double mag_b = mag(b);
        multScalar(result, (a_dot_b) / (mag_b * mag_b));
        return result;
    }

    public static Vector unit(Vector a) {
        Vector result = new Vector(a.x,a.y);
        double mag_a = mag(a);
        divScalar(result,mag_a);
        return result;
    }

    public static void addScalar(Vector a, double s) {
        a.x += s;
        a.y += s;
    }

    public static void multScalar(Vector a, double s) {
        a.x *= s;
        a.y *= s;
    }

    public static void divScalar(Vector a, double s) {
        a.x /= s;
        a.y /= s;
    }

    public static double mag(Vector a) {
        double result = Math.sqrt(a.x * a.x + a.y * a.y);
        return result;
    }
}
