/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.GroupPalette;
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.sirius.components.view.diagram.ToolSection;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Group Palette</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.GroupPaletteImpl#getNodeTools <em>Node Tools</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.GroupPaletteImpl#getQuickAccessTools <em>Quick Access Tools</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.GroupPaletteImpl#getToolSections <em>Tool Sections</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GroupPaletteImpl extends MinimalEObjectImpl.Container implements GroupPalette {
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
     * The cached value of the '{@link #getToolSections() <em>Tool Sections</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getToolSections()
     * @generated
     * @ordered
     */
    protected EList<ToolSection> toolSections;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected GroupPaletteImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return DiagramPackage.Literals.GROUP_PALETTE;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<NodeTool> getNodeTools() {
		if (nodeTools == null)
		{
			nodeTools = new EObjectContainmentEList<NodeTool>(NodeTool.class, this, DiagramPackage.GROUP_PALETTE__NODE_TOOLS);
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
			quickAccessTools = new EObjectContainmentEList<NodeTool>(NodeTool.class, this, DiagramPackage.GROUP_PALETTE__QUICK_ACCESS_TOOLS);
		}
		return quickAccessTools;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<ToolSection> getToolSections() {
		if (toolSections == null)
		{
			toolSections = new EObjectContainmentEList<ToolSection>(ToolSection.class, this, DiagramPackage.GROUP_PALETTE__TOOL_SECTIONS);
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
			case DiagramPackage.GROUP_PALETTE__NODE_TOOLS:
				return ((InternalEList<?>)getNodeTools()).basicRemove(otherEnd, msgs);
			case DiagramPackage.GROUP_PALETTE__QUICK_ACCESS_TOOLS:
				return ((InternalEList<?>)getQuickAccessTools()).basicRemove(otherEnd, msgs);
			case DiagramPackage.GROUP_PALETTE__TOOL_SECTIONS:
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
			case DiagramPackage.GROUP_PALETTE__NODE_TOOLS:
				return getNodeTools();
			case DiagramPackage.GROUP_PALETTE__QUICK_ACCESS_TOOLS:
				return getQuickAccessTools();
			case DiagramPackage.GROUP_PALETTE__TOOL_SECTIONS:
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
			case DiagramPackage.GROUP_PALETTE__NODE_TOOLS:
				getNodeTools().clear();
				getNodeTools().addAll((Collection<? extends NodeTool>)newValue);
				return;
			case DiagramPackage.GROUP_PALETTE__QUICK_ACCESS_TOOLS:
				getQuickAccessTools().clear();
				getQuickAccessTools().addAll((Collection<? extends NodeTool>)newValue);
				return;
			case DiagramPackage.GROUP_PALETTE__TOOL_SECTIONS:
				getToolSections().clear();
				getToolSections().addAll((Collection<? extends ToolSection>)newValue);
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
			case DiagramPackage.GROUP_PALETTE__NODE_TOOLS:
				getNodeTools().clear();
				return;
			case DiagramPackage.GROUP_PALETTE__QUICK_ACCESS_TOOLS:
				getQuickAccessTools().clear();
				return;
			case DiagramPackage.GROUP_PALETTE__TOOL_SECTIONS:
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
			case DiagramPackage.GROUP_PALETTE__NODE_TOOLS:
				return nodeTools != null && !nodeTools.isEmpty();
			case DiagramPackage.GROUP_PALETTE__QUICK_ACCESS_TOOLS:
				return quickAccessTools != null && !quickAccessTools.isEmpty();
			case DiagramPackage.GROUP_PALETTE__TOOL_SECTIONS:
				return toolSections != null && !toolSections.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // GroupPaletteImpl
