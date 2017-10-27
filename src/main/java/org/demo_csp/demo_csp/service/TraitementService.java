package org.demo_csp.demo_csp.service;

import org.demo_csp.demo_csp.equation.BuilderEquation;
import org.demo_csp.demo_csp.equation.Equation;
import org.demo_csp.demo_csp.equation.Resolution;
import org.demo_csp.demo_csp.equation.Resolution2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Random;

@Service
public class TraitementService implements CommandLineRunner {

	public static final Logger LOGGER = LoggerFactory.getLogger(TraitementService.class);

	private Random random = new Random(System.currentTimeMillis());

	@Autowired
	private CalculNombrePremierService calculNombrePremierService;

	@Autowired
	private ConstruitContraintesService construitContraintesService;

	@Override
	public void run(String... strings) throws Exception {
		LOGGER.info("run ...");
		exec();
		LOGGER.info("run ok");
	}

	private void exec() throws Exception {
		test1();
		test2();
		//test3();
		test4();
	}

	private void test4() {
		BuilderEquation builderEquation = new BuilderEquation();

		long val;
		val = 12345L;
		//val=12345678910L;
		//val = 12;
		val = 123;

		BigInteger max = BigInteger.valueOf(val);
		List<Equation> res = builderEquation.buildEquationSimple(max);
		LOGGER.info("res={}", res);

		Resolution2 resolution = new Resolution2();
		LOGGER.info("resolution ...");
		resolution.resolution(res);
		LOGGER.info("resolution ok");
	}

	private void test3() {
		BuilderEquation builderEquation = new BuilderEquation();

		long val;
		val = 12345L;
		//val=12345678910L;
		val = 12;

		BigInteger max = BigInteger.valueOf(val);
		List<Equation> res = builderEquation.buildEquationSimple(max);
		LOGGER.info("res={}", res);

		Resolution resolution = new Resolution();
		LOGGER.info("resolution ...");
		resolution.resolution(res);
		LOGGER.info("resolution ok");
	}

	private void test2() {
		final int maxInt = 300;
		BigInteger max = BigInteger.valueOf(maxInt);
		List<BigInteger> liste = calculNombrePremierService.calcul(max);
		int n1 = getPos(liste);
		int n2 = getPos(liste);
		BigInteger i1 = liste.get(n1);
		BigInteger i2 = liste.get(n2);
		BigInteger mult = i1.multiply(i2);
		LOGGER.info("mult {} * {} = {}", i1, i2, mult);
		construitContraintesService.calcul(mult);
		//List<Integer> listeChiffres = decoupe(mult);
		//LOGGER.info("listeChiffres = {}", listeChiffres);
	}

//	private List<Integer> decoupe(BigInteger mult) {
//		List<Integer> list = new ArrayList<>();
//		BigInteger tmp = BigInteger.TEN;
//		BigInteger val = mult;
//		while (val.compareTo(BigInteger.ZERO) > 0) {
//			BigInteger[] res = val.divideAndRemainder(tmp);
//			list.add(0,res[1].intValueExact());
//			val = res[0];
//		}
//		return list;
//	}

	private int getPos(List<BigInteger> liste) {
		int n = random.nextInt(liste.size());
		BigInteger b = liste.get(n);
		if (b.compareTo(BigInteger.TEN) < 0) {
			do {
				n = random.nextInt(liste.size());
				b = liste.get(n);
			} while (b.compareTo(BigInteger.TEN) < 0);
		}
		return n;
	}

	private void test1() throws Exception {
		BigInteger max = BigInteger.valueOf(300);
		List<BigInteger> liste = calculNombrePremierService.calcul(max);
		LOGGER.info("liste des nombre premiers inferieur à {} : ", max);
		for (BigInteger i : liste) {
			LOGGER.info("{}", i);
		}
	}
}
