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
package org.eclipse.sirius.components.formdescriptioneditors.renderer;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.formdescriptioneditors.AbstractFormDescriptionEditorWidget;
import org.eclipse.sirius.components.formdescriptioneditors.FormDescriptionEditor;
import org.eclipse.sirius.components.formdescriptioneditors.FormDescriptionEditorFlexboxContainer;
import org.eclipse.sirius.components.formdescriptioneditors.FormDescriptionEditorWidget;
import org.eclipse.sirius.components.formdescriptioneditors.elements.FormDescriptionEditorElementProps;
import org.eclipse.sirius.components.formdescriptioneditors.elements.FormDescriptionEditorFlexboxContainerElementProps;
import org.eclipse.sirius.components.formdescriptioneditors.elements.FormDescriptionEditorWidgetElementProps;
import org.eclipse.sirius.components.representations.IElementFactory;
import org.eclipse.sirius.components.representations.IProps;

/**
 * Used to instantiate the elements of the form description editor.
 *
 * @author arichard
 */
public class FormDescriptionEditorElementFactory implements IElementFactory {

    @Override
    public Object instantiateElement(String type, IProps props, List<Object> children) {
        Object object = null;
        if (FormDescriptionEditorElementProps.TYPE.equals(type) && props instanceof FormDescriptionEditorElementProps) {
            object = this.instantiateForm((FormDescriptionEditorElementProps) props, children);
        } else if (FormDescriptionEditorWidgetElementProps.TYPE.equals(type) && props instanceof FormDescriptionEditorWidgetElementProps) {
            object = this.instantiateWidget((FormDescriptionEditorWidgetElementProps) props, children);
        } else if (FormDescriptionEditorFlexboxContainerElementProps.TYPE.equals(type) && props instanceof FormDescriptionEditorFlexboxContainerElementProps) {
            object = this.instantiateFlexboxContainer((FormDescriptionEditorFlexboxContainerElementProps) props, children);
        }
        return object;
    }

    private FormDescriptionEditor instantiateForm(FormDescriptionEditorElementProps props, List<Object> children) {
        // @formatter:off
        List<AbstractFormDescriptionEditorWidget> widgets = children.stream()
                .filter(AbstractFormDescriptionEditorWidget.class::isInstance)
                .map(AbstractFormDescriptionEditorWidget.class::cast)
                .collect(Collectors.toList());

        return FormDescriptionEditor.newFormDescriptionEditor(props.getId())
                .label(props.getLabel())
                .targetObjectId(props.getTargetObjectId())
                .descriptionId(props.getDescriptionId())
                .widgets(widgets)
                .build();
        // @formatter:on
    }

    private FormDescriptionEditorWidget instantiateWidget(FormDescriptionEditorWidgetElementProps props, List<Object> children) {
        // @formatter:off
        return FormDescriptionEditorWidget.newFormDescriptionEditorWidget(props.getId())
                .label(props.getLabel())
                .kind(props.getKind())
                .build();
        // @formatter:on
    }

    private FormDescriptionEditorFlexboxContainer instantiateFlexboxContainer(FormDescriptionEditorFlexboxContainerElementProps props, List<Object> children) {
        // @formatter:off
        List<AbstractFormDescriptionEditorWidget> widgets = children.stream()
                .filter(AbstractFormDescriptionEditorWidget.class::isInstance)
                .map(AbstractFormDescriptionEditorWidget.class::cast)
                .collect(Collectors.toList());

        return FormDescriptionEditorFlexboxContainer.newFormDescriptionEditorFlexboxContainer(props.getId())
                .label(props.getLabel())
                .kind(props.getKind())
                .flexDirection(props.getFlexDirection().getLiteral())
                .flexWrap("wrap") //$NON-NLS-1$
                .flexGrow(1)
                .children(widgets)
                .build();
        // @formatter:on
    }
}
