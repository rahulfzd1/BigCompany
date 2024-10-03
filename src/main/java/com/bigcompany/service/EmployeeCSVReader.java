package com.bigcompany.service;

import com.bigcompany.model.Employee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EmployeeCSVReader {

    public static Map<Integer, Employee> readEmployeesFromCSV(String filePath) {
        Map<Integer, Employee> employees = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip the header line
                }

                String[] values = line.split(",");
                if (values.length < 4) {
                    continue; // Skip lines that do not have at least 4 columns
                }

                int id = Integer.parseInt(values[0].trim());
                String firstName = values[1].trim();
                String lastName = values[2].trim();
                double salary = Double.parseDouble(values[3].trim());
                Integer managerId = values.length > 4 && !values[4].trim().isEmpty() ? Integer.parseInt(values[4].trim()) : null;

                Employee employee = new Employee(id, firstName, lastName, salary, managerId);
                employees.put(id, employee);

                if (managerId != null) {
                    Employee manager = employees.get(managerId);
                    if (manager != null) {
                        manager.addSubordinate(employee);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return employees;
    }
}