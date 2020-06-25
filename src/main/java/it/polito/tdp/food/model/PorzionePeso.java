package it.polito.tdp.food.model;

public class PorzionePeso {
	
	private String porzione;
	private Integer peso;
	
	public PorzionePeso(String porzione, Integer peso) {
		this.porzione = porzione;
		this.peso = peso;
	}

	public String getPorzione() {
		return porzione;
	}

	public void setPorzione(String porzione) {
		this.porzione = porzione;
	}

	public Integer getPeso() {
		return peso;
	}

	public void setPeso(Integer peso) {
		this.peso = peso;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((peso == null) ? 0 : peso.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PorzionePeso other = (PorzionePeso) obj;
		if (peso == null) {
			if (other.peso != null)
				return false;
		} else if (!peso.equals(other.peso))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PorzionePeso [porzione=" + porzione + ", peso=" + peso + "]";
	}

}
