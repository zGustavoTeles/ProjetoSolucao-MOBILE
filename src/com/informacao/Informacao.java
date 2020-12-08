package com.informacao;

import com.auxiliares.Auxiliares;
import totalcross.ui.Label;
import totalcross.ui.MultiEdit;
import totalcross.ui.dialog.MessageBox;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.Button;
import totalcross.ui.gfx.Color;

public class Informacao extends totalcross.ui.Window{
	
    private Label 							lblInformacao;
    private Label							lblVersao;
    private Label							lblSobre;
    private Label							lblCopyright;
    private MultiEdit						editInformacao;
	private Button 						btnVoltar;
	private int 							linha = 6;
	private int							    dist = 1;
	
	public Informacao(){
		 setBackColor(0x003366);
		 initUI();
	}
	
	public void initUI() {
		
		try {

			lblInformacao = new Label("INFORMAC�ES: ");
			add(lblInformacao);
			lblInformacao.setRect(CENTER, TOP, PREFERRED, PREFERRED);
			lblInformacao.setBackColor(0x003366);
	        lblInformacao.setForeColor(Color.WHITE);
	        
	        editInformacao = (new MultiEdit("", linha, dist));
			add(editInformacao);			
			editInformacao.setMaxLength(500);
			editInformacao.setRect(LEFT, AFTER + 30,FILL + 5, height - 200, lblInformacao);
			editInformacao.setBackColor(Color.WHITE);
			editInformacao.setForeColor(0x003366);
			editInformacao.setText("Um controle de estoque efetivo � hoje um fator determinante para o sucesso de uma empresa. "
					+ " Por representar uma parte importante dos ativos � necess�rio que se tenha um sistema que supra as "
					+ " necessidades de controle de estoque da empresa. A distribuidora Umbrella do ramo de bebidaspossui "
					+ " uma variedade de mais de 50 tipos de produtos em seu estoque, sem nenhum controle r�gido. O objetivo "
					+ " deste trabalho � identificar os diferentes fatores que influenciam o estoque, desenvolvendo um sistema "
					+ " de controle para a distribuidora. .As teorias de base fundamentadas de cita��es de diversos autores ajudou "
					+ " abranger uma �rea mais ampla de te�ricos e tamb�m compondo os tr�s pilares que ser� nossa base de sustenta��o "
					+ " do projeto. O controle e gest�o de estoque, que ter� uma grande import�ncia para nosso cliente e tamb�m na constru��o "
					+ " do sistema, o estoque de seguran�a, que prevenir� nosso cliente de futuros problemas de corte no fornecimento. "
					+ " Por fim o gerenciamento de estoque, que ajudar� o mesmo a organizar melhor o seu empreendimento.O resultado da "
					+ " an�lise mostrou uma utiliza��o incorreta dos recursos da distribuidora, tanto de espa�o f�sico, quanto de datas de "
					+ " compras e tipos de produtos a serem comprados em cada momento. Baseados na an�lise dos dados e da situa��o s�o esbo�ados "
					+ " algumas recomenda��es. ");
			editInformacao.setEditable(false);
			
			
			lblCopyright = new Label(Auxiliares.DESCRICAO);
			add(lblCopyright);
			lblCopyright.setRect(LEFT, AFTER + 5, PREFERRED, PREFERRED, editInformacao);
			lblCopyright.setBackColor(0x003366);
			lblCopyright.setForeColor(Color.WHITE);  
			
			lblSobre = new Label("Sobre: " + Auxiliares.NOMESISTEMA);
			add(lblSobre);
			lblSobre.setRect(LEFT, AFTER, PREFERRED, PREFERRED, lblCopyright);
			lblSobre.setBackColor(0x003366);
			lblSobre.setForeColor(Color.WHITE);
			
			lblVersao = new Label("Vers�o: " + Auxiliares.VERSAO);
			add(lblVersao);
			lblVersao.setRect(LEFT, AFTER, PREFERRED, PREFERRED, lblSobre);
			lblVersao.setBackColor(0x003366);
			lblVersao.setForeColor(Color.WHITE);  

			btnVoltar = new Button("VOLTAR");
	        add(btnVoltar);
	        btnVoltar.setRect(RIGHT, BOTTOM, SCREENSIZE - 4, PREFERRED + 15);
	        btnVoltar.setBackColor(0x003366);
	        btnVoltar.setForeColor(Color.WHITE);
	        
	        reposition();
	        
		} catch (Exception e) {
			Auxiliares.messagebox("ERRO","Erro ao construir a tela informacao\n" + e);

		}
		
	}
	public void onEvent(Event evt) {
		try {
			switch (evt.type) {
			case ControlEvent.PRESSED:

				if (evt.target == btnVoltar) {
					unpop();
				}
			}
		}catch (Exception e) {
			Auxiliares.messagebox("ERRO", "Erro na valida��o da tela informacao\n" + e);
			}
			
			
		}
	
}
