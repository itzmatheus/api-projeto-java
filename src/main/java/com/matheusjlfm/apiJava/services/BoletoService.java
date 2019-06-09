package com.matheusjlfm.apiJava.services;

import com.matheusjlfm.apiJava.domain.PagamentoComBoleto;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class BoletoService {

    public void preencherPagamentoComBoleto(PagamentoComBoleto pgto, Date instateDoPedido){
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(instateDoPedido);
        calendario.add(Calendar.DAY_OF_MONTH, 3);
        pgto.setDataVencimento(calendario.getTime());
    }

}
