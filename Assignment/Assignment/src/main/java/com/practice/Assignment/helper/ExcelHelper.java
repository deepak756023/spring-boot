package com.practice.Assignment.helper;

import com.practice.Assignment.entities.hr.Employee;
import com.practice.Assignment.entities.hr.Office;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelHelper {

    //check that file is of Excel type or not
    public static boolean checkExcelFormat(MultipartFile file) {

        String contentType = file.getContentType();

        if (contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
            return true;
        } else {
            return false;
        }

    }

    //convert excel to list of products
    public static List<Employee> convertExcelToListOfEmployee(InputStream is) {
        List<Employee> list = new ArrayList<>();

        try {
            XSSFWorkbook workbook = new XSSFWorkbook(is);
            XSSFSheet sheet = workbook.getSheet("data");


            int rowNumber = 0;
            Iterator<Row> iterator = sheet.iterator();

            while (iterator.hasNext()) {
                Row row = iterator.next();

                if (rowNumber == 0) {
                    rowNumber++;
                    continue; // skip header
                }

                Iterator<Cell> cells = row.iterator();
                int cid = 0;

                Employee employee = new Employee();
                Office office = new Office();

                while (cells.hasNext()) {
                    Cell cell = cells.next();

                    switch (cid) {
                        case 0:
                            employee.setEmployeeId((int) cell.getNumericCellValue());
                            break;
                        case 1:
                            employee.setFirstName(cell.getStringCellValue());
                            break;
                        case 2:
                            employee.setLastName(cell.getStringCellValue());
                            break;
                        case 3:
                            employee.setJobTitle(cell.getStringCellValue());
                            break;
                        case 4:
                            employee.setSalary((int) cell.getNumericCellValue());
                            break;
                        case 5:
                            office.setOfficeId((int) cell.getNumericCellValue());
                            break;
                        case 6:
                            office.setAddress(cell.getStringCellValue());
                            break;
                        case 7:
                            office.setCity(cell.getStringCellValue());
                            break;
                        case 8:
                            office.setState(cell.getStringCellValue());
                            break;
                        default:
                            break;
                    }
                    cid++;
                }

                employee.setOffice(office);
                list.add(employee);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //Exporting from db to Excel
    public static String[] header = {
            "employee_id",
            "first_name",
            "last_name",
            "job_title",
            "salary",
            "officeId",
            "address",
            "city",
            "state"
    };

    public static String sheetNme = "employee_data";

    public static ByteArrayInputStream dataToExcel(List<Employee> list) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {


            Sheet sheet = workbook.createSheet(sheetNme);

            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);

            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.WHITE.getIndex());
            headerStyle.setFont(headerFont);

            Row row = sheet.createRow(0);
            for (int i = 0; i < header.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(header[i]);
                cell.setCellStyle(headerStyle);
            }

            int rowIndex = 1;
            for (Employee e : list) {
                Row dataRow = sheet.createRow(rowIndex);


                dataRow.createCell(0).setCellValue(e.getEmployeeId());
                dataRow.createCell(1).setCellValue(e.getFirstName());
                dataRow.createCell(2).setCellValue(e.getLastName());
                dataRow.createCell(3).setCellValue(e.getJobTitle());
                dataRow.createCell(4).setCellValue(e.getSalary());
                dataRow.createCell(5).setCellValue(e.getOffice().getOfficeId());
                dataRow.createCell(6).setCellValue(e.getOffice().getAddress());
                dataRow.createCell(7).setCellValue(e.getOffice().getCity());
                dataRow.createCell(8).setCellValue(e.getOffice().getState());

                rowIndex++;
            }

            workbook.write(out);

            return new ByteArrayInputStream(out.toByteArray());


        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("failed to export data");
            return null;
        } finally {
            workbook.close();
            out.close();

        }


    }

}
