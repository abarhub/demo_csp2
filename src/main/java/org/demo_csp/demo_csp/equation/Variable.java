package org.demo_csp.demo_csp.equation;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;

public class Variable implements Valeur {

	private final String nom;
	private final Domaine domaine;

	public Variable(String nom, Domaine domaine) {
		Preconditions.checkNotNull(nom);
		Preconditions.checkArgument(StringUtils.isNotBlank(nom));
		Preconditions.checkNotNull(domaine);
		this.nom = nom;
		this.domaine = domaine;
	}

	public String getNom() {
		return nom;
	}

	public Domaine getDomaine() {
		return domaine;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Variable)) return false;

		Variable variable = (Variable) o;

		return nom.equals(variable.nom);
	}

	@Override
	public int hashCode() {
		return nom.hashCode();
	}

	@Override
	public String toString() {
		return nom;
	}
}
