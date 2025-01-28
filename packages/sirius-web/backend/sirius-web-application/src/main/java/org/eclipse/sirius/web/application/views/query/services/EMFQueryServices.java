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
package org.eclipse.sirius.web.application.views.query.services;

import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;

/**
 * Custom services to manipulate EMF objects.
 *
 * @author arichard
 */
public class EMFQueryServices {

    private final IFeedbackMessageService feedbackMessageService;

    public EMFQueryServices(IFeedbackMessageService feedbackMessageService) {
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
    }

    public void eSet(EObject object, EStructuralFeature eStructuralFeature, Object newValue) {
        object.eSet(eStructuralFeature, newValue);
    }

    public void eSet(EObject object, String eStructuralFeatureName, Object newValue) {
        var eStructuralFeature = object.eClass().getEStructuralFeature(eStructuralFeatureName);
        if (eStructuralFeature != null) {
            object.eSet(eStructuralFeature, newValue);
        } else {
            var message = String.format("The feature %s has not been found on the type %s", eStructuralFeatureName, object.eClass().getName());
            this.feedbackMessageService.addFeedbackMessage(new Message(message, MessageLevel.ERROR));
        }
    }

    public void eUnset(EObject object, EStructuralFeature eStructuralFeature) {
        object.eUnset(eStructuralFeature);
    }

    public void eUnset(EObject object, String eStructuralFeatureName) {
        var eStructuralFeature = object.eClass().getEStructuralFeature(eStructuralFeatureName);
        if (eStructuralFeature != null) {
            object.eUnset(eStructuralFeature);
        } else {
            var message = String.format("The feature %s has not been found on the type %s", eStructuralFeatureName, object.eClass().getName());
            this.feedbackMessageService.addFeedbackMessage(new Message(message, MessageLevel.ERROR));
        }
    }
}
