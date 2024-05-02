package curso.java.tienda.services;


import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.java.tienda.Entities.Producto;

import org.apache.poi.ss.usermodel.*;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class ExcelService{
	
	@Autowired
	ProductoService productoService;

	public  byte[] generarExcel() {
		 ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        // Crear un nuevo libro de Excel (.xlsx)
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Productos"); // Crear una nueva hoja

            List <Producto> productos = productoService.todos();
            
         // Crear encabezados
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("ID_CATEGORIA");
            headerRow.createCell(2).setCellValue("Nombre");
            headerRow.createCell(3).setCellValue("Descripción");
            headerRow.createCell(4).setCellValue("Precio");
            headerRow.createCell(5).setCellValue("Stock");
            headerRow.createCell(6).setCellValue("Fecha de Alta");
            headerRow.createCell(7).setCellValue("Fecha de Baja");
            headerRow.createCell(8).setCellValue("Impuesto");
            headerRow.createCell(9).setCellValue("Imagen");


            // Llenar la hoja con los datos de los productos
            int rowNum = 1;
            for (Producto producto : productos) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(producto.getId());
                row.createCell(1).setCellValue(producto.getId_categoria());
                row.createCell(2).setCellValue(producto.getNombre());
                row.createCell(3).setCellValue(producto.getDescripcion());
                row.createCell(4).setCellValue(producto.getPrecio());
                row.createCell(5).setCellValue(producto.getStock());
                row.createCell(6).setCellValue(producto.getFechaAlta());
                row.createCell(7).setCellValue(producto.getFechaBaja());
                row.createCell(8).setCellValue(producto.getImpuesto());
                row.createCell(9).setCellValue(producto.getImagen());
            }

            // Guardar el libro de Excel en un archivo
            
                try {
					workbook.write(byteArrayOutputStream);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            

            System.out.println("Archivo Excel creado satisfactoriamente.");
           
        } catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  return byteArrayOutputStream.toByteArray();
    }
	
	
	public List<Producto> importarExcel(InputStream inputStream) {
        List<Producto> productos = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0); // Obtener la primera hoja del libro (index 0)

            // Iterar sobre las filas de la hoja
            for (Row row : sheet) {
                // Ignorar la primera fila (encabezados)
                if (row.getRowNum() == 0) {
                    continue;
                }

                // Crear un nuevo objeto Producto desde los datos de la fila
                Producto producto = new Producto();

                // ID de categoría (columna 0)
                if (row.getCell(0) != null) {
                    producto.setId_categoria((long) row.getCell(0).getNumericCellValue());
                }

                // Nombre (columna 1)
                if (row.getCell(1) != null) {
                    producto.setNombre(row.getCell(1).getStringCellValue());
                }

                // Descripción (columna 2)
                if (row.getCell(2) != null) {
                    producto.setDescripcion(row.getCell(2).getStringCellValue());
                }

                // Precio (columna 3)
                if (row.getCell(3) != null) {
                    producto.setPrecio(row.getCell(3).getNumericCellValue());
                }

                // Stock (columna 4)
                if (row.getCell(4) != null) {
                    producto.setStock((int) row.getCell(4).getNumericCellValue());
                }

               
                producto.setFechaAlta(new java.sql.Timestamp(System.currentTimeMillis()));

                // Impuesto (columna 5)
                if (row.getCell(5) != null) {
                    producto.setImpuesto((float) row.getCell(5).getNumericCellValue());
                }

                // Imagen (columna 6)
                if (row.getCell(6) != null) {
                    producto.setImagen(row.getCell(6).getStringCellValue());
                }

                // Agregar el producto a la lista solo si tiene un nombre válido
                if (producto.getNombre() != null && !producto.getNombre().isEmpty()) {
                    productos.add(producto);
                }
            }

            System.out.println("Datos importados desde Excel correctamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return productos;
    }

}
