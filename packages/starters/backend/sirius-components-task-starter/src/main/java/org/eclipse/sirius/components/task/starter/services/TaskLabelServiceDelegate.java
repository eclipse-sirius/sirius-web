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
package org.eclipse.sirius.components.task.starter.services;

import java.util.List;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.sirius.components.core.api.ILabelServiceDelegate;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.core.api.IRepresentationMetadataProvider;
import org.eclipse.sirius.components.core.api.labels.StyledString;
import org.eclipse.sirius.components.emf.services.DefaultLabelService;
import org.eclipse.sirius.components.emf.services.LabelFeatureProviderRegistry;
import org.eclipse.sirius.components.task.TaskTag;
import org.eclipse.sirius.ext.emf.edit.EditingDomainServices;
import org.springframework.stereotype.Service;

/**
 * This class allows to override {@link IObjectSearchService} behavior.
 *
 * @author Laurent Fasani
 */
@Service
public class TaskLabelServiceDelegate extends DefaultLabelService implements ILabelServiceDelegate {

    private final EditingDomainServices editingDomainServices = new EditingDomainServices();

    public TaskLabelServiceDelegate(List<IRepresentationMetadataProvider> representationMetadataProviders, ComposedAdapterFactory composedAdapterFactory, LabelFeatureProviderRegistry labelFeatureProviderRegistry) {
        super(representationMetadataProviders, labelFeatureProviderRegistry, composedAdapterFactory, List.of());
    }

    @Override
    public boolean canHandle(Object object) {
        return object instanceof TaskTag;
    }

    @Override
    public String getLabel(Object object) {
        if (object instanceof TaskTag tag) {
            return this.editingDomainServices.getLabelProviderText(tag);
        } else {
            return super.getLabel(object);
        }
    }

    @Override
    public String getFullLabel(Object object) {
        if (object instanceof TaskTag tag) {
            return this.editingDomainServices.getLabelProviderText(tag);
        } else {
            return super.getFullLabel(object);
        }
    }

    @Override
    public Optional<String> getLabelField(Object object) {
        if (object instanceof TaskTag tag) {
            return Optional.of(this.editingDomainServices.getLabelProviderText(tag));
        } else {
            return super.getLabelField(object);
        }
    }

    @Override
    public StyledString getStyledLabel(Object object) {
        if (object instanceof TaskTag tag) {
            return StyledString.of(this.editingDomainServices.getLabelProviderText((EObject) object));
        } else {
            return StyledString.of(super.getLabel(object));
        }
    }
}
