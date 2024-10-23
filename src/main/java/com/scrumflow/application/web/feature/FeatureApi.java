package com.scrumflow.application.web.feature;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.scrumflow.application.dto.filter.FeatureRequestFilterDTO;
import com.scrumflow.application.dto.request.FeatureRequestDTO;
import com.scrumflow.application.dto.response.FeatureResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Feature", description = "Operações de Funcionalidade")
@RequestMapping(value = "/api/v1/feature", produces = MediaType.APPLICATION_JSON_VALUE)
public interface FeatureApi {

    @Operation(description = "Realiza o cadastro de uma funcionalidade no sistema")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    FeatureResponseDTO createFeature(@Valid @RequestBody FeatureRequestDTO featureRequestDTO);

    @Operation(description = "Retorna uma lista com as funcionalidade cadastradas no sistema")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<FeatureResponseDTO> findAllFeatures(@ParameterObject FeatureRequestFilterDTO filter);

    @Operation(description = "Retorna uma funcionalidade cadastrada no sistema")
    @GetMapping("/{featureId}")
    @ResponseStatus(HttpStatus.OK)
    FeatureResponseDTO findFeatureById(@PathVariable Long featureId);

    @Operation(description = "Permite a edição de determinada funcionalidade")
    @PutMapping("/{featureId}")
    @ResponseStatus(HttpStatus.OK)
    void updateFeature(
            @PathVariable Long featureId, @Valid @RequestBody FeatureRequestDTO featureRequestDTO);

    @Operation(description = "Permite a inativação de determinada funcionalidade")
    @DeleteMapping("/{featureId}")
    @ResponseStatus(HttpStatus.OK)
    void deleteFeature(@PathVariable Long featureId);

    @Operation(
            description = "Retorna uma lista com as features que ainda não foram vinculadas em sprints")
    @GetMapping("/nosprint")
    @ResponseStatus(HttpStatus.OK)
    List<FeatureResponseDTO> findAllFeaturesWithoutSprints();
}
