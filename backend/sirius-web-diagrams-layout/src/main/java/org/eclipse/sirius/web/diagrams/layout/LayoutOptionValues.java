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

/**
 * The common values needed by both elk layout and incremental layout.
 *
 * @author gcoutable
 */
public final class LayoutOptionValues {

    /**
     * The default elk padding between a node and its content. This padding is set on the four direction: top, right,
     * bottom and left.
     */
    public static final double DEFAULT_ELK_PADDING = 12d;

    /**
     * The default elk padding between a node and its label. This padding is set on the four direction: top, right,
     * bottom and left.
     */
    public static final double DEFAULT_ELK_NODE_LABELS_PADDING = 5d;

    /**
     * The minimum height constraint value defined for a node in our elk configuration.
     */
    public static final int MIN_HEIGHT_CONSTRAINT = 35;

    /**
     * The minimum width constraint value defined for a node in our elk configuration.
     */
    public static final int MIN_WIDTH_CONSTRAINT = 50;

    /**
     * The value defined for NodeList in the elk configuration to set the padding top between a NodeList and its
     * NodeListItems.
     */
    public static final double NODE_LIST_ELK_PADDING_TOP = 13d;

    /**
     * The value defined for NodeList in the elk configuration to set the padding left between a NodeList and its
     * NodeListItems.
     */
    public static final double NODE_LIST_ELK_PADDING_LEFT = 0d;

    /**
     * The value defined for NodeList in the elk configuration to set the padding right between a NodeList and its
     * NodeListItems.
     */
    public static final double NODE_LIST_ELK_PADDING_RIGHT = 0d;

    /**
     * The value defined for NodeList in the elk configuration to set the gap between two containing NodeListItems.
     */
    public static final double NODE_LIST_ELK_NODE_NODE_GAP = 6d;

    /**
     * The value defined for NodeList in the elk configuration to set the padding left of the containing NodeListItem
     * labels.
     */
    public static final double NODE_LIST_ELK_NODE_LABELS_PADDING_LEFT = 6d;

    /**
     * The value defined for NodeList in the elk configuration to set the padding right of the containing NodeListItem
     * labels.
     */
    public static final double NODE_LIST_ELK_NODE_LABELS_PADDING_RIGHT = 12d;

    /**
     * The value defined for NodeList in the elk configuration to set the padding top between a NodeListItem to its
     * label.
     */
    public static final double NODE_LIST_ELK_NODE_LABELS_PADDING_TOP = 0d;

    /**
     * The value defined for NodeList in the elk configuration to set the padding bottom between a NodeListItem to its
     * label.
     */
    public static final double NODE_LIST_ELK_NODE_LABELS_PADDING_BOTTOM = 0d;

}
