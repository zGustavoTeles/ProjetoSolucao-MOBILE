package com.inserir;

import java.util.Random;
import com.auxiliares.Auxiliares;
import com.bottom.BottomImg;
import com.litebase.LitebasePack;
import com.venda.Loja;
import totalcross.ui.Button;
import totalcross.ui.ComboBox;
import litebase.ResultSet;
import totalcross.sys.Convert;
import totalcross.ui.Edit;
import totalcross.ui.ImageControl;
import totalcross.ui.Label;
import totalcross.ui.dialog.MessageBox;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.gfx.Color;
import totalcross.ui.image.Image;
import totalcross.util.Date;

public class InserirProdutoCarrinho extends totalcross.ui.Window {

	private Label				 lblProduto;
	private Label 				 lblEstoque;
	private Label 			     lblValor;
	private Label                lblAviso;
	private Label 				 lblQuantidade;
	private Label				 lblTotal;
	private Label   			 lblCodigo;
	private Label				 lblCategoria;
	private Label 				 lblMarca;
	private Label 				 lblDescricao;
	private Edit				 editCategoria;
	private Edit				 editMarca;
	private Edit				 editDescricao;
	private Edit 				 editCodigo;
	private Edit 				 editProduto;
	private Edit 				 editEstoque;
	private Edit 				 editValor;
	private Edit				 editQuantidade;
	private Edit 				 editTotal;
	
	private Button	 			 btnInserir;
	private Button 				 btnVoltar;
	
	private ComboBox			 cmbTipoPagamento;
	private ImageControl		 imgInserirProdutoCarrinho;
	
	public double 				 valorProduto = 0.0;
	public double				 total = 0.0;
	public int				     qntEstoqueFinal = 0;
	public int 				     quantidade = 0;
	public int 					 quantidadeEstoque = 0;
	public int 					 qntEstoqueCalculo = 0;
	public String 				 quantidadeVendida = "";
	public String 				 dataEntrada = "";
	public String				 estoque = "";
	public String 				 quantidadeInserida = "";


	public InserirProdutoCarrinho() {
		setBackColor(0x1c355d);
		initUI();
	}

