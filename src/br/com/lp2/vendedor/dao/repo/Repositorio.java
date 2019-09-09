package br.com.lp2.vendedor.dao.repo;

import br.com.lp2.vendedor.comum.VO.Funcionario;

public abstract class Repositorio {
	
	public abstract void Insere(Funcionario func);
	public abstract Funcionario Seleciona(int id);
	public abstract Funcionario Seleciona(String username);	

}
