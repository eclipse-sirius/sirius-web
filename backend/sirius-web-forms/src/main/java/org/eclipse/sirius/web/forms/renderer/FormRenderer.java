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
package org.eclipse.sirius.web.forms.renderer;

import java.util.Optional;

import org.eclipse.sirius.web.components.BaseRenderer;
import org.eclipse.sirius.web.components.Element;
import org.eclipse.sirius.web.forms.Form;
import org.slf4j.Logger;

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

    public FormRenderer(Logger logger) {
        this.baseRenderer = new BaseRenderer(new FormInstancePropsValidator(), new FormComponentPropsValidator(), new FormElementFactory(), logger);
    }

    public Form render(Element element) {
        // @formatter:off
        return Optional.of(this.baseRenderer.renderElement(element))
                .filter(Form.class::isInstance)
                .map(Form.class::cast)
                .orElse(null);
        // @fomatter:on
    }

}
