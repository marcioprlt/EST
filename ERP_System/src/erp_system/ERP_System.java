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
    public static Node rootNode;
    
    public static void main(String[] args) {
        
        input = new Scanner(System.in);
        
        CargarDatos();
        
        rootNode = CrearNodo(0);
        
        Menu();
    }
    
    static void CargarDatos()
    {
        //Cargar CSV
        CSVReader data;
        
        try {
            FileReader fr = new FileReader("Products.csv"); //<------ dirección del CSV
            data = new CSVReader(fr);
            System.out.println("Datos leidos!");
        }
        catch (Exception e) {
            System.err.println("Datos no fueron leidos!");
            System.exit(0);
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
            System.exit(0);
        }
        catch (Exception e)
        {
            System.err.println("No se leyeron los datos!");
            System.exit(0);
        }
    }
 
    static Node CrearNodo(int i)
    {
        if (i >= database.size()) return null;
            
        Node n = new Node(database.get(i));
        
        n.setLeft(CrearNodo(2*i+1));
        n.setRight(CrearNodo(2*i+2));
        
        return n;
    }
    
    static void Menu ()
    {
        String option;
        
        while (true)
        {
            System.out.println("1 – Buscar producto por código\n" +
                    "2 – Buscar producto por nombre\n"
                    + "3 - Retirar producto por código\n"
                    + "4 – Buscar por productos en árbol binaria\n"
                    + "5 - Salir");
            
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
                    BuscarPorArbolBinaria();
                    break;
                case "5":
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
        ImprimirDatos(database.get(id));
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
    
    static void BuscarPorArbolBinaria()
    {
        int id = GetInt("Ingresar ID a buscar:");
        
        if (BuscarEnNodo(rootNode, id))
        {
            
        }
        else
        {
            System.out.println("Producto no encontrado!");
        }
    }
    
    static boolean BuscarEnNodo(Node nodo, int id)
    {
        System.out.println("Buscando en Nodo " + nodo.getElement().getId());
        
        if (nodo.getElement().getId() == id)
        {
            ImprimirDatos(nodo.getElement());
            return true;
        }
        
        if (nodo.getLeft() != null)
            if (BuscarEnNodo(nodo.getLeft(), id)) return true;
        
        if (nodo.getRight() != null)
            if (BuscarEnNodo(nodo.getRight(), id)) return true;
        
        return false;
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
    
    static void ImprimirDatos(Product p)
    {
        System.out.printf("%-25s%d%n", "ID", p.getId());
        System.out.printf("%-25s%s%n", "Nombre", p.getName());
        System.out.printf("%-25s%.2f%n", "Precio", p.getPrice());
        System.out.printf("%-25s%d%n", "Cantidad en stock", p.getStockList().size());
    }
}
