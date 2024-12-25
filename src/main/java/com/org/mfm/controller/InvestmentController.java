/*
 * package com.org.mfm.controller;
 *
 * import org.springframework.http.ResponseEntity; import
 * org.springframework.web.bind.annotation.DeleteMapping; import
 * org.springframework.web.bind.annotation.GetMapping; import
 * org.springframework.web.bind.annotation.PostMapping; import
 * org.springframework.web.bind.annotation.RequestMapping; import
 * org.springframework.web.bind.annotation.RestController;
 *
 * import com.org.mfm.dto.PortfolioRequest; import com.org.mfm.entity.PortFolio;
 * import com.org.mfm.service.InvestmentService; import
 * com.org.mfm.service.PortfolioService;
 *
 * import io.swagger.v3.oas.annotations.Operation; import
 * io.swagger.v3.oas.annotations.responses.ApiResponse; import
 * io.swagger.v3.oas.annotations.tags.Tag;
 *
 * @RestController
 *
 * @RequestMapping("/portfolio")
 *
 * @Tag(name = "Portfolio") public class InvestmentController {
 *
 * private InvestmentService invService;
 *
 * InvestmentController(InvestmentService invService) { this.invService =
 * invService; }
 *
 * @Operation(description = "Generate/Create new Portfolio", summary =
 * "Generate/Create new Portfolio", responses = {
 *
 * @ApiResponse(description = "Success", responseCode = "200"),
 *
 * @ApiResponse(description = "Unauthorized / Invalid Token", responseCode =
 * "403") })
 *
 * @PostMapping("/generate") public ResponseEntity<PortFolio>
 * createPortfolio(PortfolioRequest folioRequest) { return
 * ResponseEntity.ok(this.folioService.createPortfolio(folioRequest)); }
 *
 *
 * @Operation(description = "Get API for Portfolio Details", summary =
 * "Get API for Portfolio Details", responses = {
 *
 * @ApiResponse(description = "Success", responseCode = "200"),
 *
 * @ApiResponse(description = "Not found", responseCode = "404"),
 *
 * @ApiResponse(description = "Unauthorized / Invalid Token", responseCode =
 * "403") })
 *
 * @GetMapping("/fetch/{folioNumber}") public ResponseEntity<PortFolio>
 * loadPortfolio(int folioNumber) { PortFolio
 * folio=this.folioService.getPortFolio(folioNumber); folio. return
 * ResponseEntity.ok();
 *
 * }
 *
 *
 * @Operation(description = "Delete API for Portfolio", summary =
 * "Save API for Portfolio", responses = {
 *
 * @ApiResponse(description = "Success", responseCode = "200"),
 *
 * @ApiResponse(description = "Not found", responseCode = "404"),
 *
 * @ApiResponse(description = "Unauthorized / Invalid Token", responseCode =
 * "403") })
 *
 * @DeleteMapping("/delete/{folioNumber}") public void deleteTransaction(int
 * folioNumber) { this.folioService.deletePortFolio(folioNumber); }
 *
 * }
 */