package com.cgvsu;

import com.cgvsu.model.Model;
import com.cgvsu.objreader.ObjReader;
import com.cgvsu.objwriter.ObjWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) throws IOException {

        Path fileName = Path.of("D:\\Teapot01.obj");
        String fileContent = Files.readString(fileName);


        Model model = ObjReader.read(fileContent);
        System.out.println("Модель открыта");


        ObjWriter.write("D:\\Teapot04.obj",model);
        System.out.println("Модель сохранена");
    }
}
