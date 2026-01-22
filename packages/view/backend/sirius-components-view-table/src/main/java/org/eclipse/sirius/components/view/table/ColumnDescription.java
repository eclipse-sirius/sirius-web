/*******************************************************************************
 * Copyright (c) 2024, 2025 CEA LIST.
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
package org.eclipse.sirius.components.view.table;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Column Description</b></em>'. <!-- end-user-doc
 * -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.table.ColumnDescription#getHeaderExpression <em>Header
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.table.ColumnDescription#getLabelExpression <em>Label
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.table.ColumnDescription#getIconExpression <em>Icon
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.table.ColumnDescription#getInitialWidthExpression <em>Initial Width
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.table.ColumnDescription#getIsResizableExpression <em>Is Resizable
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.table.ColumnDescription#getFilterWidgetExpression <em>Filter Widget
 * Expression</em>}</li>
 * </ul>
 *
 * @model
 * @generated
 * @see org.eclipse.sirius.components.view.table.TablePackage#getColumnDescription()
 */
public interface ColumnDescription extends EObject {

    /**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.sirius.components.view.table.TablePackage#getColumnDescription_Name()
	 * @model dataType="org.eclipse.sirius.components.view.Identifier"
	 * @generated
	 */
    String getName();

    /**
	 * Sets the value of the '{@link org.eclipse.sirius.components.view.table.ColumnDescription#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Domain Type</b></em>' attribute. The default value is <code>""</code>. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Domain Type</em>' attribute.
     * @model default="" dataType="org.eclipse.sirius.components.view.DomainType"
     * @generated
     * @see #setDomainType(String)
     * @see org.eclipse.sirius.components.view.table.TablePackage#getColumnDescription_DomainType()
     */
    String getDomainType();

    /**
	 * Sets the value of the '{@link org.eclipse.sirius.components.view.table.ColumnDescription#getDomainType <em>Domain Type</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Domain Type</em>' attribute.
	 * @see #getDomainType()
	 * @generated
	 */
    void setDomainType(String value);

    /**
	 * Returns the value of the '<em><b>Semantic Candidates Expression</b></em>' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @return the value of the '<em>Semantic Candidates Expression</em>' attribute.
	 * @see #setSemanticCandidatesExpression(String)
	 * @see org.eclipse.sirius.components.view.table.TablePackage#getColumnDescription_SemanticCandidatesExpression()
	 * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
	 * @generated
	 */
    String getSemanticCandidatesExpression();

    /**
	 * Sets the value of the '{@link org.eclipse.sirius.components.view.table.ColumnDescription#getSemanticCandidatesExpression <em>Semantic Candidates Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Semantic Candidates Expression</em>' attribute.
	 * @see #getSemanticCandidatesExpression()
	 * @generated
	 */
    void setSemanticCandidatesExpression(String value);

    /**
	 * Returns the value of the '<em><b>Precondition Expression</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the value of the '<em>Precondition Expression</em>' attribute.
	 * @see #setPreconditionExpression(String)
	 * @see org.eclipse.sirius.components.view.table.TablePackage#getColumnDescription_PreconditionExpression()
	 * @model default="" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
	 * @generated
	 */
    String getPreconditionExpression();

    /**
	 * Sets the value of the '{@link org.eclipse.sirius.components.view.table.ColumnDescription#getPreconditionExpression <em>Precondition Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Precondition Expression</em>' attribute.
	 * @see #getPreconditionExpression()
	 * @generated
	 */
    void setPreconditionExpression(String value);

    /**
	 * Returns the value of the '<em><b>Header Index Label Expression</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the value of the '<em>Header Index Label Expression</em>' attribute.
	 * @see #setHeaderIndexLabelExpression(String)
	 * @see org.eclipse.sirius.components.view.table.TablePackage#getColumnDescription_HeaderIndexLabelExpression()
	 * @model default="" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
	 * @generated
	 */
    String getHeaderIndexLabelExpression();

    /**
	 * Sets the value of the '{@link org.eclipse.sirius.components.view.table.ColumnDescription#getHeaderIndexLabelExpression <em>Header Index Label Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Header Index Label Expression</em>' attribute.
	 * @see #getHeaderIndexLabelExpression()
	 * @generated
	 */
    void setHeaderIndexLabelExpression(String value);

