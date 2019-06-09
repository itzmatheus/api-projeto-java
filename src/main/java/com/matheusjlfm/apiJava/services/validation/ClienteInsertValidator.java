package com.matheusjlfm.apiJava.services.validation;

import com.matheusjlfm.apiJava.domain.Cliente;
import com.matheusjlfm.apiJava.domain.enums.TipoCliente;
import com.matheusjlfm.apiJava.dto.ClienteNewDTO;
import com.matheusjlfm.apiJava.repositories.ClienteRepository;
import com.matheusjlfm.apiJava.resources.exceptions.FieldMessage;
import com.matheusjlfm.apiJava.services.validation.utils.BR;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

    @Autowired
    private ClienteRepository repo;

    @Override
    public void initialize(ClienteInsert ann) {
    }

    @Override
    public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        try{
            if (objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCode()) && !BR.isValidCPF(objDto.getCpfOuCnpj())) {
                list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
            }

            if (objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCode()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
                list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
            }

        }catch (NullPointerException e){
            list.add(new FieldMessage("tipo", "Campo tipo obrigatório!"));
        }

        Cliente aux = repo.findByEmail(objDto.getEmail());
        if (aux != null){
            list.add(new FieldMessage("email", "Email ja existente!"));
        }
        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}