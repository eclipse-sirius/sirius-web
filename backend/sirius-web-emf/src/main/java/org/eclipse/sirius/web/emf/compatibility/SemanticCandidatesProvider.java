/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.web.emf.compatibility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.eclipse.sirius.web.interpreter.Result;
import org.eclipse.sirius.web.representations.VariableManager;

/**
 * Utility class used to provide the semantic candidates.
 *
 * @author sbegaudeau
 */
public class SemanticCandidatesProvider implements Function<VariableManager, List<Object>> {
    /**
     * The default expression to use when no semantic candidates expression is explicitly defined.
     * <p>
     * Note that this is slightly different from what Sirius Desktop does (in
     * DiagramElementMappingQuery.getAllCandidates() or
     * AbstractSynchronizerHelper.getAllCandidates(DiagramElementMapping)): Sirius Desktop will find candidates in all
     * semantic resources of the session's ResourceSet, while the expression below will only look into the current
     * resource.
     */
    private static final String DEFAULT_SEMANTIC_CANDIDATES_EXPRESSION = "aql:self.eResource().getContents().eAllContents()"; //$NON-NLS-1$

    private AQLInterpreter interpreter;

    private String domainClass;

    private String semanticCandidatesExpression;

    private String preconditionExpression;

    public SemanticCandidatesProvider(AQLInterpreter interpreter, String domainClass, String semanticCandidatesExpression, String preconditionExpression) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.domainClass = Objects.requireNonNull(domainClass);
        if (semanticCandidatesExpression == null || semanticCandidatesExpression.isBlank()) {
            this.semanticCandidatesExpression = DEFAULT_SEMANTIC_CANDIDATES_EXPRESSION;
        } else {
            this.semanticCandidatesExpression = Objects.requireNonNull(semanticCandidatesExpression);
        }
        this.preconditionExpression = Objects.requireNonNull(preconditionExpression);
    }

    @Override
    public List<Object> apply(VariableManager variableManager) {
        List<Object> semanticCandidates = new ArrayList<>();

        Result result = this.interpreter.evaluateExpression(variableManager.getVariables(), this.semanticCandidatesExpression);

        DomainClassPredicate domainClassPredicate = new DomainClassPredicate(this.domainClass);

        // @formatter:off
        List<EObject> eObjects = result.asObjects().orElse(List.of()).stream()
                .filter(EObject.class::isInstance)
                .map(EObject.class::cast)
                .filter(domainClassPredicate::test)
                .collect(Collectors.toList());
        // @formatter:on

        for (EObject eObject : eObjects) {
            // Retrieve all the variables and overwrite the variable self
            Map<String, Object> variables = new HashMap<>();
            variables.putAll(variableManager.getVariables());
            variables.put(VariableManager.SELF, eObject);

            if (!this.preconditionExpression.isBlank()) {
                Result preconditionResult = this.interpreter.evaluateExpression(variables, this.preconditionExpression);
                preconditionResult.asBoolean().ifPresent(isValid -> {
                    if (isValid) {
                        semanticCandidates.add(eObject);
                    }
                });
            } else {
                semanticCandidates.add(eObject);
            }
        }
        return semanticCandidates;
    }
}
