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

import org.eclipse.sirius.components.forms.LinkStyle;
import org.eclipse.sirius.components.forms.description.LinkDescription;
import org.eclipse.sirius.components.forms.elements.LinkElementProps;
import org.eclipse.sirius.components.forms.validation.DiagnosticComponent;
import org.eclipse.sirius.components.forms.validation.DiagnosticComponentProps;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The component used to render the Link.
 *
 * @author ldelaigue
 */
public class LinkComponent implements IComponent {

    private final LinkComponentProps props;

    public LinkComponent(LinkComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        LinkDescription linkDescription = this.props.getLinkDescription();

        String label = linkDescription.getLabelProvider().apply(variableManager);

        VariableManager idVariableManager = variableManager.createChild();
        idVariableManager.put(FormComponent.TARGET_OBJECT_ID, linkDescription.getTargetObjectIdProvider().apply(variableManager));
        idVariableManager.put(FormComponent.CONTROL_DESCRIPTION_ID, linkDescription.getId());
        idVariableManager.put(FormComponent.WIDGET_LABEL, label);
        String id = linkDescription.getIdProvider().apply(idVariableManager);

        List<String> iconURL = linkDescription.getIconURLProvider().apply(variableManager);
        String url = linkDescription.getUrlProvider().apply(variableManager);
        LinkStyle style = linkDescription.getStyleProvider().apply(variableManager);
        List<Element> children = List.of(new Element(DiagnosticComponent.class, new DiagnosticComponentProps(linkDescription, variableManager)));

        // @formatter:off
        LinkElementProps.Builder linkElementPropsBuilder = LinkElementProps.newLinkElementProps(id)
                .label(label)
                .url(url)
                .children(children);
        // @formatter:on

        if (style != null) {
            linkElementPropsBuilder.style(style);
        }
        if (iconURL != null) {
            linkElementPropsBuilder.iconURL(iconURL);
        }
        if (linkDescription.getHelpTextProvider() != null) {
            linkElementPropsBuilder.helpTextProvider(() -> linkDescription.getHelpTextProvider().apply(variableManager));
        }

        LinkElementProps linkElementProps = linkElementPropsBuilder.build();
        return new Element(LinkElementProps.TYPE, linkElementProps);
    }
}
