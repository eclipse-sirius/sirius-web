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
import org.eclipse.sirius.components.forms.validation.Diagnostic;
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

        List<Diagnostic> diagnostics = children.stream()
                .filter(Diagnostic.class::isInstance)
                .map(Diagnostic.class::cast)
                .toList();

        if (Objects.equals(type, ReferenceElementProps.TYPE) && elementProps instanceof ReferenceElementProps props) {
            var builder = ReferenceWidget.newReferenceWidget(props.getId())
                    .descriptionId(props.getDescriptionId())
                    .label(props.getLabel())
                    .iconURL(props.getIconURL())
                    .diagnostics(diagnostics)
                    .readOnly(props.isReadOnly())
                    .ownerKind(props.getOwnerKind())
                    .referenceKind(props.getReferenceKind())
                    .containment(props.isContainment())
                    .many(props.isMany())
                    .referenceValues(props.getValues())
                    .referenceOptionsProvider(props.getOptionsProvider())
                    .ownerId(props.getOwnerId())
                    .clearHandler(props.getClearHandler())
                    .setHandler(props.getSetHandler())
                    .addHandler(props.getAddHandler())
                    .createElementHandler(props.getCreateElementHandler())
                    .moveHandler(props.getMoveHandler());
            if (props.getHelpTextProvider() != null) {
                builder.helpTextProvider(props.getHelpTextProvider());
            }
            if (props.getStyle() != null) {
                builder.style(props.getStyle());
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
