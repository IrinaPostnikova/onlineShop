package com.example.onlineshop.controllers;

import com.example.onlineshop.entity.order.Order;
import com.example.onlineshop.entity.order.ProductInOrder;
import com.example.onlineshop.entity.product.Product;
import com.example.onlineshop.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


//@RequestMapping("/order") для того, чтобы повесить /ордер на все методы
@Controller
public class OrderController {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ConditionRepository conditionRepository;
    private final ProductInOrderRepository productInOrderRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository, ConditionRepository conditionRepository, ProductInOrderRepository productInOrderRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.conditionRepository = conditionRepository;
        this.productRepository = productRepository;
        this.productInOrderRepository = productInOrderRepository;
    }

    @GetMapping("/order")
    public String findAll(Model model) {
        List<Order> orders = orderRepository.findAll();
        model.addAttribute("orders", orders);
        return "orders"; // список заказов
    }

    @GetMapping("/order-create")   //создание заказа
    public String createOrderForm(Model model) {
        model.addAttribute("conditions", conditionRepository.findAll());
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("order", new Order());
        return "order-create";
    }

    @PostMapping("/order-create")
    public String createOrder(Order order) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");
        LocalDateTime dateTime = LocalDateTime.now();
        order.setDate(dateTime.format(formatter));
        orderRepository.save(order);
        return "redirect:/order";
    }

    @GetMapping("/order-delete/{orderId}")
    public String deleteOrder(@PathVariable("orderId") Long orderId) {
        orderRepository.deleteById(orderId);
        return "redirect:/order";
    }

    @GetMapping("/order-update/{orderId}")
    public String updateOrderForm(@PathVariable("orderId") Long orderId, Model model) {
        Order order = orderRepository.findById(orderId).orElseThrow(() ->
                new IllegalArgumentException("Invalid type ID" + orderId));
        model.addAttribute("order", order);
        model.addAttribute("conditions", conditionRepository.findAll());
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("products", productRepository.findAll());
        return "order-update";
    }

    @PostMapping("/order-update")
    public String updateOrder(@Valid Order order) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");
        LocalDateTime dateTime = LocalDateTime.now();
        order.setDate(dateTime.format(formatter));
        order.getProductInOrder().removeIf(p->(p.getProduct()==null));
        int amount = orderRepository.findById(order.getOrderId()).get().getProductInOrder().size();
        var productList = new ArrayList<ProductInOrder>();
        for(int i = 0 ; i < amount;i++ ) {
            var product = orderRepository.findById(order.getOrderId()).get().getProductInOrder().get(i);
            productList.add(product);
         }
        orderRepository.findById(order.getOrderId()).get().getProductInOrder().clear();
        orderRepository.save(orderRepository.findById(order.getOrderId()).get());
        productInOrderRepository.deleteAll(productList);
 //       productInOrderRepository.deleteAll(orderRepository.findById(order.getOrderId()).get().getProductInOrder());
        orderRepository.save(order);
        return "redirect:/order";
    }
    @RequestMapping(value="/order-update", params={"removeRow"})
    public String removeRow(
            Order order, HttpServletRequest req) {
        Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
        order.getProductInOrder().remove(rowId.intValue());
        return "order-update";
    }
}
