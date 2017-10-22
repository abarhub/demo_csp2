package org.demo_csp.demo_csp.equation;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.*;

public class Resolution {

	public static final Logger LOGGER = LoggerFactory.getLogger(Resolution.class);

	public void resolution(List<Equation> list) {

		List<Variable> listeVarGlobal = new ArrayList<>();

		for (Equation e : list) {

			LOGGER.info("equation : {}", e);

			if (e instanceof EquationSimple2
					|| e instanceof EquationSimple3) {
				listeVarGlobal = resout0(e, listeVarGlobal);
			} else {
				Preconditions.checkArgument(false, "e invalide : " + e);
			}

//			if (e instanceof EquationSimple2) {
//				resout((EquationSimple2) e);
//			} else if (e instanceof EquationSimple3) {
//				resout((EquationSimple3) e);
//			} else {
//				Preconditions.checkArgument(false, "e invalide : " + e);
//			}
			//break;
		}

		LOGGER.info("resultat : ");

		for (Variable v : listeVarGlobal) {
			LOGGER.info("{} : {}", v.getNom(), v.getDomaine());
		}

	}

	private List<Variable> resout0(Equation equation, List<Variable> listeVarGlobal) {
		List<Variable> listeVar = new ArrayList<>();

		for (Variable v : equation.getVariables()) {
			if (listeVarGlobal.contains(v)) {
				int i = listeVarGlobal.indexOf(v);
				listeVar.add(listeVarGlobal.get(i));
			} else {
				listeVar.add(v);
			}
		}

		LOGGER.info("listeVar={}", listeVar);

		List<ContextVariables> listeResultats = new ArrayList<>();

		evalue0(equation, listeVar, listeResultats);

		//evalue0(equation, listeVar, listeResultats);

		LOGGER.info("res ={}", listeResultats);
		LOGGER.info("nb res ={}", listeResultats.size());

		Map<Variable, Set<BigInteger>> map = new HashMap<>();

		for (ContextVariables contextVariables : listeResultats) {

			for (Variable var : contextVariables.getListeVariableAffectee()) {
				if (!map.containsKey(var)) {
					map.put(var, new HashSet<>());
				}
				map.get(var).add(contextVariables.getValeurVariable(var));
			}
		}

		LOGGER.info("map ={}", map);

		List<Variable> listeVarResultat = new ArrayList<>();
		for (Map.Entry<Variable, Set<BigInteger>> tmp : map.entrySet()) {
			Variable v = new Variable(tmp.getKey().getNom(), new Domaine(tmp.getValue()));
			listeVarResultat.add(v);
		}

		LOGGER.info("listeVarResultat1 = {}", listeVarResultat);

		for (Variable v : listeVarGlobal) {
			if (!listeVarResultat.contains(v)) {
				listeVarResultat.add(v);
			}
		}

		LOGGER.info("listeVarResultat2 = {}", listeVarResultat);

		return listeVarResultat;
	}


	private void evalue0(Equation equation, List<Variable> listeVar, List<ContextVariables> listeResultats) {
		if (listeVar.isEmpty()) {
			EtatEquation etat = equation.etatEquation();
			if (etat == EtatEquation.OK) {
				//LOGGER.info("trouve={}", equation);
				listeResultats.add(equation.getContextVariables());
			}
		} else {
			Variable v = listeVar.get(0);
			//LOGGER.info("var={}", v);
			List<Variable> listeVar2 = Lists.newArrayList(listeVar);
			listeVar2.remove(0);
			for (BigInteger val : v.getDomaine().getValeurs()) {
				//LOGGER.info("val={}", val);
				ValeurVariable vv = new ValeurVariable(v, val);
				ContextVariables contextVariables = new ContextVariables(equation.getContextVariables(), Lists.newArrayList(vv));
				//EquationSimple3 eq = new EquationSimple3(equation, contextVariables);
				Equation eq = copie(equation, contextVariables);
				evalue0(eq, listeVar2, listeResultats);
			}
		}
	}

	private Equation copie(Equation equation, ContextVariables contextVariables) {
		if (equation instanceof EquationSimple2) {
			//evalue0(equation, listeVar, listeResultats);
			return new EquationSimple2((EquationSimple2) equation, contextVariables);
		} else if (equation instanceof EquationSimple3) {
			//evalue0(equation, listeVar, listeResultats);
			return new EquationSimple3((EquationSimple3) equation, contextVariables);
		} else {
			Preconditions.checkArgument(false, "e invalide : " + equation);
			return null;
		}
	}

	private void resout(EquationSimple3 equation) {
		List<Variable> listeVar = equation.getVariables();

		LOGGER.info("listeVar={}", listeVar);

		List<ContextVariables> listeResultats = new ArrayList<>();

		evalue(equation, listeVar, listeResultats);

		LOGGER.info("nb res ={}", listeResultats.size());
	}

	private void evalue(EquationSimple3 equation, List<Variable> listeVar, List<ContextVariables> listeResultats) {
		if (listeVar.isEmpty()) {
			EtatEquation etat = equation.etatEquation();
			if (etat == EtatEquation.OK) {
				//LOGGER.info("trouve={}", equation);
				listeResultats.add(equation.getContextVariables());
			}
		} else {
			Variable v = listeVar.get(0);
			//LOGGER.info("var={}", v);
			List<Variable> listeVar2 = Lists.newArrayList(listeVar);
			listeVar2.remove(0);
			for (BigInteger val : v.getDomaine().getValeurs()) {
				//LOGGER.info("val={}", val);
				ValeurVariable vv = new ValeurVariable(v, val);
				ContextVariables contextVariables = new ContextVariables(equation.getContextVariables(), Lists.newArrayList(vv));
				EquationSimple3 eq = new EquationSimple3(equation, contextVariables);
				evalue(eq, listeVar2, listeResultats);
			}
		}
	}

	private void resout(EquationSimple2 equation) {
		List<Variable> listeVar = equation.getVariables();

		LOGGER.info("listeVar={}", listeVar);

		List<ContextVariables> listeResultats = new ArrayList<>();

		evalue(equation, listeVar, listeResultats);

		LOGGER.info("nb res ={}", listeResultats.size());
	}

	private void evalue(EquationSimple2 equation, List<Variable> listeVar, List<ContextVariables> listeResultats) {
		if (listeVar.isEmpty()) {
			EtatEquation etat = equation.etatEquation();
			if (etat == EtatEquation.OK) {
				//LOGGER.info("trouve={}", equation);
				listeResultats.add(equation.contextVariables);
			}
		} else {
			Variable v = listeVar.get(0);
			//LOGGER.info("var={}", v);
			List<Variable> listeVar2 = Lists.newArrayList(listeVar);
			listeVar2.remove(0);
			for (BigInteger val : v.getDomaine().getValeurs()) {
				//LOGGER.info("val={}", val);
				ValeurVariable vv = new ValeurVariable(v, val);
				ContextVariables contextVariables = new ContextVariables(equation.getContextVariables(), Lists.newArrayList(vv));
				EquationSimple2 eq = new EquationSimple2(equation, contextVariables);
				evalue(eq, listeVar2, listeResultats);
			}
		}
	}
}
