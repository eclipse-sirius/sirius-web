/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.forms.components;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.eclipse.sirius.web.components.Element;
import org.eclipse.sirius.web.components.IComponent;
import org.eclipse.sirius.web.forms.description.CheckboxDescription;
import org.eclipse.sirius.web.forms.elements.CheckboxElementProps;
import org.eclipse.sirius.web.representations.Status;
import org.eclipse.sirius.web.representations.VariableManager;

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
        BiFunction<VariableManager, Boolean, Status> genericHandler = checkboxDescription.getNewValueHandler();
        Function<Boolean, Status> specializedHandler = newValue -> genericHandler.apply(variableManager, newValue);

        // @formatter:off
        CheckboxElementProps checkboxElementProps = CheckboxElementProps.newCheckboxElementProps(id)
                .label(label)
                .value(value.booleanValue())
                .newValueHandler(specializedHandler)
                .build();
        return new Element(CheckboxElementProps.TYPE, checkboxElementProps);
        // @formatter:on
    }
}
