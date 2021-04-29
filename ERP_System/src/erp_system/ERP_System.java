/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package erp_system;

import java.util.ArrayList;
import java.util.Scanner;

import java.io.FileReader;
import java.util.Random;
import java.util.Date;
import java.util.Calendar;

import com.opencsv.*;

/**
 *
 * @author Marcio
 */
public class ERP_System {

    static Scanner input;
    
    public static ArrayList<Product> database = new ArrayList<>();
    public static String[] headers;
    
    public static void main(String[] args) {
        
        input = new Scanner(System.in);
        
        CargarDatos();
        
        Menu();
    }
    
    static void CargarDatos()
    {
        //Cargar CSV
        CSVReader data;
        
        try {
            FileReader fr = new FileReader("Products.csv");
            data = new CSVReader(fr);
            System.out.println("Datos leidos!");
        }
        catch (Exception e) {
            System.err.println("Datos no fueron leidos!");
            return;
        }
        
        //Leer datos del CSV
        try {
            //variables para porcentaje de carga
            int total = 14593;
            int count = 0;
            float percent = 0;
            int nextPercent = 0;
            //
            
            String[] line;
            Random r = new Random();
            
            while ((line = data.readNext()) != null)
            {   
                //no ingresar el header
                if (count == 0)
                {
                    headers = line;
                    count += 1;
                    continue;
                }
                
                //crear producto
                Product p = new Product(Integer.valueOf(line[0]), line[21], Float.valueOf(line[1]));
                
                //llenar stock
                int n = 1 + r.nextInt(20); //número random de cantidad de stock
                Calendar c = Calendar.getInstance();
                
                for (int i = 0; i < n; i++)
                {
                    //generar nuevas fechas
                    c.setTime(new Date());
                    c.add(Calendar.DATE, i);
                    
                    //añadir nuevo stock
                    Stock s = new Stock(c.getTime(), 1);
                    p.getStockList().add(s);
                }
                
                database.add(p);
                
                //porcentaje de carga
                count += 1;
                if (count >= total) break; //opcional: limitar cantidad de productos a cargar
            
                percent = (float)count / total * 100;

                if (percent > nextPercent)
                {
                    System.out.println("Creando productos " + nextPercent + "%");
                    nextPercent += 10;
                }
            }
            
            System.out.println("Productos creados!");
        }
        catch (NumberFormatException nfe)
        {
            System.err.println("Error al convertir números!");
        }
        catch (Exception e)
        {
            System.err.println("No se leyeron los datos!");
        }
    }
 
    static void Menu ()
    {
        String option;
        
        while (true)
        {
            System.out.println("1 – Buscar producto por código\n" +
                    "2 – Buscar producto por nombre\n"
                    + "3 - Retirar producto por código\n"
                    + "4 - Salir");
            
            option = input.nextLine();
            
            switch(option)
            {
                case "1":
                    BuscarPorID();
                    break;
                case "2":
                    BuscarPorNombre();
                    break;
                case "3":
                    RetirarPorID();
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Opción inválida!");
            }
        }  
    }
    
    static void BuscarPorID()
    {
        //obtener número entero
        int id = GetInt("Ingrese ID:");
    
        if (id > database.size() || id < 1)
        {
            System.out.println("Producto no encontrado!");
            return;
        }

        //offset de id
        id -= 1;
        
        //imprimir datos
        System.out.printf("%-25s%d%n", "ID", database.get(id).getId());
        System.out.printf("%-25s%s%n", "Nombre", database.get(id).getName());
        System.out.printf("%-25s%.2f%n", "Precio", database.get(id).getPrice());
        System.out.printf("%-25s%d%n", "Cantidad en stock", database.get(id).getStockList().size());
    }
    
    static void BuscarPorNombre()
    {
        boolean found = false;
        
        System.out.println("Ingrese nombre a buscar:");
        String name = input.nextLine();
        
        for (Product producto : database)
        {
            if (producto.getName().toLowerCase().contains(name.toLowerCase()))
            {
                found = true;
                
                System.out.printf("ID:%d   %s   $%.2f%n",
                        producto.getId(), producto.getName(), producto.getPrice());
            }
        }
        
        if (!found) System.out.println("No se encontraron resultados!");
    }

    static void RetirarPorID()
    {
        //obtener número entero para el ID
        int id = GetInt("Ingrese ID:");
    
        if (id > database.size() || id < 1)
        {
            System.out.println("Producto no encontrado!");
            return;
        }
        
        //offset de id
        id -= 1;
        
        //obtener producto
        Product p = database.get(id);
        int size = p.getStockList().size();
        
        if (size == 0)
        {
            System.out.println("No hay en stock!");
            return;
        }
        
        //obtener cantidad
        int quantity = GetInt("Cantidad a retirar:");
        if (quantity < 1)
        {
            System.out.println("Valor no aceptado!");
            return;
        }
        
        //cantidad insuficiente
        if (size < quantity)
        {
            while (true)
            {
                System.out.println("Hay solamente " + size + " en stock!");
                System.out.println("Desea retirar esa cantidad de cualquier forma? SI/NO");
                
                String option = input.nextLine();
                
                if (option.equalsIgnoreCase("SI"))
                {
                    for(int i = 0; i < size; i += 1)
                    {
                        Stock s = p.getStockList().poll();
                        
                        System.out.println("Retirado >" + p.getName() + "< del " + s.getDate());
                    }
                    return;
                }
                else if (option.equalsIgnoreCase("NO"))
                {
                    return;
                }
            }
        }
        else
        {
            for(int i = 0; i < quantity; i += 1)
            {
                Stock s = p.getStockList().poll();

                System.out.println("Retirado >" + p.getName() + "< del " + s.getDate());
            }
            
            System.out.println("Ahora quedan " + p.getStockList().size() + " en stock!");
            return;
        }
        
    }
    
    static int GetInt(String prompt)
    {
        System.out.println(prompt);
        while (true)
        {
            try {
                int i = Integer.valueOf(input.nextLine());
                return i;
            }
            catch (Exception e)
            {
                System.err.println("No se pudo leer un entero!");
            }
        }
    }
}
