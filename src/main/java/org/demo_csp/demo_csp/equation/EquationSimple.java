package org.demo_csp.demo_csp.equation;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import java.math.BigInteger;
import java.util.List;

public class EquationSimple extends Equation {

	private final List<Produit> somme;
	private final BigInteger resultat;

	public EquationSimple(ContextVariables contextVariables, List<Produit> somme, BigInteger resultat) {
		super(contextVariables);
		Preconditions.checkNotNull(contextVariables);
		Preconditions.checkNotNull(somme);
		Preconditions.checkArgument(!somme.isEmpty());
		Preconditions.checkNotNull(resultat);
		this.somme = ImmutableList.copyOf(somme);
		this.resultat = resultat;
	}

	public EquationSimple(EquationSimple equationSimple, ContextVariables contextVariables) {
		super(contextVariables);
		Preconditions.checkNotNull(contextVariables);
		Preconditions.checkNotNull(equationSimple);
		this.somme = ImmutableList.copyOf(equationSimple.somme);
		this.resultat = equationSimple.resultat;
	}

	public List<Produit> getSomme() {
		return somme;
	}

	public BigInteger getResultat() {
		return resultat;
	}

	@Override
	public String toString() {
		return "EquationSimple{" +
				"contextVariables=" + contextVariables +
				", somme=" + somme +
				", resultat=" + resultat +
				'}';
	}

	@Override
	public List<Variable> getVariables() {
		return null;
	}

	@Override
	public EtatEquation etatEquation() {
		return null;
	}
}
