package com.example.gateway.event;

import com.example.gateway.models.UserDelete;
import com.example.gateway.service.RevokedTokenService;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RabbitSubscriber {

    @Autowired
    RevokedTokenService revokedTokenService;

    @Bean
    public FanoutExchange userDeleteExchange() {
        return new FanoutExchange("user.delete", true, false);
    }

    @Bean
    public Queue userDeleteQueue(){
        return new Queue("gateway:user.delete",true);
    }

    @Bean
    public Binding userDeleteBinding(){
        return BindingBuilder.bind(userDeleteQueue()).to(userDeleteExchange());
    }



    @Bean
    public FanoutExchange userActivationExchange() {
        return new FanoutExchange("user.activation", true, false);
    }

    @Bean
    public Queue userActivationQueue(){
        return new Queue("gateway:user.activation",true);
    }

    @Bean
    public Binding userActivationBinding(){
        return BindingBuilder.bind(userActivationQueue()).to(userActivationExchange());
    }


    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @RabbitListener(queues = "gateway:user.delete")
    public void receiveUserDelete(UserDelete userDelete) {
        revokedTokenService.save(userDelete.revocationId);
    }

//    @RabbitListener(queues = "gateway:user.activation")
//    public void receiveUserActivation(UserDelete userDelete) {
//        revokedTokenService.save(userDelete.revocationId);
//    }
}
