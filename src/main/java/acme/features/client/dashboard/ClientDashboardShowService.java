
package acme.features.client.dashboard;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.datatypes.Money;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.forms.ClientDashboard;
import acme.roles.Client;

@Service
public class ClientDashboardShowService extends AbstractService<Client, ClientDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ClientDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {

		int clientId = super.getRequest().getPrincipal().getActiveRoleId();

		ClientDashboard dashboard;
		Integer totalProgressLogsLessThan25;
		Integer totalProgressLogsBetween25And50;
		Integer totalProgressLogsBetween50And75;
		Integer totalProgressLogsAbove75;

		Money averageContractBudget;
		Money deviationContractBudget;
		Money minContractBudget;
		Money maxContractBudget;

		Collection<Money> clientBudgets = this.repository.findBudgetsByClientId(clientId);

		totalProgressLogsLessThan25 = this.repository.totalProgressLogsLessThan25(clientId);
		totalProgressLogsBetween25And50 = this.repository.totalProgressLogsBetween25And50(clientId);
		totalProgressLogsBetween50And75 = this.repository.totalProgressLogsBetween50And75(clientId);
		totalProgressLogsAbove75 = this.repository.totalProgressLogsAbove75(clientId);

		averageContractBudget = this.calculateAverage(clientBudgets);
		minContractBudget = this.calculateMin(clientBudgets);
		maxContractBudget = this.calculateMax(clientBudgets);
		deviationContractBudget = this.calculateStandardDeviation(clientBudgets);

		dashboard = new ClientDashboard();

		dashboard.setTotalProgressLogsLessThan25(totalProgressLogsLessThan25);
		dashboard.setTotalProgressLogsBetween25And50(totalProgressLogsBetween25And50);
		dashboard.setTotalProgressLogsBetween50And75(totalProgressLogsBetween50And75);
		dashboard.setTotalProgressLogsAbove75(totalProgressLogsAbove75);

		dashboard.setAverageContractBudget(averageContractBudget.getAmount());
		dashboard.setDeviationContractBudget(deviationContractBudget.getAmount());
		dashboard.setMinContractBudget(minContractBudget.getAmount());
		dashboard.setMaxContractBudget(maxContractBudget.getAmount());

		super.getBuffer().addData(dashboard);
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
			money.setCurrency("EUR");
			money.setAmount(currentAmount);
		}

		return money;
	}

	@Override
	public void unbind(final ClientDashboard object) {
		Dataset dataset;

		dataset = super.unbind(object, "totalProgressLogsLessThan25",//
			"totalProgressLogsBetween25And50", "totalProgressLogsBetween50And75",//
			"totalProgressLogsAbove75",//
			"averageContractBudget", "minContractBudget", // 
			"deviationContractBudget", "maxContractBudget");

		super.getResponse().addData(dataset);
	}

}
