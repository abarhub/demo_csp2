package org.demo_csp.demo_csp.equation;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


public class Resolution2 {

	public static final Logger LOGGER = LoggerFactory.getLogger(Resolution2.class);

	public void resolution(List<Equation> list) {

		Equation eq = list.get(0);

		LOGGER.info("equation : {}", eq);

		createObservable(eq)
				.subscribe(contextVariables -> {
					evalue2(contextVariables, list.subList(1, list.size()), 1, list.size());
				});
	}

	private void evalue2(ContextVariables contextVariables, List<Equation> list, int niveau, int niveauMax) {

		if (!list.isEmpty()) {
			Equation eq = list.get(0);

			Equation eq2 = copie(eq, contextVariables);

			LOGGER.info("equation bis {}/{} : {}", niveau, niveauMax, eq2);

			eq2 = simplifie(eq2);

			LOGGER.info("equation simplifie : {}", eq2);

			createObservable(eq2)
					.subscribe(contextVariables2 -> {
						LOGGER.info("res ({}) : {}", niveau, contextVariables2.toString());
						evalue2(contextVariables2, list.subList(1, list.size()), niveau + 1, niveauMax);
					});
		}
	}

	private Observable<ContextVariables> createObservable(Equation equation) {

		Observable<ContextVariables> obs = Observable.create(s -> {
			resout(equation, s);

			s.onComplete();
		});

		return obs;
	}

	private void resout(Equation equation, ObservableEmitter<ContextVariables> obs) {
		List<Variable> listeVar = new ArrayList<>();

		for (Variable v : equation.getVariables()) {
			listeVar.add(v);
		}

		//LOGGER.info("listeVar={}", listeVar);

		evalue(equation, listeVar, obs);

	}


	private void evalue(Equation equation, List<Variable> listeVar, ObservableEmitter<ContextVariables> obs) {
		if (listeVar.isEmpty()) {
			EtatEquation etat = equation.etatEquation();
			if (etat == EtatEquation.OK) {
				//LOGGER.info("trouve={}", equation);
				obs.onNext(equation.getContextVariables());
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
				evalue(eq, listeVar2, obs);
			}
		}
	}

	private Equation copie(Equation equation, ContextVariables contextVariables) {
		if (equation instanceof EquationSimple2) {
			return new EquationSimple2((EquationSimple2) equation, contextVariables);
		} else if (equation instanceof EquationSimple3) {
			return new EquationSimple3((EquationSimple3) equation, contextVariables);
		} else {
			Preconditions.checkArgument(false, "e invalide : " + equation);
			return null;
		}
	}

	private Equation simplifie(Equation equation) {
		if (equation instanceof EquationSimple2) {
			return simplifieEq((EquationSimple2) equation);
		} else if (equation instanceof EquationSimple3) {
			return simplifieEq((EquationSimple3) equation);
		} else {
			Preconditions.checkArgument(false, "e invalide : " + equation);
			return null;
		}
	}

	private EquationSimple2 simplifieEq(EquationSimple2 equation) {
		List<Produit> liste = equation.getSomme();
		List<Produit> liste2 = new ArrayList<>();
		for (int i = 0; i < liste.size(); i++) {
			Produit p = liste.get(i);

			List<Valeur> liste3 = new ArrayList<>();
			if (!p.getListeValeurs().isEmpty()) {
				for (int j = 0; j < p.getListeValeurs().size(); j++) {
					Valeur v = p.getListeValeurs().get(j);
					if (v instanceof Variable) {
						BigInteger val = equation.getContextVariables().getValeurVariable((Variable) v);
						if (val != null) {
							liste3.add(new Constante(val));
						} else {
							liste3.add(v);
						}
					} else {
						liste3.add(v);
					}
				}
			}

			Produit p2 = new Produit(liste3);
			liste2.add(p2);
		}

		EquationSimple2 eq = new EquationSimple2(equation.contextVariables, liste2, equation.getResultat(), equation.getMod());
		return eq;
	}

	private EquationSimple3 simplifieEq(EquationSimple3 equation) {
		List<Produit> liste = equation.getSomme();
		List<Produit> liste2 = new ArrayList<>();
		for (int i = 0; i < liste.size(); i++) {
			Produit p = liste.get(i);

			List<Valeur> liste3 = new ArrayList<>();
			if (!p.getListeValeurs().isEmpty()) {
				for (int j = 0; j < p.getListeValeurs().size(); j++) {
					Valeur v = p.getListeValeurs().get(j);
					if (v instanceof Variable) {
						BigInteger val = equation.getContextVariables().getValeurVariable((Variable) v);
						if (val != null) {
							liste3.add(new Constante(val));
						} else {
							liste3.add(v);
						}
					} else {
						liste3.add(v);
					}
				}
			}

			Produit p2 = new Produit(liste3);
			liste2.add(p2);
		}

		EquationSimple3 eq = new EquationSimple3(equation.contextVariables, liste2, equation.getResultat(), equation.getBase(), equation.getReste());
		return eq;
	}

}
