package org.demo_csp.demo_csp.equation;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

public class BuilderEquation {

	public static final Logger LOGGER = LoggerFactory.getLogger(BuilderEquation.class);

	public BuilderEquation() {
	}

	public List<Equation> buildEquationSimple(BigInteger valeurRef) {
		Preconditions.checkNotNull(valeurRef);
		Preconditions.checkArgument(valeurRef.signum() > 0);

		List<Equation> res = new ArrayList<>();

		int base = 10;

		List<Integer> listeChiffres = decoupe(valeurRef);

		LOGGER.info("listeChiffres = {}", listeChiffres);

		int nbChiffres = listeChiffres.size();
		int nbX, nbY;
		if (nbChiffres % 2 == 0) {
			nbX = nbY = nbChiffres / 2;
		} else {
			nbY = nbChiffres / 2;
			nbX = nbY + 1;
		}

		Domaine domaine = new Domaine(BigInteger.ZERO, BigInteger.valueOf(base - 1));

		List<Variable> listeX = new ArrayList<>();
		for (int i = 0; i < nbX; i++) {
			Variable x = new Variable("X" + i, domaine);
			listeX.add(x);
		}

		List<Variable> listeY = new ArrayList<>();
		for (int i = 0; i < nbY; i++) {
			Variable y = new Variable("Y" + i, domaine);
			listeY.add(y);
		}

		List<Variable> listeR = new ArrayList<>();
		for (int i = 0; i < nbX + nbY; i++) {
			Variable r = new Variable("R" + i, domaine);
			listeR.add(r);
		}

		List<Variable> listeVar = Lists.newArrayList(listeX);
		listeVar.addAll(listeY);
		listeVar.addAll(listeR);
		ContextVariables contextVariables = new ContextVariables(listeVar);

		Map<Integer, List<Produit>> map = new TreeMap<>();

		for (int z = 0; z <= listeX.size() + listeY.size(); z++) {
			for (int i = 0; i < listeX.size(); i++) {
				for (int j = 0; j < listeY.size(); j++) {
					if (i + j == z) {
						LOGGER.info("{} * {}", listeX.get(i), listeY.get(j));
						if (!map.containsKey(z)) {
							map.put(z, new ArrayList<>());
						}
						List<Variable> tmp = new ArrayList<>();
						tmp.add(listeX.get(i));
						tmp.add(listeY.get(j));
						Produit produit = new Produit(tmp);
						map.get(z).add(produit);
					}
				}
			}
		}

		LOGGER.info("map={}", map);

		AtomicInteger compteur = new AtomicInteger(1);

		for (int i = 0; i < listeX.size() + listeY.size(); i++) {
			List<Produit> liste = map.get(i);


			if (liste != null && !liste.isEmpty()) {

				int val = 0;

				if (i < nbChiffres) {
					val = listeChiffres.get(i);
				}

				EquationSimple2 equationSimple = new EquationSimple2(contextVariables, liste, BigInteger.valueOf(val),BigInteger.valueOf(base));

				res.add(equationSimple);

				EquationSimple3 equationSimple3 = new EquationSimple3(contextVariables, liste, BigInteger.valueOf(val),BigInteger.valueOf(base),listeR.get((i)));

				res.add(equationSimple3);

			}
		}

		//LOGGER.info("res={}", res);

		return res;

	}

	private List<Integer> decoupe(BigInteger mult) {
		List<Integer> list = new ArrayList<>();
		BigInteger tmp = BigInteger.TEN;
		BigInteger val = mult;
		while (val.compareTo(BigInteger.ZERO) > 0) {
			BigInteger[] res = val.divideAndRemainder(tmp);
			list.add(0, res[1].intValueExact());
			val = res[0];
		}
		return list;
	}

}
