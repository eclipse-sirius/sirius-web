/*******************************************************************************
 * Copyright (c) 2024 CEA LIST.
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
package org.eclipse.sirius.components.collaborative.tables;

import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.IRepresentationImageProvider;
import org.eclipse.sirius.components.tables.Table;
import org.springframework.stereotype.Service;

/**
 * Provides the image representing a table.
 *
 * @author frouene
 */
@Service
public class TableImageProvider implements IRepresentationImageProvider {

    @Override
    public Optional<String> getImageURL(String kind) {
        if (Table.KIND.equals(kind)) {
            return Optional.of("/table-images/table.svg");
        }
        return Optional.empty();
    }
}
