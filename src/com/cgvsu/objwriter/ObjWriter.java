package com.cgvsu.objwriter;

import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;
import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Locale;

public class ObjWriter {

    public static void write(String fileContent, Model model) {
        Locale.setDefault(Locale.US);

        try (FileWriter fw = new FileWriter(fileContent, false)) {
            for (Vector3f a : model.vertices) {
                fw.write(String.format("v %.4f %.4f %.4f%n", a.getX(), a.getY(), a.getZ()));
            }
            fw.write(String.format("# %d vertices%n%n", model.vertices.size()));

            for (Vector2f a : model.textureVertices) {
                fw.write(String.format("vt %.4f %.4f%n", a.getX(), a.getY()));
            }
            fw.write(String.format("# %d texture coords%n%n", model.textureVertices.size()));

            for (Vector3f a : model.normals) {
                fw.write(String.format("vn %.4f %.4f %.4f%n", a.getX(), a.getY(), a.getZ()));
            }
            fw.write(String.format("# %d normals%n%n", model.normals.size()));

            int triangles = 0;
            for (Polygon p : model.polygons) {
                int k = p.getVertexIndices().size();
                if (k == 3) {
                    triangles++;
                }
                fw.append("f");
                if (p.getTextureVertexIndices().isEmpty() && p.getNormalIndices().isEmpty()) {
                    for (int i = 0; i < k; i++) {
                        fw.write(String.format(" %d", p.getVertexIndices().get(i)+1));
                    }

                } else if (!p.getTextureVertexIndices().isEmpty() && p.getNormalIndices().isEmpty()) {
                    for (int i = 0; i < k; i++) {
                        fw.write(String.format(" %d/%d", p.getVertexIndices().get(i)+1, p.getTextureVertexIndices().get(i)+1));
                    }
                } else if (p.getTextureVertexIndices().isEmpty() && !p.getNormalIndices().isEmpty()) {
                    for (int i = 0; i < k; i++) {
                        fw.write(String.format(" %d//%d", p.getVertexIndices().get(i)+1, p.getNormalIndices().get(i)+1));
                    }
                } else if (!p.getTextureVertexIndices().isEmpty() && !p.getNormalIndices().isEmpty()) {
                    for (int i = 0; i < k; i++) {
                        fw.write(String.format(" %d/%d/%d", p.getVertexIndices().get(i)+1, p.getTextureVertexIndices().get(i)+1, p.getNormalIndices().get(i)+1));
                    }
                }
                fw.append("\n");
            }
            fw.write(String.format("# %d polygons - %d triangles", model.polygons.size()-triangles, triangles));


            fw.flush();
        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }


    }


}
