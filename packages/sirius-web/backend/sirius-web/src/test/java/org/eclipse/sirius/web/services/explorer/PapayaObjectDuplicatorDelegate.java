/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.services.explorer;

import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.papaya.NamedElement;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.web.application.views.explorer.services.DefaultObjectDuplicator;
import org.eclipse.sirius.web.application.views.explorer.services.api.DuplicationSettings;
import org.eclipse.sirius.web.application.views.explorer.services.api.IObjectDuplicator;
import org.eclipse.sirius.web.application.views.explorer.services.api.IObjectDuplicatorDelegate;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.springframework.stereotype.Service;

/**
 * Custom object duplication logic for Papaya named elements to give a distinct name to the new copy.
 *
 * @author pcdavid
 */
@Service
public class PapayaObjectDuplicatorDelegate implements IObjectDuplicatorDelegate {

    private final IMessageService messageService;

    public PapayaObjectDuplicatorDelegate(IMessageService messageService) {
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, EObject objectToDuplicate, EObject containerEObject, String containmentFeature, DuplicationSettings settings) {
        return objectToDuplicate instanceof NamedElement;
    }

    @Override
    public IStatus duplicateObject(IEditingContext editingContext, EObject objectToDuplicate, EObject containerEObject, String containmentFeature, DuplicationSettings settings) {
        var defaultResut = new DefaultObjectDuplicator(this.messageService).duplicateObject(editingContext, objectToDuplicate, containerEObject, containmentFeature, settings);
        if (defaultResut instanceof Success success) {
            var copiedObject = success.getParameters().get(IObjectDuplicator.NEW_OBJECT);
            if (copiedObject instanceof NamedElement namedElement) {
                namedElement.setName(namedElement.getName() + " (copy)");
            }
        }
        return defaultResut;
    }

}
