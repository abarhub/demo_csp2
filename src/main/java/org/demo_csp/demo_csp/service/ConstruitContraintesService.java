package org.demo_csp.demo_csp.service;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ConstruitContraintesService {

	public static final Logger LOGGER = LoggerFactory.getLogger(ConstruitContraintesService.class);

	public void calcul(BigInteger valeur) {
		List<Integer> listeChiffres = decoupe(valeur);
		LOGGER.info("listeChiffres = {}", listeChiffres);

		int nbChiffres = listeChiffres.size();
		int nbX, nbY;
		if (nbChiffres % 2 == 0) {
			nbX = nbY = nbChiffres / 2;
		} else {
			nbX = nbChiffres / 2;
			nbY = nbX + 1;
		}

		Model model = new Model("my first problem");

		List<IntVar> listeX = new ArrayList<>();
		for (int i = 0; i < nbX; i++) {
			IntVar x = model.intVar("X" + i, 0, 9);
			listeX.add(x);
		}

		List<IntVar> listeY = new ArrayList<>();
		for (int i = 0; i < nbY; i++) {
			IntVar y = model.intVar("Y" + i, 0, 9);
			listeY.add(y);
		}

		Map<Integer, List<List<IntVar>>> map = new TreeMap<>();

		for (int z = 0; z <= listeX.size() + listeY.size(); z++) {
			for (int i = 0; i < listeX.size(); i++) {
				for (int j = 0; j < listeY.size(); j++) {
					LOGGER.info("{} * {}", listeX.get(i), listeY.get(j));
					if (!map.containsKey(z)) {
						map.put(z, new ArrayList<>());
					}
					List<IntVar> tmp = new ArrayList<>();
					tmp.add(listeX.get(i));
					tmp.add(listeY.get(j));
					map.get(z).add(tmp);
				}
			}
		}

		LOGGER.info("res={}", map);

		//model.arithm(x, "+", y, "<", 5).post();

		AtomicInteger compteur = new AtomicInteger(1);

		for (int i = 0; i < listeX.size() + listeY.size(); i++) {
			List<List<IntVar>> liste = map.get(i);


			if (!liste.isEmpty()) {
				List<IntVar> listeSum = new ArrayList<>();

				for (List<IntVar> liste2 : liste) {
					IntVar x = liste2.get(0);
					IntVar y = liste2.get(1);
					IntVar z = model.intVar("Z" + i + "_" + compteur.getAndIncrement(), 0, 9);
					model.times(x, y, z).post();
					listeSum.add(z);
				}

				int val = 0;

				if (i < nbChiffres) {
					val = listeChiffres.get(i);
				}

				model.sum(listeSum.toArray(new IntVar[0]), "=", val);
			}
		}

		model.getSolver().solve();


		model.getSolver().printStatistics();
		for (int i = 0; i < model.getNbVars(); i++) {
			LOGGER.info("resulat = {}", model.getVar(i));
		}
	}

	protected List<Integer> decoupe(BigInteger mult) {
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
