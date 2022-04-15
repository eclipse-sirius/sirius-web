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
package org.eclipse.sirius.components.emf.view;

import java.util.List;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.view.View;

/**
 * Interface for ViewConverter services.
 *
 * @author fbarbin
 */
public interface IViewConverter {

    /**
     * Extract and convert the {@link IRepresentationDescription} from a {@link View} model.
     */
    List<IRepresentationDescription> convert(View view, List<EPackage> visibleEPackages);
}
