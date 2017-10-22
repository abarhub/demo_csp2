package org.demo_csp.demo_csp.equation;

import com.google.common.base.Preconditions;

import java.math.BigInteger;

public class ValeurVariable {

	private final Variable variable;
	private final BigInteger valeur;

	public ValeurVariable(Variable variable, BigInteger valeur) {
		Preconditions.checkNotNull(variable);
		Preconditions.checkNotNull(valeur);
		Preconditions.checkArgument(variable.getDomaine().contient(valeur));
		this.variable = variable;
		this.valeur = valeur;
	}

	public Variable getVariable() {
		return variable;
	}

	public BigInteger getValeur() {
		return valeur;
	}

	@Override
	public String toString() {
		return variable + "=" + valeur;
	}
}
