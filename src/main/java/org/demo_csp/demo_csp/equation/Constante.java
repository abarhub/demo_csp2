package org.demo_csp.demo_csp.equation;

import com.google.common.base.Preconditions;

import java.math.BigInteger;

public class Constante implements Valeur {

	private final BigInteger valeur;

	public Constante(BigInteger valeur) {
		Preconditions.checkNotNull(valeur);
		this.valeur = valeur;
	}

	public BigInteger getValeur() {
		return valeur;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Constante)) return false;

		Constante constante = (Constante) o;

		return valeur.equals(constante.valeur);
	}

	@Override
	public int hashCode() {
		return valeur.hashCode();
	}

	@Override
	public String toString() {
		return valeur.toString();
	}
}
