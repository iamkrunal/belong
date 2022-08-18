package com.belong.app.controller;

import com.belong.app.exception.ErrorResponse;
import com.belong.app.exception.EntityNotFoundException;
import com.belong.app.model.PhoneNumber;
import com.belong.app.service.PhoneNumberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/v1/phone-numbers")
public class PhoneNumberController {

    @Autowired
    private PhoneNumberService phoneNumberService;

    @Operation(summary = "Get all the phone numbers", description = "Get all the phone numbers", tags = "Get")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return the list of phone numbers",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema=  @Schema(implementation = PhoneNumber.class)))}
            ),
            @ApiResponse(responseCode = "500", description = "Error occurred while processing request"),
    })
    @GetMapping()
    public ResponseEntity<List<PhoneNumber>> getAllPhoneNumbers() {
        return new ResponseEntity<>(phoneNumberService.getAllPhoneNumbers(), HttpStatus.OK);
    }

    @Operation(summary = "Activate a phone number", description = "Activate a phone number", tags = "Patch")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Activated successfully"),
            @ApiResponse(responseCode = "404", description = "Invalid phone number",
                    content = {@Content(mediaType = "application/json",
                            schema =  @Schema(implementation = ErrorResponse.class))}
            ),
            @ApiResponse(responseCode = "500", description = "Error occurred while processing request"),
    })
    @PatchMapping("/{phoneNumber}/activate")
    public ResponseEntity<Void>
        activatePhoneNumber(@PathVariable String phoneNumber)
            throws EntityNotFoundException {
        phoneNumberService.activatePhoneNumber(phoneNumber);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
