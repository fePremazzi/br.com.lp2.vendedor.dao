package br.com.lp2.vendedor.dao.daos;

import br.com.lp2.vendedor.comum.VO.Funcionario;

public class testes {

	public static void main(String[] args) {
		FuncionarioDAO fDAO = new FuncionarioDAO();
		Funcionario func = new Funcionario();
		func.setSenha("admin");
		func.setUsername("admin");
//		String s = "1|admin|GERENTE|admin|admin";
//		String[] sa = new String[5];
//		sa = s.split("a");
		
		
		System.out.println(fDAO.Seleciona(func.getUsername()));
//		System.out.println(sa.length);
	}

}
