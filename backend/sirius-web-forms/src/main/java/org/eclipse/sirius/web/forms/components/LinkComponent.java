/*******************************************************************************
 * Copyright (c) 22021 Obeo.
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

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.web.components.Element;
import org.eclipse.sirius.web.components.IComponent;
import org.eclipse.sirius.web.forms.description.LinkDescription;
import org.eclipse.sirius.web.forms.elements.LinkElementProps;
import org.eclipse.sirius.web.forms.validation.DiagnosticComponent;
import org.eclipse.sirius.web.forms.validation.DiagnosticComponentProps;
import org.eclipse.sirius.web.representations.VariableManager;

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

        String id = linkDescription.getIdProvider().apply(variableManager);
        String label = linkDescription.getLabelProvider().apply(variableManager);
        String url = linkDescription.getUrlProvider().apply(variableManager);
        List<Element> children = List.of(new Element(DiagnosticComponent.class, new DiagnosticComponentProps(linkDescription, variableManager)));

        // @formatter:off
        LinkElementProps linkElementProps = LinkElementProps.newLinkElementProps(id)
                .label(label)
                .url(url)
                .children(children)
                .build();
        return new Element(LinkElementProps.TYPE, linkElementProps);
        // @formatter:on
    }
}
