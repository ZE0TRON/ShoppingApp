package com.babob.sporcantam.OrderHistory;
import com.babob.sporcantam.Customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path="/orderHistory")
public class OrderHistoryController {
    @Autowired
    private OrderHistoryRepository orderHistoryRepository;
    @Autowired
    private CustomerRepository customerRepository;

}

