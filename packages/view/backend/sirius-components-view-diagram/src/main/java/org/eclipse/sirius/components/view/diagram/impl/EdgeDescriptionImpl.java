/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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
package org.eclipse.sirius.components.view.diagram.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.components.view.diagram.ConditionalEdgeStyle;
import org.eclipse.sirius.components.view.diagram.DiagramElementDescription;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.EdgePalette;
import org.eclipse.sirius.components.view.diagram.EdgeStyle;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Edge Description</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgeDescriptionImpl#getBeginLabelExpression <em>Begin
 * Label Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgeDescriptionImpl#getEndLabelExpression <em>End Label
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgeDescriptionImpl#isIsDomainBasedEdge <em>Is Domain
 * Based Edge</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgeDescriptionImpl#getPalette <em>Palette</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgeDescriptionImpl#getSourceNodeDescriptions <em>Source
 * Node Descriptions</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgeDescriptionImpl#getTargetNodeDescriptions <em>Target
 * Node Descriptions</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgeDescriptionImpl#getSourceNodesExpression <em>Source
 * Nodes Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgeDescriptionImpl#getTargetNodesExpression <em>Target
 * Nodes Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgeDescriptionImpl#getStyle <em>Style</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgeDescriptionImpl#getConditionalStyles <em>Conditional
 * Styles</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EdgeDescriptionImpl extends DiagramElementDescriptionImpl implements EdgeDescription {

    /**
     * The default value of the '{@link #getBeginLabelExpression() <em>Begin Label Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getBeginLabelExpression()
     */
    protected static final String BEGIN_LABEL_EXPRESSION_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getBeginLabelExpression() <em>Begin Label Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getBeginLabelExpression()
     */
    protected String beginLabelExpression = BEGIN_LABEL_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getCenterLabelExpression() <em>Center Label Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getCenterLabelExpression()
     */
    protected static final String CENTER_LABEL_EXPRESSION_EDEFAULT = "aql:self.name";

    /**
     * The cached value of the '{@link #getCenterLabelExpression() <em>Center Label Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getCenterLabelExpression()
     */
    protected String centerLabelExpression = CENTER_LABEL_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getEndLabelExpression() <em>End Label Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getEndLabelExpression()
     */
    protected static final String END_LABEL_EXPRESSION_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getEndLabelExpression() <em>End Label Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getEndLabelExpression()
     */
    protected String endLabelExpression = END_LABEL_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #isIsDomainBasedEdge() <em>Is Domain Based Edge</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #isIsDomainBasedEdge()
     */
    protected static final boolean IS_DOMAIN_BASED_EDGE_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isIsDomainBasedEdge() <em>Is Domain Based Edge</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #isIsDomainBasedEdge()
     */
    protected boolean isDomainBasedEdge = IS_DOMAIN_BASED_EDGE_EDEFAULT;

    /**
	 * The cached value of the '{@link #getPalette() <em>Palette</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getPalette()
	 * @generated
	 * @ordered
	 */
    protected EdgePalette palette;

    /**
     * The cached value of the '{@link #getSourceDescriptions() <em>Source Descriptions</em>}' reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getSourceDescriptions()
     * @generated
     * @ordered
     */
    protected EList<DiagramElementDescription> sourceDescriptions;

    /**
     * The cached value of the '{@link #getTargetDescriptions() <em>Target Descriptions</em>}' reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getTargetDescriptions()
     * @generated
     * @ordered
     */
    protected EList<DiagramElementDescription> targetDescriptions;

    /**
     * The default value of the '{@link #getSourceExpression() <em>Source Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getSourceExpression()
     * @generated
     * @ordered
     */
    protected static final String SOURCE_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getSourceExpression() <em>Source Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getSourceExpression()
     * @generated
     * @ordered
     */
    protected String sourceExpression = SOURCE_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getTargetExpression() <em>Target Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getTargetExpression()
     * @generated
     * @ordered
     */
    protected static final String TARGET_EXPRESSION_EDEFAULT = "aql:self.eCrossReferences()";

    /**
     * The cached value of the '{@link #getTargetExpression() <em>Target Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getTargetExpression()
     * @generated
     * @ordered
     */
    protected String targetExpression = TARGET_EXPRESSION_EDEFAULT;

    /**
	 * The cached value of the '{@link #getStyle() <em>Style</em>}' containment reference.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getStyle()
	 * @generated
	 * @ordered
	 */
    protected EdgeStyle style;

    /**
	 * The cached value of the '{@link #getConditionalStyles() <em>Conditional Styles</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getConditionalStyles()
	 * @generated
	 * @ordered
	 */
    protected EList<ConditionalEdgeStyle> conditionalStyles;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected EdgeDescriptionImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return DiagramPackage.Literals.EDGE_DESCRIPTION;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getBeginLabelExpression() {
		return beginLabelExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setBeginLabelExpression(String newBeginLabelExpression) {
		String oldBeginLabelExpression = beginLabelExpression;
		beginLabelExpression = newBeginLabelExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_DESCRIPTION__BEGIN_LABEL_EXPRESSION, oldBeginLabelExpression, beginLabelExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getCenterLabelExpression() {
		return centerLabelExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setCenterLabelExpression(String newCenterLabelExpression) {
		String oldCenterLabelExpression = centerLabelExpression;
		centerLabelExpression = newCenterLabelExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_DESCRIPTION__CENTER_LABEL_EXPRESSION, oldCenterLabelExpression, centerLabelExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getEndLabelExpression() {
		return endLabelExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setEndLabelExpression(String newEndLabelExpression) {
		String oldEndLabelExpression = endLabelExpression;
		endLabelExpression = newEndLabelExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_DESCRIPTION__END_LABEL_EXPRESSION, oldEndLabelExpression, endLabelExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public boolean isIsDomainBasedEdge() {
		return isDomainBasedEdge;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setIsDomainBasedEdge(boolean newIsDomainBasedEdge) {
		boolean oldIsDomainBasedEdge = isDomainBasedEdge;
		isDomainBasedEdge = newIsDomainBasedEdge;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_DESCRIPTION__IS_DOMAIN_BASED_EDGE, oldIsDomainBasedEdge, isDomainBasedEdge));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EdgePalette getPalette() {
		return palette;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setPalette(EdgePalette newPalette) {
		if (newPalette != palette)
		{
			NotificationChain msgs = null;
			if (palette != null)
				msgs = ((InternalEObject)palette).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.EDGE_DESCRIPTION__PALETTE, null, msgs);
			if (newPalette != null)
				msgs = ((InternalEObject)newPalette).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.EDGE_DESCRIPTION__PALETTE, null, msgs);
			msgs = basicSetPalette(newPalette, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_DESCRIPTION__PALETTE, newPalette, newPalette));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<DiagramElementDescription> getSourceDescriptions() {
		if (sourceDescriptions == null)
		{
			sourceDescriptions = new EObjectResolvingEList<DiagramElementDescription>(DiagramElementDescription.class, this, DiagramPackage.EDGE_DESCRIPTION__SOURCE_DESCRIPTIONS);
		}
		return sourceDescriptions;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<DiagramElementDescription> getTargetDescriptions() {
		if (targetDescriptions == null)
		{
			targetDescriptions = new EObjectResolvingEList<DiagramElementDescription>(DiagramElementDescription.class, this, DiagramPackage.EDGE_DESCRIPTION__TARGET_DESCRIPTIONS);
		}
		return targetDescriptions;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getSourceExpression() {
		return sourceExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setSourceExpression(String newSourceExpression) {
		String oldSourceExpression = sourceExpression;
		sourceExpression = newSourceExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_DESCRIPTION__SOURCE_EXPRESSION, oldSourceExpression, sourceExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getTargetExpression() {
		return targetExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setTargetExpression(String newTargetExpression) {
		String oldTargetExpression = targetExpression;
		targetExpression = newTargetExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_DESCRIPTION__TARGET_EXPRESSION, oldTargetExpression, targetExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetPalette(EdgePalette newPalette, NotificationChain msgs) {
		EdgePalette oldPalette = palette;
		palette = newPalette;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_DESCRIPTION__PALETTE, oldPalette, newPalette);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EdgeStyle getStyle() {
		return style;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setStyle(EdgeStyle newStyle) {
		if (newStyle != style)
		{
			NotificationChain msgs = null;
			if (style != null)
				msgs = ((InternalEObject)style).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.EDGE_DESCRIPTION__STYLE, null, msgs);
			if (newStyle != null)
				msgs = ((InternalEObject)newStyle).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.EDGE_DESCRIPTION__STYLE, null, msgs);
			msgs = basicSetStyle(newStyle, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_DESCRIPTION__STYLE, newStyle, newStyle));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetStyle(EdgeStyle newStyle, NotificationChain msgs) {
		EdgeStyle oldStyle = style;
		style = newStyle;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_DESCRIPTION__STYLE, oldStyle, newStyle);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<ConditionalEdgeStyle> getConditionalStyles() {
		if (conditionalStyles == null)
		{
			conditionalStyles = new EObjectContainmentEList<ConditionalEdgeStyle>(ConditionalEdgeStyle.class, this, DiagramPackage.EDGE_DESCRIPTION__CONDITIONAL_STYLES);
		}
		return conditionalStyles;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID)
		{
			case DiagramPackage.EDGE_DESCRIPTION__PALETTE:
				return basicSetPalette(null, msgs);
			case DiagramPackage.EDGE_DESCRIPTION__STYLE:
				return basicSetStyle(null, msgs);
			case DiagramPackage.EDGE_DESCRIPTION__CONDITIONAL_STYLES:
				return ((InternalEList<?>)getConditionalStyles()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID)
		{
			case DiagramPackage.EDGE_DESCRIPTION__BEGIN_LABEL_EXPRESSION:
				return getBeginLabelExpression();
			case DiagramPackage.EDGE_DESCRIPTION__CENTER_LABEL_EXPRESSION:
				return getCenterLabelExpression();
			case DiagramPackage.EDGE_DESCRIPTION__END_LABEL_EXPRESSION:
				return getEndLabelExpression();
			case DiagramPackage.EDGE_DESCRIPTION__IS_DOMAIN_BASED_EDGE:
				return isIsDomainBasedEdge();
			case DiagramPackage.EDGE_DESCRIPTION__PALETTE:
				return getPalette();
			case DiagramPackage.EDGE_DESCRIPTION__SOURCE_DESCRIPTIONS:
				return getSourceDescriptions();
			case DiagramPackage.EDGE_DESCRIPTION__TARGET_DESCRIPTIONS:
				return getTargetDescriptions();
			case DiagramPackage.EDGE_DESCRIPTION__SOURCE_EXPRESSION:
				return getSourceExpression();
			case DiagramPackage.EDGE_DESCRIPTION__TARGET_EXPRESSION:
				return getTargetExpression();
			case DiagramPackage.EDGE_DESCRIPTION__STYLE:
				return getStyle();
			case DiagramPackage.EDGE_DESCRIPTION__CONDITIONAL_STYLES:
				return getConditionalStyles();
		}
		return super.eGet(featureID, resolve, coreType);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
		switch (featureID)
		{
			case DiagramPackage.EDGE_DESCRIPTION__BEGIN_LABEL_EXPRESSION:
				setBeginLabelExpression((String)newValue);
				return;
			case DiagramPackage.EDGE_DESCRIPTION__CENTER_LABEL_EXPRESSION:
				setCenterLabelExpression((String)newValue);
				return;
			case DiagramPackage.EDGE_DESCRIPTION__END_LABEL_EXPRESSION:
				setEndLabelExpression((String)newValue);
				return;
			case DiagramPackage.EDGE_DESCRIPTION__IS_DOMAIN_BASED_EDGE:
				setIsDomainBasedEdge((Boolean)newValue);
				return;
			case DiagramPackage.EDGE_DESCRIPTION__PALETTE:
				setPalette((EdgePalette)newValue);
				return;
			case DiagramPackage.EDGE_DESCRIPTION__SOURCE_DESCRIPTIONS:
				getSourceDescriptions().clear();
				getSourceDescriptions().addAll((Collection<? extends DiagramElementDescription>)newValue);
				return;
			case DiagramPackage.EDGE_DESCRIPTION__TARGET_DESCRIPTIONS:
				getTargetDescriptions().clear();
				getTargetDescriptions().addAll((Collection<? extends DiagramElementDescription>)newValue);
				return;
			case DiagramPackage.EDGE_DESCRIPTION__SOURCE_EXPRESSION:
				setSourceExpression((String)newValue);
				return;
			case DiagramPackage.EDGE_DESCRIPTION__TARGET_EXPRESSION:
				setTargetExpression((String)newValue);
				return;
			case DiagramPackage.EDGE_DESCRIPTION__STYLE:
				setStyle((EdgeStyle)newValue);
				return;
			case DiagramPackage.EDGE_DESCRIPTION__CONDITIONAL_STYLES:
				getConditionalStyles().clear();
				getConditionalStyles().addAll((Collection<? extends ConditionalEdgeStyle>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void eUnset(int featureID) {
		switch (featureID)
		{
			case DiagramPackage.EDGE_DESCRIPTION__BEGIN_LABEL_EXPRESSION:
				setBeginLabelExpression(BEGIN_LABEL_EXPRESSION_EDEFAULT);
				return;
			case DiagramPackage.EDGE_DESCRIPTION__CENTER_LABEL_EXPRESSION:
				setCenterLabelExpression(CENTER_LABEL_EXPRESSION_EDEFAULT);
				return;
			case DiagramPackage.EDGE_DESCRIPTION__END_LABEL_EXPRESSION:
				setEndLabelExpression(END_LABEL_EXPRESSION_EDEFAULT);
				return;
			case DiagramPackage.EDGE_DESCRIPTION__IS_DOMAIN_BASED_EDGE:
				setIsDomainBasedEdge(IS_DOMAIN_BASED_EDGE_EDEFAULT);
				return;
			case DiagramPackage.EDGE_DESCRIPTION__PALETTE:
				setPalette((EdgePalette)null);
				return;
			case DiagramPackage.EDGE_DESCRIPTION__SOURCE_DESCRIPTIONS:
				getSourceDescriptions().clear();
				return;
			case DiagramPackage.EDGE_DESCRIPTION__TARGET_DESCRIPTIONS:
				getTargetDescriptions().clear();
				return;
			case DiagramPackage.EDGE_DESCRIPTION__SOURCE_EXPRESSION:
				setSourceExpression(SOURCE_EXPRESSION_EDEFAULT);
				return;
			case DiagramPackage.EDGE_DESCRIPTION__TARGET_EXPRESSION:
				setTargetExpression(TARGET_EXPRESSION_EDEFAULT);
				return;
			case DiagramPackage.EDGE_DESCRIPTION__STYLE:
				setStyle((EdgeStyle)null);
				return;
			case DiagramPackage.EDGE_DESCRIPTION__CONDITIONAL_STYLES:
				getConditionalStyles().clear();
				return;
		}
		super.eUnset(featureID);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public boolean eIsSet(int featureID) {
		switch (featureID)
		{
			case DiagramPackage.EDGE_DESCRIPTION__BEGIN_LABEL_EXPRESSION:
				return BEGIN_LABEL_EXPRESSION_EDEFAULT == null ? beginLabelExpression != null : !BEGIN_LABEL_EXPRESSION_EDEFAULT.equals(beginLabelExpression);
			case DiagramPackage.EDGE_DESCRIPTION__CENTER_LABEL_EXPRESSION:
				return CENTER_LABEL_EXPRESSION_EDEFAULT == null ? centerLabelExpression != null : !CENTER_LABEL_EXPRESSION_EDEFAULT.equals(centerLabelExpression);
			case DiagramPackage.EDGE_DESCRIPTION__END_LABEL_EXPRESSION:
				return END_LABEL_EXPRESSION_EDEFAULT == null ? endLabelExpression != null : !END_LABEL_EXPRESSION_EDEFAULT.equals(endLabelExpression);
			case DiagramPackage.EDGE_DESCRIPTION__IS_DOMAIN_BASED_EDGE:
				return isDomainBasedEdge != IS_DOMAIN_BASED_EDGE_EDEFAULT;
			case DiagramPackage.EDGE_DESCRIPTION__PALETTE:
				return palette != null;
			case DiagramPackage.EDGE_DESCRIPTION__SOURCE_DESCRIPTIONS:
				return sourceDescriptions != null && !sourceDescriptions.isEmpty();
			case DiagramPackage.EDGE_DESCRIPTION__TARGET_DESCRIPTIONS:
				return targetDescriptions != null && !targetDescriptions.isEmpty();
			case DiagramPackage.EDGE_DESCRIPTION__SOURCE_EXPRESSION:
				return SOURCE_EXPRESSION_EDEFAULT == null ? sourceExpression != null : !SOURCE_EXPRESSION_EDEFAULT.equals(sourceExpression);
			case DiagramPackage.EDGE_DESCRIPTION__TARGET_EXPRESSION:
				return TARGET_EXPRESSION_EDEFAULT == null ? targetExpression != null : !TARGET_EXPRESSION_EDEFAULT.equals(targetExpression);
			case DiagramPackage.EDGE_DESCRIPTION__STYLE:
				return style != null;
			case DiagramPackage.EDGE_DESCRIPTION__CONDITIONAL_STYLES:
				return conditionalStyles != null && !conditionalStyles.isEmpty();
		}
		return super.eIsSet(featureID);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (beginLabelExpression: ");
		result.append(beginLabelExpression);
		result.append(", centerLabelExpression: ");
		result.append(centerLabelExpression);
		result.append(", endLabelExpression: ");
		result.append(endLabelExpression);
		result.append(", isDomainBasedEdge: ");
		result.append(isDomainBasedEdge);
		result.append(", sourceExpression: ");
		result.append(sourceExpression);
		result.append(", targetExpression: ");
		result.append(targetExpression);
		result.append(')');
		return result.toString();
	}

} // EdgeDescriptionImpl
