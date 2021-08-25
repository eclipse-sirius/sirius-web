/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.diagrams.layout;

import org.eclipse.elk.core.util.IGraphElementVisitor;
import org.eclipse.elk.graph.ElkGraphElement;
import org.eclipse.elk.graph.properties.IPropertyHolder;

/**
 * Interface that can configure layout options based on the {@code id} and {@code type} attributes of diagram elements.
 *
 * @author arichard
 */
public interface ISiriusWebLayoutConfigurator extends IGraphElementVisitor {

    /**
     * Configure layout options for all model elements with the given type.
     */
    IPropertyHolder configureByType(String type);

    IPropertyHolder configureByElementClass(Class<? extends ElkGraphElement> elementClass);

}
