/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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

import org.eclipse.sirius.components.forms.description.RichTextDescription;
import org.eclipse.sirius.components.forms.elements.RichTextElementProps;
import org.eclipse.sirius.components.forms.elements.RichTextElementProps.Builder;
import org.eclipse.sirius.components.forms.validation.DiagnosticComponent;
import org.eclipse.sirius.components.forms.validation.DiagnosticComponentProps;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The component used to render the rich text.
 *
 * @author pcdavid
 */
public class RichTextComponent implements IComponent {

    private RichTextComponentProps props;

    public RichTextComponent(RichTextComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        RichTextDescription richTextDescription = this.props.getRichTextDescription();

        String id = richTextDescription.getIdProvider().apply(variableManager);
        String label = richTextDescription.getLabelProvider().apply(variableManager);
        String iconURL = richTextDescription.getIconURLProvider().apply(variableManager);
        String value = richTextDescription.getValueProvider().apply(variableManager);
        BiFunction<VariableManager, String, IStatus> genericHandler = richTextDescription.getNewValueHandler();
        Function<String, IStatus> specializedHandler = newValue -> {
            return genericHandler.apply(variableManager, newValue);
        };
        List<Element> children = List.of(new Element(DiagnosticComponent.class, new DiagnosticComponentProps(richTextDescription, variableManager)));

        // @formatter:off
        Builder richTextElementPropsBuilder = RichTextElementProps.newRichTextElementProps(id)
                .label(label)
                .value(value)
                .newValueHandler(specializedHandler)
                .children(children);

        if (iconURL != null) {
            richTextElementPropsBuilder.iconURL(iconURL);
        }
        if (richTextDescription.getHelpTextProvider() != null) {
            richTextElementPropsBuilder.helpTextProvider(() -> richTextDescription.getHelpTextProvider().apply(variableManager));
        }
        RichTextElementProps richTextElementProps = richTextElementPropsBuilder.build();

        return new Element(RichTextElementProps.TYPE, richTextElementProps);
        // @formatter:on
    }
}
