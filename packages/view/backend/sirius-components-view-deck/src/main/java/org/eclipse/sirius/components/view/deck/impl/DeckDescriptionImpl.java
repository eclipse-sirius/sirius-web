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
package org.eclipse.sirius.components.view.deck.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.components.view.deck.ConditionalDeckDescriptionStyle;
import org.eclipse.sirius.components.view.deck.DeckDescription;
import org.eclipse.sirius.components.view.deck.DeckDescriptionStyle;
import org.eclipse.sirius.components.view.deck.DeckPackage;
import org.eclipse.sirius.components.view.deck.LaneDescription;
import org.eclipse.sirius.components.view.deck.LaneDropTool;
import org.eclipse.sirius.components.view.impl.RepresentationDescriptionImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Description</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.components.view.deck.impl.DeckDescriptionImpl#getLaneDescriptions <em>Lane Descriptions</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.deck.impl.DeckDescriptionImpl#getLaneDropTool <em>Lane Drop Tool</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.deck.impl.DeckDescriptionImpl#getStyle <em>Style</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.deck.impl.DeckDescriptionImpl#getConditionalStyles <em>Conditional Styles</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DeckDescriptionImpl extends RepresentationDescriptionImpl implements DeckDescription {
    /**
	 * The cached value of the '{@link #getLaneDescriptions() <em>Lane Descriptions</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getLaneDescriptions()
	 * @generated
	 * @ordered
	 */
    protected EList<LaneDescription> laneDescriptions;

    /**
     * The cached value of the '{@link #getLaneDropTool() <em>Lane Drop Tool</em>}' containment reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getLaneDropTool()
     * @generated
     * @ordered
     */
    protected LaneDropTool laneDropTool;

    /**
	 * The cached value of the '{@link #getStyle() <em>Style</em>}' containment reference.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getStyle()
	 * @generated
	 * @ordered
	 */
    protected DeckDescriptionStyle style;

    /**
	 * The cached value of the '{@link #getConditionalStyles() <em>Conditional Styles</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getConditionalStyles()
	 * @generated
	 * @ordered
	 */
    protected EList<ConditionalDeckDescriptionStyle> conditionalStyles;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected DeckDescriptionImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return DeckPackage.Literals.DECK_DESCRIPTION;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<LaneDescription> getLaneDescriptions() {
		if (laneDescriptions == null)
		{
			laneDescriptions = new EObjectContainmentEList<LaneDescription>(LaneDescription.class, this, DeckPackage.DECK_DESCRIPTION__LANE_DESCRIPTIONS);
		}
		return laneDescriptions;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public LaneDropTool getLaneDropTool() {
		return laneDropTool;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetLaneDropTool(LaneDropTool newLaneDropTool, NotificationChain msgs) {
		LaneDropTool oldLaneDropTool = laneDropTool;
		laneDropTool = newLaneDropTool;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DeckPackage.DECK_DESCRIPTION__LANE_DROP_TOOL, oldLaneDropTool, newLaneDropTool);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setLaneDropTool(LaneDropTool newLaneDropTool) {
		if (newLaneDropTool != laneDropTool)
		{
			NotificationChain msgs = null;
			if (laneDropTool != null)
				msgs = ((InternalEObject)laneDropTool).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DeckPackage.DECK_DESCRIPTION__LANE_DROP_TOOL, null, msgs);
			if (newLaneDropTool != null)
				msgs = ((InternalEObject)newLaneDropTool).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DeckPackage.DECK_DESCRIPTION__LANE_DROP_TOOL, null, msgs);
			msgs = basicSetLaneDropTool(newLaneDropTool, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DeckPackage.DECK_DESCRIPTION__LANE_DROP_TOOL, newLaneDropTool, newLaneDropTool));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public DeckDescriptionStyle getStyle() {
		return style;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetStyle(DeckDescriptionStyle newStyle, NotificationChain msgs) {
		DeckDescriptionStyle oldStyle = style;
		style = newStyle;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DeckPackage.DECK_DESCRIPTION__STYLE, oldStyle, newStyle);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setStyle(DeckDescriptionStyle newStyle) {
		if (newStyle != style)
		{
			NotificationChain msgs = null;
			if (style != null)
				msgs = ((InternalEObject)style).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DeckPackage.DECK_DESCRIPTION__STYLE, null, msgs);
			if (newStyle != null)
				msgs = ((InternalEObject)newStyle).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DeckPackage.DECK_DESCRIPTION__STYLE, null, msgs);
			msgs = basicSetStyle(newStyle, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DeckPackage.DECK_DESCRIPTION__STYLE, newStyle, newStyle));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<ConditionalDeckDescriptionStyle> getConditionalStyles() {
		if (conditionalStyles == null)
		{
			conditionalStyles = new EObjectContainmentEList<ConditionalDeckDescriptionStyle>(ConditionalDeckDescriptionStyle.class, this, DeckPackage.DECK_DESCRIPTION__CONDITIONAL_STYLES);
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
			case DeckPackage.DECK_DESCRIPTION__LANE_DESCRIPTIONS:
				return ((InternalEList<?>)getLaneDescriptions()).basicRemove(otherEnd, msgs);
			case DeckPackage.DECK_DESCRIPTION__LANE_DROP_TOOL:
				return basicSetLaneDropTool(null, msgs);
			case DeckPackage.DECK_DESCRIPTION__STYLE:
				return basicSetStyle(null, msgs);
			case DeckPackage.DECK_DESCRIPTION__CONDITIONAL_STYLES:
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
			case DeckPackage.DECK_DESCRIPTION__LANE_DESCRIPTIONS:
				return getLaneDescriptions();
			case DeckPackage.DECK_DESCRIPTION__LANE_DROP_TOOL:
				return getLaneDropTool();
			case DeckPackage.DECK_DESCRIPTION__STYLE:
				return getStyle();
			case DeckPackage.DECK_DESCRIPTION__CONDITIONAL_STYLES:
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
			case DeckPackage.DECK_DESCRIPTION__LANE_DESCRIPTIONS:
				getLaneDescriptions().clear();
				getLaneDescriptions().addAll((Collection<? extends LaneDescription>)newValue);
				return;
			case DeckPackage.DECK_DESCRIPTION__LANE_DROP_TOOL:
				setLaneDropTool((LaneDropTool)newValue);
				return;
			case DeckPackage.DECK_DESCRIPTION__STYLE:
				setStyle((DeckDescriptionStyle)newValue);
				return;
			case DeckPackage.DECK_DESCRIPTION__CONDITIONAL_STYLES:
				getConditionalStyles().clear();
				getConditionalStyles().addAll((Collection<? extends ConditionalDeckDescriptionStyle>)newValue);
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
			case DeckPackage.DECK_DESCRIPTION__LANE_DESCRIPTIONS:
				getLaneDescriptions().clear();
				return;
			case DeckPackage.DECK_DESCRIPTION__LANE_DROP_TOOL:
				setLaneDropTool((LaneDropTool)null);
				return;
			case DeckPackage.DECK_DESCRIPTION__STYLE:
				setStyle((DeckDescriptionStyle)null);
				return;
			case DeckPackage.DECK_DESCRIPTION__CONDITIONAL_STYLES:
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
			case DeckPackage.DECK_DESCRIPTION__LANE_DESCRIPTIONS:
				return laneDescriptions != null && !laneDescriptions.isEmpty();
			case DeckPackage.DECK_DESCRIPTION__LANE_DROP_TOOL:
				return laneDropTool != null;
			case DeckPackage.DECK_DESCRIPTION__STYLE:
				return style != null;
			case DeckPackage.DECK_DESCRIPTION__CONDITIONAL_STYLES:
				return conditionalStyles != null && !conditionalStyles.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // DeckDescriptionImpl
