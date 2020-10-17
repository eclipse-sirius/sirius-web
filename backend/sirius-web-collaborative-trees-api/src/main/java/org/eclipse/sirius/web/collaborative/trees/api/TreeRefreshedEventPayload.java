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
package org.eclipse.sirius.web.collaborative.trees.api;

import java.text.MessageFormat;
import java.util.Objects;

import org.eclipse.sirius.web.annotations.graphql.GraphQLField;
import org.eclipse.sirius.web.annotations.graphql.GraphQLNonNull;
import org.eclipse.sirius.web.annotations.graphql.GraphQLObjectType;
import org.eclipse.sirius.web.services.api.dto.IPayload;
import org.eclipse.sirius.web.trees.Tree;

/**
 * Payload used to indicate that the tree has been refreshed.
 *
 * @author sbegaudeau
 */
@GraphQLObjectType
public final class TreeRefreshedEventPayload implements IPayload {
    private final Tree tree;

    public TreeRefreshedEventPayload(Tree tree) {
        this.tree = Objects.requireNonNull(tree);
    }

    @GraphQLField
    @GraphQLNonNull
    public Tree getTree() {
        return this.tree;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'tree: '{'id: {1}, label: {2}'}''}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.tree.getId(), this.tree.getLabel());
    }
}
