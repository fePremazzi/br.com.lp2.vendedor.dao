package br.com.lp2.vendedor.dao.daos;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import br.com.lp2.vendedor.comum.Enums.TipoCargo;
import br.com.lp2.vendedor.comum.VO.Funcionario;
import br.com.lp2.vendedor.dao.repo.Repositorio;

public class FuncionarioDAO extends Repositorio {

	@Override
	public void Insere(Funcionario func) {
		String fileName = "funcionario_table.txt";

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));) {
			writer.write(montaRegistro(func));
			writer.newLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public Funcionario Seleciona(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Funcionario Seleciona(String username) {
		String fileName = "funcionario_table.txt";
		List<Funcionario> allFuncs = null;
		try {
			Path path = Paths.get(fileName);
			byte[] bytes = Files.readAllBytes(path);
			List<String> allLines = Files.readAllLines(path, StandardCharsets.UTF_8);
			for (String registro : allLines) {
				String[] conteudo = new String[5];
				conteudo = registro.split("\\|");
				if(conteudo[3].equals(username)) {
					if(conteudo[2].equals("GERENTE"))
						return new Funcionario(conteudo[1], Integer.parseInt(conteudo[0]), TipoCargo.GERENTE, conteudo[3], conteudo[4]);
					else if (conteudo[2] == "VENDEDOR")
						return new Funcionario(conteudo[1], Integer.parseInt(conteudo[0]), TipoCargo.VENDEDOR, conteudo[3], conteudo[4]);					
				}				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	private String montaRegistro(Funcionario f) {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append(f.getId());
		strBuilder.append("|");
		strBuilder.append(f.getNome());
		strBuilder.append("|");
		strBuilder.append(f.getCargo());
		strBuilder.append("|");
		strBuilder.append(f.getUsername());		
		strBuilder.append("|");
		strBuilder.append(f.getSenha());
		return strBuilder.toString();
	}

}
