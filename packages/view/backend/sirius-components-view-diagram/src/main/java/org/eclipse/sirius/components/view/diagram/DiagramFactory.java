/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
package org.eclipse.sirius.components.view.diagram;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a create method for each non-abstract class of
 * the model. <!-- end-user-doc -->
 *
 * @generated
 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage
 */
public interface DiagramFactory extends EFactory {

    /**
     * The singleton instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    DiagramFactory eINSTANCE = org.eclipse.sirius.components.view.diagram.impl.DiagramFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Description</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Description</em>'.
     * @generated
     */
    DiagramDescription createDiagramDescription();

    /**
     * Returns a new object of class '<em>Node Description</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Node Description</em>'.
     * @generated
     */
    NodeDescription createNodeDescription();

    /**
     * Returns a new object of class '<em>Edge Description</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Edge Description</em>'.
     * @generated
     */
    EdgeDescription createEdgeDescription();

    /**
     * Returns a new object of class '<em>List Layout Strategy Description</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>List Layout Strategy Description</em>'.
     * @generated
     */
    ListLayoutStrategyDescription createListLayoutStrategyDescription();

    /**
     * Returns a new object of class '<em>Free Form Layout Strategy Description</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Free Form Layout Strategy Description</em>'.
     * @generated
     */
    FreeFormLayoutStrategyDescription createFreeFormLayoutStrategyDescription();

    /**
     * Returns a new object of class '<em>Label Description</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Label Description</em>'.
     * @generated
     */
    LabelDescription createLabelDescription();

    /**
     * Returns a new object of class '<em>Inside Label Description</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Inside Label Description</em>'.
     * @generated
     */
    InsideLabelDescription createInsideLabelDescription();

    /**
     * Returns a new object of class '<em>Outside Label Description</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Outside Label Description</em>'.
     * @generated
     */
    OutsideLabelDescription createOutsideLabelDescription();

    /**
     * Returns a new object of class '<em>Inside Label Style</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Inside Label Style</em>'.
     * @generated
     */
    InsideLabelStyle createInsideLabelStyle();

    /**
     * Returns a new object of class '<em>Outside Label Style</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Outside Label Style</em>'.
     * @generated
     */
    OutsideLabelStyle createOutsideLabelStyle();

    /**
     * Returns a new object of class '<em>Conditional Node Style</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Conditional Node Style</em>'.
     * @generated
     */
    ConditionalNodeStyle createConditionalNodeStyle();

    /**
     * Returns a new object of class '<em>Conditional Inside Label Style</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Conditional Inside Label Style</em>'.
     * @generated
     */
    ConditionalInsideLabelStyle createConditionalInsideLabelStyle();

    /**
     * Returns a new object of class '<em>Conditional Outside Label Style</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Conditional Outside Label Style</em>'.
     * @generated
     */
    ConditionalOutsideLabelStyle createConditionalOutsideLabelStyle();

    /**
     * Returns a new object of class '<em>Rectangular Node Style Description</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Rectangular Node Style Description</em>'.
     * @generated
     */
    RectangularNodeStyleDescription createRectangularNodeStyleDescription();

    /**
     * Returns a new object of class '<em>Image Node Style Description</em>'. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return a new object of class '<em>Image Node Style Description</em>'.
     * @generated
     */
    ImageNodeStyleDescription createImageNodeStyleDescription();

    /**
     * Returns a new object of class '<em>Icon Label Node Style Description</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Icon Label Node Style Description</em>'.
     * @generated
     */
    IconLabelNodeStyleDescription createIconLabelNodeStyleDescription();

    /**
     * Returns a new object of class '<em>Edge Style</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Edge Style</em>'.
     * @generated
     */
    EdgeStyle createEdgeStyle();

    /**
     * Returns a new object of class '<em>Conditional Edge Style</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Conditional Edge Style</em>'.
     * @generated
     */
    ConditionalEdgeStyle createConditionalEdgeStyle();

    /**
     * Returns a new object of class '<em>Palette</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Palette</em>'.
     * @generated
     */
    DiagramPalette createDiagramPalette();

    /**
     * Returns a new object of class '<em>Node Palette</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Node Palette</em>'.
     * @generated
     */
    NodePalette createNodePalette();

    /**
     * Returns a new object of class '<em>Edge Palette</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Edge Palette</em>'.
     * @generated
     */
    EdgePalette createEdgePalette();

    /**
     * Returns a new object of class '<em>Delete Tool</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Delete Tool</em>'.
     * @generated
     */
    DeleteTool createDeleteTool();

    /**
     * Returns a new object of class '<em>Drop Tool</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Drop Tool</em>'.
     * @generated
     */
    DropTool createDropTool();

    /**
     * Returns a new object of class '<em>Edge Tool</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Edge Tool</em>'.
     * @generated
     */
    EdgeTool createEdgeTool();

    /**
     * Returns a new object of class '<em>Label Edit Tool</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Label Edit Tool</em>'.
     * @generated
     */
    LabelEditTool createLabelEditTool();

    /**
     * Returns a new object of class '<em>Node Tool</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Node Tool</em>'.
     * @generated
     */
    NodeTool createNodeTool();

    /**
     * Returns a new object of class '<em>Source Edge End Reconnection Tool</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Source Edge End Reconnection Tool</em>'.
     * @generated
     */
    SourceEdgeEndReconnectionTool createSourceEdgeEndReconnectionTool();

    /**
     * Returns a new object of class '<em>Target Edge End Reconnection Tool</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Target Edge End Reconnection Tool</em>'.
     * @generated
     */
    TargetEdgeEndReconnectionTool createTargetEdgeEndReconnectionTool();

    /**
     * Returns a new object of class '<em>Create View</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Create View</em>'.
     * @generated
     */
    CreateView createCreateView();

    /**
     * Returns a new object of class '<em>Delete View</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Delete View</em>'.
     * @generated
     */
    DeleteView createDeleteView();

    /**
     * Returns a new object of class '<em>Selection Description</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Selection Description</em>'.
     * @generated
     */
    SelectionDescription createSelectionDescription();

    /**
     * Returns a new object of class '<em>Tool Section</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Tool Section</em>'.
     * @generated
     */
    DiagramToolSection createDiagramToolSection();

    /**
     * Returns a new object of class '<em>Node Tool Section</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Node Tool Section</em>'.
     * @generated
     */
    NodeToolSection createNodeToolSection();

    /**
     * Returns a new object of class '<em>Edge Tool Section</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Edge Tool Section</em>'.
     * @generated
     */
    EdgeToolSection createEdgeToolSection();

    /**
     * Returns a new object of class '<em>Drop Node Tool</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Drop Node Tool</em>'.
     * @generated
     */
    DropNodeTool createDropNodeTool();

    /**
     * Returns the package supported by this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the package supported by this factory.
     * @generated
     */
    DiagramPackage getDiagramPackage();

} // DiagramFactory
