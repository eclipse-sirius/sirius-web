/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.validation.render;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.sirius.web.components.IElementFactory;
import org.eclipse.sirius.web.components.IProps;
import org.eclipse.sirius.web.validation.Diagnostic;
import org.eclipse.sirius.web.validation.Validation;
import org.eclipse.sirius.web.validation.elements.DiagnosticElementProps;
import org.eclipse.sirius.web.validation.elements.ValidationElementProps;

/**
 * Used to instantiate the elements of the validation.
 *
 * @author gcoutable
 */
public class ValidationElementFactory implements IElementFactory {

    @Override
    public Object instantiateElement(String type, IProps props, List<Object> children) {
        Object object = null;

        if (ValidationElementProps.TYPE.equals(type) && props instanceof ValidationElementProps) {
            object = this.instantiateValidation((ValidationElementProps) props, children);
        } else if (DiagnosticElementProps.TYPE.equals(type) && props instanceof DiagnosticElementProps) {
            object = this.instantiateDiagnostic((DiagnosticElementProps) props, children);
        }

        return object;
    }

    private Validation instantiateValidation(ValidationElementProps props, List<Object> children) {
        // @formatter:off
        List<Diagnostic> diagnostics = children.stream()
                .filter(Diagnostic.class::isInstance)
                .map(Diagnostic.class::cast)
                .collect(Collectors.toList());

        return Validation.newValidation(props.getId())
                .diagnostics(diagnostics)
                .build();
        // @formatter:on
    }

    private Diagnostic instantiateDiagnostic(DiagnosticElementProps props, List<Object> children) {
        // @formatter:off
        return Diagnostic.newDiagnostic(props.getId())
                .kind(props.getKind())
                .message(props.getMessage())
                .build();
        // @formatter:on
    }

}
