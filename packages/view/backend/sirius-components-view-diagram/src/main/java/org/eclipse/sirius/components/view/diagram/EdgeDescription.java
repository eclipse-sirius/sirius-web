/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Edge Description</b></em>'. <!-- end-user-doc
 * -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.EdgeDescription#getBeginLabelExpression <em>Begin Label
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.EdgeDescription#getEndLabelExpression <em>End Label
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.EdgeDescription#isIsDomainBasedEdge <em>Is Domain Based
 * Edge</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.EdgeDescription#getPalette <em>Palette</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.EdgeDescription#getSourceNodeDescriptions <em>Source Node
 * Descriptions</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.EdgeDescription#getTargetNodeDescriptions <em>Target Node
 * Descriptions</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.EdgeDescription#getSourceNodesExpression <em>Source Nodes
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.EdgeDescription#getTargetNodesExpression <em>Target Nodes
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.EdgeDescription#getStyle <em>Style</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.EdgeDescription#getConditionalStyles <em>Conditional
 * Styles</em>}</li>
 * </ul>
 *
 * @model
 * @generated
 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgeDescription()
 */
public interface EdgeDescription extends DiagramElementDescription {

    /**
     * Returns the value of the '<em><b>Begin Label Expression</b></em>' attribute. The default value is
     * <code>""</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Begin Label Expression</em>' attribute.
     * @see #setBeginLabelExpression(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgeDescription_BeginLabelExpression()
     * @model default="" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getBeginLabelExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.EdgeDescription#getBeginLabelExpression
     * <em>Begin Label Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Begin Label Expression</em>' attribute.
     * @see #getBeginLabelExpression()
     * @generated
     */
    void setBeginLabelExpression(String value);

    /**
     * Returns the value of the '<em><b>Center Label Expression</b></em>' attribute. The default value is
     * <code>"aql:self.name"</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Center Label Expression</em>' attribute.
     * @see #setCenterLabelExpression(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgeDescription_CenterLabelExpression()
     * @model default="aql:self.name" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getCenterLabelExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.EdgeDescription#getCenterLabelExpression
     * <em>Center Label Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Center Label Expression</em>' attribute.
     * @see #getCenterLabelExpression()
     * @generated
     */
    void setCenterLabelExpression(String value);

    /**
     * Returns the value of the '<em><b>End Label Expression</b></em>' attribute. The default value is <code>""</code>.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>End Label Expression</em>' attribute.
     * @see #setEndLabelExpression(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgeDescription_EndLabelExpression()
     * @model default="" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getEndLabelExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.EdgeDescription#getEndLabelExpression
     * <em>End Label Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>End Label Expression</em>' attribute.
     * @see #getEndLabelExpression()
     * @generated
     */
    void setEndLabelExpression(String value);

    /**
     * Returns the value of the '<em><b>Is Domain Based Edge</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Is Domain Based Edge</em>' attribute.
     * @see #setIsDomainBasedEdge(boolean)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgeDescription_IsDomainBasedEdge()
     * @model
     * @generated
     */
    boolean isIsDomainBasedEdge();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.EdgeDescription#isIsDomainBasedEdge
     * <em>Is Domain Based Edge</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Is Domain Based Edge</em>' attribute.
     * @see #isIsDomainBasedEdge()
     * @generated
     */
    void setIsDomainBasedEdge(boolean value);

    /**
     * Returns the value of the '<em><b>Palette</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Palette</em>' containment reference.
     * @see #setPalette(EdgePalette)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgeDescription_Palette()
     * @model containment="true"
     * @generated
     */
    EdgePalette getPalette();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.EdgeDescription#getPalette
     * <em>Palette</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Palette</em>' containment reference.
     * @see #getPalette()
     * @generated
     */
    void setPalette(EdgePalette value);

    /**
     * Returns the value of the '<em><b>Source Descriptions</b></em>' reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.view.diagram.DiagramElementDescription}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Source Descriptions</em>' reference list.
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgeDescription_SourceDescriptions()
     * @model required="true"
     * @generated
     */
    EList<DiagramElementDescription> getSourceDescriptions();

    /**
     * Returns the value of the '<em><b>Target Descriptions</b></em>' reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.view.diagram.DiagramElementDescription}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Target Descriptions</em>' reference list.
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgeDescription_TargetDescriptions()
     * @model required="true"
     * @generated
     */
    EList<DiagramElementDescription> getTargetDescriptions();

    /**
     * Returns the value of the '<em><b>Source Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Source Expression</em>' attribute.
     * @see #setSourceExpression(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgeDescription_SourceExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getSourceExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.EdgeDescription#getSourceExpression
     * <em>Source Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Source Expression</em>' attribute.
     * @see #getSourceExpression()
     * @generated
     */
    void setSourceExpression(String value);

    /**
     * Returns the value of the '<em><b>Target Expression</b></em>' attribute. The default value is
     * <code>"aql:self.eCrossReferences()"</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Target Expression</em>' attribute.
     * @see #setTargetExpression(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgeDescription_TargetExpression()
     * @model default="aql:self.eCrossReferences()" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getTargetExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.EdgeDescription#getTargetExpression
     * <em>Target Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Target Expression</em>' attribute.
     * @see #getTargetExpression()
     * @generated
     */
    void setTargetExpression(String value);

    /**
     * Returns the value of the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Style</em>' containment reference.
     * @model containment="true"
     * @generated
     * @see #setStyle(EdgeStyle)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgeDescription_Style()
     */
    EdgeStyle getStyle();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.EdgeDescription#getStyle
     * <em>Style</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Style</em>' containment reference.
     * @see #getStyle()
     * @generated
     */
    void setStyle(EdgeStyle value);

    /**
     * Returns the value of the '<em><b>Conditional Styles</b></em>' containment reference list. The list contents are
     * of type {@link org.eclipse.sirius.components.view.diagram.ConditionalEdgeStyle}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Conditional Styles</em>' containment reference list.
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgeDescription_ConditionalStyles()
     * @model containment="true"
     * @generated
     */
    EList<ConditionalEdgeStyle> getConditionalStyles();

} // EdgeDescription
