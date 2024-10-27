/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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
package org.eclipse.sirius.components.validation.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.validation.Validation;
import org.eclipse.sirius.components.validation.description.ValidationDescription;
import org.eclipse.sirius.components.validation.elements.ValidationElementProps;

/**
 * The component used to render the validation.
 *
 * @author gcoutable
 */
public class ValidationComponent implements IComponent {

    private final ValidationComponentProps props;

    public ValidationComponent(ValidationComponentProps props) {
        this.props = props;
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        ValidationDescription validationDescription = this.props.getValidationDescription();
        Optional<Validation> optionalPreviousValidation = this.props.getPreviousValidation();

        String id = optionalPreviousValidation.map(Validation::getId).orElseGet(() -> Validation.PREFIX);
        String targetObjectId = validationDescription.getTargetObjectIdProvider().apply(variableManager);

        List<?> diagnostics = validationDescription.getDiagnosticsProvider().apply(variableManager);
        List<Element> children = new ArrayList<>(diagnostics.size());
        for (Object diagnostic : diagnostics) {
            DiagnosticComponentProps diagnosticComponentProps = new DiagnosticComponentProps(diagnostic, validationDescription);
            children.add(new Element(DiagnosticComponent.class, diagnosticComponentProps));
        }

        ValidationElementProps validationElementProps = ValidationElementProps.newValidationElementProps(id)
                .descriptionId(validationDescription.getId())
                .targetObjectId(targetObjectId)
                .children(children)
                .build();

        return new Element(ValidationElementProps.TYPE, validationElementProps);
    }

}
