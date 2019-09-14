package br.com.lp2.vendedor.dao.repo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import br.com.lp2.vendedor.comum.VO.Funcionario;

public abstract class Repositorio {

	protected String tablePath;

	public abstract void Insere(Object obj) throws Exception;

	public abstract Object Seleciona(int id);

	public abstract List<Object> SelecionaTodos();

	public abstract void Atualiza(Object objAtualizado, int id);

	public void Deleta(int id) {
		List<String> escreverDeVolta = new ArrayList<String>();

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(tablePath, true));) {
			List<String> allLines = getTextFromTable(tablePath);
			for (String linha : allLines) {
				String[] conteudo = new String[5];
				conteudo = linha.split("\\|");

				if (!(Integer.parseInt(conteudo[0]) == id)) {
					escreverDeVolta.add(linha);
				} else {
					System.out.println("Deletado");
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
	}

	public int getProximoId() {
		int proximoId = 0;
		try {
			List<String> allLines = getTextFromTable(tablePath);
			for (String registro : allLines) {
				String[] linha = registro.split("\\|");
//				linha = registro.split("\\|");
				if (registro.equals("")) {
				} else if (Integer.parseInt(linha[0]) >= proximoId)
					proximoId = Integer.parseInt(linha[0]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (proximoId + 1);
	};

	protected List<String> getTextFromTable(String tablePath) throws IOException {
		Path path = Paths.get(tablePath);
		byte[] bytes = Files.readAllBytes(path);
		List<String> allLines = Files.readAllLines(path, StandardCharsets.UTF_8);
		return allLines;
	}

	protected void clearTheFile(String tablePath) throws IOException {
		FileWriter fwOb = new FileWriter(tablePath, false);
		PrintWriter pwOb = new PrintWriter(fwOb, false);
		pwOb.flush();
		pwOb.close();
		fwOb.close();
	}

}
