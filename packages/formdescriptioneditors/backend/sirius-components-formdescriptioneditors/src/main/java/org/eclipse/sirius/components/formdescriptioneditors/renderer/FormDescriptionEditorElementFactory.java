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
package org.eclipse.sirius.components.formdescriptioneditors.renderer;

import java.util.List;

import org.eclipse.sirius.components.formdescriptioneditors.FormDescriptionEditor;
import org.eclipse.sirius.components.formdescriptioneditors.FormDescriptionEditorFor;
import org.eclipse.sirius.components.formdescriptioneditors.FormDescriptionEditorIf;
import org.eclipse.sirius.components.formdescriptioneditors.elements.FormDescriptionEditorElementProps;
import org.eclipse.sirius.components.formdescriptioneditors.elements.FormDescriptionEditorForElementProps;
import org.eclipse.sirius.components.formdescriptioneditors.elements.FormDescriptionEditorIfElementProps;
import org.eclipse.sirius.components.forms.AbstractWidget;
import org.eclipse.sirius.components.forms.Page;
import org.eclipse.sirius.components.forms.renderer.FormElementFactory;
import org.eclipse.sirius.components.forms.renderer.IWidgetDescriptor;
import org.eclipse.sirius.components.representations.IElementFactory;
import org.eclipse.sirius.components.representations.IProps;

/**
 * Used to instantiate the elements of the form description editor.
 *
 * @author arichard
 */
public class FormDescriptionEditorElementFactory implements IElementFactory {

    private final FormElementFactory formElementFactory;

    public FormDescriptionEditorElementFactory(List<IWidgetDescriptor> widgetDescriptors) {
        this.formElementFactory = new FormElementFactory(widgetDescriptors);
    }

    @Override
    public Object instantiateElement(String type, IProps props, List<Object> children) {
        Object object = null;
        if (FormDescriptionEditorElementProps.TYPE.equals(type) && props instanceof FormDescriptionEditorElementProps) {
            object = this.instantiateFormDescriptionEditor((FormDescriptionEditorElementProps) props, children);
        } else if (FormDescriptionEditorForElementProps.TYPE.equals(type) && props instanceof FormDescriptionEditorForElementProps) {
            object = this.instantiateFormDescriptionEditorFor((FormDescriptionEditorForElementProps) props, children);
        } else if (FormDescriptionEditorIfElementProps.TYPE.equals(type) && props instanceof FormDescriptionEditorIfElementProps) {
            object = this.instantiateFormDescriptionEditorIf((FormDescriptionEditorIfElementProps) props, children);
        } else {
            object = this.formElementFactory.instantiateElement(type, props, children);
        }
        return object;
    }

    private FormDescriptionEditor instantiateFormDescriptionEditor(FormDescriptionEditorElementProps props, List<Object> children) {
        List<Page> pages = children.stream()
                .filter(Page.class::isInstance)
                .map(Page.class::cast)
                .toList();

        return FormDescriptionEditor.newFormDescriptionEditor(props.getId())
                .label(props.getLabel())
                .targetObjectId(props.getTargetObjectId())
                .descriptionId(props.getDescriptionId())
                .pages(pages)
                .build();
    }

    private FormDescriptionEditorFor instantiateFormDescriptionEditorFor(FormDescriptionEditorForElementProps props, List<Object> children) {
        List<AbstractWidget> widgets = children.stream()
                .filter(AbstractWidget.class::isInstance)
                .map(AbstractWidget.class::cast)
                .toList();

        return FormDescriptionEditorFor.newFormDescriptionEditorFor(props.getId())
                .label(props.getLabel())
                .diagnostics(List.of())
                .children(widgets)
                .build();
    }

    private FormDescriptionEditorIf instantiateFormDescriptionEditorIf(FormDescriptionEditorIfElementProps props, List<Object> children) {
        List<AbstractWidget> widgets = children.stream()
                .filter(AbstractWidget.class::isInstance)
                .map(AbstractWidget.class::cast)
                .toList();

        return FormDescriptionEditorIf.newFormDescriptionEditorIf(props.getId())
                .label(props.getLabel())
                .diagnostics(List.of())
                .children(widgets)
                .build();
    }
}
