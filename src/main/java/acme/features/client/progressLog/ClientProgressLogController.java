
package acme.features.client.progressLog;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.contracts.ProgressLog;
import acme.roles.Client;

@Controller
public class ClientProgressLogController extends AbstractController<Client, ProgressLog> {

	// Internal state ---------------------------------------------------------

	@Autowired
	ClientProgressLogListService listService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
	}
}