	public void initUI() {

		try {
			
			imgInserirProdutoCarrinho = new ImageControl(new Image("img/inserirProdutoCarrinho.png"));
			imgInserirProdutoCarrinho.scaleToFit = true;
			imgInserirProdutoCarrinho.centerImage = true;
			add(imgInserirProdutoCarrinho, CENTER, TOP - 20, SCREENSIZE + 20, SCREENSIZE + 20);
			
//			lblCategoria = new Label("CATEGORIA:   ");
//			add(lblCategoria);
//			lblCategoria.setRect(LEFT + 5, AFTER - 30, PREFERRED, PREFERRED, imgInserirProdutoCarrinho);
//			lblCategoria.setBackColor(0x1c355d);
//			lblCategoria.setForeColor(Color.WHITE);

			editCategoria = new Edit();
			add(editCategoria);
			editCategoria.setRect(CENTER, AFTER - 30, SCREENSIZE + 80, SCREENSIZE + 4, imgInserirProdutoCarrinho);
			editCategoria.setBackColor(Color.WHITE);
			editCategoria.setForeColor(0x1c355d);
			editCategoria.setText(Loja.categoria);
			editCategoria.setEditable(false);
			
//			lblMarca = new Label("MARCA:          ");
//			add(lblMarca);
//			lblMarca.setRect(LEFT + 5, AFTER + 15, PREFERRED, PREFERRED, editCategoria);
//			lblMarca.setBackColor(0x1c355d);
//			lblMarca.setForeColor(Color.WHITE);

			editMarca = new Edit();
			add(editMarca);
			editMarca.setRect(CENTER, AFTER, SCREENSIZE + 80, SCREENSIZE + 4, editCategoria);
			editMarca.setBackColor(Color.WHITE);
			editMarca.setForeColor(0x1c355d);
			editMarca.setText(Loja.marca);
			editMarca.setEditable(false);
			
//			lblDescricao = new Label("DESCRI��O:   ");
//			add(lblDescricao);
//			lblDescricao.setRect(LEFT + 5, AFTER + 15, PREFERRED, PREFERRED, editMarca);
//			lblDescricao.setBackColor(0x1c355d);
//			lblDescricao.setForeColor(Color.WHITE);

			editDescricao = new Edit();
			add(editDescricao);
			editDescricao.setRect(CENTER, AFTER, SCREENSIZE + 80, SCREENSIZE + 4, editMarca);
			editDescricao.setBackColor(Color.WHITE);
			editDescricao.setForeColor(0x1c355d);
			editDescricao.setText(Loja.descricao);
			editDescricao.setEditable(false);		

//			lblProduto = new Label("PRODUTO:      ");
//			add(lblProduto);
//			lblProduto.setRect(LEFT + 5, AFTER + 15, PREFERRED, PREFERRED, editDescricao);
//			lblProduto.setBackColor(0x1c355d);
//			lblProduto.setForeColor(Color.WHITE);

			editProduto = new Edit();
			add(editProduto);
			editProduto.setRect(CENTER, AFTER, SCREENSIZE + 80, SCREENSIZE + 4, editDescricao);
			editProduto.setBackColor(Color.WHITE);
			editProduto.setForeColor(0x1c355d);
			editProduto.setText(Loja.produto);
			editProduto.setEditable(false);

//			lblCodigo = new Label("C�DIGO:          ");
//			add(lblCodigo);
//			lblCodigo.setRect(LEFT + 5, AFTER + 15, PREFERRED, PREFERRED, editProduto);
//			lblCodigo.setBackColor(0x1c355d);
//			lblCodigo.setForeColor(Color.WHITE);

			editCodigo = new Edit();
			add(editCodigo);
			editCodigo.setRect(CENTER, AFTER, SCREENSIZE + 80, SCREENSIZE + 4, editProduto);
			editCodigo.setBackColor(Color.WHITE);
			editCodigo.setForeColor(0x1c355d);
			editCodigo.setText(Loja.codigo);
			editCodigo.setEditable(false);

//			lblEstoque = new Label("ESTOQUE:       ");
//			add(lblEstoque);
//			lblEstoque.setRect(LEFT + 5, AFTER + 15, PREFERRED, PREFERRED, editCodigo);
//			lblEstoque.setBackColor(0x1c355d);
//			lblEstoque.setForeColor(Color.WHITE);

			editEstoque = new Edit();
			add(editEstoque);
			editEstoque.setRect(CENTER, AFTER, SCREENSIZE + 80, SCREENSIZE + 4, editCodigo);
			editEstoque.setBackColor(Color.WHITE);
			editEstoque.setForeColor(0x1c355d);
			editEstoque.setText(Loja.quantidade);
			editEstoque.setEditable(false);

//			lblQuantidade = new Label("QUANTIDADE: ");
//			add(lblQuantidade);
//			lblQuantidade.setRect(LEFT + 5, AFTER + 15, PREFERRED, PREFERRED, lblEstoque);
//			lblQuantidade.setBackColor(0x1c355d);
//			lblQuantidade.setForeColor(Color.WHITE);

			editQuantidade = new Edit();
			add(editQuantidade);
			editQuantidade.setRect(CENTER, AFTER, SCREENSIZE + 80, SCREENSIZE + 4, editEstoque);
			editQuantidade.setBackColor(Color.WHITE);
			editQuantidade.setForeColor(0x1c355d);
			editQuantidade.setValidChars("0 1 2 3 4 5 6 7 8 9");

//			lblValor = new Label("VALOR:            ");
//			add(lblValor);
//			lblValor.setRect(LEFT + 5, AFTER + 15, PREFERRED, PREFERRED, lblQuantidade);
//			lblValor.setBackColor(0x1c355d);
//			lblValor.setForeColor(Color.WHITE);

			editValor = new Edit();
			add(editValor);
			editValor.setRect(CENTER, AFTER, SCREENSIZE + 80, SCREENSIZE + 4, editQuantidade);
			editValor.setBackColor(Color.WHITE);
			editValor.setForeColor(0x1c355d);
			editValor.setText(Loja.valor);
			editValor.setEditable(false);

//			lblTotal = new Label("TOTAL:            ");
//			add(lblTotal);
//			lblTotal.setRect(LEFT + 5, AFTER + 15, PREFERRED, PREFERRED, editValor);
//			lblTotal.setBackColor(0x1c355d);
//			lblTotal.setForeColor(Color.WHITE);

			editTotal = new Edit();
			add(editTotal);
			editTotal.setRect(CENTER, AFTER, SCREENSIZE + 80, SCREENSIZE + 4, editValor);
			editTotal.setBackColor(Color.WHITE);
			editTotal.setForeColor(0x1c355d);
			editTotal.setText("0.00");
			editTotal.setEditable(false);
			
			cmbTipoPagamento = new ComboBox();
			add(cmbTipoPagamento);
			cmbTipoPagamento.setRect(CENTER, AFTER + 50, SCREENSIZE + 40, PREFERRED, editTotal);
			
			btnVoltar = BottomImg.imageWithText(new Image("img/voltar.png"), "Voltar", Button.BOTTOM);
			add(btnVoltar, RIGHT - 2, BOTTOM, SCREENSIZE + 25, PREFERRED + 10);
			btnVoltar.setBackColor(0x1c355d);
			btnVoltar.setForeColor(Color.WHITE);
            
            btnInserir = BottomImg.imageWithText(new Image("img/compra.png"), "Inserir", Button.BOTTOM);
			add(btnInserir, LEFT, BOTTOM, SCREENSIZE + 25, PREFERRED + 10);
			btnInserir.setBackColor(0x1c355d);
			btnInserir.setForeColor(Color.WHITE);
			
			reposition();
			
			carregaCmbTipoPagamento();
			cmbTipoPagamento.setSelectedIndex(0);
			
			editQuantidade.requestFocus();
			
		} catch (Exception e) {
			Auxiliares.messagebox("ERRO","Erro ao construir a tela inserir\n" + e);

		}

	}

