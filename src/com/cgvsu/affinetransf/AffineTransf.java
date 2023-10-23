package com.cgvsu.affinetransf;


import com.cgvsu.math.Vector3f;
import com.cgvsu.model.Model;

import javax.vecmath.Matrix4f;
import java.util.ArrayList;

public class AffineTransf {

    private OrderRotation or = OrderRotation.ZYX;
    private float Sx = 1;
    private float Sy = 1;
    private float Sz = 1;
    private float Rx = 0;
    private float Ry = 0;
    private float Rz = 0;
    //УГЛЫ ПОВОРОТА ЗАДАЮТСЯ ПО ЧАСОВОЙ СРЕЛКЕ
    private float Tx = 0;
    private float Ty = 0;
    private float Tz = 0;

    private Matrix4f R = new Matrix4f(1, 0, 0, 0,
            0, 1, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1);
    private Matrix4f S;
    private Matrix4f T;
    private Matrix4f A = new Matrix4f(1, 0, 0, 0,
            0, 1, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1);

    public AffineTransf() {
    }

    public AffineTransf(OrderRotation or, float sx, float sy, float sz, float rx, float ry, float rz, float tx, float ty, float tz) {
        this.or = or;
        Sx = sx;
        Sy = sy;
        Sz = sz;
        Rx = rx;
        Ry = ry;
        Rz = rz;
        Tx = tx;
        Ty = ty;
        Tz = tz;

        calculateA();
    }

    private void calculateA() {

        R = new Matrix4f(1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1);

        T = new Matrix4f(1, 0, 0, Tx,
                0, 1, 0, Ty,
                0, 0, 1, Tz,
                0, 0, 0, 1);

        S = new Matrix4f(Sx, 0, 0, 0,
                0, Sy, 0, 0,
                0, 0, Sz, 0,
                0, 0, 0, 1);

        float sinA = (float) Math.sin(Rx * Math.PI / 180);
        float cosA = (float) Math.cos(Rx * Math.PI / 180);

        float sinB = (float) Math.sin(Ry * Math.PI / 180);
        float cosB = (float) Math.cos(Ry * Math.PI / 180);

        float sinY = (float) Math.sin(Rz * Math.PI / 180);
        float cosY = (float) Math.cos(Rz * Math.PI / 180);

        Matrix4f Z = new Matrix4f(cosY, sinY, 0, 0,
                -sinY, cosY, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1);


        Matrix4f Y = new Matrix4f(cosB, 0, sinB, 0,
                0, 1, 0, 0,
                -sinB, 0, cosB, 0,
                0, 0, 0, 1);

        Matrix4f X = new Matrix4f(1, 0, 0, 0,
                0, cosA, sinA, 0,
                0, -sinA, cosA, 0,
                0, 0, 0, 1);

        A = new Matrix4f(T);

        switch (or) {
            case ZYX -> {
                R.mul(X);
                R.mul(Y);
                R.mul(Z);
            }
            case ZXY -> {
                R.mul(Y);
                R.mul(X);
                R.mul(Z);
            }
            case YZX -> {
                R.mul(X);
                R.mul(Z);
                R.mul(Y);
            }
            case YXZ -> {
                R.mul(Z);
                R.mul(X);
                R.mul(Y);
            }
            case XZY -> {
                R.mul(Y);
                R.mul(Z);
                R.mul(X);
            }
            case XYZ -> {
                R.mul(Z);
                R.mul(Y);
                R.mul(X);
            }
            default -> R.mul(1);
        }

        A.mul(R);
        A.mul(S);
    }

    public Vector3f transformVertex(Vector3f v) {
        return VectorMath.mullMatrix4fOnVector3f(A, v);
    }

    public Model transformModel(Model m) {
        Model rez = new Model();
        rez.polygons = new ArrayList<>(m.polygons);
        rez.textureVertices = new ArrayList<>(m.textureVertices);

        rez.vertices = new ArrayList<>();
        for (Vector3f v : m.vertices) {
            rez.vertices.add(transformVertex(v));
        }

        for (Vector3f v : m.normals) {
            rez.normals.add(VectorMath.mullMatrix4fOnVector3f(R,v));
        }

        return rez;
    }


    public OrderRotation getOr() {
        return or;
    }

    public void setOr(OrderRotation or) {
        this.or = or;
        calculateA();
    }

    public float getSx() {
        return Sx;
    }

    public void setSx(float sx) {
        Sx = sx;
        calculateA();
    }

    public float getSy() {
        return Sy;
    }

    public void setSy(float sy) {
        Sy = sy;
        calculateA();
    }

    public float getSz() {
        return Sz;
    }

    public void setSz(float sz) {
        Sz = sz;
        calculateA();
    }

    public float getRx() {
        return Rx;
    }

    public void setRx(float rx) {
        Rx = rx;
        calculateA();
    }

    public float getRy() {
        return Ry;
    }

    public void setRy(float ry) {
        Ry = ry;
        calculateA();
    }

    public float getRz() {
        return Rz;
    }

    public void setRz(float rz) {
        Rz = rz;
        calculateA();
    }

    public float getTx() {
        return Tx;
    }

    public void setTx(float tx) {
        Tx = tx;
        calculateA();
    }

    public float getTy() {
        return Ty;
    }

    public void setTy(float ty) {
        Ty = ty;
        calculateA();
    }

    public float getTz() {
        return Tz;
    }

    public void setTz(float tz) {
        Tz = tz;
        calculateA();
    }

    public Matrix4f getR() {
        return R;
    }

    public Matrix4f getS() {
        return S;
    }

    public Matrix4f getT() {
        return T;
    }

    public Matrix4f getA() {
        return A;
    }
}
