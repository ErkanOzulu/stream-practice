package com.cydeo.streampractice.practice;

import com.cydeo.streampractice.model.*;
import com.cydeo.streampractice.service.*;
import org.apache.logging.log4j.util.PropertySource;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class Practice {

    public static CountryService countryService;
    public static DepartmentService departmentService;
    public static EmployeeService employeeService;
    public static JobHistoryService jobHistoryService;
    public static JobService jobService;
    public static LocationService locationService;
    public static RegionService regionService;


    public Practice(CountryService countryService, DepartmentService departmentService,
                    EmployeeService employeeService, JobHistoryService jobHistoryService,
                    JobService jobService, LocationService locationService,
                    RegionService regionService) {

        Practice.countryService = countryService;
        Practice.departmentService = departmentService;
        Practice.employeeService = employeeService;
        Practice.jobHistoryService = jobHistoryService;
        Practice.jobService = jobService;
        Practice.locationService = locationService;
        Practice.regionService = regionService;

    }

    // You can use the services above for all the CRUD (create, read, update, delete) operations.
    // Above services have all the required methods.
    // Also, you can check all the methods in the ServiceImpl classes inside the service.impl package, they all have explanations.

    // Display all the employees
    public static List<Employee> getAllEmployees() {
        return employeeService.readAll();
    }

    // Display all the countries
    public static List<Country> getAllCountries() {

        return countryService.readAll();
    }

    // Display all the departments
    public static List<Department> getAllDepartments() {

        return departmentService.readAll();
    }

    // Display all the jobs
    public static List<Job> getAllJobs() {

        return jobService.readAll();
    }

    // Display all the locations
    public static List<Location> getAllLocations() {

        return locationService.readAll();
    }

    // Display all the regions
    public static List<Region> getAllRegions() {

        return regionService.readAll();
    }

    // Display all the job histories
    public static List<JobHistory> getAllJobHistories() {

        return jobHistoryService.readAll();
    }

    // Display all the employees' first names
    public static List<String> getAllEmployeesFirstName() {
//        return employeeService.readAll().stream()
//                .map(e -> e.getFirstName()).collect(Collectors.toList());
        return getAllEmployees().stream()
                .map(Employee::getFirstName)
                .collect(Collectors.toList());


    }

    // Display all the countries' names
    public static List<String> getAllCountryNames() {

        return countryService.readAll().stream()
                .map(e -> e.getCountryName()).collect(Collectors.toList());
    }

    // Display all the departments' managers' first names
    public static List<String> getAllDepartmentManagerFirstNames() {

        //        return getAllDepartments().stream()
//                .map(Department::getManager)
//                .map(Employee::getFirstName)
//                .collect(Collectors.toList());

        return departmentService.readAll().stream().map(d -> d.getManager().getFirstName())
                .collect(Collectors.toList());

    }

    // Display all the departments where manager name of the department is 'Steven'
    public static List<Department> getAllDepartmentsWhichManagerFirstNameIsSteven() {
        return departmentService.readAll().stream()
                .filter(d -> d.getManager().getFirstName().equals("Steven"))
                .collect(Collectors.toList());
    }

    // Display all the departments where postal code of the location of the department is '98199'
    public static List<Department> getAllDepartmentsWhereLocationPostalCodeIs98199() {
//        return departmentService.readAll().stream()
//                .filter(d -> d.getLocation().getPostalCode().equals("98199"))
//                .collect(Collectors.toList());
        return getAllDepartments().stream()
                .filter(d -> d.getLocation().getPostalCode().equals("98199"))
                .collect(Collectors.toList());

    }

    // Display the region of the IT department
    public static Region getRegionOfITDepartment() throws Exception {
//        return departmentService.readAll().stream()
//                .filter(d -> d.getDepartmentName().equals("IT"))
//                .map(d -> d.getLocation().getCountry().getRegion())
//                .findAny().get();

//        return getAllDepartments().stream()
//                .filter(d -> d.getDepartmentName().equals("IT"))
//                .findFirst().orElseThrow(() -> new Exception("Department not found!"))
//                .getLocation().getCountry().getRegion();


        return getAllDepartments().stream()
                .filter(d -> d.getDepartmentName().equals("IT"))
                .map(d -> d.getLocation().getCountry().getRegion())
                .findFirst().orElseThrow(() -> new Exception("Department not found!"));
    }

    // Display all the departments where the region of department is 'Europe'
    public static List<Department> getAllDepartmentsWhereRegionOfCountryIsEurope() {
        return departmentService.readAll().stream()
                .filter(d -> d.getLocation().getCountry().getRegion().getRegionName().equals("Europe"))
                .collect(Collectors.toList());
    }

    // Display if there is any employee with salary less than 1000. If there is none, the method should return true
    public static boolean checkIfThereIsNoSalaryLessThan1000() {

        //        return getAllEmployees().stream()
        //                .allMatch(employee -> employee.getSalary() >= 1000);

        //        return !getAllEmployees().stream()
        //                .anyMatch(employee -> employee.getSalary() < 1000);

        //        return getAllEmployees().stream()
        //                .filter(employee -> employee.getSalary() < 1000)
        //                .findAny().isEmpty();
        return employeeService.readAll().stream()
                .noneMatch(e -> e.getSalary() < 1000);

    }

    // Check if the salaries of all the employees in IT department are greater than 2000 (departmentName: IT)
    public static boolean checkIfThereIsAnySalaryGreaterThan2000InITDepartment() {
        return employeeService.readAll().stream()
                .filter(d -> d.getDepartment().equals("IT"))
                .allMatch(d -> d.getSalary() > 2000);
    }

    // Display all the employees whose salary is less than 5000
    public static List<Employee> getAllEmployeesWithLessSalaryThan5000() {

        return employeeService.readAll().stream()
                .filter(e -> e.getSalary() < 5000)
                .collect(Collectors.toList());
    }

    // Display all the employees whose salary is between 6000 and 7000
    public static List<Employee> getAllEmployeesSalaryBetween() {

        return employeeService.readAll().stream()
                .filter(e -> e.getSalary() > 6000 && e.getSalary() < 7000)
                .collect(Collectors.toList());
    }

    // Display the salary of the employee Grant Douglas (lastName: Grant, firstName: Douglas)
    public static Long getGrantDouglasSalary() throws Exception {

//        return getAllEmployees().stream()
//                .filter(employee -> employee.getFirstName().equals("Douglas"))
//                .filter(employee -> employee.getLastName().equals("Grant"))
//                .findFirst().orElseThrow(() -> new Exception("No employee found!"))
//                .getSalary();


        return employeeService.readAll().stream()
                .filter(e -> e.getFirstName().equals("Douglas") && e.getLastName().equals("Grant"))
                .map(e -> e.getSalary()).findAny().get();
    }

    // Display the maximum salary an employee gets
    public static Long getMaxSalary() throws Exception {
        return employeeService.readAll().stream()
                .map(e -> e.getSalary())
                .reduce(Long::max).get();

    }

    // Display the employee(s) who gets the maximum salary
    public static List<Employee> getMaxSalaryEmployee() {

        long maxSalary = employeeService.readAll().stream().map(e -> e.getSalary()).reduce(Long::max).get();
        return employeeService.readAll().stream()
                .filter(e -> e.getSalary() == maxSalary)
                .collect(Collectors.toList());

    }

    // Display the max salary employee's job
    public static Job getMaxSalaryEmployeeJob() throws Exception {

        long maxSalary = employeeService.readAll().stream().map(e -> e.getSalary()).reduce(Long::max).get();
        return employeeService.readAll().stream()
                .filter(e -> e.getSalary() == maxSalary)
                .map(e -> e.getJob()).findAny().get();

    }

    // Display the max salary in Americas Region
    public static Long getMaxSalaryInAmericasRegion() throws Exception {
        return employeeService.readAll().stream()
                .filter(e -> e.getDepartment().getLocation().getCountry().getRegion().getRegionName().equals("Americas"))
                .map(e -> e.getSalary()).reduce(Long::max).get();

        //        .map(Employee::getSalary)
        //        .max(Long::compareTo).orElse(Long.MAX_VALUE);

    }

    // Display the second maximum salary an employee gets
    public static Long getSecondMaxSalary() throws Exception {

        return employeeService.readAll().stream()
                .map(Employee::getSalary)
                .sorted(Comparator.reverseOrder())
                .skip(1)
                .findFirst().get();
        // .reduce(Long::max).get();
    }

    // Display the employee(s) who gets the second maximum salary
    public static List<Employee> getSecondMaxSalaryEmployee() {

        return employeeService.readAll().stream()
                .filter(e -> {
                    try {
                        return e.getSalary().equals(getSecondMaxSalary());
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }).collect(Collectors.toList());

    }

    // Display the minimum salary an employee gets
    public static Long getMinSalary() throws Exception {

        return employeeService.readAll().stream()
                .map(Employee::getSalary)
                .reduce(Long::min).get();
    }

    // Display the employee(s) who gets the minimum salary
    public static List<Employee> getMinSalaryEmployee() {

        return employeeService.readAll().stream()
                .filter(e -> {
                    try {
                        return e.getSalary().equals(getMinSalary());
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                })
                .collect(Collectors.toList());
    }

    // Display the second minimum salary an employee gets
    public static Long getSecondMinSalary() throws Exception {
        return employeeService.readAll().stream()
                .map(Employee::getSalary)
                .sorted()
                .skip(1)
                .findFirst().get();

    }

    // Display the employee(s) who gets the second minimum salary
    public static List<Employee> getSecondMinSalaryEmployee() {

        return employeeService.readAll().stream()
                .filter(e -> {
                    try {
                        return e.getSalary().equals(getSecondMinSalary());
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }).collect(Collectors.toList());
    }

    // Display the average salary of the employees
    public static Double getAverageSalary() {
        return employeeService.readAll().stream()
                .collect(Collectors.averagingDouble(Employee::getSalary));

    }

    // Display all the employees who are making more than average salary
    public static List<Employee> getAllEmployeesAboveAverage() {
        return employeeService.readAll().stream()
                .filter(e -> e.getSalary() > getAverageSalary())
                .collect(Collectors.toList());
    }

    // Display all the employees who are making less than average salary
    public static List<Employee> getAllEmployeesBelowAverage() {
        return employeeService.readAll().stream()
                .filter(e -> e.getSalary() < getAverageSalary())
                .collect(Collectors.toList());
    }

    // Display all the employees separated based on their department id number
    public static Map<Long, List<Employee>> getAllEmployeesForEachDepartment() {
        return employeeService.readAll().stream()
                .collect(Collectors.groupingBy(e -> e.getDepartment().getId()));
    }

    // Display the total number of the departments
    public static Long getTotalDepartmentsNumber() {
        return departmentService.readAll().stream()
                .map(d -> d.getId())
                .count();
    }

    // Display the employee whose first name is 'Alyssa' and manager's first name is 'Eleni' and department name is 'Sales'
    public static Employee getEmployeeWhoseFirstNameIsAlyssaAndManagersFirstNameIsEleniAndDepartmentNameIsSales() throws Exception {
        return employeeService.readAll().stream()
                .filter(e -> e.getFirstName().equals("Alyssa")
                        && e.getManager().getFirstName().equals("Eleni")
                        && e.getDepartment().getDepartmentName().equals("Sales"))
                .findFirst().get();
    }

    // Display all the job histories in ascending order by start date
    public static List<JobHistory> getAllJobHistoriesInAscendingOrder() {
        return jobHistoryService.readAll().stream()
                .sorted(Comparator.comparing(JobHistory::getStartDate))
                .collect(Collectors.toList());
    }

    // Display all the job histories in descending order by start date
    public static List<JobHistory> getAllJobHistoriesInDescendingOrder() {
        return jobHistoryService.readAll().stream()
                .sorted(Comparator.comparing(JobHistory::getStartDate).reversed())
                .collect(Collectors.toList());
    }

    // Display all the job histories where the start date is after 01.01.2005
    public static List<JobHistory> getAllJobHistoriesStartDateAfterFirstDayOfJanuary2005() {
        return jobHistoryService.readAll().stream()
                .filter(j -> j.getStartDate().isAfter(LocalDate.of(2005, 01, 01)))
                .collect(Collectors.toList());
    }

    // Display all the job histories where the end date is 31.12.2007 and the job title of job is 'Programmer'
    public static List<JobHistory> getAllJobHistoriesEndDateIsLastDayOfDecember2007AndJobTitleIsProgrammer() {
        return jobHistoryService.readAll().stream()
                .filter(j -> j.getEndDate().isEqual(LocalDate.of(2007, 12, 31))
                        && j.getJob().getJobTitle().equals("Programmer"))
                .collect(Collectors.toList());
    }

    // Display the employee whose job history start date is 01.01.2007 and job history end date is 31.12.2007 and department's name is 'Shipping'
    public static Employee getEmployeeOfJobHistoryWhoseStartDateIsFirstDayOfJanuary2007AndEndDateIsLastDayOfDecember2007AndDepartmentNameIsShipping() throws Exception {
        return jobHistoryService.readAll().stream()
                .filter(j -> j.getStartDate().isEqual(LocalDate.of(2007, 01, 01))
                        && j.getEndDate().isEqual(LocalDate.of(2007, 12, 31))
                        && j.getDepartment().getDepartmentName().equals("Shipping"))
                .map(j -> j.getEmployee())
                .findFirst().get();
    }

    // Display all the employees whose first name starts with 'A'
    public static List<Employee> getAllEmployeesFirstNameStartsWithA() {
        return employeeService.readAll().stream()
                .filter(e -> e.getFirstName().startsWith("A"))
                .collect(Collectors.toList());
    }

    // Display all the employees whose job id contains 'IT'
    public static List<Employee> getAllEmployeesJobIdContainsIT() {
        return employeeService.readAll().stream()
                .filter(e -> e.getJob().getId().contains("IT"))
                .collect(Collectors.toList());
    }

    // Display the number of employees whose job title is programmer and department name is 'IT'
    public static Long getNumberOfEmployeesWhoseJobTitleIsProgrammerAndDepartmentNameIsIT() {
        return employeeService.readAll().stream()
                .filter(e -> e.getJob().getJobTitle().equals("Programmer")
                        && e.getDepartment().getDepartmentName().equals("IT"))
                .count();

    }

    // Display all the employees whose department id is 50, 80, or 100
    public static List<Employee> getAllEmployeesDepartmentIdIs50or80or100() {
        return employeeService.readAll().stream()
                .filter(e -> e.getDepartment().getId().equals(50L)
                        || e.getDepartment().getId().equals(80L)
                        || e.getDepartment().getId().equals(100L))
                .collect(Collectors.toList());
    }

    // Display the initials of all the employees
    // Note: You can assume that there is no middle name
    public static List<String> getAllEmployeesInitials() {
        return employeeService.readAll().stream()
                .map(e -> e.getFirstName().charAt(0) + "" + e.getLastName().charAt(0))
                .collect(Collectors.toList());
    }

    // Display the full names of all the employees
    public static List<String> getAllEmployeesFullNames() {
        return employeeService.readAll().stream()
                .map(e -> e.getFirstName() + " " + e.getLastName())
                .collect(Collectors.toList());
    }

    // Display the length of the longest full name(s)
    public static Integer getLongestNameLength() throws Exception {
        return getAllEmployeesFullNames().stream()
                .map(n -> n.length())
                .reduce(Integer::max).get();
    }

    // Display the employee(s) with the longest full name(s)
    public static List<Employee> getLongestNamedEmployee() {
        return employeeService.readAll().stream()
                .filter(e -> {
                    try {
                        return (e.getFirstName() + " " + e.getLastName()).length() == getLongestNameLength();
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }).collect(Collectors.toList());
    }

    // Display all the employees whose department id is 90, 60, 100, 120, or 130
    public static List<Employee> getAllEmployeesDepartmentIdIs90or60or100or120or130() {
        return employeeService.readAll().stream()
                .filter(e -> e.getDepartment().getId().equals(90L)
                        || e.getDepartment().getId().equals(60L)
                        || e.getDepartment().getId().equals(100L)
                        || e.getDepartment().getId().equals(120L)
                        || e.getDepartment().getId().equals(130L))
                .collect(Collectors.toList());

//        return employeeService.readAll().stream()
//                .filter(employee -> new ArrayList<>(Arrays.asList(90l, 60l, 100l, 120l, 130l)).contains(employee.getDepartment().getId()))
//                .collect(Collectors.toList());
    }

    // Display all the employees whose department id is NOT 90, 60, 100, 120, or 130
    public static List<Employee> getAllEmployeesDepartmentIdIsNot90or60or100or120or130() {
        return employeeService.readAll().stream()
                .filter(e -> e.getDepartment().getId() != 90L
                        && e.getDepartment().getId() != 60L
                        && e.getDepartment().getId() != 100L
                        && e.getDepartment().getId() != 120L
                        && e.getDepartment().getId() != 130L)
                .collect(Collectors.toList());

//        return employeeService.readAll().stream()
//                .filter(employee -> !new ArrayList<>(Arrays.asList(90l, 60l, 100l, 120l, 130l)).contains(employee.getDepartment().getId()))
//                .collect(Collectors.toList());


    }

}
