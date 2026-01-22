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
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.components.view.diagram.ArrangeLayoutDirection;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.DiagramPalette;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.GroupPalette;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.impl.RepresentationDescriptionImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Description</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.DiagramDescriptionImpl#isAutoLayout <em>Auto Layout</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.DiagramDescriptionImpl#getPalette <em>Palette</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.DiagramDescriptionImpl#getGroupPalette <em>Group Palette</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.DiagramDescriptionImpl#getNodeDescriptions <em>Node Descriptions</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.DiagramDescriptionImpl#getEdgeDescriptions <em>Edge Descriptions</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.DiagramDescriptionImpl#getArrangeLayoutDirection <em>Arrange Layout Direction</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DiagramDescriptionImpl extends RepresentationDescriptionImpl implements DiagramDescription {

    /**
	 * The default value of the '{@link #isAutoLayout() <em>Auto Layout</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #isAutoLayout()
	 * @generated
	 * @ordered
	 */
    protected static final boolean AUTO_LAYOUT_EDEFAULT = false;

    /**
	 * The cached value of the '{@link #isAutoLayout() <em>Auto Layout</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #isAutoLayout()
	 * @generated
	 * @ordered
	 */
    protected boolean autoLayout = AUTO_LAYOUT_EDEFAULT;

    /**
	 * The cached value of the '{@link #getPalette() <em>Palette</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getPalette()
	 * @generated
	 * @ordered
	 */
    protected DiagramPalette palette;

    /**
     * The cached value of the '{@link #getGroupPalette() <em>Group Palette</em>}' containment reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getGroupPalette()
     * @generated
     * @ordered
     */
    protected GroupPalette groupPalette;

    /**
	 * The cached value of the '{@link #getNodeDescriptions() <em>Node Descriptions</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getNodeDescriptions()
	 * @generated
	 * @ordered
	 */
    protected EList<NodeDescription> nodeDescriptions;

    /**
	 * The cached value of the '{@link #getEdgeDescriptions() <em>Edge Descriptions</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getEdgeDescriptions()
	 * @generated
	 * @ordered
	 */
    protected EList<EdgeDescription> edgeDescriptions;

    /**
     * The default value of the '{@link #getArrangeLayoutDirection() <em>Arrange Layout Direction</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getArrangeLayoutDirection()
     */
    protected static final ArrangeLayoutDirection ARRANGE_LAYOUT_DIRECTION_EDEFAULT = ArrangeLayoutDirection.UNDEFINED;

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
	 * @generated
	 */
    protected DiagramDescriptionImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return DiagramPackage.Literals.DIAGRAM_DESCRIPTION;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public boolean isAutoLayout() {
		return autoLayout;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setAutoLayout(boolean newAutoLayout) {
		boolean oldAutoLayout = autoLayout;
		autoLayout = newAutoLayout;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.DIAGRAM_DESCRIPTION__AUTO_LAYOUT, oldAutoLayout, autoLayout));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public DiagramPalette getPalette() {
		return palette;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setPalette(DiagramPalette newPalette) {
		if (newPalette != palette)
		{
			NotificationChain msgs = null;
			if (palette != null)
				msgs = ((InternalEObject)palette).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.DIAGRAM_DESCRIPTION__PALETTE, null, msgs);
			if (newPalette != null)
				msgs = ((InternalEObject)newPalette).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.DIAGRAM_DESCRIPTION__PALETTE, null, msgs);
			msgs = basicSetPalette(newPalette, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.DIAGRAM_DESCRIPTION__PALETTE, newPalette, newPalette));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetPalette(DiagramPalette newPalette, NotificationChain msgs) {
		DiagramPalette oldPalette = palette;
		palette = newPalette;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DiagramPackage.DIAGRAM_DESCRIPTION__PALETTE, oldPalette, newPalette);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<NodeDescription> getNodeDescriptions() {
		if (nodeDescriptions == null)
		{
			nodeDescriptions = new EObjectContainmentEList<NodeDescription>(NodeDescription.class, this, DiagramPackage.DIAGRAM_DESCRIPTION__NODE_DESCRIPTIONS);
		}
		return nodeDescriptions;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<EdgeDescription> getEdgeDescriptions() {
		if (edgeDescriptions == null)
		{
			edgeDescriptions = new EObjectContainmentEList<EdgeDescription>(EdgeDescription.class, this, DiagramPackage.DIAGRAM_DESCRIPTION__EDGE_DESCRIPTIONS);
		}
		return edgeDescriptions;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public ArrangeLayoutDirection getArrangeLayoutDirection() {
		return arrangeLayoutDirection;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setArrangeLayoutDirection(ArrangeLayoutDirection newArrangeLayoutDirection) {
		ArrangeLayoutDirection oldArrangeLayoutDirection = arrangeLayoutDirection;
		arrangeLayoutDirection = newArrangeLayoutDirection == null ? ARRANGE_LAYOUT_DIRECTION_EDEFAULT : newArrangeLayoutDirection;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.DIAGRAM_DESCRIPTION__ARRANGE_LAYOUT_DIRECTION, oldArrangeLayoutDirection, arrangeLayoutDirection));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public GroupPalette getGroupPalette() {
		return groupPalette;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetGroupPalette(GroupPalette newGroupPalette, NotificationChain msgs) {
		GroupPalette oldGroupPalette = groupPalette;
		groupPalette = newGroupPalette;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DiagramPackage.DIAGRAM_DESCRIPTION__GROUP_PALETTE, oldGroupPalette, newGroupPalette);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setGroupPalette(GroupPalette newGroupPalette) {
		if (newGroupPalette != groupPalette)
		{
			NotificationChain msgs = null;
			if (groupPalette != null)
				msgs = ((InternalEObject)groupPalette).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.DIAGRAM_DESCRIPTION__GROUP_PALETTE, null, msgs);
			if (newGroupPalette != null)
				msgs = ((InternalEObject)newGroupPalette).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.DIAGRAM_DESCRIPTION__GROUP_PALETTE, null, msgs);
			msgs = basicSetGroupPalette(newGroupPalette, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.DIAGRAM_DESCRIPTION__GROUP_PALETTE, newGroupPalette, newGroupPalette));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID)
		{
			case DiagramPackage.DIAGRAM_DESCRIPTION__PALETTE:
				return basicSetPalette(null, msgs);
			case DiagramPackage.DIAGRAM_DESCRIPTION__GROUP_PALETTE:
				return basicSetGroupPalette(null, msgs);
			case DiagramPackage.DIAGRAM_DESCRIPTION__NODE_DESCRIPTIONS:
				return ((InternalEList<?>)getNodeDescriptions()).basicRemove(otherEnd, msgs);
			case DiagramPackage.DIAGRAM_DESCRIPTION__EDGE_DESCRIPTIONS:
				return ((InternalEList<?>)getEdgeDescriptions()).basicRemove(otherEnd, msgs);
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
			case DiagramPackage.DIAGRAM_DESCRIPTION__AUTO_LAYOUT:
				return isAutoLayout();
			case DiagramPackage.DIAGRAM_DESCRIPTION__PALETTE:
				return getPalette();
			case DiagramPackage.DIAGRAM_DESCRIPTION__GROUP_PALETTE:
				return getGroupPalette();
			case DiagramPackage.DIAGRAM_DESCRIPTION__NODE_DESCRIPTIONS:
				return getNodeDescriptions();
			case DiagramPackage.DIAGRAM_DESCRIPTION__EDGE_DESCRIPTIONS:
				return getEdgeDescriptions();
			case DiagramPackage.DIAGRAM_DESCRIPTION__ARRANGE_LAYOUT_DIRECTION:
				return getArrangeLayoutDirection();
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
			case DiagramPackage.DIAGRAM_DESCRIPTION__AUTO_LAYOUT:
				setAutoLayout((Boolean)newValue);
				return;
			case DiagramPackage.DIAGRAM_DESCRIPTION__PALETTE:
				setPalette((DiagramPalette)newValue);
				return;
			case DiagramPackage.DIAGRAM_DESCRIPTION__GROUP_PALETTE:
				setGroupPalette((GroupPalette)newValue);
				return;
			case DiagramPackage.DIAGRAM_DESCRIPTION__NODE_DESCRIPTIONS:
				getNodeDescriptions().clear();
				getNodeDescriptions().addAll((Collection<? extends NodeDescription>)newValue);
				return;
			case DiagramPackage.DIAGRAM_DESCRIPTION__EDGE_DESCRIPTIONS:
				getEdgeDescriptions().clear();
				getEdgeDescriptions().addAll((Collection<? extends EdgeDescription>)newValue);
				return;
			case DiagramPackage.DIAGRAM_DESCRIPTION__ARRANGE_LAYOUT_DIRECTION:
				setArrangeLayoutDirection((ArrangeLayoutDirection)newValue);
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
			case DiagramPackage.DIAGRAM_DESCRIPTION__AUTO_LAYOUT:
				setAutoLayout(AUTO_LAYOUT_EDEFAULT);
				return;
			case DiagramPackage.DIAGRAM_DESCRIPTION__PALETTE:
				setPalette((DiagramPalette)null);
				return;
			case DiagramPackage.DIAGRAM_DESCRIPTION__GROUP_PALETTE:
				setGroupPalette((GroupPalette)null);
				return;
			case DiagramPackage.DIAGRAM_DESCRIPTION__NODE_DESCRIPTIONS:
				getNodeDescriptions().clear();
				return;
			case DiagramPackage.DIAGRAM_DESCRIPTION__EDGE_DESCRIPTIONS:
				getEdgeDescriptions().clear();
				return;
			case DiagramPackage.DIAGRAM_DESCRIPTION__ARRANGE_LAYOUT_DIRECTION:
				setArrangeLayoutDirection(ARRANGE_LAYOUT_DIRECTION_EDEFAULT);
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
			case DiagramPackage.DIAGRAM_DESCRIPTION__AUTO_LAYOUT:
				return autoLayout != AUTO_LAYOUT_EDEFAULT;
			case DiagramPackage.DIAGRAM_DESCRIPTION__PALETTE:
				return palette != null;
			case DiagramPackage.DIAGRAM_DESCRIPTION__GROUP_PALETTE:
				return groupPalette != null;
			case DiagramPackage.DIAGRAM_DESCRIPTION__NODE_DESCRIPTIONS:
				return nodeDescriptions != null && !nodeDescriptions.isEmpty();
			case DiagramPackage.DIAGRAM_DESCRIPTION__EDGE_DESCRIPTIONS:
				return edgeDescriptions != null && !edgeDescriptions.isEmpty();
			case DiagramPackage.DIAGRAM_DESCRIPTION__ARRANGE_LAYOUT_DIRECTION:
				return arrangeLayoutDirection != ARRANGE_LAYOUT_DIRECTION_EDEFAULT;
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
		result.append(" (autoLayout: ");
		result.append(autoLayout);
		result.append(", arrangeLayoutDirection: ");
		result.append(arrangeLayoutDirection);
		result.append(')');
		return result.toString();
	}

} // DiagramDescriptionImpl
