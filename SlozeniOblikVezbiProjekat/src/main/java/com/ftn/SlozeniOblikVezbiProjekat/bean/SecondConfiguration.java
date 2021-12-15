package com.ftn.SlozeniOblikVezbiProjekat.bean;

import java.util.HashMap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ftn.SlozeniOblikVezbiProjekat.bean.SecondConfiguration.ApplicationMemory;

@Configuration
public class SecondConfiguration {
	
	@Bean(name= {"memorijaAplikacije"}, 
			initMethod="init", destroyMethod="destroy")
	public ApplicationMemory getApplicationMemory() {
		return new ApplicationMemory();
	}
	
	public class ApplicationMemory extends HashMap {
		
		@Override
		public String toString() {
			return "ApplicationMemory"+this.hashCode();
		}
		
		public void init() {
			//inicijalizacija
			System.out.println("init method called");
		}
		
		public void destroy() {
			//brisanje
			System.out.println("destroy method called");
		}
	}

}
