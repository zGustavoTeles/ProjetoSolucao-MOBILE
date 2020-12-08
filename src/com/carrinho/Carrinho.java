package com.carrinho;


import com.auxiliares.Auxiliares;
import com.bottom.BottomImg;
import com.cellcontroller6.CellController6;
import com.estoque.AlterarProdutoEstoque;
import com.inserir.InserirProdutoCarrinho;
import com.litebase.LitebasePack;
import com.venda.Loja;
import litebase.ResultSet;
import totalcross.sys.Convert;
import totalcross.sys.Settings;
import totalcross.ui.Button;
import totalcross.ui.Container;
import totalcross.ui.Grid;
import totalcross.ui.ImageControl;
import totalcross.ui.Label;
import totalcross.ui.MainWindow;
import totalcross.ui.dialog.MessageBox;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.event.GridEvent;
import totalcross.ui.gfx.Color;
import totalcross.ui.image.Image;
import totalcross.util.Date;

public class Carrinho extends totalcross.ui.Window{
	
	private Label							lblCarrinho;
	private Label							lblQuantidade;
	private Label							lblTotal;
	private Button 							btnVoltar;
	private Button							btnVender;
	private Button							btnAlterar;
	private Button							btnRemover;
	public Grid							    gridCarrinho;
	public ImageControl					    imgCarrinho;
	
	public int 					 			quantidadeEstoque = 0;
	public int 						        qntEstoqueCalculo = 0;
	public int  				 			quantidadeVendida = 0;
	public int								codigo = 0;
	public int								codigoProdTemp = 0;
	public int 								codigoTemp;
	public int								quantidadeCarrinho = 0;
	public double							totalCarrinho = 0.0;
	public Date 				 			dataEntrada;
	public String							dataString;
	public String							dataSaidaString;
	public String							valorProduto;
	
	public String							produtoTemp;
	public String							totalTemp;
	public String							categoriaTemp;
	public String							marcaTemp;
	public String                           descricaoTemp;
	public String							tipoPagamento;
	public Date								dataSaidaTemp;
	public int   							quantidadeTemp;
	
	public static String					codigoProduto = "";
	public static String					quantidadeProduto = "";
	public static String					tipoPagamentoProduto = "";
	public static String					totalProduto = "";
	
	public Carrinho(){
		 setBackColor(0x1c355d);
		 initUI();
		 
	}
	
