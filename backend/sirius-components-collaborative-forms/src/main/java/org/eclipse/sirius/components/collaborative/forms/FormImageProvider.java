/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
package org.eclipse.sirius.components.collaborative.forms;

import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.IRepresentationImageProvider;
import org.eclipse.sirius.components.forms.Form;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.springframework.stereotype.Service;

/**
 * Provide the image representing a form.
 *
 * @author sbegaudeau
 */
@Service
public class FormImageProvider implements IRepresentationImageProvider {

    @Override
    public Optional<String> getImageURL(IRepresentation representation) {
        // @formatter:off
        return Optional.of(representation)
                .filter(Form.class::isInstance)
                .map(Form.class::cast)
                .map(form -> "/form-images/form.svg"); //$NON-NLS-1$
        // @formatter:on
    }

}
