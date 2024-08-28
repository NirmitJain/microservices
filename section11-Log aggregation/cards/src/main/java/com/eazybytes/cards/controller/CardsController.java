package com.eazybytes.cards.controller;

import com.eazybytes.cards.constants.CardsConstant;
import com.eazybytes.cards.dto.CardsContactInfoDto;
import com.eazybytes.cards.dto.CardsDto;
import com.eazybytes.cards.dto.ErrorResponseDto;
import com.eazybytes.cards.dto.ResponseDto;
import com.eazybytes.cards.service.ICardsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
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

@Tag(
        name= "CRUD REST APIs for Cards in Sample bank",
        description = "CRUD REST APIs in Sample bank to CREATE,UPDATE, FETCH & DELETE card details"
)
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class CardsController {

    private static final Logger logger = LoggerFactory.getLogger(CardsController.class);

    private final ICardsService iCardsService;

    public CardsController(ICardsService iCardsService){
        this.iCardsService = iCardsService;
    }

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private Environment environment;

    @Autowired
    private CardsContactInfoDto cardsContactInfoDto;

    @Operation(
            summary = "Create Card REST API",
            description = "REST API to create new Card inside sample bank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status INTERNAL SERVER ERROR",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
            )
            )
        }
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createCard(@Valid @RequestParam
                    @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number must be 10 digit")
                    String mobileNumber){
        iCardsService.createCard(mobileNumber);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(CardsConstant.STATUS_201,CardsConstant.MESSAGE_201));

    }

    @Operation(
            summary = "Update Card REST API",
            description = "REST API to update Card inside sample bank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "HTTP Status EXPECTATION FAILED",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status INTERNAL SERVER ERROR",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
            )
            )
    })
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateCardDetails(@Valid @RequestBody CardsDto cardsDto){
        boolean isUpdated = iCardsService.updateCardsDetails(cardsDto);
        if(isUpdated)
            return  ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(CardsConstant.STATUS_200,CardsConstant.MESSAGE_200));
        else
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(CardsConstant.STATUS_417,CardsConstant.MESSAGE_417_UPDATE));
    }

    @Operation(
            summary = "FETCH Card details REST API",
            description = "REST API to fetch Card based on given mobile number inside sample bank"
    )
    @ApiResponses({
            @ApiResponse(
                responseCode = "200",
                description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status INTERNAL SERVER ERROR",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )


            )
    }
    )
    @GetMapping("/get")
    public ResponseEntity<CardsDto> fetchCardDetails(
            @RequestHeader("eazybank-correlation-id") String correlationId,
            @Valid @RequestParam @Pattern(regexp = "(^$|[0-9]{10})"
                    ,message = "Mobile number must be 10 digit") String mobileNumber){

        logger.debug("eazybank-Correlation-id found: {}", correlationId);

        CardsDto cardsDto = iCardsService.fetchCardDetails(mobileNumber);
         return ResponseEntity
                 .status(HttpStatus.OK)
                 .body(cardsDto);
    }

    @Operation(
            summary = "DELETE REST API",
            description = "REST API to delete Card based on given mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                responseCode = "200",
                description = "HTTP status OK"
            ),
            @ApiResponse(
                responseCode = "417",
                description = "HTTP Status EXPECTATION FAILED",
                content = @Content(
                        schema = @Schema(implementation = ErrorResponseDto.class)
                )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status INTERNAL SERVER ERROR",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteCard(@Valid @RequestParam
                                                  @Pattern(regexp = "(^$|[0-9]{10})",
                                                  message = "Mobile number must be 10 digit")
                                                  String mobileNumber){
        boolean isDeleted = iCardsService.deleteCard(mobileNumber);
        if(isDeleted)
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(CardsConstant.STATUS_200,CardsConstant.MESSAGE_200));
        else
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(CardsConstant.STATUS_417,CardsConstant.MESSAGE_417_DELETE));
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
    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInfo(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildVersion);

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
    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(environment.getProperty("JAVA_HOME"));

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
    public ResponseEntity<CardsContactInfoDto> getContactInfo(){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cardsContactInfoDto);

    }
}

