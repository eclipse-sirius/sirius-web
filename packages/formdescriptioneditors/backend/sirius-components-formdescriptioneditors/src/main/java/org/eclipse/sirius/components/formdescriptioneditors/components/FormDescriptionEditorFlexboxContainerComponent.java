/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.formdescriptioneditors.components;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.sirius.components.formdescriptioneditors.description.FormDescriptionEditorFlexboxContainerDescription;
import org.eclipse.sirius.components.formdescriptioneditors.description.FormDescriptionEditorWidgetDescription;
import org.eclipse.sirius.components.formdescriptioneditors.elements.FormDescriptionEditorFlexboxContainerElementProps;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.Fragment;
import org.eclipse.sirius.components.representations.FragmentProps;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.view.FlexDirection;

/**
 * The component used to render the form description editor flexbox container.
 *
 * @author arichard
 */
public class FormDescriptionEditorFlexboxContainerComponent implements IComponent {

    private final FormDescriptionEditorFlexboxContainerComponentProps props;

    public FormDescriptionEditorFlexboxContainerComponent(FormDescriptionEditorFlexboxContainerComponentProps props) {
        this.props = props;
    }

    @Override
    public Element render() {
        FormDescriptionEditorFlexboxContainerDescription flexboxContainerDescription = this.props.getFormDescriptionEditorFlexboxContainerDescription();

        String id = flexboxContainerDescription.getId();
        String label = flexboxContainerDescription.getLabel();
        String kind = flexboxContainerDescription.getKind();
        FlexDirection flexDirection = flexboxContainerDescription.getFlexDirection();

        List<Element> childrenWidgets = new ArrayList<>();

        flexboxContainerDescription.getChildren().forEach(widget -> {
            if (widget instanceof FormDescriptionEditorWidgetDescription) {
                childrenWidgets.add(new Element(FormDescriptionEditorWidgetComponent.class, new FormDescriptionEditorWidgetComponentProps((FormDescriptionEditorWidgetDescription) widget)));
            } else if (widget instanceof FormDescriptionEditorFlexboxContainerDescription) {
                childrenWidgets.add(new Element(FormDescriptionEditorFlexboxContainerComponent.class,
                        new FormDescriptionEditorFlexboxContainerComponentProps((FormDescriptionEditorFlexboxContainerDescription) widget)));
            }
        });

        // @formatter:off
        FormDescriptionEditorFlexboxContainerElementProps flexboxContainerElementProps = FormDescriptionEditorFlexboxContainerElementProps.newFormDescriptionEditorFlexboxContainerElementProps(id)
                .label(label)
                .kind(kind)
                .flexDirection(flexDirection)
                .children(childrenWidgets)
                .build();
        // @formatter:on

        List<Element> children = new ArrayList<>();
        Element flexboxContainerElement = new Element(FormDescriptionEditorFlexboxContainerElementProps.TYPE, flexboxContainerElementProps);
        children.add(flexboxContainerElement);

        FragmentProps fragmentProps = new FragmentProps(children);
        return new Fragment(fragmentProps);
    }
}