    /**
	 * Returns the value of the '<em><b>Header Label Expression</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the value of the '<em>Header Label Expression</em>' attribute.
	 * @see #setHeaderLabelExpression(String)
	 * @see org.eclipse.sirius.components.view.table.TablePackage#getColumnDescription_HeaderLabelExpression()
	 * @model default="" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
	 * @generated
	 */
    String getHeaderLabelExpression();

    /**
	 * Sets the value of the '{@link org.eclipse.sirius.components.view.table.ColumnDescription#getHeaderLabelExpression <em>Header Label Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Header Label Expression</em>' attribute.
	 * @see #getHeaderLabelExpression()
	 * @generated
	 */
    void setHeaderLabelExpression(String value);

    /**
	 * Returns the value of the '<em><b>Header Icon Expression</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the value of the '<em>Header Icon Expression</em>' attribute.
	 * @see #setHeaderIconExpression(String)
	 * @see org.eclipse.sirius.components.view.table.TablePackage#getColumnDescription_HeaderIconExpression()
	 * @model default="" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
	 * @generated
	 */
    String getHeaderIconExpression();

    /**
	 * Sets the value of the '{@link org.eclipse.sirius.components.view.table.ColumnDescription#getHeaderIconExpression <em>Header Icon Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Header Icon Expression</em>' attribute.
	 * @see #getHeaderIconExpression()
	 * @generated
	 */
    void setHeaderIconExpression(String value);

    /**
	 * Returns the value of the '<em><b>Initial Width Expression</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the value of the '<em>Initial Width Expression</em>' attribute.
	 * @see #setInitialWidthExpression(String)
	 * @see org.eclipse.sirius.components.view.table.TablePackage#getColumnDescription_InitialWidthExpression()
	 * @model default="" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
	 * @generated
	 */
    String getInitialWidthExpression();

    /**
	 * Sets the value of the '{@link org.eclipse.sirius.components.view.table.ColumnDescription#getInitialWidthExpression <em>Initial Width Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Initial Width Expression</em>' attribute.
	 * @see #getInitialWidthExpression()
	 * @generated
	 */
    void setInitialWidthExpression(String value);

    /**
	 * Returns the value of the '<em><b>Is Resizable Expression</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the value of the '<em>Is Resizable Expression</em>' attribute.
	 * @see #setIsResizableExpression(String)
	 * @see org.eclipse.sirius.components.view.table.TablePackage#getColumnDescription_IsResizableExpression()
	 * @model default="" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
	 * @generated
	 */
    String getIsResizableExpression();

    /**
	 * Sets the value of the '{@link org.eclipse.sirius.components.view.table.ColumnDescription#getIsResizableExpression <em>Is Resizable Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Resizable Expression</em>' attribute.
	 * @see #getIsResizableExpression()
	 * @generated
	 */
    void setIsResizableExpression(String value);

    /**
	 * Returns the value of the '<em><b>Filter Widget Expression</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the value of the '<em>Filter Widget Expression</em>' attribute.
	 * @see #setFilterWidgetExpression(String)
	 * @see org.eclipse.sirius.components.view.table.TablePackage#getColumnDescription_FilterWidgetExpression()
	 * @model default="" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
	 * @generated
	 */
    String getFilterWidgetExpression();

    /**
	 * Sets the value of the '{@link org.eclipse.sirius.components.view.table.ColumnDescription#getFilterWidgetExpression <em>Filter Widget Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Filter Widget Expression</em>' attribute.
	 * @see #getFilterWidgetExpression()
	 * @generated
	 */
    void setFilterWidgetExpression(String value);

    /**
	 * Returns the value of the '<em><b>Is Sortable Expression</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the value of the '<em>Is Sortable Expression</em>' attribute.
	 * @see #setIsSortableExpression(String)
	 * @see org.eclipse.sirius.components.view.table.TablePackage#getColumnDescription_IsSortableExpression()
	 * @model default="" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
	 * @generated
	 */
    String getIsSortableExpression();

    /**
	 * Sets the value of the '{@link org.eclipse.sirius.components.view.table.ColumnDescription#getIsSortableExpression <em>Is Sortable Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Sortable Expression</em>' attribute.
	 * @see #getIsSortableExpression()
	 * @generated
	 */
    void setIsSortableExpression(String value);

} // ColumnDescription
