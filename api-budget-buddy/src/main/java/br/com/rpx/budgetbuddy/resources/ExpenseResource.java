package br.com.rpx.budgetbuddy.resources;

import br.com.rpx.budgetbuddy.dto.ExpenseCreateDTO;
import br.com.rpx.budgetbuddy.dto.ExpenseDTO;
import br.com.rpx.budgetbuddy.dto.ExpenseUpdateDTO;
import br.com.rpx.budgetbuddy.entities.Expense;
import br.com.rpx.budgetbuddy.services.ExpenseService;
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
@RequestMapping(value = "/expenses")
public class ExpenseResource {

    private ExpenseService expenseService;

    public ExpenseResource(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public ResponseEntity<List<ExpenseDTO>> getExpesses() {
        return ResponseEntity.ok(
                expenseService.getExpenses().stream().map(
                        expense -> new ExpenseDTO(expense)
                ).collect(Collectors.toList()));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ExpenseDTO> findById(@PathVariable final Long id) {
        final Expense obj = expenseService.findById(id);
        final ExpenseDTO expenseDTO = new ExpenseDTO(obj);
        return ResponseEntity.ok().body(expenseDTO);
    }

    @PostMapping
    public ResponseEntity<ExpenseDTO> createExpense(
            @RequestBody ExpenseCreateDTO expense
    ) {
        Expense obj = expenseService.create(expense);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        ExpenseDTO dto = new ExpenseDTO(obj);
        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping(value = "/totalExpenseAmount")
    public ResponseEntity<BigDecimal> getTotalExpenseAmount(
            @RequestParam(name = "dtStart") final LocalDate dtStart,
            @RequestParam(name = "dtStop") final LocalDate dtStop
    ) {
        BigDecimal value = expenseService.getTotalExpenseAmount(dtStart, dtStop);
        value = value == null ? BigDecimal.ZERO : value;
        return ResponseEntity.ok(value);
    }

    @GetMapping(value = "/paged")
    public ResponseEntity<Page<ExpenseDTO>> getAllExpenses(
            @RequestParam(value = "description", required = false) final String description,
            final Pageable pageable
    ) {
        Page<Expense> expenses = expenseService.findAll(description, pageable);
        Page<ExpenseDTO> expenseDTOS = expenses.map(obj -> new ExpenseDTO(obj));
        return ResponseEntity.ok(expenseDTOS);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id){
        expenseService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ExpenseDTO> update(
            @PathVariable final Long id,
            @RequestBody final ExpenseUpdateDTO obj
    ){
        final Expense response = expenseService.update(id, obj);
        final ExpenseDTO dto = new ExpenseDTO(response);
        return ResponseEntity.ok().body(dto);

    }

}
