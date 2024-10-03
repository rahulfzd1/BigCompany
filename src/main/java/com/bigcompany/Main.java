package com.bigcompany;

import com.bigcompany.model.Employee;
import com.bigcompany.service.EmployeeAnalyzer;
import com.bigcompany.service.EmployeeCSVReader;


import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String filePath = "C:\\Users\\RSI302\\Downloads\\New folder\\myrepo\\BigCompany\\employees.csv";
        Map<Integer, Employee> employees = EmployeeCSVReader.readEmployeesFromCSV(filePath);
        EmployeeAnalyzer analyzer = new EmployeeAnalyzer(employees);

        System.out.println("Salary Analysis:");
        analyzer.analyzeSalaries().forEach(System.out::println);

        System.out.println("\nLong Reporting Lines:");
        analyzer.findLongReportingLines().forEach(System.out::println);
    }
}
