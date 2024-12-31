/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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
package org.eclipse.sirius.components.view.emf.table;

import java.util.List;

import org.eclipse.sirius.components.emf.tables.CursorBasedNavigationServices;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.emf.IJavaServiceProvider;
import org.eclipse.sirius.components.view.table.TableDescription;
import org.springframework.stereotype.Service;

/**
 * Used to provide Java services for the Table related views.
 *
 * @author frouene
 */
@Service
public class TableJavaServiceProvider implements IJavaServiceProvider {

    @Override
    public List<Class<?>> getServiceClasses(View view) {
        boolean isTableRelatedView = view.getDescriptions().stream()
                .anyMatch(TableDescription.class::isInstance);

        if (isTableRelatedView) {
            return List.of(CursorBasedNavigationServices.class);
        }
        return List.of();
    }

}
