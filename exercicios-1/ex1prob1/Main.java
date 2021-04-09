package ex1prob1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner input = new Scanner(System.in);
		int n;
		HashMap<String, String> mapa = new HashMap<String, String>();
		ArrayList<String> aBuscar = new ArrayList<String>();
		
		//obtener número de entradas entero
		while (true)
		{
			try {
				System.out.println("Ingrese número de entradas:");
				String s = input.nextLine();
				n = Integer.parseInt(s);
				break;
			}
			catch (Exception e)
			{
				System.err.println("No se pudo leer número entero!");
			}
		}
		
		//obtener datos
		for (int i = 0; i < n; i += 1)
		{
			System.out.println("Nombre " + (i + 1) + ":");
			String nombre = input.nextLine();
			System.out.println("Teléfono " + (i + 1) + ":");
			String tel = input.nextLine();
			
			mapa.put(nombre, tel);
		}
		
		//nombres a buscar
		while (true)
		{
			System.out.println("Ingrese nombre a buscar o '0' para terminar:");
			String s = input.nextLine();
			
			if (s.equals("0")) break;
			
			aBuscar.add(s);
		}
		
		//buscar nombres
		for (String v : aBuscar)
		{
			String[] p = v.split(" ");
			
			String key = p[0] + " " + p[1];
			if (mapa.containsKey(key))
			{
				System.out.println(key + "=" + mapa.get(key));
				continue;
			}
			
			key = p[1] + " " + p[0];
			if (mapa.containsKey(key))
			{
				System.out.println(key + "=" + mapa.get(key));
				continue;
			}
			
			System.out.println("Não encontrado");
		}
		
		input.close();
	}

}