	public void onEvent(Event evt) {

		try {
			switch (evt.type) {
			case ControlEvent.PRESSED:

				if (evt.target == btnVoltar) {
					unpop();

				}
				if (evt.target == btnInserir) {

					if (editQuantidade.getText().length() == 0) {
						Auxiliares.messagebox("SOLUCAO", "Por favor insira uma quantidade!");
						return;
					}

					if (editQuantidade.getText().length() > 0) {

						estoque = editEstoque.getText();
						quantidadeInserida = editQuantidade.getText();

						if (Convert.toInt(quantidadeInserida) > Convert.toInt(Loja.quantidade)) {
							Auxiliares.messagebox("SOLUCAO", "Quantidade inserida maior que a quantidade em estoque!");
							
							editQuantidade.setText("");
							editEstoque.setText(Loja.quantidade);
							editTotal.setText("0.00");
							
							editQuantidade.requestFocus();
							return;

						}

						String[] ArtButtonArray = { "Sim", "N�o" };

						int i = Auxiliares.messageBox("SOLUCAO", "Deseja inserir " + editQuantidade.getText()
								+ " unidade(s) deste produto\nno carrinho?", ArtButtonArray);

						if (i == 1) {
							return;

						} else {

							insereProdutoNoCarrinho();

							Auxiliares.messagebox("SOLUCAO", "Produto inserido no carrinho!");

							Loja.btnCarrinho.setEnabled(true);
							unpop();
						}
					}

				}
				if (editQuantidade.getText().length() > 0) {
					calculaTotalProduto();

				} else if (editQuantidade.getText().length() == 0) {
					editEstoque.setText(Loja.quantidade);
					editTotal.setText("");
				}
			}
		} catch (Exception e) {
			Auxiliares.messagebox("ERRO", "Erro na valida��o da tela inserir\n" + e);
		}

	}