	public void initUI() {
		
		try {
					
			lblQuantidade = new Label("");
			add(lblQuantidade);
			lblQuantidade.setRect(LEFT + 2, TOP + 5, PREFERRED, PREFERRED);
			lblQuantidade.setBackColor(0x1c355d);
			lblQuantidade.setForeColor(Color.WHITE);
			
			lblTotal = new Label("");
			add(lblTotal);
			lblTotal.setRect(LEFT + 2, AFTER + 5, PREFERRED, PREFERRED, lblQuantidade);
			lblTotal.setBackColor(0x1c355d);
			lblTotal.setForeColor(Color.WHITE);
			
			imgCarrinho = new ImageControl(new Image("img/carrinho.png"));
			imgCarrinho.scaleToFit = true;
			imgCarrinho.centerImage = true;
			add(imgCarrinho, RIGHT - 5, TOP, SCREENSIZE + 16, PREFERRED);
			
			lblCarrinho = new Label("CARRINHO:");
			add(lblCarrinho);
			lblCarrinho.setRect(BEFORE - 4, TOP + 5, PREFERRED, PREFERRED, imgCarrinho);
			lblCarrinho.setBackColor(0x1c355d);
			lblCarrinho.setForeColor(Color.WHITE);
            
			btnVender = BottomImg.imageWithText(new Image("img/vender.png"), "Vender", Button.BOTTOM);
			add(btnVender, LEFT, BOTTOM, SCREENSIZE + 25, SCREENSIZE + 10);
			btnVender.setBackColor(0x1c355d);
			btnVender.setForeColor(Color.WHITE);

			btnAlterar = BottomImg.imageWithText(new Image("img/alterar.png"), "Alterar", Button.BOTTOM);
			add(btnAlterar, AFTER + 8, BOTTOM, SCREENSIZE + 25, SCREENSIZE + 10);
			btnAlterar.setBackColor(0x1c355d);
			btnAlterar.setForeColor(Color.WHITE);

			btnRemover = BottomImg.imageWithText(new Image("img/remover.png"), "Remover", Button.BOTTOM);
			add(btnRemover, AFTER + 8, BOTTOM, SCREENSIZE + 25, SCREENSIZE + 10);
			btnRemover.setBackColor(0x1c355d);
			btnRemover.setForeColor(Color.WHITE);
			
			btnVoltar = BottomImg.imageWithText(new Image("img/voltar.png"), "Voltar", Button.BOTTOM);
			add(btnVoltar, RIGHT - 2, BOTTOM, SCREENSIZE + 25, SCREENSIZE + 10);
			btnVoltar.setBackColor(0x1c355d);
			btnVoltar.setForeColor(Color.WHITE);

			int gridWidths[] = new int[9];
			gridWidths[0] = 5;
			gridWidths[1] = 400;
			gridWidths[2] = 100;
			gridWidths[3] = 5;
			gridWidths[4] = 300;
			gridWidths[5] = 190;
			gridWidths[6] = 100;
			gridWidths[7] = 100;
			gridWidths[8] = 140;

			String[] caps = { "COD.", "PRODUTO", "QNT", "DESCRI��O", "MARCA", "CATEGORIA", " CODIGOPROD", "PAGAMENTO", "TOTAL" };
			int[] aligns = { Grid.LEFT, Grid.CENTER, Grid.LEFT, Grid.LEFT, Grid.LEFT, Grid.LEFT, Grid.LEFT, Grid.LEFT, Grid.LEFT};
			gridCarrinho = new Grid(caps, gridWidths, aligns, false);
			add(gridCarrinho);
			gridCarrinho.enableColumnResize = true;
			gridCarrinho.secondStripeColor = Color.getRGB(255, 255, 255);
			gridCarrinho.setBackColor(Auxiliares.backColorGridNovo);
			gridCarrinho.setForeColor(Auxiliares.defaultForeColorGridNovo);
			gridCarrinho.transparentBackground = false;
			gridCarrinho.setBorderStyle(totalcross.ui.Container.BORDER_NONE);
			gridCarrinho.verticalLineStyle = totalcross.ui.Grid.VERT_LINE;
			gridCarrinho.drawCheckBox = true;
			gridCarrinho.disableSort = false;
			gridCarrinho.boldCheck = false;
			gridCarrinho.captionsBackColor = Auxiliares.captionsBackColorNovo;
			gridCarrinho.checkColor = Auxiliares.checkColorNovo;
			gridCarrinho.firstStripeColor = Auxiliares.firstStripeColorNovo;
			gridCarrinho.enableColumnResize = false;
			gridCarrinho.setFont(Auxiliares.getFontNormal().asBold());
			gridCarrinho.secondStripeColor = Auxiliares.secondStripeColorNovo;
			gridCarrinho.setCellController(new CellController6());
			gridCarrinho.setRect(Grid.CENTER + 1, BEFORE - 30, SCREENSIZE + 100, SCREENSIZE + 70, btnVoltar);
			
			reposition();
			
            carregaGridCarrinho();
            calculaItensCarrinho();
            
		} catch (Exception e) {
			Auxiliares.messagebox("ERRO","Erro ao construir a tela carrinho\n" + e);

		}
		
	}
	
	
	public void onEvent(Event evt) {
		try {
			switch (evt.type) {
			case ControlEvent.PRESSED:

				if (evt.target == btnVoltar) {
					unpop();

				} else if (evt.target == btnVender) {
					String[] ArtButtonArray = { "Sim", "N�o" };
					
					int i = Auxiliares.messageBox("SOLUCAO",
							"Deseja finalizar a venda?\n" + lblQuantidade.getText() + "\n" + lblTotal.getText(),
							ArtButtonArray);

					if (i == 1) {
						return;
						
					} else {

						baixaEstoque();
						salvaInfoVenda();
						
						Auxiliares.messagebox("SOLUCAO",
								"Venda efetuada com sucesso!\n" + lblQuantidade.getText() + "\n" + lblTotal.getText());

						Loja.btnCarrinho.setEnabled(false);
						unpop();
					}
				}else if(evt.target == btnAlterar) {
					if (gridCarrinho.getSelectedItem() != null) {						
							AlterarProdutoCarrinho alterarItem = new AlterarProdutoCarrinho();
							alterarItem.popup();
							
					} else {
						
						Auxiliares.messagebox("SOLUCAO", "Deve-se selecionar um item para alterar!");
					}
				}else if(evt.target == btnRemover) {
					
					if (gridCarrinho.getSelectedItem() != null) {						
						RemoverProdutoCarrinho removerProdutoCarrinho = new RemoverProdutoCarrinho();
						removerProdutoCarrinho.popup();

					} else {
						Auxiliares.messagebox("SOLUCAO", "Deve-se selecionar um item para remover!");
					}

				}

			}
			switch (evt.type) {
			case GridEvent.SELECTED_EVENT:
				if (evt.target == gridCarrinho) {

					try {
						
						quantidadeProduto = gridCarrinho.getSelectedItem()[2];
						codigoProduto = gridCarrinho.getSelectedItem()[6];
						tipoPagamentoProduto = gridCarrinho.getSelectedItem()[7];
						totalProduto = gridCarrinho.getSelectedItem()[8];

					} catch (Exception e) {
						Auxiliares.messagebox("SOLUCAO", "Clique em um Item!");
					}

				}

			}

		} catch (Exception e) {
			Auxiliares.messagebox("ERRO", "Erro na valida��o da tela carrinho\n" + e);
		}

	}
	
