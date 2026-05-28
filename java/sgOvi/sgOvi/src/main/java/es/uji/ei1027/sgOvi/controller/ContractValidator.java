package es.uji.ei1027.sgOvi.controller;

import es.uji.ei1027.sgOvi.model.Contract;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ContractValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Contract.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Contract contract = (Contract) target;

        if (contract.getStartDate() == null) {
            errors.rejectValue("startDate", "obligatorio", "La fecha de inicio es obligatoria.");
        }
        if (contract.getEndDate() == null) {
            errors.rejectValue("endDate", "obligatorio", "La fecha de fin es obligatoria.");
        }

        if (contract.getStartDate() != null && contract.getEndDate() != null) {
            if (contract.getStartDate().isAfter(contract.getEndDate())) {
                errors.rejectValue("endDate", "fechaInvalida", "La fecha de fin debe ser posterior a la de inicio.");
            }
        }

        if (contract.getSalary() <= 0) {
            errors.rejectValue("salary", "invalido", "El salario debe ser mayor que 0.");
        }

        if (contract.getSchedule() == null || contract.getSchedule().trim().isEmpty()) {
            errors.rejectValue("schedule", "obligatorio", "El horario es obligatorio.");
        }

        if (contract.getIdSelection() == null || contract.getIdSelection().trim().isEmpty()) {
            errors.rejectValue("idSelection", "obligatorio", "Debe asociar este contrato a una selección.");
        } else if (contract.getIdSelection().length() != 9) {
            errors.rejectValue("idSelection", "longitud", "El ID de la selección debe tener 9 caracteres.");
        }
    }
}