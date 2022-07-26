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
package org.eclipse.sirius.components.forms.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.Fragment;
import org.eclipse.sirius.components.representations.FragmentProps;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The component used to render the diagnostic for forms.
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
        AbstractWidgetDescription widgetDescription = this.props.getWidgetDescription();
        VariableManager variableManager = this.props.getVariableManager();

        List<?> diagnostics = widgetDescription.getDiagnosticsProvider().apply(variableManager);
        List<Element> children = new ArrayList<>(diagnostics.size());

        for (Object diagnostic : diagnostics) {
            UUID id = UUID.randomUUID();
            String kind = widgetDescription.getKindProvider().apply(diagnostic);
            String message = widgetDescription.getMessageProvider().apply(diagnostic);

            // @formatter:off
            DiagnosticElementProps diagnosticElementProps = DiagnosticElementProps.newDiagnosticElementProps(id)
                    .kind(kind)
                    .message(message)
                    .build();
            // @formatter:on

            children.add(new Element(DiagnosticElementProps.TYPE, diagnosticElementProps));
        }

        FragmentProps fragmentProps = new FragmentProps(children);
        return new Fragment(fragmentProps);
    }

}
