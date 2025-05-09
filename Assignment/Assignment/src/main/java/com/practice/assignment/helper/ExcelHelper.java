package com.practice.assignment.helper;

import com.practice.assignment.entities.hr.Employee;
import com.practice.assignment.entities.hr.Office;
import com.practice.assignment.exception.custom_exception.ExcelColumnMismatch;
import com.practice.assignment.repo.hr.OfficeRepository;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class ExcelHelper {


    private  final OfficeRepository officeRepository;

    public ExcelHelper(OfficeRepository officeRepository) {
        this.officeRepository = officeRepository;
    }

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
    public  List<Employee> convertExcelToListOfEmployee(InputStream is) throws ExcelColumnMismatch, IllegalArgumentException {
        List<Employee> list = new ArrayList<>();
        List<String> expectedHeaders = Arrays.asList(
                "employeeId", "firstName", "lastName", "jobTitle", "salary",
                "officeId"
        );

        try (XSSFWorkbook workbook = new XSSFWorkbook(is)) {
            XSSFSheet sheet = workbook.getSheet("data");

            if (sheet == null) {
                throw new IllegalArgumentException("Sheet with name 'data' not found.");
            }

            // Check if the first row matches the expected header
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new IllegalArgumentException("No header row found.");
            }

            // Validate the column names in the header
            for (int i = 0; i < expectedHeaders.size(); i++) {
                Cell headerCell = headerRow.getCell(i);
                if (headerCell == null || !headerCell.getStringCellValue().equals(expectedHeaders.get(i))) {
                    throw new ExcelColumnMismatch("Column name mismatch at index " + i + ": Expected '"
                            + expectedHeaders.get(i) + "' but got '" +
                            (headerCell != null ? headerCell.getStringCellValue() : "null") + "'");
                }
            }

            // Process the data rows
            for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                Row row = sheet.getRow(rowNum);
                if (row == null) continue;

                Employee employee = new Employee();
                int officeId = 0;

                // Process all cells in the row
                for (int cellNum = 0; cellNum < expectedHeaders.size(); cellNum++) {
                    Cell cell = row.getCell(cellNum);
                    if (cell == null) continue; // Skip empty cells

                    switch (cellNum) {
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
                            officeId = (int) cell.getNumericCellValue(); // Store officeId
                            break;
                    }
                }


                Office office = officeRepository.findByOfficeId(officeId);


                employee.setOffice(office);
                list.add(employee);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading Excel file", e);
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
            for (int i = 0; i < header.length; i++) {
                sheet.autoSizeColumn(i);
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

