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
package org.eclipse.sirius.components.validation.render;

import java.util.List;

import org.eclipse.sirius.components.representations.IElementFactory;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.validation.Diagnostic;
import org.eclipse.sirius.components.validation.Validation;
import org.eclipse.sirius.components.validation.elements.DiagnosticElementProps;
import org.eclipse.sirius.components.validation.elements.ValidationElementProps;

/**
 * Used to instantiate the elements of the validation.
 *
 * @author gcoutable
 */
public class ValidationElementFactory implements IElementFactory {

    @Override
    public Object instantiateElement(String type, IProps props, List<Object> children) {
        Object object = null;

        if (ValidationElementProps.TYPE.equals(type) && props instanceof ValidationElementProps validationElementProps) {
            object = this.instantiateValidation(validationElementProps, children);
        } else if (DiagnosticElementProps.TYPE.equals(type) && props instanceof DiagnosticElementProps diagnosticElementProps) {
            object = this.instantiateDiagnostic(diagnosticElementProps, children);
        }

        return object;
    }

    private Validation instantiateValidation(ValidationElementProps props, List<Object> children) {
        List<Diagnostic> diagnostics = children.stream()
                .filter(Diagnostic.class::isInstance)
                .map(Diagnostic.class::cast)
                .toList();

        return Validation.newValidation(props.getId())
                .label(props.getLabel())
                .descriptionId(props.getDescriptionId())
                .targetObjectId(props.getTargetObjectId())
                .diagnostics(diagnostics)
                .build();
    }

    private Diagnostic instantiateDiagnostic(DiagnosticElementProps props, List<Object> children) {
        return Diagnostic.newDiagnostic(props.getId())
                .kind(props.getKind())
                .message(props.getMessage())
                .build();
    }

}
