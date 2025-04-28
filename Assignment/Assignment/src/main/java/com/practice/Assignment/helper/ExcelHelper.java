package com.practice.Assignment.helper;

import com.practice.Assignment.entities.hr.Employee;
import com.practice.Assignment.entities.hr.Office;
import org.springframework.web.multipart.MultipartFile;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
                            employee.setSalary((int)cell.getNumericCellValue());
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


}
