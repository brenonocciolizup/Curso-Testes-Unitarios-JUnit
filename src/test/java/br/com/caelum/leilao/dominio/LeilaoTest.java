package br.com.caelum.leilao.dominio;

import static org.junit.Assert.assertEquals;
import static br.com.caelum.leilao.matchers.LeilaoMatcher.temUmLance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

import br.com.caelum.leilao.builder.CriadorDeLeilao;
import org.junit.Test;

public class LeilaoTest {

    @Test
    public void deveReceberUmLance(){

        Leilao leilao = new CriadorDeLeilao().para("notebook").constroi();
        assertEquals(0, leilao.getLances().size());

        Lance lance = new Lance(new Usuario("steve"), 2000.0);
        leilao.propoe(lance);

        assertThat(leilao.getLances().size(), equalTo(1));
        assertThat(leilao, temUmLance(lance));
    }

    @Test
    public void deveReceberVariosLances(){

        Leilao leilao = new CriadorDeLeilao().para("notebook")
                        .lance(new Usuario("steve"), 2000.0)
                        .lance(new Usuario("bob"), 3000.0)
                        .lance(new Usuario("mark"), 4000.0)
                        .constroi();

        assertEquals(3, leilao.getLances().size());
        assertEquals(2000, leilao.getLances().get(0).getValor(), 0.00001);
        assertEquals(3000, leilao.getLances().get(1).getValor(), 0.00001);
        assertEquals(4000, leilao.getLances().get(2).getValor(), 0.00001);
    }

    @Test
    public void naoDeveAceitarDoisLancesSeguidosdoMesmoUsuario(){
        Usuario steve = new Usuario("steve");
        Leilao leilao = new CriadorDeLeilao().para("notebook")
                .lance(steve, 2000.0)
                .lance(steve, 3000.0)
                .constroi();


        assertEquals(1, leilao.getLances().size());
        assertEquals(2000.0, leilao.getLances().get(0).getValor(), 0.00001);
    }

    @Test
    public void naoDeveAceitarMaisQueCincoLancesDoMesmoUsuario(){
        Usuario steveJobs = new Usuario("Steve Jobs");
        Usuario billGates = new Usuario("Bill Gates");

        Leilao leilao = new CriadorDeLeilao().para("Macbook Pro 15")
                .lance(steveJobs, 2000)
                .lance(billGates, 3000)
                .lance(steveJobs, 4000)
                .lance(billGates, 5000)
                .lance(steveJobs, 6000)
                .lance(billGates, 7000)
                .lance(steveJobs, 8000)
                .lance(billGates, 9000)
                .lance(steveJobs, 10000)
                .lance(billGates, 11000)
                .lance(steveJobs, 12000)
                .constroi();

        assertEquals(10, leilao.getLances().size());
        int ultimo = leilao.getLances().size()-1;
        assertEquals(11000.0, leilao.getLances().get(ultimo).getValor(), 0.00001);
    }

    @Test
    public void deveDobrarOUltimoLanceDado() {
        Leilao leilao = new Leilao("Macbook Pro 15");
        Usuario steveJobs = new Usuario("Steve Jobs");
        Usuario billGates = new Usuario("Bill Gates");

        leilao.propoe(new Lance(steveJobs, 2000));
        leilao.propoe(new Lance(billGates, 3000));
        leilao.dobraLance(steveJobs);

        assertEquals(4000, leilao.getLances().get(2).getValor(), 0.00001);
    }

    @Test
    public void naoDeveDobrarCasoNaoHajaLanceAnterior() {
        Leilao leilao = new Leilao("Macbook Pro 15");
        Usuario steveJobs = new Usuario("Steve Jobs");

        leilao.dobraLance(steveJobs);

        assertEquals(0, leilao.getLances().size());
    }
}
