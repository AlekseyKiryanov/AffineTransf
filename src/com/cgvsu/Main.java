package com.cgvsu;

import com.cgvsu.affinetransformations.AffineTransformations;
import com.cgvsu.affinetransformations.RotationOrder;
import com.cgvsu.model.Model;
import com.cgvsu.objreader.ObjReader;
import com.cgvsu.objwriter.ObjWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) throws IOException {

        Path fileName = Path.of("D:\\serdtse.obj");
        String fileContent = Files.readString(fileName);


        Model model = ObjReader.read(fileContent);
        System.out.println("Модель открыта");

        AffineTransformations AT = new AffineTransformations();
        AT.setRotationOrder(RotationOrder.YXZ);
        AT.setRx(90);
        Model new_model = AT.transformModel(model);
        System.out.println("Модель изменена");


        ObjWriter.write("D:\\serdtse_1.obj",new_model);
        System.out.println("Модель сохранена");

    }
}
