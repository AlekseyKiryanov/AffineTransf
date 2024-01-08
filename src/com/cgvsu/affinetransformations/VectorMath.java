package com.cgvsu.affinetransformations;

import com.cgvsu.math.Vector3f;

import javax.vecmath.Matrix4f;

public class VectorMath {
    public static Vector3f mullMatrix4fOnVector3f(Matrix4f m, Vector3f v) {
        return new Vector3f(m.m00 * v.getX() + m.m01 * v.getY() + m.m02 * v.getZ() + m.m03,
                m.m10 * v.getX() + m.m11 * v.getY() + m.m12 * v.getZ() + m.m13,
                m.m20 * v.getX() + m.m21 * v.getY() + m.m22 * v.getZ() + m.m23);
    }
}