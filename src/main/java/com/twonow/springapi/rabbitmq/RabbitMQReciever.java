package com.twonow.springapi.rabbitmq;

import com.twonow.springapi.service.UserService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQReciever implements RabbitListenerConfigurer {

    @Autowired
    private UserService userService;

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {
    }
    @RabbitListener(queues = "${rabbitmq.queue}")
    public void receivedMessage(Message message) {
        JSONParser parser = new JSONParser();
        JSONObject json = null;
        try {
            json = (JSONObject) parser.parse(new String(message.getBody()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(json);
        if(json.get("test").equals("test"))
        {
            System.out.println("TEST COMPLETE");
            userService.testCall();
        }

    }
}