/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.widget.reference;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.forms.renderer.IWidgetDescriptor;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;
import org.springframework.stereotype.Component;

/**
 * Widget descriptor for the reference widget(s).
 *
 * @author pcdavid
 */
@Component
public class ReferenceWidgetDescriptor implements IWidgetDescriptor {
    public static final String TYPE = "ReferenceWidget";

    @Override
    public List<String> getWidgetTypes() {
        return List.of(ReferenceElementProps.TYPE);
    }

    @Override
    public Optional<Boolean> validateComponentProps(Class<?> componentType, IProps props) {
        if (ReferenceWidgetComponent.class.equals(componentType)) {
            return Optional.of(props instanceof ReferenceWidgetComponentProps);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Boolean> validateInstanceProps(String type, IProps props) {
        Optional<Boolean> result = Optional.empty();
        if (Objects.equals(type, ReferenceElementProps.TYPE)) {
            result = Optional.of(props instanceof ReferenceElementProps);
        }
        return result;
    }

    @Override
    public Optional<Object> instanciate(String type, IProps elementProps, List<Object> children) {
        Optional<Object> result = Optional.empty();
        if (Objects.equals(type, ReferenceElementProps.TYPE) && elementProps instanceof ReferenceElementProps props) {
            var builder = ReferenceWidget.newMultiValuedReferenceWidget(props.getId())
                    .label(props.getLabel())
                    .iconURL(props.getIconURL())
                    .diagnostics(props.getDiagnostics())
                    .manyValued(props.isManyValued())
                    .container(props.isContainer())
                    .setting(props.getSetting())
                    .referenceValues(props.getValues());
            if (props.getHelpTextProvider() != null) {
                builder.helpTextProvider(props.getHelpTextProvider());
            }
            result = Optional.of(builder.build());
        }
        return result;
    }

    @Override
    public Optional<Element> createElement(VariableManager variableManager, AbstractWidgetDescription widgetDescription) {
        if (widgetDescription instanceof ReferenceWidgetDescription referenceWidgetDescription) {
            ReferenceWidgetComponentProps referenceComponentProps = new ReferenceWidgetComponentProps(variableManager, referenceWidgetDescription);
            return Optional.of(new Element(ReferenceWidgetComponent.class, referenceComponentProps));
        } else {
            return Optional.empty();
        }
    }
}
