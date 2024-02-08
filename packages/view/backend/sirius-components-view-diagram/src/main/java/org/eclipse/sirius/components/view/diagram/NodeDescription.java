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

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Node Description</b></em>'. <!-- end-user-doc
 * -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.NodeDescription#isCollapsible <em>Collapsible</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.NodeDescription#getPalette <em>Palette</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.NodeDescription#getChildrenLayoutStrategy <em>Children Layout
 * Strategy</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.NodeDescription#getStyle <em>Style</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.NodeDescription#getConditionalStyles <em>Conditional
 * Styles</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.NodeDescription#getChildrenDescriptions <em>Children
 * Descriptions</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.NodeDescription#getBorderNodesDescriptions <em>Border Nodes
 * Descriptions</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.NodeDescription#getReusedChildNodeDescriptions <em>Reused Child
 * Node Descriptions</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.NodeDescription#getReusedBorderNodeDescriptions <em>Reused
 * Border Node Descriptions</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.NodeDescription#isUserResizable <em>User Resizable</em>}</li>
 * </ul>
 *
 * @model
 * @generated
 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getNodeDescription()
 */
public interface NodeDescription extends DiagramElementDescription {

    /**
     * Returns the value of the '<em><b>Collapsible</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Collapsible</em>' attribute.
     * @model
     * @generated
     * @see #setCollapsible(boolean)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getNodeDescription_Collapsible()
     */
    boolean isCollapsible();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.NodeDescription#isCollapsible
     * <em>Collapsible</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Collapsible</em>' attribute.
     * @generated
     * @see #isCollapsible()
     */
    void setCollapsible(boolean value);

    /**
     * Returns the value of the '<em><b>Palette</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Palette</em>' containment reference.
     * @model containment="true"
     * @generated
     * @see #setPalette(NodePalette)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getNodeDescription_Palette()
     */
    NodePalette getPalette();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.NodeDescription#getPalette
     * <em>Palette</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Palette</em>' containment reference.
     * @generated
     * @see #getPalette()
     */
    void setPalette(NodePalette value);

    /**
     * Returns the value of the '<em><b>Children Layout Strategy</b></em>' containment reference. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Children Layout Strategy</em>' containment reference.
     * @model containment="true"
     * @generated
     * @see #setChildrenLayoutStrategy(LayoutStrategyDescription)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getNodeDescription_ChildrenLayoutStrategy()
     */
    LayoutStrategyDescription getChildrenLayoutStrategy();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.diagram.NodeDescription#getChildrenLayoutStrategy <em>Children Layout
     * Strategy</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Children Layout Strategy</em>' containment reference.
     * @generated
     * @see #getChildrenLayoutStrategy()
     */
    void setChildrenLayoutStrategy(LayoutStrategyDescription value);

    /**
     * Returns the value of the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Style</em>' containment reference.
     * @model containment="true"
     * @generated
     * @see #setStyle(NodeStyleDescription)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getNodeDescription_Style()
     */
    NodeStyleDescription getStyle();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.NodeDescription#getStyle
     * <em>Style</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Style</em>' containment reference.
     * @generated
     * @see #getStyle()
     */
    void setStyle(NodeStyleDescription value);

    /**
     * Returns the value of the '<em><b>Conditional Styles</b></em>' containment reference list. The list contents are
     * of type {@link org.eclipse.sirius.components.view.diagram.ConditionalNodeStyle}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Conditional Styles</em>' containment reference list.
     * @model containment="true"
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getNodeDescription_ConditionalStyles()
     */
    EList<ConditionalNodeStyle> getConditionalStyles();

    /**
     * Returns the value of the '<em><b>Children Descriptions</b></em>' containment reference list. The list contents
     * are of type {@link org.eclipse.sirius.components.view.diagram.NodeDescription}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Children Descriptions</em>' containment reference list.
     * @model containment="true"
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getNodeDescription_ChildrenDescriptions()
     */
    EList<NodeDescription> getChildrenDescriptions();

