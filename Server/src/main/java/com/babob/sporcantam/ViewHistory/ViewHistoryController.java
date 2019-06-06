package com.babob.sporcantam.ViewHistory;
import com.babob.sporcantam.Customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path="/viewHistory")
public class ViewHistoryController {
    @Autowired
    private ViewHistoryRepository viewHistoryRepository;
    @Autowired
    private CustomerRepository customerRepository;

}