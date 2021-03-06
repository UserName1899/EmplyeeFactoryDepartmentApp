
package factory.service;


import factory.model.Department;
import factory.repository.EmployeeRepository;
import factory.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;


  @Autowired

    public EmployeeService(EmployeeRepository employeeRepository) {

        this.employeeRepository = employeeRepository;
    }



    public Employee findEmployeeById(Long id) {

        return employeeRepository.findOne(id);
    }

    public Employee findByName(String name) {
        return employeeRepository.findByEmployeeName(name);
    }
    public Page<Employee> findAll(Pageable pageable)
    {
    Page<Employee> employeeList = employeeRepository.findAll(pageable);
        return employeeList;
    }

    public void deleteAllFactores() {
        employeeRepository.deleteAll();
    }
    public void deleteEmployeeById(Long id) {
        employeeRepository.delete(id);
    }

    public void saveEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    public void updateEmployee(Employee employee) {
        saveEmployee(employee);
    }

    public boolean isEmployeeExist(Employee employee){
        return findByName(employee.getEmployeeName()) !=null;
    }

    public List<Employee> findDepartmentEmployeeByID(Department departmentId) {
      return employeeRepository.findEmployeesByDepartmentId(departmentId);
    }
}
