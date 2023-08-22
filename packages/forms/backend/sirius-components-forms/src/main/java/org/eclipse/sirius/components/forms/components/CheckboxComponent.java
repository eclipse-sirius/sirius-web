/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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

        String label = checkboxDescription.getLabelProvider().apply(variableManager);

        VariableManager idVariableManager = variableManager.createChild();
        idVariableManager.put(FormComponent.TARGET_OBJECT_ID, checkboxDescription.getTargetObjectIdProvider().apply(variableManager));
        idVariableManager.put(FormComponent.CONTROL_DESCRIPTION_ID, checkboxDescription.getId());
        idVariableManager.put(FormComponent.WIDGET_LABEL, label);
        String id = checkboxDescription.getIdProvider().apply(idVariableManager);

        String iconURL = checkboxDescription.getIconURLProvider().apply(variableManager);
        Boolean readOnly = checkboxDescription.getIsReadOnlyProvider().apply(variableManager);
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

        if (iconURL != null) {
            checkboxElementPropsBuilder.iconURL(iconURL);
        }
        if (checkboxStyle != null) {
            checkboxElementPropsBuilder.style(checkboxStyle);
        }
        if (checkboxDescription.getHelpTextProvider() != null) {
            checkboxElementPropsBuilder.helpTextProvider(() -> checkboxDescription.getHelpTextProvider().apply(variableManager));
        }
        if (readOnly != null) {
            checkboxElementPropsBuilder.readOnly(readOnly);
        }

        CheckboxElementProps checkboxElementProps = checkboxElementPropsBuilder.build();

        return new Element(CheckboxElementProps.TYPE, checkboxElementProps);
        // @formatter:on
    }
}
