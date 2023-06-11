/*******************************************************************************
 * Copyright (c)  2023 Obeo.
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
package org.eclipse.sirius.components.collaborative.dynamicdialogs.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.sirius.components.dynamicdialogs.description.DynamicDialogDescription;

/**
 * Registry containing all the dynamic dialog descriptions.
 *
 * @author lfasani
 */
public class DynamicDialogDescriptionRegistry  {

    private final Map<String, DynamicDialogDescription> id2dynamicDialogDescriptions = new HashMap<>();

    public void add(DynamicDialogDescription dynamicDialogDescription) {
        this.id2dynamicDialogDescriptions.put(dynamicDialogDescription.getId(), dynamicDialogDescription);
    }

    public List<DynamicDialogDescription> getDynamicDialogDescriptions() {
        return this.id2dynamicDialogDescriptions.values().stream().toList();
    }
    public DynamicDialogDescription getDynamicDialogDescription(String dynamicDialogDescriptionId) {
        return this.id2dynamicDialogDescriptions.get(dynamicDialogDescriptionId);
    }

}
