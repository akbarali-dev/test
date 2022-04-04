package uz.pdp.wordExample;

import com.google.gson.Gson;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import uz.pdp.wordExample.model.Student;

import java.io.*;

public class Main {
    public static void main(String[] args) {


        // 1. CREATE A NEW FILE
        try (OutputStream out = new FileOutputStream("src/main/resources/test.docx")) {

            //2. CREATE A WORD DOCUMENT
            XWPFDocument document = new XWPFDocument();

            //3. CREATE A NEW PARAGRAPH IN WORD DOC
            XWPFParagraph paragraph = document.createParagraph();
//            paragraph.setAlignment(ParagraphAlignment.CENTER);

            //4. CREATE A RUN FROM PARAGRAPH (text)
            XWPFRun run = paragraph.createRun();

//            4.1 GIVING A VALUE TO RUN
            run.setText("Hello B6, good afternoon!!!");
            run.setFontSize(14);
            run.setFontFamily("Algerian");
            run.setTextPosition(50);


//            4.2 CREATE A TABLE FROM DOCUMENT
            XWPFTable table = document.createTable();
//          QATOR (HEADER)
            XWPFTableRow row = table.getRow(0);
//          YACHEYKA
            XWPFTableCell cell = row.getCell(0);
//          YACHEYKANI ICHIGA TEXT YOZISH
            cell.setText("№");
            // IKKINCHI YACHEYKA
            XWPFTableCell cell2 = row.addNewTableCell();
            cell2.setText("name");
            cell2.setWidth("1500");
            // UCHINCHI YACHEYKA
            XWPFTableCell cell3 = row.addNewTableCell();
            cell3.setWidth("2000");
            cell3.setText("age");

//            4.3 GET DATA FROM JSON
            try (Reader reader = new FileReader("src/main/resources/students.json")) {
                Gson gson = new Gson();
                Student[] students = gson.fromJson(reader, Student[].class);

                //4.3.1 WRITING DATA FROM JSON TO TABLE
                for (int i = 0; i < students.length; i++) {
                    Student student = students[i];
                    XWPFTableRow rowN = table.createRow();
                    rowN.getCell(0).setText(String.valueOf(i + 1));
                    rowN.getCell(1).setText(student.getName());
                    rowN.getCell(2).setText(String.valueOf(student.getAge()));
                }

            }

//            4.4 INSERTING AN IMAGE
            XWPFParagraph paragraph2 = document.createParagraph();

            XWPFRun run2 = paragraph2.createRun();
            String imgPath = "C:/Users/Abror/Desktop/img.png";
            try (InputStream inputStream = new FileInputStream(imgPath)) {
                run2.addPicture(
                        inputStream,
                        XWPFDocument.PICTURE_TYPE_PNG, imgPath, Units.toEMU(400),
                        Units.toEMU(200));

            } catch (InvalidFormatException e) {
                e.printStackTrace();
            }
//            5. WRITING EVERYTHING IN TO FILE
            document.write(out);
            System.out.println("Successfully done!!");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
