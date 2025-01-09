package com.org.mfm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.mfm.dto.InvestmentResponse;
import com.org.mfm.dto.LiabilityResponse;
import com.org.mfm.dto.PortfolioRequest;
import com.org.mfm.dto.PortfolioResponse;
import com.org.mfm.entity.Investment;
import com.org.mfm.entity.Liability;
import com.org.mfm.entity.PortFolio;
import com.org.mfm.enums.InvestmentType;
import com.org.mfm.service.PortfolioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/portfolio")
@Tag(name = "Portfolio", description = "Controller for Portfolio Generation, Deletion and Fetch")
public class PortfolioController {

	private PortfolioService folioService;

	PortfolioController(PortfolioService folioService) {
		this.folioService = folioService;
	}

	@Operation(description = "API to generate/Create new Portfolio", summary = "Generate/Create new Portfolio", responses = {
			@ApiResponse(description = "Success", responseCode = "200"),
			@ApiResponse(description = "Unauthorized / Invalid Token", responseCode = "403") })
	@PostMapping("/generate")
	public ResponseEntity<PortFolio> createPortfolio(PortfolioRequest folioRequest) {
		return ResponseEntity.ok(this.folioService.createPortfolio(folioRequest));
	}

	@Operation(description = "API to delete the Portfolio", summary = "Delete Portfolio details", responses = {
			@ApiResponse(description = "Success", responseCode = "200"),
			@ApiResponse(description = "Not found", responseCode = "404"),
			@ApiResponse(description = "Unauthorized / Invalid Token", responseCode = "403") })
	@DeleteMapping("/delete/{folioNumber}")
	public void deleteTransaction(@PathVariable("folioNumber") int folioNumber) {
		this.folioService.deletePortFolio(folioNumber);
	}

	@Operation(description = "API to fetch Portfolio details", summary = "Get Portfolio Details", responses = {
			@ApiResponse(description = "Success", responseCode = "200"),
			@ApiResponse(description = "Not found", responseCode = "404"),
			@ApiResponse(description = "Unauthorized / Invalid Token", responseCode = "403") })
	@GetMapping("/fetch/{folioNumber}")
	public ResponseEntity<PortfolioResponse> loadPortfolio(@PathVariable("folioNumber") int folioNumber) {
		PortFolio folio = this.folioService.getPortFolio(folioNumber);
		double networth = 0;
		List<Investment> investments = folio.getInvestments();

		double totalInvestmentvalue = investments.stream()
				.collect(Collectors.summingDouble(Investment::getCurrentValue));

		Map<InvestmentType, List<Investment>> byInvestmentType = investments.stream()
				.collect(Collectors.groupingBy(Investment::getInvestmentType));
		List<InvestmentResponse> inves = new ArrayList<>();
		byInvestmentType.forEach((key, val) -> {
			Map<InvestmentType, Double> totalCurrentValue = val.stream().collect(Collectors
					.groupingBy(Investment::getInvestmentType, Collectors.summingDouble(Investment::getCurrentValue)));

			Map<InvestmentType, Double> totalByProfit = val.stream().collect(Collectors
					.groupingBy(Investment::getInvestmentType, Collectors.summingDouble(Investment::getNetProfit)));

			Map<InvestmentType, Double> totalByInvestmentValue = val.stream().collect(Collectors.groupingBy(
					Investment::getInvestmentType, Collectors.summingDouble(Investment::getInvestmentValue)));

			Map<InvestmentType, Double> totalByDayGain = val.stream().collect(Collectors
					.groupingBy(Investment::getInvestmentType, Collectors.summingDouble(Investment::getDayGain)));

			double totalValue = totalCurrentValue.get(key);
			double totalProfit = totalByProfit.get(key);
			double totalInvestmentValue = totalByInvestmentValue.get(key);
			double dayGain = totalByDayGain.get(key);
			folio.setTotalGain(folio.getTotalGain() + totalProfit);
			double investmentPercentage = (totalValue * 100d) / totalInvestmentvalue;
			InvestmentResponse rr = new InvestmentResponse(key, totalInvestmentValue, dayGain, totalValue, totalProfit,
					folioNumber, investmentPercentage);
			inves.add(rr);

		});

		List<Liability> liabilities = folio.getLiabilities();

		double totalLiabilitiesvalue = liabilities.stream()
				.collect(Collectors.summingDouble(Liability::getCurrentValue));

		// Map<LiabilityType, List<Liability>> byLiabilityType =
		// liabilities.stream().collect(Collectors.groupingBy(Liability::getLiabilityType));
		List<LiabilityResponse> libs = new ArrayList<>();
		byInvestmentType.forEach((key, val) -> {
			Map<InvestmentType, Double> totalCurrentValue = val.stream().collect(Collectors
					.groupingBy(Investment::getInvestmentType, Collectors.summingDouble(Investment::getCurrentValue)));

			Map<InvestmentType, Double> totalByProfit = val.stream().collect(Collectors
					.groupingBy(Investment::getInvestmentType, Collectors.summingDouble(Investment::getNetProfit)));

			Map<InvestmentType, Double> totalByInvestmentValue = val.stream().collect(Collectors.groupingBy(
					Investment::getInvestmentType, Collectors.summingDouble(Investment::getInvestmentValue)));

			Map<InvestmentType, Double> totalByDayGain = val.stream().collect(Collectors
					.groupingBy(Investment::getInvestmentType, Collectors.summingDouble(Investment::getDayGain)));

			double totalValue = totalCurrentValue.get(key);
			double totalProfit = totalByProfit.get(key);
			double totalInvestmentValue = totalByInvestmentValue.get(key);
			double dayGain = totalByDayGain.get(key);
			folio.setTotalGain(folio.getTotalGain() + totalProfit);
			double investmentPercentage = (totalValue * 100d) / totalInvestmentvalue;
			LiabilityResponse rr = new LiabilityResponse(key, totalInvestmentValue, dayGain, totalValue, totalProfit,
					folioNumber, investmentPercentage);
			libs.add(rr);

		});
		networth = totalInvestmentvalue - totalLiabilitiesvalue;
		PortfolioResponse respo = new PortfolioResponse(folioNumber, folio.getTittle(), networth, folio.getTotalGain(),
				inves, libs);

		return ResponseEntity.ok(respo);

	}

}