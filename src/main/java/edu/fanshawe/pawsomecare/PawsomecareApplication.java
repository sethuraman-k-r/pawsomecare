package edu.fanshawe.pawsomecare;

import edu.fanshawe.pawsomecare.model.OfferService;
import edu.fanshawe.pawsomecare.model.Role;
import edu.fanshawe.pawsomecare.model.Service;
import edu.fanshawe.pawsomecare.repository.RoleRepository;
import edu.fanshawe.pawsomecare.repository.ServiceRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class PawsomecareApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(PawsomecareApplication.class, args);

		RoleRepository roleRepository = context.getBean(RoleRepository.class);
		if(roleRepository.findAll().isEmpty()) {
			List<Role> roles = Arrays.asList(
					new Role(1, "CLIENT"),
					new Role(2, "ADMIN"),
					new Role(3, "VETERINARIAN"),
					new Role(4, "GROOMING")
			);
			roleRepository.saveAll(roles);
		}

		ServiceRepository serviceRepository = context.getBean(ServiceRepository.class);
		if(serviceRepository.findAll().isEmpty()) {
			List<Service> services = Arrays.asList(
					new Service(1, OfferService.CONSULTATION),
					new Service(2, OfferService.VACCINATION),
					new Service(3, OfferService.GROOMING),
					new Service(4, OfferService.CHECKUP)
			);
			serviceRepository.saveAll(services);
		}
	}

}