	public void calculaTotalProduto() {

		try {

			estoque = Loja.quantidade;
			quantidadeInserida = editQuantidade.getText();
			qntEstoqueFinal = Convert.toInt(estoque) - Convert.toInt(quantidadeInserida);

			String quantidadeString = "";
			quantidadeString = editQuantidade.getText();
			valorProduto = Convert.toDouble(Loja.valor.replace(".", "."));
			quantidade = Convert.toInt(quantidadeString);

			total = valorProduto * quantidade;
			editTotal.setText(Convert.toString(total, 2));
			editEstoque.setText(Convert.toString(qntEstoqueFinal));

		} catch (Exception e) {
			Auxiliares.messagebox("ERRO", "Erro no calculo do produto\n" + e);
		}

	}
	
	public void insereProdutoNoCarrinho() {

		String sql = "";
		LitebasePack lb = null;
		ResultSet rs = null;

		try {
			try {
				
				lb = new LitebasePack();
				
				sql = "SELECT CODIGO FROM VENDAPRODUTOTEMP";
				rs = lb.executeQuery(sql);
				rs.first();
				
				if (rs.getRowCount() == 0) {

					Random random = new Random();
					int codigo = random.nextInt(900);
					String dataVenda;
					Date year = new Date();
					Date month = new Date();
					Date day = new Date();

					dataVenda = Date.formatDate(year.getYear(), month.getMonth(), day.getDay());

					sql = "INSERT INTO VENDAPRODUTOTEMP " + "(" + " CODIGO, PRODUTO, VALOR, QUANTIDADE, CODIGOPROD, "
							+ " CATEGORIA, MARCA, DESCRICAO, TIPOPAGAMENTO, DATASAIDA " + ")" + " VALUES " 
							+ "( '" + codigo + "' , '" + editProduto.getText()
	                        + "', '" + editTotal.getText() + "', '"
							+ editQuantidade.getText() + "', '" + editCodigo.getText() + "','" + editCategoria.getText() + "','" 
							+ editMarca.getText() + "', '"+ editDescricao.getText() 
							+ "', '" + cmbTipoPagamento.getSelectedItem() + "', '" + dataVenda + "'" + ")";

					lb.executeUpdate(sql);

				}else {
					
					int codigo = rs.getInt("CODIGO");
					String dataVenda;
					Date year = new Date();
					Date month = new Date();
					Date day = new Date();

					dataVenda = Date.formatDate(year.getYear(), month.getMonth(), day.getDay());

					sql = "INSERT INTO VENDAPRODUTOTEMP " + "(" + " CODIGO, PRODUTO, VALOR, QUANTIDADE, CODIGOPROD, "
							+ " CATEGORIA, MARCA, DESCRICAO, TIPOPAGAMENTO, DATASAIDA " + ")" + " VALUES " 
							+ "( '" + codigo + "' , '" + editProduto.getText()
	                        + "', '" + editTotal.getText() + "', '"
							+ editQuantidade.getText() + "', '" + editCodigo.getText() + "','" + editCategoria.getText() + "','" 
							+ editMarca.getText() + "', '"+ editDescricao.getText() 
							+ "', '" + cmbTipoPagamento.getSelectedItem() + "', '" + dataVenda + "'" + ")";

					lb.executeUpdate(sql);
					
				}

			} finally {
				if (lb != null)
					lb.closeAll();
			}

		} catch (Exception e) {
			Auxiliares.messagebox("ERRO", "Erro ao inserir produto estoque\n" + e);
		}

	}
	
	public void carregaCmbTipoPagamento() {
		{
			String sql = "";
			LitebasePack lb = null;
			ResultSet rs = null;

			try {
				try {
					lb = new LitebasePack();
					sql = " SELECT DESCRICAO FROM TIPOPAGAMENTO";

					rs = lb.executeQuery(sql);
					rs.first();
					for (int i = 0; rs.getRowCount() > i; i++) {
						String[] b = new String[1];
						b[0] = rs.getString("DESCRICAO");
						cmbTipoPagamento.add(b);
						rs.next();
					}
				} finally {
					if (lb != null)
						lb.closeAll();

				}
			} catch (Exception e) {
				Auxiliares.messagebox("ERRO", "Erro carregaCmbTipoPagamento\n" + e);

			}

		}
	}

}
