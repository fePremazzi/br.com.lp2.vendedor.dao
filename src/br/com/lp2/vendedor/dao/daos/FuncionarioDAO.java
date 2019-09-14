package br.com.lp2.vendedor.dao.daos;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.lp2.vendedor.comum.Enums.TipoCargo;
import br.com.lp2.vendedor.comum.VO.Cliente;
import br.com.lp2.vendedor.comum.VO.Funcionario;
import br.com.lp2.vendedor.dao.repo.Repositorio;

public class FuncionarioDAO extends Repositorio {

	public FuncionarioDAO() {
		tablePath = "funcionario_table.txt";
	}

	@Override
	public void Insere(Object obj) throws Exception {
		Funcionario func = ((Funcionario) obj);
		func.setId(getProximoId());

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(tablePath, true));) {
			writer.write(montaRegistro(func));
			writer.newLine();
		} catch (Exception e) {
//			e.printStackTrace();
		}

	}

	@Override
	public List<Object> SelecionaTodos() {
		List<Object> allFuncs = new ArrayList<Object>();

		try {
			List<String> allLines = getTextFromTable(tablePath);

			for (String linha : allLines) {
				String[] conteudo = linha.split("\\|");
				allFuncs.add(new Funcionario(Integer.parseInt(conteudo[0]), conteudo[1],
						TipoCargo.valueOf(conteudo[2].toUpperCase()), conteudo[3], conteudo[4]));

			}

			return allFuncs;

		} catch (Exception e) {
//			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Object Seleciona(int id) {
		try {
			List<String> allLines = getTextFromTable(tablePath);
			for (String linha : allLines) {
				String[] conteudo = new String[5];
				conteudo = linha.split("\\|");

				if (Integer.parseInt(conteudo[0]) == (id)) {
					return new Funcionario(Integer.parseInt(conteudo[0]), conteudo[1],
							TipoCargo.valueOf(conteudo[2].toUpperCase()), conteudo[3], conteudo[4]);
				}
			}

		} catch (Exception e) {
//			e.printStackTrace();
			return null;
		}
		return null;
	}

	public Funcionario Seleciona(String username) {

		try {
			List<String> allLines = getTextFromTable(tablePath);
			for (String registro : allLines) {
				String[] conteudo = new String[5];
				conteudo = registro.split("\\|");

				if (conteudo[3].equals(username)) {
					return new Funcionario(Integer.parseInt(conteudo[0]), conteudo[1],
							TipoCargo.valueOf(conteudo[2].toUpperCase()), conteudo[3], conteudo[4]);
				}
			}

		} catch (Exception e) {
//			e.printStackTrace();
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

	@Override
	public void Atualiza(Object objNovosDados, int id) {

		Funcionario fNovosDados = ((Funcionario) objNovosDados);

		Funcionario fBanco = (Funcionario) Seleciona(id);

		if (fBanco != null) {

			List<String> escreverDeVolta = new ArrayList<String>();

			try (BufferedWriter writer = new BufferedWriter(new FileWriter(tablePath, true));) {
				List<String> allLines = getTextFromTable(tablePath);
				for (String linha : allLines) {
					String[] conteudo = new String[5];
					conteudo = linha.split("\\|");
					Funcionario fLinha = null;
					if (!conteudo[0].equals("")) {

						fLinha = new Funcionario(Integer.parseInt(conteudo[0]), conteudo[1],
								TipoCargo.valueOf(conteudo[2].toUpperCase()), conteudo[3], conteudo[4]);

						if (fLinha.getId() == fBanco.getId()) {
							fNovosDados.setId(fBanco.getId());
							escreverDeVolta.add(montaRegistro(fNovosDados));
						} else {
							escreverDeVolta.add(montaRegistro(fLinha));
						}
					}
				}

				clearTheFile(tablePath);

				if (escreverDeVolta != null) {
					for (String string : escreverDeVolta) {
						writer.write(string);
						writer.newLine();
					}
				}
			} catch (Exception e) {
//				e.printStackTrace();
			}
		} else {
			System.out.println("Nao existe usuario");
		}
	}

}
