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

import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import org.eclipse.sirius.components.collaborative.api.IStdDeserializerProvider;
import org.eclipse.sirius.components.tables.ICell;
import org.springframework.stereotype.Service;

/**
 * The deserializer provider for ICell.
 *
 * @author frouene
 */
@Service
public class CellStdDeserializerProvider implements IStdDeserializerProvider<ICell> {

    @Override
    public StdDeserializer<ICell> getDeserializer() {
        return new ICellDeserializer();
    }

    @Override
    public Class<ICell> getType() {
        return ICell.class;
    }

}
