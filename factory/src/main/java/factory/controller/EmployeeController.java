
package factory.controller;

import factory.model.Department;
import factory.model.PageWrapper;
import factory.service.DepartmentService;
import factory.service.EmployeeService;
import factory.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

/*
    * ----------------------Create operation-----------------------------------------------
    * We first create our view to return our form
    * Then we create our employee and store it in our DB
    * */

    @GetMapping("create-employee")
    private ModelAndView createEmployeeView(Pageable pageable){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("employee/employeeForm");
        modelAndView.addObject("employee", new Employee());
        modelAndView.addObject("department", departmentService.findAll(pageable));
        return modelAndView;
    }

    @PostMapping("create-employee")
    private ModelAndView createEmployee(Employee employee, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        if(bindingResult.hasErrors()){

            modelAndView.setViewName("employee/employeeForm");
            modelAndView.addObject("employee", new Employee());

        }

        if (employee.getId() == null ) {

            employeeService.saveEmployee(employee);
        }else{
            employeeService.updateEmployee(employee);
        }
        modelAndView.setViewName("employee/employeeForm");
        return new ModelAndView("redirect:/employee/employees");

    }


/*
    * ----------------------Read Operation----------------------------------------
    * Get/list all employees stored in out system
    * Get a employee by it's ID
    * */


    @GetMapping("employees")
    public String listAllEmployees(Model model , Pageable pageable){

        PageWrapper<Employee> page = new PageWrapper<Employee>
                (employeeService.findAll(pageable), "employees");

        model.addAttribute("page",page);
        model.addAttribute("myEmployees", employeeService.findAll(pageable));
        return "employee/employeesView";
    }

    @GetMapping("employee/{id}")
    private String getEmployee(@PathVariable Long id, Model model){

        model.addAttribute("myEmployee", employeeService.findEmployeeById(id));
        return "employee/employeeView";
    }


/*
    * Update operation
    * */
@PostMapping("update")
public ModelAndView update(@RequestParam("id") Long id,
                           @RequestParam("employeeName") String name,
                           @RequestParam("department") Department department,
                           @RequestParam("employeeEmail") String email,
                           @RequestParam("employeeNo") String employeeNo){
Employee employee = employeeService.findEmployeeById(id);
employee.setEmployeeName(name);
employee.setDepartment(department);
employee.setEmployeeEmail(email);
employee.setEmployeeNo(employeeNo);
    employeeService.saveEmployee(employee);
    return new ModelAndView("redirect:/employee/employees");
}

    @GetMapping("edit/{id}")
    private ModelAndView edit(@PathVariable("id") Long id){
        ModelAndView model = new ModelAndView("employee/employeeUpdate");
        model.addObject("myEmployee", employeeService.findEmployeeById(id));
        model.addObject("department", departmentService.findDepartmentById(id));

        return model;
    }



/*
    * Delete Operation
    * */

    @PostMapping("delete/{id}")
    private String delete(@PathVariable Long id){

        employeeService.deleteEmployeeById(id);
        return id.toString();
    }
}

