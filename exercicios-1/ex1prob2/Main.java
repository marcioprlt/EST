package ex1prob2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner input = new Scanner(System.in);
		int n;
		ArrayList<String> lista = new ArrayList<String>();
		ArrayList<String> listaB = new ArrayList<String>();
		
		//obtener número de alumnos entero
		while (true)
		{
			try {
				System.out.println("Ingrese número de alumnos:");
				String s = input.nextLine();
				n = Integer.parseInt(s);
				break;
			}
			catch (Exception e)
			{
				System.err.println("No se pudo leer número entero!");
			}
		}
		
		//rellenar lista
		for (int i = 0; i < n; i += 1)
		{
			System.out.println("Ingrese en formato 00 nombre 0.00");
			lista.add(input.nextLine());
		}
		
		//crear otra lista con la nota al principio para ordernar
		for (String v : lista)
		{
			String[] p = v.split(" ");
			listaB.add(p[2] + " " + p[0] + " " + p[1]);
		}
		
		Collections.sort(listaB, Collections.reverseOrder());
		
		//rearmar lista
		lista.clear();
		for (String v : listaB)
		{
			String[] p = v.split(" ");
			lista.add(p[1] + " " + p[2] + " " + p[0]);
		}
		
		//mostrar lista
		for (String v : lista)
		{
			System.out.println(v);
		}
		
		input.close();
	}

}
