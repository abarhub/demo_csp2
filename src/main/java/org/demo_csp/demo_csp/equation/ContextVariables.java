package org.demo_csp.demo_csp.equation;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ContextVariables {

	private final ContextVariables contextVariables;
	private final List<Variable> listVariables;
	private final List<ValeurVariable> listeValeurs;

	public ContextVariables(List<Variable> listVariables) {
		Preconditions.checkNotNull(listVariables);
		contextVariables = null;
		this.listVariables = ImmutableList.copyOf(listVariables);
		this.listeValeurs = ImmutableList.of();
	}

	public ContextVariables(List<Variable> listVariables, List<ValeurVariable> listeValeurs) {
		Preconditions.checkNotNull(listVariables);
		Preconditions.checkNotNull(listeValeurs);
		contextVariables = null;
		this.listVariables = ImmutableList.copyOf(listVariables);
		this.listeValeurs = ImmutableList.copyOf(listeValeurs);
	}

	public ContextVariables(ContextVariables contextVariables, List<Variable> listVariables, List<ValeurVariable> listeValeurs) {
		Preconditions.checkNotNull(contextVariables);
		Preconditions.checkNotNull(listVariables);
		Preconditions.checkNotNull(listeValeurs);
		this.contextVariables = contextVariables;
		this.listVariables = ImmutableList.copyOf(listVariables);
		this.listeValeurs = ImmutableList.copyOf(listeValeurs);
	}

	public ContextVariables(ContextVariables contextVariables, List<ValeurVariable> listeValeurs) {
		Preconditions.checkNotNull(contextVariables);
		Preconditions.checkNotNull(listeValeurs);
		this.contextVariables = contextVariables;
		this.listVariables = ImmutableList.of();
		this.listeValeurs = ImmutableList.copyOf(listeValeurs);
	}

	public List<Variable> getListVariables() {
		return listVariables;
	}

	public List<ValeurVariable> getListeValeurs() {
		return listeValeurs;
	}

	public BigInteger getValeurVariable(Variable var) {
		for (ValeurVariable vv : listeValeurs) {
			if (vv.getVariable().equals(var)) {
				return vv.getValeur();
			}
		}

		if (contextVariables != null) {
			return contextVariables.getValeurVariable(var);
		} else {
			return null;
		}

	}

	public List<Variable> getListeVariableAffectee(){
		List<Variable> res=new ArrayList<>();
		for (ValeurVariable vv : listeValeurs) {
			if (!res.contains(vv.getVariable())) {
				res.add(vv.getVariable());
			}
		}

		if (contextVariables != null) {
			List<Variable> res2=contextVariables.getListeVariableAffectee();

			if(res2!=null){
				for(Variable v:res2){
					if (!res.contains(v)) {
						res.add(v);
					}
				}
			}
		}

		return res;
	}

	@Override
	public String toString() {
		List<Variable> listVariables = new ArrayList<>(this.listVariables);
		List<ValeurVariable> listeValeurs = new ArrayList<>(this.listeValeurs);

		ContextVariables context = contextVariables;

		while (context != null) {
			for (Variable v : context.listVariables) {
				if (!listVariables.contains(v)) {
					listVariables.add(v);
				}
			}
			for (ValeurVariable v : context.listeValeurs) {
				if (!listeValeurs.contains(v)) {
					listeValeurs.add(v);
				}
			}
			context = context.contextVariables;
		}

		return "ContextVariables{" +
				//"contextVariables=" + contextVariables +
				" listVariables=" + listVariables +
				", listeValeurs=" + listeValeurs +
				'}';
	}
}
