package com.cgvsu;

import com.cgvsu.affinetransf.AffineTransf;
import com.cgvsu.affinetransf.OrderRotation;
import com.cgvsu.model.Model;
import com.cgvsu.objreader.ObjReader;
import com.cgvsu.objwriter.ObjWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) throws IOException {

        Path fileName = Path.of("D:\\tatraT6B5.obj");
        String fileContent = Files.readString(fileName);


        Model model = ObjReader.read(fileContent);
        System.out.println("Модель открыта");

        AffineTransf AT = new AffineTransf();
        AT.setOr(OrderRotation.YXZ);
        AT.setRx(180);
        Model new_model = AT.transformModel(model);
        System.out.println("Модель изменена");


        ObjWriter.write("D:\\tatraT6B5_1.obj",new_model);
        System.out.println("Модель сохранена");
    }
}
