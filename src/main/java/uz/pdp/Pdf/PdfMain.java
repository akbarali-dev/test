package uz.pdp.Pdf;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import uz.pdp.wordExample.model.Student;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PdfMain {
    public static void main(String[] args) {
        Gson gson = new Gson();
        List<Student> studentList;
        try (PdfWriter writer = new PdfWriter("src/main/resources/test.pdf")) {
            PdfDocument pdfDocument = new PdfDocument(writer);
            pdfDocument.addNewPage();
            Document document = new Document(pdfDocument);
            Paragraph paragraph = new Paragraph("JADVAL");
            paragraph.setTextAlignment(TextAlignment.CENTER);
            paragraph.setFontSize(50);
            document.add(paragraph);

            Reader reader = new FileReader("src/main/resources/studentList.json");
            Type type = new TypeToken<ArrayList<Student>>() {
            }.getType();
            studentList = gson.fromJson(reader, type);
            if (studentList.isEmpty())
                for (int i = 0; i < studentList.size(); i++) {
                    System.out.println((i + 1) + " name: " + studentList.get(i).getName() + "  age: " + studentList.get(i).getAge());
                }


            studentList.add(addStudent());


            Writer writer1 = new FileWriter("src/main/resources/studentList.json");
            String newStudent = gson.toJson(studentList);
            writer1.write(newStudent);
            writer1.close();
            float[] columns = {30f, 30, 300, 30, 30};
            Table table = new Table(columns);
            table.setMarginLeft(70);
            table.addCell("N");
            table.addCell("ID");
            table.addCell("NAME");
            table.addCell("age");
            table.addCell("image");

            for (int i = 0; i < studentList.size(); i++) {
                table.addCell(String.valueOf((i + 1)));
                table.addCell(String.valueOf(studentList.get(i).getId()));
                table.addCell(studentList.get(i).getName());
                table.addCell(String.valueOf(studentList.get(i).getAge()));
                ImageData data1 = ImageDataFactory.create("src/main/resources/image/Зеленая_галочка.jpg");
                ImageData data2 = ImageDataFactory.create("src/main/resources/image/красный-крест-знак-удаления-d-сияющий-r-152625115.jpg");
                Image image;
                if(studentList.get(i).getAge()%2 == 0) {
                     image = new Image(data1);
                } else image = new Image(data2);

                image.setMaxHeight(20);
                table.addCell(image);

            }
            document.add(table);

            ImageData data = ImageDataFactory.create("D:\\GALERIYA\\fon uchun rasmlar\\@wall8k_uz(6).jpg");

            Image image = new Image(data);
            image.setMaxHeight(250);
            document.add(image);

            document.close();
            System.out.println("success");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Student addStudent() {
        System.out.print("name: ");
        String name = new Scanner(System.in).nextLine();
        System.out.print("age: ");
        int age = new Scanner(System.in).nextInt();
        return new Student((int) (Math.random() * 1000), name, age);
    }
}
