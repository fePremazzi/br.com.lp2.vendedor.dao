package br.com.lp2.vendedor.dao.daos;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import br.com.lp2.vendedor.comum.Enums.TipoProduto;
import br.com.lp2.vendedor.comum.VO.Cliente;
import br.com.lp2.vendedor.comum.VO.Funcionario;
import br.com.lp2.vendedor.comum.VO.Pedido;
import br.com.lp2.vendedor.comum.VO.Produto;
import br.com.lp2.vendedor.dao.repo.Repositorio;

public class PedidoDAO extends Repositorio {

	public PedidoDAO() {
		tablePath = "pedido_table.txt";
	}

	private ClienteDAO clDao = new ClienteDAO();
	private FuncionarioDAO fDAO = new FuncionarioDAO();
	private ProdutoDAO prodDAO = new ProdutoDAO();

	@Override
	public void Insere(Object obj) throws Exception {
		Pedido pedido = ((Pedido) obj);
		pedido.setId(getProximoId());

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(tablePath, true));) {
			writer.write(montaRegistro(pedido));
			writer.newLine();
		} catch (Exception e) {
//			e.printStackTrace();
		}
	}

	public void Insere(Object obj, boolean proximoId) throws Exception {
		Pedido pedido = ((Pedido) obj);
		if (proximoId)
			pedido.setId(getProximoId());

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(tablePath, true));) {
			writer.write(montaRegistro(pedido));
			writer.newLine();
		} catch (Exception e) {
//			e.printStackTrace();
		}
	}

	@Override
	public List<Object> SelecionaTodos() {
		// TODO
		return null;
	}

	@Override
	public Object Seleciona(int id) {

		Pedido pedido = new Pedido();
		boolean iniciaObjeto = true;
		pedido.setListaProdutos(new ArrayList<Produto>());
		try {
			List<String> allLines = getTextFromTable(tablePath);
			for (String linha : allLines) {
				String[] conteudo = linha.split("\\|");

				if (conteudo[0].equals("")) {
				} else if (Integer.parseInt(conteudo[0]) == (id)) {
					if (iniciaObjeto) {
						pedido.setId(Integer.parseInt(conteudo[0]));
						pedido.setNome(conteudo[1]);
						pedido.setDataAbertura(conteudo[2]);
						pedido.setCliente((Cliente) clDao.Seleciona(Integer.parseInt(conteudo[4])));
						pedido.setUsuario((Funcionario) fDAO.Seleciona(Integer.parseInt(conteudo[5])));
						iniciaObjeto = false;
					}

					pedido.getListaProdutos().add((Produto) prodDAO.Seleciona(Integer.parseInt(conteudo[3])));

				}
			}
			return pedido;

		} catch (Exception e) {
//			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void Atualiza(Object objAtualizado, int id) {
		try {
			Deleta(id);
			((Pedido) objAtualizado).setId(id);
			Insere(objAtualizado, false);
		} catch (Exception e) {
//			e.printStackTrace();
		}

	}

	private String montaRegistro(Pedido pedido) {
		StringBuilder strBuilder = new StringBuilder();
		int k = 0;
		for (Produto produto : pedido.getListaProdutos()) {
			strBuilder.append(pedido.getId());
			strBuilder.append("|");
			strBuilder.append(pedido.getNome());
			strBuilder.append("|");
			strBuilder.append(pedido.getDataAbertura());
			strBuilder.append("|");
			strBuilder.append(produto.getId());
			strBuilder.append("|");
			strBuilder.append(pedido.getCliente().getId());
			strBuilder.append("|");
			strBuilder.append(pedido.getUsuario().getId());
			k++;
			if (k != pedido.getListaProdutos().size() - 1)
				strBuilder.append(System.getProperty("line.separator"));
		}

		return strBuilder.toString();
	}

}
