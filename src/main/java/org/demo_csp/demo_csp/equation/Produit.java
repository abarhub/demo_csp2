package org.demo_csp.demo_csp.equation;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import java.util.List;

public class Produit {

	private final List<Valeur> listeValeurs;

	public Produit(List<? extends Valeur> listeValeurs) {
		Preconditions.checkNotNull(listeValeurs);
		Preconditions.checkArgument(!listeValeurs.isEmpty());
		this.listeValeurs = ImmutableList.copyOf(listeValeurs);
	}

	public List<Valeur> getListeValeurs() {
		return listeValeurs;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		for (Valeur v : listeValeurs) {
			if (s.length() > 0)
				s.append("*");
			s.append(v);
		}
		return s.toString();
	}
}
