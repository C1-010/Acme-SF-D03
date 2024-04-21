
package acme.features.any.claim;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Any;
import acme.entities.group.Claim;

@Controller
public class AnyClaimController extends AbstractController<Any, Claim> {

	//Internal state ------------------------------------------

	@Autowired
	private AnyClaimListService		listService;

	@Autowired
	private AnyClaimShowService		showService;

	@Autowired
	private AnyClaimPublishService	publishService;

	//Constructors ---------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);

		super.addCustomCommand("publish", "update", this.publishService);

	}

}
