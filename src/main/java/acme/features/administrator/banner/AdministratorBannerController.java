
package acme.features.administrator.banner;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Administrator;
import acme.entities.group.Banner;

@Controller
public class AdministratorBannerController extends AbstractController<Administrator, Banner> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AdministratorBannerListAllService		listAllService;

	@Autowired
	private AdministratorBannerListRecentService	listRecentService;

	@Autowired
	private AdministratorBannerShowService			showService;

	@Autowired
	private AdministratorBannerCreateService		createService;

	@Autowired
	private AdministratorBannerUpdateService		updateService;

	@Autowired
	private AdministratorBannerDeleteService		deleteService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);
		super.addCustomCommand("list-all", "list", this.listAllService);
		super.addCustomCommand("list-recent", "list", this.listRecentService);
	}

}
