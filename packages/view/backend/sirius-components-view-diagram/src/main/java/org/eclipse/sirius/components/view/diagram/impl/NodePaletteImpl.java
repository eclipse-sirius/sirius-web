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
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.components.view.diagram.LabelEditTool;
import org.eclipse.sirius.components.view.diagram.NodePalette;
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.sirius.components.view.diagram.NodeToolSection;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Node Palette</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.NodePaletteImpl#getDeleteTool <em>Delete Tool</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.NodePaletteImpl#getLabelEditTool <em>Label Edit
 * Tool</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.NodePaletteImpl#getNodeTools <em>Node Tools</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.NodePaletteImpl#getEdgeTools <em>Edge Tools</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.NodePaletteImpl#getToolSections <em>Tool
 * Sections</em>}</li>
 * </ul>
 *
 * @generated
 */
public class NodePaletteImpl extends MinimalEObjectImpl.Container implements NodePalette {
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
     * The cached value of the '{@link #getLabelEditTool() <em>Label Edit Tool</em>}' containment reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getLabelEditTool()
     * @generated
     * @ordered
     */
    protected LabelEditTool labelEditTool;

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
    protected EList<NodeToolSection> toolSections;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected NodePaletteImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DiagramPackage.Literals.NODE_PALETTE;
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
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DiagramPackage.NODE_PALETTE__DELETE_TOOL, oldDeleteTool, newDeleteTool);
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
                msgs = ((InternalEObject) this.deleteTool).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.NODE_PALETTE__DELETE_TOOL, null, msgs);
            if (newDeleteTool != null)
                msgs = ((InternalEObject) newDeleteTool).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.NODE_PALETTE__DELETE_TOOL, null, msgs);
            msgs = this.basicSetDeleteTool(newDeleteTool, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.NODE_PALETTE__DELETE_TOOL, newDeleteTool, newDeleteTool));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public LabelEditTool getLabelEditTool() {
        return this.labelEditTool;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetLabelEditTool(LabelEditTool newLabelEditTool, NotificationChain msgs) {
        LabelEditTool oldLabelEditTool = this.labelEditTool;
        this.labelEditTool = newLabelEditTool;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DiagramPackage.NODE_PALETTE__LABEL_EDIT_TOOL, oldLabelEditTool, newLabelEditTool);
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
    public void setLabelEditTool(LabelEditTool newLabelEditTool) {
        if (newLabelEditTool != this.labelEditTool) {
            NotificationChain msgs = null;
            if (this.labelEditTool != null)
                msgs = ((InternalEObject) this.labelEditTool).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.NODE_PALETTE__LABEL_EDIT_TOOL, null, msgs);
            if (newLabelEditTool != null)
                msgs = ((InternalEObject) newLabelEditTool).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.NODE_PALETTE__LABEL_EDIT_TOOL, null, msgs);
            msgs = this.basicSetLabelEditTool(newLabelEditTool, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.NODE_PALETTE__LABEL_EDIT_TOOL, newLabelEditTool, newLabelEditTool));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<NodeTool> getNodeTools() {
        if (this.nodeTools == null) {
            this.nodeTools = new EObjectContainmentEList<>(NodeTool.class, this, DiagramPackage.NODE_PALETTE__NODE_TOOLS);
        }
        return this.nodeTools;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<EdgeTool> getEdgeTools() {
        if (this.edgeTools == null) {
            this.edgeTools = new EObjectContainmentEList<>(EdgeTool.class, this, DiagramPackage.NODE_PALETTE__EDGE_TOOLS);
        }
        return this.edgeTools;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<NodeToolSection> getToolSections() {
        if (this.toolSections == null) {
            this.toolSections = new EObjectContainmentEList<>(NodeToolSection.class, this, DiagramPackage.NODE_PALETTE__TOOL_SECTIONS);
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
            case DiagramPackage.NODE_PALETTE__DELETE_TOOL:
                return this.basicSetDeleteTool(null, msgs);
            case DiagramPackage.NODE_PALETTE__LABEL_EDIT_TOOL:
                return this.basicSetLabelEditTool(null, msgs);
            case DiagramPackage.NODE_PALETTE__NODE_TOOLS:
                return ((InternalEList<?>) this.getNodeTools()).basicRemove(otherEnd, msgs);
            case DiagramPackage.NODE_PALETTE__EDGE_TOOLS:
                return ((InternalEList<?>) this.getEdgeTools()).basicRemove(otherEnd, msgs);
            case DiagramPackage.NODE_PALETTE__TOOL_SECTIONS:
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
            case DiagramPackage.NODE_PALETTE__DELETE_TOOL:
                return this.getDeleteTool();
            case DiagramPackage.NODE_PALETTE__LABEL_EDIT_TOOL:
                return this.getLabelEditTool();
            case DiagramPackage.NODE_PALETTE__NODE_TOOLS:
                return this.getNodeTools();
            case DiagramPackage.NODE_PALETTE__EDGE_TOOLS:
                return this.getEdgeTools();
            case DiagramPackage.NODE_PALETTE__TOOL_SECTIONS:
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
            case DiagramPackage.NODE_PALETTE__DELETE_TOOL:
                this.setDeleteTool((DeleteTool) newValue);
                return;
            case DiagramPackage.NODE_PALETTE__LABEL_EDIT_TOOL:
                this.setLabelEditTool((LabelEditTool) newValue);
                return;
            case DiagramPackage.NODE_PALETTE__NODE_TOOLS:
                this.getNodeTools().clear();
                this.getNodeTools().addAll((Collection<? extends NodeTool>) newValue);
                return;
            case DiagramPackage.NODE_PALETTE__EDGE_TOOLS:
                this.getEdgeTools().clear();
                this.getEdgeTools().addAll((Collection<? extends EdgeTool>) newValue);
                return;
            case DiagramPackage.NODE_PALETTE__TOOL_SECTIONS:
                this.getToolSections().clear();
                this.getToolSections().addAll((Collection<? extends NodeToolSection>) newValue);
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
            case DiagramPackage.NODE_PALETTE__DELETE_TOOL:
                this.setDeleteTool((DeleteTool) null);
                return;
            case DiagramPackage.NODE_PALETTE__LABEL_EDIT_TOOL:
                this.setLabelEditTool((LabelEditTool) null);
                return;
            case DiagramPackage.NODE_PALETTE__NODE_TOOLS:
                this.getNodeTools().clear();
                return;
            case DiagramPackage.NODE_PALETTE__EDGE_TOOLS:
                this.getEdgeTools().clear();
                return;
            case DiagramPackage.NODE_PALETTE__TOOL_SECTIONS:
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
            case DiagramPackage.NODE_PALETTE__DELETE_TOOL:
                return this.deleteTool != null;
            case DiagramPackage.NODE_PALETTE__LABEL_EDIT_TOOL:
                return this.labelEditTool != null;
            case DiagramPackage.NODE_PALETTE__NODE_TOOLS:
                return this.nodeTools != null && !this.nodeTools.isEmpty();
            case DiagramPackage.NODE_PALETTE__EDGE_TOOLS:
                return this.edgeTools != null && !this.edgeTools.isEmpty();
            case DiagramPackage.NODE_PALETTE__TOOL_SECTIONS:
                return this.toolSections != null && !this.toolSections.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} // NodePaletteImpl
