package com.cg.controller;


import com.cg.model.Customer;
import com.cg.model.Deposit;
import com.cg.model.Transfer;
import com.cg.service.customer.CustomerService;
import com.cg.service.deposit.DepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private DepositService depositService;

    @GetMapping
    public ModelAndView showListPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/customer/list");

        List<Customer> customers = customerService.findAll();
        modelAndView.addObject("customers", customers);

        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCreatePage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/customer/create");

        modelAndView.addObject("customer", new Customer());

        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditPage(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/customer/update");

        Optional<Customer> customer = customerService.findById(id);

        if (customer.isPresent()) {
            modelAndView.addObject("customer", customer);
        }
        else {
            modelAndView.addObject("customer", new Customer());
        }

        return modelAndView;
    }

    @GetMapping("/deposit/{id}")
    public ModelAndView showDepositPage(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/customer/deposit");

        Optional<Customer> customer = customerService.findById(id);

        if (customer.isPresent()) {
            modelAndView.addObject("customer", customer);
        }
        else {
            modelAndView.addObject("customer", new Customer());
        }

        modelAndView.addObject("deposit", new Deposit());

        return modelAndView;
    }

    @GetMapping("/transfer/{id}")
    public ModelAndView showTransferPage(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/customer/transfer");

        Optional<Customer> customer = customerService.findById(id);

        if (customer.isPresent()) {
            modelAndView.addObject("sender", customer.get());

            List<Customer> recipients = customerService.findAllByIdNot(customer.get().getId());

            modelAndView.addObject("recipients", recipients);
        }
        else {
            modelAndView.addObject("sender", new Customer());
            modelAndView.addObject("recipients", null);
        }

        modelAndView.addObject("transfer", new Transfer());

        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView doCreate(@ModelAttribute Customer customer) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/customer/create");

        try {
            customer.setBalance(BigDecimal.ZERO);
            customerService.save(customer);

            modelAndView.addObject("success", "New customer add success");
        } catch (Exception e) {
            e.printStackTrace();
            modelAndView.addObject("error", "Bad data");
        }

        modelAndView.addObject("customer", new Customer());

        return modelAndView;
    }

    @PostMapping("/edit/{id}")
    public ModelAndView doUpdate(@PathVariable Long id, @ModelAttribute Customer customer) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/customer/update");

        try {
            customer.setId(id);
            customerService.save(customer);

            modelAndView.addObject("customer", customer);
            modelAndView.addObject("success", "Update customer success");
        } catch (Exception e) {
            e.printStackTrace();
            modelAndView.addObject("error", "Bad data");
            modelAndView.addObject("customer", new Customer());
        }

        return modelAndView;
    }

    @PostMapping("/deposit/{id}")
    public ModelAndView doDeposit(@PathVariable Long id, @ModelAttribute Deposit deposit) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/customer/deposit");

        Optional<Customer> optionalCustomer = customerService.findById(id);

        if (optionalCustomer.isPresent()) {
            try {
                customerService.deposit(optionalCustomer.get(), deposit);

                modelAndView.addObject("customer", optionalCustomer.get());
                modelAndView.addObject("success", "Deposit success");
            } catch (Exception e) {
                e.printStackTrace();
                modelAndView.addObject("error", "Bad data");
                modelAndView.addObject("customer", new Customer());
            }
        }
        else {
            modelAndView.addObject("error", "Invalid Id");
            modelAndView.addObject("customer", new Customer());
        }

        modelAndView.addObject("deposit", new Deposit());

        return modelAndView;
    }

    @PostMapping("/transfer/{senderId}")
    public ModelAndView doTransfer(@PathVariable Long senderId, @ModelAttribute Transfer transfer) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/customer/transfer");

        Optional<Customer> senderOptional = customerService.findById(senderId);

        if (senderOptional.isPresent()) {

            Optional<Customer> recipientOptional = customerService.findById(transfer.getRecipient().getId());

            if (recipientOptional.isPresent()) {
                if (senderOptional.get().getId().equals(recipientOptional.get().getId())) {
                    modelAndView.addObject("error", "Invalid Sender and Recipient information");
                    modelAndView.addObject("sender", new Customer());
                }
                else {
                    BigDecimal currentBalance = senderOptional.get().getBalance();
                    BigDecimal transferAmount = transfer.getTransferAmount();
                    int fees = 10;
                    BigDecimal feesAmount = transferAmount.multiply(BigDecimal.valueOf(fees)).divide(BigDecimal.valueOf(100));
                    BigDecimal transactionAmount = transferAmount.add(feesAmount);

                    if (currentBalance.compareTo(transactionAmount) >= 0) {
                        transfer.setId(0l);
                        transfer.setSender(senderOptional.get());
                        transfer.setFees(10);
                        transfer.setFeesAmount(feesAmount);
                        transfer.setTransactionAmount(transactionAmount);

                        customerService.doTransfer(transfer);

                        Optional<Customer> sender = customerService.findById(senderId);

                        modelAndView.addObject("sender", sender.get());

                        List<Customer> recipients = customerService.findAllByIdNot(sender.get().getId());

                        modelAndView.addObject("recipients", recipients);
                    }
                    else {
                        modelAndView.addObject("error", "Sender balance not enough to transfer transaction");
                        modelAndView.addObject("sender", new Customer());
                    }

                }
            }
            else {
                modelAndView.addObject("error", "Invalid Recipient information");
                modelAndView.addObject("sender", new Customer());
            }
        }
        else {
            modelAndView.addObject("error", "Invalid Sender information");
            modelAndView.addObject("customer", new Customer());
        }

        modelAndView.addObject("transfer", new Transfer());

        return modelAndView;
    }

}
