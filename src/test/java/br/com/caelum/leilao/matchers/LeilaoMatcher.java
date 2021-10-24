package br.com.caelum.leilao.matchers;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class LeilaoMatcher extends TypeSafeMatcher<Leilao> {

    private final Lance lance;

    public LeilaoMatcher(Lance lance){
        this.lance = lance;
    }

    @Override //retorna true se o campo existir e false se não
    protected boolean matchesSafely(Leilao leilao) {
        return leilao.getLances().contains(lance);
    }

    @Override //descrição do matcher
    public void describeTo(Description description) {
        description.appendText("leilao com lance " + lance.getValor());
    }

    //instancia nosso matcher nos testes
    public static Matcher<Leilao> temUmLance(Lance lance){
        return new LeilaoMatcher(lance);
    }
}
