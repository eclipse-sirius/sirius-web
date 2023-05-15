/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.diagrams.layout.api.configuration;

import java.util.List;

public final class NodeLayoutConfiguration implements IParentLayoutConfiguration {

    @Override
    public String getDisplayName() {
        return "node";
    }

    @Override
    public List<NodeLayoutConfiguration> getChildNodeLayoutConfigurations() {
        return List.of();
    }

}
