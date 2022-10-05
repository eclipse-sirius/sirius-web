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

export class ModelData {
  public children: ModelData[];
  constructor(
    public readonly id: string,
    public readonly label: string,
    public readonly kind: string,
    public readonly imageURL: string,
    public readonly serverId: string,
    public readonly projectId: string,
    public readonly hasChildren: boolean
  ) {
    this.children = [];
  }
}
