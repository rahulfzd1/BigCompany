package com.bigcompany.service;

import com.bigcompany.model.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EmployeeAnalyzer {
    private final Map<Integer, Employee> employees;

    public EmployeeAnalyzer(Map<Integer, Employee> employees) {
        this.employees = employees;
    }

    public List<String> analyzeSalaries() {
        List<String> results = new ArrayList<>();

        for (Employee manager : employees.values()) {
            if (manager.getSubordinates().isEmpty()) continue;

            double totalSalary = 0;
            int count = 0;
            for (Employee subordinate : manager.getSubordinates()) {
                totalSalary += subordinate.getSalary();
                count++;
            }
            double avgSalary = totalSalary / count;

            double minRequiredSalary = avgSalary * 1.2;
            double maxAllowedSalary = avgSalary * 1.5;

            if (manager.getSalary() < minRequiredSalary) {
                results.add(manager.getFirstName() + " " + manager.getLastName() +
                        " is underpaid by $" + String.format("%.1f", (minRequiredSalary - manager.getSalary())));
            } else if (manager.getSalary() > maxAllowedSalary) {
                results.add(manager.getFirstName() + " " + manager.getLastName() +
                        " is overpaid by $" + String.format("%.1f", (manager.getSalary() - maxAllowedSalary)));
            }
        }
        return results;
    }

    public List<String> findLongReportingLines() {
        List<String> results = new ArrayList<>();

        for (Employee employee : employees.values()) {
            int depth = getReportingDepth(employee);
            if (depth > 4) {
                results.add(employee.getFirstName() + " " + employee.getLastName() +
                        " has a reporting line of " + depth + " levels.");
            }
        }
        return results;
    }

    private int getReportingDepth(Employee employee) {
        if (employee.getManagerId() == null) {
            return 0;
        }
        return 1 + getReportingDepth(employees.get(employee.getManagerId()));
    }
}