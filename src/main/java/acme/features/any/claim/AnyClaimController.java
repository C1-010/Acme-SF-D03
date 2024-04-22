
package acme.features.any.claim;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Any;
import acme.entities.group.Claim;

@Controller
public class AnyClaimController extends AbstractController<Any, Claim> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyClaimListAllService		listAllService;

	@Autowired
	private AnyClaimListRecentService	listRecentService;

	@Autowired
	private AnyClaimShowService			showService;

	@Autowired
	private AnyClaimCreateService		createService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addCustomCommand("list-all", "list", this.listAllService);
		super.addCustomCommand("list-recent", "list", this.listRecentService);
	}

}
