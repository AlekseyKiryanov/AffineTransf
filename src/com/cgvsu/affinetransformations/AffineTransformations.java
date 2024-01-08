package com.cgvsu.affinetransformations;


import com.cgvsu.math.Vector3f;
import com.cgvsu.model.Model;

import javax.vecmath.Matrix4f;

public class AffineTransformations {

    //Перечесисление отвечающее за порядок поворотов в каждой из плоскостей
    private RotationOrder rotationOrder = RotationOrder.ZYX;
    //Параметры масштабирования
    private float Sx = 1;
    private float Sy = 1;
    private float Sz = 1;
    //Параметры поворота
    //УГЛЫ ПОВОРОТА ЗАДАЮТСЯ ПО ЧАСОВОЙ СРЕЛКЕ В ГРАДУСАХ
    private float Rx = 0;
    private float Ry = 0;
    private float Rz = 0;
    //Параметры переноса
    private float Tx = 0;
    private float Ty = 0;
    private float Tz = 0;

    private Matrix4f rotationMatrix = new Matrix4f(1, 0, 0, 0,
            0, 1, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1);
    private Matrix4f scaleMatrix = new Matrix4f(1, 0, 0, 0,
                                           0, 1, 0, 0,
                                           0, 0, 1, 0,
                                           0, 0, 0, 1);
    private Matrix4f translateMatrix = new Matrix4f(1, 0, 0, 0,
            0, 1, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1);;
    private Matrix4f affineMatrix = new Matrix4f(1, 0, 0, 0,
            0, 1, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1);

    private Matrix4f normalsTransformMatrix = new Matrix4f(1, 0, 0, 0,
            0, 1, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1);

    public AffineTransformations() {
    }

    public AffineTransformations(RotationOrder rotationOrder, float sx, float sy, float sz, float rx, float ry, float rz, float tx, float ty, float tz) {
        this.rotationOrder = rotationOrder;
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
        //Матрица поворота задается единичной
        rotationMatrix = new Matrix4f(1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1);

        //Вычисление матрицы переноса
        translateMatrix = new Matrix4f(1, 0, 0, Tx,
                0, 1, 0, Ty,
                0, 0, 1, Tz,
                0, 0, 0, 1);
        //Вычисление матрицы масштабирования
        scaleMatrix = new Matrix4f(Sx, 0, 0, 0,
                0, Sy, 0, 0,
                0, 0, Sz, 0,
                0, 0, 0, 1);
        //Вычисление тригонометрических функций
        float sinA = (float) Math.sin(Rx * Math.PI / 180);
        float cosA = (float) Math.cos(Rx * Math.PI / 180);

        float sinB = (float) Math.sin(Ry * Math.PI / 180);
        float cosB = (float) Math.cos(Ry * Math.PI / 180);

        float sinY = (float) Math.sin(Rz * Math.PI / 180);
        float cosY = (float) Math.cos(Rz * Math.PI / 180);

        //Матрицы поворота в каждой из плоскостей
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



        //Перемножение матриц поворота согласно их порядку
        switch (rotationOrder) {
            case ZYX -> {
                rotationMatrix.mul(X);
                rotationMatrix.mul(Y);
                rotationMatrix.mul(Z);
            }
            case ZXY -> {
                rotationMatrix.mul(Y);
                rotationMatrix.mul(X);
                rotationMatrix.mul(Z);
            }
            case YZX -> {
                rotationMatrix.mul(X);
                rotationMatrix.mul(Z);
                rotationMatrix.mul(Y);
            }
            case YXZ -> {
                rotationMatrix.mul(Z);
                rotationMatrix.mul(X);
                rotationMatrix.mul(Y);
            }
            case XZY -> {
                rotationMatrix.mul(Y);
                rotationMatrix.mul(Z);
                rotationMatrix.mul(X);
            }
            case XYZ -> {
                rotationMatrix.mul(Z);
                rotationMatrix.mul(Y);
                rotationMatrix.mul(X);
            }
            default -> rotationMatrix.mul(1);
        }
        //Вычисление матрицы афинных преобразований
        affineMatrix = new Matrix4f(translateMatrix);
        affineMatrix.mul(rotationMatrix);
        affineMatrix.mul(scaleMatrix);

        normalsTransformMatrix = new Matrix4f(rotationMatrix);
        normalsTransformMatrix.mul(scaleMatrix);
    }

    public Vector3f transformVertex(Vector3f v) {
        return VectorMath.mullMatrix4fOnVector3f(affineMatrix, v);
    }

    public Model transformModel(Model model) {


        for (int i = 0; i < model.vertices.size(); i++) {
            model.vertices.set(i, transformVertex(model.vertices.get(i)));
        }

        for (int i = 0; i < model.normals.size(); i++) {
            model.normals.set(i, VectorMath.mullMatrix4fOnVector3f(normalsTransformMatrix, model.normals.get(i)));
        }

        return model;
    }



    public RotationOrder getRotationOrder() {
        return rotationOrder;
    }

    public void setRotationOrder(RotationOrder rotationOrder) {
        this.rotationOrder = rotationOrder;
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

    public Matrix4f getRotationMatrix() {
        return rotationMatrix;
    }

    public Matrix4f getScaleMatrix() {
        return scaleMatrix;
    }

    public Matrix4f getTranslateMatrix() {
        return translateMatrix;
    }

    public Matrix4f getAffineMatrix() {
        return affineMatrix;
    }
}
