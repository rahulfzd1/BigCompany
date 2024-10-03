package com.bigcompany;

import com.bigcompany.model.Employee;
import com.bigcompany.service.EmployeeAnalyzer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String csvFilePath = "employees.csv"; // Change to your actual CSV file path
        Map<Integer, Employee> employees = readEmployeesFromCSV(csvFilePath);

        EmployeeAnalyzer analyzer = new EmployeeAnalyzer(employees);

        List<String> salaryIssues = analyzer.analyzeSalaries();
        List<String> longLines = analyzer.findLongReportingLines();

        System.out.println("Managers earning less than they should or more than they should:");
        salaryIssues.forEach(System.out::println);

        System.out.println("\nEmployees with too long reporting lines:");
        longLines.forEach(System.out::println);
    }

    private static Map<Integer, Employee> readEmployeesFromCSV(String filePath) {
        Map<Integer, Employee> employees = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                int id = Integer.parseInt(fields[0]);
                String firstName = fields[1];
                String lastName = fields[2];
                double salary = Double.parseDouble(fields[3]);
                Integer managerId = fields[4].isEmpty() ? null : Integer.parseInt(fields[4]);

                Employee employee = new Employee(id, firstName, lastName, salary, managerId);
                employees.put(id, employee);

                if (managerId != null) {
                    employees.get(managerId).addSubordinate(employee);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return employees;
    }
}

