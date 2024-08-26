package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.constants.AccountsConstants;
import com.eazybytes.accounts.dto.AccountsContactsInfoDto;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.dto.ErrorResponseDto;
import com.eazybytes.accounts.dto.ResponseDto;
import com.eazybytes.accounts.service.IAccountsService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
@Tag(name = "CRUD REST APIs for Account MS",
        description = "CRUD REST APIs in Sample Bank to CREATE,UPDATE,FETCH & DELETE account details")
public class AccountsController {

    private static final Logger logger = LoggerFactory.getLogger(AccountsController.class);


    private final IAccountsService iAccountsService;

    public  AccountsController(IAccountsService iAccountsService){
        this.iAccountsService = iAccountsService;
    }

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private Environment environment;

    @Autowired
    private AccountsContactsInfoDto accountsContactsInfoDto;


    @Operation(summary = "Create Account REST API",
            description = "REST API to create new Customer & Account inside Sample Bank")
    @ApiResponse(
            responseCode =  "201",
            description = "HTTP Status CREATED"
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {

        iAccountsService.createAccount(customerDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }

    @Operation(summary = "Fetch Account REST API",
            description = "REST API to fetch  Customer & Account inside Sample Bank")
    @ApiResponses({
            @ApiResponse(
                    responseCode =  "200",
                    description = "HTTP Status OK"
            )
            ,
            @ApiResponse(
                    responseCode =  "500",
                    description = "HTTP Status Internal Server Error ",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )}
    )
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccountDetails(@Valid @RequestParam
                                                           @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digit")
                                                           String mobileNumber) {
        CustomerDto customerDto = iAccountsService.fetchAccount(mobileNumber);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerDto);
    }

    @Operation(summary = "Update Account REST API",
            description = "REST API to Update  Customer & Account inside Sample Bank")
    @ApiResponses({
            @ApiResponse(
                    responseCode =  "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode =  "417",
                    description = "Expectation Failure"
            ),
            @ApiResponse(
                    responseCode =  "500",
                    description = "HTTP Status Internal Server Error ",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )}
    )
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto) {
        boolean isUpdated = iAccountsService.updateAccount(customerDto);
        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_UPDATE));
        }
    }

    @Operation(summary = "Delete Account REST API",
            description = "REST API to Delete  Account inside Sample Bank")
    @ApiResponses({
            @ApiResponse(
                    responseCode =  "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode =  "417",
                    description = "Expectation Failure"
            ),
            @ApiResponse(
                    responseCode =  "500",
                    description = "HTTP Status Internal Server Error ",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )}
    )
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccount(@RequestParam
                                                     @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digit")
                                                     String mobileNumber) {
        boolean isDeleted = iAccountsService.deleteAccount(mobileNumber);

        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE__417_DELETE));
        }

    }


    @Operation(summary = "Fetch Build API",
            description = "REST API to fetch build info")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),@ApiResponse(
            responseCode =  "500",
            description = "HTTP Status Internal Server Error ",
            content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
            )
    )
    })

    @Retry(name = "getBuildInfo",fallbackMethod = "getBuildInfoFallback")
    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInfo(){
        logger.debug("getBuildInfo() method invoked");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildVersion);

    }

    public ResponseEntity<String> getBuildInfoFallback(Throwable throwable){
        logger.debug("getBuildInfoFallback() method invoked");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("0.9");
    }

    @Operation(summary = "Fetch java version API",
            description = "REST API to fetch java version info")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),@ApiResponse(
            responseCode =  "500",
            description = "HTTP Status Internal Server Error ",
            content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
            )
    )
    })

    @RateLimiter(name="getJavaVersion",fallbackMethod = "getJavaVersionFallback")
    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(environment.getProperty("JAVA_HOME"));

    }

    public ResponseEntity<String> getJavaVersionFallback(Throwable throwable){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Java 17");

    }


    @Operation(summary = "Fetch contact info API",
            description = "REST API to fetch contact info")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),@ApiResponse(
            responseCode =  "500",
            description = "HTTP Status Internal Server Error ",
            content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
            )
    )
    })
    @GetMapping("/contact-info")
    public ResponseEntity<AccountsContactsInfoDto> getContactInfo(){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountsContactsInfoDto);

    }
}
