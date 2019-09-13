package br.com.lp2.vendedor.dao.daos;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import br.com.lp2.vendedor.comum.Enums.TipoCargo;
import br.com.lp2.vendedor.comum.Enums.TipoProduto;
import br.com.lp2.vendedor.comum.VO.Funcionario;
import br.com.lp2.vendedor.comum.VO.Produto;
import br.com.lp2.vendedor.dao.repo.Repositorio;

public class ProdutoDAO extends Repositorio {

	public ProdutoDAO() {
		tablePath = "produto_table.txt";
	}

	/*
	 * id nome private String descricao; private double valorUnit; private
	 * TipoProduto tipoProduto;
	 */

	@Override
	public void Insere(Object obj) throws Exception {
		Produto produto = ((Produto) obj);
		produto.setId(getProximoId());

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(tablePath, true));) {
			writer.write(montaRegistro(produto));
			writer.newLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public List<Object> SelecionaTodos() {
		List<Object> allProdutos = new ArrayList<Object>();

		try {
			List<String> allLines = getTextFromTable(tablePath);

			for (String linha : allLines) {
				String[] conteudo = linha.split("\\|");
				allProdutos.add(new Produto(Integer.parseInt(conteudo[0]), conteudo[1], conteudo[2],
						Double.parseDouble(conteudo[3]), TipoProduto.valueOf(conteudo[4].toUpperCase())));

			}
			
			return allProdutos;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Object Seleciona(int id) {
		try {
			List<String> allLines = getTextFromTable(tablePath);
			for (String linha : allLines) {
				String[] conteudo = linha.split("\\|");

				if (Integer.parseInt(conteudo[0]) == (id)) {
					return new Produto(Integer.parseInt(conteudo[0]), conteudo[1], conteudo[2],
							Double.parseDouble(conteudo[3]), TipoProduto.valueOf(conteudo[4].toUpperCase()));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	@Override
	public void Atualiza(Object objAtualizado, int id) {
		Produto fNovosDados = ((Produto) objAtualizado);

		Produto pBanco = (Produto) Seleciona(id);//

		if (pBanco != null) {

			List<String> escreverDeVolta = new ArrayList<String>();

			try (BufferedWriter writer = new BufferedWriter(new FileWriter(tablePath, true));) {
				List<String> allLines = getTextFromTable(tablePath);
				for (String linha : allLines) {
					String[] conteudo = linha.split("\\|");
//					conteudo = linha.split("\\|");

					Produto pLinha = new Produto(Integer.parseInt(conteudo[0]), conteudo[1], conteudo[2],
							Double.parseDouble(conteudo[3]), TipoProduto.valueOf(conteudo[4].toUpperCase()));

					if (pLinha.getId() == pBanco.getId()) {
						fNovosDados.setId(pBanco.getId());
						escreverDeVolta.add(montaRegistro(fNovosDados));
					} else {
						escreverDeVolta.add(montaRegistro(pLinha));
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
			System.out.println("Nao existe produto");
		}

	}

	private String montaRegistro(Produto produto) {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append(produto.getId());
		strBuilder.append("|");
		strBuilder.append(produto.getNome());
		strBuilder.append("|");
		strBuilder.append(produto.getDescricao());
		strBuilder.append("|");
		strBuilder.append(produto.getValorUnit());
		strBuilder.append("|");
		strBuilder.append(produto.getTipoProduto());
		return strBuilder.toString();
	}

}
