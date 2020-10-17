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

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.eclipse.sirius.web.components.Element;
import org.eclipse.sirius.web.components.IComponent;
import org.eclipse.sirius.web.forms.description.FormDescription;
import org.eclipse.sirius.web.forms.description.PageDescription;
import org.eclipse.sirius.web.forms.elements.FormElementProps;
import org.eclipse.sirius.web.representations.VariableManager;

/**
 * The component used to render the form.
 *
 * @author sbegaudeau
 */
public class FormComponent implements IComponent {

    private final FormComponentProps props;

    public FormComponent(FormComponentProps props) {
        this.props = props;
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        FormDescription formDescription = this.props.getFormDescription();

        UUID id = formDescription.getIdProvider().apply(variableManager);
        String label = formDescription.getLabelProvider().apply(variableManager);
        String targetObjectId = formDescription.getTargetObjectIdProvider().apply(variableManager);
        List<PageDescription> pageDescriptions = formDescription.getPageDescriptions();

        // @formatter:off
        List<Element> children = pageDescriptions.stream().map(pageDescription -> {
            PageComponentProps pageComponentProps = new PageComponentProps(variableManager, pageDescription);
            return new Element(PageComponent.class, pageComponentProps);
        }).collect(Collectors.toList());

        FormElementProps formElementProps = FormElementProps.newFormElementProps(id)
                .label(label)
                .targetObjectId(targetObjectId)
                .children(children)
                .build();

        return new Element(FormElementProps.TYPE, formElementProps);
        // @formatter:on
    }
}
