package org.demo_csp.demo_csp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CalculNombrePremierService {

	public static final Logger LOGGER = LoggerFactory.getLogger(CalculNombrePremierService.class);

	public List<BigInteger> calcul(BigInteger max) {
		final BigInteger min = BigInteger.valueOf(2);
		final List<BigInteger> res = new ArrayList<>();

		final Map<BigInteger, Boolean> map_premier = initMapPremier(max, min);

		for (BigInteger i = min; i.compareTo(max) <= 0; i = i.add(BigInteger.ONE)) {
			LOGGER.debug("test {}", i);
			if (i.equals(BigInteger.valueOf(2))) {
				setPremier(res, map_premier, i);
				LOGGER.trace("premier");
			} else {
				boolean premier = true;

				for (BigInteger j = min; j.compareTo(i) < 0; j = j.add(BigInteger.ONE)) {
					if (!map_premier.get(j)) {
						// j n'est pas premier => on le saute
					} else if (i.remainder(j).compareTo(BigInteger.ZERO) == 0) {
						premier = false;
						break;
					}
				}

				if (premier) {
					setPremier(res, map_premier, i);
					LOGGER.trace("premier");
				} else {
					LOGGER.trace("pas premier");
				}
			}
		}

		return res;
	}

	private void setPremier(List<BigInteger> res, Map<BigInteger, Boolean> map_premier, BigInteger i) {
		res.add(i);
		map_premier.put(i, true);
	}

	private Map<BigInteger, Boolean> initMapPremier(BigInteger max, BigInteger min) {
		Map<BigInteger, Boolean> map_premier = new HashMap<>();
		for (BigInteger i = min; i.compareTo(max) <= 0; i = i.add(BigInteger.ONE)) {
			map_premier.put(i, false);
		}
		return map_premier;
	}
}
