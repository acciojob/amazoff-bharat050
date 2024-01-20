package com.driver;

import org.springframework.stereotype.Repository;

import java.util.*;
@Repository
public class OrderRepository {
    Map<String, Order> orderDB = new HashMap<>();
    Map<String, DeliveryPartner> deliveryPartnerDB = new HashMap<>();
    Map<String, String> orderPartnerDB = new HashMap<>();

//    private static int numberOfOrders = 0;
    public void addOrder(Order order) {
        orderDB.put(order.getId(), order);
    }

    public void addPartner(String partnerId, DeliveryPartner partner) {
        deliveryPartnerDB.put(partnerId, partner);
    }

    public void addPair(String orderId, String partnerId) {
        orderPartnerDB.put(orderId, partnerId);
        deliveryPartnerDB.get(partnerId).setNumberOfOrders(getOrderByPartnerId(partnerId).size());
    }

    public Order getOrderById(String orderId) {
        for(String id : orderDB.keySet()){
            if(orderId.equals(orderDB.get(id).getId())) return orderDB.get(id);
        }
        return null;
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        for(String id : deliveryPartnerDB.keySet()){
            if(partnerId.equals(deliveryPartnerDB.get(id).getId())) return deliveryPartnerDB.get(id);
        }
        return null;
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        for(String id : deliveryPartnerDB.keySet()){
            if(partnerId.equals(deliveryPartnerDB.get(id).getId())) return deliveryPartnerDB.get(id).getNumberOfOrders();
        }
        return 0;
    }

    public List<String> getOrderByPartnerId(String partnerId) {
        List<String> orders = new ArrayList<>();
        for(String orderId : orderPartnerDB.keySet()){
            if(partnerId.equals(orderPartnerDB.get(orderId))) orders.add(orderId);
        }
        return orders;
    }

    public List<String> getAllOrders() {
        List<String> ans = new ArrayList<>();
        for(String s: orderDB.keySet()){
            ans.add(s);
        }
        return ans;
    }

    public Integer getCountOfUnassignedOrders() {
        int count =0;
        for(String id : orderDB.keySet()){
            if(!orderPartnerDB.containsKey(id)) count++;
        }
        return count;
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        int count =0;
        String[] t = time.split(":");
        int time1 = Integer.parseInt(t[0])*60 + Integer.parseInt(t[1]);
        List<String> orders = getOrderByPartnerId(partnerId);
        for(String order: orders){
            if(orderDB.get(order).getDeliveryTime() > time1) count++;
        }
        return count;
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        List<String> orders = getOrderByPartnerId(partnerId);
        List<Integer> time = new ArrayList<>();
        for(String order: orders){
            time.add(orderDB.get(order).getDeliveryTime());
        }
        Collections.sort(time);
        String ans ="";
        int hrs = time.get(time.size()-1)/60;
        int min = time.get(time.size()-1)%60;

        if(hrs<10) ans+= "0" + hrs;
        else ans+= hrs;
        ans+= ":";
        if(min<10) ans+= "0" + min;
        else ans+= min;

        return ans;
    }

    public void deletePartnerById(String partnerId) {
        List<String> orders = getOrderByPartnerId(partnerId);
        for(String order: orders){
            orderPartnerDB.remove(order);
        }
        deliveryPartnerDB.remove(partnerId);
    }

    public void deleteOrderById(String orderId) {
        DeliveryPartner partner = deliveryPartnerDB.get(orderPartnerDB.get(orderId));
        partner.setNumberOfOrders(partner.getNumberOfOrders()-1);
        orderDB.remove(orderId);
        orderPartnerDB.remove(orderId);
    }
}
