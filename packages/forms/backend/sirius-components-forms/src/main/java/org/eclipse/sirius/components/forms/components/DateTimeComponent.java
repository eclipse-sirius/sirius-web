/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

import org.eclipse.sirius.components.forms.DateTimeType;
import org.eclipse.sirius.components.forms.description.DateTimeDescription;
import org.eclipse.sirius.components.forms.elements.DateTimeElementProps;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The component to render a dateTime.
 *
 * @author lfasani
 */
public class DateTimeComponent implements IComponent {

    private final DateTimeComponentProps props;

    public DateTimeComponent(DateTimeComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        DateTimeDescription dateTimeDescription = this.props.getDateTimeDescription();

        String label = dateTimeDescription.getLabelProvider().apply(variableManager);

        VariableManager idVariableManager = variableManager.createChild();
        idVariableManager.put(FormComponent.TARGET_OBJECT_ID, dateTimeDescription.getTargetObjectIdProvider().apply(variableManager));
        idVariableManager.put(FormComponent.CONTROL_DESCRIPTION_ID, dateTimeDescription.getId());
        idVariableManager.put(FormComponent.WIDGET_LABEL, label);
        String id = dateTimeDescription.getIdProvider().apply(idVariableManager);

        List<String> iconURL = dateTimeDescription.getIconURLProvider().apply(variableManager);
        Boolean readOnly = dateTimeDescription.getIsReadOnlyProvider().apply(variableManager);
        String stringValue = dateTimeDescription.getStringValueProvider().apply(variableManager);
        BiFunction<VariableManager, String, IStatus> newValueHandler = dateTimeDescription.getNewValueHandler();
        Function<String, IStatus> specializedHandler = (newValue) -> newValueHandler.apply(variableManager, newValue);
        var style = dateTimeDescription.getStyleProvider().apply(variableManager);
        DateTimeType type = dateTimeDescription.getType();

        var dateTimeElementPropsBuilder = DateTimeElementProps.newDateTimeElementProps(id)
                .label(label)
                .stringValue(stringValue)
                .type(type)
                .newValueHandler(specializedHandler);

        if (iconURL != null) {
            dateTimeElementPropsBuilder.iconURL(iconURL);
        }
        if (style != null) {
            dateTimeElementPropsBuilder.style(style);
        }
        if (dateTimeDescription.getHelpTextProvider() != null) {
            dateTimeElementPropsBuilder.helpTextProvider(() -> dateTimeDescription.getHelpTextProvider().apply(variableManager));
        }
        if (readOnly != null) {
            dateTimeElementPropsBuilder.readOnly(readOnly);
        }

        DateTimeElementProps dateTimeElementProps = dateTimeElementPropsBuilder.build();

        return new Element(DateTimeElementProps.TYPE, dateTimeElementProps);
    }
}
