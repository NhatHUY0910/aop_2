package com.practice_aop_2.controller;

import com.practice_aop_2.exception.DuplicateEmailException;
import com.practice_aop_2.model.Customer;
import com.practice_aop_2.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    @GetMapping
    public String customerList(Model model) {
        List<Customer> customerList = customerService.findAll();
        model.addAttribute("customerList", customerList);
        return "/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "/create";
    }

    @PostMapping("/save")
    public String save(Customer customer) throws DuplicateEmailException {
        customerService.save(customer);
        return "redirect:/customers";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        model.addAttribute("customer", customerService.findById(id));
        return "/update";
    }

    @PostMapping("/update")
    public String update(Customer customer) throws DuplicateEmailException {
        customerService.save(customer);
        return "redirect:/customers";
    }

    @GetMapping("/delete/{id}")
    public String deleteForm(@PathVariable Long id, Model model) {
        model.addAttribute("customer", customerService.findById(id));
        return "/delete";
    }

    @PostMapping("/delete")
    public String delete(Customer customer, RedirectAttributes redirectAttributes) {
        customerService.delete(customer.getId());
        redirectAttributes.addFlashAttribute("message", "Customer deleted successfully");
        return "redirect:/customers";
    }

    @GetMapping("/detail/{id}")
    public String detailForm(@PathVariable Long id, Model model) {
        model.addAttribute("customer", customerService.findById(id));
        return "/detail";
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ModelAndView showInputNotAcceptable() {
        return new ModelAndView("/inputs-not-acceptable");
    }
}
