package br.com.caelum.leilao.servico;

import br.com.caelum.leilao.builder.CriadorDeLeilao;
import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class AvaliadorTest {

    private Avaliador leiloeiro;
    private Usuario joao;
    private Usuario jose;
    private Usuario maria;

    @Before
    public void setUp(){
        this.leiloeiro = new Avaliador();

        joao = new Usuario("joao");
        jose = new Usuario("jose");
        maria = new Usuario("maria");
    }

    @Test(expected = RuntimeException.class)
    public void naoDeveAvaliarLeilaoSemLances(){
            Leilao leilao = new CriadorDeLeilao().para("notebook").constroi();

            leiloeiro.avalia(leilao);
            Assert.fail();
    }

    @Test
    public  void deveEntenderLancesEmOrdemCrescente() {
        //1 Montar o cenário

        Leilao leilao = new CriadorDeLeilao().para("Videogame novo")
                        .lance(joao, 250.0)
                        .lance(jose, 300.0)
                        .lance(maria, 400.0)
                        .constroi();

        //2 Executar ação
        leiloeiro.avalia(leilao);

        //3 Validar resultados
        //double maiorEsperado = 400;
        //double menorEsperado = 250;

        //System.out.println(maiorEsperado == leiloeiro.getMaiorLance());
        //assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.00001);

        //System.out.println(menorEsperado == leiloeiro.getMenorLance());
        //assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.00001);
        assertThat(leiloeiro.getMenorLance(), equalTo(250.0));
        assertThat(leiloeiro.getMaiorLance(), equalTo(400.0));
    }

    @Test
    public void deveEntenderLancesEmOrdemDecrescente(){

        Leilao leilao = new CriadorDeLeilao().para("mesa")
                .lance(joao, 400)
                .lance(maria, 300)
                .lance(joao, 200)
                .lance(maria, 100)
                .constroi();

        leiloeiro.avalia(leilao);

        assertThat(leiloeiro.getMenorLance(), equalTo(100.0));
        assertThat(leiloeiro.getMaiorLance(), equalTo(400.0));
    }


    @Test
    public void deveEntenderLancesEmOrdemAleatoria(){

        Leilao leilao = new CriadorDeLeilao().para("mesa")
                        .lance(joao, 200)
                        .lance(maria, 450)
                        .lance(joao, 120)
                        .lance(maria, 700)
                        .lance(joao, 630)
                        .constroi();

        leiloeiro.avalia(leilao);

        List<Lance> maiores = leiloeiro.getTresMaiores();

        assertThat(leiloeiro.getMenorLance(), equalTo(120.0));
        assertThat(leiloeiro.getMaiorLance(), equalTo(700.0));
        assertThat(maiores.size(), equalTo(3));
    }

    @Test
    public void deveEntenderLeilaoComApenasUmLance(){

        Leilao leilao = new Leilao("quadro");

        leilao.propoe(new Lance(joao, 1000.0));

        leiloeiro.avalia(leilao);

        assertThat(leiloeiro.getMaiorLance(), equalTo(1000.0));
        assertThat(leiloeiro.getMenorLance(), equalTo(1000.0));
    }

    @Test
    public void deveEncontrarOsTresMaioresLances(){
        Leilao leilao = new CriadorDeLeilao()
                .para("Playstation 3 Novo")
                .lance(joao, 100)
                .lance(maria, 200)
                .lance(joao, 300)
                .lance(maria, 400)
                .constroi();

        leiloeiro.avalia(leilao);

        List<Lance> maiores = leiloeiro.getTresMaiores();
        assertEquals(3, maiores.size());
        assertThat(maiores, hasItems(
                new Lance(maria, 400.0),
                new Lance(joao, 300),
                new Lance(maria, 200)
        ));
    }

    @Test
    public void deveRetornarListaComDoisValores(){

        Leilao leilao = new CriadorDeLeilao().para("quadro")
                .lance(new Usuario("joao"), 100)
                .lance(new Usuario("maria"), 150)
                .constroi();

        leiloeiro.avalia(leilao);

        List<Lance> maiores = leiloeiro.getTresMaiores();

        assertEquals(2, maiores.size());
        assertThat(maiores.get(1).getValor(), equalTo(100.0));
        assertThat(maiores.get(0).getValor(), equalTo(150.0));
    }
}
