/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.validation.render;

import java.util.Optional;

import org.eclipse.sirius.web.representations.BaseRenderer;
import org.eclipse.sirius.web.representations.Element;
import org.eclipse.sirius.web.validation.Validation;

/**
 * Renderer used to create the validation from its description and some variables.
 *
 * @author gcoutable
 */
public class ValidationRenderer {

    private final BaseRenderer baseRenderer;

    public ValidationRenderer() {
        this.baseRenderer = new BaseRenderer(new ValidationInstancePropsValidator(), new ValidationComponentPropsValidator(), new ValidationElementFactory());
    }

    public Validation render(Element element) {
        // @formatter:off
        return Optional.of(this.baseRenderer.renderElement(element))
                .filter(Validation.class::isInstance)
                .map(Validation.class::cast)
                .orElse(null);
        // @formatter:on
    }

}
