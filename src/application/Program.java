package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import entities.Product;
import model.service.ProductService;

/*
 * Fazer um programa para ler os dados (nome, email e salário) de funcionários a
 * partir de um arquivo em formato .csv. Em seguida mostrar, em ordem
 * alfabética, o email dos funcionários cujo salário seja superior a um dado
 * valor fornecido pelo usuário. Mostrar também a soma dos salários dos
 * funcionários cujo nome começa com a letra 'M'.
 * 
 * Arquivo:
 * Maria,maria@gmail.com,3200.00 
 * Alex,alex@gmail.com,1900.00
 * Marco,marco@gmail.com,1700.00 
 * Bob,bob@gmail.com,3500.00
 * Anna,anna@gmail.com,2800.00
 */

// **c:\ws-eclipse\in.txt
public class Program {

	public static void main(String[] args) {

		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		System.out.print("Enter full file path: ");
		String path = sc.nextLine();

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {

			List<Product> list = new ArrayList<>();

			String line = br.readLine();
			while (line != null) {
				String[] fields = line.split(",");
				list.add(new Product(fields[0], fields[1], Double.parseDouble(fields[2])));
				line = br.readLine();
			}

			System.out.print("Enter salary: ");
			double salary = sc.nextDouble();

			Predicate<Product> pred = p -> p.getPrice() >= salary;

			System.out.printf("Email of people whose salary is more than %.2f:", salary);
			System.out.println();

			List<String> email = list.stream().filter(pred).map(p -> p.getEmail()).sorted().collect(Collectors.toList());

			email.forEach(System.out::println);

			ProductService ps = new ProductService();
			double sum = ps.filteredSum(list, p -> p.getName().charAt(0) == 'M');

			System.out.println("Sum of salary of people whose name starts with 'M': " + String.format("%.2f", sum));

		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}

		sc.close();
	}
}
