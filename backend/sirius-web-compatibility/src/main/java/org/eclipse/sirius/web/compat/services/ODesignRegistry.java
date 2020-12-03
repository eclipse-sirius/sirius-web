/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.compat.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.sirius.viewpoint.description.Group;
import org.eclipse.sirius.web.compat.services.api.IODesignRegistry;

/**
 * Registry containing all the odesign files used by the server.
 *
 * @author sbegaudeau
 */
public class ODesignRegistry implements IODesignRegistry {

    private final List<Group> groups = new ArrayList<>();

    public void add(Group group) {
        this.groups.add(group);
    }

    @Override
    public List<Group> getODesigns() {
        return Collections.unmodifiableList(this.groups);
    }
}
