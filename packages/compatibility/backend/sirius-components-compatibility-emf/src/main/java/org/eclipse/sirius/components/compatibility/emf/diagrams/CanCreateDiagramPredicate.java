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
package org.eclipse.sirius.components.compatibility.emf.diagrams;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.sirius.components.compatibility.emf.DomainClassPredicate;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.Result;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.diagram.description.DiagramDescription;

/**
 * Predicate to test the ability to create a diagram according to the given {@Link VariableManager}.
 *
 * @author hmarchadour
 */
public class CanCreateDiagramPredicate implements Predicate<VariableManager> {

    private final DiagramDescription diagramDescription;

    private final AQLInterpreter interpreter;

    public CanCreateDiagramPredicate(DiagramDescription diagramDescription, AQLInterpreter interpreter) {
        this.diagramDescription = Objects.requireNonNull(diagramDescription);
        this.interpreter = Objects.requireNonNull(interpreter);
    }

    @Override
    public boolean test(VariableManager variableManager) {
        boolean result = false;

        String domainClass = this.diagramDescription.getDomainClass();

        // @formatter:off
        Optional<EClass> optionalEObject = variableManager.get(IRepresentationDescription.CLASS, EClass.class)
                .filter(new DomainClassPredicate(domainClass));
        // @formatter:on

        if (optionalEObject.isPresent()) {
            String preconditionExpression = this.diagramDescription.getPreconditionExpression();
            if (preconditionExpression != null && !preconditionExpression.isBlank()) {
                Result preconditionResult = this.interpreter.evaluateExpression(variableManager.getVariables(), preconditionExpression);
                result = preconditionResult.asBoolean().orElse(false);
            } else {
                result = true;
            }
        }

        return result;
    }

}
