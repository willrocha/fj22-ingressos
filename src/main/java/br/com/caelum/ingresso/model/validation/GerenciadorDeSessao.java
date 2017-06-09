package br.com.caelum.ingresso.model.validation;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import br.com.caelum.ingresso.model.Sessao;

public class GerenciadorDeSessao {

	private List<Sessao> sessoesDaSala;
	
	public GerenciadorDeSessao(List<Sessao> sessoesDaSala){
		this.sessoesDaSala = sessoesDaSala;
	}
	
	public boolean cabe(Sessao sessaoAtual){
		Stream<Sessao> stream = sessoesDaSala
				.stream();
		
		Stream<Boolean> booleanStream = stream
				.map(sessaoExistente -> horarioIsValido(sessaoExistente, sessaoAtual));
		
		Optional<Boolean> optionalCabe = booleanStream.reduce(Boolean::logicalAnd);
		
		/**
		 * 
		 *
		for(Sessao sessaoDoCinema : sessoesDaSala){
			if (!horarioIsValido (sessaoDoCinema, sessaoAtual)){
				return false;
			}
		}
		*/
		
		return optionalCabe.orElse(true);
	}

	private boolean horarioIsValido(Sessao sessaoExistente, Sessao sessaoAtual) {
		
		LocalTime horarioSessao = sessaoExistente.getHorario();
		LocalTime horarioAtual = sessaoAtual.getHorario();
		
		boolean ehAntes = horarioAtual.isBefore(horarioSessao);
		
		if(ehAntes){
			
			return horarioAtual.plusMinutes(sessaoAtual.getFilme().getDuracao().toMinutes())
					.isBefore(sessaoExistente.getHorario());
		}else{
			return sessaoExistente.getHorarioTermino().isBefore(horarioAtual);
		}
	}

}