	public void carregaGridCarrinho() {
		String sql = "";
		LitebasePack lb = null;
		ResultSet rs = null;

		try {
			try {
				lb = new LitebasePack();
				sql = " SELECT * FROM VENDAPRODUTOTEMP";
				
				gridCarrinho.removeAllElements();
				rs = lb.executeQuery(sql);
				rs.first();
				for (int i = 0; rs.getRowCount() > i; i++) {
					String[] b = new String[9];
					b[0] = Convert.toString(rs.getInt("CODIGO"));
					b[1] = rs.getString("PRODUTO");
					b[2] = Convert.toString(rs.getInt("QUANTIDADE"));
					b[3] = rs.getString("DESCRICAO");
					b[4] = rs.getString("MARCA");
					b[5] = rs.getString("CATEGORIA");
					b[6] =Convert.toString(rs.getInt("CODIGOPROD"));
					b[7] = rs.getString("TIPOPAGAMENTO");
					b[8] = rs.getString("VALOR");
					gridCarrinho.add(b);
					rs.next();										 
				}
			} finally {
				if (lb != null)
					lb.closeAll();

			}
		} catch (Exception e) {
			Auxiliares.messagebox("ERRO", "Erro ao carregaGridProdutos carrinho\n" + e);

		}

	}
	
	public void baixaEstoque() {
		String sql 			= "";
		LitebasePack lb     = null;
		ResultSet rs 		= null;
		
		String sql2 		= "";
		ResultSet rs2 		= null;
		LitebasePack lb2 	= null;

		try {

			try {
				lb = new LitebasePack();
				
				sql = "SELECT * FROM VENDAPRODUTOTEMP";
				
				rs = lb.executeQuery(sql);
				rs.beforeFirst();
				
				while (rs.next()) {
					codigoTemp = rs.getInt("CODIGO");
					produtoTemp = rs.getString("PRODUTO");
					codigoProdTemp = rs.getInt("CODIGOPROD");
					quantidadeTemp = rs.getInt("QUANTIDADE");
					categoriaTemp = rs.getString("CATEGORIA");
					marcaTemp = rs.getString("MARCA");
					descricaoTemp = rs.getString("DESCRICAO");
					tipoPagamento = rs.getString("TIPOPAGAMENTO");
					dataSaidaTemp = rs.getDate("DATASAIDA");
					
					lb2 = new LitebasePack();
					sql2 = " SELECT QUANTIDADE, CODIGO, DATAENTRADA, VALOR FROM ESTOQUE "
						 + " WHERE CODIGO = " + codigoProdTemp;

					rs2 = lb2.executeQuery(sql2);
					rs2.first();
					
					qntEstoqueCalculo = rs2.getInt("QUANTIDADE");
					dataEntrada = rs2.getDate("DATAENTRADA");
					valorProduto = rs2.getString("VALOR");
					
					dataString = dataEntrada.toString(Settings.DATE_YMD);
									
					codigo = rs2.getInt("CODIGO");
					quantidadeVendida = quantidadeTemp;

					quantidadeEstoque = qntEstoqueCalculo - quantidadeVendida;
										
					sql = "DELETE FROM ESTOQUE WHERE CODIGO = " + codigoProdTemp;

					lb.executeUpdate(sql);
					
						
					sql = "INSERT INTO 	ESTOQUE " + "(" + " CODIGO, PRODUTO, MARCA, VALOR, QUANTIDADE, "
						+ " CATEGORIA, DESCRICAO, DATAENTRADA " + ")" + " VALUES " + "( '" + codigoProdTemp
						+ "' , '" + produtoTemp + "', '" + marcaTemp + "', '" + valorProduto + "', '"
						+ quantidadeEstoque + "', '" + categoriaTemp + "','" + descricaoTemp + "', '"
						+ dataString + "'" + ")";

						lb.executeUpdate(sql);
					}

				
			} finally {
				if (lb != null)
					lb.closeAll();
			}

		} catch (Exception e) {
			Auxiliares.messagebox("ERRO", "Erro baixaEstoque\n" + e);
		}
	}
	
