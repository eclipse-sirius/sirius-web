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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.sirius.components.forms.description.FormDescription;
import org.eclipse.sirius.components.forms.description.PageDescription;
import org.eclipse.sirius.components.forms.elements.FormElementProps;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.VariableManager;

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

        String id = formDescription.getIdProvider().apply(variableManager);
        String label = formDescription.getLabelProvider().apply(variableManager);
        String targetObjectId = formDescription.getTargetObjectIdProvider().apply(variableManager);
        List<PageDescription> pageDescriptions = formDescription.getPageDescriptions();

        List<Object> candidates = new ArrayList<>();

        var optionalSelf = variableManager.get(VariableManager.SELF, Object.class);
        if (optionalSelf.isPresent()) {
            Object self = optionalSelf.get();
            if (self instanceof Collection<?>) {
                Collection<?> objects = (Collection<?>) self;
                candidates.addAll(objects);
            } else {
                candidates.add(self);
            }
        }

        // @formatter:off
        List<Element> children = candidates.stream().map(candidate -> {
            VariableManager childVariableManager = variableManager.createChild();
            childVariableManager.put(VariableManager.SELF, candidate);
            return childVariableManager;
        }).flatMap(childVariableManager -> pageDescriptions.stream().map(pageDescription -> {
            PageComponentProps pageComponentProps = new PageComponentProps(childVariableManager, pageDescription);
            return new Element(PageComponent.class, pageComponentProps);
        })).toList();

        FormElementProps formElementProps = FormElementProps.newFormElementProps(id)
                .label(label)
                .targetObjectId(targetObjectId)
                .descriptionId(formDescription.getId())
                .children(children)
                .build();

        return new Element(FormElementProps.TYPE, formElementProps);
        // @formatter:on
    }
}
