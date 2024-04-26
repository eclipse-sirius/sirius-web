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
package org.eclipse.sirius.components.view.diagram.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.components.view.diagram.ArrangeLayoutDirection;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.DiagramPalette;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.impl.RepresentationDescriptionImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Description</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.DiagramDescriptionImpl#isAutoLayout <em>Auto
 * Layout</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.DiagramDescriptionImpl#getPalette <em>Palette</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.DiagramDescriptionImpl#getNodeDescriptions <em>Node
 * Descriptions</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.DiagramDescriptionImpl#getEdgeDescriptions <em>Edge
 * Descriptions</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.DiagramDescriptionImpl#getArrangeLayoutDirection
 * <em>Arrange Layout Direction</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DiagramDescriptionImpl extends RepresentationDescriptionImpl implements DiagramDescription {

    /**
     * The default value of the '{@link #isAutoLayout() <em>Auto Layout</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #isAutoLayout()
     */
    protected static final boolean AUTO_LAYOUT_EDEFAULT = false;
    /**
     * The default value of the '{@link #getArrangeLayoutDirection() <em>Arrange Layout Direction</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getArrangeLayoutDirection()
     */
    protected static final ArrangeLayoutDirection ARRANGE_LAYOUT_DIRECTION_EDEFAULT = ArrangeLayoutDirection.RIGHT;
    /**
     * The cached value of the '{@link #isAutoLayout() <em>Auto Layout</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #isAutoLayout()
     */
    protected boolean autoLayout = AUTO_LAYOUT_EDEFAULT;
    /**
     * The cached value of the '{@link #getPalette() <em>Palette</em>}' containment reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getPalette()
     */
    protected DiagramPalette palette;
    /**
     * The cached value of the '{@link #getNodeDescriptions() <em>Node Descriptions</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getNodeDescriptions()
     */
    protected EList<NodeDescription> nodeDescriptions;
    /**
     * The cached value of the '{@link #getEdgeDescriptions() <em>Edge Descriptions</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getEdgeDescriptions()
     */
    protected EList<EdgeDescription> edgeDescriptions;
    /**
     * The cached value of the '{@link #getArrangeLayoutDirection() <em>Arrange Layout Direction</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getArrangeLayoutDirection()
     */
    protected ArrangeLayoutDirection arrangeLayoutDirection = ARRANGE_LAYOUT_DIRECTION_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected DiagramDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DiagramPackage.Literals.DIAGRAM_DESCRIPTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean isAutoLayout() {
        return this.autoLayout;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setAutoLayout(boolean newAutoLayout) {
        boolean oldAutoLayout = this.autoLayout;
        this.autoLayout = newAutoLayout;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.DIAGRAM_DESCRIPTION__AUTO_LAYOUT, oldAutoLayout, this.autoLayout));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public DiagramPalette getPalette() {
        return this.palette;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setPalette(DiagramPalette newPalette) {
        if (newPalette != this.palette) {
            NotificationChain msgs = null;
            if (this.palette != null)
                msgs = ((InternalEObject) this.palette).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.DIAGRAM_DESCRIPTION__PALETTE, null, msgs);
            if (newPalette != null)
                msgs = ((InternalEObject) newPalette).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.DIAGRAM_DESCRIPTION__PALETTE, null, msgs);
            msgs = this.basicSetPalette(newPalette, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.DIAGRAM_DESCRIPTION__PALETTE, newPalette, newPalette));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetPalette(DiagramPalette newPalette, NotificationChain msgs) {
        DiagramPalette oldPalette = this.palette;
        this.palette = newPalette;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DiagramPackage.DIAGRAM_DESCRIPTION__PALETTE, oldPalette, newPalette);
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
    public EList<NodeDescription> getNodeDescriptions() {
        if (this.nodeDescriptions == null) {
            this.nodeDescriptions = new EObjectContainmentEList<>(NodeDescription.class, this, DiagramPackage.DIAGRAM_DESCRIPTION__NODE_DESCRIPTIONS);
        }
        return this.nodeDescriptions;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<EdgeDescription> getEdgeDescriptions() {
        if (this.edgeDescriptions == null) {
            this.edgeDescriptions = new EObjectContainmentEList<>(EdgeDescription.class, this, DiagramPackage.DIAGRAM_DESCRIPTION__EDGE_DESCRIPTIONS);
        }
        return this.edgeDescriptions;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ArrangeLayoutDirection getArrangeLayoutDirection() {
        return this.arrangeLayoutDirection;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setArrangeLayoutDirection(ArrangeLayoutDirection newArrangeLayoutDirection) {
        ArrangeLayoutDirection oldArrangeLayoutDirection = this.arrangeLayoutDirection;
        this.arrangeLayoutDirection = newArrangeLayoutDirection == null ? ARRANGE_LAYOUT_DIRECTION_EDEFAULT : newArrangeLayoutDirection;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.DIAGRAM_DESCRIPTION__ARRANGE_LAYOUT_DIRECTION, oldArrangeLayoutDirection, this.arrangeLayoutDirection));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case DiagramPackage.DIAGRAM_DESCRIPTION__PALETTE:
                return this.basicSetPalette(null, msgs);
            case DiagramPackage.DIAGRAM_DESCRIPTION__NODE_DESCRIPTIONS:
                return ((InternalEList<?>) this.getNodeDescriptions()).basicRemove(otherEnd, msgs);
            case DiagramPackage.DIAGRAM_DESCRIPTION__EDGE_DESCRIPTIONS:
                return ((InternalEList<?>) this.getEdgeDescriptions()).basicRemove(otherEnd, msgs);
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
            case DiagramPackage.DIAGRAM_DESCRIPTION__AUTO_LAYOUT:
                return this.isAutoLayout();
            case DiagramPackage.DIAGRAM_DESCRIPTION__PALETTE:
                return this.getPalette();
            case DiagramPackage.DIAGRAM_DESCRIPTION__NODE_DESCRIPTIONS:
                return this.getNodeDescriptions();
            case DiagramPackage.DIAGRAM_DESCRIPTION__EDGE_DESCRIPTIONS:
                return this.getEdgeDescriptions();
            case DiagramPackage.DIAGRAM_DESCRIPTION__ARRANGE_LAYOUT_DIRECTION:
                return this.getArrangeLayoutDirection();
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
            case DiagramPackage.DIAGRAM_DESCRIPTION__AUTO_LAYOUT:
                this.setAutoLayout((Boolean) newValue);
                return;
            case DiagramPackage.DIAGRAM_DESCRIPTION__PALETTE:
                this.setPalette((DiagramPalette) newValue);
                return;
            case DiagramPackage.DIAGRAM_DESCRIPTION__NODE_DESCRIPTIONS:
                this.getNodeDescriptions().clear();
                this.getNodeDescriptions().addAll((Collection<? extends NodeDescription>) newValue);
                return;
            case DiagramPackage.DIAGRAM_DESCRIPTION__EDGE_DESCRIPTIONS:
                this.getEdgeDescriptions().clear();
                this.getEdgeDescriptions().addAll((Collection<? extends EdgeDescription>) newValue);
                return;
            case DiagramPackage.DIAGRAM_DESCRIPTION__ARRANGE_LAYOUT_DIRECTION:
                this.setArrangeLayoutDirection((ArrangeLayoutDirection) newValue);
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
            case DiagramPackage.DIAGRAM_DESCRIPTION__AUTO_LAYOUT:
                this.setAutoLayout(AUTO_LAYOUT_EDEFAULT);
                return;
            case DiagramPackage.DIAGRAM_DESCRIPTION__PALETTE:
                this.setPalette(null);
                return;
            case DiagramPackage.DIAGRAM_DESCRIPTION__NODE_DESCRIPTIONS:
                this.getNodeDescriptions().clear();
                return;
            case DiagramPackage.DIAGRAM_DESCRIPTION__EDGE_DESCRIPTIONS:
                this.getEdgeDescriptions().clear();
                return;
            case DiagramPackage.DIAGRAM_DESCRIPTION__ARRANGE_LAYOUT_DIRECTION:
                this.setArrangeLayoutDirection(ARRANGE_LAYOUT_DIRECTION_EDEFAULT);
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
            case DiagramPackage.DIAGRAM_DESCRIPTION__AUTO_LAYOUT:
                return this.autoLayout != AUTO_LAYOUT_EDEFAULT;
            case DiagramPackage.DIAGRAM_DESCRIPTION__PALETTE:
                return this.palette != null;
            case DiagramPackage.DIAGRAM_DESCRIPTION__NODE_DESCRIPTIONS:
                return this.nodeDescriptions != null && !this.nodeDescriptions.isEmpty();
            case DiagramPackage.DIAGRAM_DESCRIPTION__EDGE_DESCRIPTIONS:
                return this.edgeDescriptions != null && !this.edgeDescriptions.isEmpty();
            case DiagramPackage.DIAGRAM_DESCRIPTION__ARRANGE_LAYOUT_DIRECTION:
                return this.arrangeLayoutDirection != ARRANGE_LAYOUT_DIRECTION_EDEFAULT;
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String toString() {
        if (this.eIsProxy())
            return super.toString();

        String result = super.toString() + " (autoLayout: " +
                this.autoLayout +
                ", arrangeLayoutDirection: " +
                this.arrangeLayoutDirection +
                ')';
        return result;
    }

} // DiagramDescriptionImpl