	public void salvaInfoVenda() {
		String sql = "";
		LitebasePack lb = null;
		ResultSet rs = null;

		try {

			try {
				lb = new LitebasePack();
				
				sql = "SELECT * FROM VENDAPRODUTOTEMP WHERE CODIGO = " + codigoTemp;
				
				rs = lb.executeQuery(sql);
				rs.beforeFirst();
				
				while (rs.next()) {
					codigoTemp = rs.getInt("CODIGO");
					produtoTemp = rs.getString("PRODUTO");
					totalTemp = rs.getString("VALOR");
					codigoProdTemp = rs.getInt("CODIGOPROD");
					quantidadeTemp = rs.getInt("QUANTIDADE");
					categoriaTemp = rs.getString("CATEGORIA");
					marcaTemp = rs.getString("MARCA");
					descricaoTemp = rs.getString("DESCRICAO");
					tipoPagamento = rs.getString("TIPOPAGAMENTO");
					dataSaidaTemp = rs.getDate("DATASAIDA");
					
					dataSaidaString = dataSaidaTemp.toString(Settings.DATE_YMD);
					
					sql = "INSERT INTO VENDAPRODUTO " + "(" + " CODIGO, PRODUTO, VALOR, CODIGOPROD, QUANTIDADE, "
							+ " CATEGORIA, MARCA, DESCRICAO,TIPOPAGAMENTO, DATASAIDA " + ")" + " VALUES " + "( '" + codigoTemp
							+ "' , '" + produtoTemp + "', '" + totalTemp + "', '" +  codigoProdTemp + "', '" + quantidadeTemp + "', '"
							+ categoriaTemp + "','" + marcaTemp + "', '" + descricaoTemp + "', '" + tipoPagamento + "', '" + dataSaidaString + "'"
							+ ")";

					lb.executeUpdate(sql);

				}
				
				sql = "DELETE VENDAPRODUTOTEMP WHERE CODIGO = " + codigoTemp;

				lb.executeUpdate(sql);

			} finally {
				if (lb != null)
					lb.closeAll();
			}

		} catch (Exception e) {
			Auxiliares.messagebox("ERRO", "Erro salvaInfoVenda\n" + e);
		}
	}
	
	public void calculaItensCarrinho() throws Exception {
		String sql = "";
		LitebasePack lb = null;
		ResultSet rs = null;

		try {

			try {

				lb = new LitebasePack();
				sql = "SELECT QUANTIDADE FROM VENDAPRODUTOTEMP ";

				rs = lb.executeQuery(sql);
				rs.beforeFirst();

				while (rs.next()) {

					quantidadeCarrinho += rs.getInt("QUANTIDADE");
				}
				
				lblQuantidade.setText("QUANTIDADE: " + Convert.toString(quantidadeCarrinho));

				sql = "SELECT VALOR FROM VENDAPRODUTOTEMP ";

				rs = lb.executeQuery(sql);
				rs.beforeFirst();

				while (rs.next()) {
					
					totalCarrinho += Convert.toDouble(rs.getString("VALOR"));					
				}
				
				lblTotal.setText("TOTAL: R$" + Convert.toCurrencyString(totalCarrinho, 2).replace(",", "."));

			} catch (Exception e) {
				Auxiliares.messagebox("ERRO", "Erro calculaItensCarrinho\n" + e);
			}

		} finally {
			if (lb != null) {
				lb.closeAll();
			}
		}

	}
	
}
