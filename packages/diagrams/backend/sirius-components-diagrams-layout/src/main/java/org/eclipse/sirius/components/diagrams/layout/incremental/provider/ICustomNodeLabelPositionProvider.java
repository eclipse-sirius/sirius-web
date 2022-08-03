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
package org.eclipse.sirius.components.diagrams.layout.incremental.provider;

import java.util.Optional;

import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.layout.ISiriusWebLayoutConfigurator;

/**
 * Used to customized the position of the node label for the ELK and incremental layout.
 *
 * @author lfasani
 */
public interface ICustomNodeLabelPositionProvider {

    Optional<Position> getLabelPosition(ISiriusWebLayoutConfigurator layoutConfigurator, Size initialLabelSize, Size nodeSize, String nodeType, INodeStyle nodeStyle);
}