    /**
     * Returns the value of the '<em><b>Border Nodes Descriptions</b></em>' containment reference list. The list
     * contents are of type {@link org.eclipse.sirius.components.view.diagram.NodeDescription}. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Border Nodes Descriptions</em>' containment reference list.
     * @model containment="true"
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getNodeDescription_BorderNodesDescriptions()
     */
    EList<NodeDescription> getBorderNodesDescriptions();

    /**
     * Returns the value of the '<em><b>Reused Child Node Descriptions</b></em>' reference list. The list contents are
     * of type {@link org.eclipse.sirius.components.view.diagram.NodeDescription}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Reused Child Node Descriptions</em>' reference list.
     * @model
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getNodeDescription_ReusedChildNodeDescriptions()
     */
    EList<NodeDescription> getReusedChildNodeDescriptions();

    /**
     * Returns the value of the '<em><b>Reused Border Node Descriptions</b></em>' reference list. The list contents are
     * of type {@link org.eclipse.sirius.components.view.diagram.NodeDescription}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Reused Border Node Descriptions</em>' reference list.
     * @model
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getNodeDescription_ReusedBorderNodeDescriptions()
     */
    EList<NodeDescription> getReusedBorderNodeDescriptions();

    /**
     * Returns the value of the '<em><b>User Resizable</b></em>' attribute. The default value is <code>"true"</code>.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>User Resizable</em>' attribute.
     * @model default="true" required="true"
     * @generated
     * @see #setUserResizable(boolean)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getNodeDescription_UserResizable()
     */
    boolean isUserResizable();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.NodeDescription#isUserResizable <em>User
     * Resizable</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>User Resizable</em>' attribute.
     * @generated
     * @see #isUserResizable()
     */
    void setUserResizable(boolean value);

    /**
     * Returns the value of the '<em><b>Default Width Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Default Width Expression</em>' attribute.
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     * @see #setDefaultWidthExpression(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getNodeDescription_DefaultWidthExpression()
     */
    String getDefaultWidthExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.diagram.NodeDescription#getDefaultWidthExpression <em>Default Width
     * Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Default Width Expression</em>' attribute.
     * @generated
     * @see #getDefaultWidthExpression()
     */
    void setDefaultWidthExpression(String value);

    /**
     * Returns the value of the '<em><b>Default Height Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Default Height Expression</em>' attribute.
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     * @see #setDefaultHeightExpression(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getNodeDescription_DefaultHeightExpression()
     */
    String getDefaultHeightExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.diagram.NodeDescription#getDefaultHeightExpression <em>Default Height
     * Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Default Height Expression</em>' attribute.
     * @generated
     * @see #getDefaultHeightExpression()
     */
    void setDefaultHeightExpression(String value);

    /**
     * Returns the value of the '<em><b>Keep Aspect Ratio</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Keep Aspect Ratio</em>' attribute.
     * @model
     * @generated
     * @see #setKeepAspectRatio(boolean)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getNodeDescription_KeepAspectRatio()
     */
    boolean isKeepAspectRatio();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.NodeDescription#isKeepAspectRatio
     * <em>Keep Aspect Ratio</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Keep Aspect Ratio</em>' attribute.
     * @generated
     * @see #isKeepAspectRatio()
     */
    void setKeepAspectRatio(boolean value);

    /**
     * Returns the value of the '<em><b>Inside Label</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Inside Label</em>' containment reference.
     * @model containment="true"
     * @generated
     * @see #setInsideLabel(InsideLabelDescription)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getNodeDescription_InsideLabel()
     */
    InsideLabelDescription getInsideLabel();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.NodeDescription#getInsideLabel
     * <em>Inside Label</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Inside Label</em>' containment reference.
     * @generated
     * @see #getInsideLabel()
     */
    void setInsideLabel(InsideLabelDescription value);

    /**
     * Returns the value of the '<em><b>Outside Labels</b></em>' containment reference list. The list contents are of
     * type {@link org.eclipse.sirius.components.view.diagram.OutsideLabelDescription}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Outside Labels</em>' containment reference list.
     * @model containment="true"
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getNodeDescription_OutsideLabels()
     */
    EList<OutsideLabelDescription> getOutsideLabels();

} // NodeDescription
