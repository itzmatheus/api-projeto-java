package com.matheusjlfm.apiJava.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class URL {

    public static String decodeParam(String s){

        try{
            return URLDecoder.decode(s, "UTF-8");

        }catch (UnsupportedEncodingException e){
            return "";
        }

    }

    /** Nivel Iniciante
     * Metodo para quebrar uma string que possui uma lista de inteiros separados por virgula
     * e retornar um List de Inteiros
     * @param "1,2,3"
     * @return [1,2,3]
     *
     * */
    public static List<Integer> decodeIntList(String s){
        String[] vet = s.split(",");
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < vet.length; i++) {
            list.add(Integer.parseInt(vet[i]));
        }
        return list;
    }

    /** Nivel Uma Linha
     * Metodo para quebrar uma string que possui uma lista de inteiros separados por virgula
     * e retornar um List de Inteiros
     * @param "1,2,3"
     * @return [1,2,3]
     *
     * */
    public static List<Integer> decodeIntListHard(String s){
        return Arrays.asList(s.split(",")).stream().map(x -> Integer.parseInt(x)).collect(Collectors.toList());
    }

}
