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
 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getNodeDescription()
 * @model
 * @generated
 */
public interface NodeDescription extends DiagramElementDescription {
    /**
     * Returns the value of the '<em><b>Collapsible</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Collapsible</em>' attribute.
     * @see #setCollapsible(boolean)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getNodeDescription_Collapsible()
     * @model
     * @generated
     */
    boolean isCollapsible();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.NodeDescription#isCollapsible
     * <em>Collapsible</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Collapsible</em>' attribute.
     * @see #isCollapsible()
     * @generated
     */
    void setCollapsible(boolean value);

    /**
     * Returns the value of the '<em><b>Palette</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Palette</em>' containment reference.
     * @see #setPalette(NodePalette)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getNodeDescription_Palette()
     * @model containment="true"
     * @generated
     */
    NodePalette getPalette();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.NodeDescription#getPalette
     * <em>Palette</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Palette</em>' containment reference.
     * @see #getPalette()
     * @generated
     */
    void setPalette(NodePalette value);

    /**
     * Returns the value of the '<em><b>Children Layout Strategy</b></em>' containment reference. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Children Layout Strategy</em>' containment reference.
     * @see #setChildrenLayoutStrategy(LayoutStrategyDescription)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getNodeDescription_ChildrenLayoutStrategy()
     * @model containment="true"
     * @generated
     */
    LayoutStrategyDescription getChildrenLayoutStrategy();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.diagram.NodeDescription#getChildrenLayoutStrategy <em>Children Layout
     * Strategy</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Children Layout Strategy</em>' containment reference.
     * @see #getChildrenLayoutStrategy()
     * @generated
     */
    void setChildrenLayoutStrategy(LayoutStrategyDescription value);

    /**
     * Returns the value of the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Style</em>' containment reference.
     * @see #setStyle(NodeStyleDescription)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getNodeDescription_Style()
     * @model containment="true"
     * @generated
     */
    NodeStyleDescription getStyle();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.NodeDescription#getStyle
     * <em>Style</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Style</em>' containment reference.
     * @see #getStyle()
     * @generated
     */
    void setStyle(NodeStyleDescription value);

    /**
     * Returns the value of the '<em><b>Conditional Styles</b></em>' containment reference list. The list contents are
     * of type {@link org.eclipse.sirius.components.view.diagram.ConditionalNodeStyle}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Conditional Styles</em>' containment reference list.
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getNodeDescription_ConditionalStyles()
     * @model containment="true"
     * @generated
     */
    EList<ConditionalNodeStyle> getConditionalStyles();

    /**
     * Returns the value of the '<em><b>Children Descriptions</b></em>' containment reference list. The list contents
     * are of type {@link org.eclipse.sirius.components.view.diagram.NodeDescription}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Children Descriptions</em>' containment reference list.
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getNodeDescription_ChildrenDescriptions()
     * @model containment="true"
     * @generated
     */
    EList<NodeDescription> getChildrenDescriptions();

    /**
     * Returns the value of the '<em><b>Border Nodes Descriptions</b></em>' containment reference list. The list
     * contents are of type {@link org.eclipse.sirius.components.view.diagram.NodeDescription}. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Border Nodes Descriptions</em>' containment reference list.
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getNodeDescription_BorderNodesDescriptions()
     * @model containment="true"
     * @generated
     */
    EList<NodeDescription> getBorderNodesDescriptions();

    /**
     * Returns the value of the '<em><b>Reused Child Node Descriptions</b></em>' reference list. The list contents are
     * of type {@link org.eclipse.sirius.components.view.diagram.NodeDescription}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Reused Child Node Descriptions</em>' reference list.
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getNodeDescription_ReusedChildNodeDescriptions()
     * @model
     * @generated
     */
    EList<NodeDescription> getReusedChildNodeDescriptions();

    /**
     * Returns the value of the '<em><b>Reused Border Node Descriptions</b></em>' reference list. The list contents are
     * of type {@link org.eclipse.sirius.components.view.diagram.NodeDescription}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Reused Border Node Descriptions</em>' reference list.
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getNodeDescription_ReusedBorderNodeDescriptions()
     * @model
     * @generated
     */
    EList<NodeDescription> getReusedBorderNodeDescriptions();

    /**
     * Returns the value of the '<em><b>User Resizable</b></em>' attribute. The default value is <code>"true"</code>.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>User Resizable</em>' attribute.
     * @see #setUserResizable(boolean)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getNodeDescription_UserResizable()
     * @model default="true" required="true"
     * @generated
     */
    boolean isUserResizable();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.NodeDescription#isUserResizable <em>User
     * Resizable</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>User Resizable</em>' attribute.
     * @see #isUserResizable()
     * @generated
     */
    void setUserResizable(boolean value);

    /**
     * Returns the value of the '<em><b>Default Width Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Default Width Expression</em>' attribute.
     * @see #setDefaultWidthExpression(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getNodeDescription_DefaultWidthExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getDefaultWidthExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.diagram.NodeDescription#getDefaultWidthExpression <em>Default Width
     * Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Default Width Expression</em>' attribute.
     * @see #getDefaultWidthExpression()
     * @generated
     */
    void setDefaultWidthExpression(String value);

    /**
     * Returns the value of the '<em><b>Default Height Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Default Height Expression</em>' attribute.
     * @see #setDefaultHeightExpression(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getNodeDescription_DefaultHeightExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getDefaultHeightExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.diagram.NodeDescription#getDefaultHeightExpression <em>Default Height
     * Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Default Height Expression</em>' attribute.
     * @see #getDefaultHeightExpression()
     * @generated
     */
    void setDefaultHeightExpression(String value);

    /**
     * Returns the value of the '<em><b>Keep Aspect Ratio</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Keep Aspect Ratio</em>' attribute.
     * @see #setKeepAspectRatio(boolean)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getNodeDescription_KeepAspectRatio()
     * @model
     * @generated
     */
    boolean isKeepAspectRatio();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.NodeDescription#isKeepAspectRatio
     * <em>Keep Aspect Ratio</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Keep Aspect Ratio</em>' attribute.
     * @see #isKeepAspectRatio()
     * @generated
     */
    void setKeepAspectRatio(boolean value);

} // NodeDescription
