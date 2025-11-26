package com.opencart.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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

    public static void setCellData(String filePath, String sheetName, int rowNum, int colNum, String data) {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            Row row = sheet.getRow(rowNum);

            // Si la fila no existe, la creamos (aunque no debería pasar si usas la plantilla)
            if (row == null) {
                row = sheet.createRow(rowNum);
            }

            Cell cell = row.getCell(colNum);
            // Si la celda no existe, la creamos
            if (cell == null) {
                cell = row.createCell(colNum);
            }

            // Escribimos el valor
            cell.setCellValue(data);

            // Guardamos los cambios cerrando el flujo de salida
            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                workbook.write(fos);
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al escribir en el Excel: " + e.getMessage());
        }
    }
}