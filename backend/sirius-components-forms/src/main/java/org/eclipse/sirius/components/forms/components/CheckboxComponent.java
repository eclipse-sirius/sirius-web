/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
package org.eclipse.sirius.components.forms.components;

import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.eclipse.sirius.components.forms.description.CheckboxDescription;
import org.eclipse.sirius.components.forms.elements.CheckboxElementProps;
import org.eclipse.sirius.components.forms.elements.CheckboxElementProps.Builder;
import org.eclipse.sirius.components.forms.validation.DiagnosticComponent;
import org.eclipse.sirius.components.forms.validation.DiagnosticComponentProps;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The component used to create the checkbox widget.
 *
 * @author sbegaudeau
 */
public class CheckboxComponent implements IComponent {

    private CheckboxComponentProps props;

    public CheckboxComponent(CheckboxComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        CheckboxDescription checkboxDescription = this.props.getCheckboxDescription();

        String id = checkboxDescription.getIdProvider().apply(variableManager);
        String label = checkboxDescription.getLabelProvider().apply(variableManager);
        Boolean value = checkboxDescription.getValueProvider().apply(variableManager);
        BiFunction<VariableManager, Boolean, IStatus> genericHandler = checkboxDescription.getNewValueHandler();
        Function<Boolean, IStatus> specializedHandler = newValue -> genericHandler.apply(variableManager, newValue);
        var checkboxStyle = checkboxDescription.getStyleProvider().apply(variableManager);

        List<Element> children = List.of(new Element(DiagnosticComponent.class, new DiagnosticComponentProps(checkboxDescription, variableManager)));

        // @formatter:off
        Builder checkboxElementPropsBuilder = CheckboxElementProps.newCheckboxElementProps(id)
                .label(label)
                .value(value.booleanValue())
                .newValueHandler(specializedHandler)
                .children(children);

        if (checkboxStyle != null) {
            checkboxElementPropsBuilder.style(checkboxStyle);
        }

        CheckboxElementProps checkboxElementProps = checkboxElementPropsBuilder.build();

        return new Element(CheckboxElementProps.TYPE, checkboxElementProps);
        // @formatter:on
    }
}
