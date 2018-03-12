package com.learning.reservation;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
public class ReservationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservationApplication.class, args);

	}

	@Bean
	CommandLineRunner  runner(ReservationRepository rr) {
		return  args-> {
			Arrays.asList("Harish, philip, John, Mohan, Chetan".split(","))
			.forEach(n -> rr.save(new Reservation((String)n)));
			rr.findAll().forEach(System.out::println);
			rr.findByName("Harish").forEach(System.out::println);
		};
	}

}

//@RestController
class ReservationRestController{

	@Autowired
	ReservationRepository reservationRepository;

	@RequestMapping("/reservations")
	Collection<Reservation> reservations()
	{
		return reservationRepository.findAll();
	}

}

@Controller
class ReservationMvcController {
	@Autowired
	ReservationRepository reservationRepository;

	@RequestMapping("/reservations.harish")
	String reservations(Model model)
	{
		model.addAttribute("reservations", reservationRepository.findAll());
		return "reservations";
	}

}

@RepositoryRestResource
interface ReservationRepository extends JpaRepository<Reservation, Long>
{
	Collection<Reservation> findByName (@Param ("r") String r);
}

@Entity
class Reservation {
	
	@GeneratedValue
	@Id
	private Long id;

	private String name;


	public Reservation() {

	}

	public Reservation(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Reservarion [id=" + id + ", name=" + name + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	

}
