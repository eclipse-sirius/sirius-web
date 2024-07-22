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
package org.eclipse.sirius.components.view.emf.form.api;

import java.util.Optional;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.view.form.FormDescription;
import org.eclipse.sirius.components.view.form.FormElementDescription;

/**
 * Used to find information from a view based form descriptions.
 *
 * @author Arthur Daussy
 */
public interface IViewFormDescriptionSearchService {

    Optional<FormDescription> findById(IEditingContext editingContext, String formDescriptionId);

    Optional<FormElementDescription> findFormElementDescriptionById(IEditingContext editingContext, String formElementDescriptionId);

    /**
     * No operation implementation.
     *
     * @author Arthur Daussy
     */
    class NoOp implements IViewFormDescriptionSearchService {

        @Override
        public Optional<FormDescription> findById(IEditingContext editingContext, String formDescriptionId) {
            return Optional.empty();
        }

        @Override
        public Optional<FormElementDescription> findFormElementDescriptionById(IEditingContext editingContext, String formElementDescriptionId) {
            return Optional.empty();
        }

    }
}
