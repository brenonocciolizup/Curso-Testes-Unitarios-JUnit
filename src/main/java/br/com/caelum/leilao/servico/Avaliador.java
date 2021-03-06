package br.com.caelum.leilao.servico;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Avaliador {

    private double maiorLance = Double.NEGATIVE_INFINITY;
    private double menorLance = Double.POSITIVE_INFINITY;
    private List<Lance> maiores;

    public void avalia(Leilao leilao){

        if(leilao.getLances().size() == 0){
            throw new RuntimeException("Não é possível avaliar um leilão sem lances.");
        }

        for(Lance lance : leilao.getLances()){
            if(lance.getValor() > maiorLance) maiorLance = lance.getValor();

            if(lance.getValor() < menorLance) menorLance = lance.getValor();
        }

        maiores = new ArrayList<>(leilao.getLances());
        Collections.sort(maiores, (o1, o2) -> {
            if(o1.getValor() < o2.getValor()) return 1;
            if(o1.getValor() > o2.getValor()) return -1;
            return 0;
        });
        maiores = maiores.subList(0,maiores.size() > 3 ? 3 : maiores.size());
    }

    public double getMaiorLance() {
        return maiorLance;
    }

    public double getMenorLance() {
        return menorLance;
    }

    public List<Lance> getTresMaiores() {
        return maiores;
    }
}
