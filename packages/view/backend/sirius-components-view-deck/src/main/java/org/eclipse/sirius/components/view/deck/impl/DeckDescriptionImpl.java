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
import org.eclipse.sirius.components.view.UserColor;
import org.eclipse.sirius.components.view.deck.DeckDescription;
import org.eclipse.sirius.components.view.deck.DeckPackage;
import org.eclipse.sirius.components.view.deck.LaneDescription;
import org.eclipse.sirius.components.view.impl.RepresentationDescriptionImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Description</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.deck.impl.DeckDescriptionImpl#getLaneDescriptions <em>Lane
 * Descriptions</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.deck.impl.DeckDescriptionImpl#getBackgroundColor <em>Background
 * Color</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DeckDescriptionImpl extends RepresentationDescriptionImpl implements DeckDescription {
    /**
     * The cached value of the '{@link #getLaneDescriptions() <em>Lane Descriptions</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getLaneDescriptions()
     * @generated
     * @ordered
     */
    protected EList<LaneDescription> laneDescriptions;

    /**
     * The cached value of the '{@link #getBackgroundColor() <em>Background Color</em>}' containment reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getBackgroundColor()
     * @generated
     * @ordered
     */
    protected UserColor backgroundColor;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected DeckDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DeckPackage.Literals.DECK_DESCRIPTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<LaneDescription> getLaneDescriptions() {
        if (this.laneDescriptions == null) {
            this.laneDescriptions = new EObjectContainmentEList<>(LaneDescription.class, this, DeckPackage.DECK_DESCRIPTION__LANE_DESCRIPTIONS);
        }
        return this.laneDescriptions;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public UserColor getBackgroundColor() {
        return this.backgroundColor;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetBackgroundColor(UserColor newBackgroundColor, NotificationChain msgs) {
        UserColor oldBackgroundColor = this.backgroundColor;
        this.backgroundColor = newBackgroundColor;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DeckPackage.DECK_DESCRIPTION__BACKGROUND_COLOR, oldBackgroundColor, newBackgroundColor);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setBackgroundColor(UserColor newBackgroundColor) {
        if (newBackgroundColor != this.backgroundColor) {
            NotificationChain msgs = null;
            if (this.backgroundColor != null)
                msgs = ((InternalEObject) this.backgroundColor).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DeckPackage.DECK_DESCRIPTION__BACKGROUND_COLOR, null, msgs);
            if (newBackgroundColor != null)
                msgs = ((InternalEObject) newBackgroundColor).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DeckPackage.DECK_DESCRIPTION__BACKGROUND_COLOR, null, msgs);
            msgs = this.basicSetBackgroundColor(newBackgroundColor, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DeckPackage.DECK_DESCRIPTION__BACKGROUND_COLOR, newBackgroundColor, newBackgroundColor));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case DeckPackage.DECK_DESCRIPTION__LANE_DESCRIPTIONS:
                return ((InternalEList<?>) this.getLaneDescriptions()).basicRemove(otherEnd, msgs);
            case DeckPackage.DECK_DESCRIPTION__BACKGROUND_COLOR:
                return this.basicSetBackgroundColor(null, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case DeckPackage.DECK_DESCRIPTION__LANE_DESCRIPTIONS:
                return this.getLaneDescriptions();
            case DeckPackage.DECK_DESCRIPTION__BACKGROUND_COLOR:
                return this.getBackgroundColor();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case DeckPackage.DECK_DESCRIPTION__LANE_DESCRIPTIONS:
                this.getLaneDescriptions().clear();
                this.getLaneDescriptions().addAll((Collection<? extends LaneDescription>) newValue);
                return;
            case DeckPackage.DECK_DESCRIPTION__BACKGROUND_COLOR:
                this.setBackgroundColor((UserColor) newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
            case DeckPackage.DECK_DESCRIPTION__LANE_DESCRIPTIONS:
                this.getLaneDescriptions().clear();
                return;
            case DeckPackage.DECK_DESCRIPTION__BACKGROUND_COLOR:
                this.setBackgroundColor((UserColor) null);
                return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case DeckPackage.DECK_DESCRIPTION__LANE_DESCRIPTIONS:
                return this.laneDescriptions != null && !this.laneDescriptions.isEmpty();
            case DeckPackage.DECK_DESCRIPTION__BACKGROUND_COLOR:
                return this.backgroundColor != null;
        }
        return super.eIsSet(featureID);
    }

} // DeckDescriptionImpl
