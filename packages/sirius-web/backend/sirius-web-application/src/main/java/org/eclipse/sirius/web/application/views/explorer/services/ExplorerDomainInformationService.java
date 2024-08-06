/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

package org.eclipse.sirius.web.application.views.explorer.services;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;
import org.eclipse.sirius.web.application.views.explorer.services.api.IExplorerDomainInformationService;
import org.springframework.stereotype.Service;

/**
 * Service used to get meta-model information from Explorer.
 *
 * @author lfasani
 */
@Service
public class ExplorerDomainInformationService implements IExplorerDomainInformationService {
    private final IObjectService objectService;
    private final IFeedbackMessageService feedbackMessageService;
    public ExplorerDomainInformationService(IObjectService objectService, IFeedbackMessageService feedbackMessageService) {
        this.objectService = objectService;
        this.feedbackMessageService = feedbackMessageService;
    }

    @Override
    public Optional<List<String>> getContainmentFeatureNames(IEditingContext editingContext, String containerId, String containedObjectId) {
        Optional<Object> containerOpt = this.objectService.getObject(editingContext, containerId);
        Optional<Object> containedObjectOpt = this.objectService.getObject(editingContext, containedObjectId);

        Optional<List<String>> containmentFeatureNamesOpt = Optional.empty();
        if (containerOpt.isEmpty()) {
            this.feedbackMessageService.addFeedbackMessage(new Message(MessageFormat.format("The object with id \"{0}\" does not exist", containerId), MessageLevel.ERROR));
        } else if (containedObjectOpt.isEmpty()) {
            this.feedbackMessageService.addFeedbackMessage(new Message(MessageFormat.format("The object with id \"{0}\" does not exist", containedObjectId), MessageLevel.ERROR));
        } else if (containerOpt.get() instanceof EObject containerObj && containedObjectOpt.get() instanceof EObject containedObj) {
            EClass containedObjectEClass = containedObj.eClass();
            return Optional.of(containerObj.eClass().getEAllContainments().stream()
                    .filter(eReference -> containedObjectEClass.equals(eReference.getEReferenceType()))
                    .map(eReference -> eReference.getName())
                    .toList());
        }
        return Optional.empty();
    }
}
