package uz.pdp.excelExample;

import com.google.gson.Gson;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import uz.pdp.wordExample.model.Student;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        //1.
        XSSFWorkbook workbook = new XSSFWorkbook();


        try (OutputStream out = new FileOutputStream("src/main/resources/test.xlsx")) {

            //
            XSSFSheet sheet = workbook.createSheet("Sahifa B6");
            sheet.setDefaultColumnWidth(20);
            sheet.setDefaultRowHeightInPoints(50);

            //
            XSSFRow row = sheet.createRow(0);
            XSSFCell cell0 = row.createCell(0);
            cell0.setCellValue("№");
            XSSFCellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            // TODO: 07.12.2021 set color in cell
//            cellStyle.setFillForegroundColor(HSSFColor.);
            cell0.setCellStyle(cellStyle);


            XSSFCell cell1 = row.createCell(1);
            cell1.setCellStyle(cellStyle);
            cell1.setCellValue("name");
            System.out.println("Yosh nechinchi ustunda chiqsin for Islomjon Isoqov special 07.12.2021 ");
            int ageColNum = new Scanner(System.in).nextInt();
            XSSFCell cell2 = row.createCell(ageColNum);
            cell2.setCellStyle(cellStyle);
            cell2.setCellValue("age");

            //
            try (Reader reader = new FileReader("src/main/resources/students.json")) {
                Gson gson = new Gson();
                Student[] students = gson.fromJson(reader, Student[].class);

                //4.3.1 WRITING DATA FROM JSON TO TABLE
                for (int i = 0; i < students.length; i++) {
                    Student student = students[i];
                    XSSFRow rowN = sheet.createRow(i + 1);
                    rowN.createCell(0).setCellValue(i + 1);
                    rowN.createCell(1).setCellValue(student.getName());
                    rowN.createCell(ageColNum).setCellValue(student.getAge());
                }

                int avgAgeRowNum = students.length + 1;

                XSSFRow avgAgeRow = sheet.createRow(avgAgeRowNum);
                XSSFCell cell = avgAgeRow.createCell(ageColNum - 1);
                cell.setCellValue("Average age: ");
                cell.setCellStyle(cellStyle);

                XSSFCell avgAgeCell = avgAgeRow.createCell(ageColNum);
                avgAgeCell.setCellStyle(cellStyle);

                String column_letter = CellReference.convertNumToColString(ageColNum);


                avgAgeCell.setCellFormula("AVERAGE(" + column_letter + "2:" + column_letter + "" + avgAgeRowNum + ")");


            }


            workbook.write(out);
            System.out.println("Success");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
