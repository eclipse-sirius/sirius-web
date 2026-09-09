/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceStrategy;
import org.eclipse.sirius.components.collaborative.api.IRepresentationRefreshPolicy;
import org.eclipse.sirius.components.collaborative.api.IRepresentationRefreshPolicyRegistry;
import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.collaborative.forms.api.IFormCreationService;
import org.eclipse.sirius.components.collaborative.forms.api.IFormEventProcessor;
import org.eclipse.sirius.components.collaborative.representations.api.IRepresentationRefresher;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.forms.Form;
import org.springframework.stereotype.Service;

/**
 * Used to refresh form representations.
 *
 * @author sbegaudeau
 */
@Service
public class FormRefresher implements IRepresentationRefresher {

    private final IObjectSearchService objectSearchService;

    private final IFormCreationService formCreationService;

    private final IRepresentationSearchService representationSearchService;

    private final IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry;

    private final IRepresentationPersistenceStrategy representationPersistenceStrategy;

    public FormRefresher(IObjectSearchService objectSearchService, IFormCreationService formCreationService, IRepresentationSearchService representationSearchService,
            IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry, IRepresentationPersistenceStrategy representationPersistenceStrategy) {
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.formCreationService = Objects.requireNonNull(formCreationService);
        this.representationSearchService = Objects.requireNonNull(representationSearchService);
        this.representationRefreshPolicyRegistry = Objects.requireNonNull(representationRefreshPolicyRegistry);
        this.representationPersistenceStrategy = Objects.requireNonNull(representationPersistenceStrategy);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IRepresentationEventProcessor representationEventProcessor, ChangeDescription changeDescription) {
        return representationEventProcessor instanceof IFormEventProcessor formEventProcessor
                && formEventProcessor.getRepresentation() instanceof Form form
                && (this.getRefreshPolicy(formEventProcessor).shouldRefresh(changeDescription) || this.isReloadRefresh(changeDescription, form));
    }

    @Override
    public void refresh(IEditingContext editingContext, IRepresentationEventProcessor representationEventProcessor, ChangeDescription changeDescription) {
        if (representationEventProcessor instanceof IFormEventProcessor formEventProcessor && formEventProcessor.getRepresentation() instanceof Form form) {
            if (this.getRefreshPolicy(formEventProcessor).shouldRefresh(changeDescription)) {
                var context = formEventProcessor.getFormContext();
                var object = this.objectSearchService.getObject(editingContext, form.getTargetObjectId()).orElse(context.object());
                var refreshedForm = this.formCreationService.create(editingContext, context.formDescription(), object, context);
                this.representationPersistenceStrategy.applyPersistenceStrategy(changeDescription.getCause(), editingContext, refreshedForm);
                formEventProcessor.update(changeDescription.getCause(), refreshedForm);
            } else if (this.isReloadRefresh(changeDescription, form)) {
                this.representationSearchService.findById(editingContext, form.getId(), Form.class)
                        .ifPresent(reloadedForm -> formEventProcessor.update(changeDescription.getCause(), reloadedForm));
            }
        }
    }

    private IRepresentationRefreshPolicy getRefreshPolicy(IFormEventProcessor formEventProcessor) {
        return this.representationRefreshPolicyRegistry.getRepresentationRefreshPolicy(formEventProcessor.getFormContext().formDescription())
                .orElse(changeDescription -> ChangeKind.SEMANTIC_CHANGE.equals(changeDescription.getKind()));
    }

    private boolean isReloadRefresh(ChangeDescription changeDescription, Form form) {
        return ChangeKind.RELOAD_REPRESENTATION.equals(changeDescription.getKind()) && changeDescription.getSourceId().equals(form.getId());
    }
}
