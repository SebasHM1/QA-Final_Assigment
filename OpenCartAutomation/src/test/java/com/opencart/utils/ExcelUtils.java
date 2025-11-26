package com.opencart.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtils {

    // Método para obtener todos los datos de una hoja como lista de objetos (para DataProvider)
    public static Object[][] getData(String filePath, String sheetName) {
        List<Object[]> data = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            int rowCount = sheet.getPhysicalNumberOfRows();
            int colCount = sheet.getRow(0).getPhysicalNumberOfCells();

            // Empezamos en i=1 para saltar los encabezados
            for (int i = 1; i < rowCount; i++) {
                Row row = sheet.getRow(i);
                Object[] rowData = new Object[colCount];
                for (int j = 0; j < colCount; j++) {
                    Cell cell = row.getCell(j);
                    rowData[j] = cell != null ? cell.toString() : "";
                }
                data.add(rowData);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return data.toArray(new Object[0][0]);
    }
}