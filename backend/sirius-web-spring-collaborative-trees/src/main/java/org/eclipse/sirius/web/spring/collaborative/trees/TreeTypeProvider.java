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
package org.eclipse.sirius.web.spring.collaborative.trees;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.sirius.web.graphql.utils.providers.GraphQLObjectTypeProvider;
import org.eclipse.sirius.web.graphql.utils.schema.ITypeProvider;
import org.eclipse.sirius.web.trees.Tree;
import org.eclipse.sirius.web.trees.TreeItem;
import org.eclipse.sirius.web.trees.description.TreeDescription;
import org.springframework.stereotype.Service;

import graphql.schema.GraphQLType;

/**
 * This class is used to create all the definitions of the types related to the tree-based representation.
 *
 * @author sbegaudeau
 */
@Service
public class TreeTypeProvider implements ITypeProvider {
    public static final String TREE_TYPE = "Tree"; //$NON-NLS-1$

    public static final String TREE_ITEM_TYPE = "TreeItem"; //$NON-NLS-1$

    public static final String TARGET_FIELD = "target"; //$NON-NLS-1$

    private final GraphQLObjectTypeProvider graphQLObjectTypeProvider = new GraphQLObjectTypeProvider();

    @Override
    public Set<GraphQLType> getTypes() {
        // @formatter:off
        List<Class<?>> objectClasses = List.of(
            Tree.class,
            TreeItem.class,
            TreeDescription.class
        );
        var graphQLObjectTypes = objectClasses.stream()
                .map(this.graphQLObjectTypeProvider::getType)
                .collect(Collectors.toUnmodifiableList());
        // @formatter:on

        Set<GraphQLType> types = new LinkedHashSet<>();
        types.addAll(graphQLObjectTypes);
        return types;
    }
}
