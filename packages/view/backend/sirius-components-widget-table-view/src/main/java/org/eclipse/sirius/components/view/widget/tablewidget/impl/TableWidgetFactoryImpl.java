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
package org.eclipse.sirius.components.view.widget.tablewidget.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.sirius.components.view.widget.tablewidget.*;
import org.eclipse.sirius.components.view.widget.tablewidget.TableWidgetDescription;
import org.eclipse.sirius.components.view.widget.tablewidget.TableWidgetFactory;
import org.eclipse.sirius.components.view.widget.tablewidget.TableWidgetPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 * @generated
 */
public class TableWidgetFactoryImpl extends EFactoryImpl implements TableWidgetFactory {

    /**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public TableWidgetFactoryImpl() {
		super();
	}

    /**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public static TableWidgetFactory init() {
		try
		{
			TableWidgetFactory theTableWidgetFactory = (TableWidgetFactory)EPackage.Registry.INSTANCE.getEFactory(TableWidgetPackage.eNS_URI);
			if (theTableWidgetFactory != null)
			{
				return theTableWidgetFactory;
			}
		}
		catch (Exception exception)
		{
			EcorePlugin.INSTANCE.log(exception);
		}
		return new TableWidgetFactoryImpl();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EObject create(EClass eClass) {
		switch (eClass.getClassifierID())
		{
			case TableWidgetPackage.TABLE_WIDGET_DESCRIPTION: return createTableWidgetDescription();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public TableWidgetDescription createTableWidgetDescription() {
		TableWidgetDescriptionImpl tableWidgetDescription = new TableWidgetDescriptionImpl();
		return tableWidgetDescription;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public TableWidgetPackage getTableWidgetPackage() {
		return (TableWidgetPackage)getEPackage();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
    @Deprecated
    public static TableWidgetPackage getPackage() {
		return TableWidgetPackage.eINSTANCE;
	}

} // TableWidgetFactoryImpl
