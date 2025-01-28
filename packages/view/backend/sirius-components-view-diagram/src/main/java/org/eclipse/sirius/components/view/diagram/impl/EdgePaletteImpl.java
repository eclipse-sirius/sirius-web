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
import org.eclipse.sirius.components.view.diagram.EdgeToolSection;
import org.eclipse.sirius.components.view.diagram.LabelEditTool;
import org.eclipse.sirius.components.view.diagram.NodeTool;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Edge Palette</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgePaletteImpl#getDeleteTool <em>Delete Tool</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgePaletteImpl#getCenterLabelEditTool <em>Center Label
 * Edit Tool</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgePaletteImpl#getBeginLabelEditTool <em>Begin Label Edit
 * Tool</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgePaletteImpl#getEndLabelEditTool <em>End Label Edit
 * Tool</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgePaletteImpl#getNodeTools <em>Node Tools</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgePaletteImpl#getQuickAccessTools <em>Quick Access
 * Tools</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgePaletteImpl#getEdgeReconnectionTools <em>Edge
 * Reconnection Tools</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgePaletteImpl#getToolSections <em>Tool
 * Sections</em>}</li>
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
     * The cached value of the '{@link #getCenterLabelEditTool() <em>Center Label Edit Tool</em>}' containment
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getCenterLabelEditTool()
     * @generated
     * @ordered
     */
    protected LabelEditTool centerLabelEditTool;

    /**
     * The cached value of the '{@link #getBeginLabelEditTool() <em>Begin Label Edit Tool</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
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
     *
     * @see #getQuickAccessTools()
     * @generated
     * @ordered
     */
    protected EList<NodeTool> quickAccessTools;

    /**
     * The cached value of the '{@link #getEdgeReconnectionTools() <em>Edge Reconnection Tools</em>}' containment
     * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getEdgeReconnectionTools()
     * @generated
     * @ordered
     */
    protected EList<EdgeReconnectionTool> edgeReconnectionTools;

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
     *
     * @generated
     */
    protected EdgePaletteImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DiagramPackage.Literals.EDGE_PALETTE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public DeleteTool getDeleteTool() {
        return this.deleteTool;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetDeleteTool(DeleteTool newDeleteTool, NotificationChain msgs) {
        DeleteTool oldDeleteTool = this.deleteTool;
        this.deleteTool = newDeleteTool;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_PALETTE__DELETE_TOOL, oldDeleteTool, newDeleteTool);
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
    public void setDeleteTool(DeleteTool newDeleteTool) {
        if (newDeleteTool != this.deleteTool) {
            NotificationChain msgs = null;
            if (this.deleteTool != null)
                msgs = ((InternalEObject) this.deleteTool).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.EDGE_PALETTE__DELETE_TOOL, null, msgs);
            if (newDeleteTool != null)
                msgs = ((InternalEObject) newDeleteTool).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.EDGE_PALETTE__DELETE_TOOL, null, msgs);
            msgs = this.basicSetDeleteTool(newDeleteTool, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_PALETTE__DELETE_TOOL, newDeleteTool, newDeleteTool));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public LabelEditTool getCenterLabelEditTool() {
        return this.centerLabelEditTool;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetCenterLabelEditTool(LabelEditTool newCenterLabelEditTool, NotificationChain msgs) {
        LabelEditTool oldCenterLabelEditTool = this.centerLabelEditTool;
        this.centerLabelEditTool = newCenterLabelEditTool;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_PALETTE__CENTER_LABEL_EDIT_TOOL, oldCenterLabelEditTool, newCenterLabelEditTool);
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
    public void setCenterLabelEditTool(LabelEditTool newCenterLabelEditTool) {
        if (newCenterLabelEditTool != this.centerLabelEditTool) {
            NotificationChain msgs = null;
            if (this.centerLabelEditTool != null)
                msgs = ((InternalEObject) this.centerLabelEditTool).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.EDGE_PALETTE__CENTER_LABEL_EDIT_TOOL, null, msgs);
            if (newCenterLabelEditTool != null)
                msgs = ((InternalEObject) newCenterLabelEditTool).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.EDGE_PALETTE__CENTER_LABEL_EDIT_TOOL, null, msgs);
            msgs = this.basicSetCenterLabelEditTool(newCenterLabelEditTool, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_PALETTE__CENTER_LABEL_EDIT_TOOL, newCenterLabelEditTool, newCenterLabelEditTool));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public LabelEditTool getBeginLabelEditTool() {
        return this.beginLabelEditTool;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetBeginLabelEditTool(LabelEditTool newBeginLabelEditTool, NotificationChain msgs) {
        LabelEditTool oldBeginLabelEditTool = this.beginLabelEditTool;
        this.beginLabelEditTool = newBeginLabelEditTool;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_PALETTE__BEGIN_LABEL_EDIT_TOOL, oldBeginLabelEditTool, newBeginLabelEditTool);
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
    public void setBeginLabelEditTool(LabelEditTool newBeginLabelEditTool) {
        if (newBeginLabelEditTool != this.beginLabelEditTool) {
            NotificationChain msgs = null;
            if (this.beginLabelEditTool != null)
                msgs = ((InternalEObject) this.beginLabelEditTool).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.EDGE_PALETTE__BEGIN_LABEL_EDIT_TOOL, null, msgs);
            if (newBeginLabelEditTool != null)
                msgs = ((InternalEObject) newBeginLabelEditTool).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.EDGE_PALETTE__BEGIN_LABEL_EDIT_TOOL, null, msgs);
            msgs = this.basicSetBeginLabelEditTool(newBeginLabelEditTool, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_PALETTE__BEGIN_LABEL_EDIT_TOOL, newBeginLabelEditTool, newBeginLabelEditTool));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public LabelEditTool getEndLabelEditTool() {
        return this.endLabelEditTool;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetEndLabelEditTool(LabelEditTool newEndLabelEditTool, NotificationChain msgs) {
        LabelEditTool oldEndLabelEditTool = this.endLabelEditTool;
        this.endLabelEditTool = newEndLabelEditTool;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_PALETTE__END_LABEL_EDIT_TOOL, oldEndLabelEditTool, newEndLabelEditTool);
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
    public void setEndLabelEditTool(LabelEditTool newEndLabelEditTool) {
        if (newEndLabelEditTool != this.endLabelEditTool) {
            NotificationChain msgs = null;
            if (this.endLabelEditTool != null)
                msgs = ((InternalEObject) this.endLabelEditTool).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.EDGE_PALETTE__END_LABEL_EDIT_TOOL, null, msgs);
            if (newEndLabelEditTool != null)
                msgs = ((InternalEObject) newEndLabelEditTool).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.EDGE_PALETTE__END_LABEL_EDIT_TOOL, null, msgs);
            msgs = this.basicSetEndLabelEditTool(newEndLabelEditTool, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_PALETTE__END_LABEL_EDIT_TOOL, newEndLabelEditTool, newEndLabelEditTool));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<NodeTool> getNodeTools() {
        if (this.nodeTools == null) {
            this.nodeTools = new EObjectContainmentEList<>(NodeTool.class, this, DiagramPackage.EDGE_PALETTE__NODE_TOOLS);
        }
        return this.nodeTools;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<NodeTool> getQuickAccessTools() {
        if (this.quickAccessTools == null) {
            this.quickAccessTools = new EObjectContainmentEList<>(NodeTool.class, this, DiagramPackage.EDGE_PALETTE__QUICK_ACCESS_TOOLS);
        }
        return this.quickAccessTools;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<EdgeReconnectionTool> getEdgeReconnectionTools() {
        if (this.edgeReconnectionTools == null) {
            this.edgeReconnectionTools = new EObjectContainmentEList<>(EdgeReconnectionTool.class, this, DiagramPackage.EDGE_PALETTE__EDGE_RECONNECTION_TOOLS);
        }
        return this.edgeReconnectionTools;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<EdgeToolSection> getToolSections() {
        if (this.toolSections == null) {
            this.toolSections = new EObjectContainmentEList<>(EdgeToolSection.class, this, DiagramPackage.EDGE_PALETTE__TOOL_SECTIONS);
        }
        return this.toolSections;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case DiagramPackage.EDGE_PALETTE__DELETE_TOOL:
                return this.basicSetDeleteTool(null, msgs);
            case DiagramPackage.EDGE_PALETTE__CENTER_LABEL_EDIT_TOOL:
                return this.basicSetCenterLabelEditTool(null, msgs);
            case DiagramPackage.EDGE_PALETTE__BEGIN_LABEL_EDIT_TOOL:
                return this.basicSetBeginLabelEditTool(null, msgs);
            case DiagramPackage.EDGE_PALETTE__END_LABEL_EDIT_TOOL:
                return this.basicSetEndLabelEditTool(null, msgs);
            case DiagramPackage.EDGE_PALETTE__NODE_TOOLS:
                return ((InternalEList<?>) this.getNodeTools()).basicRemove(otherEnd, msgs);
            case DiagramPackage.EDGE_PALETTE__QUICK_ACCESS_TOOLS:
                return ((InternalEList<?>) this.getQuickAccessTools()).basicRemove(otherEnd, msgs);
            case DiagramPackage.EDGE_PALETTE__EDGE_RECONNECTION_TOOLS:
                return ((InternalEList<?>) this.getEdgeReconnectionTools()).basicRemove(otherEnd, msgs);
            case DiagramPackage.EDGE_PALETTE__TOOL_SECTIONS:
                return ((InternalEList<?>) this.getToolSections()).basicRemove(otherEnd, msgs);
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
            case DiagramPackage.EDGE_PALETTE__DELETE_TOOL:
                return this.getDeleteTool();
            case DiagramPackage.EDGE_PALETTE__CENTER_LABEL_EDIT_TOOL:
                return this.getCenterLabelEditTool();
            case DiagramPackage.EDGE_PALETTE__BEGIN_LABEL_EDIT_TOOL:
                return this.getBeginLabelEditTool();
            case DiagramPackage.EDGE_PALETTE__END_LABEL_EDIT_TOOL:
                return this.getEndLabelEditTool();
            case DiagramPackage.EDGE_PALETTE__NODE_TOOLS:
                return this.getNodeTools();
            case DiagramPackage.EDGE_PALETTE__QUICK_ACCESS_TOOLS:
                return this.getQuickAccessTools();
            case DiagramPackage.EDGE_PALETTE__EDGE_RECONNECTION_TOOLS:
                return this.getEdgeReconnectionTools();
            case DiagramPackage.EDGE_PALETTE__TOOL_SECTIONS:
                return this.getToolSections();
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
            case DiagramPackage.EDGE_PALETTE__DELETE_TOOL:
                this.setDeleteTool((DeleteTool) newValue);
                return;
            case DiagramPackage.EDGE_PALETTE__CENTER_LABEL_EDIT_TOOL:
                this.setCenterLabelEditTool((LabelEditTool) newValue);
                return;
            case DiagramPackage.EDGE_PALETTE__BEGIN_LABEL_EDIT_TOOL:
                this.setBeginLabelEditTool((LabelEditTool) newValue);
                return;
            case DiagramPackage.EDGE_PALETTE__END_LABEL_EDIT_TOOL:
                this.setEndLabelEditTool((LabelEditTool) newValue);
                return;
            case DiagramPackage.EDGE_PALETTE__NODE_TOOLS:
                this.getNodeTools().clear();
                this.getNodeTools().addAll((Collection<? extends NodeTool>) newValue);
                return;
            case DiagramPackage.EDGE_PALETTE__QUICK_ACCESS_TOOLS:
                this.getQuickAccessTools().clear();
                this.getQuickAccessTools().addAll((Collection<? extends NodeTool>) newValue);
                return;
            case DiagramPackage.EDGE_PALETTE__EDGE_RECONNECTION_TOOLS:
                this.getEdgeReconnectionTools().clear();
                this.getEdgeReconnectionTools().addAll((Collection<? extends EdgeReconnectionTool>) newValue);
                return;
            case DiagramPackage.EDGE_PALETTE__TOOL_SECTIONS:
                this.getToolSections().clear();
                this.getToolSections().addAll((Collection<? extends EdgeToolSection>) newValue);
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
            case DiagramPackage.EDGE_PALETTE__DELETE_TOOL:
                this.setDeleteTool((DeleteTool) null);
                return;
            case DiagramPackage.EDGE_PALETTE__CENTER_LABEL_EDIT_TOOL:
                this.setCenterLabelEditTool((LabelEditTool) null);
                return;
            case DiagramPackage.EDGE_PALETTE__BEGIN_LABEL_EDIT_TOOL:
                this.setBeginLabelEditTool((LabelEditTool) null);
                return;
            case DiagramPackage.EDGE_PALETTE__END_LABEL_EDIT_TOOL:
                this.setEndLabelEditTool((LabelEditTool) null);
                return;
            case DiagramPackage.EDGE_PALETTE__NODE_TOOLS:
                this.getNodeTools().clear();
                return;
            case DiagramPackage.EDGE_PALETTE__QUICK_ACCESS_TOOLS:
                this.getQuickAccessTools().clear();
                return;
            case DiagramPackage.EDGE_PALETTE__EDGE_RECONNECTION_TOOLS:
                this.getEdgeReconnectionTools().clear();
                return;
            case DiagramPackage.EDGE_PALETTE__TOOL_SECTIONS:
                this.getToolSections().clear();
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
            case DiagramPackage.EDGE_PALETTE__DELETE_TOOL:
                return this.deleteTool != null;
            case DiagramPackage.EDGE_PALETTE__CENTER_LABEL_EDIT_TOOL:
                return this.centerLabelEditTool != null;
            case DiagramPackage.EDGE_PALETTE__BEGIN_LABEL_EDIT_TOOL:
                return this.beginLabelEditTool != null;
            case DiagramPackage.EDGE_PALETTE__END_LABEL_EDIT_TOOL:
                return this.endLabelEditTool != null;
            case DiagramPackage.EDGE_PALETTE__NODE_TOOLS:
                return this.nodeTools != null && !this.nodeTools.isEmpty();
            case DiagramPackage.EDGE_PALETTE__QUICK_ACCESS_TOOLS:
                return this.quickAccessTools != null && !this.quickAccessTools.isEmpty();
            case DiagramPackage.EDGE_PALETTE__EDGE_RECONNECTION_TOOLS:
                return this.edgeReconnectionTools != null && !this.edgeReconnectionTools.isEmpty();
            case DiagramPackage.EDGE_PALETTE__TOOL_SECTIONS:
                return this.toolSections != null && !this.toolSections.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} // EdgePaletteImpl
