package org.demo_csp.demo_csp.equation;

import com.google.common.base.Preconditions;

import java.util.List;

public abstract class Equation {

	protected final ContextVariables contextVariables;

	public Equation(ContextVariables contextVariables) {
		Preconditions.checkNotNull(contextVariables);
		this.contextVariables = contextVariables;
	}

	public ContextVariables getContextVariables() {
		return contextVariables;
	}

	public abstract List<Variable> getVariables();

	public abstract EtatEquation etatEquation();

}
