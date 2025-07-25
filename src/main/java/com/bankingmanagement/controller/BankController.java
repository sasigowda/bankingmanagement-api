package com.bankingmanagement.controller;

import com.bankingmanagement.exception.BankDetailsNotFoundException;
import com.bankingmanagement.model.BankRequest;
import com.bankingmanagement.model.BankTO;
import com.bankingmanagement.service.BankService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api/v1/banks")
public class BankController {

    @Autowired
    private BankService bankService;

    // http://localhost:8080/api/v1/banks  GET
    @GetMapping
    public ResponseEntity<List<BankTO>> getAllBanks() throws BankDetailsNotFoundException {
        log.info("Fetching all banks");
        List<BankTO> bankTOS = bankService.getAllBanks();
        log.info("Successfully fetched all banks");

        return new ResponseEntity<>(bankTOS, HttpStatus.OK);
    }

    // http://localhost:8080/api/v1/banks/23  GET  @PathVariable @PathParam
    // http://localhost:8080/api/v1/banks?bankcode=23  GET @RequestParam

    @GetMapping("/{bankCode}")
    public ResponseEntity<BankTO> getBankByCode(@PathVariable("bankCode") int bankCode) throws BankDetailsNotFoundException {
        log.info("Fetching bank details for bank code: {}", bankCode);
        BankTO bankTO = bankService.getBankByCode(bankCode);
        log.info("Successfully fetched bank details for bank code: {}", bankCode);

        return new ResponseEntity<>(bankTO, HttpStatus.OK);
    }

    // http://localhost:8080/api/v1/banks/name?bankname=ICICI  GET

    @GetMapping("/name")
    public ResponseEntity<BankTO> getBankByName(@RequestParam("bankname") String bankName) throws BankDetailsNotFoundException {
        log.info("Fetching bank details for bank name: {}", bankName);
        BankTO bankTO = bankService.getBankByName(bankName);
        log.info("Successfully fetched bank details for bank name: {}", bankName);

        return new ResponseEntity<>(bankTO, HttpStatus.OK);
    }

    // http://localhost:8080/api/v1/banks  POST
    /**
     * Adds a new bank to the system.
     * @param bank
     * @return
     * @throws BankDetailsNotFoundException
     */
    @PostMapping
    public ResponseEntity<BankTO> addBank(@RequestBody @Valid BankRequest bank) throws BankDetailsNotFoundException {
        log.info("Adding new bank: {}", bank);
        BankTO addedBank = bankService.addBank(bank);
        log.info("Successfully added new bank: {}", addedBank);

        return new ResponseEntity<>(addedBank, HttpStatus.CREATED);
    }
}
