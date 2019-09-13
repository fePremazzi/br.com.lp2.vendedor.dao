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

public class ClienteDAO extends Repositorio {

	public ClienteDAO() {
		tablePath = "cliente_table.txt";
	}

	@Override
	public List<Object> SelecionaTodos() {

		List<Object> allClientes = new ArrayList<Object>();

		try {
			List<String> allLines = getTextFromTable(tablePath);

			for (String linha : allLines) {
				String[] conteudo = linha.split("\\|");
				allClientes.add(new Cliente(Integer.parseInt(conteudo[0]), conteudo[1],
						new SimpleDateFormat("dd/MM/yyyy").parse(conteudo[2]), conteudo[3], conteudo[4]));

			}
			
			return allClientes;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void Insere(Object obj) throws Exception {
		Cliente cliente = ((Cliente) obj);
		cliente.setId(getProximoId());

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(tablePath, true));) {
			writer.write(montaRegistro(cliente));
			writer.newLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * VO structure: private int id; (pai) private String nome; (pai) private Date
	 * dataNascimento; private String cpf; private String endereco;
	 * 
	 */

	@Override
	public Object Seleciona(int id) {
		try {
			List<String> allLines = getTextFromTable(tablePath);
			for (String linha : allLines) {
				String[] conteudo = linha.split("\\|");
//				conteudo = linha.split("\\|");

				if (Integer.parseInt(conteudo[0]) == id) {
					return new Cliente(Integer.parseInt(conteudo[0]), conteudo[1],
							new SimpleDateFormat("dd/MM/yyyy").parse(conteudo[2]), conteudo[3], conteudo[4]);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

//	@Override
//	public void Deleta(int id) {
//		List<String> escreverDeVolta = new ArrayList<String>();
//
//		try (BufferedWriter writer = new BufferedWriter(new FileWriter(tablePath, true));) {
//			List<String> allLines = getTextFromTable(tablePath);
//			for (String linha : allLines) {
//				String[] conteudo = linha.split("\\|");
////				conteudo = linha.split("\\|");
//
//				if (!(Integer.parseInt(conteudo[0]) == id)) {
//					escreverDeVolta.add(linha);
//				}
//			}
//
//			clearTheFile(tablePath);
//
//			if (escreverDeVolta != null) {
//				for (String string : escreverDeVolta) {
//					writer.write(string);
//					writer.newLine();
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//	}

	@Override
	public void Atualiza(Object objAtualizado, int id) {
		Cliente clNovosDados = ((Cliente) objAtualizado);

		Cliente clBanco = (Cliente) Seleciona(id);

		if (clBanco != null) {

			List<String> escreverDeVolta = new ArrayList<String>();

			try (BufferedWriter writer = new BufferedWriter(new FileWriter(tablePath, true));) {
				List<String> allLines = getTextFromTable(tablePath);
				for (String linha : allLines) {
					String[] conteudo = linha.split("\\|");
					linha.split("\\|");
//					conteudo = linha.split("\\|");

					Cliente clLinha = new Cliente(Integer.parseInt(conteudo[0]), conteudo[1],
							new SimpleDateFormat("dd/MM/yyyy").parse(conteudo[2]), conteudo[3], conteudo[4]);

					if (clLinha.getId() == clBanco.getId()) {
						escreverDeVolta.add(montaRegistro(clNovosDados));
					} else {
						escreverDeVolta.add(montaRegistro(clLinha));
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
				e.printStackTrace();
			}
		} else {
			System.out.println("Nao existe Cliente com o Id fornecido");
		}

	}

	private String montaRegistro(Cliente cliente) {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append(cliente.getId());
		strBuilder.append("|");
		strBuilder.append(cliente.getNome());
		strBuilder.append("|");
		strBuilder.append(cliente.getDataNascimento());
		strBuilder.append("|");
		strBuilder.append(cliente.getCpf());
		strBuilder.append("|");
		strBuilder.append(cliente.getEndereco());
		return strBuilder.toString();
	}

}
