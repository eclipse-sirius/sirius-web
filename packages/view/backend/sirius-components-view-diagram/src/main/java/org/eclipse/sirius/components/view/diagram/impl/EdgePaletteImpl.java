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
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.components.view.diagram.DeleteTool;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.EdgePalette;
import org.eclipse.sirius.components.view.diagram.EdgeReconnectionTool;
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.components.view.diagram.EdgeToolSection;
import org.eclipse.sirius.components.view.diagram.LabelEditTool;
import org.eclipse.sirius.components.view.diagram.NodeTool;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Edge Palette</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgePaletteImpl#getDeleteTool <em>Delete Tool</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgePaletteImpl#getCenterLabelEditTool <em>Center Label Edit Tool</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgePaletteImpl#getBeginLabelEditTool <em>Begin Label Edit Tool</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgePaletteImpl#getEndLabelEditTool <em>End Label Edit Tool</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgePaletteImpl#getNodeTools <em>Node Tools</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgePaletteImpl#getQuickAccessTools <em>Quick Access Tools</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgePaletteImpl#getEdgeReconnectionTools <em>Edge Reconnection Tools</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgePaletteImpl#getEdgeTools <em>Edge Tools</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgePaletteImpl#getToolSections <em>Tool Sections</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EdgePaletteImpl extends MinimalEObjectImpl.Container implements EdgePalette {
    /**
     * The cached value of the '{@link #getDeleteTool() <em>Delete Tool</em>}' containment reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getDeleteTool()
     * @generated
     * @ordered
     */
    protected DeleteTool deleteTool;

    /**
	 * The cached value of the '{@link #getCenterLabelEditTool() <em>Center Label Edit Tool</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getCenterLabelEditTool()
	 * @generated
	 * @ordered
	 */
    protected LabelEditTool centerLabelEditTool;

    /**
	 * The cached value of the '{@link #getBeginLabelEditTool() <em>Begin Label Edit Tool</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getBeginLabelEditTool()
	 * @generated
	 * @ordered
	 */
    protected LabelEditTool beginLabelEditTool;

    /**
     * The cached value of the '{@link #getEndLabelEditTool() <em>End Label Edit Tool</em>}' containment reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getEndLabelEditTool()
     * @generated
     * @ordered
     */
    protected LabelEditTool endLabelEditTool;

    /**
     * The cached value of the '{@link #getNodeTools() <em>Node Tools</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getNodeTools()
     * @generated
     * @ordered
     */
    protected EList<NodeTool> nodeTools;

    /**
	 * The cached value of the '{@link #getQuickAccessTools() <em>Quick Access Tools</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getQuickAccessTools()
	 * @generated
	 * @ordered
	 */
    protected EList<NodeTool> quickAccessTools;

    /**
	 * The cached value of the '{@link #getEdgeReconnectionTools() <em>Edge Reconnection Tools</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getEdgeReconnectionTools()
	 * @generated
	 * @ordered
	 */
    protected EList<EdgeReconnectionTool> edgeReconnectionTools;

    /**
     * The cached value of the '{@link #getEdgeTools() <em>Edge Tools</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getEdgeTools()
     * @generated
     * @ordered
     */
    protected EList<EdgeTool> edgeTools;

    /**
     * The cached value of the '{@link #getToolSections() <em>Tool Sections</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getToolSections()
     * @generated
     * @ordered
     */
    protected EList<EdgeToolSection> toolSections;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected EdgePaletteImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return DiagramPackage.Literals.EDGE_PALETTE;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public DeleteTool getDeleteTool() {
		return deleteTool;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetDeleteTool(DeleteTool newDeleteTool, NotificationChain msgs) {
		DeleteTool oldDeleteTool = deleteTool;
		deleteTool = newDeleteTool;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_PALETTE__DELETE_TOOL, oldDeleteTool, newDeleteTool);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setDeleteTool(DeleteTool newDeleteTool) {
		if (newDeleteTool != deleteTool)
		{
			NotificationChain msgs = null;
			if (deleteTool != null)
				msgs = ((InternalEObject)deleteTool).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.EDGE_PALETTE__DELETE_TOOL, null, msgs);
			if (newDeleteTool != null)
				msgs = ((InternalEObject)newDeleteTool).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.EDGE_PALETTE__DELETE_TOOL, null, msgs);
			msgs = basicSetDeleteTool(newDeleteTool, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_PALETTE__DELETE_TOOL, newDeleteTool, newDeleteTool));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public LabelEditTool getCenterLabelEditTool() {
		return centerLabelEditTool;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetCenterLabelEditTool(LabelEditTool newCenterLabelEditTool, NotificationChain msgs) {
		LabelEditTool oldCenterLabelEditTool = centerLabelEditTool;
		centerLabelEditTool = newCenterLabelEditTool;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_PALETTE__CENTER_LABEL_EDIT_TOOL, oldCenterLabelEditTool, newCenterLabelEditTool);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setCenterLabelEditTool(LabelEditTool newCenterLabelEditTool) {
		if (newCenterLabelEditTool != centerLabelEditTool)
		{
			NotificationChain msgs = null;
			if (centerLabelEditTool != null)
				msgs = ((InternalEObject)centerLabelEditTool).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.EDGE_PALETTE__CENTER_LABEL_EDIT_TOOL, null, msgs);
			if (newCenterLabelEditTool != null)
				msgs = ((InternalEObject)newCenterLabelEditTool).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.EDGE_PALETTE__CENTER_LABEL_EDIT_TOOL, null, msgs);
			msgs = basicSetCenterLabelEditTool(newCenterLabelEditTool, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_PALETTE__CENTER_LABEL_EDIT_TOOL, newCenterLabelEditTool, newCenterLabelEditTool));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public LabelEditTool getBeginLabelEditTool() {
		return beginLabelEditTool;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetBeginLabelEditTool(LabelEditTool newBeginLabelEditTool, NotificationChain msgs) {
		LabelEditTool oldBeginLabelEditTool = beginLabelEditTool;
		beginLabelEditTool = newBeginLabelEditTool;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_PALETTE__BEGIN_LABEL_EDIT_TOOL, oldBeginLabelEditTool, newBeginLabelEditTool);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setBeginLabelEditTool(LabelEditTool newBeginLabelEditTool) {
		if (newBeginLabelEditTool != beginLabelEditTool)
		{
			NotificationChain msgs = null;
			if (beginLabelEditTool != null)
				msgs = ((InternalEObject)beginLabelEditTool).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.EDGE_PALETTE__BEGIN_LABEL_EDIT_TOOL, null, msgs);
			if (newBeginLabelEditTool != null)
				msgs = ((InternalEObject)newBeginLabelEditTool).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.EDGE_PALETTE__BEGIN_LABEL_EDIT_TOOL, null, msgs);
			msgs = basicSetBeginLabelEditTool(newBeginLabelEditTool, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_PALETTE__BEGIN_LABEL_EDIT_TOOL, newBeginLabelEditTool, newBeginLabelEditTool));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public LabelEditTool getEndLabelEditTool() {
		return endLabelEditTool;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetEndLabelEditTool(LabelEditTool newEndLabelEditTool, NotificationChain msgs) {
		LabelEditTool oldEndLabelEditTool = endLabelEditTool;
		endLabelEditTool = newEndLabelEditTool;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_PALETTE__END_LABEL_EDIT_TOOL, oldEndLabelEditTool, newEndLabelEditTool);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setEndLabelEditTool(LabelEditTool newEndLabelEditTool) {
		if (newEndLabelEditTool != endLabelEditTool)
		{
			NotificationChain msgs = null;
			if (endLabelEditTool != null)
				msgs = ((InternalEObject)endLabelEditTool).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.EDGE_PALETTE__END_LABEL_EDIT_TOOL, null, msgs);
			if (newEndLabelEditTool != null)
				msgs = ((InternalEObject)newEndLabelEditTool).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.EDGE_PALETTE__END_LABEL_EDIT_TOOL, null, msgs);
			msgs = basicSetEndLabelEditTool(newEndLabelEditTool, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_PALETTE__END_LABEL_EDIT_TOOL, newEndLabelEditTool, newEndLabelEditTool));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<NodeTool> getNodeTools() {
		if (nodeTools == null)
		{
			nodeTools = new EObjectContainmentEList<NodeTool>(NodeTool.class, this, DiagramPackage.EDGE_PALETTE__NODE_TOOLS);
		}
		return nodeTools;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<NodeTool> getQuickAccessTools() {
		if (quickAccessTools == null)
		{
			quickAccessTools = new EObjectContainmentEList<NodeTool>(NodeTool.class, this, DiagramPackage.EDGE_PALETTE__QUICK_ACCESS_TOOLS);
		}
		return quickAccessTools;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<EdgeReconnectionTool> getEdgeReconnectionTools() {
		if (edgeReconnectionTools == null)
		{
			edgeReconnectionTools = new EObjectContainmentEList<EdgeReconnectionTool>(EdgeReconnectionTool.class, this, DiagramPackage.EDGE_PALETTE__EDGE_RECONNECTION_TOOLS);
		}
		return edgeReconnectionTools;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<EdgeTool> getEdgeTools() {
		if (edgeTools == null)
		{
			edgeTools = new EObjectContainmentEList<EdgeTool>(EdgeTool.class, this, DiagramPackage.EDGE_PALETTE__EDGE_TOOLS);
		}
		return edgeTools;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<EdgeToolSection> getToolSections() {
		if (toolSections == null)
		{
			toolSections = new EObjectContainmentEList<EdgeToolSection>(EdgeToolSection.class, this, DiagramPackage.EDGE_PALETTE__TOOL_SECTIONS);
		}
		return toolSections;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID)
		{
			case DiagramPackage.EDGE_PALETTE__DELETE_TOOL:
				return basicSetDeleteTool(null, msgs);
			case DiagramPackage.EDGE_PALETTE__CENTER_LABEL_EDIT_TOOL:
				return basicSetCenterLabelEditTool(null, msgs);
			case DiagramPackage.EDGE_PALETTE__BEGIN_LABEL_EDIT_TOOL:
				return basicSetBeginLabelEditTool(null, msgs);
			case DiagramPackage.EDGE_PALETTE__END_LABEL_EDIT_TOOL:
				return basicSetEndLabelEditTool(null, msgs);
			case DiagramPackage.EDGE_PALETTE__NODE_TOOLS:
				return ((InternalEList<?>)getNodeTools()).basicRemove(otherEnd, msgs);
			case DiagramPackage.EDGE_PALETTE__QUICK_ACCESS_TOOLS:
				return ((InternalEList<?>)getQuickAccessTools()).basicRemove(otherEnd, msgs);
			case DiagramPackage.EDGE_PALETTE__EDGE_RECONNECTION_TOOLS:
				return ((InternalEList<?>)getEdgeReconnectionTools()).basicRemove(otherEnd, msgs);
			case DiagramPackage.EDGE_PALETTE__EDGE_TOOLS:
				return ((InternalEList<?>)getEdgeTools()).basicRemove(otherEnd, msgs);
			case DiagramPackage.EDGE_PALETTE__TOOL_SECTIONS:
				return ((InternalEList<?>)getToolSections()).basicRemove(otherEnd, msgs);
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
			case DiagramPackage.EDGE_PALETTE__DELETE_TOOL:
				return getDeleteTool();
			case DiagramPackage.EDGE_PALETTE__CENTER_LABEL_EDIT_TOOL:
				return getCenterLabelEditTool();
			case DiagramPackage.EDGE_PALETTE__BEGIN_LABEL_EDIT_TOOL:
				return getBeginLabelEditTool();
			case DiagramPackage.EDGE_PALETTE__END_LABEL_EDIT_TOOL:
				return getEndLabelEditTool();
			case DiagramPackage.EDGE_PALETTE__NODE_TOOLS:
				return getNodeTools();
			case DiagramPackage.EDGE_PALETTE__QUICK_ACCESS_TOOLS:
				return getQuickAccessTools();
			case DiagramPackage.EDGE_PALETTE__EDGE_RECONNECTION_TOOLS:
				return getEdgeReconnectionTools();
			case DiagramPackage.EDGE_PALETTE__EDGE_TOOLS:
				return getEdgeTools();
			case DiagramPackage.EDGE_PALETTE__TOOL_SECTIONS:
				return getToolSections();
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
			case DiagramPackage.EDGE_PALETTE__DELETE_TOOL:
				setDeleteTool((DeleteTool)newValue);
				return;
			case DiagramPackage.EDGE_PALETTE__CENTER_LABEL_EDIT_TOOL:
				setCenterLabelEditTool((LabelEditTool)newValue);
				return;
			case DiagramPackage.EDGE_PALETTE__BEGIN_LABEL_EDIT_TOOL:
				setBeginLabelEditTool((LabelEditTool)newValue);
				return;
			case DiagramPackage.EDGE_PALETTE__END_LABEL_EDIT_TOOL:
				setEndLabelEditTool((LabelEditTool)newValue);
				return;
			case DiagramPackage.EDGE_PALETTE__NODE_TOOLS:
				getNodeTools().clear();
				getNodeTools().addAll((Collection<? extends NodeTool>)newValue);
				return;
			case DiagramPackage.EDGE_PALETTE__QUICK_ACCESS_TOOLS:
				getQuickAccessTools().clear();
				getQuickAccessTools().addAll((Collection<? extends NodeTool>)newValue);
				return;
			case DiagramPackage.EDGE_PALETTE__EDGE_RECONNECTION_TOOLS:
				getEdgeReconnectionTools().clear();
				getEdgeReconnectionTools().addAll((Collection<? extends EdgeReconnectionTool>)newValue);
				return;
			case DiagramPackage.EDGE_PALETTE__EDGE_TOOLS:
				getEdgeTools().clear();
				getEdgeTools().addAll((Collection<? extends EdgeTool>)newValue);
				return;
			case DiagramPackage.EDGE_PALETTE__TOOL_SECTIONS:
				getToolSections().clear();
				getToolSections().addAll((Collection<? extends EdgeToolSection>)newValue);
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
			case DiagramPackage.EDGE_PALETTE__DELETE_TOOL:
				setDeleteTool((DeleteTool)null);
				return;
			case DiagramPackage.EDGE_PALETTE__CENTER_LABEL_EDIT_TOOL:
				setCenterLabelEditTool((LabelEditTool)null);
				return;
			case DiagramPackage.EDGE_PALETTE__BEGIN_LABEL_EDIT_TOOL:
				setBeginLabelEditTool((LabelEditTool)null);
				return;
			case DiagramPackage.EDGE_PALETTE__END_LABEL_EDIT_TOOL:
				setEndLabelEditTool((LabelEditTool)null);
				return;
			case DiagramPackage.EDGE_PALETTE__NODE_TOOLS:
				getNodeTools().clear();
				return;
			case DiagramPackage.EDGE_PALETTE__QUICK_ACCESS_TOOLS:
				getQuickAccessTools().clear();
				return;
			case DiagramPackage.EDGE_PALETTE__EDGE_RECONNECTION_TOOLS:
				getEdgeReconnectionTools().clear();
				return;
			case DiagramPackage.EDGE_PALETTE__EDGE_TOOLS:
				getEdgeTools().clear();
				return;
			case DiagramPackage.EDGE_PALETTE__TOOL_SECTIONS:
				getToolSections().clear();
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
			case DiagramPackage.EDGE_PALETTE__DELETE_TOOL:
				return deleteTool != null;
			case DiagramPackage.EDGE_PALETTE__CENTER_LABEL_EDIT_TOOL:
				return centerLabelEditTool != null;
			case DiagramPackage.EDGE_PALETTE__BEGIN_LABEL_EDIT_TOOL:
				return beginLabelEditTool != null;
			case DiagramPackage.EDGE_PALETTE__END_LABEL_EDIT_TOOL:
				return endLabelEditTool != null;
			case DiagramPackage.EDGE_PALETTE__NODE_TOOLS:
				return nodeTools != null && !nodeTools.isEmpty();
			case DiagramPackage.EDGE_PALETTE__QUICK_ACCESS_TOOLS:
				return quickAccessTools != null && !quickAccessTools.isEmpty();
			case DiagramPackage.EDGE_PALETTE__EDGE_RECONNECTION_TOOLS:
				return edgeReconnectionTools != null && !edgeReconnectionTools.isEmpty();
			case DiagramPackage.EDGE_PALETTE__EDGE_TOOLS:
				return edgeTools != null && !edgeTools.isEmpty();
			case DiagramPackage.EDGE_PALETTE__TOOL_SECTIONS:
				return toolSections != null && !toolSections.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // EdgePaletteImpl
