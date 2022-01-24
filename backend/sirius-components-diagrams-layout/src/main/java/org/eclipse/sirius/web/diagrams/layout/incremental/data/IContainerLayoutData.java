/*******************************************************************************
 * Copyright (c) 2021 THALES GLOBAL SERVICES.
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
package org.eclipse.sirius.web.diagrams.layout.incremental.data;

import java.util.List;

import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.Size;

/**
 * The definition of a data structure for elements that can contain nodes.
 *
 * @author wpiers
 */
public interface IContainerLayoutData extends ILayoutData {

    Position getPosition();

    void setPosition(Position position);

    Size getSize();

    void setSize(Size size);

    List<NodeLayoutData> getChildrenNodes();

    void setChildrenNodes(List<NodeLayoutData> nodes);

    Position getAbsolutePosition();
}
