package br.com.rpx.budgetbuddy.resources;

import br.com.rpx.budgetbuddy.dto.ExpenseDTO;
import br.com.rpx.budgetbuddy.dto.ExpenseUpdateDTO;
import br.com.rpx.budgetbuddy.dto.GainDTO;
import br.com.rpx.budgetbuddy.entities.Expense;
import br.com.rpx.budgetbuddy.entities.Gain;
import br.com.rpx.budgetbuddy.services.GainService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/earnings")
public class GainResource {

    private GainService service;

    public GainResource(GainService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<GainDTO>> list(
            @RequestParam(name = "dtStart", required = false) final LocalDate dtStart,
            @RequestParam(name = "dtStop", required = false) final LocalDate dtStop
    ) {
        List<Gain> gains = service.list(dtStart, dtStop);
        List<GainDTO> gainDTOS = gains.stream().map(obj -> new GainDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok(gainDTOS);
    }

    @GetMapping(value = "/totalWinnings")
    public ResponseEntity<BigDecimal> totalWinnings(
            @RequestParam(name = "dtStart") final LocalDate dtStart,
            @RequestParam(name = "dtStop") final LocalDate dtStop
    ) {
        final BigDecimal value = service.totalWinnings(dtStart, dtStop);
        return ResponseEntity.ok(value);
    }

    @PostMapping
    public ResponseEntity<GainDTO> create(@RequestBody GainDTO gainDTO) {
        Gain obj = service.create(gainDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(new GainDTO(obj));
    }

    @GetMapping(value = "/paged")
    public ResponseEntity<Page<GainDTO>> getAllExpenses(
            @RequestParam(value = "description", required = false) final String description,
            final Pageable pageable
    ) {
        final Page<Gain> earnings = service.findAll(description, pageable);
        final Page<GainDTO> gainDTOS = earnings.map(obj -> new GainDTO(obj));
        return ResponseEntity.ok(gainDTOS);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<GainDTO> findById(@PathVariable final Long id) {
        final Gain obj = service.findById(id);
        final GainDTO gainDTO = new GainDTO(obj);
        return ResponseEntity.ok().body(gainDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<GainDTO> update(
            @PathVariable final Long id,
            @RequestBody final GainDTO obj
    ){
        final Gain response = service.update(id, obj);
        final GainDTO dto = new GainDTO(response);
        return ResponseEntity.ok().body(dto);

    }

}
