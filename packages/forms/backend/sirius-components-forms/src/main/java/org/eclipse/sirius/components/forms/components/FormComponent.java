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

    /**
     * The name of the variable used to store the id of the parent element in the {@link VariableManager} for a widget
     * to compute its own id.
     */
    public static final String PARENT_ELEMENT_ID = "parentElementId";

    /**
     * The name of the variable used to store the id of widget's description in the {@link VariableManager} for a widget
     * to compute its own id.
     */
    public static final String CONTROL_DESCRIPTION_ID = "controlDescriptionId";

    /**
     * The name of the variable used to store the id of widget's target object in the {@link VariableManager} for a widget
     * to compute its own id.
     */
    public static final String TARGET_OBJECT_ID = "targetObjectId";

    /**
     * The name of the variable used to store the id of widget's label in the {@link VariableManager} for a widget
     * to compute its own id.
     */
    public static final String WIDGET_LABEL = "widgetLabel";

    private final FormComponentProps props;

    public FormComponent(FormComponentProps props) {
        this.props = props;
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        FormDescription formDescription = this.props.getFormDescription();

        String label = formDescription.getLabelProvider().apply(variableManager);

        VariableManager idVariableManager = variableManager.createChild();
        idVariableManager.put(FormComponent.TARGET_OBJECT_ID, formDescription.getTargetObjectIdProvider().apply(variableManager));
        idVariableManager.put(FormComponent.CONTROL_DESCRIPTION_ID, formDescription.getId());
        idVariableManager.put(FormComponent.WIDGET_LABEL, label);
        String id = formDescription.getIdProvider().apply(idVariableManager);

        String targetObjectId = formDescription.getTargetObjectIdProvider().apply(variableManager);
        List<PageDescription> pageDescriptions = formDescription.getPageDescriptions();

        List<Object> candidates = new ArrayList<>();

        var optionalSelf = variableManager.get(VariableManager.SELF, Object.class);
        if (optionalSelf.isPresent()) {
            Object self = optionalSelf.get();
            if (self instanceof Collection<?> objects) {
                candidates.addAll(objects);
            } else {
                candidates.add(self);
            }
        }

        List<Element> children = candidates.stream()
                .map(candidate -> {
                    VariableManager childVariableManager = variableManager.createChild();
                    childVariableManager.put(VariableManager.SELF, candidate);
                    childVariableManager.put(FormComponent.PARENT_ELEMENT_ID, id);
                    return childVariableManager;
                }).flatMap(childVariableManager -> {
                    return pageDescriptions.stream()
                            .filter(pageDescription -> pageDescription.getCanCreatePredicate().test(childVariableManager))
                            .map(pageDescription -> new Element(PageComponent.class, new PageComponentProps(childVariableManager, pageDescription, this.props.getWidgetDescriptors())));
                })
                .toList();

        FormElementProps formElementProps = FormElementProps.newFormElementProps(id)
                .label(label)
                .targetObjectId(targetObjectId)
                .descriptionId(formDescription.getId())
                .children(children)
                .build();

        return new Element(FormElementProps.TYPE, formElementProps);
    }
}
