package br.com.caelum.leilao.dominio;

import org.junit.Test;

public class LanceTest {

    @Test(expected = IllegalArgumentException.class)
    public void deveRecusarLancesComValorZero(){
        new Lance(new Usuario("Joao"), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deveRecusarLancesComValorNegativo(){
        new Lance(new Usuario("joao"), -1);
    }
}
