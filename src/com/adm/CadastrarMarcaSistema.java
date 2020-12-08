package com.adm;


import java.util.Random;
import com.auxiliares.Auxiliares;
import com.bottom.BottomImg;
import com.litebase.LitebasePack;
import litebase.ResultSet;
import totalcross.ui.Button;
import totalcross.sys.Convert;
import totalcross.ui.ComboBox;
import totalcross.ui.Edit;
import totalcross.ui.ImageControl;
import totalcross.ui.Label;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.gfx.Color;
import totalcross.ui.image.Image;

public class CadastrarMarcaSistema extends totalcross.ui.Window {

	public Label 				lblMarca;
	public Label 				lblUsuario;
	public Label				lblCodigo;
	
	public Edit					editCodigo;
	public Edit					editCodigoProduto;
	public Edit					editMarca;
	
	private Label 				lblProduto;
	private Label 				lblCategoria;
	private Label 				lblCodigoProduto;
	
	private ComboBox			cmbProduto;
	private ComboBox			cmbCategoria;
	
	public Button		    btnCadastrar;
	public Button			btnGerarCodigo;
	public Button 			btnVoltar;
	
	public ImageControl		    imgCadastrarEmpresa;

	public CadastrarMarcaSistema() {
		setBackColor(0x1c355d);
		initUI();
		carregaCmbCategoria();
	}

	public void initUI() {

		try {
			
			imgCadastrarEmpresa = new ImageControl(new Image("img/cadastrarMarca.png"));
			imgCadastrarEmpresa.scaleToFit = true;
			imgCadastrarEmpresa.centerImage = true;
			add(imgCadastrarEmpresa, CENTER, TOP, SCREENSIZE + 20, SCREENSIZE + 20);
			
			lblMarca = new Label("MARCA: ");
			add(lblMarca);
			lblMarca.setBackColor(0x1c355d);
			lblMarca.setForeColor(Color.WHITE);
			lblMarca.setRect(LEFT, AFTER, PREFERRED, PREFERRED, imgCadastrarEmpresa);

			add(editMarca = new Edit(), LEFT, AFTER, PREFERRED, PREFERRED, lblMarca);
			editMarca.setBackColor(Color.WHITE);
			editMarca.capitalise = (Edit.ALL_UPPER);
			editMarca.setForeColor(0x1c355d);

			lblCategoria = new Label("CATEGORIA:");
			add(lblCategoria);
			lblCategoria.setRect(LEFT, AFTER + 30, PREFERRED, PREFERRED, editMarca);
			lblCategoria.setBackColor(0x1c355d);
			lblCategoria.setForeColor(Color.WHITE);
			
			cmbCategoria = new ComboBox();
			add(cmbCategoria);
			cmbCategoria.setRect(LEFT, AFTER, FILL + 5, PREFERRED, lblCategoria);

			lblProduto = new Label("PRODUTO:");
			add(lblProduto);
			lblProduto.setRect(LEFT, AFTER + 30, PREFERRED, PREFERRED, cmbCategoria);
			lblProduto.setBackColor(0x1c355d);
			lblProduto.setForeColor(Color.WHITE);
			
			cmbProduto = new ComboBox();
			add(cmbProduto);
			cmbProduto.setRect(LEFT, AFTER, FILL + 5, PREFERRED, lblProduto);

			lblCodigoProduto = new Label("C�DIGO:");
			add(lblCodigoProduto);
			lblCodigoProduto.setRect(LEFT, AFTER + 30, PREFERRED, PREFERRED,cmbProduto);
			lblCodigoProduto.setBackColor(0x1c355d);
			lblCodigoProduto.setForeColor(Color.WHITE);
			
			add(editCodigo = new Edit(), LEFT, AFTER, PREFERRED, PREFERRED,lblCodigoProduto );
			editCodigo.setBackColor(Color.WHITE);
			editCodigo.setForeColor(0x1c355d);
			editCodigo.setEditable(false);
			
			btnGerarCodigo = new Button("C�DIGO");
			add(btnGerarCodigo);
			btnGerarCodigo.setRect(LEFT, AFTER + 30, SCREENSIZE - 5, PREFERRED, editCodigo);
			btnGerarCodigo.setBackColor(0x1c355d);
			btnGerarCodigo.setForeColor(Color.WHITE);	
			
			add(editCodigoProduto = new Edit(), LEFT, AFTER + 10, PREFERRED, PREFERRED);
			editCodigoProduto.setBackColor(Color.WHITE);
			editCodigoProduto.setForeColor(0x1c355d);
			editCodigoProduto.setEditable(false);
			
			btnCadastrar = BottomImg.imageWithText(new Image("img/cadastrarProdutoSistema.png"), "Cadastrar", Button.BOTTOM);
			add(btnCadastrar, LEFT + 5, BOTTOM, SCREENSIZE - 4, PREFERRED + 15);
			btnCadastrar.setBackColor(0x1c355d);
			btnCadastrar.setForeColor(Color.WHITE);

			btnVoltar = BottomImg.imageWithText(new Image("img/voltar.png"), "Voltar", Button.BOTTOM);
			add(btnVoltar, RIGHT - 2, BOTTOM, SCREENSIZE + 25, SCREENSIZE + 10);
			btnVoltar.setBackColor(0x1c355d);
			btnVoltar.setForeColor(Color.WHITE);
			
			reposition();
			editMarca.requestFocus();
			
		} catch (Exception e) {
			Auxiliares.messagebox("ERRO", "Erro ao construir o menu CadastrarMarcaSistema\n" + e);

		}

	}

