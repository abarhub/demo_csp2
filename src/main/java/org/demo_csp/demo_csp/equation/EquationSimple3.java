package org.demo_csp.demo_csp.equation;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class EquationSimple3 extends Equation {

	/**
	 * somme = resultat + base*reste
	 */

	private final List<Produit> somme;
	private final BigInteger resultat;
	private final BigInteger base;
	private final Variable reste;

	public EquationSimple3(ContextVariables contextVariables, List<Produit> somme, BigInteger resultat, BigInteger base, Variable reste) {
		super(contextVariables);
		Preconditions.checkNotNull(somme);
		Preconditions.checkArgument(!somme.isEmpty());
		Preconditions.checkNotNull(resultat);
		Preconditions.checkNotNull(base);
		Preconditions.checkNotNull(reste);
		this.somme = ImmutableList.copyOf(somme);
		this.resultat = resultat;
		this.base = base;
		this.reste = reste;
	}

	public EquationSimple3(EquationSimple3 equationSimple, ContextVariables contextVariables) {
		super(contextVariables);
		Preconditions.checkNotNull(equationSimple);
		this.somme = ImmutableList.copyOf(equationSimple.somme);
		this.resultat = equationSimple.resultat;
		this.base = equationSimple.base;
		this.reste = equationSimple.reste;
	}

	public List<Produit> getSomme() {
		return somme;
	}

	public BigInteger getResultat() {
		return resultat;
	}

	@Override
	public List<Variable> getVariables() {
		List<Variable> liste = new ArrayList<>();
		if (somme != null) {
			for (Produit p : somme) {
				for (Valeur v : p.getListeValeurs()) {
					if (v instanceof Variable) {
						Variable v2 = (Variable) v;
						if (!liste.contains(v2)) {
							liste.add(v2);
						}
					}
				}
			}
		}
		if (!liste.contains(reste)) {
			liste.add(reste);
		}
		return liste;
	}

	@Override
	public EtatEquation etatEquation() {
		List<Variable> liste = getVariables();

		if (liste.isEmpty()) {
			return EtatEquation.OK;
		} else {
			for (Variable v : liste) {
				BigInteger val = contextVariables.getValeurVariable(v);
				if (val == null) {
					// variable non definie
					return EtatEquation.VARIABLE_NON_DEFINI;
				}
			}

			BigInteger res = BigInteger.ZERO;
			for (Produit p : somme) {

				if (!p.getListeValeurs().isEmpty()) {
					BigInteger prod = BigInteger.ONE;
					for (Valeur v : p.getListeValeurs()) {
						if (v instanceof Constante) {
							prod = prod.multiply(((Constante) v).getValeur());
						} else if (v instanceof Variable) {
							BigInteger val = contextVariables.getValeurVariable((Variable) v);
							prod = prod.multiply(val);
						}
					}

					res = res.add(prod);
				}
			}

			BigInteger res3 = resultat.add(base.multiply(contextVariables.getValeurVariable(reste)));


			BigInteger res2 = res.add(res3.negate());
			if (res2.compareTo(BigInteger.ZERO) == 0) {
				return EtatEquation.OK;
			} else {
				return EtatEquation.KO;
			}
		}
	}

	@Override
	public String toString() {
		return "EquationSimple3{" +
				"contextVariables=" + contextVariables +
				", somme=" + somme +
				", resultat=" + resultat +
				", base=" + base +
				", reste=" + reste +
				'}';
	}
}
