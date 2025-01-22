package com.example;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import com.example.dao.UserRepository;
import com.example.entities.User;

@SpringBootApplication
public class BootJpaExampleApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(BootJpaExampleApplication.class, args);
		
		UserRepository userRepository = context.getBean(UserRepository.class);
		
		User user1 = new User();
		user1.setName("Swaraj");
		user1.setCity("BSR");
		user1.setStatus("Happy");
		
		User user2 = new User();
		user2.setName("Saroj");
		user2.setCity("BSR");
		user2.setStatus("Happy");
		
		User user3 = new User();
		user3.setName("Deepak");
		user3.setCity("BSR");
		user3.setStatus("Happy");
		
		//CREATE single user
		User user = userRepository.save(user1);
		System.out.println(user);
		
		//CREATE multiple(all) users
		List<User> users = List.of(user1, user2, user3);
		Iterable<User> results = userRepository.saveAll(users);
		results.forEach(u->{System.out.println(u);});
		
		
		
		//UPDATE
		Optional<User> optional = userRepository.findById(244);
		//READ by id	
		User userName = optional.get();	
		user.setName("Swaraj Mishra") ;
		User result = userRepository.save(userName);
		System.out.println(result);
		
		//READ ALL		
        Iterable<User> itr = userRepository.findAll();
        Iterator<User> iterator = itr.iterator();
        
        while(iterator.hasNext())
        {
        	User user5 = iterator.next();
        	System.out.println(user5);
        }
        itr.forEach(u->{System.out.println(u);});
        
        
        //DELETE
        userRepository.deleteById(294);//deleteAll();//delete(User entity);
        
        
        
        //Custom finder Method
        List<User> userss = userRepository.findByName("Swaraj");
        userss.forEach(u->{System.out.println(u);});
        
        List<User> usersss = userRepository.findByNameAndCity("Deepak", "BSR");
        usersss.forEach(u->{System.out.println(u);});
		
	}
	
	
	
	
	

}
