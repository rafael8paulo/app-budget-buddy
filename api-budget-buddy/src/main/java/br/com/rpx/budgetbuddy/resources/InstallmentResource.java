package br.com.rpx.budgetbuddy.resources;

import br.com.rpx.budgetbuddy.dto.ExpenseDTO;
import br.com.rpx.budgetbuddy.dto.InstallmentDTO;
import br.com.rpx.budgetbuddy.entities.Installment;
import br.com.rpx.budgetbuddy.services.InstallmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/installments")
public class InstallmentResource {

    private InstallmentService installmentService;

    public InstallmentResource(InstallmentService installmentService) {
        this.installmentService = installmentService;
    }

    @GetMapping
    public ResponseEntity<List<InstallmentDTO>> getByDate(
            @RequestParam(name = "dtStart") final LocalDate dtStart,
            @RequestParam(name = "dtStop") final LocalDate dtStop
    ) {
        List<Installment> installments = installmentService.getByDate(dtStart, dtStop);
        List<InstallmentDTO> installmentDTOs = installments.stream().map(
                obj -> new InstallmentDTO(obj)
        ).collect(Collectors.toList());
        return ResponseEntity.ok(installmentDTOs);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        installmentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}/paid")
    public ResponseEntity<InstallmentDTO> pay(@PathVariable Long id) {
        Installment obj = installmentService.pay(id);
        return ResponseEntity.ok().body(new InstallmentDTO(obj));
    }


}
