
package acme.features.sponsor.dashboard;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.datatypes.Money;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.forms.SponsorDashboard;
import acme.roles.Sponsor;

@Service
public class SponsorDashboardShowService extends AbstractService<Sponsor, SponsorDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		int sponsorId = super.getRequest().getPrincipal().getActiveRoleId();

		SponsorDashboard dashboard;
		Integer numberOfInvoicesWithTaxLessEqualThan21;
		Integer numberOfSponsorshipsWithLink;

		Money averageSponsorshipsAmount;
		Money deviationSponsorshipsAmount;
		Money maximumSponsorshipsAmount;
		Money minimumSponsorshipsAmount;

		Money averageInvoicesQuantity;
		Money deviationInvoicesQuantity;
		Money maximumInvoicesQuantity;
		Money minimumInvoicesQuantity;

		Collection<Money> sponsorSponsorshipsAmount = this.repository.findSponsorshipsAmountBySponsorId(sponsorId);
		Collection<Money> sponsorInvoicesQuantity = this.repository.findInvoicesQuantityBySponsorId(sponsorId);

		numberOfInvoicesWithTaxLessEqualThan21 = this.repository.numberOfInvoicesWithTaxLessEqualThan21(sponsorId);
		numberOfSponsorshipsWithLink = this.repository.numberOfSponsorshipsWithLink(sponsorId);

		if (sponsorSponsorshipsAmount.size() > 0 && sponsorInvoicesQuantity.size() > 0 && numberOfInvoicesWithTaxLessEqualThan21 > 0 && numberOfSponsorshipsWithLink > 0) {

			averageSponsorshipsAmount = this.calculateAverage(sponsorSponsorshipsAmount);
			minimumSponsorshipsAmount = this.calculateMin(sponsorSponsorshipsAmount);
			maximumSponsorshipsAmount = this.calculateMax(sponsorSponsorshipsAmount);
			deviationSponsorshipsAmount = this.calculateStandardDeviation(sponsorSponsorshipsAmount);

			averageInvoicesQuantity = this.calculateAverage(sponsorInvoicesQuantity);
			minimumInvoicesQuantity = this.calculateMin(sponsorInvoicesQuantity);
			maximumInvoicesQuantity = this.calculateMax(sponsorInvoicesQuantity);
			deviationInvoicesQuantity = this.calculateStandardDeviation(sponsorInvoicesQuantity);

			dashboard = new SponsorDashboard();

			dashboard.setNumberOfInvoicesWithTaxLessEqualThan21(numberOfInvoicesWithTaxLessEqualThan21);
			dashboard.setNumberOfSponsorshipsWithLink(numberOfSponsorshipsWithLink);

			dashboard.setAverageSponsorshipsAmount(averageSponsorshipsAmount);
			dashboard.setDeviationSponsorshipsAmount(deviationSponsorshipsAmount);
			dashboard.setMaximumSponsorshipsAmount(maximumSponsorshipsAmount);
			dashboard.setMinimumSponsorshipsAmount(minimumSponsorshipsAmount);

			dashboard.setAverageInvoicesQuantity(averageInvoicesQuantity);
			dashboard.setDeviationInvoicesQuantity(deviationInvoicesQuantity);
			dashboard.setMaximumInvoicesQuantity(maximumInvoicesQuantity);
			dashboard.setMinimumInvoicesQuantity(minimumInvoicesQuantity);

			super.getBuffer().addData(dashboard);

		} else {

			dashboard = new SponsorDashboard();

			super.getBuffer().addData(dashboard);

		}

	}

	private Money calculateAverage(final Collection<Money> values) {
		Money averageMoney = new Money();

		double totalAmountInEuros = 0.0;
		int count = 0;

		for (Money money : values) {
			Money moneyInEuros = this.convertToEuros(money);
			totalAmountInEuros += moneyInEuros.getAmount();
			count++;
		}

		if (count > 0) {
			double averageAmountInEuros = totalAmountInEuros / count;
			averageMoney.setAmount(averageAmountInEuros);
			averageMoney.setCurrency("EUR");

		}

		return averageMoney;
	}

	private Money calculateMax(final Collection<Money> values) {
		Money maxMoney = null;

		for (Money money : values) {
			Money moneyInEuros = this.convertToEuros(money);

			if (maxMoney == null || moneyInEuros.getAmount() > maxMoney.getAmount())
				maxMoney = moneyInEuros;
		}

		return maxMoney;
	}

	private Money calculateMin(final Collection<Money> values) {
		Money minMoney = null;

		for (Money money : values) {
			Money moneyInEuros = this.convertToEuros(money);

			if (minMoney == null || moneyInEuros.getAmount() < minMoney.getAmount())
				minMoney = moneyInEuros;
		}

		return minMoney;
	}

	private Money calculateStandardDeviation(final Collection<Money> values) {
		Money averageMoney = this.calculateAverage(values);
		int count = values.size();

		// Calculate the sum of squared differences
		double sumOfSquaredDifferences = 0;
		for (Money money : values) {
			Money moneyInEuros = this.convertToEuros(money);
			double difference = moneyInEuros.getAmount() - averageMoney.getAmount();
			sumOfSquaredDifferences += Math.pow(difference, 2);
		}

		// Calculate the square root of the sum of squared differences divided by the count of values
		double standardDeviationAmount = Math.sqrt(sumOfSquaredDifferences / count);

		// Create a new Money object with the standard deviation amount and the same currency as the original values
		Money standardDeviationMoney = new Money();
		standardDeviationMoney.setAmount(standardDeviationAmount);
		standardDeviationMoney.setCurrency(averageMoney.getCurrency());

		return standardDeviationMoney;
	}

	//This method convert to Euros most relevant currencies supported by our application
	//Source: Refinitiv (18/04/2024)
	private Money convertToEuros(final Money money) {
		Double currentAmount = money.getAmount();
		String currentCurrency = money.getCurrency();

		if (!currentCurrency.equals("EUR")) {
			switch (currentCurrency) {
			case "USD":
				// Convert USD to EUR using a conversion rate of 0.94
				currentAmount *= 0.94;
				break;
			case "GBP":
				// Convert GBP to EUR using a conversion rate of 1.17
				currentAmount *= 1.17;
				break;
			case "AUD":
				// Convert AUD to EUR using a conversion rate of 0.60
				currentAmount *= 0.60;
				break;
			case "JPY":
				// Convert JPY to EUR using a conversion rate of 0.0061
				currentAmount *= 0.0061;
				break;
			case "CAD":
				// Convert CAD to EUR using a conversion rate of 0.68
				currentAmount *= 0.68;
				break;
			case "MXN":
				// Convert MXN to EUR using a conversion rate of 0.055
				currentAmount *= 0.055;
				break;
			case "CNY":
				// Convert CNY to EUR using a conversion rate of 0.13
				currentAmount *= 0.13;
				break;
			default:
				// No conversion available for the given currency
				return money;
			}
			Money convertedMoney = new Money();
			convertedMoney.setAmount(currentAmount);
			convertedMoney.setCurrency("EUR");

			return convertedMoney;
		}

		return money;
	}

	@Override
	public void unbind(final SponsorDashboard object) {
		Dataset dataset;

		dataset = super.unbind(object, "numberOfInvoicesWithTaxLessEqualThan21",//
			"numberOfSponsorshipsWithLink", "averageSponsorshipsAmount",//
			"deviationSponsorshipsAmount",//
			"maximumSponsorshipsAmount", "minimumSponsorshipsAmount", // 
			"averageInvoicesQuantity", "deviationInvoicesQuantity", "maximumInvoicesQuantity", "minimumInvoicesQuantity");

		super.getResponse().addData(dataset);
	}

}
