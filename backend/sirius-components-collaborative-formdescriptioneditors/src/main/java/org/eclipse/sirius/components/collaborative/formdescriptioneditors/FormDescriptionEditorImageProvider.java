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
package org.eclipse.sirius.components.collaborative.formdescriptioneditors;

import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.IRepresentationImageProvider;
import org.eclipse.sirius.components.formdescriptioneditors.FormDescriptionEditor;
import org.springframework.stereotype.Service;

/**
 * Provide the image representing a form description editor.
 *
 * @author arichard
 */
@Service
public class FormDescriptionEditorImageProvider implements IRepresentationImageProvider {

    @Override
    public Optional<String> getImageURL(String kind) {
        if (FormDescriptionEditor.KIND.equals(kind)) {
            return Optional.of("/formdescriptioneditor-images/formDescriptionEditor.svg"); //$NON-NLS-1$
        }
        return Optional.empty();
    }

}
