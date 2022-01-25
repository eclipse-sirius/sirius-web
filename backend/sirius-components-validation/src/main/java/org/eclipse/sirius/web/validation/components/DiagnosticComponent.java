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
package org.eclipse.sirius.web.validation.components;

import java.util.UUID;

import org.eclipse.sirius.web.representations.Element;
import org.eclipse.sirius.web.representations.IComponent;
import org.eclipse.sirius.web.validation.description.ValidationDescription;
import org.eclipse.sirius.web.validation.elements.DiagnosticElementProps;

/**
 * The component used to render the diagnostic.
 *
 * @author gcoutable
 */
public class DiagnosticComponent implements IComponent {

    private final DiagnosticComponentProps props;

    public DiagnosticComponent(DiagnosticComponentProps props) {
        this.props = props;
    }

    @Override
    public Element render() {
        Object diagnostic = this.props.getDiagnostic();
        ValidationDescription validationDescription = this.props.getValidationDescription();

        String kind = validationDescription.getKindProvider().apply(diagnostic);
        String message = validationDescription.getMessageProvider().apply(diagnostic);

        // @formatter:off
        DiagnosticElementProps diagnosticElementProps = DiagnosticElementProps.newDiagnosticElementProps(UUID.randomUUID())
                .kind(kind)
                .message(message)
                .build();
        // @formatter:on

        return new Element(DiagnosticElementProps.TYPE, diagnosticElementProps);
    }

}
