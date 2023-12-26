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
package org.eclipse.sirius.components.task.starter.configuration;

import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IObjectServiceDelegate;
import org.eclipse.sirius.components.emf.services.DefaultObjectService;
import org.eclipse.sirius.components.emf.services.LabelFeatureProviderRegistry;
import org.eclipse.sirius.components.emf.services.api.IEMFKindService;
import org.eclipse.sirius.components.task.TaskTag;
import org.eclipse.sirius.ext.emf.edit.EditingDomainServices;
import org.springframework.stereotype.Service;

/**
 * This class allows to override {@link IObjectService} behavior.
 *
 * @author Laurent Fasani
 */
@Service
public class TaskObjectServiceDelegate extends DefaultObjectService implements IObjectServiceDelegate {

    private EditingDomainServices editingDomainServices = new EditingDomainServices();

    public TaskObjectServiceDelegate(IEMFKindService emfKindService, ComposedAdapterFactory composedAdapterFactory, LabelFeatureProviderRegistry labelFeatureProviderRegistry) {
        super(emfKindService, composedAdapterFactory, labelFeatureProviderRegistry);
    }

    @Override
    public boolean canHandle(Object object) {
        return object instanceof TaskTag;
    }

    @Override
    public boolean canHandle(IEditingContext editingContext) {
        return true;
    }

    @Override
    public String getLabel(Object object) {
        if (object instanceof TaskTag tag) {
            return this.editingDomainServices.getLabelProviderText((EObject) object);
        } else {
            return super.getLabel(object);
        }
    }

    @Override
    public String getFullLabel(Object object) {
        if (object instanceof TaskTag tag) {
            return this.editingDomainServices.getLabelProviderText((EObject) object);
        } else {
            return super.getFullLabel(object);
        }
    }

    @Override
    public Optional<String> getLabelField(Object object) {
        if (object instanceof TaskTag tag) {
            return Optional.of(this.editingDomainServices.getLabelProviderText((EObject) object));
        } else {
            return super.getLabelField(object);
        }
    }
}
