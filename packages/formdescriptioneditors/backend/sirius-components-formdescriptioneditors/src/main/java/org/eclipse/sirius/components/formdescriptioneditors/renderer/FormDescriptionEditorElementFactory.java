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

import org.eclipse.sirius.components.formdescriptioneditors.FormDescriptionEditor;
import org.eclipse.sirius.components.formdescriptioneditors.elements.FormDescriptionEditorElementProps;
import org.eclipse.sirius.components.forms.Group;
import org.eclipse.sirius.components.forms.renderer.FormElementFactory;
import org.eclipse.sirius.components.representations.IElementFactory;
import org.eclipse.sirius.components.representations.IProps;

/**
 * Used to instantiate the elements of the form description editor.
 *
 * @author arichard
 */
public class FormDescriptionEditorElementFactory implements IElementFactory {

    private final FormElementFactory formElementFactory;

    public FormDescriptionEditorElementFactory() {
        this.formElementFactory = new FormElementFactory();
    }

    @Override
    public Object instantiateElement(String type, IProps props, List<Object> children) {
        Object object = null;
        if (FormDescriptionEditorElementProps.TYPE.equals(type) && props instanceof FormDescriptionEditorElementProps) {
            object = this.instantiateFormDescriptionEditor((FormDescriptionEditorElementProps) props, children);
        } else {
            object = this.formElementFactory.instantiateElement(type, props, children);
        }
        return object;
    }

    private FormDescriptionEditor instantiateFormDescriptionEditor(FormDescriptionEditorElementProps props, List<Object> children) {
        // @formatter:off
        List<Group> groups = children.stream()
                .filter(Group.class::isInstance)
                .map(Group.class::cast)
                .collect(Collectors.toList());

        return FormDescriptionEditor.newFormDescriptionEditor(props.getId())
                .label(props.getLabel())
                .targetObjectId(props.getTargetObjectId())
                .descriptionId(props.getDescriptionId())
                .groups(groups)
                .build();
        // @formatter:on
    }

}
