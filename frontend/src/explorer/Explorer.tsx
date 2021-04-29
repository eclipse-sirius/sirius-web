/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
import React from 'react';
import { Tree } from 'tree/Tree';
import styles from './Explorer.module.css';
import { ExplorerProps } from './Explorer.types';

export const Explorer = ({ editingContextId, tree, onExpand, selection, setSelection, readOnly }: ExplorerProps) => {
  return (
    <div className={styles.explorer} data-testid="explorer">
      <Tree
        editingContextId={editingContextId}
        tree={tree}
        onExpand={onExpand}
        selection={selection}
        setSelection={setSelection}
        readOnly={readOnly}
      />
    </div>
  );
};