	public void onEvent(Event evt) {

		try {
			switch (evt.type) {
			case ControlEvent.PRESSED:

				if (evt.target == btnVoltar) {
					unpop();

				} else if (evt.target == btnCadastrar) {
					String[] ArtButtonArray = { "Sim", "N�o" };

					if (editMarca.getText().equals("") || editCodigo.getText().equals("")
							|| cmbProduto.getSelectedItem() == "" || cmbCategoria.getSelectedItem() == "" || editCodigoProduto.getText().equals("")) {
						Auxiliares.messagebox("SOLUCAO", "Preencha todos os campos!");
						return;
					}

					int i = Auxiliares.messageBox("SOLUCAO", "Deseja cadastrar a marca no sistema?", ArtButtonArray);

					if (i == 1) {
						return;

					} else {
						cadastraMarcaSistema();
						Auxiliares.messagebox("SOLUCAO", "Marca cadastrado com sucesso!");
						unpop();

					}
				} else if (evt.target == btnGerarCodigo) {
					Random random = new Random();
					int codigo = random.nextInt(900);
					editCodigoProduto.setText(Convert.toString(codigo));

				}else if (evt.target == cmbCategoria) {
					cmbProduto.removeAll();
					carregaCmbProduto();			

				}else if (evt.target == cmbProduto) {
					buscaCodigoProduto();
				}

			}

		} catch (Exception e) {
			Auxiliares.messagebox("SOLUCAO", "Erro na valida��o do menu CadastrarMarcaSistema\n " + e);
		}

	}
	
	public void cadastraMarcaSistema() {
		String sql = "";
		LitebasePack lb = null;

		try {

			try {
				lb = new LitebasePack();
				sql = " INSERT INTO marca (codigo, descricao, categoria, codigoprod)" + " VALUES " + "(" + "'"
						+ editCodigoProduto.getText() + "'," + "'" + editMarca.getText() + "'," + "'"
						+ cmbCategoria.getSelectedItem() + "'," + "'" + editCodigo.getText() + "'" + ")";

				lb.executeUpdate(sql);

			} finally {
				if (lb != null)
					lb.closeAll();
			}

		} catch (Exception e) {
			Auxiliares.messagebox("ERRO", "Erro ao buscar cadastrarProdutoSistema\n" + e);

			return;
		}
	}
	
	public void carregaCmbCategoria() {
		{
			String sql = "";
			LitebasePack lb = null;
			ResultSet rs = null;

			try {
				try {
					cmbProduto.removeAll();
					lb = new LitebasePack();
					sql = " SELECT DESCRICAO FROM CATEGORIA";

					rs = lb.executeQuery(sql);
					rs.first();
					for (int i = 0; rs.getRowCount() > i; i++) {
						String[] b = new String[1];
						b[0] = rs.getString("DESCRICAO");
						cmbCategoria.add(b);
						rs.next();
					}
				} finally {
					if (lb != null)
						lb.closeAll();

				}
			} catch (Exception e) {
				Auxiliares.messagebox("ERRO", "Erro ao carregaCmbCategoria\n" + e);

			}

		}
	}
	
	public void carregaCmbProduto() {
		{
			String sql = "";
			LitebasePack lb = null;
			ResultSet rs = null;

			try {
				try {
					lb = new LitebasePack();
					sql = " SELECT PRODUTO, CATEGORIA FROM PRODUTO "
						+ " WHERE CATEGORIA = " + "'" + cmbCategoria.getSelectedItem() + "'";;

					rs = lb.executeQuery(sql);
					rs.first();
					for (int i = 0; rs.getRowCount() > i; i++) {
						String[] b = new String[1];
						b[0] = rs.getString("PRODUTO");
						cmbProduto.add(b);
						rs.next();
					}
				} finally {
					if (lb != null)
						lb.closeAll();

				}
			} catch (Exception e) {
				Auxiliares.messagebox("ERRO", "Erro ao carregaCmbProduto\n" + e);

			}

		}
	}
	
	public void buscaCodigoProduto() {
		{
			String sql = "";
			LitebasePack lb = null;
			ResultSet rs = null;

			try {
				try {
					lb = new LitebasePack();
					sql = " SELECT CODIGO, PRODUTO FROM PRODUTO " 
					    + " WHERE PRODUTO = " + "'" + cmbProduto.getSelectedItem() + "'";

					rs = lb.executeQuery(sql);
					rs.first();
					editCodigo.setText("");
					editCodigo.setText(Convert.toString(rs.getInt("CODIGO")));

				} finally {
					if (lb != null)
						lb.closeAll();

				}
			} catch (Exception e) {
				Auxiliares.messagebox("ERRO", "Erro ao carregaCmbProduto\n" + e);

			}

		}
	}

}
