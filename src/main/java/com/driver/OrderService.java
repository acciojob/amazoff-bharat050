package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderService {
    OrderRepository repository = new OrderRepository();
    public void addOrder(Order order){
        repository.addOrder(order);
    }

    public void addPartner(String partnerId) {
        DeliveryPartner partner = new DeliveryPartner(partnerId);
        repository.addPartner(partnerId, partner);
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        repository.addPair(orderId, partnerId);
    }

    public Order getOrderById(String orderId) {
        return repository.getOrderById(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return repository.getPartnerById(partnerId);
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        return repository.getOrderCountByPartnerId(partnerId);
    }

    public List<String> getOrderByPartnerId(String partnerId) {
        return repository.getOrderByPartnerId(partnerId);
    }

    public List<String> getAllOrders() {
        return repository.getAllOrders();
    }

    public Integer getCountOfUnassignedOrders() {
        return repository.getCountOfUnassignedOrders();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        return repository.getOrdersLeftAfterGivenTimeByPartnerId(time, partnerId);
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        return repository.getLastDeliveryTimeByPartnerId(partnerId);
    }

    public void deletePartnerById(String partnerId) {
        repository.deletePartnerById(partnerId);
    }

    public void deleteOrderById(String orderId) {
        repository.deleteOrderById(orderId);
    }
}
