package xml;

public class Transform {
	
	public final static String ALGARVE = "Algarve";
	public final static String ALENTEJO = "Alentejo";
	public final static String CENTRO = "Centro";
	public final static String LISBOA = "Lisboa";
	public final static String NORTE = "Norte";
	
	
	public final static String INFECOES = "Infecoes";
	public final static String INTERNAMENTOS = "Internamentos";
	public final static String TESTES = "Testes";
	
	
	public final static String IGUAL = "=";
	public final static String DIFERENTE = "!=";
	public final static String MENOR = "<";
	public final static String MAIOR = ">";
	public final static String MENORIGUAL = "<=";
	public final static String MAIORIGUAL = ">=";
	
	
	private String regiao;
	private String tipo;
	private String operador;
	private int valor;
	
	
	
	public Transform(String s) {
		String[] splited = s.split(" ");
		for(int i =0; i<splited.length;i++) {
			if(splited[i].equals(ALGARVE))
				regiao=ALGARVE;
			if(splited[i].equals(ALENTEJO))
				regiao=ALENTEJO;
			if(splited[i].equals(CENTRO))
				regiao=CENTRO;
			if(splited[i].equals(LISBOA))
				regiao=LISBOA;
			if(splited[i].equals(NORTE))
				regiao=NORTE;
			if(splited[i].equals(TESTES))
				tipo=TESTES;
			if(splited[i].equals(INFECOES))
				tipo=INFECOES;
			if(splited[i].equals(INTERNAMENTOS))
				tipo=INTERNAMENTOS;
			if(splited[i].equals(IGUAL))
				operador=IGUAL;
			if(splited[i].equals(DIFERENTE))
				operador=DIFERENTE;
			if(splited[i].equals(MAIOR))
				operador=MAIOR;
			if(splited[i].equals(MENOR))
				operador=MENOR;
			if(splited[i].equals(MAIORIGUAL))
				operador=MAIORIGUAL;
			if(splited[i].equals(MENORIGUAL))
				operador=MENORIGUAL;
			if(isNumeric(splited[i]))
				valor=Integer.parseInt(splited[i]);
		}
	}

	
	public void compare (int i, Main m) {
		
		if(operador != null) {
			if(operador.equals(IGUAL))
				if(valor==i)
					m.add(tipo +"  "+ regiao +" equivalem a "+ valor );
				else 
					m.add(tipo +"  "+ regiao +" são diferentes de "+ valor);
			if(operador.equals(DIFERENTE))
				if(valor!=i)
					m.add(tipo +"  "+ regiao +" são diferentes de "+ valor );
				else 
					m.add(tipo +"  "+ regiao +" são igual a "+ valor);
			if(operador.equals(MENOR))
				if(valor>i)
					m.add(tipo +"  "+ regiao +" são menores que "+ valor );
				else
					m.add(tipo +"  "+ regiao +" são maiores ou iguais que "+ valor );
			if(operador.equals(MAIOR))
				if(valor<i)
					m.add(tipo +"  "+ regiao +" são maiores que "+ valor );
				else
					m.add(tipo +"  "+ regiao +" são menores ou iguais que "+ valor );
			if(operador.equals(MAIORIGUAL))
				if(valor<=i)
					m.add(tipo +"  "+ regiao +" são maior ou igual que "+ valor );
				else 
					m.add(tipo +"  "+ regiao +" são menores que "+ valor );
			if(operador.equals(MENORIGUAL))
				if(valor>=i)
					m.add(tipo +"  "+ regiao +" são menor ou igual que "+ valor );
				else
					m.add(tipo +"  "+ regiao +" são maiores que "+ valor );
		}
	}



	public static boolean isNumeric(String strNum) {
		if (strNum == null) {
			return false;
		}
		try {
			int d = Integer.parseInt(strNum);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	public String getRegiao() {
		return regiao;
	}


	public String getTipo() {
		return tipo;
	}



	public String getOperador() {
		return operador;
	}
}
