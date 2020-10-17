/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
import { getBasicType, HtmlRoot, SGraphFactory, PreRenderedElement } from 'sprotty';

/**
 * The graph factory used to create all the graph elements.
 *
 * @sbegaudeau
 */
export class GraphFactory extends SGraphFactory {
  /**
   * Creates the element with the given schema.
   * @param schema The model element schema
   * @param parent The parent element
   */
  createElement(schema, parent) {
    if (this.isPreRenderedSchema(schema)) {
      return this.initializeChild(new PreRenderedElement(), schema, parent);
    } else {
      return super.createElement(schema, parent);
    }
  }

  /**
   * Creates the root for the given schema.
   * @param schema The schema
   */
  createRoot(schema) {
    if (this.isHtmlRootSchema(schema)) {
      return this.initializeRoot(new HtmlRoot(), schema);
    } else {
      return super.createRoot(schema);
    }
  }

  /**
   * Indicates if the given schema is an HTML root schema.
   * @param schema The model root schema
   */
  isHtmlRootSchema(schema) {
    return getBasicType(schema) === 'html';
  }

  /**
   * Indicates if the given schema is a pre-rendered schema.
   * @param schema The model element schema
   */
  isPreRenderedSchema(schema) {
    return getBasicType(schema) === 'pre-rendered';
  }
}
