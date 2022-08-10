/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
export class DataTransfer {
  data: { [key: string]: unknown };

  constructor() {
    this.data = {};
  }

  setData(format: string, data: unknown): void {
    this.data[format] = data;
  }

  getData(format: string): unknown {
    return this.data[format];
  }
}
