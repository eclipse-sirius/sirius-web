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
package org.eclipse.sirius.components.forms.renderer;

import java.util.List;
import java.util.Optional;

import org.eclipse.sirius.components.forms.Form;
import org.eclipse.sirius.components.representations.BaseRenderer;
import org.eclipse.sirius.components.representations.Element;

/**
 * Renderer used to create the form from its description and some variables.
 * <p>
 * It will delegate most of its behavior to the abstract renderer which will process the tree of elements to render. The
 * form renderer will mostly be used in order to let the abstract renderer delegate some form-specific behavior such as
 * the instantiation of the form concrete types and the validation of the properties of both the form elements and the
 * form components.
 * </p>
 *
 * @author sbegaudeau
 */
public class FormRenderer {

    private final BaseRenderer baseRenderer;

    public FormRenderer(List<IWidgetDescriptor> widgetDescriptors) {
        this.baseRenderer = new BaseRenderer(new FormInstancePropsValidator(widgetDescriptors), new FormComponentPropsValidator(widgetDescriptors), new FormElementFactory(widgetDescriptors));
    }

    public Form render(Element element) {
        // @formatter:off
        return Optional.of(this.baseRenderer.renderElement(element))
                .filter(Form.class::isInstance)
                .map(Form.class::cast)
                .orElse(null);
        // @formatter:on
    }

}
