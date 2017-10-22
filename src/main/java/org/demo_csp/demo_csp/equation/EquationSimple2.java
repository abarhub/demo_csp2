package org.demo_csp.demo_csp.equation;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class EquationSimple2 extends Equation {

	/**
	 * somme = resultat [mod]
	 */

	private final List<Produit> somme;
	private final BigInteger resultat;
	private final BigInteger mod;

	public EquationSimple2(ContextVariables contextVariables, List<Produit> somme, BigInteger resultat, BigInteger mod) {
		super(contextVariables);
		Preconditions.checkNotNull(somme);
		Preconditions.checkArgument(!somme.isEmpty());
		Preconditions.checkNotNull(resultat);
		Preconditions.checkNotNull(mod);
		this.somme = ImmutableList.copyOf(somme);
		this.resultat = resultat;
		this.mod = mod;
	}

	public EquationSimple2(EquationSimple2 equationSimple, ContextVariables contextVariables) {
		super(contextVariables);
		Preconditions.checkNotNull(equationSimple);
		this.somme = ImmutableList.copyOf(equationSimple.somme);
		this.resultat = equationSimple.resultat;
		this.mod = equationSimple.mod;
	}


	public List<Produit> getSomme() {
		return somme;
	}

	public BigInteger getResultat() {
		return resultat;
	}

	public BigInteger getMod() {
		return mod;
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

			BigInteger res2 = res.add(resultat.negate()).remainder(mod);
			if (res2.compareTo(BigInteger.ZERO) == 0) {
				return EtatEquation.OK;
			} else {
				return EtatEquation.KO;
			}
		}
	}

	@Override
	public String toString() {
		return "EquationSimple2{" +
				"contextVariables=" + contextVariables +
				", somme=" + somme +
				", resultat=" + resultat +
				", mod=" + mod +
				'}';
	}
}
