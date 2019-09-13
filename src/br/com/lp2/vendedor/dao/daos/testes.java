package br.com.lp2.vendedor.dao.daos;

import java.sql.Timestamp;

import br.com.lp2.vendedor.comum.Enums.TipoCargo;
import br.com.lp2.vendedor.comum.VO.Funcionario;

public class testes {

	public static void main(String[] args) throws Exception {
//		FuncionarioDAO fDAO = new FuncionarioDAO();
//		Funcionario func = new Funcionario();
//		func.setSenha("master");
//		func.setUsername("master");
//		func.setCargo(TipoCargo.GERENTE);
//		func.setNome("fellipe_admin");
//		String s = "1|admin|GERENTE|admin|admin";
//		String[] sa = new String[5];
//		sa = s.split("a");
		
//		fDAO.Deleta(1);
//		System.out.println(fDAO.Seleciona(func.getUsername()).getNome());
//		fDAO.Insere(obj);
//		System.out.println(sa.length);
		
		String data = "22-05-1994  888888888";
		String[] t = data.split(" ")[0].split("-");
		System.out.println(t[0]);
		System.out.println(t[1]);
		System.out.println(t[2]);
		System.out.println(new Timestamp(System.currentTimeMillis()));
		
	}

}
