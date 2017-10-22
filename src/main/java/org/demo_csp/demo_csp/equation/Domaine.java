package org.demo_csp.demo_csp.equation;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSortedSet;

import java.math.BigInteger;
import java.util.Set;
import java.util.TreeSet;

public class Domaine {

	private final Set<BigInteger> valeurs;

	public Domaine(Set<BigInteger> valeurs) {
		Preconditions.checkNotNull(valeurs);
		Preconditions.checkArgument(!valeurs.isEmpty());
		this.valeurs = ImmutableSortedSet.copyOf(valeurs);
	}

	public Domaine(BigInteger min, BigInteger max) {
		Preconditions.checkNotNull(min);
		Preconditions.checkNotNull(max);
		Preconditions.checkArgument(min.compareTo(max) <= 0);
		BigInteger val = min;
		Set<BigInteger> set = new TreeSet<>();
		do {
			set.add(val);
			val = val.add(BigInteger.ONE);
		} while (val.compareTo(max) <= 0);
		valeurs = ImmutableSortedSet.copyOf(set);
	}

	public Domaine(int min, int max) {
		this(BigInteger.valueOf(min), BigInteger.valueOf(max));
	}

	public boolean contient(BigInteger val) {
		Preconditions.checkNotNull(val);
		return valeurs.contains(val);
	}

	public Set<BigInteger> getValeurs() {
		return valeurs;
	}

	@Override
	public String toString() {
		return "(" + valeurs + ')';
	}
}
