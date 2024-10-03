package com.bigcompany;

import com.bigcompany.model.Employee;
import com.bigcompany.service.EmployeeAnalyzer;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeAnalyzerTest {

    @Test
    public void testAnalyzeSalaries() {
        Map<Integer, Employee> employees = new HashMap<>();
        Employee ceo = new Employee(123, "Joe", "Doe", 60000, null);
        Employee manager1 = new Employee(124, "Martin", "Chekov", 45000, 123);
        Employee manager2 = new Employee(125, "Bob", "Ronstad", 47000, 123);
        Employee employee1 = new Employee(300, "Alice", "Hasacat", 50000, 124);
        Employee employee2 = new Employee(305, "Brett", "Hardleaf", 34000, 300);

        ceo.addSubordinate(manager1);
        ceo.addSubordinate(manager2);
        manager1.addSubordinate(employee1);
        employee1.addSubordinate(employee2);

        employees.put(123, ceo);
        employees.put(124, manager1);
        employees.put(125, manager2);
        employees.put(300, employee1);
        employees.put(305, employee2);

        EmployeeAnalyzer analyzer = new EmployeeAnalyzer(employees);
        assertEquals("Martin Chekov is underpaid by $15000.0", analyzer.analyzeSalaries().get(0));
    }

    @Test
    public void testFindLongReportingLines() {
        Map<Integer, Employee> employees = new HashMap<>();
        Employee ceo = new Employee(123, "Joe", "Doe", 60000, null);
        Employee manager1 = new Employee(124, "Martin", "Chekov", 45000, 123);
        Employee manager2 = new Employee(125, "Bob", "Ronstad", 47000, 123);
        Employee employee1 = new Employee(300, "Alice", "Hasacat", 50000, 124);
        Employee employee2 = new Employee(305, "Brett", "Hardleaf", 34000, 300);

        ceo.addSubordinate(manager1);
        ceo.addSubordinate(manager2);
        manager1.addSubordinate(employee1);
        employee1.addSubordinate(employee2);

        employees.put(123, ceo);
        employees.put(124, manager1);
        employees.put(125, manager2);
        employees.put(300, employee1);
        employees.put(305, employee2);

        EmployeeAnalyzer analyzer = new EmployeeAnalyzer(employees);
        assertEquals(1, analyzer.findLongReportingLines().size());
    }
}
