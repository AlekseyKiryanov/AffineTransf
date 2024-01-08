package com.cgvsu.affinetransformations;

import com.cgvsu.math.Vector3f;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AffineTransformationsTest {

    @Test
    void transformVertex01() {
        AffineTransformations A = new AffineTransformations();
        Vector3f V = new Vector3f(2,3,4);
        Assertions.assertEquals(new Vector3f(2,3,4),A.transformVertex(V));
    }

    @Test
    void transformVertex02() {
        AffineTransformations A = new AffineTransformations();
        A.setRz(-90F);
        Vector3f V = new Vector3f(0,1,0);
        Assertions.assertEquals(new Vector3f(-1,0,0),A.transformVertex(V));
    }

    @Test
    void transformVertex02a() {
        AffineTransformations A = new AffineTransformations();
        A.setRy(-90);
        Vector3f V = new Vector3f(1,7,0);
        Assertions.assertEquals(new Vector3f(0,7,1),A.transformVertex(V));
    }


    @Test
    void transformVertex03() {
        AffineTransformations A = new AffineTransformations(RotationOrder.ZYX,2,6,0.5F,0,0,-60,11,12,15);

        Vector3f V = new Vector3f((float)(Math.sqrt(3)/4),(float)1/12,4);
        Assertions.assertEquals(new Vector3f(11,13,17),A.transformVertex(V));
    }


    @Test
    void transformVertex04() {
        AffineTransformations A = new AffineTransformations();
        A.setRotationOrder(RotationOrder.ZYX);
        A.setRz(-45);
        A.setRy(-90);
        Vector3f V = new Vector3f(1,0,0);
        Assertions.assertEquals(new Vector3f(0,(float) Math.sqrt(2)/2,(float) Math.sqrt(2)/2),A.transformVertex(V));
    }

    @Test
    void transformVertex04a() {
        AffineTransformations A = new AffineTransformations();
        A.setRotationOrder(RotationOrder.YXZ);
        A.setRz(-45);
        A.setRy(-90);
        Vector3f V = new Vector3f(1,0,0);
        Assertions.assertEquals(new Vector3f(0,0,1),A.transformVertex(V));
    }
}